package sdm.engine.CutomSDMClasses;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import javafx.util.Pair;
import sdm.engine.DefaultSDMClasses.SuperDuperMarketDescriptor;
import sdm.engine.exceptions.ObjectAlreadyExistException;
import sdm.engine.exceptions.ObjectNotExistException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Zone {

    private static int storesIDs = 20;

    private String name;
    private StoreOwner owner;
    private Map<Integer, Store> idToStores;
    private Map<Integer, Item> idToItems;
    private Map<Integer, Order> idToOrders;
    private Integer[][] Locations; //holds Store ID

    public Zone(String name, StoreOwner owner) {
        this.name = name;
        this.owner = owner;
        this.idToItems = new HashMap<>();
        this.idToStores = new HashMap<>();
        this.idToOrders = new HashMap<>();
        Locations = new Integer[51][51];
    }

    public Zone(SuperDuperMarketDescriptor.SDMZone zone, StoreOwner owner){
        this.name = zone.getName();
        this.idToItems = new HashMap<>();
        this.idToStores = new HashMap<>();
        this.idToOrders = new HashMap<>();
        this.owner = owner;
        Locations = new Integer[51][51];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StoreOwner getOwner() {
        return owner;
    }

    public void setOwner(StoreOwner owner) {
        this.owner = owner;
    }

    public Map<Integer, Store> getIdToStores() {
        return idToStores;
    }

    public void setIdToStores(Map<Integer, Store> idToStores) {
        this.idToStores = idToStores;
    }

    public Map<Integer, Item> getIdToItems() {
        return idToItems;
    }

    public void setIdToItems(Map<Integer, Item> idToItems) {
        this.idToItems = idToItems;
    }

    public Map<Integer, Order> getIdToOrders() {
        return idToOrders;
    }

    public void setIdToOrders(Map<Integer, Order> idToOrders) {
        this.idToOrders = idToOrders;
    }

    public Integer[][] getLocations() {
        return Locations;
    }

    public void setLocations(Integer[][] locations) {
        Locations = locations;
    }

    public void addItem(Item newItem){
        Item prevItem = this.idToItems.putIfAbsent(newItem.getID(), newItem);
        if (prevItem != null)
        {
            throw new ObjectAlreadyExistException(newItem, newItem.getID());
        }
    }

    public void addStoreFromXml(Store newStore){
        Validation.validateLocation(this, newStore.getLocation());

        Store prevStore = this.idToStores.putIfAbsent(newStore.getID(), newStore);
        if (prevStore != null)
        {
            throw new ObjectAlreadyExistException(newStore, newStore.getID());
        }
        this.Locations[newStore.getLocation().getKey()][newStore.getLocation().getValue()] = newStore.getID();
    }

    /** from user, not from xml file */
    public void addStore(StoreOwner owner, String name, Pair<Integer, Integer> location, Integer ppk, Map<Integer, Double> itemIdsToPrice){
        Validation.validateLocation(this, location);

        //check items price is valid:
        for(Double price : itemIdsToPrice.values())
            Validation.validatePrice(price);

        Store newStore = new Store(storesIDs, name, owner, location, ppk, itemIdsToPrice);
        storesIDs++;

        //add store to items AvailableIn:
        for(Integer itemId : itemIdsToPrice.keySet()){
            Item item = this.getItemById(itemId);
            item.addStoreToAvailableIn(newStore);
        }
        //add store to idToStores:
        Store prevStore = this.idToStores.putIfAbsent(newStore.getID(), newStore);
        if (prevStore != null)
        {
            throw new ObjectAlreadyExistException(newStore, newStore.getID());
        }
        this.Locations[newStore.getLocation().getKey()][newStore.getLocation().getValue()] = newStore.getID();
    }

    public void addDiscountToStore(Integer storeId, Discount discount){
        Validation.validateDiscount(this, storeId, discount); //TODO: fix validation
        this.idToStores.get(storeId).addDiscount(discount);
    }

    public Item getItemById(int itemId){
        Item item = idToItems.get(itemId);
        if(item == null) {
            item = new Item(itemId);
            throw new ObjectNotExistException(item, itemId);
        }
        return item;
    }

    public Set<Item> getItemsThatAreNotSoldInAnyStore() {
        Set<Item> dontSellItems = new HashSet<>();
        for (Item item : this.idToItems.values()) {
            if (item.getAvailableIn().isEmpty())
                dontSellItems.add(item);
        }
        return dontSellItems;
    }

    public void addOrder(Order newOrder) {
        //Set id for newOrder
        newOrder.setID();
        newOrder.save(); // update in stores
        this.owner.addTransaction("credit", newOrder.getDate(), newOrder.getTotalOrderPrice()); //update owner account

        //update details of the items in newOrder
        for(SubOrder subOrder : newOrder.getSubOrders().values()) {
            for (Integer itemId : subOrder.getItemIdToAmount().keySet()) {
                Item item = getItemById(itemId);
                item.addToNumberOfSales(subOrder.getItemAmount(itemId));
            }
        }
        //add newOrder to idToOrders
        this.idToOrders.put(newOrder.getID(), newOrder);
    }

    public Double getAverageOrdersPrice(){
        Double result = 0.0;
        for(Order order : this.idToOrders.values())
            result += order.getTotalOrderPrice();

        return result;
    }

    public Integer getNumOfItems(){
        return this.idToItems.size();
    }

    public Integer getNumOfStores(){
        return this.idToStores.size();
    }

    public Integer getNumOfOrders(){
        return this.idToOrders.size();
    }

    public Store getStoreByName(String storeName){
        for(Store store : this.idToStores.values()){
            if(store.getName().equals(storeName))
                return store;
        }
        throw new IllegalArgumentException("The store '" + storeName + "' does not exists in the market\n");
    }
}
