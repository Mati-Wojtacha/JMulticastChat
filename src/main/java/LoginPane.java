import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

public class LoginPane extends JFrame {
    private JPanel panel1;
    private JTextField nickName;
    private JButton continueButton;
    private JLabel Alert;

    private ClientManagement chatApp;

    public LoginPane(ClientManagement chatApp) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignore) {
        }

        setContentPane(panel1);
        continueButton.addActionListener(e -> {
            try {
                handleLogin();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        setSize(400, 300);
        //    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
        setLocationRelativeTo(null);
        setVisible(true);
        this.chatApp = chatApp;
        nickName.addActionListener(e -> {
            try {
                handleLogin();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        WindowListener windowListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                chatApp.exit();
                //System.exit(1);
            }
        };
        this.addWindowListener(windowListener);
//        Alert = new JLabel();

        SwingUtilities.updateComponentTreeUI(this);
    }
    void setAlertName(String name){
        Alert.setText(name);
    }

    public void handleLogin() throws IOException {

            String nick = nickName.getText();
//            System.out.println(nick);
            chatApp.sendLoginRequest(nick);

    }

    public static void main(String[] args) {
        new LoginPane(null);
    }
}
