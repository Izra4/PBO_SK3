package Customer.Account;

public class Pelanggan {
    private static int counter = 1;
    private String id;
    private int saldo;
    private boolean isMember;

    public Pelanggan(String id, boolean isMember, int saldo) {
        this.id = id;
        this.isMember = isMember;
        this.saldo = saldo;
        counter++;
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
}
