package controller;

import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import model.Text;
import org.apache.logging.log4j.core.config.plugins.util.ResolverUtil;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by sfanni on 7/5/17.
 */
public class MainController {


    public static AnalysisResults getSentimentResults(Text text) {
        TextAnalyser analyser = new TextAnalyser(
                "56cda265-786b-421c-9690-2bd3986be8a7",
                "azoEpvJAuaCs");
        return analyser.getData(text);
    }


    public static void generateMusic(List<String> orderedEmotions,String title) {
        controller.MusicController mc = new controller.MusicController(title);
        mc.makeSong(orderedEmotions);
    }
}
