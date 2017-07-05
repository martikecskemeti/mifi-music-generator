package controller;

import model.Text;
import model.Word;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.*;

/**
 * Created by sfanni on 7/4/17.
 */
public class EmotionController {


    public static List<String> getOrderedEmotions(EntityManager em, Text text) {
        List<String> orderedEmotions = new ArrayList<>();
        try {
            Text textResult = em.createNamedQuery("Text.getTheText", Text.class)
                    .setParameter("id", text.getId())
                    .getSingleResult();
            String[] separatedWords = textResult.getText().split("[\\p{Punct}\\s]+");

            Map<String, String> emotions = getEmotionsFromText(em, text);

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

    private static Map<String, String> getEmotionsFromText(EntityManager em, Text text) {
        Map<String, String> emotions = new HashMap<>();
        List<Word> words = em.createNamedQuery("Word.getAllKeywordsWithScores", Word.class)
                .setParameter("id", text.getId())
                .getResultList();

        for (Word word : words) {
            emotions.put(word.getName(), getHighestEmotion(word));
        }
        return emotions;
    }

    private static String  getHighestEmotion(Word word) {
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
