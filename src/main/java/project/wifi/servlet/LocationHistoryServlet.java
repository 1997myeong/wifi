package project.wifi.servlet;


import project.wifi.dao.LocationHistoryDao;
import project.wifi.dto.LocationHistory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "locationHistoryServlet", value = "/history")
public class LocationHistoryServlet extends HttpServlet {

    private final LocationHistoryDao locationHistoryDao = new LocationHistoryDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<LocationHistory> histories = locationHistoryDao.getAllLocation();

        req.setAttribute("historyList", histories);

        req.getRequestDispatcher("/history.jsp").forward(req, resp);
    }
}
