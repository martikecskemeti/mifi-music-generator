package generator.controller;

import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import generator.model.Text;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by sfanni on 7/5/17.
 */
@Component
public class MainController {

    public static AnalysisResults getSentimentResults(Text text) {
        TextAnalyser analyser = new TextAnalyser(
                "56cda265-786b-421c-9690-2bd3986be8a7",
                "azoEpvJAuaCs");
        return analyser.getData(text);
    }


    public static void generateMusic(List<String> orderedEmotions,String title) {
        generator.controller.MusicController mc = new generator.controller.MusicController(title);
        mc.makeSong(orderedEmotions);
    }
}
