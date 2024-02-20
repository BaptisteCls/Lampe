package api.items;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import utils.Database;
import utils.Test;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/getItems/*")
public class GetItems extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        Gson gson = new Gson();
        PrintWriter out = resp.getWriter();


        if (path == null || path.equals("/")) {
            List<Items> items = new ArrayList<>();

            try ( Connection con = Database.getConnection("website")) {

                Statement stmt = con.createStatement();
                String query = "SELECT * FROM items";
                ResultSet rs = stmt.executeQuery(query);

                while(rs.next()){
                    String name = rs.getString("name");
                    String image = rs.getString("image");
                    int id = rs.getInt("ino");
                    double price = rs.getDouble("price");

                    items.add(new Items(name, image, price, id));
                }
            } catch (Exception e) {}

            String json = gson.toJson(items);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            out.print(json);
            out.flush();
            out.close();
            
            return;
        }

        String[] splits = path.split("/");

        if(splits.length == 2){
            try( Connection con = Database.getConnection("website")) {
                int id = Integer.parseInt(splits[1]);
                String query = "SELECT * FROM items WHERE ino = ?";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                rs.next();
                String name = rs.getString("name");
                String image = rs.getString("image");
                double price = rs.getDouble("price");

                String json = gson.toJson(new Items(name, image, price, id));

                out.print(json);
                out.flush();
                out.close();
                
                return;
            } catch (Exception e) {}
        }

        if(splits.length == 3 && splits[2].equals("colors")){
            try(Connection con = Database.getConnection("website")) {
                List<String> result = new ArrayList<>();
                int id = Integer.parseInt(splits[1]);

                String query = "SELECT * FROM colors WHERE ino = ?";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                while(rs.next()){
                    String color = rs.getString("color");
                    result.add(color);
                }

                String json = gson.toJson(result);
                out.print(json);

                out.flush();
                out.close();
                
                return;
            } catch (Exception e) {}
        }

        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        return;
    }
}