import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.net.*;
import java.io.*;

class ServeurConnecte extends Thread {
    ServerSocket reception;
    static final int port = 10000;
    private static byte[] byteImage;

    public ServeurConnecte() {
        try {
            reception = new ServerSocket(port);
            System.out.println("Le serveur est en ecoute sur le " + port);
        } catch (IOException e) {
            System.exit(1);
        }
        this.start();
    }

    public void run() {
        Socket sock = null;
            while (true) {
                System.out.println("Le serveur est en attente ");
                try {
                    sock = reception.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Le serveur a accepte la connexion avec " + sock.getInetAddress());
                BufferedImage image = null;
                while (true){
                    try {
                        //prendre une capture d ecran
                        image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                        String chemin = "C:/Users/Abderrahim Dev/Pictures/screenshot.jpg";
                        ImageIO.write(image, "jpg", new File(chemin));

                        //recuperer la position de pointeur du souris
                        Point mouseLocation  = MouseInfo.getPointerInfo().getLocation();

                        //conversion de buffered image en tab de byte
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write( image, "jpg", baos );
                        baos.flush();
                        byteImage = baos.toByteArray();
                        baos.close();

                        //System.out.println(byteImage.toString());
                        OutputStream os = sock.getOutputStream();
                        ObjectOutputStream oos = new ObjectOutputStream(os);
                        oos.writeObject(byteImage);
                        os.flush();

                        // envoi de la position de souris

                        DataOutputStream out2= new DataOutputStream(sock.getOutputStream());
                        out2.writeUTF(mouseLocation.getX()+";"+mouseLocation.getY());
                        //Thread.sleep(100);
                    }catch (Exception e){}

                }

            }


    }
}
