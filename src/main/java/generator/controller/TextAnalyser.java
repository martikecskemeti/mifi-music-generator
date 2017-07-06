package generator.controller;

import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.*;
import generator.model.Text;

/**
 * Created by sfanni on 6/21/17.
 */
public class TextAnalyser {
    private static final KeywordsOptions KEYWORDS_OPTIONS = new KeywordsOptions.Builder()
            .emotion(true)
            .sentiment(true)
            .limit(100)
            .build();

    private static final SentimentOptions SENTIMENT = new SentimentOptions.Builder()
            .build();

    private static final EntitiesOptions ENTITIES = new EntitiesOptions.Builder()
            .sentiment(true)
            .build();

    private static final Features FEATURES = new Features.Builder()
            .keywords(KEYWORDS_OPTIONS)
            .sentiment(SENTIMENT)
            .entities(ENTITIES)
            .build();

    private final NaturalLanguageUnderstanding service;

    public TextAnalyser(String userName, String password) {
        service = new NaturalLanguageUnderstanding(
                NaturalLanguageUnderstanding.VERSION_DATE_2017_02_27, userName, password);
    }

    public AnalysisResults getData(Text text) {

        AnalyzeOptions parameters = new AnalyzeOptions.Builder()
                .text(text.getText())
                .features(FEATURES)
                .build();

        return service
                .analyze(parameters)
                .execute();
    }
}

