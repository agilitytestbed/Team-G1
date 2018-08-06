package nl.utwente.ing.model.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import nl.utwente.ing.repository.CategoryRepository;
import nl.utwente.ing.model.Category;
import nl.utwente.ing.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tid", updatable = false)
    private long id;

    @Column
    private LocalDateTime date;

    @Column
    private double amount;

    @Column
    private String externalIBAN;

    @Column
    private Type type;

    @ManyToOne//(fetch=FetchType.LAZY)
    @JoinColumn(name = "category", nullable = false)
    private Category category;

    @JsonIgnore
    @ManyToMany(mappedBy = "transactions")
    private Set<Session> session;

    public Transaction() {
    }

    public Transaction(LocalDateTime date, double amount, String externalIBAN, Type type, Category category) {
        this.date = date;
        this.amount = amount;
        this.externalIBAN = externalIBAN;
        this.type = type;
        this.category = category;
        this.session = new HashSet<>();
    }

    public Transaction(long id, LocalDateTime date, double amount, String externalIBAN, Type type, Category category) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.externalIBAN = externalIBAN;
        this.type = type;
        this.category = category;
        this.session = new HashSet<>();
    }

    public Set<Session> getSession() {
        return session;
    }

    public void setSession(Set<Session> session) {
        this.session = session;
    }

    public void setSession(Session session) {
        this.session.add(session);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getExternalIBAN() {
        return externalIBAN;
    }

    public void setExternalIBAN(String externalIBAN) {
        this.externalIBAN = externalIBAN;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", amount=" + amount +
                ", externalIBAN='" + externalIBAN + '\'' +
                ", category=" + category +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transaction that = (Transaction) o;
        return id == that.id &&
                Double.compare(that.amount, amount) == 0 &&
                Objects.equals(date, that.date) &&
                Objects.equals(externalIBAN, that.externalIBAN) &&
                Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, amount, externalIBAN, category);
    }

    public enum Type {
        DEPOSIT("deposit"),
        WITHDRAWAL("withdrawal");

        private String name;

        Type(String name) {
            this.name = name;
        }

        @JsonValue
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Component
    public static class RowMapper implements org.springframework.jdbc.core.RowMapper<Transaction> {

        private CategoryRepository categoryRepo;

        @Autowired
        public RowMapper(CategoryRepository categoryRepo) {
            this.categoryRepo = categoryRepo;
        }

        @Override
        public Transaction mapRow(ResultSet rs, int i) throws SQLException {
            return new Transaction(rs.getInt("tid"),
                    LocalDateTime.parse(rs.getString("date")
                            .replaceAll(" ", "T")),
                    rs.getFloat("amount"),
                    rs.getString("externalIBAN"),
                    Type.values()[rs.getInt("type")],
                    categoryRepo.findById(rs.getLong("category")).get());
        }
    }
}
