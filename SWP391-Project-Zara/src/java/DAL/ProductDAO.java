package DAL;

import Models.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;

public class ProductDAO {

    private Connection con = null;
    private String status = "OK";
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private List<Product> listProduct;
    public static ProductDAO INSTANCE = new ProductDAO();

    public List<Product> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<Product> listProduct) {
        this.listProduct = listProduct;
    }

    public ProductDAO() {
        if (INSTANCE == null) {
            try {
                con = new DBContext().getConnection();
            } catch (Exception e) {
                status = "Error at connection " + e.getMessage();
            }
        }
    }

    public void Search(String sql, String txt, int size) {
        listProduct = new Vector<Product>();
        try {
            ps = con.prepareStatement(sql);
            for (int i = 1; i <= size; i++) {
                ps.setString(i, txt);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                listProduct.add(new Product(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getFloat(8),
                        rs.getString(9)
                ));

            }
        } catch (Exception e) {
        }

    }

    public List<Product> getAllProduct() {
        listProduct = new Vector<Product>();
        String sql = "WITH RankedProducts AS (\n"
                + "    SELECT P.id, P.product_info_id, P.size,  P.color, P.name, P.quantity, P.description, PI.price, PI.img_default,\n"
                + "		ROW_NUMBER() OVER (PARTITION BY P.product_info_id ORDER BY P.id) AS rn\n"
                + "    FROM  Product P\n"
                + "    JOIN ProductInfor PI ON P.product_info_id = PI.id\n"
                + "    JOIN Style S ON PI.style_id = S.id\n"
                + "    JOIN Category C ON S.cate_id = C.id\n"
                + ")\n"
                + "SELECT id, product_info_id, size, color, name, quantity, description, price, img_default\n"
                + "FROM RankedProducts\n"
                + "WHERE rn = 1\n"
                + "ORDER BY id DESC;";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                listProduct.add(new Product(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getFloat(8),
                        rs.getString(9)
                ));
            }
        } catch (Exception e) {
            status = "Error at read Student " + e.getMessage();
        }
        return listProduct;
    }

    public List<Product> getProductByCid(String cid) {
        listProduct = new Vector<Product>();
        String sql = "WITH RankedProducts AS (\n"
                + "    SELECT P.id, P.product_info_id, P.size,  P.color, P.name AS product_name, P.quantity, P.description, PI.price, PI.img_default,\n"
                + "    ROW_NUMBER() OVER (PARTITION BY P.product_info_id ORDER BY P.id) AS rn\n"
                + "    FROM  Product P\n"
                + "    JOIN ProductInfor PI ON P.product_info_id = PI.id\n"
                + "    JOIN Style S ON PI.style_id = S.id\n"
                + "    JOIN Category C ON S.cate_id = C.id\n"
                + "    WHERE C.id = ? \n"
                + ")\n"
                + "SELECT id, product_info_id, size, color, product_name, quantity, description, price, img_default\n"
                + "FROM RankedProducts\n"
                + "WHERE rn = 1;";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, cid);
            rs = ps.executeQuery();
            while (rs.next()) {
                listProduct.add(new Product(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getFloat(8),
                        rs.getString(9)
                ));
            }
        } catch (Exception e) {
            status = "Error at read Student " + e.getMessage();
        }
        return listProduct;
    }

    public List<Product> getTop6NewArrival() {
        listProduct = new Vector<Product>();
        String sql = "WITH RankedProducts AS (\n"
                + "    SELECT P.id, P.product_info_id, P.size,  P.color, P.name, P.quantity, P.description, PI.price, PI.img_default,\n"
                + "		ROW_NUMBER() OVER (PARTITION BY P.product_info_id ORDER BY P.id) AS rn\n"
                + "    FROM  Product P\n"
                + "    JOIN ProductInfor PI ON P.product_info_id = PI.id\n"
                + "    JOIN Style S ON PI.style_id = S.id\n"
                + "    JOIN Category C ON S.cate_id = C.id\n"
                + ")\n"
                + "SELECT top 6 id, product_info_id, size, color, name, quantity, description, price, img_default\n"
                + "FROM RankedProducts\n"
                + "WHERE rn = 1\n"
                + "ORDER BY id DESC;";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                listProduct.add(new Product(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getFloat(8),
                        rs.getString(9)
                ));
            }
        } catch (Exception e) {
            status = "Error at read Student " + e.getMessage();
        }
        return listProduct;
    }

}
