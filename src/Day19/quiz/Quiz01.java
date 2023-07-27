package Day19.quiz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Quiz01 {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://localhost:3306/java";
        String id = "java";
        String pw = "java";
        Connection con = DriverManager.getConnection(url, id, pw);
        Statement stat = con.createStatement();

        String sql = "select * from job";

        ResultSet resultset = stat.executeQuery(sql);
        System.out.println(resultset); //DBMS에서 생성된 결과 테이블의 시작지 주소값을 리턴

        while (resultset.next()) {
            String jobCode = resultset.getString("JOB_CODE");
            String jobName = resultset.getString("JOB_NAME");
            System.out.println(jobCode + " " + jobName );
        }
        con.close();
    }
}
