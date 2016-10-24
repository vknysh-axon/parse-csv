Setup and Run 
=============

(maven, java v8 are required)  

1. Download project from github.
2. Build project using `maven` with command: `mvn clean install`.
3. After that, in the `target` folder, artifact `parse-csv-1.0-SNAPSHOT-jar-with-dependencies.jar` will be available.
4. Navigate to the `target` folder, and execute artifact: `java -jar parse-csv-1.0-SNAPSHOT-jar-with-dependencies.jar`.
5. If application started correctly, info message should be available:
    ```
    Route: route1 started and consuming from: Endpoint[file://inbox/counter?include=%28%3Fi%29.*.csv&recursive=true]
    Route: route2 started and consuming from: Endpoint[file://inbox/translate?include=%28%3Fi%29.*.csv&recursive=true]
    Total 2 routes, of which 2 are started.
    Apache Camel 2.17.3 (CamelContext: camel-1) started in 0.379 seconds
    ```
6. Also, after start, in directory where jar is executed, app will create `inbox` folder with two subfolders: `counter`, `translate`. 
    CSV files for counting should be placed in `counter` folder, files for translation in `translate` folder.
7. After placing file in `counter` folder, - calculation will be performed, and printed into console.
8. After placing file in `translate` folder, - translation will be performed, and result will be persisted to `out/translate/translate_result.csv` file.
9. To restrict memory consumption `-Xmx` flag may be used. App gracefully works with `-Xmx256M` restriction. 

