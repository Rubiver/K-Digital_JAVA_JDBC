package Day20.emergency_contact.dao;

import Day20.day19_Refactoring.dao.CafeDAO;
import Day20.day19_Refactoring.dto.CafeDTO;
import Day20.emergency_contact.dto.ContactDTO;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {
    private BasicDataSource dataSource = new BasicDataSource();

    private ContactDAO() {
        dataSource.setInitialSize(5);
        dataSource.setUrl("jdbc:mysql://localhost:3306/java");
        dataSource.setUsername("java");
        dataSource.setPassword("java");
    }

    //인스턴스를 한개만 생성하도록 하는 Singleton 모델링 기법으로 아래의 코드를 작성함.
    //Singleton
    private static ContactDAO instance;
    //syschronized : 단 하나의 메서드 call만 허용, 메서드 사용 시 다른 사용자는 해당 메서드를 사용하지 못하는 상태가됨.
    //서비스가 안전해지지만 그만큼 성능저하를 불러옴.
    public synchronized static ContactDAO getInstance(){ //인스턴스 1회 생성 후 다른 사용자가 추가 인스턴스를 생성하지 않음.
        if(instance==null){
            instance = new ContactDAO();
        }
        return instance;
    }

    private Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }

    public int insertContact(ContactDTO dto) throws Exception{
        String sql = "insert into contact values(null, ?, ?)";
        try(
                Connection con = this.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
        ){
            pst.setString(1, dto.getName());
            pst.setString(2,dto.getPhone());
            int result = pst.executeUpdate();
            return result;
        }
    }
    public int updateContact(ContactDTO dto) throws Exception{
        String sql = "update contact set name = ? , phone = ? where id = ?";
        try(
                Connection con = this.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
        ){
            pst.setString(1, dto.getName());
            pst.setString(2,dto.getPhone());
            pst.setInt(3,dto.getId());
            int result = pst.executeUpdate();
            return result;
        }
    }

    public int deleteContact(int id) throws Exception{
        String sql = "delete from contact where id = ?";
        try(
                Connection con = this.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
        ){
            pst.setInt(1, id);
            int result = pst.executeUpdate();
            return result;
        }
    }

    public List<ContactDTO> getContact() throws Exception{
        String sql = "select * from contact";
        try(
                Connection con = this.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
        ){
            try(ResultSet rs = pst.executeQuery();){
                List<ContactDTO> list = new ArrayList<>();
                while(rs.next()){
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String phone = rs.getString("phone");
                    ContactDTO dto = new ContactDTO(id,name,phone);
                    list.add(dto);
                }
                return list;
            }
        }
    }
    public List<ContactDTO> getContact(String info) throws Exception{
        String sql = "select * from contact where name like ? or phone like ?";
        try(
                Connection con = this.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
        ){
            pst.setString(1, "%"+info+"%");
            pst.setString(2,"%"+info+"%");
            try(ResultSet rs = pst.executeQuery();){
                List<ContactDTO> list = new ArrayList<>();
                while(rs.next()){
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String phone = rs.getString("phone");
                    ContactDTO dto = new ContactDTO(id,name,phone);
                    list.add(dto);
                }
                return list;
            }
        }
    }

}
