<%@ page import="java.util.List" %>
<%@ page import="project.wifi.dto.WifiDto" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>공공 WIFI 정보</title>
    <script>
        function getLocation() {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(showPosition);
            } else {
                alert("지원되지 않음");
            }
        }

        function showPosition(position) {
            var lat = position.coords.latitude;
            var lnt = position.coords.longitude;

            document.getElementById("lat").value = lat;
            document.getElementById("lnt").value = lnt;
        }
    </script>
</head>
<body>
    <h1>공공 와이파이 정보 구하기</h1>
    <br>
    <a href="/">홈</a>|
    <a href="/bookmark-group">위치 히스토리 목록</a>|
    <a href="/load">Open API 와이파이 정보 가져오기</a>
    <br>
    <br>
    <form id="location" method="post" action="/nearWifi">
        LAT:<input type="text" id="lat" name="lat" placeholder="0.0">,
        LNT:<input type="text" id="lnt" name="lnt" placeholder="0.0">
        <button type="button" onclick="getLocation()">내 위치 가져오기</button>
        <button type="submit">근처 WIFI 정보 보기</button>
    </form>

    <br>
    <br>

    <table border="1">
        <tr>
            <th>거리(Km)</th>
            <th>관리번호</th>
            <th>자치구</th>
            <th>와이파이명</th>
            <th>도로명주소</th>
            <th>상세주소</th>
            <th>설치위치(층)</th>
            <th>설치유형</th>
            <th>설치기관</th>
            <th>서비스구분</th>
            <th>망종류</th>
            <th>설치년도</th>
            <th>실내외구분</th>
            <th>WIFI 접속환경</th>
            <th>X 좌표</th>
            <th>Y 좌표</th>
            <th>작업일자</th>
        </tr>
        <!-- wifiList 표에 출력 -->
        <%
            List<WifiDto> wifiList = (List<WifiDto>) request.getAttribute("wifiList");

            if (wifiList != null) {
                for (WifiDto dto : wifiList) {

                %>
        <tr>
            <td><%= dto.getDistance() %></td>
            <td><%= dto.getX_SWIFI_MGR_NO() %></td>
            <td><%= dto.getX_SWIFI_WRDOFC() %></td>
            <td><%= dto.getX_SWIFI_MAIN_NM() %></td>
            <td><%= dto.getX_SWIFI_ADRES1() %></td>
            <td><%= dto.getX_SWIFI_ADRES2() %></td>
            <td><%= dto.getX_SWIFI_INSTL_FLOOR() %></td>
            <td><%= dto.getX_SWIFI_INSTL_TY() %></td>
            <td><%= dto.getX_SWIFI_INSTL_MBY() %></td>
            <td><%= dto.getX_SWIFI_SVC_SE() %></td>
            <td><%= dto.getX_SWIFI_CMCWR() %></td>
            <td><%= dto.getX_SWIFI_CNSTC_YEAR() %></td>
            <td><%= dto.getX_SWIFI_INOUT_DOOR() %></td>
            <td><%= dto.getX_SWIFI_REMARS3() %></td>
            <td><%= dto.getLAT() %></td>
            <td><%= dto.getLNT() %></td>
            <td><%= dto.getWORK_DTTM() %></td>
        </tr>

        <%
                }
            } else {%>
        <tr>
            <td colspan="17" class="center">위치 정보를 입력한 후에 조회해 주세요.</td>
        </tr>
                <%
                }
        %>

    </table>
</body>
</html>