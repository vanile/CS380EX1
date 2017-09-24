import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public final class EchoServerMT extends Thread {

    private Socket socket;

    public EchoServerMT(Socket socket) {
        this.socket = socket;
    }
    
    public static void main(String[] args) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(22222)) {
            while (true) {
                Thread newServerThread = new Thread(new EchoServerMT(serverSocket.accept()));
                newServerThread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            final String exitVar = "exit";
            String address = socket.getInetAddress().getHostAddress();
            System.out.println("Client connected:  " + address);

            OutputStream os = socket.getOutputStream();
            PrintStream out = new PrintStream(os, true, "UTF-8");

            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);

            out.println(address + " connected.");
            String userInput = "";

            while (!userInput.equals(exitVar)) {
                userInput = br.readLine();
                out.println(userInput);
            }

            if (userInput.equals(exitVar)) {
                System.out.println("Client disconnected:  " + address);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
