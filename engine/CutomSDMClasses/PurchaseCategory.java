package sdm.engine.CutomSDMClasses;

public enum PurchaseCategory {

    QUANTITY("quantity"),
    WEIGHT("weight")
    ;

    private final String name;

    PurchaseCategory(String purchaseCategory) {
        this.name = purchaseCategory;
    }

    public String getPurchaseCategoryStr() {
        return name;
    }


}
