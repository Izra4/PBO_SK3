package Admin.Promo;

import Customer.Account.CustomerManager;
import Customer.Account.Guest;
import Customer.Account.Member;
import Customer.Account.Pelanggan;
import Customer.Order.CartManager;
import Customer.Order.Order;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class PromoManager {
    private HashMap<String, Promotion> promoMap;
    private CustomerManager customerManager;
    private CartManager cartManager;

    public PromoManager(CustomerManager customerManager,CartManager cartManager) {
        this.promoMap = new HashMap<>();
        this.customerManager = customerManager;
        this.cartManager = cartManager;
    }

    public String createPromoDelivery(String input) {
        //CREATE PROMO DELIVERY DISC30|2023/05/01|2023/05/31|30%|40000|10000
        String[] splitInput = input.split(" ");
        String[] splitData = splitInput[3].split("\\|");
        String jenisPromo = splitInput[2];
        String kodePromo = splitData[0];
        String tanggalAwal = splitData[1];
        String tanggalAkhir = splitData[2];
        String percent = splitData[3];
        String percentConvert = percent.replaceAll("[^\\d.]", "");
        double persenPotongan = Double.parseDouble(percentConvert)/100;
        double maksPotongan = Double.parseDouble(splitData[4]);
        double minPembelian = Double.parseDouble(splitData[5]);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate dateAwal = LocalDate.parse(tanggalAwal,formatter);
        LocalDate dateAkhir = LocalDate.parse(tanggalAkhir,formatter);

        if (promoMap.containsKey(kodePromo)) {
            return "CREATE PROMO " + jenisPromo + " FAILED: " +kodePromo+ " IS EXISTS";
        } else {
            Promotion po1 = new PotongOngkir(kodePromo,dateAwal,dateAkhir,persenPotongan,maksPotongan,minPembelian);
            promoMap.put(kodePromo,po1);
            return "CREATE PROMO " + jenisPromo + " SUCCESS: " +kodePromo;
        }
    }

    public String createPromoDiscount(String input) {
        //CREATE PROMO DISCOUNT DISC30|2023/05/01|2023/05/31|30%|40000|10000
        String[] splitInput = input.split(" ");
        String[] splitData = splitInput[3].split("\\|");
        String jenisPromo = splitInput[2];
        String kodePromo = splitData[0];
        String tanggalAwal = splitData[1];
        String tanggalAkhir = splitData[2];
        String percent = splitData[3];
        String percentConvert = percent.replaceAll("[^\\d.]", "");
        double persenPotongan = Double.parseDouble(percentConvert)/100;
        double maksPotongan = Double.parseDouble(splitData[4]);
        double minPembelian = Double.parseDouble(splitData[5]);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate dateAwal = LocalDate.parse(tanggalAwal,formatter);
        LocalDate dateAkhir = LocalDate.parse(tanggalAkhir,formatter);

        if (promoMap.containsKey(kodePromo)) {
            return "CREATE PROMO " + jenisPromo + " FAILED: " +kodePromo+ " IS EXISTS";
        } else {
            Promotion ph1 = new PotongHarga(kodePromo,dateAwal,dateAkhir,persenPotongan,maksPotongan,minPembelian);
            promoMap.put(kodePromo,ph1);
            return "CREATE PROMO " + jenisPromo + " SUCCESS: " +kodePromo;
        }
    }

    public String createPromoCashBack(String input) {
        //CREATE PROMO CASHBACK DISC30|2023/05/01|2023/05/31|30%|40000|10000
        String[] splitInput = input.split(" ");
        String[] splitData = splitInput[3].split("\\|");
        String jenisPromo = splitInput[2];
        String kodePromo = splitData[0];
        String tanggalAwal = splitData[1];
        String tanggalAkhir = splitData[2];
        String percent = splitData[3];
        String percentConvert = percent.replaceAll("[^\\d.]", "");
        double persenPotongan = Double.parseDouble(percentConvert)/100;
        double maksPotongan = Double.parseDouble(splitData[4]);
        double minPembelian = Double.parseDouble(splitData[5]);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate dateAwal = LocalDate.parse(tanggalAwal,formatter);
        LocalDate dateAkhir = LocalDate.parse(tanggalAkhir,formatter);

        if (promoMap.containsKey(kodePromo)) {
            return "CREATE PROMO " + jenisPromo + " FAILED: " +kodePromo+ " IS EXISTS";
        } else {
            Promotion csb1 = new CashBack(kodePromo, dateAwal, dateAkhir, persenPotongan, maksPotongan, minPembelian);
            promoMap.put(kodePromo, csb1);
            return "CREATE PROMO " + jenisPromo + " SUCCESS: " + kodePromo;
        }
    }

    public String applyPromo(String input) throws Exception {
        //APPLY_PROMO IDPelanggan kodePromo
        String[] splitInput = input.split(" ");
        String id = splitInput[1];
        String kodePromo = splitInput[2];

        double totalHarga = cartManager.calculateTotalCartAmount(id);
        Order order = new Order();
        order.setTotalHarga(totalHarga);
        customerManager.getPelangganMap().get(id);
        LocalDate now = LocalDate.now();
        LocalDate mulaiDate = promoMap.get(kodePromo).getMulai();
        LocalDate berakhirDate = promoMap.get(kodePromo).getBerakhir();

        if (promoMap.containsKey(kodePromo)) {
            Promotion promotion = promoMap.get(kodePromo);
            if (!promotion.isCustomerEligible(customerManager.getPelangganMap().get(id))) {
                return "APPLY_PROMO FAILEDmember: " +kodePromo;
            } else if (!promotion.isMinimumPriceEligible(order)) {
                return "APPLY_PROMO FAILEDminim: " +kodePromo;
            } else if (!promotion.isShippingFeeEligible(order)) {
                return "APPLY_PROMO FAILEDongkir: " +kodePromo;
            } else if (now.isBefore(mulaiDate) || now.isAfter(berakhirDate)) {
                return "APPLY_PROMO FAILED: " +kodePromo + " is EXPIRED";
            }

            if (promotion instanceof PotongHarga ph1) {
                ph1.promoMember(order,customerManager.getPelangganMap().get(id));
                order.setPromotion(ph1);
                customerManager.getPelangganMap().get(id).setAppliedPromotion(ph1);
                return "APPLY_PROMO SUCCESS: " +kodePromo;
            } else if (promotion instanceof PotongOngkir po1) {
                po1.promoMember(order,customerManager.getPelangganMap().get(id));
                order.setPromotion(po1);
                customerManager.getPelangganMap().get(id).setAppliedPromotion(po1);
                System.out.println(getAppliedPromo(id));
                return "APPLY_PROMO SUCCESS: " +kodePromo;
            } else if (promotion instanceof  CashBack csb1) {
                csb1.promoMember(order,customerManager.getPelangganMap().get(id));
                order.setPromotion(csb1);
                customerManager.getPelangganMap().get(id).setAppliedPromotion(csb1);
                System.out.println(getAppliedPromo(id));
//                System.out.println(order.getTotalHarga());
                return "APPLY_PROMO SUCCESS: " +kodePromo;

            }
        }
        return "APPLY_PROMO FAILED: Terjadi kesalahan dalam penerapan promo";
    }

    public String getAppliedPromo(String id) {
        Pelanggan pelanggan = customerManager.getPelangganMap().get(id);
        if (pelanggan instanceof Guest) {
            return "Anda " + ((Guest) pelanggan).getId() + " bukan member";
        } else if (pelanggan instanceof Member member) {

            Promotion appliedPromo = member.getAppliedPromotion();
            if (appliedPromo == null) {
                return "Member dengan ID " + id + " tidak memiliki promo yang diterapkan.";
            }

            String promoCode = appliedPromo.getPromoCode();
            return promoCode;
        }
        return "Terjadi kesalahan dalam mendapatkan promo yang diterapkan.";
    }



}
