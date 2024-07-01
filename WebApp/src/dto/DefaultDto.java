package dto;

public class DefaultDto {

    boolean isSucceed;
    String errorMessage;

    public DefaultDto() {
    }

    public DefaultDto(boolean isSucceed,  String errorMessage) {
        this.isSucceed = isSucceed;
        this.errorMessage = errorMessage;
    }

    public boolean isSucceed() {
        return isSucceed;
    }

    public void setSucceed(boolean succeed) {
        isSucceed = succeed;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
