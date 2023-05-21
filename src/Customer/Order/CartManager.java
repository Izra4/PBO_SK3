package Customer.Order;

import Admin.Menu.MenuManager;
import Customer.Account.CustomerManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartManager {
    private HashMap<String, HashMap<String,Integer>> cartManageMap;
    private MenuManager menu;
    private CustomerManager customerManager;
    HashMap<String, Integer> customerCart;

    public CartManager(CustomerManager customerManager, MenuManager menuManager) {
        cartManageMap = new HashMap<>();
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
    public String getAllOrders(String idUser) {
        HashMap<String, Integer> customerCart = cartManageMap.get(idUser);
        if (customerCart == null || customerCart.isEmpty()) {
            return "No orders found for user " + idUser;
        }

        StringBuilder orders = new StringBuilder();
        orders.append("Orders for user ").append(idUser).append(":\n");

        for (Map.Entry<String, Integer> entry : customerCart.entrySet()) {
            String menuId = entry.getKey();
            int quantity = entry.getValue();
            String menuName = menu.getJudul(menuId);
            orders.append(menuName).append(": ").append(quantity).append(" items\n");
        }

        return orders.toString();
    }
}
