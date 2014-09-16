import java.io.IOException;
import java.net.Socket;

/**
 * Created by Naknut on 16/09/14.
 */
public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1337);

    }
}
