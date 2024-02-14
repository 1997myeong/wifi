package project.wifi.servlet;

import com.google.gson.*;

import project.wifi.api.ApiKey;
import project.wifi.db.DBConnector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@WebServlet(name = "apiExplorerServlet", value = "/load")
public class ApiExplorerServlet extends HttpServlet {
    // row 데이터 갯수
    static int rowCount = 1;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        for (int i = 1; i <= rowCount; i = i + 1000) {
            // API 호출 URL 빌더
            StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088/");
            urlBuilder.append(ApiKey.apiKey);
            urlBuilder.append("/" + "json");
            urlBuilder.append("/" + "TbPublicWifiInfo");
            int start = i;
            int end =  i + 999;
            urlBuilder.append("/" + start);
            urlBuilder.append("/" + end);

            String apiUrl = urlBuilder.toString();

            // url 객체 생성
            URL url = new URL(apiUrl);

            // url 통신 connection 객체 생성
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // 요청 메소드
            conn.setRequestMethod("GET");

            // Content-Type 세팅
            conn.setRequestProperty("Content-Type", "application/json");

            // 데이터 BufferedReader 객체로 저장
            BufferedReader data;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                data = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } else {
                data = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
            }

            // StringBuilder 로 객체 저장
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = data.readLine()) != null) {
                sb.append(line);
            }

            // 연결 해제
            data.close();
            conn.disconnect();

            // json 파싱
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(sb.toString(), JsonObject.class);

            JsonObject wifiInfo = jsonObject.getAsJsonObject("TbPublicWifiInfo");
            JsonArray rows = wifiInfo.getAsJsonArray("row");

            if (i == 1) {
                rowCount = wifiInfo.get("list_total_count").getAsInt();
            }

            // db 연결

            try {
                DBConnector connector = new DBConnector();
                Connection connect = connector.connect();
                String query = "INSERT INTO wifi " +
                        "(X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1, " +
                        "X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, " +
                        "X_SWIFI_SVC_SE, X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, X_SWIFI_REMARS3, " +
                        "LAT, LNT, WORK_DTTM) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = connect.prepareStatement(query)) {
                    for (JsonElement row : rows) {
                        JsonObject rowObject = row.getAsJsonObject();
                        String mgrNo = rowObject.get("X_SWIFI_MGR_NO").getAsString();
                        String wrdofc = rowObject.get("X_SWIFI_WRDOFC").getAsString();
                        String mainNm = rowObject.get("X_SWIFI_MAIN_NM").getAsString();
                        String adres1 = rowObject.get("X_SWIFI_ADRES1").getAsString();
                        String adres2 = rowObject.get("X_SWIFI_ADRES2").getAsString();
                        String instlFloor = rowObject.get("X_SWIFI_INSTL_FLOOR").getAsString();
                        String instlTy = rowObject.get("X_SWIFI_INSTL_TY").getAsString();
                        String instlMby = rowObject.get("X_SWIFI_INSTL_MBY").getAsString();
                        String svcSe = rowObject.get("X_SWIFI_SVC_SE").getAsString();
                        String cmcwr = rowObject.get("X_SWIFI_CMCWR").getAsString();
                        String cnstcYear = rowObject.get("X_SWIFI_CNSTC_YEAR").getAsString();
                        String door = rowObject.get("X_SWIFI_INOUT_DOOR").getAsString();
                        String remars3 = rowObject.get("X_SWIFI_REMARS3").getAsString();
                        String lat = rowObject.get("LAT").getAsString();
                        String lnt = rowObject.get("LNT").getAsString();
                        String dttm = rowObject.get("WORK_DTTM").getAsString();


                        pstmt.setString(1, mgrNo);
                        pstmt.setString(2, wrdofc);
                        pstmt.setString(3, mainNm);
                        pstmt.setString(4, adres1);
                        pstmt.setString(5, adres2);
                        pstmt.setString(6, instlFloor);
                        pstmt.setString(7, instlTy);
                        pstmt.setString(8, instlMby);
                        pstmt.setString(9, svcSe);
                        pstmt.setString(10, cmcwr);
                        pstmt.setString(11, cnstcYear);
                        pstmt.setString(12, door);
                        pstmt.setString(13, remars3);
                        pstmt.setString(14, lat);
                        pstmt.setString(15, lnt);
                        pstmt.setString(16, dttm);
                        pstmt.executeUpdate();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        req.setAttribute("rowCount", rowCount);
        req.getRequestDispatcher("/load-wifi.jsp").forward(req, resp);
        resp.sendRedirect("/load-wifi.jsp");
    }
}
