package Day19.quiz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Quiz02 {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String url = "jdbc:mysql://localhost:3306/java";
        String id = "java";
        String pw = "java";
        Connection con = null;

        while(true){
            System.out.println("<<카페 메뉴 관리 시스템>>");
            System.out.println("<<1. 신규 메뉴 등록");
            System.out.println("<<2. 메뉴 목록 출력");
            System.out.println("<<3. 메뉴 정보 수정");
            System.out.println("<<4. 메뉴 정보 삭제");
            System.out.println("<<5. 메뉴 정보 검색");
            System.out.println("<<6. 프로그램 종료");
            System.out.print(">> ");

            int menu = Integer.parseInt(sc.nextLine());
            switch (menu){
                case 1:
                    con = DriverManager.getConnection(url, id, pw);
                    Statement stat = con.createStatement();

                    System.out.print("메뉴 이름 : ");
                    String name = sc.nextLine();
                    System.out.print("가격 : ");
                    int price = Integer.parseInt(sc.nextLine());
                    String sql = "insert into cafe values (null, '"+name+"','"+price+"')";
                    int result = stat.executeUpdate(sql);
                    System.out.println(result);

                    con.close();
                    break;
                case 2:
                    con = DriverManager.getConnection(url, id, pw);
                    stat = con.createStatement();

                    sql = "select * from cafe";
                    ResultSet resultset = stat.executeQuery(sql);

                    System.out.println("id  name  price");
                    while (resultset.next()) {
                        String cafeCode = resultset.getString("id");
                        String cafeMenu = resultset.getString("name");
                        int cafeMoney = resultset.getInt("price");
                        System.out.println(cafeCode +" "+cafeMenu+" "+cafeMoney);
                    }
                    con.close();
                    break;
                case 3:
                    con = DriverManager.getConnection(url, id, pw);
                    stat = con.createStatement();
                    System.out.println("<<ID로 메뉴 수정 >>");
                    System.out.print("수정할 데이터의 ID : ");
                    String cafeID = sc.nextLine();
                    System.out.print("새로운 이름 : ");
                    String cafeName = sc.nextLine();
                    System.out.print("새로운 가격 : ");
                    String cafePrice = sc.nextLine();
                    String sql1 = "update cafe set name = '"+cafeName+"' where id ='"+cafeID+"'" ;
                    String sql2 = "update cafe set price = '"+cafePrice+"' where id = '"+cafeID+"'";
                    int result1 = stat.executeUpdate(sql1);
                    int result2 = stat.executeUpdate(sql2);
                    System.out.println(result1+" "+result2);
                    con.close();
                    break;
                case 4:
                    con = DriverManager.getConnection(url, id, pw);
                    stat = con.createStatement();

                    System.out.println("<<ID로 메뉴 삭제 >>");
                    System.out.print("삭제할 데이터의 ID : ");
                    cafeID = sc.nextLine();
                    sql = "delete from cafe where id = '"+cafeID+"'";
                    result = stat.executeUpdate(sql);
                    System.out.println(result);

                    con.close();
                    break;
                case 5:
                    con = DriverManager.getConnection(url, id, pw);
                    stat = con.createStatement();

                    System.out.println("<< 이름으로 검색 >>");
                    System.out.print("검색할 데이터의 Name : ");
                    cafeName = sc.nextLine();
                    sql = "select * from cafe where name like '%"+cafeName+"%'";
                    resultset = stat.executeQuery(sql);
                    System.out.println("id  name  price");
                    while (resultset.next()) {
                        String cafeCode = resultset.getString("id");
                        String cafeMenu = resultset.getString("name");
                        int cafeMoney = resultset.getInt("price");
                        System.out.println(cafeCode +" "+cafeMenu+" "+cafeMoney);
                    }

                    con.close();
                    break;
                case 6:
                    System.out.println("프로그램 종료");
                    System.exit(0);
                    break;
                default:
                    System.out.println("잘못된 입력입니다.");
                    break;
            }
        }


    }
}
