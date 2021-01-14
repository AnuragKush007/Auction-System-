package sdm.engine.exceptions;

public class StoreDoNotExistException extends RuntimeException {
    public StoreDoNotExistException(){
        super();
    }
    @Override
    public String getMessage() {
        return "This ID doesn't match to any of our stores, please select another one or press 'q' to cancel: ";
    }
}
