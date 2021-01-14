package sdm.engine.CutomSDMClasses;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Order{

    public enum OrderType {
        STATIC("static"),
        DYNAMIC("dynamic"),
        ;

        private String orderTypeStr;

        OrderType(String orderType) {
            this.orderTypeStr = orderType;
        }

        public String getOrderTypeStr() {
            return orderTypeStr;
        }
    }

    private static int IDs = 1;

    private int ID;
    private Date date;
    private Zone zone;
    private Integer storeId; //Only for STATIC order
    private Customer customer;
    private Pair<Integer, Integer> location;
    private double deliveryPrice;
    private double itemsTotalPrice; //Items total price only. Not include delivery price.
    private int totalItems; // Total amount of items in order. Item that sell on the WEIGHT will be considered as 1.
    private Map<Integer, SubOrder> subOrders; // The key is storeId
    private OrderType type;

    public Order() {
        this.subOrders = new HashMap<>();
    }

    public Order(Date date, Zone zone, Customer customer, Pair<Integer, Integer> location, double deliveryPrice, double itemsTotalPrice, int totalItems, Map<Integer, SubOrder> subOrders, String type) {
        this.ID = IDs;
        IDs++;
        this.date = date;
        this.zone = zone;
        this.customer = customer;
        this.location = location;
        this.deliveryPrice = deliveryPrice;
        this.itemsTotalPrice = itemsTotalPrice;
        this.totalItems = totalItems;
        this.subOrders = subOrders;
        this.type = convertStringToOrderCategory(type);

    }

    private static Order.OrderType convertStringToOrderCategory(String orderCategory) {
        if (orderCategory.toLowerCase().contains("static")) {
            return Order.OrderType.STATIC;
        }
        return Order.OrderType.DYNAMIC;
    }

    public int getID() {
        return ID;
    }

    public void setID() {
        this.ID = IDs;
        IDs++;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Pair<Integer, Integer> getLocation() {
        return location;
    }

    public void setLocation(Pair<Integer, Integer> location) {
        this.location = location;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }



    public Map<Integer, SubOrder> getSubOrders() {
        return subOrders;
    }

    public void setSubOrders(Map<Integer, SubOrder> subOrders) {
        this.subOrders = subOrders;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(String type) {
        this.type = convertStringToOrderCategory(type);
    }

    public double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public double getItemsTotalPrice() {
        return itemsTotalPrice;
    }

    public void setItemsTotalPrice(double itemsTotalPrice) {
        this.itemsTotalPrice = itemsTotalPrice;
    }

    public int getTotalItems() { return totalItems; }

    public void addStore(Store store){
        double distanceBetweenCustomerAndStore = Utils.calculateDistance(this.location, store.getLocation());
        SubOrder newSubOrder = new SubOrder(store, distanceBetweenCustomerAndStore);
        this.subOrders.put(store.getID(), newSubOrder);
        this.deliveryPrice += newSubOrder.getDeliveryPrice();
    }

    public double getTotalOrderPrice(){
        return this.deliveryPrice + this.itemsTotalPrice;
    }

    public SubOrder getSubOrderByStoreId(Integer storeId){
        if(!this.subOrders.containsKey(storeId))
            throw new IllegalArgumentException("The store does not exist on the order\n");
        return this.subOrders.get(storeId);
    }

    public void addSubOrder(Store store, Map<Item, Double> itemsAndAmounts) {
        if (this.getType() == OrderType.DYNAMIC) {
            addStore(store);
        }
        SubOrder subOrder = this.subOrders.get(store.getID());
        itemsAndAmounts.forEach((item, amount) -> {
            subOrder.addItem(item.getID(), amount);
        });

        this.totalItems += subOrder.getTotalItems();
        this.itemsTotalPrice += subOrder.getItemsTotalPrice();
    }

    public void addSubOrders(Map<Store, Map<Item, Double>> storesToItemsAndAmounts) {
        storesToItemsAndAmounts.forEach(this::addSubOrder);
    }

    public Integer getItemsTypeQuantityByStoreId(Integer storeId){
        return this.getSubOrderByStoreId(storeId).getItemsTypeQuantity();
    }

    public Map<Store, ArrayList<Discount>> getDiscounts() {
        Map<Store, ArrayList<Discount>> storesToDiscounts = new HashMap<>();
        for(SubOrder subOrder : this.subOrders.values()){
            ArrayList<Discount> subOrderDiscounts = subOrder.getDiscounts();
            if (subOrderDiscounts != null)
                storesToDiscounts.put(subOrder.getStore(), subOrderDiscounts);
        }
        return storesToDiscounts;
    }

    public void addChosenOffer(Store store, Integer itemId, Offer offer){
        this.subOrders.get(store.getID()).addChosenOffer(itemId, offer);
        this.itemsTotalPrice += (offer.getAdditionalPrice() * offer.getQuantity());
        this.totalItems += offer.getQuantity();
    }

    public void save(){
        this.customer.addOrder(this); //add order and update customer account
        for(SubOrder subOrder : this.subOrders.values()){
            subOrder.getStore().addOrder(this);
        }
    }
}
