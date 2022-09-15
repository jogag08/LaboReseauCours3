import java.net.*;
import java.io.*;

public class Client
{
    private Socket socket = null;

    private DataInputStream in = null;
    private DataOutputStream out = null;

    public Client(String adress, int port)
    {
        try
        {
            socket = new Socket(adress, port);
            System.out.println("Connecté");

            in = new DataInputStream(System.in);
            out = new DataOutputStream(socket.getOutputStream());

            String line = "";

            while(!line.equals("Fini"))
            {
                try
                {
                    line = in.readLine();
                    out.writeUTF(line);
                }
                catch(IOException e)
                {
                    System.out.println(e);
                }
            }

            try
            {
                in.close();
                out.close();
                socket.close();
            }
            catch(IOException e)
            {
                System.out.println(e);
            }

        }
        catch(Exception e)
        {
            System.out.println(e);
        }

    }
}
