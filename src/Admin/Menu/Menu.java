package Admin.Menu;

public class Menu {
    private String id;
    private String nama;
    private int Harga;

    public Menu(String id, String nama, int harga) {
        this.id = id;
        this.nama = nama;
        Harga = harga;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public int getHarga() {
        return Harga;
    }
}
