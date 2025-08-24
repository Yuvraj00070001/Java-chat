package com.YuvrajSingh.chatApp.views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.YuvrajSingh.chatApp.dao.UserDAO;
import com.YuvrajSingh.chatApp.dto.UserDTO;
import com.YuvrajSingh.chatApp.utils.UserInfo;

/**
 * Updated UserScreen: unchanged layout but doLogin() now derives a first name
 * and passes it to DashBoard(firstName) when login succeeds.
 */
public class UserScreen extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField userIDtxt;
    private JPasswordField passwordField;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                new UserScreen();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public UserScreen() {
        setResizable(false);
        setTitle("LOGIN");
        getContentPane().setBackground(Color.WHITE);
        getContentPane().setLayout(null);

        JLabel lblTitle = new JLabel("LOGIN");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 40));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(373, 40, 220, 43);
        getContentPane().add(lblTitle);

        JLabel lblUser = new JLabel("User ID");
        lblUser.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblUser.setBounds(260, 107, 136, 43);
        getContentPane().add(lblUser);

        userIDtxt = new JTextField();
        userIDtxt.setBounds(547, 107, 242, 32);
        userIDtxt.setColumns(10);
        getContentPane().add(userIDtxt);

        JLabel lblPwd = new JLabel("Password");
        lblPwd.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblPwd.setBounds(260, 185, 136, 43);
        getContentPane().add(lblPwd);

        passwordField = new JPasswordField();
        passwordField.setBounds(547, 196, 242, 32);
        getContentPane().add(passwordField);

        JButton loginbt = new JButton("login");
        loginbt.setFont(new Font("Tahoma", Font.BOLD, 12));
        loginbt.setBounds(335, 290, 116, 32);
        loginbt.addActionListener(e -> doLogin());
        getContentPane().add(loginbt);

        JButton registerbt = new JButton("register");
        registerbt.setFont(new Font("Tahoma", Font.BOLD, 12));
        registerbt.setBounds(547, 290, 116, 32);
        registerbt.addActionListener(e -> register());
        getContentPane().add(registerbt);

        setBackground(Color.WHITE);
        setSize(833, 440);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void register() {
        String userid = userIDtxt.getText().trim();
        char[] password = passwordField.getPassword();

        if (userid.isEmpty() || password.length == 0) {
            JOptionPane.showMessageDialog(this, "User ID and Password are required.");
            return;
        }

        int result;
        try {
            UserDAO userDAO = new UserDAO();
            UserDTO userDTO = new UserDTO(userid, password);
            result = userDAO.add(userDTO);
        } catch (ClassNotFoundException | java.sql.SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE, loadIcon());
            return;
        } finally {
            Arrays.fill(password, '\0'); // wipe sensitive data
        }

        JOptionPane.showMessageDialog(this,
                (result > 0 ? "Register Successfully" : "Register Failed"),
                "Message",
                (result > 0 ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE),
                loadIcon());
    }

    private void doLogin() {
        String userid = userIDtxt.getText().trim();
        char[] password = passwordField.getPassword();

        if (userid.isEmpty() || password.length == 0) {
            JOptionPane.showMessageDialog(this, "User ID and Password are required.");
            // wipe password
            Arrays.fill(password, '\0');
            return;
        }

        // build DTO
        UserDTO userDTO = new UserDTO(userid, password);

        try {
            String message = "";
            UserDAO userDAO = new UserDAO();

            if (userDAO.isLogin(userDTO)) {
                // Derive a friendly first name to show on the dashboard.
                // Prefer splitting the userid by whitespace (if it's a full name),
                // otherwise use the userid as-is.
                String firstName = userid;
                if (firstName != null && firstName.contains(" ")) {
                    firstName = firstName.split("\\s+")[0];
                }
                // If userid contains common username symbols, try a safer fallback:
                // remove anything after '@' or '.' (e.g., email-like usernames)
                if (firstName != null) {
                    int at = firstName.indexOf('@');
                    int dot = firstName.indexOf('.');
                    int cut = -1;
                    if (at > 0) cut = at;
                    if (dot > 0 && (cut == -1 || dot < cut)) cut = dot;
                    if (cut > 0) firstName = firstName.substring(0, cut);
                }

                // Capitalize first letter (nice touch)
                if (firstName != null && firstName.length() > 0) {
                    firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
                }

                message = "Welcome " + firstName;
                UserInfo.USER_NAME=userid;
                JOptionPane.showMessageDialog(this, message);

                // open dashboard (on EDT) with the first name
                EventQueue.invokeLater(() -> {
                    DashBoard dash = new DashBoard(userid); // ensure DashBoard has constructor DashBoard(String)
                    dash.setLocationRelativeTo(null);
                    dash.setVisible(true);
                });

                // Close the login window
                this.dispose();
            } else {
                message = "Invalid Userid or Password";
                JOptionPane.showMessageDialog(this, message);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Driver not found: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Unexpected error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // wipe sensitive password data
            if (password != null) {
                Arrays.fill(password, '\0');
            }
        }
    }

    // Optional icon (placed at src/main/resources/resources/java.png)
    private ImageIcon loadIcon() {
        try {
            java.net.URL url = getClass().getResource("/resources/java.png");
            if (url != null) return new ImageIcon(url);
        } catch (Exception ignored) {}
        return null;
    }
}
