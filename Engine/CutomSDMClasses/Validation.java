package sdm.engine.CutomSDMClasses;

import javafx.util.Pair;
import sdm.engine.exceptions.LocationAlreadyInUseException;
import sdm.engine.exceptions.LocationNotInRangeException;
import sdm.engine.exceptions.PurchaseCategoryException;

public abstract class Validation {

    public static boolean validateLocation(Zone zone, Pair<Integer, Integer> location){
        if(!(location.getValue() > 0 && location.getValue() < 51 && location.getKey() > 0 && location.getKey() < 51))
            throw new LocationNotInRangeException();
        if(zone.getLocations()[location.getKey()][location.getValue()] != null)
            throw new LocationAlreadyInUseException(location);
        return true;
    }

    public static boolean validateAmount(Zone zone, Integer itemId, Double amount){
        if(zone.getItemById(itemId).getPurchaseCategory() == PurchaseCategory.QUANTITY)
            if(amount % 1 != 0)
                throw new PurchaseCategoryException();

        return true;
    }

    public static boolean validatePrice(Double price){
        if(price <= 0)
            throw new IllegalArgumentException("Price must be positive value\n");
        return true;
    }

    public static void isItemExistsInStore(Integer itemId, Store store){
        if(!store.getItemIdToPrice().containsKey(itemId)){
            throw new IllegalArgumentException("the item: " + itemId + " does not exist in the Store: " + store.getID());
        }
    }

    public static void validateDiscount(Zone zone, Integer storeId, Discount discount){
        /** validate 'ifYouBuy' item */
        Store store = zone.getIdToStores().get(storeId);
        isItemExistsInStore(discount.getItemId(), store);

        /** validate all items in offers */
        for (Offer offer : discount.getOffers()) {
            int itemId = offer.getItemId();
            isItemExistsInStore(itemId, store);
        }

        /** validate the name: */
        for(Store tempStore : zone.getIdToStores().values()){
            for(Discount tempDiscount :  tempStore.getAllDiscounts()){
                if(tempDiscount.getName() == discount.getName())
                    throw new IllegalArgumentException("Can't add the discount because the name: "
                            + discount.getName() + " is already exists\n");
            }
        }
    }

    public static void validateStoreId(Zone zone, Integer storeId){
        for (Store store : zone.getIdToStores().values())
            if(storeId.equals(store.getID()))
                throw new IllegalArgumentException("Can't add the store, because store id: " + storeId + " already exists\n");

    }

//    public static void validateItemId(SuperDuperMarket market, Integer itemId){
//        if(market.getIdToItems().containsKey(itemId))
//            throw new IllegalArgumentException("Can't add the item, because item id: " + itemId + " already exists\n");
//    }

    public static void validateUserName(SuperDuperMarket market, String name){
        for(User user : market.getIdToUsers().values()){
            if (user.getName().equals(name))
                throw new IllegalArgumentException("Can't add the user, because user name: " + name + " already exists\n");
        }
    }
}
