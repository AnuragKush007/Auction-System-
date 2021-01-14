package dto;

import java.util.Set;

public class StoreDto {

    boolean isSucceed;
    Integer id;
    String name;
    String owner;
    String location;
    Set<StoreItemDto> items;
    Integer totalOrders;
    Double totalItemsIncome;
    Integer ppk;
    Double totalDeliveriesIncome;
    String errorMessage;

    public StoreDto() {
        this.isSucceed = true;
    }

    public StoreDto(Integer id, String name, String owner, String location, Set<StoreItemDto> items,
                    Integer totalOrders, Double totalItemsIncome, Integer ppk, Double totalDeliveriesIncome) {
        this.isSucceed = true;
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.location = location;
        this.items = items;
        this.totalOrders = totalOrders;
        this.totalItemsIncome = totalItemsIncome;
        this.ppk = ppk;
        this.totalDeliveriesIncome = totalDeliveriesIncome;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<StoreItemDto> getItems() {
        return items;
    }

    public void setItems(Set<StoreItemDto> items) {
        this.items = items;
    }

    public Integer getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Integer totalOrders) {
        this.totalOrders = totalOrders;
    }

    public Double getTotalItemsIncome() {
        return totalItemsIncome;
    }

    public void setTotalItemsIncome(Double totalItemsIncome) {
        this.totalItemsIncome = totalItemsIncome;
    }

    public Integer getPpk() {
        return ppk;
    }

    public void setPpk(Integer ppk) {
        this.ppk = ppk;
    }

    public Double getTotalDeliveriesIncome() {
        return totalDeliveriesIncome;
    }

    public void setTotalDeliveriesIncome(Double totalDeliveriesIncome) {
        this.totalDeliveriesIncome = totalDeliveriesIncome;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
