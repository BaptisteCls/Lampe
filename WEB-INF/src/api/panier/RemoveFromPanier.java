package api.panier;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.Database;
import utils.SessionManager;

@WebServlet("/removeFromPanier")
public class RemoveFromPanier extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession(false) != null){
            try ( Connection con = Database.getConnection("website")) {
                long userId = new SessionManager(req, resp).getUserId();
                int itemId = Integer.parseInt(req.getParameter("item"));
                String color = req.getParameter("color");

                String update = "DELETE FROM panier WHERE pno IN (SELECT pno FROM panier WHERE uno = ? AND ino = ? AND color = ? LIMIT 1)";
                PreparedStatement stmt = con.prepareStatement(update);

                stmt.setLong(1, userId);
                stmt.setInt(2, itemId);
                stmt.setString(3, color);

                resp.getWriter().write("" +stmt.executeUpdate());
            } catch (Exception e) {}

        }
    }
}
