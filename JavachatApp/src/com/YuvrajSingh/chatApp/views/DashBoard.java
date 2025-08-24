package com.YuvrajSingh.chatApp.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Font;
import java.net.URL;
import java.net.UnknownHostException;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * DashBoard updated to leave empty space under the welcome label
 * so you can place a JMenuBar (or drag one in the GUI builder).
 */
public class DashBoard extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    // height in pixels for the empty area where you'll place the JMenuBar
    private static final int MENU_SPACE_HEIGHT = 60;

    // NEW: constructor that accepts the user's first name
    public DashBoard(String firstName) {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Dashboard - Welcome " + (firstName == null ? "" : firstName));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 956, 721);

        contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        // === Top container with welcome label + empty spacer for menu bar placement ===
        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        topContainer.setOpaque(false);

        String welcomeText = "Welcome " + (firstName != null && !firstName.isEmpty() ? firstName : "");
        JLabel welcomeLabel = new JLabel(welcomeText, SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        welcomeLabel.setAlignmentX(CENTER_ALIGNMENT);
        welcomeLabel.setBorder(new EmptyBorder(10, 0, 6, 0)); // spacing around label

        topContainer.add(welcomeLabel);

        // Transparent spacer where you can later insert your JMenuBar
        JPanel chatmenu = new JPanel();
        chatmenu.setOpaque(false);
        chatmenu.setPreferredSize(new Dimension(0, MENU_SPACE_HEIGHT)); // width ignored by layout, height is used
        topContainer.add(chatmenu);
        
        JMenuItem mntmNewMenuItem = new JMenuItem("start chat");
        mntmNewMenuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
					new ClientChatScreen();
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        });
        chatmenu.add(mntmNewMenuItem);
        
        JMenuBar menuBar = new JMenuBar();
        chatmenu.add(menuBar);

        contentPane.add(topContainer, BorderLayout.NORTH);
        // =======================================================================

        // Label to display the image (center)
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        contentPane.add(imageLabel, BorderLayout.CENTER);

        // Preferred display size (adjust as needed)
        int displayW = 900;
        int displayH = 600;

        // Recommended resource name (no spaces)
        String resourceName = "/images/chit_chat_2.jpg"; // try this first
        String altName = "/chit_chat_2.jpg";             // if images folder is a source root, use this

        // Try a few ways to load the resource (prints debug info)
        URL url = getClass().getResource(resourceName);
        System.out.println("getResource(" + resourceName + ") = " + url);

        if (url == null) {
            // try without the images/ prefix (in case src/images was marked as a source folder)
            url = getClass().getResource(altName);
            System.out.println("getResource(" + altName + ") = " + url);
        }

        ImageIcon icon = null;
        if (url != null) {
            icon = new ImageIcon(url);
            System.out.println("Loaded image from classpath: " + url);
        } else {
            // Fallback: try classloader without leading slash
            URL url2 = Thread.currentThread().getContextClassLoader().getResource(resourceName.startsWith("/") ? resourceName.substring(1) : resourceName);
            System.out.println("ClassLoader.getResource(...) = " + url2);
            if (url2 != null) {
                icon = new ImageIcon(url2);
                System.out.println("Loaded via ClassLoader: " + url2);
            } else {
                // Final fallback: try loading from the project filesystem (dev only)
                File f = new File("src/images/chit_chat_2.jpg");
                System.out.println("File on disk src/images/chit_chat_2.jpg exists? " + f.exists() + " -> " + f.getAbsolutePath());
                if (f.exists()) {
                    icon = new ImageIcon(f.getAbsolutePath());
                    System.out.println("Loaded from filesystem: " + f.getAbsolutePath());
                }
            }
        }

        if (icon != null) {
            // scale the image to fit nicely
            Image scaled = icon.getImage().getScaledInstance(displayW, displayH, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaled);
            imageLabel.setIcon(scaledIcon);

            // also set as the window icon (small)
            setIconImage(icon.getImage());
        } else {
            imageLabel.setText("< image not found - see console for debug >");
        }
    }
}
