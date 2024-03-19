package api.items;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import utils.Database;
import utils.Test;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/getItems/*")
public class GetItems extends HttpServlet{
    private static DAOItemsDatabase dao;
    static{
        dao = new DAOItemsDatabase();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        Gson gson = new Gson();
        PrintWriter out = resp.getWriter();


        if (path == null || path.equals("/")) {
            
            List<Item> items = dao.getAll();
            String json = gson.toJson(items);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            out.print(json);
            
            return;
        }

        String[] splits = path.split("/");

        if(splits.length == 2){
            try {
                int id = Integer.parseInt(splits[1]);
                
                Item item = dao.getItem(id);

                String json = gson.toJson(item);

                out.print(json);
                
                return;
            } catch (Exception e) {}
        }

        if(splits.length == 3){

            if(splits[2].equals("colors")){
                try {
                    int id = Integer.parseInt(splits[1]);
                    
                    Item item = dao.getItem(id);

                    String json = gson.toJson(item.colors);

                    out.print(json);
                    
                    return;
                } catch (Exception e) {}
            }

            if(splits[2].equals("images")){
                try {
                    int id = Integer.parseInt(splits[1]);
                    
                    Item item = dao.getItem(id);

                    String json = gson.toJson(item.images);

                    out.print(json);
                    
                    return;
                } catch (Exception e) {}
            }
        }

        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        return;
    }
}