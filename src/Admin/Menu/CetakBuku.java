package Admin.Menu;

public class CetakBuku extends Menu{
    private String customType;

    public CetakBuku(String id, String nama, int harga, String customType) {
        super(id, nama, harga);
        this.customType = customType;
    }

    public String getCustomType() {
        return customType;
    }
}
