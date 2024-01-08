import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class StartServer extends JFrame {
    private JLabel statusLabel;
    private int numOfClient;

    public StartServer(int port) {
        setTitle("Server Status");
        setSize(300, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        statusLabel = new JLabel("Waiting for connection...", SwingConstants.CENTER);
        add(statusLabel);

        setLocationRelativeTo(null);
        setVisible(true);

        startServer(port);
    }

    public void setStatusLabel(String status) {
        statusLabel.setText(status);
    }

    public void startServer(int port) {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                setStatusLabel("Server started on port " + port);

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    handleClient(clientSocket);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "ServerThread").start();
    }

    public void handleClient(Socket clientSocket) {
        new Thread(() -> {
            Thread.currentThread().setName("ClientThread-" + ++numOfClient);

            try (DataInputStream ois = new DataInputStream(clientSocket.getInputStream());
                    DataOutputStream oos = new DataOutputStream(clientSocket.getOutputStream())) {

                //String clientName = "Client" + numOfClient;
                //String clientAddress = clientSocket.getInetAddress().getHostAddress();
                //setStatusLabel("Connected: " + clientName + " (" + clientAddress + ")");

                int query = ois.readInt();

                if (query == 1) {
                    oos.writeInt(DetailsForm.getDetailsFromFile().size());
                    oos.flush();
                } else if (query == 2) {
                    String name = ois.readUTF();
                    ArrayList<Students> sList = new ArrayList<>();
                    sList = DetailsForm.getDetailsFromFile();
                    for (Students student : sList) {
                        if (student.getName().equalsIgnoreCase(name)) {
                            String send = "Name :- " + student.getName() + "\n" + "Roll :- " + student.getRollNumber();
                            oos.writeUTF(send);
                            oos.flush();
                            break;
                        }
                    }
                } else if (query == 3) {
                    int roll = ois.readInt();
                    ArrayList<Students> sList = DetailsForm.getDetailsFromFile();

                    for (Students student : sList) {
                        if (student.getRollNumber() == roll) {
                            String send = "Name :- " + student.getName() + "\n" + "Roll :- " + student.getRollNumber();
                            oos.writeUTF(send);
                            oos.flush();
                            break;
                        }
                    }
                }

            } catch (IOException e) {
                System.out.println("Err");
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
