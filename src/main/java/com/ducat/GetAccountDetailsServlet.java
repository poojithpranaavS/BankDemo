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
@WebServlet("/GetAccountDetails")
public class GetAccountDetailsServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		resp.setContentType("application/json");

		String accountNumber = req.getParameter("accountNumber");

		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement("SELECT u.fullname, a.balance FROM usertable u "
						+ "JOIN account a ON u.accountnumber=a.accountnumber " + "WHERE u.accountnumber=?");
				PrintWriter out = resp.getWriter();) {
			ps.setString(1, accountNumber);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				out.print("{");
				out.print("\"fullName\":\"" + rs.getString("fullname") + "\",");
				out.print("\"balance\":\"" + rs.getDouble("balance") + "\"");
				out.print("}");

			} else {
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
