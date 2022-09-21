package ServeurClient;

import java.net.*;
import java.io.*;
import java.io.File;

public class Serveur {

    private String protocol = "jaune";
    private Socket socket = null;

    private ServerSocket server = null; //Permet d'ouvrir la connection, d'écouter si quelqu'un veut se connecter et
                                        // va accepter lorsqu'une demande de connexion est faite
    private DataInputStream in = null;

    public Serveur(int port){

        try{
            server = new ServerSocket(port);
            System.out.println("Waiting for connection");
            socket = server.accept();
            System.out.println("Connecté");

            in = new DataInputStream((socket.getInputStream()));

            String line = "";
            String[] listFiles;

            while(!line.equals("Fini")){
                try{
                    line= in.readUTF();
                    System.out.println(line);
                    if(CheckProtocol(line))
                    {
                        String command = CheckCommand(line);

                        if(command.equals("CreateFile"))
                        {
                            File newF = CreateFile();
                        }
                        else if(command.equals("ConsultFiles"))
                        {

                        }
                        else if(command.equals("AmendFiles"))
                        {

                        }
                        else if(command.equals("ShowFileInfo"))
                        {

                        }
                        else
                        {
                            System.out.println("La commande n'existe pas");
                        }
                    };
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

    private boolean CheckProtocol(String line)
    {
        if(line.length() >= protocol.length())
        {
            String isThisProtocol = line.substring(0,protocol.length());
            if(isThisProtocol.equals(protocol))
            {
                System.out.println("Requête acceptée");
                return true;
            }
            else if(!isThisProtocol.equals(protocol))
            {
                System.out.println("Requête refusée");
            }
        }
        return false;
    }

    public String CheckCommand(String line)
    {
        String command = line.substring(protocol.length() + 1,line.length());
        return command;
    }
    public File CreateFile()
    {
        System.out.println("Enter file name :");
        String line = "";

        try
        {
            line = in.readUTF();
            line +=".txt";

            String path ="C:\\Users\\Jonathan\\Desktop\\Labo3Reseau\\LaboReseauCours3\\Serveur\\src\\/Files/" + line;
            File f = new File(path);


            if(f.createNewFile())
            {
                System.out.println("File created : " + f.getName());
                return f;
            }
            else
            {
                System.out.println("File already exists");
            }
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
        return null;
    }

    public void ConsultFiles()
    {

    }

}
