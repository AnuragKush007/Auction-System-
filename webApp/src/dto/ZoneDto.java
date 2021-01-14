package dto;

public class ZoneDto {

    boolean isSucceed;
    String zoneName;
    String zoneOwner;
    Integer availableItems;
    Integer availableStores;
    Integer totalOrders;
    Double averageOrdersPrice;
    String errorMessage;

    public ZoneDto() {
    }

    public ZoneDto(boolean isSucceed, String zoneName, String zoneOwner, Integer availableItems, Integer availableStores,
                   Integer totalOrders, Double averageOrdersPrice, String errorMessage) {
        this.isSucceed = isSucceed;
        this.zoneName = zoneName;
        this.zoneOwner = zoneOwner;
        this.availableItems = availableItems;
        this.availableStores = availableStores;
        this.totalOrders = totalOrders;
        this.averageOrdersPrice = averageOrdersPrice;
        this.errorMessage = errorMessage;
    }

    public boolean isSucceed() {
        return isSucceed;
    }

    public void setSucceed(boolean succeed) {
        isSucceed = succeed;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getZoneOwner() {
        return zoneOwner;
    }

    public void setZoneOwner(String zoneOwner) {
        this.zoneOwner = zoneOwner;
    }

    public Integer getAvailableItems() {
        return availableItems;
    }

    public void setAvailableItems(Integer availableItems) {
        this.availableItems = availableItems;
    }

    public Integer getAvailableStores() {
        return availableStores;
    }

    public void setAvailableStores(Integer availableStores) {
        this.availableStores = availableStores;
    }

    public Integer getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Integer totalOrders) {
        this.totalOrders = totalOrders;
    }

    public Double getAverageOrdersPrice() {
        return averageOrdersPrice;
    }

    public void setAverageOrdersPrice(Double averageOrdersPrice) {
        this.averageOrdersPrice = averageOrdersPrice;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
