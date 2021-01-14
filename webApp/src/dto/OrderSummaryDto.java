package dto;

import java.util.Set;

public class OrderSummaryDto {

    boolean isSucceed;
    Set<OrderStoreDto> stores;
    Double totalItemsPrice;
    Double totalDeliveriesPrice;
    Double totalOrderPrice;
    String errorMessage;

    public OrderSummaryDto() {
    }

    public boolean isSucceed() {
        return isSucceed;
    }

    public void setSucceed(boolean succeed) {
        isSucceed = succeed;
    }

    public Set<OrderStoreDto> getStores() {
        return stores;
    }

    public void setStores(Set<OrderStoreDto> stores) {
        this.stores = stores;
    }

    public Double getTotalItemsPrice() {
        return totalItemsPrice;
    }

    public void setTotalItemsPrice(Double totalItemsPrice) {
        this.totalItemsPrice = totalItemsPrice;
    }

    public Double getTotalDeliveriesPrice() {
        return totalDeliveriesPrice;
    }

    public void setTotalDeliveriesPrice(Double totalDeliveriesPrice) {
        this.totalDeliveriesPrice = totalDeliveriesPrice;
    }

    public Double getTotalOrderPrice() {
        return totalOrderPrice;
    }

    public void setTotalOrderPrice(Double totalOrderPrice) {
        this.totalOrderPrice = totalOrderPrice;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
