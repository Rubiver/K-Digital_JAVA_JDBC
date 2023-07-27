package Day19.exam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Exam02 {
    public static void main(String[] args) throws Exception {
        Connection con = null;
        String url = "jdbc:mysql://localhost:3306/java";
        String id = "java";
        String pw = "java";
        con = DriverManager.getConnection(url,id,pw);
        Statement stat = con.createStatement();

        String sql = "update cafe set price = '2500' where id='1001'";
        int result = stat.executeUpdate(sql);
        System.out.println(result);
        con.close();
    }
}

/*
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


            String sql = "update cafe set price = '3000' where id = '1001' ";
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
 */