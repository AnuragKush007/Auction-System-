package dto;

import java.util.Date;
import java.util.Set;

public class OrderDto {

    Integer id;
    Date date;
    String customerName;
    String location;
    Integer numOfStores;
    Integer numOfItems;
    Double totalItemsPrice;
    Double totalDeliveryPrice;
    Double totalOrderPrice;
    Set<OrderItemDto> items;

    public OrderDto() {
    }

    public OrderDto(Integer id, Date date, String customerName, String location, Integer numOfStores,
                    Integer numOfItems, Double totalItemsPrice, Double totalDeliveryPrice,
                    Double totalOrderPrice, Set<OrderItemDto> items) {
        this.id = id;
        this.date = date;
        this.customerName = customerName;
        this.location = location;
        this.numOfStores = numOfStores;
        this.numOfItems = numOfItems;
        this.totalItemsPrice = totalItemsPrice;
        this.totalDeliveryPrice = totalDeliveryPrice;
        this.totalOrderPrice = totalOrderPrice;
        this.items = items;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getNumOfStores() {
        return numOfStores;
    }

    public void setNumOfStores(Integer numOfStores) {
        this.numOfStores = numOfStores;
    }

    public Integer getNumOfItems() {
        return numOfItems;
    }

    public void setNumOfItems(Integer numOfItems) {
        this.numOfItems = numOfItems;
    }

    public Double getTotalItemsPrice() {
        return totalItemsPrice;
    }

    public void setTotalItemsPrice(Double totalItemsPrice) {
        this.totalItemsPrice = totalItemsPrice;
    }

    public Double getTotalDeliveryPrice() {
        return totalDeliveryPrice;
    }

    public void setTotalDeliveryPrice(Double totalDeliveryPrice) {
        this.totalDeliveryPrice = totalDeliveryPrice;
    }

    public Double getTotalOrderPrice() {
        return totalOrderPrice;
    }

    public void setTotalOrderPrice(Double totalOrderPrice) {
        this.totalOrderPrice = totalOrderPrice;
    }

    public Set<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(Set<OrderItemDto> items) {
        this.items = items;
    }
}
