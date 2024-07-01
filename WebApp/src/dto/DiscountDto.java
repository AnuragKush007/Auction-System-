package dto;

import java.util.Set;

public class DiscountDto {

    String name;
    String storeName;
    Integer storeId;
    Integer ifYouBuyId; //item id
    String ifYouBuyName; //item name
    Double ifYouBuyQuantity;
    String operator;
    Set<OfferDto> offers;

    public DiscountDto(){

    }

    public DiscountDto(String name, String storeName, Integer storeId, Integer ifYouBuyId, String ifYouBuyName, Double ifYouBuyQuantity, String operator, Set<OfferDto> offers) {
        this.name = name;
        this.storeName = storeName;
        this.storeId = storeId;
        this.ifYouBuyId = ifYouBuyId;
        this.ifYouBuyName = ifYouBuyName;
        this.ifYouBuyQuantity = ifYouBuyQuantity;
        this.operator = operator;
        this.offers = offers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getIfYouBuyId() {
        return ifYouBuyId;
    }

    public void setIfYouBuyId(Integer ifYouBuyId) {
        this.ifYouBuyId = ifYouBuyId;
    }

    public String getIfYouBuyName() {
        return ifYouBuyName;
    }

    public void setIfYouBuyName(String ifYouBuyName) {
        this.ifYouBuyName = ifYouBuyName;
    }

    public Double getIfYouBuyQuantity() {
        return ifYouBuyQuantity;
    }

    public void setIfYouBuyQuantity(Double ifYouBuyQuantity) {
        this.ifYouBuyQuantity = ifYouBuyQuantity;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Set<OfferDto> getOffers() {
        return offers;
    }

    public void setOffers(Set<OfferDto> offers) {
        this.offers = offers;
    }
}
