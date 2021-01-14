package sdm.engine.exceptions;

public class PurchaseCategoryException extends RuntimeException{

    public PurchaseCategoryException() {
        super();
    }

    @Override
    public String getMessage() {
        return "The item is sold in entire units, not by weight";
    }
}
