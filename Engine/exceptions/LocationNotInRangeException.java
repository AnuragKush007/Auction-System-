package sdm.engine.exceptions;

public class LocationNotInRangeException extends RuntimeException {

    public LocationNotInRangeException() {
        super();
    }

    @Override
    public String getMessage() {
        return "Location must be between [1,1] to [50,50]";
    }
}
