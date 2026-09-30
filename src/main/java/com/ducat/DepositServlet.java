package com.ducat;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@SuppressWarnings("serial")
@WebServlet("/deposit")
public class DepositServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		Connection con = null;

		try {
			HttpSession session = req.getSession(false);
			if (session == null) {
				resp.sendRedirect("login.html");
				return;
			}

			String username = (String) session.getAttribute("username");
			double amount = Double.parseDouble(req.getParameter("amount"));

			con = DBConnection.getConnection();
			con.setAutoCommit(false);

			PreparedStatement accPs = con.prepareStatement("SELECT accountnumber FROM usertable WHERE username=?");
			accPs.setString(1, username);
			ResultSet accRs = accPs.executeQuery();

			String accountNumber = null;
			if (accRs.next()) {
				accountNumber = accRs.getString("accountnumber");
			}

			PreparedStatement ps = con.prepareStatement("UPDATE account SET balance=balance+? WHERE accountnumber=?");
			ps.setDouble(1, amount);
			ps.setString(2, accountNumber);
			ps.executeUpdate();

			PreparedStatement balPs = con.prepareStatement("SELECT balance FROM account WHERE accountnumber=?");
			balPs.setString(1, accountNumber);
			ResultSet balRs = balPs.executeQuery();

			double newBalance = 0;
			if (balRs.next()) {
				newBalance = balRs.getDouble("balance");
			}

			PreparedStatement log = con.prepareStatement(
					"INSERT INTO transactions(accountnumber,description,amount,type,reference_account,balance_after) VALUES(?,?,?,?,?,?)");

			log.setString(1, accountNumber);
			log.setString(2, "Deposit");
			log.setDouble(3, amount);
			log.setString(4, "CREDIT");
			log.setString(5, accountNumber);
			log.setDouble(6, newBalance);

			log.executeUpdate();

			con.commit();

			resp.sendRedirect("dashboard.html");

		} catch (Exception e) {

			try {
				if (con != null)
					con.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			e.printStackTrace();
		}
	}

}
