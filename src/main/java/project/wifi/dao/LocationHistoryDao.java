package project.wifi.dao;

import project.wifi.dto.LocationHistory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static project.wifi.db.ConnectionConst.*;

public class LocationHistoryDao {

    public void savedLocationHistory(String lnt, String lat) {

        try {
            // 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");

            // DB 연결
            Connection conn = DriverManager.getConnection(url, user, password);
            String sql = "insert into history(LNT, LAT, searchDate) values(?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, lnt);
            pstmt.setString(2, lat);
            pstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));

            // 쿼리 실행
            pstmt.executeUpdate();

            // 리소스 해제
            pstmt.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public List<LocationHistory> getAllLocation() {

        List<LocationHistory> list = new ArrayList<>();

        try {
            // 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");

            // DB 연결
            Connection conn = DriverManager.getConnection(url, user, password);
            String sql = "select * from history";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                LocationHistory locationHistory = new LocationHistory();
                locationHistory.setId(rs.getLong("id"));
                locationHistory.setLNT(rs.getString("lnt"));
                locationHistory.setLAT(rs.getString("lat"));
                locationHistory.setSearchDate(rs.getTimestamp("searchDate").toLocalDateTime());
                list.add(locationHistory);
            }


            // 리소스 해제
            pstmt.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }


    public void deleteId(Long id) {


        try {
            // 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");

            // DB 연결
            Connection conn = DriverManager.getConnection(url, user, password);
            String sql = "delete from history where id = " + id;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();


            // 리소스 해제
            pstmt.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
