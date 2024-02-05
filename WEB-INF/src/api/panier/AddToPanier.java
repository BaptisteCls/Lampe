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
import utils.Test;


@WebServlet("/addToPanier")
public class AddToPanier extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Test test = new Test(resp.getWriter());

        try ( Connection con = Database.getConnection("website")) {
            long userId = new SessionManager(req, resp).getUserId();
            int itemId = Integer.parseInt(req.getParameter("item"));
            String color = req.getParameter("color");

            String update = "INSERT INTO panier VALUES(?,?,?)";
            PreparedStatement stmt = con.prepareStatement(update);

            stmt.setLong(1, userId);
            stmt.setInt(2, itemId);
            stmt.setString(3, color);

            resp.getWriter().write("" +stmt.executeUpdate());
        } catch (Exception e) {}
    }
}
