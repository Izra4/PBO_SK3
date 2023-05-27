package Customer.Account;

import Admin.Promo.Promotion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class CustomerManager {
    private int counter = 1;
    private Map<String, Pelanggan> pelangganMap;
    private Promotion appliedPromo;

    public CustomerManager(){
        pelangganMap = new HashMap<>();
    }

    public Map<String, Pelanggan> getPelangganMap() {
        return pelangganMap;
    }

    public boolean cekId(String id){
        return pelangganMap.containsKey(id);
    }

    public String createMember(String input){
        //INPUT = CREATE MEMBER A001|Ani|2023/05/15|25000
        String[] splitInput = input.split(" ",3);
        String[] splitData = splitInput[2].split("\\|");
        String id = splitData[0];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate joinDate = LocalDate.parse(splitData[2],formatter);
        int saldo = Integer.parseInt(splitData[3]);
        if(pelangganMap.containsKey(id)){
            return "CREATE MEMBER FAILED: " + id + " IS EXISTS";
        }else {
            Member member = new Member(id,saldo,splitData[1],joinDate);
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

    public boolean hasPromotion(String id) {
        Pelanggan pelanggan = pelangganMap.get(id);
        if (pelanggan != null) {
            return pelanggan.hasPromotion();
        }
        return false;
    }

    public void setSaldo(String id, int saldo) {
        Pelanggan pelanggan = pelangganMap.get(id);
        if (pelanggan != null) {
            pelanggan.setSaldo(saldo);
        }
    }
    public Promotion getAppliedPromo() {
        return appliedPromo;
    }

    public void setAppliedPromo(Promotion appliedPromo) {
        this.appliedPromo = appliedPromo;
    }

    public int cekSaldo(String id){
        Pelanggan pelanggan = pelangganMap.get(id);
        return pelanggan.getSaldo();
    }
    public String getNamaPromosi(String id){
        Pelanggan pelanggan = pelangganMap.get(id);
        return pelanggan.namaPromotion();

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

    public String getDate(String id){
        Pelanggan pelanggan = pelangganMap.get(id);
        if(pelanggan instanceof Member member){
            LocalDate joinDate = member.getJoinDate();
            return joinDate.toString();
        }else return "Kamu bukan member";
    }

    public void getMemberDuration(String id){
        Pelanggan pelanggan = pelangganMap.get(id);
            if (pelanggan instanceof Guest){
                System.out.println("0 hari");
            }else if(pelanggan instanceof Member member){
                System.out.println(member.getDuration(getDate(id)));
            }
        }
}
