package Customer.Order;

import Admin.Menu.MenuManager;
import Admin.Promo.PotongHarga;
import Admin.Promo.Promotion;
import Customer.Account.CustomerManager;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class CheckOutManager {
    private HashMap<Integer, Checkout> checkoutMap;
    private MenuManager menu;
    private CustomerManager customerManager;

    public CheckOutManager(MenuManager menu, CustomerManager customerManager) {
        checkoutMap = new HashMap<>();
        this.menu = menu;
        this.customerManager = customerManager;
    }

    public void addCheckout(int checkoutId, Checkout checkout) {
        checkoutMap.put(checkoutId, checkout);
    }

    public Checkout getCheckout(int checkoutId) {
        return checkoutMap.get(checkoutId);
    }

    public HashMap<Integer, Checkout> getAllCheckouts() {
        return checkoutMap;
    }
    public void printCheckoutByUserId(String userId) {
        System.out.println("Kode Pelanggan: " + userId);
        System.out.println("Nama: " + customerManager.getNamaPelanggan(userId));

        for (Map.Entry<Integer, Checkout> entry : checkoutMap.entrySet()) {
            int checkoutId = entry.getKey();
            Checkout checkout = entry.getValue();
            System.out.println("Nomor Pesanan: " + checkoutId);
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd MM yyyy");
            System.out.println("Tanggal Pesanan: " + checkout.getCheckoutDateTime().format(timeFormatter));

            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator(',');
            symbols.setGroupingSeparator('.');
            DecimalFormat formatter = new DecimalFormat("###,###.##", symbols);

            System.out.printf("%3s | %-20s | %3s | %8s \n", "No", "Menu", "Qty", "Subtotal");
            System.out.println("==================================================");

            int i=1;
            if (checkout.getUserId().equals(userId)) {
//                System.out.println("Total Price: " + checkout.getTotalAmount());
//                System.out.println("Items:");
                HashMap<String, Integer> items = checkout.getCart();
                for (Map.Entry<String, Integer> itemEntry : items.entrySet()) {
                    String menuId = itemEntry.getKey();
                    int quantity = itemEntry.getValue();
                    int harga_menu = menu.getHarga(menuId);
                    String nama_menu = menu.getJudul(menuId);
                    String menuName = nama_menu.length() >= 20 ? nama_menu.substring(0, 20) : nama_menu;
                    String subtotal = formatter.format(harga_menu*quantity);
                    System.out.printf("%3d | %-20s | %3d | %8s \n", i, menuName, quantity, subtotal);
                    i++;
                }
                System.out.println("==================================================");
                String subtotal = formatter.format(checkout.getTotalAmount()); //subtotal
                String delivery = formatter.format(checkout.getOngkir()); //biaya ongkir
                String total = formatter.format((checkout.getOngkir()+checkout.getTotalAmount())); //total harga
                String balance = formatter.format(customerManager.cekSaldo(userId)); //saldo
                System.out.printf("%-27s: %9s\n", "Total",subtotal);
//                Promotion appliedPromo = customerManager.getAppliedPromo();
                if (customerManager.hasPromotion(userId)){
                    if (customerManager.getAppliedPromo() instanceof PotongHarga){
                        String discount = formatter.format(15000);
                        System.out.printf("%-27s: %9s\n", "PROMO: " + customerManager.getNamaPromosi(userId), discount);
                    }
                }
                System.out.printf("%-27s: %9s\n", "Ongkos kirim", delivery);

//                if (this.getPromo() != null){
//                    if (this.getPromo() instanceof DeliveryFeePromotion){
//                        String deliveryOff = formatter.format(-this.getPromo().getTotalShippingFeeOff(this));
//                        System.out.printf("%-27s: %9s\n", "PROMO: " + this.getPromo().getPromoCode(), deliveryOff);
//                    }
//                }
                System.out.println("==================================================");
                System.out.printf("%-27s: %9s\n", "Total", total);
//                if (this.getPromo() != null){
//                    if (this.getPromo() instanceof CashbackPromotion){
//                        String cashback = formatter.format(-this.getPromo().getTotalCashback(this));
//                        System.out.printf("%-27s: %9s\n", "PROMO: " + this.getPromo().getPromoCode(), cashback);
//                    }
//                }
                int saldoAwal = customerManager.cekSaldo(userId);
                int cost = (checkout.getOngkir()+checkout.getTotalAmount());
                customerManager.setSaldo(userId,(saldoAwal-cost));
                System.out.printf("%-27s: %9s\n", "Sisa saldo", customerManager.cekSaldo(userId)); //shopping cart Saldo bukan Sisa saldo
                System.out.println("");
            }
        }
    }
}