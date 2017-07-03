import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.*;
import model.Text;
import model.User;
import model.Word;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by marti on 2017.06.20..
 */
public class Main {

    private static void populateDb(EntityManager em) {



//        System.out.println(results);
//        System.out.println(results.getSentiment().getDocument().getScore());
//        System.out.println(results.getEntities().get(0).getSentiment());
//        System.out.println(results.getEntities().get(0).getEmotion().getJoy());

        String userInput =
                "";

        User user = new User("Kiki");
        Text text = new Text(userInput);

        AnalysisResults results = Controller.getData(text);

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(user);

        text.setSumSentiment(results.getSentiment().getDocument().getScore());
        em.persist(text);
        user.addTexts(text);

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
            text.addWords(word);
        }

        transaction.commit();
        System.out.println("stuff saved.");

    }

    private static List<String> getEmotionsFromText(EntityManager em) {
        List<String> emotions = new ArrayList<>();
        /*List<Double> emotionScores = new ArrayList<>();
        List<Word> results = em.createNamedQuery("Word.getAllKeywordsWithScores", Word.class)
                .getResultList();
        }*/

        List result = em.createQuery("SELECT s.name, s.anger, s.disgust, s.fear, s.joy, s.sadness FROM Word s WHERE s.text.id = 6").getResultList();


        for (Iterator i = result.iterator(); i.hasNext(); ) {
            Object[] values = (Object[]) i.next();
            List<Double> doubles = new ArrayList<>();

            String name = (String) values[0];

            for (int k = 1; k < values.length; k++) {
                doubles.add((Double) values [k]);
            }

            Map<Integer, String> emotionMap = new HashMap<>();
            emotionMap.put(0, "anger");
            emotionMap.put(1, "disgust");
            emotionMap.put(2, "fear");
            emotionMap.put(3, "joy");
            emotionMap.put(4, "sadness");

            double maxi = -1;
            int emo = 0;
            for (int j = 0; j < doubles.size(); j++) {
                if (doubles.get(j) > maxi) {
                    maxi = doubles.get(j);
                    emo = j;
                }
            }
            System.out.println(name);
            System.out.println(maxi);
            System.out.println(emotionMap.get(emo));
            System.out.println("-------------");

        }

        return emotions;
    }


    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("music-gen");
        EntityManager em = emf.createEntityManager();

        //populateDb(em);

//        Student foundStudent1 = em.find(Student.class, 1L);
//        System.out.println("--Found student #1");
//        System.out.println("----name----" + foundStudent1.getName());
//        System.out.println("----address of student----" + foundStudent1.getAddress());


        getEmotionsFromText(em);
        em.close();
        emf.close();

    }
}
