package Admin.Menu;

public class FotoKopi extends Menu{
    private String customType;
    public FotoKopi(String id, String nama, int harga, String customType) {
        super(id, nama, harga);
        this.customType = customType;
    }

    public String getCustomType() {
        return customType;
    }
}