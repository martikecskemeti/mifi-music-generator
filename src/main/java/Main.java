import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.*;

/**
 * Created by marti on 2017.06.20..
 */
public class Main {

    public static void main(String[] args) {


        NaturalLanguageUnderstanding service = new NaturalLanguageUnderstanding(
                NaturalLanguageUnderstanding.VERSION_DATE_2017_02_27,
                "56cda265-786b-421c-9690-2bd3986be8a7",
                "azoEpvJAuaCs"
        );


        String text = "IBM is an American multinational technology " +
                "company headquartered in Armonk, New York, " +
                "United States, with operations in over 170 countries.";

        EntitiesOptions entitiesOptions = new EntitiesOptions.Builder()
                .emotion(true)
                .sentiment(true)
                .limit(2)
                .build();

        KeywordsOptions keywordsOptions = new KeywordsOptions.Builder()
                .emotion(true)
                .sentiment(true)
                .limit(2)
                .build();

        Features features = new Features.Builder()
                .entities(entitiesOptions)
                .keywords(keywordsOptions)
                .build();

        AnalyzeOptions parameters = new AnalyzeOptions.Builder()
                .text(text)
                .features(features)
                .build();

        AnalysisResults response = service
                .analyze(parameters)
                .execute();
        System.out.println(response);
}
}
