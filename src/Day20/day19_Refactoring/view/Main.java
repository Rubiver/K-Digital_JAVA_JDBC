package Day20.day19_Refactoring.view;

import Day20.day19_Refactoring.dao.CafeDAO;
import Day20.day19_Refactoring.dto.CafeDTO;
import com.mysql.cj.log.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //main 메서드에서의 예외전가는 결국 운영체제에게 전가되는데 운영체제는 예외를 처리할 수 없기 때문에 프로그램을 종료시킨다.
        Scanner sc = new Scanner(System.in);
        CafeDAO dao = CafeDAO.getInstance();


        while (true) {
            System.out.println("<<카페 메뉴 관리 시스템>>");
            System.out.println("<<1. 신규 메뉴 등록");
            System.out.println("<<2. 메뉴 목록 출력");
            System.out.println("<<3. 메뉴 정보 수정");
            System.out.println("<<4. 메뉴 정보 삭제");
            System.out.println("<<5. 메뉴 정보 검색");
            System.out.println("<<6. 프로그램 종료");
            System.out.print(">> ");

            int menu = Integer.parseInt(sc.nextLine());
            try {
                switch (menu) {
                    case 1:
                        System.out.print("메뉴 이름 : ");
                        String name = sc.nextLine();
                        System.out.print("가격 : ");
                        int price = Integer.parseInt(sc.nextLine());

                        //throws는 callee 메서드에서 생기는 예외를 caller 메서드에게 전가하는 것임.

                        int result = dao.insertMenu(new CafeDTO(0, name, price));
                        if (result > 0) {
                            System.out.println("신규메뉴 등록 성공.");
                        }
                        break;
                    case 2:
                        List<CafeDTO> list = dao.getMenu();
                        System.out.println("id\tname\tprice");
                        for(CafeDTO data : list)
                        {
                            System.out.println(data.getId()+"\t"+data.getName()+"\t"+data.getPrice());
                        }

                        break;
                    case 3:
                        //resultset, select, !rs.next()로 id 검색

                        System.out.println("<<ID로 메뉴 수정 >>");
                        System.out.print("수정할 데이터의 ID : ");
                        int cafeID = Integer.parseInt(sc.nextLine());
                        boolean idExist = dao.isIdExist(cafeID);

                        if (!idExist) {
                            System.out.println("수정할 대항 ID를 찾을 수 없습니다.");
                            continue;
                        }
                        System.out.print("새로운 이름 : ");
                        String cafeName = sc.nextLine();
                        System.out.print("새로운 가격 : ");
                        int cafePrice = Integer.parseInt(sc.nextLine());
                        try {
                            result = dao.updateMenuById(new CafeDTO(cafeID, cafeName, cafePrice));
                            System.out.println(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("수정 중 오류 발생.");
                        }
                        break;
                    case 4:
                        System.out.print("삭제할 데이터의 ID : ");
                        cafeID = Integer.parseInt(sc.nextLine());
                        idExist = dao.isIdExist(cafeID);
                        if (!idExist) {
                            System.out.println("수정할 대항 ID를 찾을 수 없습니다.");
                            continue;
                        }
                        result = dao.deleteMenuById(cafeID);
                        System.out.println(result);

                        break;
                    case 5:
                        System.out.println("<< 이름으로 검색 >>");
                        System.out.print("검색할 데이터의 Name : ");
                        cafeName = sc.nextLine();

                        List<CafeDTO> searchList = dao.getMenu(cafeName);
                        System.out.println("id\tname\tprice");
                        for(CafeDTO data : searchList)
                        {
                            System.out.println(data.getId()+"\t"+data.getName()+"\t"+data.getPrice());
                        }
                        break;
                    case 6:
                        System.out.println("프로그램 종료");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("잘못된 입력입니다.");
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("입력 중 오류가 발생했습니다.");
            }
        }


    }
}
