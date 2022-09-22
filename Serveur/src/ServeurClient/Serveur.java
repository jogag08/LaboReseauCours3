package ServeurClient;

import java.net.*;
import java.io.*;
import java.util.Scanner;

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

            while(!line.equals("Fini")){
                try{
                    line= in.readUTF();
                    System.out.println(line);
                    if(CheckProtocol(line))
                    {
                        String command = CheckCommand(line);

                        if(command.equals("CreateFile"))
                        {
                            CreateFile();
                        }
                        else if(command.equals("ConsultFiles"))
                        {
                            ConsultFiles();
                        }
                        else if(command.equals("AmendFile"))
                        {
                            AmendFile();
                        }
                        else if(command.equals("ShowFileInfo"))
                        {
                            ShowFileInfo();
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
    public void CreateFile()
    {
        System.out.println("Enter file name :");

        try
        {
            String line ="";
            line = in.readUTF();
            File f = new File("Fichiers\\" + line);

            if(f.createNewFile())
            {
                System.out.println("File created : " + f.getName());
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
    }

    public void ConsultFiles()
    {
        File f = new File("Fichiers\\");
        File[] fList = f.listFiles();
        String fileNames = "";
        for(int i = 0; i < fList.length; i++)
        {
            fileNames = fList[i].getName();
            System.out.println(fileNames + ", ");
        }
    }

    public void AmendFile()
    {
        System.out.println("Enter file name :");
        try
        {
            String line ="";
            line = in.readUTF();

            FileWriter wFile = new FileWriter("Fichiers\\" + line);
            System.out.println("Enter text :");
            line = in.readUTF();
            wFile.write(line);
            wFile.close();

            System.out.println("File amended");
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }

    public void ShowFileInfo()
    {
        System.out.println("Enter file name :");
        try
        {
            String info ="";

            String line = "";
            line = in.readUTF();
            File f = new File("Fichiers\\" + line);

            Scanner scanFile = new Scanner(f);
            while (scanFile.hasNextLine()) {
                info += scanFile.nextLine();
                System.out.println(info);
            }
            scanFile.close();
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }
}
