package day21.exam;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

//Day 18의 Exam과 이어짐.
public class Exam05 {
    public static void main(String[] args) throws Exception{
        //날짜 데이터를 DB에 입력하는 방법, DB의 날짜 데이터를 자바에서 사용하는 방법.
        ExamDAO dao = ExamDAO.getInstance();
        Scanner sc = new Scanner(System.in);

        //Timestamp를 DB에 저장.
        long currentTime = System.currentTimeMillis();
//        ExamDTO dto = new ExamDTO(new Timestamp(currentTime));
//
//        try{
//            dao.insertDate(new ExamDTO(new Timestamp(currentTime)));
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        //임의로 입력한 날짜를 DB에 저장하는 방법.
//        System.out.print("저장할 날짜 입력 (ex : 23/07/31) >>");
//        String inputDate = sc.nextLine();
//        //방법 1 : DB 명령어를 통해 문자열 날짜를 변경
//        //방법 2 : 자바에서 날짜형으로 변경해서 DB에 입력 -> 해당 방법이 선호됨. 서버 부하 방지. String to Long to Date
//        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
//
//        try {
//            Timestamp tims = new Timestamp(sdf.parse(inputDate).getTime());
//            ExamDTO dto = new ExamDTO(tims);
//            ExamDAO.getInstance().insertDate(dto);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //DB에서 날짜 데이터 꺼내오기
        List<ExamDTO> list =   ExamDAO.getInstance().selectDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        for(ExamDTO data : list){
            System.out.println(data.getDate());
        }
        for(ExamDTO data : list){
            long date = data.getDate().getTime();
            System.out.println(sdf.format(date));
        }
        for(ExamDTO data : list){
            System.out.println(data.getFormedDate());
        }

    }
}
