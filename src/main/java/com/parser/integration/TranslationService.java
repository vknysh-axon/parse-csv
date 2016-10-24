package com.parser.integration;

import org.apache.log4j.Logger;

/**
 * Translation service.
 *
 * @author vknysh
 */
public class TranslationService {

    private static final Logger LOG = Logger.getLogger(TranslationService.class);

    public String translate(String phrasesToTranslate) {
        return translateApiCall(phrasesToTranslate);
    }

    /**
     * Here should be performed API call.
     * For creating JSON request object and parsing response, Jakson library should be used.
     * For performing POST call, simple {@code java.net.HttpURLConnection} may be used.
     *
     * @param input string to translate
     * @return translated string
     */
    private String translateApiCall(String input) {
        Request request = new Request();
        return request.post(input);
    }

    private class Request {
        public String post(String request) {
//            try {
//                Thread.sleep(200);
//            } catch (InterruptedException e) {
//                LOG.error(e);
//            }

            return "TRANSLATED: " + request;
        }
    }
}
