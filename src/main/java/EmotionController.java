import model.Text;
import model.Word;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.*;

/**
 * Created by sfanni on 7/4/17.
 */
public class EmotionController {


    public static List<String> getOrderedEmotions(EntityManager em, long id) {
        List<String> orderedEmotions = new ArrayList<>();
        try {
            Text textResult = em.createNamedQuery("Text.getTheText", Text.class).setParameter("id", id).getSingleResult();
            String[] separatedWords = textResult.getContent().split("[\\p{Punct}\\s]+");

            Map<String, String> emotions = getEmotionsFromText(em, id);

            for (String separatedWord : separatedWords) {
                for (String s : emotions.keySet()) {
                    if (separatedWord.equals(s)) {
                        orderedEmotions.add(emotions.get(s));
                    }
                }
            }
        }
        catch (NoResultException e) {
            System.out.println("Id not found");
        }
        return orderedEmotions;
    }

    private static Map<String, String> getEmotionsFromText(EntityManager em, long id) {
        Map<String, String> emotions = new HashMap<>();
        Map<Integer, String> emotionMap = createEmotionMap();
        List results = em.createNamedQuery("Word.getEmotionsOfText").setParameter("id", id)
                .getResultList();


        for (Object result : results) {
            Object[] resultArray = (Object[]) result;
            List<Double> emotionScores = new ArrayList<>();

            String name = (String) resultArray[0];

            for (int k = 1; k < resultArray.length; k++) {
                emotionScores.add((Double) resultArray[k]);
            }
            double maxi = -1;
            int emo = 0;
            for (int j = 0; j < emotionScores.size(); j++) {
                if (emotionScores.get(j) > maxi) {
                    maxi = emotionScores.get(j);
                    emo = j;
                }
            }
            emotions.put(name, emotionMap.get(emo));
        }
        return emotions;
    }

    private static Map<Integer,String> createEmotionMap() {
        Map<Integer, String> emotionMap = new HashMap<>();
        emotionMap.put(0, "anger");
        emotionMap.put(1, "disgust");
        emotionMap.put(2, "fear");
        emotionMap.put(3, "joy");
        emotionMap.put(4, "sadness");
        return emotionMap;
    }
}
