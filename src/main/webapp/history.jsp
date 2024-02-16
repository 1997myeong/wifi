<%@ page import="project.wifi.dto.LocationHistory" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: mj908
  Date: 2024-02-16
  Time: 오전 9:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>위치 히스토리 목록</title>
</head>
<body>
<table border="1">
  <tr>
    <th>ID</th>
    <th>X 좌표</th>
    <th>Y 좌표</th>
    <th>조회일자</th>
    <th>비고</th>
  </tr>
  <!-- wifiList 표에 출력 -->
    <%
            List<LocationHistory> locationHistoryList = (List<LocationHistory>) request.getAttribute("historyList");

            if (locationHistoryList != null) {
                for (LocationHistory dto : locationHistoryList) {

                %>
  <tr>
    <td><%= dto.getId() %></td>
    <td><%= dto.getLAT() %></td>
    <td><%= dto.getLNT() %></td>
    <td><%= dto.getSearchDate() %></td>
    <td><button onclick="deleteDate('<%=dto.getId()%>')">삭제</button></td>

  </tr>

<%
      }
    }%>
  <script>
    function deleteDate(id) {
      var form = document.createElement('form');
      form.method = 'post';
      form.action = '/deleteHistoryServlet';

      var input = document.createElement('input')
      input.type = 'hidden';
      input.name = 'id';
      input.value = id;
      form.appendChild(input);

      document.body.appendChild(form);
      form.submit();

    }
  </script>


</table>
</body>
</html>
