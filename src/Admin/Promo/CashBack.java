package Admin.Promo;

import Customer.Account.Pelanggan;
import Customer.Order.Order;

import java.time.LocalDate;

public class CashBack extends Promotion {
    private double cashBack;

    public CashBack(String promoCode, LocalDate mulai, LocalDate berakhir, double persenPotongan, double maksPotongan, double minimunPembelian) {
        super(promoCode, mulai, berakhir, persenPotongan, maksPotongan, minimunPembelian);
    }

    public double getCashBack() {
        return this.cashBack;
    }

    @Override
    public void promoMember(Order order, Pelanggan pelanggan) throws Exception {
        double harga = order.getTotalHarga();
        this.cashBack = 0;

        if (!isCustomerEligible(pelanggan)) {
            throw new Exception("Umur akun anda belum memenuhi");
        } else if (!isShippingFeeEligible(order)) {
            throw new Exception("Minimal Ongkir harus " + order.getOngkir());
        } else if (!isMinimumPriceEligible(order)) {
            throw new Exception("Minimal Transaksi harus " + getMinimunPembelian());
        } else {
            this.cashBack = harga * getPersenPotongan();

            if (this.cashBack < 0) {
                this.cashBack = 0;
            } else if (this.cashBack > getMaksPotongan()) {
                this.cashBack = getMaksPotongan();
            }
        }
    }
}
