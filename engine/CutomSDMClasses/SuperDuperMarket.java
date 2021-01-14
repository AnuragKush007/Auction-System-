package sdm.engine.CutomSDMClasses;

import javafx.util.Pair;
import sdm.engine.exceptions.*;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

import javax.xml.bind.JAXBException;

public class SuperDuperMarket {

    XmlLoader xmlLoader;
    private Map<String, Zone> nameToZones;
    private Map<Integer, User> idToUsers;


    public SuperDuperMarket() {
        xmlLoader = new XmlLoader();
        nameToZones = new HashMap<>();
        idToUsers = new HashMap<>();
    }

    public void loadFromXmlFile(InputStream fileContent, String ownerName) throws JAXBException, FileNotFoundException {
        xmlLoader.loadFile(this, fileContent, (StoreOwner)this.getUserByName(ownerName));
    }
    public Map<String, Zone> getNameToZones() {
        return nameToZones;
    }

    public void setNameToZones(Map<String, Zone> nameToZones) {
        this.nameToZones = nameToZones;
    }

    public Map<Integer, User> getIdToUsers() {
        return idToUsers;
    }

    public Integer[][] getStoresLocationsByZoneName(String zoneName) {
        return this.getZoneByName(zoneName).getLocations();
    }

    public boolean isUserExists(String userName){
        for(User user : this.idToUsers.values()){
            if(user.getName().equals(userName))
                return true;
        }
        return false;
    }

    public void addUser(String name, String role){
//        Validation.validateUserName(this, name);
        if(role.equals(Constants.ROLE_CUSTOMER)){
            Customer newCustomer = new Customer(name);
            this.idToUsers.put(newCustomer.getID(), newCustomer);
        }
        else{   /** store owner */
            StoreOwner newOwner = new StoreOwner(name);
            this.idToUsers.put(newOwner.getID(), newOwner);
        }
    }

    public void addZone(Zone newZone){
        Zone prevZone = nameToZones.putIfAbsent(newZone.getName(), newZone);
        if (prevZone != null)
        {
            throw new IllegalArgumentException("The zone " + ": " + newZone.getName() + " Already exists");
        }
        newZone.getOwner().addOwnedZone(newZone);
    }

    public void addItemToZone(Item newItem, Zone zone){
        zone.addItem(newItem);
    }

    /** Use for new store from XML file, not from user */
    public void addStoreToZone(Store newStore, Zone zone){
        zone.addStoreFromXml(newStore);
    }

    public User getUserByName(String userName){
        for(User user : this.idToUsers.values()){
            if(user.getName().equals(userName))
                return user;
        }
        return null;
    }

    public Zone getZoneByName(String zoneName){
        Zone zone = this.getNameToZones().get(zoneName);
        if(zone == null) {
            throw new IllegalArgumentException("Zone " + zoneName + " does not exists\n");
        }
        return zone;
    }

    public Item getItemByIdAndZone(int itemId, Zone zone){
        Item item = zone.getIdToItems().get(itemId);
        if(item == null) {
            item = new Item(itemId);
            throw new ObjectNotExistException(item, itemId);
        }
        return item;
   }

    public Store getStoreByIdAndZone(int storeId, Zone zone){
        Store store = zone.getIdToStores().get(storeId);
        if(store == null) {
            store = new Store(storeId);
            throw new ObjectNotExistException(store, storeId);
        }
        return store;
    }

   public void addOrder(Order newOrder){
        this.getZoneByName(newOrder.getZone().getName()).addOrder(newOrder);
   }

    public void addFeedback(Order order, String storeName, int score, String text){
        order.getZone().getStoreByName(storeName).getOwner().addFeedback(order.getZone().getName(), storeName, order.getCustomer().getName(), order.getDate(), score, text);
    }

   public double getTotalSellsForItem(Item item, Zone zone) {
       return zone.getIdToItems().get(item.getID()).getNumberOfSales();
   }

//    public boolean deleteItemFromStore(Integer storeId, Integer itemId) {
//        if(!this.getIdToStores().containsKey(storeId)){
//            Store store = new Store(storeId);
//            throw new ObjectNotExistException(store, storeId);
//        }
//        if(!this.getIdToItems().containsKey(itemId)){
//            Item item = new Item(itemId);
//            throw new ObjectNotExistException(item, itemId);
//        }
//        Store store = this.getIdToStores().get(storeId);
//        Item item = this.getIdToItems().get(itemId);
//        if(!item.getAvailableIn().contains(store)){
//            throw new IllegalArgumentException("The item: " + itemId + " is already not available in the store: " + storeId);
//        }
//        if(item.getNumOfStoresThatAvailableIn() == 1){
//            throw new IllegalArgumentException("The item: " + itemId + " is only available for purchase at the store: " + storeId
//                    + ", so it cannot be removed from it");
//        }
//        if(store.getItemIdToPrice().size() == 1){
//            throw new IllegalArgumentException("The item: " + itemId + " is the only item sold in the store:  " + storeId
//                    + " and therefore cannot be removed\n");
//        }
//        item.removeStoreFromAvailableIn(store);
//        return store.deleteItem(itemId);
//    }

//    public void addItemToStore(Integer storeId, Integer itemId, double price){
//        if(!this.getIdToStores().containsKey(storeId)){
//            Store store = new Store(storeId);
//            throw new ObjectNotExistException(store, storeId);
//        }
//        if(!this.getIdToItems().containsKey(itemId)){
//            Item item = new Item(itemId);
//            throw new ObjectNotExistException(item, itemId);
//        }
//        Store store = this.getIdToStores().get(storeId);
//        Item item = this.getIdToItems().get(itemId);
//        if(item.getAvailableIn().contains(store)){
//            throw new IllegalArgumentException("The item: " + itemId + " is already available in the store: " + storeId);
//        }
//        Validation.validatePrice(price);
//        store.addItem(item, price);
//        item.addStoreToAvailableIn(store);
//    }

//    public void updateItemPrice(Integer storeId, Integer itemId, double price){
//        if(!this.getIdToStores().containsKey(storeId)){
//            Store store = new Store(storeId);
//            throw new ObjectNotExistException(store, storeId);
//        }
//        if(!this.getIdToItems().containsKey(itemId)){
//            Item item = new Item(itemId);
//            throw new ObjectNotExistException(item, itemId);
//        }
//        Store store = this.getIdToStores().get(storeId);
//        Item item = this.getIdToItems().get(itemId);
//        if(!item.getAvailableIn().contains(store)){
//            throw new IllegalArgumentException("The item: " + itemId + " is not available in the store: " + storeId);
//        }
//        Validation.validatePrice(price);
//        double prevPrice = store.getPriceByItemId(itemId);
//        store.updateItemPrice(item, price);
//        item.updateAveragePrice(prevPrice, price, 0);
//    }

    /** Use for new store from user, not from XML file */
    public void addNewStore(String zoneName, StoreOwner owner, String name, Pair<Integer, Integer> location, Integer ppk, Map<Integer, Double> itemIdsToPrice){
        if(this.getZoneByName(zoneName) == null)
            throw new IllegalArgumentException("can't add the store, because zone name does not exist\n");

        this.getZoneByName(zoneName).addStore(owner, name, location, ppk, itemIdsToPrice);
    }

    public ArrayList<Item> getStoreItemsList(String zoneName, Integer storeId) {
        ArrayList<Item> itemsList = new ArrayList<>();
        for(Item item : this.getZoneByName(zoneName).getIdToItems().values()) {
            if(item.isAvailableIn(storeId)) {
                itemsList.add(item);
            }
        }
        return  itemsList;
    }

    public Store getStoreByNameAndZoneName(String storeName, String zoneName){
        Map<Integer, Store> idToStores = getZoneByName(zoneName).getIdToStores();
        for(Store store : idToStores.values()){
            if(store.getName().equals(storeName))
                return store;
        }
        throw new IllegalArgumentException("There is no store " + storeName + " in zone " + zoneName + "\n");
    }

    public Store getStoreByStoreId(Integer storeId){
        for(Zone zone : this.nameToZones.values()){
            for(Integer storeID : zone.getIdToStores().keySet()){
                if(storeID.equals(storeId))
                    return zone.getIdToStores().get(storeID);
            }
        }
        throw new IllegalArgumentException("There is no store " + storeId + " in the market\n");
    }

    public Collection<Order> getOrdersByCustomer(Customer customer){
        return customer.getOrders().values();
    }

    public Item getItemByItemId(Integer itemId){
        for(Zone zone : this.nameToZones.values()){
            for(Integer itemID : zone.getIdToItems().keySet()){
                if(itemID.equals(itemId))
                    return zone.getIdToItems().get(itemID);
            }
        }
        throw new IllegalArgumentException("There is no item " + itemId + " in the market\n");
    }

//    public void addNewItem(Integer id, String name, String purchaseCategory, Map<Integer, Double> storeIdsToPrice){
//        Validation.validateItemId(this, id);
//
//        for(Double price : storeIdsToPrice.values())
//            Validation.validatePrice(price);
//
//        Map<Store, Double> storesToPrice = new HashMap<>();
//
//        storeIdsToPrice.forEach((storeId, price1) -> {
//            storesToPrice.put(getStoreById(storeId), price1);
//        });
//
//        Item newItem = new Item(id, name, purchaseCategory);
//
//        for (Store store : storesToPrice.keySet()) {
//            store.addItem(newItem, storesToPrice.get(store));
//            newItem.addStoreToAvailableIn(store);
//        }
//        this.idToItems.put(id, newItem);
//    }
}
