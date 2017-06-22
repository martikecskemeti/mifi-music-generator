import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sfanni on 6/21/17.
 */
public class Controller {

    public static AnalysisResults getData() {

        NaturalLanguageUnderstanding service = new NaturalLanguageUnderstanding(
                NaturalLanguageUnderstanding.VERSION_DATE_2017_02_27,
                "56cda265-786b-421c-9690-2bd3986be8a7",
                "azoEpvJAuaCs"
        );


        String text = "IBM is an American multinational technology " +
                "Thank you and have a nice dog " +
                "I love apples! I don't like oranges.";


        KeywordsOptions keywordsOptions = new KeywordsOptions.Builder()
                .emotion(true)
                .sentiment(true)
                .limit(100)
                .build();

        List<String> targets = new ArrayList();
        targets.add("apples");
        targets.add("oranges");

        SentimentOptions sentiment = new SentimentOptions.Builder()
                .build();


        Features features = new Features.Builder()
                .keywords(keywordsOptions)
                .sentiment(sentiment)
                .build();

        AnalyzeOptions parameters = new AnalyzeOptions.Builder()
                .text(text)
                .features(features)
                .build();

        AnalysisResults response = service
                .analyze(parameters)
                .execute();

        return response;
    }
}

