import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Set;

public class GUI extends JFrame implements ActionListener {
    private JPanel Main;
    private JButton sendButton;
    private JTextField textToSend;
    private JScrollPane messScrollPane;
    private JScrollPane userScrollPane;

    private JList<String> listOfMess = new JList<>();

    private JList<String> listofUser = new JList<>();
    ChatMember chat;
    ClientManagement chatChat;

    void addNewMessage(String message){
        DefaultListModel<String> model = (DefaultListModel<String>) listOfMess.getModel();
        model.addElement(message);
        messScrollPane.setViewportView(listOfMess);
    }
    void addListUser(Set<String> userName){
       // listofUser.clearSelection();
        DefaultListModel<String> model = new DefaultListModel<String>();
        model.addAll(userName);
        listofUser.setModel(model);
        userScrollPane.setViewportView(listofUser);
    }


    public GUI(ChatMember chat, ClientManagement chatChat) {
        this.chatChat = chatChat;
        this.chat=chat;
//
       // Ustawiamy listę wiadomości
        listOfMess.setModel(new DefaultListModel<>());
        listofUser.setModel(new DefaultListModel<>());

        //getContentPane().add(scrollMessPane);


        // Dodajemy panel główny do okna
        setContentPane(Main);
        sendButton.addActionListener(this);
        setSize(600, 400);
        setMinimumSize(new Dimension(400, 300));
    //    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setVisible(true);
        WindowListener windowListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                chat.exit();
                chatChat.deleteUserAndExit();
            }
        };
        this.addWindowListener(windowListener);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendButton) {
            String text = textToSend.getText();
//            System.out.println("Wprowadzony tekst: " + text);
            textToSend.setText("");
            try {
                chat.sendMessage(text);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}