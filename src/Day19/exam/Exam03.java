package Day19.exam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

public class Exam03 {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String url = "jdbc:mysql://localhost:3306/java";
        String id = "java";
        String pw = "java";
        Connection con  = DriverManager.getConnection(url,id,pw);
        Statement stat =  con.createStatement();

        System.out.print("삭제할 데이터의 ID :");
        String dataID = sc.nextLine();

        String sql = "delete from cafe where id = '"+dataID+"'";
        //String sql = "delete from cafe where id = '1001'";
        int result = stat.executeUpdate(sql);
        System.out.println(result);

        con.close();

    }
}
