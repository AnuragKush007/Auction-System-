package dto;

public class StoreItemDto {

    Integer id;
    String name;
    String purchaseCategory;
    Double price;
    Double numberOfSells;

    public StoreItemDto() {
    }

    public StoreItemDto(Integer id, String name, String purchaseCategory, Double price, Double numberOfSells) {
        this.id = id;
        this.name = name;
        this.purchaseCategory = purchaseCategory;
        this.price = price;
        this.numberOfSells = numberOfSells;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getNumberOfSells() {
        return numberOfSells;
    }

    public void setNumberOfSells(Double numberOfSells) {
        this.numberOfSells = numberOfSells;
    }
}
