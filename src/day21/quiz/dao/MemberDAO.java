package day21.quiz.dao;

import Day20.day19_Refactoring.dto.CafeDTO;
import day21.quiz.dto.MemberDTO;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    private BasicDataSource dataSource = new BasicDataSource();

    private MemberDAO() {
        dataSource.setInitialSize(5);
        dataSource.setUrl("jdbc:mysql://localhost:3306/java");
        dataSource.setUsername("java");
        dataSource.setPassword("java");
    }

    private static MemberDAO instance;
    public synchronized static MemberDAO getInstance(){
        if(instance==null){
            instance = new MemberDAO();
        }
        return instance;
    }

    private Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }

    public int insertMember(MemberDTO dto) throws Exception{
        String sql = "insert into members values(?,?,?)";
        try(
                Connection con = this.getConnection();
                PreparedStatement pstat = con.prepareStatement(sql);
                ) {
            pstat.setString(1,dto.getId());
            pstat.setString(2,dto.getPw());
            pstat.setString(3,dto.getName());
            return pstat.executeUpdate();
        }
    }

    public List<MemberDTO> selectAll() throws Exception{
        String sql = "select * from members";
        try (
                Connection con = this.getConnection();
                PreparedStatement pstat = con.prepareStatement(sql);
                ResultSet rs = pstat.executeQuery();
                ){
            List<MemberDTO> list = new ArrayList<>();
            while(rs.next()){
                String id = rs.getString("id");
                String pw = rs.getString("pw");
                String name = rs.getString("name");
                MemberDTO dto = new MemberDTO(id,pw,name);
                list.add(dto);
            }
            return list;
        }
    }

    public int checkID(String checkid, String checkpw) throws Exception{
        String sql = "select * from members where id = ?";
        try(
                Connection con = this.getConnection();
                PreparedStatement pstat = con.prepareStatement(sql);
                ){
            pstat.setString(1,checkid);
            try ( ResultSet rs = pstat.executeQuery(); ){
                while(rs.next()){
                    String id = rs.getString("id");
                    String pw = rs.getString("pw");
                    if(id.equals(checkid)){
                        if(pw.equals(checkpw)){
                            return 2;
                        }
                        return 1;
                    }
                }
                return 0;
            }
        }
    }
    public boolean isIdExist(String checkID) throws Exception {
        String sql = "select * from members where id = ?";
        try(
                Connection con = this.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
        ){
            pst.setString(1, checkID);
            try(ResultSet rs = pst.executeQuery();){
                boolean result = rs.next();
                return result;
            }
        }
    }
}
