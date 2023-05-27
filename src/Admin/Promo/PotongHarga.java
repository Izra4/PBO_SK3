package Admin.Promo;

import Customer.Account.Pelanggan;
import Customer.Order.Order;

import java.time.LocalDate;

public class PotongHarga extends Promotion {
    private double totalDiskon;

    public PotongHarga(String promoCode, LocalDate mulai, LocalDate berakhir, double persenPotongan, double maksPotongan, double minimunPembelian) {
        super(promoCode, mulai, berakhir, persenPotongan, maksPotongan, minimunPembelian);
    }

    public double getTotalDiskon() {
        return totalDiskon;
    }

    @Override
    public void promoMember(Order order, Pelanggan pelanggan) throws Exception {
        double diskon = order.getTotalHarga();

        if (!isCustomerEligible(pelanggan)) {
            throw new Exception("Umur akun anda belum memenuhi");
        } else if (!isShippingFeeEligible(order)) {
            throw new Exception("Minimal Ongkir harus " + order.getOngkir());
        } else if (!isMinimumPriceEligible(order)) {
            throw new Exception("Minimal Transaksi harus " + getMinimunPembelian());
        } else {
            this.totalDiskon = diskon * getPersenPotongan();

            if (this.totalDiskon > getMaksPotongan()) {
                this.totalDiskon = getMaksPotongan();
            }
        }
    }
//    @Override
//    public void compareTo(Order order,PotongOngkir potongOngkir,PotongHarga potongHarga,CashBack cashBack) {
//        String[] listPromo = new String[super.getJumlahPromo()];
//        int count = 0;
//
//        if (totalDiskon(order,potongHarga) > totalOngkir(order,potongOngkir) || totalDiskon(order, potongHarga) > totalCashBack(order, cashBack)) {
//            listPromo[count] = potongHarga.getNama();
//            count++;
//        } else if (totalOngkir(order, potongOngkir) > totalDiskon(order, potongHarga) || totalOngkir(order, potongOngkir) > totalCashBack(order, cashBack)) {
//            listPromo[count] = potongOngkir.getNama();
//            count++;
//        } else if (totalCashBack(order, cashBack) > totalDiskon(order, potongHarga) || totalCashBack(order, cashBack) > totalOngkir(order, potongOngkir)) {
//            listPromo[count] = cashBack.getNama();
//            count++;
//        }
//        System.out.print("Daftar Promo: ");
//        if (count > 0) {
//            System.out.println(listPromo[0]);
//        } else {
//            System.out.println("Tidak ada promo yang tersedia untuk order ini.");
//        }
//    }
}
