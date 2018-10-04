import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.net.*;
import java.io.*;

public class ClientConnecte extends JFrame implements ComponentListener {
    Socket sock = null;
    JLabel imageLabel = null;
    ImageIcon image = null;
    JLabel mouseLabel = null;
    public ClientConnecte()
    {
        setResizable(true);
        setSize(1500, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("java Sc");
        imageLabel = new JLabel();
        mouseLabel = new JLabel();

        //detecter les events de redimensionnement de jframe
        getContentPane().addComponentListener(this);


    }

    public void connexion(String host, int port) {
        try {
            System.out.println("Le client cherche a se connecter au serveur " + host + "@" + port);
            sock = new Socket(host, port);
            System.out.println("Le client s'est connecte sur serveur " + host + "@" + port);

        } catch (IOException e) {
        }
    }

    public void envoi(String data) {
        try {
            System.out.println("Le client cherche a recuperer le canal de communication");
            PrintStream output = new PrintStream(sock.getOutputStream());
            System.out.println("Le client cherche a envoyer la donnee au serveur ");
            output.println(data);
        } catch (IOException e) {
        }

    }

    public void recevoir() {
        try {
            System.out.println("Le client cherche a recuperer le canal de communication ");

            ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
            byte[] byteImage = (byte[])ois.readObject();
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(byteImage));
            System.out.println(bufferedImage.toString());

            image = new ImageIcon(bufferedImage);
            Image img = image.getImage().getScaledInstance(getWidth(),getHeight(),Image.SCALE_SMOOTH);
            image.setImage(img);
            imageLabel.setIcon(image);

            DataInputStream inpt = null;
            inpt = new DataInputStream(sock.getInputStream());
            String coord = inpt.readUTF();
            String[] vals = coord.split(";");

            //mouse loc
            float x = Float.valueOf(vals[0]);
            float y = Float.valueOf(vals[1]);

            ImageIcon imagecur = new ImageIcon("C:/Users/Abderrahim Dev/Pictures/cursor.png");
            Image imgCur = imagecur.getImage().getScaledInstance(15,15,Image.SCALE_SMOOTH);

            imagecur.setImage(imgCur);
            mouseLabel.setIcon(imagecur);
            mouseLabel.setBounds((int)x, (int)y,15,15);

            System.out.println(x + " - "+y);

            add(mouseLabel);
            add(imageLabel);

            setVisible(true);


        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void fermer() {
        try {
            System.out.println("Le client ferme la connexion au serveur ");
            sock.close();
        } catch (IOException e) {
        }
    }



    //Detecter les evts de redimensionnement afin de redimensionner l'image
    @Override
    public void componentResized(ComponentEvent e) {
        Image img = image.getImage().getScaledInstance(getWidth(),getHeight(),Image.SCALE_SMOOTH);
        image.setImage(img);
        imageLabel.setIcon(image);
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
