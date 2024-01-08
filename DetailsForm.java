import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class DetailsForm extends JFrame {

    private JTextField rollNumberField;
    private JTextField nameField;
    private JButton submitButton;
    private ArrayList<Students> sList;
    private int remainingStudents;
    private AdminForm adminForm;

    public DetailsForm(String operation, ArrayList<Students> sList, int remainingStudents, AdminForm adminForm) {
        setTitle("Details Form - " + operation);
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.sList = sList;
        this.remainingStudents = remainingStudents;
        this.adminForm = adminForm;

        rollNumberField = new JTextField();
        nameField = new JTextField();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(new JLabel("Roll Number:"));
        panel.add(rollNumberField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> submitDetails());
        panel.add(new JLabel(""));
        panel.add(submitButton);

        add(panel);
    }
    
    private void submitDetails() {
        try {
            int rollNumber = Integer.parseInt(rollNumberField.getText());
            String name = nameField.getText();
            sList.add(new Students(rollNumber, name));
            System.out.println("Added..");

            remainingStudents--;
            if (remainingStudents == 0) {
                addToFile(sList);
                JOptionPane.showMessageDialog(this, "Details submitted successfully");
                this.dispose();
                adminForm.openAdminOperation(); // Open AdminOperation window after submitting all details
            } else {
                rollNumberField.setText("");
                nameField.setText("");
            }
        } catch (NumberFormatException ex) {
            System.out.println("Invalid roll number.");
        }
    }

    public static void addToFile(ArrayList<Students> sList) {
        //ArrayList<Students> existingData = getDetailsFromFile();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Students_data.ser"))) {
            // Append new data to the existing data
            //existingData.addAll(sList);
            oos.writeObject(sList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Students> getDetailsFromFile() {
        ArrayList<Students> s = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Students_data.ser"))) {
            s = (ArrayList<Students>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Ignore FileNotFoundException; it means the file is not found, which is expected initially
        }
        return s;
    }
}
