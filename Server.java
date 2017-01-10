import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Server extends JFrame {
	
	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection;

	// constructor
	public Server() {
		super("Bucky's Instant Messenger");
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener(
			new ActionListener(){
			public void actionPerformed(ActionEvent event){
				sendMessage(event.getActionCommand());
				userText.setText("");			
			}
		}
	);
	add(userText, BorderLayout.NORTH);
	chatWindow = new JTextArea();
	add(new JScrollPane(chatWindow));
	setSize(300,150);
	setVisible(true);
	}

	// set up and run the server
	public void startRunning() {
		try {
			server = new ServerSocket(6789, 100);
			while(true){
				try {
					// connect and have conversation
					waitForConnection();
					setupStreams();
					whileChatting();
				}catch(EOFException eofException){
					showMessage("\n Server ended the connection!");
				}finally {
					closeCrap();
				}
			}
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}

	// wait for connection, then display connection information
	private void waitForConnectin() throws IOException {

		showMessage("Waiting for someone to connect... \n");
		connection = server.accept();
		showMessage("Now connected to " + connection.getInetAddress().getHostName());
	}

	// get stream to send and receive data
	private void setupStreams() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\n Streams are now setup!\n");
	
	}

	// during the chat conversation
	private void whileChatting() throws IOException {
		String message = "You are now connected!";
		sendMessage(message);
		ableToType(true);
	}
}
