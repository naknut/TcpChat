import java.io.*;
import java.net.Socket;

/**
 * Created by Naknut on 16/09/14.
 */
public class ClientThread extends Thread{

    Socket client;
    ClientPool clientPool;

    public ClientThread(Socket client, ClientPool clientPool) {
        this.client = client;
        this.clientPool = clientPool;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                clientPool.sendToAllButMe(this, line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToClient(String message) {
        OutputStream outstream;
        try {
            outstream = client.getOutputStream();
            PrintWriter out = new PrintWriter(outstream);
            out.print(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
