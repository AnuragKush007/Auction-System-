package dto;

public class ItemDto {

    Integer id;
    String name;
    String purchaseCategory;
    Integer availableIn;
    Double totalSells;
    Double averagePrice;

    public ItemDto() {
    }

    public ItemDto(Integer id, String name, String purchaseCategory,
                   Integer availableIn, Double totalSells, Double averagePrice) {
        this.id = id;
        this.name = name;
        this.purchaseCategory = purchaseCategory;
        this.availableIn = availableIn;
        this.totalSells = totalSells;
        this.averagePrice = averagePrice;
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

    public Integer getAvailableIn() {
        return availableIn;
    }

    public void setAvailableIn(Integer availableIn) {
        this.availableIn = availableIn;
    }

    public Double getTotalSells() {
        return totalSells;
    }

    public void setTotalSells(Double totalSells) {
        this.totalSells = totalSells;
    }

    public Double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }
}
