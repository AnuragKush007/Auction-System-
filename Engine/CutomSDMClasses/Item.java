package sdm.engine.CutomSDMClasses;
import sdm.engine.DefaultSDMClasses.*;
import java.util.ArrayList;
import java.util.List;

public class Item extends SDMMember {

    private PurchaseCategory purchaseCategory;
    private double averagePrice;
    private double numberOfSales; //the real amount. in case of WEIGHT, it can be fraction
    private List<Store> availableIn;

    public Item(int id) {
        super(id, "");
    }

    public Item(int id, String name, String purchaseCategory) {
        super(id, name);
        this.purchaseCategory = PurchaseCategory.valueOf(purchaseCategory.toUpperCase());
        this.averagePrice = 0;
        this.numberOfSales = 0;
        this.availableIn = new ArrayList<>();
    }

    public Item(SDMItem item){
        super(item.getId(), item.getName());
        this.purchaseCategory = PurchaseCategory.valueOf(item.getPurchaseCategory().toUpperCase());
        this.averagePrice = 0;
        this.numberOfSales = 0;
        availableIn = new ArrayList<>();
    }

    public PurchaseCategory getPurchaseCategory() {
        return purchaseCategory;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public double getNumberOfSales() {
        return numberOfSales;
    }

    public List<Store> getAvailableIn() {
        return availableIn;
    }

    public void setPurchaseCategory(PurchaseCategory purchaseCategory) {
        this.purchaseCategory = purchaseCategory;
    }

    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public void setNumberAllSales(double numberOfSales) {
        this.numberOfSales = numberOfSales;
    }

    public int getNumOfStoresThatAvailableIn(){
        return availableIn.size();
    }

    public boolean isAvailableIn(int storeId){
        boolean isAvailable = false;
        for(Store store : availableIn) {
            isAvailable = (storeId == store.getID());
            if (isAvailable)
                break;
        }
        return isAvailable;
    }

    public void addStoreToAvailableIn(Store newStore){
        double price = newStore.getPriceByItemId(this.getID());
        this.updateAveragePrice(0, price, 1);
        this.availableIn.add(newStore);
    }

    public void removeStoreFromAvailableIn(Store store){
        double price = store.getPriceByItemId(this.getID());
        updateAveragePrice(0, price, 2);
        this.availableIn.remove(store);
    }

    public void updateAveragePrice(double prevPrice, double newPrice, int addOrRemoveOrNone){   //add = 1, remove = 2, None = 0
        int size = this.availableIn.size();
        if(addOrRemoveOrNone == 1)
            this.averagePrice = (((this.averagePrice * size) + newPrice) / (size+1));
        else if(addOrRemoveOrNone == 2)
            this.averagePrice = ((this.averagePrice * size) - newPrice) / (size-1);
        else
            this.averagePrice = ((this.averagePrice *size) - prevPrice + newPrice) / size;
    }

    public void addToNumberOfSales(double amount) {
        this.numberOfSales += amount;
    }

    @Override
    public String toString() { return this.getName();}
}




