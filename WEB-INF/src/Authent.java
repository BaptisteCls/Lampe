import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import utils.Database;
import utils.SessionManager;

//@WebServlet("/authent")
public class Authent extends HttpServlet{
  public void doPost( HttpServletRequest req, HttpServletResponse res ) 
  throws ServletException, IOException{
    try {
      Connection connection = Database.getConnection("users");
      String query = "Select email from users where email = ? and password = MD5(?)";
      PreparedStatement psStmt = connection.prepareStatement(query);

      String login = req.getParameter("login");
      String password = req.getParameter("password");

      psStmt.setString(1, login);
      psStmt.setString(2, password);

      ResultSet rs = psStmt.executeQuery();
      connection.close();

      if(!rs.next()){
        res.sendRedirect("login?e=t");
      }
      else{
        SessionManager sessions = new SessionManager(req, res);
        sessions.setAttribute("login", login);

        String from = req.getParameter("from");
        res.sendRedirect(from);
      }

    } catch (Exception e) {}
  }
}