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
@WebServlet("/getUsers")
public class GetUsersServlet extends HttpServlet {

 @Override
protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws IOException {

  resp.setContentType("application/json");

  try(Connection con=DBConnection.getConnection()){

   PreparedStatement ps=con.prepareStatement(
   "SELECT u.accountnumber,u.fullname,u.email,u.phone,u.accountType,a.balance "+
   "FROM usertable u JOIN account a ON u.accountnumber=a.accountnumber");

   ResultSet rs=ps.executeQuery();

   PrintWriter out=resp.getWriter();

   out.print("[");

   boolean first=true;

   while(rs.next()){

    if(!first) {
		out.print(",");
	}
    first=false;

    out.print("{");
    out.print("\"accountNumber\":\""+rs.getString("accountnumber")+"\",");
    out.print("\"fullName\":\""+rs.getString("fullname")+"\",");
    out.print("\"email\":\""+rs.getString("email")+"\",");
    out.print("\"phone\":\""+rs.getString("phone")+"\",");
    out.print("\"balance\":\""+rs.getDouble("balance")+"\",");
    out.print("\"accountType\":\""+rs.getString("accountType")+"\"");
    out.print("}");
   }

   out.print("]");

  }catch(Exception e){
   e.printStackTrace();
  }
 }
}