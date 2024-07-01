package sdm.engine.CutomSDMClasses;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Customer extends User {

    private Map<Integer, Order> orders;  //key is orderId

    public Customer() {
        super("");
    }

    public Customer(String name) {
        super(name);
        this.orders = new HashMap<>();
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public Map<Integer, Order> getOrders() {
        return orders;
    }

    public void setOrders(Map<Integer, Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order newOrder){
        this.orders.put(newOrder.getID(), newOrder);
        this.addTransaction("debit", newOrder.getDate(), newOrder.getTotalOrderPrice());
    }

    public Double getAverageOrdersPrice() {
        return orders.values()
                .stream().collect(Collectors.averagingDouble
                        (order -> order.getTotalOrderPrice()));
    }

    public Double getAverageOrdersDeliveryPrice() {
        return orders.values()
                .stream().collect(Collectors.averagingDouble
                        (order -> order.getDeliveryPrice()));
    }
}
