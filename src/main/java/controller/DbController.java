package controller;

import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import model.Text;
import model.User;
import model.Word;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by sfanni on 7/5/17.
 */
public class DbController {

    public static List<String> generateOrderedEmotions(Text text) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("music-gen");
        EntityManager em = emf.createEntityManager();

        List<String> orderedEmotions = EmotionController.getOrderedEmotions(em, text);

        em.close();
        emf.close();
        return orderedEmotions;
    }

    public static void populateDb(Text text, AnalysisResults results) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("music-gen");
        EntityManager em = emf.createEntityManager();

        User user = new User("Kiki");

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(user);

        text.setSumSentiment(results.getSentiment().getDocument().getScore());
        em.persist(text);
        user.addText(text);

        for(int i=0; i<results.getKeywords().size(); i++){
            Word word = new Word();
            word.setName(results.getKeywords().get(i).getText());
            word.setAnger(results.getKeywords().get(i).getEmotion().getAnger());
            word.setFear(results.getKeywords().get(i).getEmotion().getFear());
            word.setJoy(results.getKeywords().get(i).getEmotion().getJoy());
            word.setDisgust(results.getKeywords().get(i).getEmotion().getDisgust());
            word.setSadness(results.getKeywords().get(i).getEmotion().getSadness());
            word.setSentiment(results.getKeywords().get(i).getSentiment().getScore());
            em.persist(word);
            text.addWord(word);
        }

        transaction.commit();
        System.out.println("stuff saved.");
        em.close();
        emf.close();

    }
}
