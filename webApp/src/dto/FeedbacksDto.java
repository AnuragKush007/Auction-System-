package dto;

import java.util.Set;

public class FeedbacksDto {

    boolean isSucceed;
    Set<FeedbackDto> feedbacks;
    String errorMessage;

    public FeedbacksDto(){

    }

    public FeedbacksDto(boolean isSucceed, Set<FeedbackDto> feedbacks, String errorMessage) {
        this.isSucceed = isSucceed;
        this.feedbacks = feedbacks;
        this.errorMessage = errorMessage;
    }

    public boolean isSucceed() {
        return isSucceed;
    }

    public void setSucceed(boolean succeed) {
        isSucceed = succeed;
    }

    public Set<FeedbackDto> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(Set<FeedbackDto> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}