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

/**
 * Created by sfanni on 7/5/17.
 */

@Component
public class DbController {

    private TextRepository textRepository;
    private UserRepository userRepository;
    private WordRepository wordRepository;

    @Autowired
    public DbController(TextRepository textRepository, UserRepository userRepository, WordRepository wordRepository) {
        this.wordRepository = wordRepository;
        this.userRepository = userRepository;
        this.textRepository = textRepository;
    }


    public void populateDb(Text text, AnalysisResults results) {

        User user = new User("Maci");
        userRepository.save(user);

        text.setSumSentiment(results.getSentiment().getDocument().getScore());
        user.addText(text);
        textRepository.save(text);

        for(int i=0; i<results.getKeywords().size(); i++){
            Word word = new Word();
            word.setName(results.getKeywords().get(i).getText());
            word.setAnger(results.getKeywords().get(i).getEmotion().getAnger());
            word.setFear(results.getKeywords().get(i).getEmotion().getFear());
            word.setJoy(results.getKeywords().get(i).getEmotion().getJoy());
            word.setDisgust(results.getKeywords().get(i).getEmotion().getDisgust());
            word.setSadness(results.getKeywords().get(i).getEmotion().getSadness());
            word.setSentiment(results.getKeywords().get(i).getSentiment().getScore());
            text.addWord(word);
            wordRepository.save(word);
        }
        System.out.println("stuff saved.");

        }
}
