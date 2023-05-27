package Admin.Promo;


import Customer.Account.Member;
import Customer.Account.Pelanggan;
import Customer.Order.Order;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;



public abstract class Promotion implements Applicable {
    private String promoCode;
    private LocalDate mulai;
    private LocalDate berakhir;
    private double persenPotongan;
    private double maksPotongan;
    private double minimunPembelian;
//    private int jumlahPromo = 0;
    private String nama;

    public Promotion(String promoCode, LocalDate mulai,LocalDate berakhir,double persenPotongan,double maksPotongan,double minimunPembelian) {
        this.promoCode = promoCode;
        this.mulai = mulai;
        this.berakhir = berakhir;
        this.persenPotongan = persenPotongan;
        this.maksPotongan = maksPotongan;
        this.minimunPembelian = minimunPembelian;
    }
    public abstract void promoMember(Order order, Pelanggan pelanggan) throws Exception;



    public boolean isPromoCodeValid(String inputPromo) {
        return inputPromo.equals(promoCode);
    }

    public String getNama() {
        return nama;
    }

    public double getPersenPotongan() {
        return persenPotongan;
    }

    public void setPersenPotongan(double persenPotongan) {
        this.persenPotongan = persenPotongan;
    }

    public double getMaksPotongan() {
        return maksPotongan;
    }

    public void setMaksPotongan(double maksPotongan) {
        this.maksPotongan = maksPotongan;
    }

    public double getMinimunPembelian() {
        return minimunPembelian;
    }

    public void setMinimunPembelian(double minimunPembelian) {
        this.minimunPembelian = minimunPembelian;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public LocalDate getMulai() {
        return mulai;
    }

    public void setMulai(LocalDate mulai) {
        this.mulai = mulai;
    }

    public LocalDate getBerakhir() {
        return berakhir;
    }

    public void setBerakhir(LocalDate berakhir) {
        this.berakhir = berakhir;
    }

    @Override
    public boolean isCustomerEligible(Pelanggan pelanggan) {
        if (pelanggan instanceof Member member) {
            LocalDate bergabung = member.getJoinDate();
            LocalDate now = LocalDate.now();
            long lamaBergabung = ChronoUnit.DAYS.between(bergabung, now);

            return lamaBergabung > 30;
        } else {
            System.out.println("bukan");
            return false;
        }
    }

    @Override
    public boolean isMinimumPriceEligible(Order order) {
        return order.getTotalHarga() > getMinimunPembelian();
    }

    @Override
    public boolean isShippingFeeEligible(Order order) {
        return order.getTotalHarga() > getMinimunPembelian();
    }

    @Override
    public double totalCashBack(Order order,CashBack cashBack) {
        return cashBack.getCashBack();
    }

    @Override
    public double totalDiskon(Order order,PotongHarga potongHarga) {
        return potongHarga.getTotalDiskon();
    }

    @Override
    public double totalOngkir(Order order,PotongOngkir potongOngkir) {
        return potongOngkir.getTotalOngkir();
    }
}

