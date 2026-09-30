package com.ducat;

import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@SuppressWarnings("serial")
@WebServlet("/changePassword")
public class ChangePasswordServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String oldPass = req.getParameter("oldPassword");
		String newPass = req.getParameter("newPassword");
		String confirmPass = req.getParameter("confirmPassword");

		HttpSession session = req.getSession(false);

		if (session == null || session.getAttribute("username") == null) {
			resp.sendRedirect("login.html");
			return;
		}

		String username = (String) session.getAttribute("username");

		if (!newPass.equals(confirmPass)) {
			resp.getWriter().println("New password and confirm password don't match.");
			return;
		}

		try (Connection con = DBConnection.getConnection()) {

			String dbPassword = null;
			try (PreparedStatement ps = con.prepareStatement("SELECT password FROM usertable WHERE username=?")) {
				ps.setString(1, username);
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						dbPassword = rs.getString("password");
					}
				}
			}

			if (dbPassword != null && oldPass.equals(dbPassword)) {

				try (PreparedStatement ps = con.prepareStatement("UPDATE usertable SET password=? WHERE username=?")) {
					ps.setString(1, newPass);
					ps.setString(2, username);
					int rowsAffected = ps.executeUpdate();

					if (rowsAffected > 0) {
						resp.getWriter().println("Password updated successfully!");
					}
				}
			} else {
				resp.getWriter().println("Incorrect old password.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
