import jm.JMC;
import jm.music.data.Score;
import jm.util.Write;

import java.util.List;

/**
 * Created by marti on 2017.07.03..
 */
public class MusicController implements JMC {

    private Score score;
    private String title;

    public MusicController(String title) {
        this.title = title;
        this.score = new Score(this.title);

    }

    public void makeSong(List<String> emotions) {
       for (String emotion : emotions) {
           if(emotion.equals("joy")){
                Joy joy = new Joy(score);
                joy.completeScore();
            }else if(emotion.equals("sadness")){
               Sad sad = new Sad(score);
                sad.completeScore();
            }else if(emotion.equals("anger")){
                Anger anger = new Anger(score);
                anger.completeScore();
//           }else if(emotion.equals("disgust")){
//               Disgust disgust = new Disgust(score);
//               disgust.completeScore();
           }else{
               Fear fear = new Fear(score);
               fear.completeScore();
           }
           //Play.midi(score);
           renderAudio();
       }
    }

    private void renderAudio() {
        Write.midi(score, title +".mid");
    }
}
