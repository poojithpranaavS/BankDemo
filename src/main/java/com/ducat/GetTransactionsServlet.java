package com.ducat;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@SuppressWarnings("serial")
@WebServlet("/GetTransactions")
public class GetTransactionsServlet extends HttpServlet {


@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");

    String type = req.getParameter("type");      
    String period = req.getParameter("period");  

    try (Connection con = DBConnection.getConnection()) {

      
        StringBuilder query = new StringBuilder("SELECT * FROM transactions WHERE 1=1");

        if (type != null && !type.equalsIgnoreCase("all")) {
            query.append(" AND type=?");
        }

        if (period != null && period.equalsIgnoreCase("today")) {
            query.append(" AND DATE(created_at)=CURDATE()");
        } else if (period != null && period.equalsIgnoreCase("month")) {
            query.append(" AND MONTH(created_at)=MONTH(CURDATE()) AND YEAR(created_at)=YEAR(CURDATE())");
        }

        query.append(" ORDER BY created_at DESC");

        PreparedStatement ps = con.prepareStatement(query.toString());

        int index = 1;

        if (type != null && !type.equalsIgnoreCase("all")) {
            ps.setString(index++, type.toUpperCase());
        }

        ResultSet rs = ps.executeQuery();

        PrintWriter out = resp.getWriter();

        out.print("[");
        boolean first = true;

        while (rs.next()) {

        	
        	if (!first) out.print(",");
        	first = false;

        	out.print("{");
        	out.print("\"dateTime\":\"" + rs.getString("created_at") + "\",");
        	out.print("\"description\":\"" + rs.getString("description") + "\",");
        	out.print("\"type\":\"" + rs.getString("type") + "\",");
        	out.print("\"amount\":\"" + rs.getDouble("amount") + "\",");
        	out.print("\"balanceAfter\":\"" + rs.getDouble("balance_after") + "\",");
        	out.print("\"status\":\"Success\"");
        	out.print("}");
        	

        	}


        out.print("]");

    } catch (Exception e) {
        e.printStackTrace();
        resp.setStatus(500);
    }
}


}
