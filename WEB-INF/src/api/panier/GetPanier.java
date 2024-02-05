package api.panier;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import utils.Database;
import utils.SessionManager;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/getPanier")
public class GetPanier extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Items> panier = new ArrayList<>();

        if(req.getSession(false) != null){
            try ( Connection con = Database.getConnection("website")) {
                long userId = new SessionManager(req, resp).getUserId();
                Statement stmt = con.createStatement();
                String query = "SELECT name, image, color, Count(*) AS number, price, p.ino AS id FROM panier p INNER JOIN items i ON p.ino = i.ino WHERE uno = "+userId+" GROUP BY name, image, color, price, uno, p.ino;";
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()){
                    String name = rs.getString("name");
                    String image = rs.getString("image");
                    String color = rs.getString("color");
                    int number = rs.getInt("number");
                    double price = rs.getDouble("price");
                    int id = rs.getInt("id");
                    panier.add(new Items(name, image, color, number, price, id));
                }
            } catch (Exception e) {}

        }

        String json = new Gson().toJson(panier);
        resp.setContentType("application/json");
        resp.getWriter().write(json);
    }
}