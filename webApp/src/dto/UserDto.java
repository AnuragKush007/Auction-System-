package dto;

public class UserDto {

    boolean isSucceed;
    String userName;
    String userRole;
    String errorMessage;

    public UserDto() {
    }

    public UserDto(boolean isSucceed, String userName, String userRole, String errorMessage) {
        this.isSucceed = isSucceed;
        this.userName = userName;
        this.userRole = userRole;
        this.errorMessage = errorMessage;
    }

    public boolean isSucceed() {
        return isSucceed;
    }

    public void setSucceed(boolean succeed) {
        isSucceed = succeed;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
