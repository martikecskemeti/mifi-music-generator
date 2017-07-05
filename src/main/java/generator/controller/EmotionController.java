package generator.controller;

import generator.model.Text;
import generator.model.Word;
import generator.repo.TextRepository;
import generator.repo.UserRepository;
import generator.repo.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.*;

/**
 * Created by sfanni on 7/4/17.
 */

@Component
public class EmotionController {

    private TextRepository textRepository;

    private WordRepository wordRepository;

    @Autowired
    public EmotionController(TextRepository textRepository, WordRepository wordRepository) {
        this.textRepository = textRepository;
        this.wordRepository = wordRepository;
    }

    public List<String> getOrderedEmotions(Text text) {
        List<String> orderedEmotions = new ArrayList<>();
        try {
            Text textResult = textRepository.findOne(text.getId());
            String[] separatedWords = textResult.getText().split("[\\p{Punct}\\s]+");

            Map<String, String> emotions = getEmotionsFromText(text);

            for (String separatedWord : separatedWords) {
                String emo = emotions.get(separatedWord);
                if (emo != null) {
                    orderedEmotions.add(emo);
                }
            }
        }
        catch (NoResultException e) {
            System.out.println("Id not found");
        }
        return orderedEmotions;
    }

    private  Map<String, String> getEmotionsFromText(Text text) {
        textRepository.findOne(text.getId());
        Map<String, String> emotions = new HashMap<>();
        List<Word> words =  wordRepository.findByTextId(text.getId());

        for (Word word : words) {
            emotions.put(word.getName(), getHighestEmotion(word));
        }
        return emotions;
    }

    private String  getHighestEmotion(Word word) {
        Map <String, Double> emotionScores = new HashMap<>();
        emotionScores.put("anger", word.getAnger());
        emotionScores.put("joy", word.getJoy());
        emotionScores.put("fear", word.getFear());
        emotionScores.put("sadness", word.getSadness());
        emotionScores.put("disgust", word.getDisgust());

        Map.Entry<String, Double> max = null;
        for (Map.Entry<String, Double> entry : emotionScores.entrySet()) {
            if (max == null || entry.getValue().compareTo(max.getValue()) > 0) {
                max = entry;
            }
        }
        return max.getKey();
    }
}
