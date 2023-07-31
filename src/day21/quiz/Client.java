package day21.quiz;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception{
        //10.2.15.193
        Socket client = new Socket("localhost",15000);
        DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
        Scanner sc = new Scanner(System.in);

        String id;
        String pw;
        String name;
        String msg = "";
        String menu = "";
        while(true){
            System.out.println("1. 로그인");
            System.out.println("2. 회원가입");
            menu = sc.nextLine();
            dataOutputStream.writeUTF(menu);
            dataOutputStream.flush();
            switch (menu){
                case "1":
                    System.out.print("ID >>");
                    id = sc.nextLine();
                    System.out.print("PW >>");
                    pw = sc.nextLine();
                    msg = id +" "+ pw;
                    dataOutputStream.writeUTF(msg);
                    dataOutputStream.flush();

                    int result = dataInputStream.readInt();
                    if(result==2){
                        System.out.println("로그인 성공");
                    }else if(result == 1){
                        System.out.println("로그인 실패, 비밀번호를 확인하세요");
                    }
                    else{
                        System.out.println("로그인 실패, 아이디를 확인하세요");
                    }
                    break;
                case "2":
                    System.out.println("<<회원가입>>");
                    System.out.println("ID >>");
                    id = sc.nextLine();
                    System.out.println("PW >>");
                    pw = sc.nextLine();
                    System.out.println("NAME >>");
                    name = sc.nextLine();
                    msg = id+" "+pw+" "+name;
                    dataOutputStream.writeUTF(msg);

                    boolean check = dataInputStream.readBoolean();
                    if(check){
                        System.out.println("중복 ID가 존재합니다.");
                        continue;
                    }
                    else{
                        result = dataInputStream.readInt();
                        if(result>0){
                            System.out.println("회원가입 성공");
                        }
                    }
                    break;
                case "3":
                    int index = dataInputStream.readInt();
                    System.out.println("id\tpw\tname");
                    for(int i=0; i<index; i++){
                        System.out.println(dataInputStream.readUTF());
                    }
                    break;
                default:
                    System.out.println("잘못된 번호 선택함.");
                    break;
            }
        }
    }
}
