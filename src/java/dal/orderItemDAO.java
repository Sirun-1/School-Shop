package dal;
import java.util.Vector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.orderItem;

public class orderItemDAO extends DBContext {

    // Get all items belonging to one order
    public Vector<orderItem> getItemsByOrderID(int orderID) {
        Vector<orderItem> vector = new Vector<>();
        String sql = "SELECT * FROM [dbo].[order_items] WHERE order_id = ?";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setInt(1, orderID);
            ResultSet rs = ptm.executeQuery();
            while (rs.next()) {
                orderItem item = new orderItem(
                    rs.getInt(1),   
                    rs.getInt(2),  
                    rs.getInt(3),   
                    rs.getInt(4),   
                    rs.getDouble(5)
                );
                vector.add(item);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vector;
    }

    public orderItem searchOrderItem(int itemID) {
        String sql = "SELECT * FROM [dbo].[order_items] WHERE item_id = ?";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setInt(1, itemID);
            ResultSet rs = ptm.executeQuery();
            if (rs.next()) {
                return new orderItem(
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getInt(3),
                    rs.getInt(4),
                    rs.getDouble(5)
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public int insertOrderItem(orderItem item) {
        int n = 0;
        String sql = "INSERT INTO [dbo].[order_items] " +
                     "([order_id], [product_id], [quantity], [unit_price]) " +
                     "VALUES (?,?,?,?)";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setInt(1, item.getOrderID());
            ptm.setInt(2, item.getProductID());
            ptm.setInt(3, item.getQuantity());
            ptm.setDouble(4, item.getPrice());
            n = ptm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public int deleteOrderItem(int itemID) {
        int n = 0;
        String sql = "DELETE FROM [dbo].[order_items] WHERE item_id = ?";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setInt(1, itemID);
            n = ptm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }
    public int deleteItemsByOrderID(int orderID) {
        int n = 0;
        String sql = "DELETE FROM [dbo].[order_items] WHERE order_id = ?";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setInt(1, orderID);
            n = ptm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }
    public int updateQuantity(int itemID, int quantity) {
    String sql = "UPDATE [dbo].[order_items] SET quantity = ? WHERE item_id = ?";
    try {
        PreparedStatement ptm = connection.prepareStatement(sql);
        ptm.setInt(1, quantity);
        ptm.setInt(2, itemID);
        return ptm.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
        }
        return 0;
    }
    public orderItem getItemByOrderAndProduct(int orderID, int productID) {
    String sql = "SELECT * FROM [dbo].[order_items] WHERE order_id = ? AND product_id = ?";
    try {
        PreparedStatement ptm = connection.prepareStatement(sql);
        ptm.setInt(1, orderID);
        ptm.setInt(2, productID);
        ResultSet rs = ptm.executeQuery();
        if (rs.next()) {
            return new orderItem(rs.getInt(1), rs.getInt(2), rs.getInt(3),
                                rs.getInt(4), rs.getDouble(5));
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
    return null;
    }
}