package sdm.engine.CutomSDMClasses;

import sdm.engine.DefaultSDMClasses.SDMDiscount;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Discount {

    private String name;
    private int itemId;
    private double itemQuantity;
    private DiscountOperator operator;
    private Map<Integer, Offer> offers; // key is a itemId

    public  Discount(String name, int itemId, double itemQuantity, String operator){
        this.name = name;
        this.itemId = itemId;
        this.itemQuantity = itemQuantity;
        this.operator = convertStringToCategory(operator.toLowerCase());
        this.offers = new HashMap<>();
    }

    public Discount(String name, int itemId, double itemQuantity, DiscountOperator operator) {
        this.name = name;
        this.itemId = itemId;
        this.itemQuantity = itemQuantity;
        this.operator = operator;
        this.offers = new HashMap<>();
    }

    public Discount(SDMDiscount newDiscount) {
        this(newDiscount.getName(), newDiscount.getIfYouBuy().getItemId(), newDiscount.getIfYouBuy().getQuantity(),
                convertStringToCategory(newDiscount.getThenYouGet().getOperator().toLowerCase()));
    }

    private static DiscountOperator convertStringToCategory(String operator) {
        if (operator.contains(DiscountOperator.ONE_OF.getDiscountOperatorStr())) {
            return DiscountOperator.ONE_OF;
        }
        else if (operator.contains(DiscountOperator.ALL_OR_NOTHING.getDiscountOperatorStr())) {
            return DiscountOperator.ALL_OR_NOTHING;
        }
        return DiscountOperator.IRRELEVANT;
    }

    public String getName() {
        return name;
    }

    public int getItemId() {
        return itemId;
    }

    public double getItemQuantity() {
        return itemQuantity;
    }

    public DiscountOperator getCategory() {
        return operator;
    }

    public Collection<Offer> getOffers() {
        return offers.values();
    }

    public DiscountOperator getOperator() {
        return operator;
    }

    public Offer getOfferByThenYouGetId(Integer thenYouGetId){
        return this.offers.get(thenYouGetId);
    }

    public void addOffer(Offer offer) {
        int itemId = offer.getItemId();
        if (!isItemExistsInOffers(itemId)) {
            offers.put(itemId, offer);
        }
        else {
            throw new IllegalArgumentException("There is already an offer with ID: " + itemId + ". Each offer should have an unique ID\n");
        }
    }

    public boolean isItemExistsInOffers(int itemId) {
        return offers.containsKey(itemId);
    }

    public boolean isGreaterOrEqualToItemQuantity(double itemQuantity) {
        return (itemQuantity >= this.itemQuantity);
    }

}
