import java.io.*;
import java.net.Socket;

/**
 * Created by Naknut on 16/09/14.
 */
public class ClientThread extends Thread{

    Socket client;
    String username = "Unknown";

    public ClientThread(Socket client) {
        this.client = client;
    }

    public void run() {
        ClientPool.getInstance().sendWelcomeMessage(this);
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String line;
            while ((line = in.readLine()) != null && !client.isClosed()) {
                handleInput(line);
            }

        } catch (IOException e) {
            System.out.println("IOException in ClientThread");
        }
        finally{
            System.out.println("Disconnecting client");
            if(!client.isClosed()){
                ClientPool.getInstance().disconnectMe(this);
            }
        }
    }

    public void sendToClient(String message) {
        PrintWriter out;
        try {
            out = new PrintWriter(client.getOutputStream(), true);
            out.println(message);
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
               ClientPool.getInstance().listAllUsers(this);
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
            ClientPool.getInstance().sendToAllButMe(this, input);
        }
    }

    private void quit() {
        ClientPool.getInstance().disconnectMe(this);
    }

    private void changeUserName(String newName) {
        ClientPool.getInstance().changeUsername(newName, this);
    }

}
