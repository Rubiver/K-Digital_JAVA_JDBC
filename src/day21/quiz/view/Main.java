package day21.quiz.view;

import day21.quiz.dao.GuestbookDAO;
import day21.quiz.dao.MemberDAO;
import day21.quiz.dto.GuestbookDTO;
import day21.quiz.dto.MemberDTO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

class ServiceThread extends Thread{
    public static Timestamp getTimestamp() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
        long currentTime = System.currentTimeMillis();
        Timestamp result = new Timestamp(currentTime);
        return result;
    }
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
    GuestbookDAO gdao = GuestbookDAO.getInstance();
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
                        if(result==2){
                            while(true){
                                msg = dataInputStream.readUTF();
                                switch (msg){
                                    case "1":
                                        System.out.println("방명록 작성중");
                                        String writer = dataInputStream.readUTF();

                                        String contents = dataInputStream.readUTF();
                                        dataOutputStream.writeInt(gdao.insertComment(new GuestbookDTO(0,writer,contents,getTimestamp())));
                                        break;
                                    case "2":
                                        List<GuestbookDTO> gdto = new ArrayList<>();
                                        gdto = gdao.selectAll();
                                        dataOutputStream.writeInt(gdto.size());
                                        dataOutputStream.flush();
                                        for(GuestbookDTO list : gdto){
                                            dataOutputStream.writeUTF(list.getSeq()+"\t"+list.getWriter()+"\t"+list.getMessage()+"\t"+list.getWrite_date());
                                            dataOutputStream.flush();
                                        }
                                        break;
                                    case "3":
                                        System.out.println("방명록 삭제중");
                                        int seq = dataInputStream.readInt();
                                        result = gdao.deleteComment(seq);
                                        dataOutputStream.writeInt(result);
                                        break;
                                    case "4":
                                        String info;
                                        info = dataInputStream.readUTF();
                                        gdto = gdao.searchGuestbook(info);
                                        dataOutputStream.writeInt(gdto.size());
                                        dataOutputStream.flush();
                                        for(GuestbookDTO list : gdto){
                                            dataOutputStream.writeUTF(list.getSeq()+"\t"+list.getWriter()+"\t"+list.getMessage()+"\t"+list.getWrite_date());
                                            dataOutputStream.flush();
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                        System.out.println("<<로그인 종료>>");
                        break;
                    case "2":
                        System.out.println("<<회원가입 진행중>>");
                        msg = dataInputStream.readUTF();
                        data = msg.split(" ");
                        id = data[0];
                        pw = getSHA512(data[1]);
                        name = data[2];

                        boolean check =  dao.isIdExist(id);
                        if(check){
                            dataOutputStream.writeBoolean(check);
                            continue;
                        }
                        else{
                            int qResult =  dao.insertMember(new MemberDTO(id,pw,name));
                            dataOutputStream.writeInt(qResult);
                            dataOutputStream.flush();
                            System.out.println("<<회원가입 종료>>");
                        }

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
                        System.out.println("서비스 오류 발생.");
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
