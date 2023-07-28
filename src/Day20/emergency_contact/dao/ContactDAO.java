package Day20.emergency_contact.dao;

import Day20.day19_Refactoring.dto.CafeDTO;
import Day20.emergency_contact.dto.ContactDTO;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {
    private Connection getConnection() throws Exception{
        String url = "jdbc:mysql://localhost:3306/java";
        String id = "java";
        String pw = "java";
        return DriverManager.getConnection(url, id, pw);
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
