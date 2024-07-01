package dto;

public class OfferDto {

    Integer thenYouGetId;
    String thenYouGetName;
    Double quantity;
    Integer additionalPrice; //per one item

    public OfferDto(){

    }

    public OfferDto(Integer thenYouGetId, String thenYouGetName, Double quantity, Integer additionalPrice) {
        this.thenYouGetId = thenYouGetId;
        this.thenYouGetName = thenYouGetName;
        this.quantity = quantity;
        this.additionalPrice = additionalPrice;
    }

    public Integer getThenYouGetId() {
        return thenYouGetId;
    }

    public void setThenYouGetId(Integer thenYouGetId) {
        this.thenYouGetId = thenYouGetId;
    }

    public String getThenYouGetName() {
        return thenYouGetName;
    }

    public void setThenYouGetName(String thenYouGetName) {
        this.thenYouGetName = thenYouGetName;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Integer getAdditionalPrice() {
        return additionalPrice;
    }

    public void setAdditionalPrice(Integer additionalPrice) {
        this.additionalPrice = additionalPrice;
    }
}
