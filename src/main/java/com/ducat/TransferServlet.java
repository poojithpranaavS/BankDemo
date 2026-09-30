package com.ducat;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@SuppressWarnings("serial")
@WebServlet("/transfer")
public class TransferServlet extends HttpServlet {


@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    HttpSession session = req.getSession(false);
    if (session == null || session.getAttribute("username") == null) {
        resp.sendRedirect("login.html");
        return;
    }

    String username = (String) session.getAttribute("username");

    String receiver = req.getParameter("recipientAccount");
    BigDecimal amount = new BigDecimal(req.getParameter("amount"));

    Connection con = null;

    try {
        con = DBConnection.getConnection();
        con.setAutoCommit(false);

        PreparedStatement ps = con.prepareStatement(
                "SELECT a.accountnumber, a.balance FROM account a JOIN usertable u ON a.accountnumber=u.accountnumber WHERE u.username=?");
        ps.setString(1, username);

        ResultSet rs = ps.executeQuery();

        if (!rs.next()) {
            resp.getWriter().println("Sender not found");
            con.rollback();
            return;
        }

        String senderAcc = rs.getString("accountnumber");

        PreparedStatement debit = con.prepareStatement(
                "UPDATE account SET balance=balance-? WHERE accountnumber=?");
        debit.setBigDecimal(1, amount);
        debit.setString(2, senderAcc);
        debit.executeUpdate();

        PreparedStatement credit = con.prepareStatement(
                "UPDATE account SET balance=balance+? WHERE accountnumber=?");
        credit.setBigDecimal(1, amount);
        credit.setString(2, receiver);
        credit.executeUpdate();

       PreparedStatement sBal = con.prepareStatement(
                "SELECT balance FROM account WHERE accountnumber=?");
        sBal.setString(1, senderAcc);
        ResultSet rs1 = sBal.executeQuery();

        BigDecimal senderBalance = BigDecimal.ZERO;
        if (rs1.next()) {
            senderBalance = rs1.getBigDecimal("balance");
        }

        PreparedStatement rBal = con.prepareStatement(
                "SELECT balance FROM account WHERE accountnumber=?");
        rBal.setString(1, receiver);
        ResultSet rs2 = rBal.executeQuery();

        BigDecimal receiverBalance = BigDecimal.ZERO;
        if (rs2.next()) {
            receiverBalance = rs2.getBigDecimal("balance");
        }

        PreparedStatement log1 = con.prepareStatement(
                "INSERT INTO transactions(accountnumber,description,amount,type,reference_account,balance_after) VALUES(?,?,?,?,?,?)");

        log1.setString(1, senderAcc);
        log1.setString(2, "Transfer to " + receiver);
        log1.setBigDecimal(3, amount);
        log1.setString(4, "DEBIT");
        log1.setString(5, receiver);
        log1.setBigDecimal(6, senderBalance);
        log1.executeUpdate();

        PreparedStatement log2 = con.prepareStatement(
                "INSERT INTO transactions(accountnumber,description,amount,type,reference_account,balance_after) VALUES(?,?,?,?,?,?)");

        log2.setString(1, receiver);
        log2.setString(2, "Received from " + senderAcc);
        log2.setBigDecimal(3, amount);
        log2.setString(4, "CREDIT");
        log2.setString(5, senderAcc);
        log2.setBigDecimal(6, receiverBalance);
        log2.executeUpdate();

        con.commit();

        resp.sendRedirect("dashboard.html");

    } catch (Exception e) {

        try {
            if (con != null) con.rollback();  
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        e.printStackTrace();
    }
}


}
