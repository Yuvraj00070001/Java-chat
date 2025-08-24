package com.YuvrajSingh.chatApp.views;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.YuvrajSingh.chatApp.network.Client;
import com.YuvrajSingh.chatApp.utils.UserInfo;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

public class ClientChatScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextArea textArea ;
	private Client client;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ClientChatScreen frame=new ClientChatScreen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void sendIt() {
		String message=textField.getText();
		try {
			client.sendMessage(UserInfo.USER_NAME+"-"+message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public ClientChatScreen() throws UnknownHostException, IOException {
		textArea=new JTextArea();
		client=new Client(textArea);
		setTitle("Chit Chat");
	    setResizable(false);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setBounds(100, 100, 799, 425);
	    
	    contentPane = new JPanel();
	    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	    setContentPane(contentPane);
	    contentPane.setLayout(null);
	    
	    
	    textArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
	    
	    JScrollPane scrollPane = new JScrollPane();
	    // Set the JTextArea as the view for the JScrollPane
	    scrollPane.setViewportView(textArea); // or scrollPane.getViewport().add(textArea);
	    
	    // Position the scroll pane and add it to the content pane
	    scrollPane.setBounds(10, 10, 730, 326);
	    contentPane.add(scrollPane); // Add the scroll pane, not the text area
	    
	    textField = new JTextField();
	    textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
	    textField.setBounds(10, 346, 622, 32);
	    contentPane.add(textField);
	    textField.setColumns(10);
	    
	    JButton sendIt = new JButton("Send Message");
	    sendIt.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		sendIt();
	    	}
	    });
	    sendIt.setBounds(642, 346, 98, 32);
	    contentPane.add(sendIt);
	    
	    setVisible(true);
	}	
}
