import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;


class PhoneClient {
    public static void main(String args[]) throws Exception {
        String serverHostname = "localhost";
        int portNumber = 2014;
        Scanner scanner = new Scanner(System.in);
        String userInput;
        while (true) {
            try {
                // connect to server
                Socket clientSocket = new Socket(serverHostname, portNumber);
                // create input and output streams
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                Thread serverListener = new Thread(() -> {
                    try {
                        String serverResponse;
                        // receive and display server response
                        while ((serverResponse = in.readLine()) != null) {
                            System.out.println(serverResponse);
                        }
                    } catch (IOException e) {
                        System.err.println("Error reading from server: " + e.getMessage());
                    }
                });
                serverListener.start();

                // continually read user input and send it to the server
                while (true) {
                    userInput = scanner.nextLine();
                    out.println(userInput);
                }
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            } finally {
                // close resources properly
                scanner.close();
                System.exit(0);
            }
        }
    }
}