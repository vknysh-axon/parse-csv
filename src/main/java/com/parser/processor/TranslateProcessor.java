package com.parser.processor;

import com.parser.integration.TranslationService;

import java.util.List;

/**
 * Translate processor.
 *
 * @author vknysh
 */
public class TranslateProcessor {

    private TranslationService translationService = new TranslationService();

    public String process(List<String> csvRecord) {

        String comment = csvRecord.get(9);
        if (comment.isEmpty()) {
            return comment;
        }

        return translationService.translate(comment) + "\n";
    }
}
