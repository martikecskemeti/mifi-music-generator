package generator.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by marti on 2017.08.15..
 */
public class WatsonConnectionController {

    private static WatsonConnectionController instance;
    private String filePath = "src/main/watson.csv";
    private String USERNAME;
    private String PASSWORD;

    private WatsonConnectionController(){}

    public static WatsonConnectionController getInstance() {
        if (instance == null) {
            instance = new WatsonConnectionController();
            instance.setupUserAndPasswordFromFile();
        }
        return instance;
    }

    public String getUserName(){
        return USERNAME;
    }

    public String getPassword(){
        return PASSWORD;
    }

    private void setupUserAndPasswordFromFile() {
        try{
        List<String> allLinesList = Files.readAllLines(Paths.get(filePath));
        USERNAME = allLinesList.get(0);
        PASSWORD = allLinesList.get(1);
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
