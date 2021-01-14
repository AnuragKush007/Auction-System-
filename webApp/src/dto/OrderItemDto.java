package dto;

public class OrderItemDto {

    Integer id;
    String name;
    String purchaseCategory;
    String storeName;
    Integer storeId;
    Double quantity;
    Double pricePerUnit;
    Double totalPrice;
    Boolean isChosenOffer;

    public OrderItemDto() {
    }

    public OrderItemDto(Integer id, String name, String purchaseCategory, String storeName, Integer storeId,
                        Double quantity, Double pricePerUnit, Double totalPrice, Boolean isChosenOffer) {
        this.id = id;
        this.name = name;
        this.purchaseCategory = purchaseCategory;
        this.storeName = storeName;
        this.storeId = storeId;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.totalPrice = totalPrice;
        this.isChosenOffer = isChosenOffer;
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

    public String getPurchaseCategory() {
        return purchaseCategory;
    }

    public void setPurchaseCategory(String purchaseCategory) {
        this.purchaseCategory = purchaseCategory;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(Double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Boolean getChosenOffer() {
        return isChosenOffer;
    }

    public void setChosenOffer(Boolean chosenOffer) {
        isChosenOffer = chosenOffer;
    }
}
