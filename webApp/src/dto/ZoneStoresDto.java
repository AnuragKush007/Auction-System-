package dto;

import java.util.Set;

public class ZoneStoresDto {

    boolean isSucceed;
    Set<StoreDto> stores;
    String errorMessage;

    public ZoneStoresDto(){

    }

    public ZoneStoresDto(boolean isSucceed, Set<StoreDto> stores, String errorMessage) {
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

    public Set<StoreDto> getStores() {
        return stores;
    }

    public void setStores(Set<StoreDto> stores) {
        this.stores = stores;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
