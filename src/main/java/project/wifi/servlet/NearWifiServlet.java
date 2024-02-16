package project.wifi.servlet;


import project.wifi.dao.LocationHistoryDao;
import project.wifi.dto.WifiDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static project.wifi.db.ConnectionConst.*;


@WebServlet(name = "nearWifiServlet", value = "/nearWifi")
public class NearWifiServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        double lat = Double.parseDouble(req.getParameter("lat"));
        double lnt = Double.parseDouble(req.getParameter("lnt"));

        // DB 의 lat lnt 값으로 lat와 lnt 좌표와 가장 가까운 위치 20개를 불러온다
        List<WifiDto> wifiList = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // DB 연결
            Connection conn = DriverManager.getConnection(url, user, password);

            String sql = "SELECT " +
                    "X_SWIFI_MGR_NO," +
                    "X_SWIFI_WRDOFC," +
                    "X_SWIFI_MAIN_NM," +
                    "X_SWIFI_ADRES1," +
                    "X_SWIFI_ADRES2," +
                    "X_SWIFI_INSTL_FLOOR," +
                    "X_SWIFI_INSTL_TY," +
                    "X_SWIFI_INSTL_MBY," +
                    "X_SWIFI_SVC_SE," +
                    "X_SWIFI_CMCWR," +
                    "X_SWIFI_CNSTC_YEAR," +
                    "X_SWIFI_INOUT_DOOR," +
                    "X_SWIFI_REMARS3," +
                    "LAT," +
                    "LNT," +
                    "WORK_DTTM, " +
                    "SQRT(POWER(?, 2) + POWER(?, 2) - 2 * ? * LAT + POWER(LAT, 2) - 2 * ? * LNT + POWER(LNT, 2)) AS distance " +
                    "FROM wifi ORDER BY distance ASC limit 20";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, lat);
            pstmt.setDouble(2, lnt);
            pstmt.setDouble(3, lat);
            pstmt.setDouble(4, lnt);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                WifiDto wifiDto = new WifiDto();
                wifiDto.setX_SWIFI_MGR_NO(rs.getString("X_SWIFI_MGR_NO"));
                wifiDto.setX_SWIFI_WRDOFC(rs.getString("X_SWIFI_WRDOFC"));
                wifiDto.setX_SWIFI_MAIN_NM(rs.getString("X_SWIFI_MAIN_NM"));
                wifiDto.setX_SWIFI_ADRES1(rs.getString("X_SWIFI_ADRES1"));
                wifiDto.setX_SWIFI_ADRES2(rs.getString("X_SWIFI_ADRES2"));
                wifiDto.setX_SWIFI_INSTL_FLOOR(rs.getString("X_SWIFI_INSTL_FLOOR"));
                wifiDto.setX_SWIFI_INSTL_TY(rs.getString("X_SWIFI_INSTL_TY"));
                wifiDto.setX_SWIFI_INSTL_MBY(rs.getString("X_SWIFI_INSTL_MBY"));
                wifiDto.setX_SWIFI_SVC_SE(rs.getString("X_SWIFI_SVC_SE"));
                wifiDto.setX_SWIFI_CMCWR(rs.getString("X_SWIFI_CMCWR"));
                wifiDto.setX_SWIFI_CNSTC_YEAR(rs.getString("X_SWIFI_CNSTC_YEAR"));
                wifiDto.setX_SWIFI_INOUT_DOOR(rs.getString("X_SWIFI_INOUT_DOOR"));
                wifiDto.setX_SWIFI_REMARS3(rs.getString("X_SWIFI_REMARS3"));
                wifiDto.setLAT(rs.getString("LAT"));
                wifiDto.setLNT(rs.getString("LNT"));
                wifiDto.setWORK_DTTM(rs.getString("WORK_DTTM"));
                wifiList.add(wifiDto);
            }


            rs.close();
            pstmt.close();
            conn.close();

            LocationHistoryDao locationHistoryDao = new LocationHistoryDao();
            locationHistoryDao.savedLocationHistory(Double.toString(lnt), Double.toString(lat));


        } catch (SQLException  | ClassNotFoundException e) {
            e.printStackTrace();
        }
        req.setAttribute("wifiList", wifiList);

        req.getRequestDispatcher("/index.jsp").forward(req, resp);




    }
}
