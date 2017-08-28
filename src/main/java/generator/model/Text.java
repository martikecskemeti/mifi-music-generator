package generator.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by marti on 2017.06.22..
 */

@Entity
public class Text implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String content;

    private double sumSentiment;

    @OneToMany(mappedBy = "text")
    private List<Word> words = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Text(){}

    public Text(String content) {
        this.content = content;
    }

    public List<Word> getWords() {
        return words;
    }

    public void addWord(Word word) {
        if(word.getText() != this){
            word.setText(this);
        }
        words.add(word);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getSumSentiment() {
        return sumSentiment;
    }

    public void setSumSentiment(double sumSentiment) {
        this.sumSentiment = sumSentiment;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
