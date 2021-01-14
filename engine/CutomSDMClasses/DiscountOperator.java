package sdm.engine.CutomSDMClasses;

public enum DiscountOperator {

    IRRELEVANT("irrelevant"),
    ONE_OF("one-of"),
    ALL_OR_NOTHING("all-or-nothing")
    ;

    private final String name;

    DiscountOperator(String discountOperator) {
        this.name = discountOperator;
    }

    public String getDiscountOperatorStr() {
        return name;
    }
}
