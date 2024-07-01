package sdm.engine.exceptions;

import javafx.util.Pair;

public class ObjectNotExistException extends RuntimeException {

    private Class clazz;
    private Object obj;
    private int id;

    public ObjectNotExistException(Object obj, int id) {
        super();
        this.obj = obj;
        this.clazz = this.obj.getClass();
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "The " + this.clazz.getSimpleName() + ": " + this.id + " does not exist";
    }
}
