package Customer.Account;

import java.util.HashMap;
import java.util.Map;

public class CustomerManager {
    private int counter = 1;
    private Map<String, Pelanggan> pelangganMap;

    public CustomerManager(){
        pelangganMap = new HashMap<>();
    }

    public boolean cekId(String id){
        return pelangganMap.containsKey(id);
    }

    public String createMember(String input){
        //INPUT = CREATE MEMBER A001|Ani|2023/05/15|25000
        String[] splitInput = input.split(" ",3);
        String[] splitData = splitInput[2].split("\\|");
        String id = splitData[0];
        int saldo = Integer.parseInt(splitData[3]);
        if(pelangganMap.containsKey(id)){
            return "CREATE MEMBER FAILED: " + id + " IS EXISTS";
        }else {
            Member member = new Member(id,saldo,splitData[1],splitData[2]);
            pelangganMap.put(id,member);
            return "CREATE MEMBER SUCCESS: " + id + " " + splitData[2];
        }
    }

    public String createGuest(String input){
        //CREATE GUEST ID|SALDO
        String[] splitInput = input.split(" ");
        String[] splitData = splitInput[2].split("\\|");
        String id = splitData[0];
        int saldo = Integer.parseInt(splitData[1]);
        if(pelangganMap.containsKey(id)){
            return "CREATE GUEST FAILED: " + id + " IS EXISTS";
        }else {
            Guest guest = new Guest(id,saldo);
            pelangganMap.put(id,guest);
            return "CREATE GUEST SUCCESS: " + id;
        }
    }

    public String getNamaPelanggan(String id) {
        Pelanggan pelanggan = pelangganMap.get(id);
        if (pelanggan != null) {
            if (pelanggan instanceof Member member) {
                return member.getNama();
            } else if (pelanggan instanceof Guest) {
                return "Guest";
            }
        }
        return null;
    }

    public int cekSaldo(String id){
        Pelanggan pelanggan = pelangganMap.get(id);
        return pelanggan.getSaldo();
    }

    public String tambahSaldo(String input){
        //TOPUP ID SALDO
        //SPLIT
        String[] reqSplit = input.split(" ");
        String id = reqSplit[1];
        int saldo = Integer.parseInt(reqSplit[2]);

        if(!cekId(id)) return "TOPUP FAILED: NON EXISTENT CUSTOMER";
        int awal = cekSaldo(id);

        //PROSES
        Pelanggan pelanggan = pelangganMap.get(id);
        String nama = getNamaPelanggan(id);
        pelanggan.setSaldo(saldo+awal);
        return "TOPUP SUCCESS: "+nama+" "+awal+" => "+pelanggan.getSaldo();
    }

    public void getDate(String id){
        Pelanggan pelanggan = pelangganMap.get(id);
        if (pelanggan instanceof Guest){
            System.out.println("Kamu bukan member");
        }else if(pelanggan instanceof Member member){
            System.out.println(member.getJoinDate());
        }
    }
}
