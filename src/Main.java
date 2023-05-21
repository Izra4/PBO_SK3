import Admin.Menu.MenuManager;
import Customer.Account.CustomerManager;
import Customer.Order.CartManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String input = "";
        String result = "";
        CustomerManager cm = new CustomerManager();
        MenuManager menu = new MenuManager();
        CartManager cart = new CartManager(cm,menu);

        System.out.println("======== Selamat Datang di Cetak Filkom ========");
        while (!input.equals("1")) {
            System.out.println("9. EXIT");
            input = in.nextLine();
            if (input.equals("9")) {
                System.exit(0);
            }
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
                    // Handle apply promo logic
                    break;
                case "TOPUP":
                    result = cm.tambahSaldo(input);
                    System.out.println(result);
                    break;
                case "CHECK_OUT":
                    // Handle check-out logic
                    break;
                case "PRINT":
                    // Handle print logic
                    break;
                case "PRINT_HISTORY":
                    // Handle print history logic
                    break;
            }
            System.out.println("Testing");
            System.out.println("Testing 2");
        }
    }
}