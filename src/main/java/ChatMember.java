import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Set;

public class ChatMember {
    private static final int PORT = 4444;
    private static final String HOST = "224.0.0.1";
    private static final int BUFFER_SIZE = 1024;

    private MulticastSocket socket;
    private InetAddress group;
    Thread receiverThread;
    String username;
    private GUI gui;

    public ChatMember(String username) throws IOException {
        this.username = username;

        socket = new MulticastSocket(PORT);
        group = InetAddress.getByName(HOST);
        socket.joinGroup(group);

        receiverThread = new Thread(this::receiveMessages);
        receiverThread.start();
    }

    public void setGUI(GUI gui) {
        this.gui = gui;
    }
    public void setUserListGUI(Set<String> userNames){gui.addListUser(userNames); }
    public void exit(){
        receiverThread.interrupt();
        socket.close();
    }

    public void sendMessage(String message) throws IOException {
        String formattedMessage = String.format("[%s]: %s", username, message);
        byte[] buffer = formattedMessage.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, PORT);
        socket.send(packet);
    }

    private void receiveMessages() {
        byte[] buffer = new byte[BUFFER_SIZE];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        String pattern = "\\[.*\\]:.*";

        while (true) {
            try {
                socket.receive(packet);
                String message = new String(packet.getData(), packet.getOffset(), packet.getLength());

                if (gui != null && message.matches(pattern)) {
                    gui.addNewMessage(message);
                }
            } catch (IOException e) {
                return;
            }
        }
    }
}