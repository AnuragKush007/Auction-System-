package dto;

import java.util.Set;

public class OrdersHistoryDto {

    boolean isSucceed;
    Set<OrderDto> orders;
    String errorMessage;

    public OrdersHistoryDto() {
    }

    public OrdersHistoryDto(boolean isSucceed, Set<OrderDto> orders, String errorMessage) {
        this.isSucceed = isSucceed;
        this.orders = orders;
        this.errorMessage = errorMessage;
    }

    public boolean isSucceed() {
        return isSucceed;
    }

    public void setSucceed(boolean succeed) {
        isSucceed = succeed;
    }

    public Set<OrderDto> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderDto> orders) {
        this.orders = orders;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
