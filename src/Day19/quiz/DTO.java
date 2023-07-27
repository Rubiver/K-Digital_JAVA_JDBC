package Day19.quiz;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DTO {
    private String url = "jdbc:mysql://localhost/java";
    private String ID = "java";
    private String PW = "java";
    private String table = "cafe";
    Connection con = null;
    Statement stat = null;
    private int id;
    private String name;
    private int price;
    private String sql = "";

    public DTO() {
    }

    public DTO(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
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
    public void pushData(){
        try {
            this.con = DriverManager.getConnection(this.url, this.ID, this.PW);
            this.stat = con.createStatement();
            this.sql = "insert into "+this.table
                    +" values (null,'"+this.id+"','"+this.name+"')";
            this.stat.executeUpdate(this.sql);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
