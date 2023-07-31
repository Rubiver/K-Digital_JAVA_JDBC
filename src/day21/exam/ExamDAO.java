package day21.exam;

import Day20.day19_Refactoring.dao.CafeDAO;
import Day20.day19_Refactoring.dto.CafeDTO;
import Day20.emergency_contact.dto.ContactDTO;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExamDAO {
    private BasicDataSource dataSource = new BasicDataSource();

    private ExamDAO() {
        dataSource.setInitialSize(5);
        dataSource.setUrl("jdbc:mysql://localhost:3306/java");
        dataSource.setUsername("java");
        dataSource.setPassword("java");
    }

    //인스턴스를 한개만 생성하도록 하는 Singleton 모델링 기법으로 아래의 코드를 작성함.
    //Singleton
    private static ExamDAO instance;
    //syschronized : 단 하나의 메서드 call만 허용, 메서드 사용 시 다른 사용자는 해당 메서드를 사용하지 못하는 상태가됨.
    //서비스가 안전해지지만 그만큼 성능저하를 불러옴.
    public synchronized static ExamDAO getInstance(){ //인스턴스 1회 생성 후 다른 사용자가 추가 인스턴스를 생성하지 않음.
        if(instance==null){
            instance = new ExamDAO();
        }
        return instance;
    }

    private Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }

    public int insertDate(ExamDTO dto) throws Exception{
        String sql = "insert into exam values(?)";
        try(
                Connection con = this.getConnection();
                PreparedStatement pstat = con.prepareStatement(sql);
                ){
            pstat.setTimestamp(1,dto.getDate());
            return pstat.executeUpdate();
        }
    }

    public List<ExamDTO> selectDate() throws Exception{
        String sql = "select * from exam";
        try(
                Connection con = this.getConnection();
                PreparedStatement pstat = con.prepareStatement(sql);
                ResultSet rs = pstat.executeQuery();
                ){
            List<ExamDTO> list = new ArrayList<>();
            while(rs.next()){
                ExamDTO dto = new ExamDTO();
                dto.setDate(rs.getTimestamp("HMS_date"));
                list.add(dto);
            }
            return list;
        }
    }
}
