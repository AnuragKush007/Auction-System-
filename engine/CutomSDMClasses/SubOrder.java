package sdm.engine.CutomSDMClasses;

import java.util.*;

public class SubOrder {

    private Date date;
    private Store store;
    private Map<Integer, Double> itemIdToAmount; //the real amount. in case of WEIGHT, it can be fraction
    private Map<Integer, Double> itemIdToPrice; //price per unit * amount
    private Map<Integer, ArrayList<Offer>> chosenOffers; //key is the "ifYouBuy" itemId
    private int totalItems;
    private double itemsTotalPrice; //Items total price only. Not include delivery price.
    private double deliveryPrice;
    private double distanceFromCustomer;

    public SubOrder(Store store, double distanceFromCustomer){
        this.store = store;
        this.distanceFromCustomer = distanceFromCustomer;
        this.itemIdToAmount = new HashMap<>();
        this.itemIdToPrice = new HashMap<>();
        this.chosenOffers = new HashMap<>();
        setDeliveryPrice();
    }

    public SubOrder(Date date, Store store, Map<Integer, Double> itemIdToAmount, Map<Integer, Double> itemIdToPrice, double distanceFromCustomer) {
        this.date = date;
        this.store = store;
        this.itemIdToAmount = itemIdToAmount;
        this.itemIdToPrice = itemIdToPrice;
        this.distanceFromCustomer = distanceFromCustomer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Map<Integer, Double> getItemIdToAmount() {
        return itemIdToAmount;
    }

    public void setItemIdToAmount(Map<Integer, Double> itemIdToAmount) {
        this.itemIdToAmount = itemIdToAmount;
    }

    public Map<Integer, Double> getItemIdToPrice() {
        return itemIdToPrice;
    }

    public void setItemIdToPrice(Map<Integer, Double> itemIdToPrice) {
        this.itemIdToPrice = itemIdToPrice;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public double getItemsTotalPrice() {
        return itemsTotalPrice;
    }

    public void setItemsTotalPrice(double itemsTotalPrice) {
        this.itemsTotalPrice = itemsTotalPrice;
    }

    public double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice() {
        this.deliveryPrice = this.distanceFromCustomer * this.store.getPPK();
    }

    public double getTotalPrice() {
        return this.deliveryPrice + this.itemsTotalPrice;
    }

    public double getDistanceFromCustomer() {
        return distanceFromCustomer;
    }

    public void setDistanceFromCustomer(double distanceFromCustomer) {
        this.distanceFromCustomer = distanceFromCustomer;
    }

    public double getItemAmount(Integer itemId){
        return this.itemIdToAmount.get(itemId);
    }

    public double getItemPricePerUnit(Integer itemId) {
        return this.itemIdToPrice.get(itemId);
    }

    public double getItemTotalPrice(Integer itemId) {
        return this.itemIdToPrice.get(itemId) * this.itemIdToAmount.get(itemId);
    }

    public  Map<Integer, ArrayList<Offer>> getChosenOffers(){
        return this.chosenOffers;
    }

    public void addItem(Integer itemId, Double amount){
        Double previousAmount = this.itemIdToAmount.putIfAbsent(itemId, amount);
        if (previousAmount != null)
            itemIdToAmount.put(itemId, previousAmount + amount);
        else
            itemIdToPrice.put(itemId, this.store.getPriceByItemId(itemId));
        this.itemsTotalPrice = this.itemsTotalPrice + itemIdToPrice.get(itemId) * amount;
        if(amount % 1 != 0)
            this.totalItems++;
        else
            this.totalItems += amount;
    }

    public Double getTotalItemCostInOrder(int itemId){
        return itemIdToAmount.get(itemId) * itemIdToPrice.get(itemId);
    }

    public ArrayList<Discount> getDiscounts(){
        ArrayList<Discount> discounts = new ArrayList<>();
        for(Integer itemId : this.itemIdToAmount.keySet())
            try {
                discounts.addAll(this.store.getDiscountsByItemId(itemId, getItemAmount(itemId)));
            } catch (NullPointerException e){}

        return discounts;
    }

    public void addChosenOffer(Integer itemId, Offer offer){
        if(this.chosenOffers.get(itemId) == null){
            ArrayList<Offer> offers = new ArrayList<>();
            this.chosenOffers.put(itemId, offers);
        }
        this.chosenOffers.get(itemId).add(offer);
        this.itemsTotalPrice += offer.getAdditionalPrice() * offer.getQuantity();
        this.totalItems += offer.getQuantity();
    }

    public Integer getItemsTypeQuantity(){
        return this.itemIdToAmount.size();
    }
}
