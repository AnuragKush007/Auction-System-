package sdm.engine.CutomSDMClasses;

import java.util.Date;

public class Feedback {

    private String customerName;
    private String storeName;
    private Date date;
    private int score;
    private String text;

    public Feedback(String customerName, String storeName, Date date, int score, String text) {
        this.customerName = customerName;
        this.storeName = storeName;
        this.date = date;
        this.score = score;
        this.text = text;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
