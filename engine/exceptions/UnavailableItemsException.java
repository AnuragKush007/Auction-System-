package sdm.engine.exceptions;

import sdm.engine.CutomSDMClasses.Item;
import java.util.Set;

public class UnavailableItemsException extends RuntimeException {
    private String missingItems;

    public UnavailableItemsException(Set<Item> missingItems) {
        super();
        StringBuilder str = new StringBuilder();
        for (Item item : missingItems) {
            str.append("ID: ").append(item.getID()).append(", name: ").append(item.getName()).append("\n");
            this.missingItems = str.toString();
        }
    }

    @Override
    public String getMessage() {
        return "The following items are not sell in any store:\n" + this.missingItems + "Each item must be sold in at least one store\n";
    }
}