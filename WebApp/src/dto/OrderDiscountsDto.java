package dto;

import java.util.Set;

public class OrderDiscountsDto {

    boolean isSucceed;
    Set<DiscountDto> discounts;
    String errorMessage;

    public OrderDiscountsDto() {
    }

    public OrderDiscountsDto(boolean isSucceed, Set<DiscountDto> discounts, String errorMessage) {
        this.isSucceed = isSucceed;
        this.discounts = discounts;
        this.errorMessage = errorMessage;
    }

    public boolean isSucceed() {
        return isSucceed;
    }

    public void setSucceed(boolean succeed) {
        isSucceed = succeed;
    }

    public Set<DiscountDto> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(Set<DiscountDto> discounts) {
        this.discounts = discounts;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
