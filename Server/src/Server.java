import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Naknut on 16/09/14.
 */
public class Server {

    static ClientPool clientPool = new ClientPool();

    public static void main(String[] args) throws IOException {
        while (true) {
            ServerSocket serverSocket = new ServerSocket(1337);
            Socket client = serverSocket.accept();
            ClientThread clientThread = new ClientThread(client, clientPool);
            clientPool.add(clientThread);
            new Thread(clientThread).start();
        }
    }
}
