package sdm.engine.CutomSDMClasses;

import sdm.engine.DefaultSDMClasses.SDMOffer;

public class Offer {

    private int itemId;
    private double quantity;
    private int additionalPrice;

    public Offer(int itemId, double quantity, int additionalPrice) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.additionalPrice = additionalPrice;
    }

    public Offer(SDMOffer sdmOffer) {
        this(sdmOffer.getItemId(), sdmOffer.getQuantity(), sdmOffer.getForAdditional());
    }

    public int getItemId() {
        return itemId;
    }

    public double getQuantity() {
        return quantity;
    }

    public int getAdditionalPrice() {
        return additionalPrice;
    }
}
