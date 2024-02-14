<%--
  Created by IntelliJ IDEA.
  User: mj908
  Date: 2024-02-13
  Time: 오후 3:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>WIFI 로딩 완료 페이지</title>
</head>
<body>

    <%
    int result = (int) request.getAttribute("rowCount");

    if (result < 100) { %>

        <h1>WIFI를 불러오지 못했습니다.</h1>

    <%} else { %>

        <h1><%=result%>개의 WIFI 정보를 정상적으로 저장하였습니다.</h1>
    <%}%>

        <br>
        <a href="index.jsp">홈 으로 가기</a>


</body>
</html>
