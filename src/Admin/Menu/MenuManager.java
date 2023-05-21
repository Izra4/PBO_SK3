package Admin.Menu;

import java.util.HashMap;

public class MenuManager {
    private int counter = 1;
    private HashMap<String, Menu> menuMap;

    public MenuManager(){
        menuMap = new HashMap<>();
    }

    public boolean cekId(String id){
        return menuMap.containsKey(id);
    }
    public String createMenuCetakBuku(String input){
        //CREATE MENU JENIS ID|TITLE|HARGA|CUSTOMTYPE
        String[] splitInput = input.split(" ");
        String[] splitReq = splitInput[3].split("\\|");
        String id = splitReq[0];
        String customType = "";
        if (menuMap.containsKey(id)){
            return "CREATE MENU FAILED: "+id+" IS EXISTS";
        }else {
            String nama = splitReq[1];
            int harga = Integer.parseInt(splitReq[2]);
            if (splitReq.length >= 4){
                customType = splitReq[3];
            }
            CetakBuku cetakBuku = new CetakBuku(id,nama,harga,customType);
            menuMap.put(id,cetakBuku);
            return "CREATE MENU SUCCESS: "+id+" "+nama;
        }
    }

    public String createMenuFotoKopi(String input){
        //CREATE MENU JENIS ID|TITLE|HARGA
        String[] splitInput = input.split(" ",4);
        String[] splitReq = splitInput[3].split("\\|");
        String id = splitReq[0];
        String nama = splitReq[1];
        int harga = Integer.parseInt(splitReq[2]);
        if (menuMap.containsKey(id)){
            return "CREATE MENU FAILED: "+id+" IS EXISTS";
        }else {
            FotoKopi fotoKopi = new FotoKopi(id,nama,harga);
            menuMap.put(id,fotoKopi);
            return "CREATE MENU SUCCESS: "+id+" "+nama;
        }
    }

    public String getJudul(String id){
        Menu menu = menuMap.get(id);
        if (menu != null){
            return menu.getNama();
        }
        return null;
    }

    public int getHarga(String id){
        Menu menu = menuMap.get(id);
        if (menu != null){
            return menu.getHarga();
        }
        return 0;
    }

    public String getCustomType(String id){
        Menu menu = menuMap.get(id);
        if (menu instanceof CetakBuku) {
            return ((CetakBuku) menu).getCustomType();
        }
        return "";
    }
}
