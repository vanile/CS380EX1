import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public final class EchoServer {

    public static void main(String[] args) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(22222)) {
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    final String exitVar = "exit";
                    String address = socket.getInetAddress().getHostAddress();
                    System.out.println("Client connected:  " + address);

                    OutputStream os = socket.getOutputStream();
                    PrintStream out = new PrintStream(os, true, "UTF-8");

                    InputStream is = socket.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                    BufferedReader br = new BufferedReader(isr);

                    out.printf("Hi %s, thanks for connecting!%n", address);
                    String userInput = "";

                    while (!userInput.equals(exitVar)) {
                        userInput = br.readLine();
                        out.println(userInput);
                    }

                    if (userInput.equals(exitVar)) {
                        System.out.println("Client disconnected:  " + address);
                    }
                }
            }
        }
    }
}
