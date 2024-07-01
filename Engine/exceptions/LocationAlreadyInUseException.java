package sdm.engine.exceptions;

import javafx.util.Pair;

public class LocationAlreadyInUseException extends RuntimeException {
    private Pair<Integer, Integer> location;

    public LocationAlreadyInUseException(Pair<Integer, Integer> location) {
        super();
        this.location = location;
    }

    @Override
    public String getMessage() {
        return "There is a store located in [" + location.getKey().toString() + "," + location.getValue().toString() + "]";
    }
}
