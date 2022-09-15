package ServeurClient;

import java.net.*;
import java.io.*;

public class Serveur {
    private Socket socket = null;

    private ServerSocket server = null; //Permet d'ouvrir la connection, d'écouter si quelqu'un veut se connecter et
                                        // va accepter lorsqu'une demande de connexion est faite
    private DataInputStream in = null;

    public Serveur(int port){

        try{
            server = new ServerSocket(port);
            System.out.println("Waiting for connection");
            socket = server.accept();

            in = new DataInputStream((socket.getInputStream()));

            String line = "";

            while(!line.equals("Fini")){
                try{
                    line= in.readUTF();
                    System.out.println(line);
                }
                catch(IOException i){
                    System.out.println(i);
                }
            }
            System.out.println("Connexion fermée");

            socket.close();
            in.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
}
