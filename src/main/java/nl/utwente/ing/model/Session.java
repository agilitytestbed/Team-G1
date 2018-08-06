package nl.utwente.ing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.utwente.ing.model.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Session {

    @Id
    @Column(name = "sid")
    private String id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "session_transaction",
            joinColumns = { @JoinColumn(name = "sid") },
            inverseJoinColumns = { @JoinColumn(name = "tid") }
    )
    @JsonIgnore
    private Set<Transaction> transactions;

    public Session(String id) {
        this.id = id;
        this.transactions = new HashSet<>();
    }

    public Session() {
        this.transactions = new HashSet<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(id, session.id) &&
                Objects.equals(transactions, session.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transactions);
    }
    private static final Logger log = LoggerFactory.getLogger(Session.class);
    @Override
    public String toString() {
        String sessionText = "";
        sessionText += "Session{" +
                       "id='" + id + '\'' +
                       ", transactions=[  \n";
        for (Transaction tr: transactions){
            sessionText += tr.toString() + ",\n";
        }
        return sessionText.substring(0, sessionText.length() - 3).concat("]}");
    }
}
