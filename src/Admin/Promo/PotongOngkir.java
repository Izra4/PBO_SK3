package Admin.Promo;

import Customer.Account.Pelanggan;
import Customer.Order.Order;

import java.time.LocalDate;

public class PotongOngkir extends Promotion {
    private double totalOngkir;

    public PotongOngkir(String promoCode, LocalDate mulai, LocalDate berakhir, double persenPotongan, double maksPotongan, double minimunPembelian) {
        super(promoCode, mulai, berakhir, persenPotongan, maksPotongan, minimunPembelian);
    }

    public double getTotalOngkir() {
        return totalOngkir;
    }

    @Override
    public void promoMember(Order order, Pelanggan pelanggan) throws Exception {
        double ongkir = order.getOngkir();

        if (!isCustomerEligible(pelanggan)) {
            throw new Exception("Umur akun anda belum memenuhi");
        } else if (!isShippingFeeEligible(order)) {
            throw new Exception("Minimal Ongkir harus " + order.getOngkir());
        } else if (!isMinimumPriceEligible(order)) {
            throw new Exception("Minimal Transaksi harus " + getMinimunPembelian());
        } else {
            this.totalOngkir = ongkir * getPersenPotongan();

            if (this.totalOngkir > getMaksPotongan()) {
                this.totalOngkir = getMaksPotongan();
            }
        }
    }
}
