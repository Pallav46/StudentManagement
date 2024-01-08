import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI extends JFrame {

    public MainGUI() {
        setTitle("Student Information System");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton adminButton = new JButton("Admin");
        JButton userButton = new JButton("User");

        adminButton.addActionListener(e -> {
            openAdminForm();
        });

        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openUserForm();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.add(adminButton);
        panel.add(userButton);

        add(panel);
    }

    private void openAdminForm() {
        AdminForm adminForm = new AdminForm();
        adminForm.setVisible(true);
    }

    private void openUserForm() {
        UserForm userForm = new UserForm();
        userForm.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGUI mainGUI = new MainGUI();
            mainGUI.setVisible(true);
        });
    }
}
