package Day20.day19_Refactoring.dao;

import Day20.Day20;
import Day20.day19_Refactoring.dto.CafeDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CafeDAO {


    private Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/java";
        String id = "java";
        String pw = "java";

        return DriverManager.getConnection(url, id, pw);
    }

    //finally의 기능 : 예외가 발생하든 정상적으로 실행되든, finally 내부의 코드를 반드시 실행함.
    public int insertMenu(CafeDTO dto) throws Exception {
        //try with resource, ()안에 넣을 수 있는 : AutoClosable 인터페이스를 상속받는 클래스를 인스턴스를 만들어 변수에 저장할 수 있는 클래스만 가능.
        //해당 구문을 벗어나는 경우 소괄호 안에 들어간 모든 요소를 반드시 close 해줌.
        String sql = "insert into cafe values (null,?,?)";
        // ? : place holder
        try(
                Connection con = this.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ){
            pst.setString(1, dto.getName());
            pst.setInt(2, dto.getPrice());
            int result = pst.executeUpdate();
            return result;
        }
    }

    public int updateMenuById(CafeDTO dto) throws Exception {
        String sql = "update cafe set name = ?, price = ? where id = ?";
        try(
                Connection con = this.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = null;
        ){
            pst.setString(1, dto.getName());
            pst.setInt(2, dto.getPrice());
            pst.setInt(3, dto.getId());
            int result = pst.executeUpdate();
            return result;
        }
    }

    public boolean isIdExist(int cafeId) throws Exception {
        String sql = "select * from cafe where id = ?";
        try(
                Connection con = this.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
        ){
            pst.setInt(1, cafeId);
            try(ResultSet rs = pst.executeQuery();){ //select 쿼리 + 쿼리에 ?를 채워넣어야 하는 경우 2중 try with resource문을 사용해야함.
                boolean result = rs.next();
                return result;
            }
        }
    }

    public int deleteMenuById(int cafeID) throws Exception {
        String sql = "delete from cafe where id = ?";
        try(
                Connection con = this.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = null;
        ){
            pst.setInt(1, cafeID);
            int result = pst.executeUpdate();
            return result;
        }
    }

    public List<CafeDTO> selectAll() throws Exception {
        Connection con = this.getConnection();
        String sql = "select * from cafe";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        List<CafeDTO> cafeData = new ArrayList<>();
        while (rs.next()) {
            int cafeID = rs.getInt("id");
            String cafeMenu = rs.getString("name");
            int cafePrice = rs.getInt("price");
            CafeDTO dto = new CafeDTO(cafeID, cafeMenu, cafePrice);
            cafeData.add(dto);
        }
        con.close();
        return cafeData;
    }

    public List<CafeDTO> getMenu() throws Exception {

        String sql = "select * from cafe";
        try(
                Connection con = this.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
        ){
            try(ResultSet rs = pst.executeQuery();){
                List<CafeDTO> result = new ArrayList<>();
                while (rs.next()) {
                    int cafeID = rs.getInt("id");
                    String cafeMenu = rs.getString("name");
                    int cafePrice = rs.getInt("price");
                    CafeDTO dto = new CafeDTO(cafeID, cafeMenu, cafePrice);
                    result.add(dto);
                }
                return result;
            }
        }
    }

    public List<CafeDTO> getMenu(String searchName) throws Exception {
        String sql = "select * from cafe where name like ?";
        try(
                Connection con = this.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
        ){
            pst.setString(1, "%" + searchName + "%");
            try(ResultSet rs = pst.executeQuery();){
                List<CafeDTO> result = new ArrayList<>();
                while (rs.next()) {
                    int cafeID = rs.getInt("id");
                    String cafeMenu = rs.getString("name");
                    int cafePrice = rs.getInt("price");
                    CafeDTO dto = new CafeDTO(cafeID, cafeMenu, cafePrice);
                    result.add(dto);
                }
                return result;
            }
        }
    }

    /*public void getMenu(){

    }*/
}
