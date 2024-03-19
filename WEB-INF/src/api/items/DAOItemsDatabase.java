package api.items;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import utils.Database;

public class DAOItemsDatabase {
    public static Item getItem(int id){
        try (Connection con = Database.getConnection("website")) {
            String query = "SELECT * FROM items WHERE ino = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            rs.next();
            String name = rs.getString("name");
            double price = rs.getDouble("price");

            Item result = new Item(name, price, id);

            query = "Select color from color where item = ?";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while(rs.next()){
                result.addAColor(rs.getString(1));
            }

            query = "Select image from images where item = ?";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while(rs.next()){
                result.addAnImage(rs.getString(1));
            }

            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public static List<Item> getAll(){
        List<Item> result = new ArrayList<>();
        try (Connection con = Database.getConnection("website")) {
            
            String query = "Select ino from items";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Item actual = getItem(rs.getInt(1));
                result.add(actual);
            }

        } catch (Exception e) {}
        return result;
    }
}
