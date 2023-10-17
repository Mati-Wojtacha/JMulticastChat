import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.HashSet;
import java.util.Set;

public class ClientManagement {
    private static final int PORT = 8888;
    private static final String HOST = "224.0.0.1";
    private static final int BUFFER_SIZE = 1024;

    private static final int MAX_USERS = 5;

    private Set<String> usernames = new HashSet<>();
    private MulticastSocket socket;
    private InetAddress group;
    public Set<String> UsedUsername = new HashSet<>();
    private ChatMember client;
    LoginPane loginPane;
    Thread receiverThread;
    Thread sendUser;

    public ClientManagement() throws IOException {

        socket = new MulticastSocket(PORT);
        group = InetAddress.getByName(HOST);
        socket.joinGroup(group);

        receiverThread = new Thread(this::receiveUserName);
        receiverThread.start();

        loginPane = new LoginPane(this);

    }
    void deleteUserAndExit(){
        try {
            String message = "LOGOUT:" + client.username;
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, PORT);
            socket.send(packet);

            receiverThread.interrupt();
            sendUser.interrupt();
            socket.close();
//        System.out.println("delete");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void exit(){
        socket.close();
        receiverThread.interrupt();
        if(sendUser!=null)
            sendUser.interrupt();
    }

    private void receiveUserName() {
        try {
            byte[] buffer = new byte[BUFFER_SIZE];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            while (true) {
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());
                if (message != null) {
//                    System.out.println(message);
                    if (message.startsWith("USER_NAME:")) {
                        String username = message.substring("USER_NAME:".length());
//                    System.out.println("Received username: " + username);
                            UsedUsername.add(username);
                            if(client!=null){
                                client.setUserListGUI(UsedUsername);
                            }
                    }
                    if (message.startsWith("LOGOUT:")) {
//                        System.out.println(message);
                        String username = message.substring("LOGOUT:".length());
//                        System.out.println(username);
                        UsedUsername.remove(username);
//                        System.out.println(UsedUsername);
//                        client.setUserListGUI(UsedUsername);
                    }
                }
                if(receiverThread.isInterrupted())
                    return;
            }
        } catch (IOException e) {
//            System.out.println(e);
        }
    }

    private void sendUserName() {
        while (true) {
            if (client.username != null) {
                try {
                    Thread.sleep(1000);
                    String userNameMessage = "USER_NAME:" + client.username;
                    byte[] buffer = userNameMessage.getBytes();
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                    socket.send(packet);
//                    System.out.println(userNameMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    public void sendLoginRequest(String nick) throws IOException {
//        System.out.println(UsedUsername);
//        System.out.println(UsedUsername.size() > 5);
        if (!UsedUsername.contains(nick) && UsedUsername.size() < MAX_USERS) {
            UsedUsername.add(nick);
            client=new ChatMember(nick);
            client.setGUI(new GUI(client,this));
            loginPane.dispose();
            loginPane = null;
            sendUser = new Thread(this::sendUserName);
            sendUser.start();
        } else {
            loginPane.setAlertName("Nazwa uzytkownika jest zajeta, lub osiagieto maksymalna ilosc uzytkownikow");
        }
    }
}