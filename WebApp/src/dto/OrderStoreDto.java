package dto;

import java.util.Set;

public class OrderStoreDto {

    boolean isSucceed;
    Integer id;
    String name;
    String location;
    Double distanceFromCustomer;
    Integer ppk;
    Double deliveryCost;
    Integer itemsTypeQuantity;
    Double itemsCost;
    Set<OrderItemDto> items;
    String errorMessage;

    public OrderStoreDto() {
        this.isSucceed = true;
    }

    public OrderStoreDto(boolean isSucceed, Integer id, String name, String location,
                         Double distanceFromCustomer, Integer ppk, Double deliveryCost,
                         Integer itemsTypeQuantity, Double itemsCost, String errorMessage) {
        this.isSucceed = isSucceed;
        this.id = id;
        this.name = name;
        this.location = location;
        this.distanceFromCustomer = distanceFromCustomer;
        this.ppk = ppk;
        this.deliveryCost = deliveryCost;
        this.itemsTypeQuantity = itemsTypeQuantity;
        this.itemsCost = itemsCost;
        this.errorMessage = errorMessage;
    }

    public boolean isSucceed() {
        return isSucceed;
    }

    public void setSucceed(boolean succeed) {
        isSucceed = succeed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getDistanceFromCustomer() {
        return distanceFromCustomer;
    }

    public void setDistanceFromCustomer(Double distanceFromCustomer) {
        this.distanceFromCustomer = distanceFromCustomer;
    }

    public Integer getPpk() {
        return ppk;
    }

    public void setPpk(Integer ppk) {
        this.ppk = ppk;
    }

    public Double getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(Double deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public Integer getItemsTypeQuantity() {
        return itemsTypeQuantity;
    }

    public void setItemsTypeQuantity(Integer itemsTypeQuantity) {
        this.itemsTypeQuantity = itemsTypeQuantity;
    }

    public Double getItemsCost() {
        return itemsCost;
    }

    public void setItemsCost(Double itemsCost) {
        this.itemsCost = itemsCost;
    }

    public Set<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(Set<OrderItemDto> items) {
        this.items = items;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
