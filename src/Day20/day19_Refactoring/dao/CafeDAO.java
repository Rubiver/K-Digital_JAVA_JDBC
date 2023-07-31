package Day20.day19_Refactoring.dao;

import Day20.Day20;
import Day20.day19_Refactoring.dto.CafeDTO;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CafeDAO {
    //Day 21, DBCP 적용하기.
//    private Connection getConnection() throws Exception {
//        String url = "jdbc:mysql://localhost:3306/java";
//        String id = "java";
//        String pw = "java";
//
//        return DriverManager.getConnection(url, id, pw);
//    }
    //라이브러리 사용할 때 같이 사용해야되는 연관된 라이브러리들이 있다.
    private BasicDataSource dataSource = new BasicDataSource();

    private CafeDAO() {
        dataSource.setInitialSize(5);
        dataSource.setUrl("jdbc:mysql://localhost:3306/java");
        dataSource.setUsername("java");
        dataSource.setPassword("java");
    }

    //인스턴스를 한개만 생성하도록 하는 Singleton 모델링 기법으로 아래의 코드를 작성함.
    //Singleton
    private static CafeDAO instance;
    //syschronized : 단 하나의 메서드 call만 허용, 메서드 사용 시 다른 사용자는 해당 메서드를 사용하지 못하는 상태가됨.
    //서비스가 안전해지지만 그만큼 성능저하를 불러옴.
    public synchronized static CafeDAO getInstance(){ //인스턴스 1회 생성 후 다른 사용자가 추가 인스턴스를 생성하지 않음.
        if(instance==null){
            instance = new CafeDAO();
        }
        return instance;
    }

    private Connection getConnection() throws Exception {
        return dataSource.getConnection();
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
