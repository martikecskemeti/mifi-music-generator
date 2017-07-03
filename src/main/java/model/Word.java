package model;

import javax.persistence.*;

/**
 * Created by marti on 2017.06.22..
 */
@NamedQueries( {
        @NamedQuery(name="Word.getAllKeywordsWithScores", query="SELECT s FROM Word s"),
        @NamedQuery(name="Word.getMaxScoredEmotionOfText", query ="SELECT s.anger, s.disgust, s.fear, s.joy, s.sadness FROM Word s WHERE s.id = 6")
})


@Entity
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private double sentiment;
    private double anger;
    private double disgust;
    private double fear;
    private double joy;
    private double sadness;

    @ManyToOne
    @JoinColumn(name = "text_id")
    private Text text;

    public Word() {}

    public Word(String name, double sentiment, double anger, double disgust, double fear, double joy, double sadness) {
        this.name = name;
        this.sentiment = sentiment;
        this.anger = anger;
        this.disgust = disgust;
        this.fear = fear;
        this.joy = joy;
        this.sadness = sadness;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSentiment() {
        return sentiment;
    }

    public void setSentiment(double sentiment) {
        this.sentiment = sentiment;
    }

    public double getAnger() {
        return anger;
    }

    public void setAnger(double anger) {
        this.anger = anger;
    }

    public double getDisgust() {
        return disgust;
    }

    public void setDisgust(double disgust) {
        this.disgust = disgust;
    }

    public double getFear() {
        return fear;
    }

    public void setFear(double fear) {
        this.fear = fear;
    }

    public double getJoy() {
        return joy;
    }

    public void setJoy(double joy) {
        this.joy = joy;
    }

    public double getSadness() {
        return sadness;
    }

    public void setSadness(double sadness) {
        this.sadness = sadness;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }
}
