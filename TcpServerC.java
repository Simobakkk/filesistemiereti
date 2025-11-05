
/**
 * from network/..
 * javac network/TcpServer.java; java network.TcpServer 
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {
    public static void main(String[] args) throws Exception {

        int severPort = 8765;
        String clientMsg = "";
        boolean verify = false;

        try {
            // Creazione del socket sul server e ascolto sulla porta
            ServerSocket serverSocket = new ServerSocket(severPort);
            System.out.println("Server: in ascolto sulla porta " + severPort);

            // Attesa della connessione con il client
            Socket clientSocket = serverSocket.accept();

            // Create input and output streams to read/write data
            DataInputStream inStream = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream outStream = new DataOutputStream(clientSocket.getOutputStream());

            // Scambio di dati tra client e server
            while ((!clientMsg.equals("quit")) && (verify == false)) {
                clientMsg = inStream.readUTF();
                System.out.println("Server: ricevuto messaggio " + clientMsg);
                String vocali = "aeiou";
                boolean vocalTrue = false;
                int contV = 0, contC = 0;
                for (int i = 0; i < clientMsg.length(); i++) {
                    // conta consonanti
                    for (int j = 0; j < vocali.length(); j++) {
                        if (clientMsg.charAt(i) == vocali.charAt(j)) {
                            contV++;
                            vocalTrue = true;
                            break;
                        }
                    }
                    if (!vocalTrue && clientMsg.charAt(i) != ' ') {
                        contC++;
                    }
                    vocalTrue = false;
                }
                if (contC * 2 == contV) {
                    verify = true;
                } else {
                    outStream.writeUTF("Numero vocali: " + contV + " - Numero consonanti: " + contC);
                    outStream.flush();
                }
            }

            // Close resources
            serverSocket.close();
            clientSocket.close();
            inStream.close();
            outStream.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}