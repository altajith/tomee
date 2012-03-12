package jug.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@NamedQueries({
        @NamedQuery(name = Subject.FIND_ALL, query = "select s from Subject s"),
        @NamedQuery(name = Subject.FIND_BY_NAME_QUERY, query = "select s from Subject s where s.name = :name"),
        @NamedQuery(name = Subject.COUNT_VOTE, query = "select count(s) from Subject s left join s.votes v where v.value = :value and :name = s.name")
})
@XmlRootElement
public class Subject {
    public static final String FIND_BY_NAME_QUERY = "Subject.findByName";
    public static final String COUNT_VOTE = "Subject.countVoteNumber";
    public static final String FIND_ALL = "Subject.findAll";

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String question;

    @OneToMany(fetch = FetchType.EAGER)
    private Collection<Vote> votes = new ArrayList<Vote>();

    public Subject() {
        // no-op
    }

    public Subject(String name, String question) {
        this.name = name;
        this.question = question;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Collection<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Collection<Vote> votes) {
        this.votes = votes;
    }

    public int score() {
        int s = 0;
        for (Vote vote : votes) {
            if (vote.getValue().equals(Value.I_LIKE)) {
                s++;
            } else {
                s--;
            }
        }
        return s;
    }
}