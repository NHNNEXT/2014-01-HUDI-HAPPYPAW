package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DAO;
import model.StampHistory;

public class NyamHistory extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DAO db = DAO.getInstance();
		ArrayList<StampHistory> stampList=db.selectMonthHistory("121001");
		request.setAttribute("record", stampList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("nyamHistory.jsp");
		dispatcher.forward(request, response);
		
	}
}
