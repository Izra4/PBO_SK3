package Admin.Promo;


import Customer.Account.Pelanggan;
import Customer.Order.Order;

public interface Applicable {
    public boolean isCustomerEligible(Pelanggan pelanggan);
    public boolean isMinimumPriceEligible(Order order);
    public boolean isShippingFeeEligible(Order order);
    public double totalDiskon(Order order,PotongHarga potongHarga);
    public double totalCashBack(Order order,CashBack cashBack);
    public double totalOngkir(Order order,PotongOngkir potongOngkir);
}
