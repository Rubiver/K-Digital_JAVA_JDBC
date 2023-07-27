package Day19.exam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Exam01 {
    public static void main(String[] args) throws Exception {
        //JDBC
        Scanner sc = new Scanner(System.in);
        Connection con = null;
        try{
            String url = "jdbc:mysql://localhost:3306/java";
            String ID = "root";
            String PW = "root";

            con = DriverManager.getConnection(url,"root","root");
            System.out.println(con.toString());
            Statement statement = con.createStatement();

            String sqlQuery = "SELECT * FROM employee";
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()) {
                String data = resultSet.getString("EMP_NAME");
                System.out.println("Data: " + data);
            }
            System.out.print("메뉴 이름 : ");
            String menu = sc.nextLine();
            System.out.print("가격 : ");
            int price = Integer.parseInt(sc.nextLine());

            String sql = "insert into cafe values(null,'"+menu+"','"+price+"')";
            int result = statement.executeUpdate(sql);
            System.out.println(result);


        }catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                con.close(); //sql connection 생성 후 프로그램 종료시에 con.close() 필수.
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}