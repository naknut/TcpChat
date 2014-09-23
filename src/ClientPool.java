import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Naknut on 16/09/14.
 */
public class ClientPool {

    private static ClientPool instance;

    private ArrayList<ClientThread> clientPool = new ArrayList<ClientThread>();

    String welcomeMessage = "Welcome to our super cool chat!";

    private ClientPool() { }

    public static synchronized ClientPool getInstance() {
        if(instance == null)
            instance = new ClientPool();
        return instance;
    }

    public void add(ClientThread clientThread) {
        synchronized (clientPool) {
            clientPool.add(clientThread);
        }
    }

    public void sendToAllButMe(ClientThread sender, String message) {
        synchronized (clientPool) {
            for (ClientThread clientThread : clientPool) {
                if (clientThread != sender)
                    clientThread.sendToClient(sender.username + ": " + message);
            }
        }
    }

    public void disconnectMe(ClientThread sender) {
        try {
            synchronized (clientPool) {
                sender.client.close();
                clientPool.remove(sender);
                sendToAll(sender.username + " has disconnected.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeUsername(String username, ClientThread caller) {
        synchronized (clientPool) {
            for (ClientThread clientThread : clientPool) {
                if (clientThread.username.equals(username)) {
                    caller.sendToClient("Username allready in use");
                    return;
                }
            }
            String oldName = caller.username;
            caller.username = username;
            sendToAll(oldName + " changed name to " + username);
        }
    }

    public void listAllUsers(ClientThread caller) {
        String users = "";
        synchronized (clientPool) {
            for (ClientThread clientThread : clientPool)
                users += clientThread.username + " ";
            caller.sendToClient(users);
        }
    }

    public void sendWelcomeMessage(ClientThread caller) {
        caller.sendToClient(welcomeMessage);
    }

    private void sendToAll(String message) {
        synchronized (clientPool) {
            for (ClientThread clientThread : clientPool)
                clientThread.sendToClient(message);
        }
    }
}
