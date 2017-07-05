import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import controller.DbController;
import controller.MainController;
import model.Text;


/**
 * Created by marti on 2017.06.20..
 */
public class Main {




    public static void main(String[] args) {

        String userInput ="I love apples, but I hate oranges. ";
        //"I hate this music. I fear in dark. I love apples, but I hate oranges. Your story is sad. ";
        String title = "title";

        Text text = new Text(userInput);
        AnalysisResults results = MainController.getSentimentResults(text);

        DbController.populateDb(text, results);
        MainController.generateMusic(DbController.generateOrderedEmotions(text),title);

    }
}
