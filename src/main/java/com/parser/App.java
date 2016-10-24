package com.parser;

import com.parser.processor.CountProcessor;
import com.parser.processor.ResultProcessor;
import com.parser.processor.TranslateProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;
import org.apache.camel.model.dataformat.CsvDataFormat;
import org.apache.log4j.Logger;

/**
 * App.
 * {@code Camel} allow configure number of threads that should be executed
 * simultaneously during {@code split} and {@code parallelProcessing(true)}.
 * By default it 20, and may easily changed using {@code maxPoolSize} property
 * of the {@code defaultThreadPoolProfile}.
 */
public class App {
    private static final Logger LOG = Logger.getLogger(App.class);

    private Main main;

    public static void main(String[] args) throws Exception {
        App app = new App();
        app.boot();
    }

    private void boot() throws Exception {
        main = new Main();
        main.addRouteBuilder(new AppRouteBuilder());
        main.run();
    }

    private class AppRouteBuilder extends RouteBuilder {
        @Override
        public void configure() throws Exception {
            CsvDataFormat csv = new CsvDataFormat(",");
            csv.setSkipHeaderRecord(true);
            csv.setLazyLoad(true); //To not read whole file at once

            CountProcessor countProcessor = new CountProcessor();
            ResultProcessor resultProcessor = new ResultProcessor();
            TranslateProcessor translateProcessor = new TranslateProcessor();

            from("file:inbox/counter?recursive=true&include=(?i).*.csv")
                    .log("Start Count process.")
                    .unmarshal(csv)
                    .split(body())
                        .streaming() //To not load whole split result in memory
                        .parallelProcessing(true)
                        .bean(countProcessor, "process")
                    .end()
                    .log("Count process is finished.")
                    .bean(resultProcessor, "process");

            from("file:inbox/translate?recursive=true&include=(?i).*.csv")
                    .log("Start Translation process.")
                    .setHeader("CamelFileName", constant("translate_result.csv"))
                    .unmarshal(csv)
                    .split(body())
                        .streaming()
                        .parallelProcessing(true)
                        .bean(translateProcessor, "process")
                        .to("file:out/translate?fileExist=Append")
                    .end()
                    .log("Translation process is finished.");
        }
    }

}
