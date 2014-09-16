import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

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
                handleInput(line);
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


    private void handleInput(String input) {

        if(input.charAt(0)=='/'){   // Input is a command

            if(input.startsWith("/quit")){
                quit();
            }
            else if(input.startsWith("/who")){
               sendToClient("Who command used");
            }
           else if(input.startsWith("/nick")){
                String[] parameters = input.split(" ");

                if(parameters.length<2){ // No parameters
                    sendToClient("Usage: /nick <nickname>");
                }
                String newName = parameters[1];

                changeUserName(newName);
            }
            else if(input.startsWith("/help")){
                String message = "The following commands are available:" +
                                 "\n/quit - disconnect this client"+
                                 "\n/who - view all connected users"+
                              "\n/nick <nickname> - change your nick name"+
                                 "\n/help - list available commands";


                sendToClient(message);
            }
            else{
                sendToClient("Unknown command");
            }

        }

        // Input is an ordinary message
        else{
            clientPool.sendToAllButMe(this, input);
        }

    }

    private void quit() {
        // TODO
        sendToClient("Quit command used");
    }

    private void changeUserName(String newName) {
        // TODO
        sendToClient("/nick command used properly with nick "+newName);
    }
}
