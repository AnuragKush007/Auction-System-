package dto;

import java.util.Date;

public class FeedbackDto {

    String storeName;
    String customerName;
    Date date;
    Integer score;
    String text;

    public FeedbackDto() {
    }

    public FeedbackDto(String customerName, Date date, Integer score, String text) {
        this.customerName = customerName;
        this.date = date;
        this.score = score;
        this.text = text;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
