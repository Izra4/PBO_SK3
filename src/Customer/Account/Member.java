package Customer.Account;

import java.time.LocalDate;
import java.time.Period;

public class Member extends Pelanggan {
    private LocalDate joinDate;
    private String nama;


    public Member(String id, int saldo, String nama, LocalDate joinDate) {
        super(id, true, saldo);
        this.nama = nama;
        this.joinDate = joinDate;
    }

    public String getNama() {
        return nama;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public int getDuration(String date){
        LocalDate tanggalJoin = LocalDate.parse(date.replace("/","-"));
        Period selisih = Period.between(tanggalJoin,LocalDate.now());
        return selisih.getDays();
    }
}
