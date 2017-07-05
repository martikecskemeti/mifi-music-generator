package generator.controller;

import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import generator.model.Text;
import generator.model.User;
import generator.model.Word;
import generator.repo.TextRepository;
import generator.repo.UserRepository;
import generator.repo.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


@Controller

public class WebController {

    private EmotionController emotionController;
    private DbController dbController;

    @Autowired
    public WebController(DbController dbController, EmotionController emotionController) {
        this.dbController = dbController;
        this.emotionController = emotionController;
    }


    @RequestMapping("/generate")
    public String generating(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "generate";
    }


    @RequestMapping(value = "/textinput", method = RequestMethod.POST)
    public String getTextInput(@RequestParam Map<String, String> params) {
        String title=params.get("title");
        String textContent=params.get("text");

        System.out.println(title);
        System.out.println(textContent);
        Text text = new Text(textContent);

        AnalysisResults results = MainController.getSentimentResults(text);

        dbController.populateDb(text, results);
        MainController.generateMusic(emotionController.getOrderedEmotions(text),title);
        System.out.println(emotionController.getOrderedEmotions(text));
        System.out.println();
        return "generate";
    }
}


