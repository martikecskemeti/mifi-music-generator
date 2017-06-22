import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.*;
import model.Text;
import model.User;
import model.Word;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by marti on 2017.06.20..
 */
public class Main {

    public static void populateDb(EntityManager em) {



//        System.out.println(results);
//        System.out.println(results.getSentiment().getDocument().getScore());
//        System.out.println(results.getEntities().get(0).getSentiment());
//        System.out.println(results.getEntities().get(0).getEmotion().getJoy());

        String userInput = "IBM is an American multinational technology " +
                "Thank you and have a nice dog " +
                "I love apples! I don't like oranges.";

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


    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("music-gen");
        EntityManager em = emf.createEntityManager();

        populateDb(em);

//        Student foundStudent1 = em.find(Student.class, 1L);
//        System.out.println("--Found student #1");
//        System.out.println("----name----" + foundStudent1.getName());
//        System.out.println("----address of student----" + foundStudent1.getAddress());


        em.close();
        emf.close();

    }
}
