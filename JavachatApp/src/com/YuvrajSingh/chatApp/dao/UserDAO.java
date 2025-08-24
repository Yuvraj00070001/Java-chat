package com.YuvrajSingh.chatApp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.YuvrajSingh.chatApp.dto.UserDTO;
import com.YuvrajSingh.chatApp.utils.Encryption;

public class UserDAO {

    public boolean isLogin(UserDTO userDTO) throws SQLException, ClassNotFoundException {
        final String SQL = "SELECT userid FROM users WHERE userid = ? AND password = ?";
        try (Connection con = CommonDAO.createConnection();
             PreparedStatement pstmt = con.prepareStatement(SQL)) {

            pstmt.setString(1, userDTO.getUserid());
            String encryptedPwd = Encryption.passwordEncrypt(new String(userDTO.getPassword()));
            pstmt.setString(2, encryptedPwd);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new SQLException("Password encryption failed", e);
        }
    }

    // returns number of inserted rows (1 if success)
    public int add(UserDTO userDTO) throws ClassNotFoundException, SQLException {
        final String SQL = "INSERT INTO users(userid, password) VALUES (?, ?)";

        String encrypted;
        try {
            encrypted = Encryption.passwordEncrypt(new String(userDTO.getPassword()));
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new SQLException("Password encryption failed", e);
        }

        try (Connection con = CommonDAO.createConnection();
             PreparedStatement ps = con.prepareStatement(SQL)) {

            ps.setString(1, userDTO.getUserid());
            ps.setString(2, encrypted);
            return ps.executeUpdate();
        }
    }
}
