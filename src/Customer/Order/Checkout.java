package Customer.Order;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Checkout {
    private String userId;
    private HashMap<String, Integer> cart;
    private int totalAmount;
    private final int ongkir = 15000;
    private LocalDate checkoutDateTime;

    public Checkout(String userId, HashMap<String, Integer> cart, int totalAmount, LocalDate checkoutDateTime) {
        this.userId = userId;
        this.cart = cart;
        this.totalAmount = totalAmount;
        this.checkoutDateTime = checkoutDateTime;
    }

    public String getUserId() {
        return userId;
    }

    public int getOngkir() {
        return ongkir;
    }

    public HashMap<String, Integer> getCart() {
        return cart;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public LocalDate getCheckoutDateTime() {
        return checkoutDateTime;
    }
}
