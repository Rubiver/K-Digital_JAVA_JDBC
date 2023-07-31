package day21.quiz.dao;

import day21.quiz.dto.GuestbookDTO;
import day21.quiz.dto.MemberDTO;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class GuestbookDAO {
    private BasicDataSource dataSource = new BasicDataSource();

    private GuestbookDAO() {
        dataSource.setInitialSize(5);
        dataSource.setUrl("jdbc:mysql://localhost:3306/java");
        dataSource.setUsername("java");
        dataSource.setPassword("java");
    }

    private static GuestbookDAO instance;
    public synchronized static GuestbookDAO getInstance(){
        if(instance==null){
            instance = new GuestbookDAO();
        }
        return instance;
    }

    private Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }

    public int insertComment(GuestbookDTO dto) throws Exception{
        String sql = "insert into guestbook values(null,?,?,?)";
        try(
                Connection con = this.getConnection();
                PreparedStatement pstat = con.prepareStatement(sql);
                ) {
            pstat.setString(1,dto.getWriter());
            pstat.setString(2,dto.getMessage());
            pstat.setTimestamp(3,dto.getWrite_date());
            return pstat.executeUpdate();
        }
    }

    public int deleteComment(int id) throws Exception{
        String sql = "delete from guestbook where seq = ?";
        try(
                Connection con = this.getConnection();
                PreparedStatement pstat = con.prepareStatement(sql);
        ) {
            pstat.setInt(1,id);
            return pstat.executeUpdate();
        }
    }

    public List<GuestbookDTO> selectAll() throws Exception{
        String sql = "select * from guestbook";
        try (
                Connection con = this.getConnection();
                PreparedStatement pstat = con.prepareStatement(sql);
                ResultSet rs = pstat.executeQuery();
                ){
            List<GuestbookDTO> list = new ArrayList<>();
            while(rs.next()){
                int seq = rs.getInt("seq");
                String writer = rs.getString("writer");
                String message = rs.getString("message");
                Timestamp write_date = rs.getTimestamp("write_date");
                GuestbookDTO dto = new GuestbookDTO(seq, writer, message, write_date);
                list.add(dto);
            }
            return list;
        }
    }

    public List<GuestbookDTO> searchGuestbook(String info) throws Exception{
        String sql = "select * from guestbook where writer like ? or message like ?";
        try (
                Connection con = this.getConnection();
                PreparedStatement pstat = con.prepareStatement(sql);
        ){
            pstat.setString(1,"%"+info+"%");
            pstat.setString(2,"%"+info+"%");
            try(
                    ResultSet rs = pstat.executeQuery();
                    ){
                List<GuestbookDTO> list = new ArrayList<>();
                while(rs.next()){
                    int seq = rs.getInt("seq");
                    String writer = rs.getString("writer");
                    String message = rs.getString("message");
                    Timestamp write_date = rs.getTimestamp("write_date");
                    GuestbookDTO dto = new GuestbookDTO(seq, writer, message, write_date);
                    list.add(dto);
                }
                return list;
            }

        }
    }
}
