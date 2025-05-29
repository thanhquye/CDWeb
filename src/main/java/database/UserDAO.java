package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ShowTime;
import model.User;

public class UserDAO {
    public boolean checkEmailExits(String email) {
        Connection connection = null;
        boolean checkEmail = false;
        try {
            connection = JDBCUtil.getConnection();
            String checkEmailQuery = "select email from userlogin where email = ?";
            PreparedStatement pr = connection.prepareStatement(checkEmailQuery);
            pr.setString(1, email);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                checkEmail = true;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.closeConnection(connection);
        }
        return checkEmail;
    }


    public User getUserbyEmailAndPassword(String email, String password) {
        Connection connection = null;
        try {
            connection = JDBCUtil.getConnection();
            String query = "select * from userlogin where email = ? and userPassword = ?";
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setString(1, email);
            pr.setString(2, password);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getString("userId"));
                user.setUserName(rs.getString("userName"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("userPassword"));
                user.setActive(rs.getBoolean("isActive"));
                user.setAdmin(rs.getBoolean("isAdmin"));
                user.setVerifyEmail(rs.getBoolean("isVerifyEmail"));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.closeConnection(connection);
        }
        return null;
    }


//    public boolean registerUser(String userName, String email, String password) {
//        Connection connection = null;
//        int maxID = 0;
//        try {
//            connection = JDBCUtil.getConnection();
//            String maxIdQuery = "select userID from userlogin";
//            PreparedStatement idPr = connection.prepareStatement(maxIdQuery);
//            ResultSet rsId = idPr.executeQuery();
//            while (rsId.next()) {
//                String id = rsId.getString("userID").substring(4);
//                int intID = Integer.parseInt(id);
//                if (intID > maxID) {
//                    maxID = intID;
//                }
//            }
//            try {
//                String insertQuery = "insert into userlogin values (?, ?, ? , ?, ?,?) ";
//                PreparedStatement insertPr = connection.prepareStatement(insertQuery);
//                insertPr.setString(1, "user" + (maxID + 1));
//                insertPr.setString(2, userName);
//                insertPr.setString(3, email);
//                insertPr.setString(4, password);
//                insertPr.setInt(5, 1);
//                insertPr.setInt(6, 0);
//                int insertRs = insertPr.executeUpdate();
//                if (insertRs > 0) {
//                    return true;
//                } else {
//                    return false;
//                }
//
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            JDBCUtil.closeConnection(connection);
//        }
//
//    }

    // Register
    public boolean registerUser(String userName, String email, String password) {
        Connection connection = null;
        int maxID = 0;

        try {
            connection = JDBCUtil.getConnection();

            // Lấy userID lớn nhất
            String maxIdQuery = "SELECT userID FROM userlogin";
            PreparedStatement idPr = connection.prepareStatement(maxIdQuery);
            ResultSet rsId = idPr.executeQuery();
            while (rsId.next()) {
                String id = rsId.getString("userID").substring(4); // giả sử ID dạng "user001"
                int intID = Integer.parseInt(id);
                if (intID > maxID) {
                    maxID = intID;
                }
            }

            // Tạo ID mới
            String newUserID = "user" + String.format("%03d", maxID + 1);

            // Chèn người dùng mới
            String insertQuery = "INSERT INTO userlogin (userID, userName, email, userPassword, isActive, isAdmin, isVerifyEmail) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertPr = connection.prepareStatement(insertQuery);
            insertPr.setString(1, newUserID);
            insertPr.setString(2, userName);
            insertPr.setString(3, email);
            insertPr.setString(4, password);
            insertPr.setInt(5, 1);  // isActive
            insertPr.setInt(6, 0);  // isAdmin
            insertPr.setInt(7, 0);  // isVerifyEmail (chưa xác thực)

            int insertRs = insertPr.executeUpdate();
            return insertRs > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.closeConnection(connection);
        }
    }

    public ArrayList<User> getAllUser() {
        Connection connection = null;
        ArrayList<User> list = new ArrayList<>();
        ArrayList<User> userList = new ArrayList<>();
        try {
            connection = JDBCUtil.getConnection();
            String query = "select * from userlogin";
            PreparedStatement pr = connection.prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getString("userId"));
                user.setUserName(rs.getString("userName"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("userPassword"));
                user.setActive(rs.getBoolean("isActive"));
                user.setAdmin(rs.getBoolean("isAdmin"));
                list.add(user);
            }
            for (User user : list) {
                if (!user.isAdmin()) {
                    userList.add(user);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.closeConnection(connection);
        }
        return userList;
    }

    public ArrayList<User> getAllAdimin() {
        Connection connection = null;
        ArrayList<User> user = new ArrayList<>();
        ArrayList<User> listAdmin = new ArrayList<>();
        try {
            connection = JDBCUtil.getConnection();
            String query = "select * from userlogin";
            PreparedStatement pr = connection.prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                User us = new User();
                us.setUserId(rs.getString("userId"));
                us.setUserName(rs.getString("userName"));
                us.setEmail(rs.getString("email"));
                us.setPassword(rs.getString("password"));
                us.setActive(rs.getBoolean("isActive"));
                us.setAdmin(rs.getBoolean("isAdmin"));
                user.add(us);
            }
            for (User user1 : user) {
                if (user1.isAdmin()) {
                    listAdmin.add(user1);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException();
        } finally {
            JDBCUtil.closeConnection(connection);
        }
        return listAdmin;
    }

    public boolean deleteUser(String id) {
        Connection connection = null;
        boolean checkEmail = false;
        try {
            connection = JDBCUtil.getConnection();
            String checkEmailQuery = "delete from userlogin where userID = ?";
            PreparedStatement pr = connection.prepareStatement(checkEmailQuery);
            pr.setString(1, id);
            int rs = pr.executeUpdate();
            if (rs > 0) {
                checkEmail = true;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.closeConnection(connection);
        }
        return checkEmail;
    }

    public boolean blockUser(String id, String active) {
        Connection connection = null;
        boolean checkEmail = false;
        try {
            connection = JDBCUtil.getConnection();
            String checkEmailQuery = "UPDATE userlogin set isActive = ? where userID = ?";
            PreparedStatement pr = connection.prepareStatement(checkEmailQuery);
            pr.setString(1, active);
            pr.setString(2, id);
            int rs = pr.executeUpdate();
            if (rs > 0) {
                checkEmail = true;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.closeConnection(connection);
        }
        return checkEmail;
    }


    public ArrayList<User> getAllUserByName(String name) {
        Connection connection = null;
        ArrayList<User> list = new ArrayList<>();
        ArrayList<User> userList = new ArrayList<>();
        try {
            connection = JDBCUtil.getConnection();
            String query = "select * from userlogin where userName = ?";
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setString(1, name);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getString("userId"));
                user.setUserName(rs.getString("userName"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("userPassword"));
                user.setActive(rs.getBoolean("isActive"));
                user.setAdmin(rs.getBoolean("isAdmin"));
                list.add(user);
            }
            for (User user : list) {
                if (!user.isAdmin()) {
                    userList.add(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.closeConnection(connection);
        }
        return userList;
    }

    public static User getUSerById(String userID) {
        Connection c = JDBCUtil.getConnection();
        String sql = "SELECT u.* FROM customer cu JOIN userlogin u ON cu.userID = u.userID WHERE u.userID = ?";
        try {
            PreparedStatement s = c.prepareStatement(sql);
            s.setString(1, userID);
            ResultSet rs = s.executeQuery();
            User us = new User();
            while (rs.next()) {
                us.setUserId(rs.getString("userId"));
                us.setUserName(rs.getString("userName"));
                us.setEmail(rs.getString("email"));
                us.setPassword(rs.getString("password"));
                us.setActive(rs.getBoolean("isActive"));
                us.setAdmin(rs.getBoolean("isAdmin"));
            }
            return us;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean updateUser(User user) {
        Connection c = JDBCUtil.getConnection();
        String update = " UPDATE userlogin ";
        String set = " SET userName = ? , email = ? , userPassword = ?";
        String where = " WHERE userID = ? ";
        String sql = update + set + where;
        try {
            PreparedStatement s = c.prepareStatement(sql);
            s.setString(1, user.getUserName());
            s.setString(2, user.getEmail());
            s.setString(3, user.getPassword());
            s.setString(4, user.getUserId());
            int rs = s.executeUpdate();
            return (rs > 0) ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        ArrayList<User> list = userDAO.getAllUserByName("vansang");
        for (User user : list) {
            System.out.println(user);
        }

//        System.out.println(userDAO.deleteUser("user6"));
//        System.out.println(userDAO.blockUser("user7", "false"));

//        System.out.println(userDAO.registerUser("vansang", "nguyenvansang1@email.com", "vansang"));

    }

    /**
     * Cập nhật mật khẩu mới dựa vào email của người dùng.
     *
     * @param email       Email của người dùng cần cập nhật mật khẩu.
     * @param newPassword Mật khẩu mới cần cập nhật.
     * @return true nếu cập nhật thành công, false nếu thất bại.
     */
    public boolean updatePasswordByEmail(String email, String newPassword) {
        Connection connection = null;
        try {
            // Lấy kết nối tới database thông qua JDBCUtil
            connection = JDBCUtil.getConnection();

            // Câu lệnh SQL: cập nhật mật khẩu theo email
            String sql = "UPDATE userlogin SET userPassword = ? WHERE email = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, newPassword);
            ps.setString(2, email);

            // Thực hiện update, nếu số dòng update > 0 tức là cập nhật thành công
            int rowsUpdated = ps.executeUpdate();
            return (rowsUpdated > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            JDBCUtil.closeConnection(connection);
        }
    }

    // kiểm tra xem gmail có tồn tại không
    // chức năng forgot password
    public boolean isEmailExists(String email) {
        // Khai báo kết nối và PreparedStatement
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM userlogin WHERE email = ?")) {

            // Set giá trị email vào câu truy vấn
            stmt.setString(1, email);

            // Thực thi câu lệnh truy vấn và lấy kết quả
            ResultSet rs = stmt.executeQuery();

            // Nếu có kết quả trả về, kiểm tra số lượng kết quả (nếu > 0, tức là email đã tồn tại)
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // Nếu không có kết quả hoặc có lỗi xảy ra, trả về false
    }

    //    xác thực gmail
    public void markEmailVerified(String email) {
        try (Connection conn = JDBCUtil.getConnection()) {
            String sql = "UPDATE userlogin SET isVerifyEmail = true WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi xác minh email: " + e.getMessage(), e);
        }
    }

    public User getUserByEmail(String email) {
        Connection connection = null;
        User user = null;

        try {
            connection = JDBCUtil.getConnection();
            String query = "SELECT * FROM userlogin WHERE email = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getString("userId"));
                user.setUserName(rs.getString("userName"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("userPassword"));
                user.setActive(rs.getBoolean("isActive"));
                user.setAdmin(rs.getBoolean("isAdmin"));
                user.setVerifyEmail(rs.getBoolean("isVerifyEmail"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi lấy user theo email: " + e.getMessage(), e);
        } finally {
            JDBCUtil.closeConnection(connection);
        }
        return user;
    }

    public boolean checkUsernameExist(String username) {
        Connection connection = null;
        boolean exists = false;

        try {
            connection = JDBCUtil.getConnection();
            String query = "SELECT 1 FROM userlogin WHERE userName = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                exists = true; // đã tồn tại
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi kiểm tra username: " + e.getMessage(), e);
        } finally {
            JDBCUtil.closeConnection(connection);
        }

        return exists;
    }

}