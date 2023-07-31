package day21.quiz;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client_pair {

    public static void main(String[] args) {
        try{
            Scanner sc = new Scanner(System.in);
            //Socket client = new Socket("127.0.0.1",15000);
            Socket client = new Socket("10.2.10.178",15000);
            InputStream is = client.getInputStream();
            DataInputStream dis = new DataInputStream(is);

            OutputStream os = client.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);

            while(true) {
                printTitle();
                try {
                    Thread.sleep(10);
                    String send = sc.nextLine();
                    dos.writeUTF(send);
                    dos.flush();

                    if(send.equals("2")) {
                        System.out.println("<< 회원가입 >>");
                        System.out.print("id : ");
                        String id = sc.nextLine();
                        dos.writeUTF(id);
                        dos.flush();

                        System.out.print("pw : ");
                        String pw = sc.nextLine();
                        dos.writeUTF(pw);
                        dos.flush();

                        System.out.print("name : ");
                        String name = sc.nextLine();
                        dos.writeUTF(name);
                        dos.flush();

                    }else if(send.equals("1")) {
                        System.out.println("<< 로그인 >>");
                        System.out.print("id : ");
                        String id = sc.nextLine();
                        dos.writeUTF(id);
                        dos.flush();

                        System.out.print("pw : ");
                        String pw = sc.nextLine();
                        dos.writeUTF(pw);
                        dos.flush();
                    }else if(send.equals("0")) {
                        System.out.println("시스템을 종료합니다.");
                        System.exit(0);
                    }
                    else {
                    }

                    String msg = dis.readUTF();
                    System.out.println("서버 : " + msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printTitle() {
        System.out.print("<< 인증시스템 >>\n"+
                "1.로그인\n"+
                "2.회원가입\n"+
                ">>> ");
    }


}