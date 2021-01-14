package dto;

import java.util.Set;

public class ZoneItemsDto {

    boolean isSucceed;
    Set<ItemDto> items;
    String errorMessage;

    public ZoneItemsDto(){

    }

    public ZoneItemsDto(boolean isSucceed, Set<ItemDto> items, String errorMessage) {
        this.isSucceed = isSucceed;
        this.items = items;
        this.errorMessage = errorMessage;
    }

    public boolean isSucceed() {
        return isSucceed;
    }

    public void setSucceed(boolean succeed) {
        isSucceed = succeed;
    }

    public Set<ItemDto> getItems() {
        return items;
    }

    public void setItems(Set<ItemDto> items) {
        this.items = items;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
