package dto;

public class UploadFileDto {

    boolean isSucceed;
    String errorMessage;

    public UploadFileDto() {
    }

    public UploadFileDto(boolean isSucceed, String errorMessage) {
        this.errorMessage = errorMessage;
        this.isSucceed = isSucceed;
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
