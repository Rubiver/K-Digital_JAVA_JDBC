package day21.quiz.view;

import day21.quiz.dao.MemberDAO;
import day21.quiz.dto.MemberDTO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

class ServiceThread extends Thread{
    public static String getSHA512(String input){

        String toReturn = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(input.getBytes("utf8"));
            toReturn = String.format("%0128x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toReturn;
    }
    MemberDAO dao = MemberDAO.getInstance();
    Socket sock;
    public ServiceThread(Socket socket){
        this.sock  = socket;
        System.out.println(this.sock.getInetAddress()+"로 부터 접속");
    }
    public void run(){
        try {
            DataInputStream dataInputStream = new DataInputStream(sock.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(sock.getOutputStream());

            while(true){
                String msg = "";
                String id = "";
                String pw = "";
                String name = "";
                String[] data = new String[3];
                msg = dataInputStream.readUTF();
                switch (msg){
                    case "1":
                        System.out.println("<<로그인 진행중>>");
                        msg = dataInputStream.readUTF();
                        data = msg.split(" ");
                        id = data[0];
                        pw = getSHA512(data[1]) ;
                        int result = dao.checkID(id, pw);

                        dataOutputStream.writeInt(result);
//                        if(result==2){
//                            dataOutputStream.writeUTF("로그인 성공");
//                            dataOutputStream.flush();
//                        }else if(result == 1){
//                            dataOutputStream.writeUTF("로그인 실패, 비밀번호를 확인하세요");
//                            dataOutputStream.flush();
//                        }
//                        else{
//                            dataOutputStream.writeUTF("로그인 실패, 아이디를 확인하세요");
//                            dataOutputStream.flush();
//                        }
                        System.out.println("<<로그인 종료>>");
                        break;
                    case "2":
                        System.out.println("<<회원가입 진행중>>");
                        msg = dataInputStream.readUTF();
                        data = msg.split(" ");
                        id = data[0];
                        pw = getSHA512(data[1]);
                        name = data[2];
                        int qResult =  dao.insertMember(new MemberDTO(id,pw,name));
                        dataOutputStream.writeInt(qResult);
                        dataOutputStream.flush();
                        System.out.println("<<회원가입 종료>>");
                        break;
                    case "3":
                        List<MemberDTO> mdto = new ArrayList<>();
                        mdto = dao.selectAll();
                        dataOutputStream.writeInt(mdto.size());;
                        dataOutputStream.flush();
                        for(MemberDTO list : mdto){
                            dataOutputStream.writeUTF(list.getId()+"\t"+list.getPw()+"\t"+list.getName());
                            dataOutputStream.flush();
                        }
                        break;
                    default:
                        dataOutputStream.writeUTF("서비스 오류발생.");
                        break;

                }
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(sock.getInetAddress()+" 로부터 연결 끊김.");
        }
    }
}
public class Main extends Thread{
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(15000);
        while(true){
            Socket client = serverSocket.accept();
            new ServiceThread(client).start();
        }
    }
}
