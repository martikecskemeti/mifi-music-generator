import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.*;

/**
 * Created by marti on 2017.06.20..
 */
public class Main {

    public static void main(String[] args) {
        AnalysisResults results = Controller.getData();
        System.out.println(results);
        //System.out.println(results.getSentiment().getDocument().getScore());
        //System.out.println(results.getEntities().get(0).getSentiment());
        //System.out.println(results.getEntities().get(0).getEmotion().getJoy());

    }
}
