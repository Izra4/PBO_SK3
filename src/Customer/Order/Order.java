package Customer.Order;

import Admin.Promo.Promotion;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Order {
    private static int noPesanan = 0;
    private final int ongkir = 15000;
    private int biayaCetak;
    private boolean isCheckedOut;
    private double totalHarga;
    private Promotion promotion;

    public Order() {

    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public void setTotalHarga(double totalHarga) {
        this.totalHarga = totalHarga;
    }

    public double getTotalHarga() {
        return totalHarga;
    }

    public boolean isCheckedOut() {
        return isCheckedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        isCheckedOut = checkedOut;
    }

    public int getOngkir() {
        return ongkir;
    }

}

