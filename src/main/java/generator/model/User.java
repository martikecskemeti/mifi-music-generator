package generator.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by marti on 2017.06.22..
 */

@Entity
@Table(name="person")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "user")
    private Set<Text> texts = new HashSet<>();

    private String name;

    public User() {}

    public User(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Text> getTexts() {
        return texts;
    }

    public void addText(Text text) {
        if(text.getUser() != this){
            text.setUser(this);
        }
        texts.add(text);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
