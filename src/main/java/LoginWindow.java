import org.apache.kafka.clients.producer.ProducerRecord;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoginWindow extends JFrame {
    private JPanel loginPanel;
    private JTextField loginField;
    private JButton loginButton;



    public LoginWindow(String topic) {
        setSize(300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setTitle("Login");

        loginPanel = new JPanel();
        loginField = new JTextField(20);
        loginButton = new JButton("Login");

        loginPanel.add(new JLabel("Enter your name: "));
        loginPanel.add(loginField);
        loginPanel.add(loginButton);

        add(loginPanel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = loginField.getText().trim();
                if (!name.isEmpty()) {
                    new Chat(name, "test");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String formattedDate = LocalDateTime.now().format(formatter);
                    MessageProducer.send(new ProducerRecord<>(topic, "USER: " + name + ", logged in at: "+
                            formattedDate));
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginWindow.this, "You need to enter a name.");
                }
            }
        });

        setVisible(true);
    }
}