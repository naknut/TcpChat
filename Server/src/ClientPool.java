import java.util.ArrayList;

/**
 * Created by Naknut on 16/09/14.
 */
public class ClientPool {
    ArrayList<ClientThread> clientPool = new ArrayList<ClientThread>();

    public void add(ClientThread clientThread) {
        clientPool.add(clientThread);
    }

    public void sendToAllButMe(ClientThread sender, String message) {
        for(ClientThread clientThread : clientPool) {
            if(clientThread != sender)
                clientThread.sendToClient(message);
        }
    }
}
