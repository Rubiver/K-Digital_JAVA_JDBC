package Day20.emergency_contact.view;

import Day20.emergency_contact.dao.ContactDAO;
import Day20.emergency_contact.dto.ContactDTO;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ContactDAO dao = new ContactDAO();

        while(true){
            System.out.println("<<연락처 관리 프로그램>>");
            System.out.println("1. 신규 연락처 등록");
            System.out.println("2. 연락처 정보 출력");
            System.out.println("3. 연락처 정보 수정");
            System.out.println("4. 연락처 정보 삭제");
            System.out.println("5. 연락처 정보 검색");
            System.out.println("0. 프로그램 종료");
            System.out.print(">>");
            int menu = Integer.parseInt(sc.nextLine());
            try{
                switch (menu){
                    case 1:
                        System.out.println("<<신규 연락처 등록>>");
                        System.out.print("이름 : ");
                        String name = sc.nextLine();
                        System.out.print("연락처 : ");
                        String phone = sc.nextLine();

                        int result = dao.insertContact(new ContactDTO(0,name,phone));
                        if(result>0){
                            System.out.println("등록 성공");
                        }
                        break;
                    case 2:
                        System.out.println("<<연락처 출력>>");
                        List<ContactDTO> list = dao.getContact();
                        System.out.println("id\tname\tphone");
                        for(ContactDTO dto : list){
                            System.out.println(dto.getId()+"\t"+dto.getName()+"\t"+dto.getPhone());
                        }
                        break;
                    case 3:
                        System.out.println("<<연락처 수정>>");
                        System.out.print("수정할 ID 입력 : ");
                        int id = Integer.parseInt(sc.nextLine());
                        System.out.print("새로운 이름 입력 : ");
                        name = sc.nextLine();
                        System.out.print("새로운 연락처 입력 : ");
                        phone = sc.nextLine();
                        result = dao.updateContact(new ContactDTO(id, name, phone));
                        if(result>0)
                        {
                            System.out.println("연락처 수정 성공");
                        }
                        break;
                    case 4:
                        System.out.println("<<연락처 삭제>>");
                        System.out.print("삭제할 ID 입력 : ");
                        id = Integer.parseInt(sc.nextLine());

                        result = dao.deleteContact(id);
                        System.out.println(result);
                        break;
                    case 5:
                        System.out.println("<<연락처 검색>>");
                        System.out.print("검색할 연락처 정보 입력 : ");
                        String info = sc.nextLine();

                        List<ContactDTO> searchlist = dao.getContact(info);
                        System.out.println("id\tname\tphone");
                        for(ContactDTO dto : searchlist){
                            System.out.println(dto.getId()+"\t"+dto.getName()+"\t"+dto.getPhone());
                        }
                        break;
                    case 0:
                        System.out.println("프로그램 종료");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("잘못된 입력 발생.");
                        break;
                }
            }catch (Exception e) {
                e.printStackTrace();
                System.out.println("프로그램 오류 발생.");
            }
        }
    }
}
