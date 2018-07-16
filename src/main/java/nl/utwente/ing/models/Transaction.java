package nl.utwente.ing.models;

import java.util.Objects;

public class Transaction {
    private long id;
    private String date;
    private float amount;
    private String externalIBAN;
    private String type;
    private Category category;

    public Transaction(long id, String date, float amount, String externalIBAN, String type, Category category) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.externalIBAN = externalIBAN;
        this.type = type;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
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
                Float.compare(that.amount, amount) == 0 &&
                Objects.equals(date, that.date) &&
                Objects.equals(externalIBAN, that.externalIBAN) &&
                Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, amount, externalIBAN, category);
    }
}
