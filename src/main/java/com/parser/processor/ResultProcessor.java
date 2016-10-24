package com.parser.processor;

import com.parser.counter.Counter;
import org.apache.log4j.Logger;

/**
 * Result com.parser.processor.
 */
public class ResultProcessor {

    private static final Logger LOG = Logger.getLogger(CountProcessor.class);
    private Counter counter = Counter.getInstance();

    public void process() {
        LOG.info("------------ Users count ------------");
        LOG.info(counter.getUsers());
        LOG.info("------------ Products count ------------");
        LOG.info(counter.getProducts());
        LOG.info("------------ Words count ------------");
        LOG.info(counter.getWords());

        counter.cleanCounters();

    }
}
