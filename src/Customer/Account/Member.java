package Customer.Account;

public class Member extends Pelanggan {
    private String joinDate;
    private String nama;


    public Member(String id, int saldo, String nama, String joinDate) {
        super(id, true, saldo);
        this.nama = nama;
        this.joinDate = joinDate;
    }

    public String getNama() {
        return nama;
    }

    public String getJoinDate() {
        return joinDate;
    }
}
