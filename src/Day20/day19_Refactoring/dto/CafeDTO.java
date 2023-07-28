package Day20.day19_Refactoring.dto;

public class CafeDTO {
    private int id;
    private String name;
    private int price;

    public CafeDTO() {
    }

    public CafeDTO(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
