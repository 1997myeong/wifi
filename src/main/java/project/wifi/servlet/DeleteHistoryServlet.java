package project.wifi.servlet;


import project.wifi.dao.LocationHistoryDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "deleteHistoryServlet", value = "/deleteHistoryServlet")
public class DeleteHistoryServlet extends HttpServlet {


    private final LocationHistoryDao locationHistoryDao = new LocationHistoryDao();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        locationHistoryDao.deleteId(Long.valueOf(req.getParameter("id")));



        resp.sendRedirect("/history");
    }
}
