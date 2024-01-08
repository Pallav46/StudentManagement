import java.awt.GridLayout;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

class DetailsByRoll extends JFrame {
    private JTextField rollField;
    private JButton submitButton;

    public DetailsByRoll() {
        setTitle("Details By ROll");
        setSize(300, 100);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        rollField = new JTextField();
        submitButton = new JButton("Submit");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(new JLabel("Roll :- "));
        panel.add(rollField);
        panel.add(submitButton);

        submitButton.addActionListener(e -> {
            String roll = rollField.getText();
            if (!roll.isEmpty()) {
                requestDetailsByRoll(roll);
                dispose(); 
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a roll.");
            }
        });

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);

        add(panel);
    }

    private void requestDetailsByRoll(String roll) {
        UserOperation userOperation = new UserOperation(3, Integer.parseInt(roll), "");
        userOperation.setVisible(true);
    }
}


class DetailsByName extends JFrame {
    private JTextField nameField;
    private JButton submitButton;

    public DetailsByName() {
        
        setTitle("Details By Name");
        setSize(300, 100);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        nameField = new JTextField();
        submitButton = new JButton("Submit");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(new JLabel("Name :- "));
        panel.add(nameField);
        panel.add(submitButton);

        submitButton.addActionListener(e -> {
            String name = nameField.getText();
            if (!name.isEmpty()) {
                requestDetailsByName(name);
                dispose(); // Close the form after submitting
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a name.");
            }
        });

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);

        add(panel);
    }

    private void requestDetailsByName(String name) {
        UserOperation userOperation = new UserOperation(2, -1, this.nameField.getText());
        userOperation.setVisible(true);
    }
}


class UserOperation extends JFrame {
    private JTextField portNumber;
    private JButton submit;

    public UserOperation(int choice, int roll, String name) {
        setTitle("Connect to server..");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel jPanel = new JPanel(new GridLayout(2, 2));
        submit = new JButton("Sumbit");
        portNumber = new JTextField();
        JLabel jLabel = new JLabel("Port number");

        jPanel.add(jLabel);
        jPanel.add(portNumber);
        jPanel.add(submit);

        if (choice == 1) {
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getTotalNumberOfStudents(Integer.parseInt(portNumber.getText()));
                }
            });
        } else if (choice == 2) {
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getDetailsByName(Integer.parseInt(portNumber.getText()), name);
                }
            });
        } else if (choice == 3) {
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getDetailsByRoll(Integer.parseInt(portNumber.getText()), roll);
                }
            });
        }

        add(jPanel);
    }

    public void getTotalNumberOfStudents(int portNumber) {
        try {
            try (Socket socket = new Socket("localhost", portNumber)) {
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

                dataOutputStream.writeInt(1);
                dataOutputStream.flush();

                JOptionPane.showMessageDialog(this, "Total number of students :- " + dataInputStream.readInt());
            }
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Enter correct port number..");

        }
    }

    public void getDetailsByName(int portNumber, String name) {
        try {
            try (Socket socket = new Socket("localhost", portNumber)) {
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

                dataOutputStream.writeInt(2);
                dataOutputStream.flush();

                dataOutputStream.writeUTF(name);
                dataOutputStream.flush();

                String o = dataInputStream.readUTF();

                JOptionPane.showMessageDialog(this, o);
            }
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Enter correct port number..");

        }
    }

    public void getDetailsByRoll(int portNumber, int roll) {
        try {
            try (Socket socket = new Socket("localhost", portNumber)) {
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

                dataOutputStream.writeInt(3);
                dataOutputStream.flush();

                dataOutputStream.writeInt(roll);
                dataOutputStream.flush();

                String o = dataInputStream.readUTF();

                JOptionPane.showMessageDialog(this, o);
            }
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Enter correct port number..");

        }
    }
}


public class UserForm extends JFrame {
    private JButton getNumOfStudents;
    private JButton getDetailsByName;
    private JButton getDetailsByRoll;

    public UserForm() {
        setTitle("User Form");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getNumOfStudents = new JButton("View No. Of Students");
        getDetailsByName = new JButton("View Details by Name");
        getDetailsByRoll = new JButton("View Details by Roll");

        JPanel jPanel = new JPanel(new GridLayout(3, 1));
        jPanel.add(getNumOfStudents);
        jPanel.add(getDetailsByName);
        jPanel.add(getDetailsByRoll);

        getNumOfStudents.addActionListener(e -> {
            UserOperation userOperation = new UserOperation(1, -1, "");
            userOperation.setVisible(true);
        });

        getDetailsByName.addActionListener(e -> {
            DetailsByName detailsByName = new DetailsByName();
            detailsByName.setVisible(true);
        });

        getDetailsByRoll.addActionListener(e -> {
            DetailsByRoll detailsByRoll = new DetailsByRoll();
            detailsByRoll.setVisible(true);
        });

        add(jPanel);
    }

}