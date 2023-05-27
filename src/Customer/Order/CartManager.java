package Customer.Order;

import Admin.Menu.MenuManager;
import Customer.Account.CustomerManager;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartManager {
    private HashMap<String, HashMap<String,Integer>> cartManageMap;
    private static int counter = 1;
    private MenuManager menu;
    private CustomerManager customerManager;
    private CheckOutManager checkOutManager;

    public CartManager(CustomerManager customerManager, MenuManager menuManager,CheckOutManager checkOutManager) {
        cartManageMap = new HashMap<>();
        this.checkOutManager = checkOutManager;
        this.customerManager = customerManager;
        this.menu = menuManager;
    }
    public String addCart(String input){
        //INPUT = ADD_TO_CART userID menuID Qty
        String[] splitReq = input.split(" ");
        String idUser = splitReq[1];
        String menuId = splitReq[2];
        int jumlah = Integer.parseInt(splitReq[3]);

        if (!customerManager.cekId(idUser) || !menu.cekId(menuId)){
            return "ADD_TO_CART FAILED: NON EXISTENT CUSTOMER OR MENU";
        }
        HashMap<String, Integer> customerCart = cartManageMap.get(idUser);
        if (customerCart == null) {
            customerCart = new HashMap<>();
            cartManageMap.put(idUser, customerCart);
        }

        if(!customerCart.containsKey(menuId)){
            customerCart.put(menuId,jumlah);
//            listPesanan.add(cartManageMap);
            return "ADD_TO_CART SUCCESS: " +jumlah + " " + menu.getJudul(menuId) + " IS ADDED";
        }else{
            int before = customerCart.get(menuId);
            int after = before + jumlah;
            customerCart.put(menuId,after);
            return "ADD_TO_CART SUCCESS: " + after + " " + menu.getJudul(menuId) + " QUANTITY IS INCREMENTED";
        }
    }

    public String removeCart(String input){
        //INPUT = REMOVE_FROM_CART userId menuId Qty
        String[] splitReq = input.split(" ");
        String userId = splitReq[1];
        String menuId = splitReq[2];
        int jumlah = Integer.parseInt(splitReq[3]);

        if (!customerManager.cekId(userId) || !menu.cekId(menuId)){
            return "ADD_TO_CART FAILED: NON EXISTENT CUSTOMER OR MENU";
        }
        HashMap<String, Integer> customerCart = cartManageMap.get(userId);
        int before = customerCart.get(menuId);
        int after = before - jumlah;
        customerCart.put(menuId,after);
        if (after >= 1){
            return "REMOVE_FROM_CART_SUCCESS: "+menu.getJudul(menuId)+" QUANTITY IS DECREMENTED";
        }else if (after <= 0){
            customerCart.remove(menuId);
            return "REMOVE_FROM_CART: "+menu.getJudul(menuId)+" IS REMOVED";
        }
        return "";
    }
    public String checkout(String input) {
        //CHECK_OUT A001
        String[] splitReq = input.split(" ");
        String idUser = splitReq[1];
        if(!customerManager.cekId(idUser)){
            return "CHECK OUT FAILED: NON EXISTENT CUSTOMER";
        }

        HashMap<String, Integer> customerCart = cartManageMap.get(idUser);
        if (customerCart == null || customerCart.isEmpty()) {
            return "CHECK OUT FAILED: NON EXISTENT ORDER";
        }
        int total = calculateTotalAmount(customerCart);
        if (customerManager.cekSaldo(idUser) < total){
            return "CHECKOUT FAILED: "+idUser+" "+customerManager.getNamaPelanggan(idUser)+" INSUFFICIENT BALANCE";
        }
        Checkout checkout = new Checkout(idUser,customerCart,total, LocalDate.now()); // Create Checkout object with customerCart data
        checkOutManager.addCheckout(counter, checkout); // Add the checkout to CheckOutManager

        cartManageMap.remove(idUser);
        counter++;

        return "CHECKOUT SUCCESS: " + idUser+" "+customerManager.getNamaPelanggan(idUser);
    }
    private int calculateTotalAmount(HashMap<String, Integer> cart) {
        int total = 0;

        for (String menuId : cart.keySet()) {
            int quantity = cart.get(menuId);
            double price = menu.getHarga(menuId);
            total += quantity * price;
        }

        return total;
    }

    public int calculateTotalCartAmount(String idUser) {
        HashMap<String, Integer> customerCart = cartManageMap.get(idUser);
        if (customerCart == null || customerCart.isEmpty()) {
            return 0;
        }

        int totalAmount = 0;

        for (String menuId : customerCart.keySet()) {
            int quantity = customerCart.get(menuId);
            double price = menu.getHarga(menuId);
            totalAmount += quantity * price;
        }

        return totalAmount;
    }

    public void printOrders(String input) {
        String[] splitReq = input.split(" ");
        String idUser = splitReq[1];
        if (cartManageMap.containsKey(idUser)) {
            System.out.println("Kode Pelanggan: " + idUser);
            System.out.println("Nama: " + customerManager.getNamaPelanggan(idUser));
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator(',');
            symbols.setGroupingSeparator('.');
            DecimalFormat formatter = new DecimalFormat("###,###.##", symbols);

            System.out.printf("%3s | %-20s | %3s | %8s \n", "No", "Menu", "Qty", "Subtotal");
            System.out.println("==================================================");
            int i=1;
            HashMap<String, Integer> customerCart = cartManageMap.get(idUser);
            for (String menuId : customerCart.keySet()) {
                int quantity = customerCart.get(menuId);
                String nama_menu = menu.getJudul(menuId);
                int harga_menu = menu.getHarga(menuId);
                String menuName = nama_menu.length() >= 20 ? nama_menu.substring(0, 20) : nama_menu;
                String subtotal = formatter.format(harga_menu*quantity);
                System.out.printf("%3d | %-20s | %3d | %8s \n", i, menuName, quantity, subtotal);
                i++;
            }
            System.out.println("==================================================");
            String subtotal = formatter.format(calculateTotalCartAmount(idUser)); //subtotal
            String delivery = formatter.format(15000); //biaya ongkir
            String total = formatter.format((calculateTotalCartAmount(idUser)+15000)); //total harga
            String balance = formatter.format(customerManager.cekSaldo(idUser)); //saldo
            System.out.printf("%-27s: %9s\n", "Total",subtotal);
            System.out.printf("%-27s: %9s\n", "Ongkos kirim", delivery);
            System.out.println("==================================================");
            System.out.printf("%-27s: %9s\n", "Total", total);
            System.out.printf("%-27s: %9s\n", "Saldo", balance);


        } else {
            checkOutManager.printCheckoutByUserId(idUser);
        }
    }


}
