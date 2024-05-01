package dal;

import static constant.constant.RECORD_PER_PAGE;
import context.DBContext;
import entity.Category;
import entity.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class ProductDAO {

    private List<Product> listProduct;
    private List<Category> listCategory;
    private DBContext dbContext;

    public ProductDAO() {
        listProduct = new LinkedList<>();
        listCategory = new LinkedList<>();
        dbContext = DBContext.getInstance();
    }

    PreparedStatement ps;
    ResultSet rs;

    public int findTotalRecord() {
        String sql = "SELECT COUNT(id) FROM Product";
        try {
            ps = dbContext.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResultSetAndStatement(rs, ps);
        }
        return -1;
    }

    public List<Product> findByPage(int page) {
        String sql = "SELECT p.id, p.product_name, p.product_prices, p.product_details, p.quantity, p.category_id, u.fullname, p.created_date, p.update_date FROM Product p "
                + "JOIN User u ON p.created_by_userID = u.id "
                + "ORDER BY p.id LIMIT ?, ?";
        try {
            ps = dbContext.getConnection().prepareStatement(sql);
            ps.setInt(1, (page - 1) * RECORD_PER_PAGE);
            ps.setInt(2, RECORD_PER_PAGE);
            rs = ps.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt(1);
                String productName = rs.getString(2);
                int productPrice = rs.getInt(3);
                String productDetails = rs.getString(4);
                int productQuantity = rs.getInt(5);
                int productCategoryid = rs.getInt(6);
                String created_by_userID = rs.getString(7);
                Timestamp date_created = rs.getTimestamp(8);
                Timestamp date_updated = rs.getTimestamp(9);

                Product product = new Product();

                product.setId(productId);
                product.setCategory_id(productCategoryid);
                product.setProduct_name(productName);
                product.setProduct_details(productDetails);
                product.setProduct_prices(productPrice);
                product.setCreated_by_userID(created_by_userID);
                product.setCreated_date(date_created);
                product.setUpdate_date(date_updated);
                product.setQuantity(productQuantity);

                listProduct.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResultSetAndStatement(rs, ps);
        }

        return listProduct;
    }

    public int findTotalRecordByProperty(String product_id, String product_name, String price_from, String price_to) {
        String sql = null;

        if (product_id.isEmpty() && price_from.isEmpty() && price_to.isEmpty()) {
            sql = "SELECT COUNT(id) FROM Product WHERE product_name LIKE ?";
        } else {
            if (product_id.isEmpty()) {
                sql = "SELECT COUNT(id) FROM Product WHERE product_name LIKE ? AND product_prices BETWEEN ? AND ?";
            } else if (price_from.isEmpty() && price_to.isEmpty()) {
                sql = "SELECT COUNT(id) FROM Product WHERE id = ? AND product_name LIKE ?";
            } else if (!price_from.isEmpty() && !price_to.isEmpty()) {
                sql = "SELECT COUNT(id) FROM Product WHERE id = ? AND product_name LIKE ? AND product_prices BETWEEN ? AND ?";
            }
        }

        try {
            ps = dbContext.getConnection().prepareStatement(sql);
            if (product_id.isEmpty() && price_from.isEmpty() && price_to.isEmpty()) {
                ps.setString(1, "%" + product_name + "%");
            } else {
                if (product_id.isEmpty()) {
                    ps.setString(1, "%" + product_name + "%");
                    ps.setInt(2, Integer.parseInt(price_from));
                    ps.setInt(3, Integer.parseInt(price_to));
                } else if (price_from.isEmpty() && price_to.isEmpty()) {
                    ps.setInt(1, Integer.parseInt(product_id));
                    ps.setString(2, "%" + product_name + "%");
                } else if (!price_from.isEmpty() && !price_to.isEmpty()) {
                    ps.setInt(1, Integer.parseInt(product_id));
                    ps.setString(2, "%" + product_name + "%");
                    ps.setInt(3, Integer.parseInt(price_from));
                    ps.setInt(4, Integer.parseInt(price_to));
                }
            }

            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        } finally {
            // Close the resources (ps, rs) in a finally block
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeResultSetAndStatement(rs, ps);
            }
        }
        return -1;
    }

    public List<Product> findByPropertyAndPage(String product_id, String product_name, String price_from, String price_to, int page) {
        String sql = null;
        if (product_id.isEmpty() && price_from.isEmpty() && price_to.isEmpty()) {
            sql = "SELECT p.id, p.product_name, p.product_prices, p.product_details, p.quantity, p.category_id, u.fullname, p.created_date, p.update_date "
                    + "FROM Product p "
                    + "JOIN User u ON p.created_by_userID = u.id "
                    + "WHERE p.product_name LIKE ? "
                    + "ORDER BY p.id "
                    + "LIMIT ?, ?";
        } else {
            if (product_id.isEmpty()) {
                sql = "SELECT p.id, p.product_name, p.product_prices, p.product_details, p.quantity, p.category_id, u.fullname, p.created_date, p.update_date "
                        + "FROM Product p "
                        + "JOIN User u ON p.created_by_userID = u.id "
                        + "WHERE p.product_name LIKE ? AND p.product_prices BETWEEN ? AND ? "
                        + "ORDER BY p.id "
                        + "LIMIT ?, ?";
            } else if (price_from.isEmpty() && price_to.isEmpty()) {
                sql = "SELECT p.id, p.product_name, p.product_prices, p.product_details, p.quantity, p.category_id, u.fullname, p.created_date, p.update_date "
                        + "FROM Product p "
                        + "JOIN User u ON p.created_by_userID = u.id "
                        + "WHERE p.id = ? AND p.product_name LIKE ? "
                        + "ORDER BY p.id "
                        + "LIMIT ?, ?";
            } else if (!price_from.isEmpty() && !price_to.isEmpty()) {
                sql = "SELECT p.id, p.product_name, p.product_prices, p.product_details, p.quantity, p.category_id, u.fullname, p.created_date, p.update_date "
                        + "FROM Product p "
                        + "JOIN User u ON p.created_by_userID = u.id "
                        + "WHERE p.id = ? AND p.product_name LIKE ? AND p.product_prices BETWEEN ? AND ? "
                        + "ORDER BY p.id "
                        + "LIMIT ?, ?";
            }
        }

        try {
            ps = dbContext.getConnection().prepareStatement(sql);
            if (product_id.isEmpty() && price_from.isEmpty() && price_to.isEmpty()) {
                ps.setString(1, "%" + product_name + "%");
                ps.setInt(2, (page - 1) * RECORD_PER_PAGE);
                ps.setInt(3, RECORD_PER_PAGE);
            } else {
                if (product_id.isEmpty()) {
                    ps.setString(1, "%" + product_name + "%");
                    ps.setInt(2, Integer.parseInt(price_from));
                    ps.setInt(3, Integer.parseInt(price_to));
                    ps.setInt(4, (page - 1) * RECORD_PER_PAGE);
                    ps.setInt(5, RECORD_PER_PAGE);

                } else if (price_from.isEmpty() && price_to.isEmpty()) {
                    ps.setInt(1, Integer.parseInt(product_id));
                    ps.setString(2, "%" + product_name + "%");
                    ps.setInt(3, (page - 1) * RECORD_PER_PAGE);
                    ps.setInt(4, RECORD_PER_PAGE);

                } else if (!price_from.isEmpty() && !price_to.isEmpty()) {
                    ps.setInt(1, Integer.parseInt(product_id));
                    ps.setString(2, "%" + product_name + "%");
                    ps.setInt(3, Integer.parseInt(price_from));
                    ps.setInt(4, Integer.parseInt(price_to));
                    ps.setInt(5, (page - 1) * RECORD_PER_PAGE);
                    ps.setInt(6, RECORD_PER_PAGE);
                }
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt(1);
                String productName = rs.getString(2);
                int productPrice = rs.getInt(3);
                String productDetails = rs.getString(4);
                int productQuantity = rs.getInt(5);
                int productCategoryid = rs.getInt(6);
                String created_by_userID = rs.getString(7);
                Timestamp date_created = rs.getTimestamp(8);
                Timestamp date_updated = rs.getTimestamp(9);

                Product product = new Product();

                product.setId(productId);
                product.setCategory_id(productCategoryid);
                product.setProduct_name(productName);
                product.setProduct_details(productDetails);
                product.setProduct_prices(productPrice);
                product.setCreated_by_userID(created_by_userID);
                product.setCreated_date(date_created);
                product.setUpdate_date(date_updated);
                product.setQuantity(productQuantity);

                listProduct.add(product);
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        } finally {
            // Close the resources (ps, rs) in a finally block
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeResultSetAndStatement(rs, ps);
            }
        }
        return listProduct;
    }

    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();
        List<Product> list = productDAO.findByPropertyAndPage("1", "", "20000", "500000", 1);
        for (Product o : list) {
            System.out.println(o);
        }
    }

    public List<Category> findAllCategory() {
        String sql = "SELECT * FROM Category";
        try {
            ps = dbContext.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int categoryId = rs.getInt(1);
                String categoryName = rs.getString(2);

                Category category = new Category();

                category.setId(categoryId);
                category.setCategory_name(categoryName);

                listCategory.add(category);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResultSetAndStatement(rs, ps);
        }

        return listCategory;
    }

    public int findTotalRecordByCategoryId(int category_Id) {
        String sql = "SELECT COUNT(p.id) "
                + "FROM Product p "
                + "JOIN Category c ON p.category_id = c.id "
                + "WHERE c.id = ?";
        try {
            ps = dbContext.getConnection().prepareStatement(sql);
            ps.setInt(1, category_Id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            // Close the resources (ps, rs) in a finally block
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeResultSetAndStatement(rs, ps);
            }
        }
        return -1;
    }

    public List<Product> findByCategoryIdAndPage(int category_Id, int page) {
        String sql = "SELECT p.id, p.product_name, p.product_prices, p.product_details, p.quantity, p.category_id, u.fullname, p.created_date, p.update_date "
                + "FROM Product p "
                + "JOIN User u ON p.created_by_userID = u.id "
                + "JOIN Category c ON p.category_id = c.id "
                + "WHERE p.category_id = ? "
                + "ORDER BY p.id "
                + "LIMIT ?, ?";
        try {
            ps = dbContext.getConnection().prepareStatement(sql);
            ps.setInt(1, category_Id);
            ps.setInt(2, (page - 1) * RECORD_PER_PAGE);
            ps.setInt(3, RECORD_PER_PAGE);
            rs = ps.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt(1);
                String productName = rs.getString(2);
                int productPrice = rs.getInt(3);
                String productDetails = rs.getString(4);
                int productQuantity = rs.getInt(5);
                int productCategoryid = rs.getInt(6);
                String created_by_userID = rs.getString(7);
                Timestamp date_created = rs.getTimestamp(8);
                Timestamp date_updated = rs.getTimestamp(9);

                Product product = new Product();

                product.setId(productId);
                product.setCategory_id(productCategoryid);
                product.setProduct_name(productName);
                product.setProduct_details(productDetails);
                product.setProduct_prices(productPrice);
                product.setCreated_by_userID(created_by_userID);
                product.setCreated_date(date_created);
                product.setUpdate_date(date_updated);
                product.setQuantity(productQuantity);

                listProduct.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResultSetAndStatement(rs, ps);
        }

        return listProduct;
    }

    private void closeResultSetAndStatement(ResultSet rs, PreparedStatement ps) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                System.out.println(e + "ham mysaleorder");
            }
        }

    }

}
