package Customer.Account;

import Admin.Promo.CashBack;
import Admin.Promo.PotongHarga;
import Admin.Promo.PotongOngkir;
import Admin.Promo.Promotion;

public abstract class Pelanggan {
    private static int counter = 1;
    private String id;
    private int saldo;
    private boolean isMember;
    private Promotion appliedPromotion;

    public Pelanggan(String id, boolean isMember, int saldo) {
        this.id = id;
        this.isMember = isMember;
        this.saldo = saldo;
        counter++;
    }

    public Promotion getAppliedPromotion() {
        return appliedPromotion;
    }

    public String namaPromotion(){
        if (getAppliedPromotion() instanceof CashBack){
            return "CASHBACK";
        }else if(getAppliedPromotion() instanceof PotongHarga){
            return "ANJAY";
        }else if(getAppliedPromotion() instanceof PotongOngkir){
            return "MEMEK";
        }else return "";
    }
    public void setAppliedPromotion(Promotion appliedPromotion) {
        this.appliedPromotion = appliedPromotion;
    }

    public String getId() {
        return id;
    }

    public boolean isMember() {
        return isMember;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public int getSaldo() {
        return saldo;
    }
    public boolean hasPromotion() {
        return appliedPromotion != null;
    }
}
