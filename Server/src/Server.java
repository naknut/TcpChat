import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Naknut on 16/09/14.
 */
public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1337);
        while (true) {
            Socket client = serverSocket.accept();
            ClientThread clientThread = new ClientThread(client);
            ClientPool.getInstance().add(clientThread);
            new Thread(clientThread).start();
        }
    }
}
