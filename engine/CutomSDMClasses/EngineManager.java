package sdm.engine.CutomSDMClasses;


import javafx.util.Pair;
import sdm.engine.exceptions.ObjectNotExistException;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class EngineManager {

    SuperDuperMarket superDuperMarket;
    private Map<Customer, Order> newOrders;
    private Map<Customer, Map<Item, Double>> ordersItemsToAmounts;
    private Map<Customer, Map<Store, ArrayList<Discount>>> newOrders_Discounts;

    public EngineManager() {
        this.superDuperMarket = new SuperDuperMarket();
        this.newOrders = new HashMap<>();
        this.ordersItemsToAmounts = new HashMap<>();
        this.newOrders_Discounts = new HashMap<>();
    }

    public SuperDuperMarket getSuperDuperMarket() {
        return superDuperMarket;
    }

    public Map<Customer, Order> getNewOrders() {
        return this.newOrders;
    }

    public void loadFromXmlFile(InputStream fileContent, String ownerName) throws JAXBException, FileNotFoundException {
        this.superDuperMarket.loadFromXmlFile(fileContent, ownerName);
    }

    public void newOrder_Create(Customer customer) {
        Order newOrder = new Order();

        if(!this.superDuperMarket.getIdToUsers().containsKey(customer.getID()))
            throw new IllegalArgumentException("The customer you selected does not exist on the system\n");
        newOrder.setCustomer(customer);

        this.newOrders.put(customer, newOrder);

        Map<Item, Double> itemsToAmounts = new HashMap<>();
        Map<Store, ArrayList<Discount>> discounts = new HashMap<>();

        this.ordersItemsToAmounts.put(customer, itemsToAmounts);
        this.newOrders_Discounts.put(customer, discounts);
    }

    public void newOrder_SetZone(String zoneName, Customer customer){
        if(!this.superDuperMarket.getNameToZones().containsKey(zoneName)){
            throw new IllegalArgumentException("Zone name doesn't exists");
        }
        this.newOrders.get(customer).setZone(this.superDuperMarket.getNameToZones().get(zoneName));
    }

    public void newOrder_SetLocation(Pair<Integer, Integer> location, Customer customer){
        Validation.validateLocation(this.newOrders.get(customer).getZone(), location);
        this.newOrders.get(customer).setLocation(location);
    }

    public void newOrder_SetStore(int storeId, Customer customer) {
        if(!this.newOrders.get(customer).getZone().getIdToStores().containsKey(storeId)){
            Store store = new Store(storeId);
            throw new ObjectNotExistException(store, storeId);
        }
        newOrders.get(customer).addStore(this.superDuperMarket.getStoreByIdAndZone(storeId, this.newOrders.get(customer).getZone()));
        newOrders.get(customer).setStoreId(storeId);
    }

    public void newOrder_SetDate(Date date, Customer customer) {
        newOrders.get(customer).setDate(date);
    }

    public void newOrder_SetType(String type, Customer customer){
        this.newOrders.get(customer).setType(type);
    }

    public void newOrder_ValidateItem(Integer itemId, Double amount, Customer customer) {
        Validation.validateAmount(this.newOrders.get(customer).getZone(), itemId, amount);
        if (this.newOrders.get(customer).getType() == Order.OrderType.STATIC)
            this.superDuperMarket.getStoreByIdAndZone(newOrders.get(customer).getStoreId(), this.newOrders.get(customer).getZone()).isItemExist(itemId);
    }

    /** Caller to this method must wrap his call with an appropriate try & catch block */
//   public void newOrder_AddItem(Integer itemId, Double amount) {
//        Validation.validateAmount(this.superDuperMarket, itemId, amount);
//        this.superDuperMarket.getIdToStores().get(this.newOrder.getStoreId()).isItemExist(itemId);
//        newOrder.addItem(itemId, amount, superDuperMarket.getIdToStores().get(newOrder.getStoreId()).getPriceByItemId(itemId));
//    }

    public double newOrder_GetDistanceFromStore(Customer customer){
        return this.newOrders.get(customer).getSubOrderByStoreId(this.newOrders.get(customer).getStoreId()).getDistanceFromCustomer();
    }

    public Order newOrder_getOrderByCustomer(Customer customer){
        return this.newOrders.get(customer);
    }

    public double newOrder_GetDeliveryPrice(Customer customer){
        return this.newOrders.get(customer).getSubOrderByStoreId(this.newOrders.get(customer).getStoreId()).getDeliveryPrice();
    }

    public Map<Store, Map<Item, Double>> getMinimalCart(Map<Integer, Double> itemsIdsToAmounts, Customer customer) {
        Map<Store, Map<Item, Double>> storesToItemsAndAmounts = new HashMap<>();

        itemsIdsToAmounts.forEach((itemId,itemAmount) -> {
            Store minStore = null;
            double minPrice = Double.MAX_VALUE;

            for (Store store : this.newOrders.get(customer).getZone().getIdToStores().values()) {
                if (store.isItemExist(itemId)) {
                    double itemPrice = store.getPriceByItemId(itemId);
                    if (itemPrice < minPrice) {
                        minStore = store;
                        minPrice = itemPrice;
                    }
                }
            }
            Item item = this.superDuperMarket.getItemByIdAndZone(itemId, this.newOrders.get(customer).getZone());

            if (storesToItemsAndAmounts.containsKey(minStore))
                storesToItemsAndAmounts.get(minStore).put(item, itemAmount);
            else {
                Map<Item, Double> itemsAndAmounts = new HashMap<>();
                itemsAndAmounts.put(item, itemAmount);
                storesToItemsAndAmounts.put(minStore, itemsAndAmounts);
            }
        });

        return storesToItemsAndAmounts;
    }

    public void newOrder_SaveItemsSelection(Map<Item, Double> itemsToAmount, Customer customer){
        Map<Integer, Double> itemIdsToAmount = new HashMap<>();
        for (Item item : itemsToAmount.keySet())
            itemIdsToAmount.put(item.getID(), itemsToAmount.get(item));

        if(this.newOrders.get(customer).getType() == Order.OrderType.DYNAMIC){
            Map<Store, Map<Item, Double>> storesToItemsAndAmounts = getMinimalCart(itemIdsToAmount, customer);
            this.newOrders.get(customer).addSubOrders(storesToItemsAndAmounts);
        }
        else{
            Store store = this.superDuperMarket.getStoreByIdAndZone(this.newOrders.get(customer).getStoreId(), this.newOrders.get(customer).getZone());
            this.newOrders.get(customer).addSubOrder(store, itemsToAmount);

        }
        this.ordersItemsToAmounts.put(customer, itemsToAmount);
    }

    public Map<Integer, SubOrder> newOrder_getSubOrders(Customer customer){
        return this.newOrders.get(customer).getSubOrders();
    }

    public Map<Store, ArrayList<Discount>> newOrder_GetDiscounts(Customer customer){
        this.newOrders_Discounts.put(customer, newOrders.get(customer).getDiscounts());
        return this.newOrders_Discounts.get(customer);
    }
    /** This method changes ordersItemsToAmounts.get(customer) */
    public Map<Store, ArrayList<Discount>> newOrder_UpdateDiscountsList(Store store, String discountName , Offer offer, Customer customer){ // if all or nothing pass offer = null
        Discount discount = store.getDiscountByName(discountName);
        if(offer == null) {
            for(Offer offer1 : discount.getOffers())
                this.newOrders.get(customer).addChosenOffer(store, discount.getItemId(), offer1);
        }
        else
            this.newOrders.get(customer).addChosenOffer(store, discount.getItemId(), offer);

        double newAmount = this.ordersItemsToAmounts.get(customer).get(this.superDuperMarket.getItemByIdAndZone(discount.getItemId(), this.newOrders.get(customer).getZone()));
        newAmount -= discount.getItemQuantity();
        this.ordersItemsToAmounts.get(customer).put(this.superDuperMarket.getItemByIdAndZone(discount.getItemId(), this.newOrders.get(customer).getZone()), newAmount);
        ArrayList<Discount> tempDiscounts = new ArrayList<>();
        this.newOrders_Discounts.get(customer).get(store).forEach((discount2) -> {
            tempDiscounts.add(discount2);
        });
        for (Discount discount1 : tempDiscounts){
            if(discount1.getItemId() == discount.getItemId()) {
                if (!discount1.isGreaterOrEqualToItemQuantity(newAmount)) {
                    this.newOrders_Discounts.get(customer).get(store).remove(discount1);
                }
            }
        }
        if(this.newOrders_Discounts.get(customer).get(store).isEmpty())
            this.newOrders_Discounts.get(customer).remove(store);

        return this.newOrders_Discounts.get(customer);
    }

    public void newOrder_Finish(Customer customer){
        this.superDuperMarket.addOrder(this.newOrders.get(customer));
    }

    public synchronized void newOrder_addFeedback(String storeName, int score, String text, Customer customer){
        this.superDuperMarket.addFeedback(this.newOrders.get(customer), storeName, score, text);
    }

    public Zone newOrder_getZone(Customer customer){
        return this.newOrders.get(customer).getZone();
    }

    public void newOrder_delete(Customer customer){
        this.newOrders.remove(customer);
    }

//    public boolean deleteItemFromStore(Integer storeId, Integer itemId) {
//        return this.superDuperMarket.deleteItemFromStore(storeId, itemId);
//    }

//    public void addItemToStore(Integer storeId, Integer itemId, double price) {
//        this.superDuperMarket.addItemToStore(storeId, itemId, price);
//    }

//    public void updateItemPrice(Integer storeId, Integer itemId, double price) {
//        this.superDuperMarket.updateItemPrice(storeId, itemId, price);
//    }

    public Map<String, Zone> getNameToZone(){
        return this.superDuperMarket.getNameToZones();
    }

    public ArrayList<Item> getStoreItemsList(String zoneName, Integer storeId) {
        return this.superDuperMarket.getStoreItemsList(zoneName, storeId);
    }

    public Double getStoreItemPrice(String zoneName, Integer storeId, Integer itemId) {
        return this.superDuperMarket.getZoneByName(zoneName).getIdToStores().get(storeId).getPriceByItemId(itemId);
    }

    public Collection<Order> getStoreOrderList(String zoneName, Integer storeId) {
        if (superDuperMarket.getZoneByName(zoneName).getIdToStores().get(storeId).getOrders().size() != 0) {
          return  this.superDuperMarket.getZoneByName(zoneName).getIdToStores().get(storeId).getOrders().values();
        }
        return null;
    }

    public Map<Integer, User> getIdToUsers(){
        return this.superDuperMarket.getIdToUsers();
    }

    public Map<Integer, Store> getIdToStoresByZoneName(String zoneName){
        return this.superDuperMarket.getZoneByName(zoneName).getIdToStores();
    }

    public Map<Integer, Item> getIdToItemsByZoneName(String zoneName){
        return this.superDuperMarket.getZoneByName(zoneName).getIdToItems();
    }

    public Map<Integer, Order> getIdToOrdersByZoneName(String zoneName){
        return this.superDuperMarket.getZoneByName(zoneName).getIdToOrders();
    }

    public Integer[][] getStoresLocationsByZoneName(String zoneName){
        return this.superDuperMarket.getStoresLocationsByZoneName(zoneName);
    }

    public Map<Customer, Map<Item, Double>> getOrdersItemsToAmounts() {
        return this.ordersItemsToAmounts;
    }

    public void addNewStore(String zoneName, StoreOwner owner, String name, Pair<Integer, Integer> location, Integer ppk, Map<Integer, Double> itemIdsToPrice){
        this.superDuperMarket.addNewStore(zoneName, owner, name, location, ppk, itemIdsToPrice);
    }

//    public void addNewItem(Integer id, String name, String purchaseCategory, Map<Integer, Double> storeIdsToPrice){
//        this.superDuperMarket.addNewItem(id, name, purchaseCategory, storeIdsToPrice);
//    }

//    public void addNewDiscount(Integer storeId, String name, Integer ifYouBuyItemId, Double ifYouBuyQuantity, String discountOperator, ArrayList<Offer> offers){
//        this.superDuperMarket.addNewDiscount(storeId, name, ifYouBuyItemId, ifYouBuyQuantity, discountOperator, offers);
//    }

    public boolean isUserExists(String userName){
        return this.superDuperMarket.isUserExists(userName);
    }

    public synchronized void addUser(String name, String role){
        this.superDuperMarket.addUser(name, role);
    }

    public User getUserByName(String name){
        for(User user : this.superDuperMarket.getIdToUsers().values()){
            if(user.getName().equals(name))
                return user;
        }
        return null;
    }

    public User getUserById(Integer id) {
        return this.superDuperMarket.getIdToUsers().get(id);
    }

    public Zone getZoneByName(String zoneName){
        return this.superDuperMarket.getZoneByName(zoneName);
    }

    public Store getStoreByStoreNameAndZoneName(String storeName, String zoneName){
        return this.superDuperMarket.getStoreByNameAndZoneName(storeName, zoneName);
    }

    public Account getAccountByUserName(String userName){
        return this.superDuperMarket.getUserByName(userName).getAccount();
    }

    public Store getStoreByStoreId(Integer storeId){
        return this.superDuperMarket.getStoreByStoreId(storeId);
    }

    public Set<Feedback> getFeedbacksByStoreOwnerNameAndZoneName(String ownerName, String zoneName){
        return ((StoreOwner)this.superDuperMarket.getUserByName(ownerName)).getFeedbacksByZoneName(zoneName);
    }

    public void loadAccount(Customer customer, Date date, double amount){
        customer.addTransaction(Transaction.TransactionType.LOAD.getTransactionTypeStr(), date, amount);
    }

    public Collection<Order> getOrdersByCustomer(Customer customer){
        return this.superDuperMarket.getOrdersByCustomer(customer);
    }

    public Item getItemByItemId(Integer itemId){
        return this.superDuperMarket.getItemByItemId(itemId);
    }
}
