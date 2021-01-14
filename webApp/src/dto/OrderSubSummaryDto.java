package dto;

import java.util.Set;

public class OrderSubSummaryDto {

    boolean isSucceed;
    Set<OrderStoreDto> stores;
    String errorMessage;

    public OrderSubSummaryDto() {
    }

    public OrderSubSummaryDto(boolean isSucceed, Set<OrderStoreDto> stores, String errorMessage) {
        this.isSucceed = isSucceed;
        this.stores = stores;
        this.errorMessage = errorMessage;
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

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
