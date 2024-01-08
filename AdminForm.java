import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.*;

class AddStudentsForm extends JFrame {

    private JTextField rollNumberField;
    private JTextField nameField;
    private JButton submitButton;


    public AddStudentsForm() {
        setTitle("Edit Details");
        setSize(400, 200);
        //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        rollNumberField = new JTextField();
        nameField = new JTextField();

        panel.add(new JLabel("Roll Number: "));
        panel.add(rollNumberField);
        panel.add(new JLabel("Name: "));
        panel.add(nameField);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> submitDetails());
        panel.add(new JLabel(""));
        panel.add(submitButton);

        add(panel);
    }

    public void submitDetails() {
        int rollNumber = Integer.parseInt(rollNumberField.getText());
        String name = nameField.getText();

        Students s = new Students(rollNumber, name);
        ArrayList<Students> sList = DetailsForm.getDetailsFromFile();
        sList.add(s);

        DetailsForm.addToFile(sList);


        JOptionPane.showMessageDialog(this, "Details added successfully");
        this.dispose();
    }
}


class ModifyDetailsForm extends JFrame {

    private JTextField searchRollNumberField;
    private JButton searchButton;

    public ModifyDetailsForm() {
        setTitle("Modify Details");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        panel.add(new JLabel("Enter Roll Number to Modify: "));
        searchRollNumberField = new JTextField();
        panel.add(searchRollNumberField);

        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchAndOpenEditForm());
        panel.add(searchButton);

        add(panel);
    }

    private void searchAndOpenEditForm() {
        String searchRollNumber = searchRollNumberField.getText();
        Students student = findStudentByRollNumber(searchRollNumber);

        if (student != null) {
            openEditForm(student);
        } else {
            JOptionPane.showMessageDialog(this, "Student not found with Roll Number: " + searchRollNumber);
        }
    }

    private void openEditForm(Students student) {
        EditDetailsForm editDetailsForm = new EditDetailsForm(student);
        editDetailsForm.setVisible(true);
        this.dispose();
    }

    private Students findStudentByRollNumber(String rollNumber) {
        ArrayList<Students> s = DetailsForm.getDetailsFromFile();
        for(Students sr : s) {
            if(sr.getRollNumber() == Integer.parseInt(rollNumber)) {
                return sr;
            }
        }
        return null;
    }
}

class EditDetailsForm extends JFrame {

    private JTextField rollNumberField;
    private JTextField nameField;
    private JButton submitButton;
    private Students student;

    public EditDetailsForm(Students student) {
        this.student = student;

        setTitle("Edit Details");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        rollNumberField = new JTextField(String.valueOf(student.getRollNumber()));
        nameField = new JTextField(student.getName());

        panel.add(new JLabel("Roll Number: "));
        panel.add(rollNumberField);
        panel.add(new JLabel("Name: "));
        panel.add(nameField);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> submitDetails());
        panel.add(new JLabel(""));
        panel.add(submitButton);

        add(panel);
    }

    private void submitDetails() {
        int newRollNumber = Integer.parseInt(rollNumberField.getText());
        String newName = nameField.getText();

        ArrayList<Students> sList = DetailsForm.getDetailsFromFile();
        for (Students students : sList) {
            if (students.getRollNumber() == student.getRollNumber()) {
                students.setRollNumber(newRollNumber);
                students.setName(newName);
                DetailsForm.addToFile(sList);
                break;
            }
        }

        // Perform any additional logic you need for updating the student details
        JOptionPane.showMessageDialog(this, "Details updated successfully");
        this.dispose();
    }
}


class DeleteStudentsForm extends JFrame {

    private JTextField deleteRollNumberField;
    private JButton deleteButton;

    public DeleteStudentsForm() {
        setTitle("Delete Students");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        panel.add(new JLabel("Enter Roll Number to Delete: "));
        deleteRollNumberField = new JTextField();
        panel.add(deleteRollNumberField);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteStudent());
        panel.add(deleteButton);

        add(panel);
    }

    private void deleteStudent() {
        String deleteRollNumber = deleteRollNumberField.getText();
        Students student = findStudentByRollNumber(deleteRollNumber);

        if (student != null) {
            deleteStudentLogic(student);
            JOptionPane.showMessageDialog(this, "Student deleted successfully");
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Student not found with Roll Number: " + deleteRollNumber);
        }
    }

    private void deleteStudentLogic(Students student) {
        ArrayList<Students> sList = DetailsForm.getDetailsFromFile();
        for (Students students : sList) {
            if (students.getRollNumber() == student.getRollNumber()) {
                sList.remove(students);
                DetailsForm.addToFile(sList);
                break;
            }
        }
    }

    private Students findStudentByRollNumber(String rollNumber) {
        ArrayList<Students> sList = DetailsForm.getDetailsFromFile();
        for (Students students : sList) {
            if (students.getRollNumber() == Integer.parseInt(rollNumber)) {
                return students;
            }
        }
        return null;
    }
}

class AdminOperation extends JFrame {

    public AdminOperation() {
        setTitle("Admin Operations");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JButton addStudentsButton = new JButton("Add Students");
        addStudentsButton.addActionListener(e -> openAddStudentsForm());

        JButton modifyDetailsButton = new JButton("Modify Details");
        modifyDetailsButton.addActionListener(e -> openModifyDetailsForm());

        JButton deleteStudentsButton = new JButton("Delete Students");
        deleteStudentsButton.addActionListener(e -> openDeleteStudentsForm());

        panel.add(addStudentsButton);
        panel.add(modifyDetailsButton);
        panel.add(deleteStudentsButton);

        add(panel);
    }

    private void openAddStudentsForm() {
        AddStudentsForm addStudentsForm = new AddStudentsForm();
        addStudentsForm.setVisible(true);
    }

    private void openModifyDetailsForm() {
        ModifyDetailsForm modifyDetailsForm = new ModifyDetailsForm();
        modifyDetailsForm.setVisible(true);
    }

    private void openDeleteStudentsForm() {
        DeleteStudentsForm deleteStudentsForm = new DeleteStudentsForm();
        deleteStudentsForm.setVisible(true);
    }

}

public class AdminForm extends JFrame {

    private JTextField portNumber;
    private JTextField numOfStudents;
    private JButton submitButton;
    private ArrayList<Students> sList;

    public AdminForm() {
        setTitle("Admin Form");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        portNumber = new JTextField();
        numOfStudents = new JTextField();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(new JLabel("Port Number: "));
        panel.add(portNumber);
        panel.add(new JLabel("No. Of Students: "));
        panel.add(numOfStudents);

        submitButton = new JButton("Submit");
        panel.add(new JLabel(""));
        panel.add(submitButton);

        submitButton.addActionListener(e -> {
            int n = Integer.parseInt(numOfStudents.getText());
            DetailsForm detailsForm = new DetailsForm("Add New Students", sList, n, this);
            detailsForm.setVisible(true);
            StartServer startServer = new StartServer(Integer.parseInt(portNumber.getText()));
            startServer.setVisible(true);
            this.dispose(); 
        });

        add(panel);

        sList = new ArrayList<>();
    }

    public void openAdminOperation() {
        SwingUtilities.invokeLater(() -> {
            AdminOperation adminOperation = new AdminOperation();
            adminOperation.setVisible(true);
        });
    }
}
