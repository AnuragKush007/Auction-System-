package sdm.engine.CutomSDMClasses;

import javafx.util.Pair;
import sdm.engine.exceptions.*;
import sdm.engine.DefaultSDMClasses.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Store extends SDMMember {

    private StoreOwner owner;
    private Pair<Integer, Integer> location;
    private int PPK;    //Can't be fraction!
    private double totalDeliveriesIncome;
    private int totalSoldItems;
    private Map<Integer, Double> itemIdToPrice;
    private Map<Integer, Double> itemIdToNumberOfSales; //the real amount. in case of WEIGHT, it can be fraction
    private Map<Integer, Order> orders;
    private Map<Integer, ArrayList<Discount>> itemIdToDiscounts; //key is the "ifYouBuy" itemId

    @Override
    public String toString() {
        return this.getName();
    }
    public Store(int id) {
        super(id, "");
    }

    public Store(int id, String name, StoreOwner owner, Pair<Integer, Integer> location, int PPK, Map<Integer, Double> itemIdsToPrice) {
        super(id, name);
        this.owner = owner;
        this.location = location;
        this.PPK = PPK;
        this.totalDeliveriesIncome = 0;
        this.totalSoldItems = 0;
        this.itemIdToPrice = itemIdsToPrice;
        this.itemIdToNumberOfSales = new HashMap<>();
        this.orders = new HashMap<>();
        this.itemIdToDiscounts = new HashMap<>();

        for(Integer itemId : this.itemIdToPrice.keySet()){
            this.itemIdToNumberOfSales.put(itemId, 0.0);
        }
    }

    public Store(SDMStore store, StoreOwner owner){
        super(store.getId(), store.getName());
        this.owner = owner;
        this.location = new Pair<>(store.getLocation().getX(), store.getLocation().getY());
        this.PPK = store.getDeliveryPpk();
        this.totalDeliveriesIncome = 0;
        this.totalSoldItems = 0;
        this.itemIdToPrice = new HashMap<>();
        this.itemIdToNumberOfSales = new HashMap<>();
        this.orders = new HashMap<>();
        this.itemIdToDiscounts = new HashMap<>();
    }

    public StoreOwner getOwner() {
        return owner;
    }

    public void setOwner(StoreOwner owner) {
        this.owner = owner;
    }

    public Pair<Integer, Integer> getLocation() {
        return location;
    }

    public void setLocation(Pair<Integer, Integer> location) {
        this.location = location;
    }

    public int getPPK() {
        return PPK;
    }

    public void setPPK(int PPK) {
        this.PPK = PPK;
    }

    public double getTotalDeliveriesIncome() {
        return totalDeliveriesIncome;
    }

    public void setTotalDeliveriesIncome(double totalDeliveriesIncome) {
        this.totalDeliveriesIncome = totalDeliveriesIncome;
    }

    public Map<Integer, Double> getItemIdToPrice() {
        return itemIdToPrice;
    }

    public void setItemIdToPrice(Map<Integer, Double> itemIdToPrice) {
        this.itemIdToPrice = itemIdToPrice;
    }

    public Map<Integer, Double> getItemIdToNumberOfSales() {
        return itemIdToNumberOfSales;
    }

    public void setItemIdToNumberOfSales(Map<Integer, Double> itemIdToNumberOfSales) {
        this.itemIdToNumberOfSales = itemIdToNumberOfSales;
    }

    public Map<Integer, Order> getOrders() {
        return orders;
    }

    public void setOrders(Map<Integer, Order> orders) {
        this.orders = orders;
    }

    public Map<Integer, ArrayList<Discount>> getItemIdToDiscounts() { return this.itemIdToDiscounts; }

    /**
     * @return the price to which the specified itemId is mapped, or
     *          null if this map contains no mapping for the itemId
     */
    public Double getPriceByItemId (Integer itemId){
        return itemIdToPrice.get(itemId);
    }

    public void addItem(Item item, Double price) {
        Double prevPrice = this.itemIdToPrice.putIfAbsent(item.getID(), price);
        if (prevPrice != null)
            throw new ObjectAlreadyExistException(item, item.getID());
        this.itemIdToNumberOfSales.put(item.getID(), 0.0);
    }

    public void addOrder(Order newOrder){
        Order prevOrder = this.orders.putIfAbsent(newOrder.getID(), newOrder);
        if(prevOrder != null)
        {
            throw new ObjectAlreadyExistException(prevOrder, prevOrder.getID());
        }
        this.totalDeliveriesIncome += newOrder.getSubOrderByStoreId(this.getID()).getDeliveryPrice();
        this.totalSoldItems += newOrder.getSubOrderByStoreId(this.getID()).getTotalItems();
        for(Integer itemId : newOrder.getSubOrderByStoreId(this.getID()).getItemIdToAmount().keySet()){
            this.itemIdToNumberOfSales.put(itemId, this.itemIdToNumberOfSales.get(itemId)
                    + newOrder.getSubOrderByStoreId(this.getID()).getItemIdToAmount().get(itemId));
        }
    }



    public boolean isItemExist(int itemId){
        return this.itemIdToPrice.get(itemId) != null;
    }

    public int getTotalSoldItems() {
        return this.totalSoldItems;
    }

    public boolean deleteItem(Integer itemId){ //If there is a discount on the item, then return true, else false
        this.itemIdToPrice.remove(itemId);
        this.itemIdToNumberOfSales.remove(itemId);
        boolean deleted1 = this.itemIdToDiscounts.remove(itemId) == null;
        boolean deleted2 = false;
        for(ArrayList<Discount> discountsArr : this.itemIdToDiscounts.values()){
            for(Discount discount : discountsArr){
                if(discount.isItemExistsInOffers(itemId) == true) {
                    discountsArr.remove(discount);
                    if(deleted2 == false)
                        deleted2 = true;
                }
            }
        }
        if(deleted1 == true || deleted2 == true)
            return true;
        else
            return false;
    }

    public void updateItemPrice(Item item, double newPrice){
        this.itemIdToPrice.put(item.getID(), newPrice);
    }

    public void addDiscount(Discount newDiscount){
        if(this.itemIdToDiscounts.get(newDiscount.getItemId()) == null) {
            ArrayList<Discount> list = new ArrayList<>();
            this.itemIdToDiscounts.put(newDiscount.getItemId(), list);
        }
        this.itemIdToDiscounts.get(newDiscount.getItemId()).add(newDiscount);
    }

    /** if there is no discounts on the item, return null */
    public ArrayList<Discount> getDiscountsByItemId(Integer itemId, Double amount){
        ArrayList<Discount> realDiscounts = new ArrayList<>();
        this.itemIdToDiscounts.get(itemId).forEach((discount) -> {
            if(discount.isGreaterOrEqualToItemQuantity(amount))
                realDiscounts.add(discount);

        });

        return this.itemIdToDiscounts.get(itemId);
    }

    public Discount getDiscountByName(String discountName){
        for(ArrayList<Discount> discounts : this.itemIdToDiscounts.values()){
            for(Discount discount : discounts){
                if(discount.getName().equals(discountName))
                    return discount;
            }
        }
        return null;
    }
    public ArrayList<Discount> getAllDiscounts(){
        ArrayList<Discount> discounts = new ArrayList<>();
        for(Integer itemId : this.itemIdToDiscounts.keySet()) {
            for(Discount discount : itemIdToDiscounts.get(itemId)){
                discounts.add(discount);
            }
        }
        return discounts;
    }

    public Offer getOfferByDiscountNameAndThenYouGetId(String discountName, Integer thenYouGetId){
        Discount discount = getDiscountByName(discountName);
        return discount.getOfferByThenYouGetId(thenYouGetId);
    }

}


