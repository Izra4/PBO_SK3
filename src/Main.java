import Admin.Menu.MenuManager;
import Admin.Promo.PromoManager;
import Customer.Account.CustomerManager;
import Customer.Order.CartManager;
import Customer.Order.CheckOutManager;

import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);

        ArrayList<String> perintah = new ArrayList<>();
        String input = "";
        String result = "";
        CustomerManager cm = new CustomerManager();
        MenuManager menu = new MenuManager();
        CheckOutManager com = new CheckOutManager(menu,cm);
        CartManager cart = new CartManager(cm, menu,com);
        PromoManager pm = new PromoManager(cm, cart);

        System.out.println("======== Selamat Datang di Cetak Filkom ========");
        System.out.println("9. EXIT");
        while (true) {
            input = in.nextLine();
            if (input.equals("9")) {
                break;
            }
            perintah.add(input);
        }

            for (int i = 0; i < perintah.size(); i++) {
                input = perintah.get(i);
                String[] reqSplit = input.split(" ");
                String command = reqSplit[0];

                switch (command) {
                    case "CREATE":
                        String entityType = reqSplit[1];
                        switch (entityType) {
                            case "MEMBER" -> {
                                result = cm.createMember(input);
                                System.out.println(result);
                            }
                            case "GUEST" -> {
                                result = cm.createGuest(input);
                                System.out.println(result);
                            }
                            case "MENU" -> {
                                String menuType = reqSplit[2];
                                if (menuType.equals("FOTOKOPI")) {
                                    result = menu.createMenuFotoKopi(input);
                                    System.out.println(result);
                                } else if (menuType.equals("CETAK")) {
                                    result = menu.createMenuCetakBuku(input);
                                    System.out.println(result);
                                }
                            }
                            case "PROMO" -> {
                                String jenisPromo = reqSplit[2];
                                switch (jenisPromo) {
                                    case "DELIVERY" -> {
                                        result = pm.createPromoDelivery(input);
                                        System.out.println(result);
                                    }
                                    case "DISCOUNT" -> {
                                        result = pm.createPromoDiscount(input);
                                        System.out.println(result);
                                    }
                                    case "CASHBACK" -> {
                                        result = pm.createPromoCashBack(input);
                                        System.out.println(result);
                                    }
                                }
                            }
                        }
                        break;
                    case "ADD_TO_CART":
                        result = cart.addCart(input);
                        System.out.println(result);
                        break;
                    case "REMOVE_FROM_CART":
                        result = cart.removeCart(input);
                        System.out.println(result);
                        break;
                    case "APPLY_PROMO":
                        result = pm.applyPromo(input);
                        System.out.println(result);
                        break;
                    case "TOPUP":
                        result = cm.tambahSaldo(input);
                        System.out.println(result);
                        break;
                    case "CHECK_OUT":
                        System.out.println(cart.checkout(input));
                        break;
                    case "PRINT":
                        // Handle print logic
                        cart.printOrders(input);
                        break;
                    case "PRINT_HISTORY":
                        // Handle print history logic
                        break;
                }
            }
//        System.out.println(pm.getAppliedPromo("001"));
            while (true) {
                input = in.nextLine();
                if (input.equals("9")) System.exit(0);

            }
    }
}