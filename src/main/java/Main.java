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


        User user = new User("Kiki");
        Text text = new Text(0.89,"I like apples, but hate oranges.");
        Word firstWord = new Word("apples",0.75,0.55,0.45, 0.65,0.78,0.11);
        Word secondWord = new Word("oranges",0.75,0.55,0.45, 0.65,0.78,0.11);
        text.addWords(firstWord);
        text.addWords(secondWord);
        user.addTexts(text);

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(user);
        em.persist(text);
        em.persist(firstWord);
        em.persist(secondWord);
        transaction.commit();
        System.out.println("stuff saved.");

    }


    public static void main(String[] args) {
        //AnalysisResults results = Controller.getData();
        //System.out.println(results);
        //System.out.println(results.getSentiment().getDocument().getScore());
        //System.out.println(results.getEntities().get(0).getSentiment());
        //System.out.println(results.getEntities().get(0).getEmotion().getJoy());


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
