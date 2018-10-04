/**
 * Created by Abderrahim Dev on 03/05/2018.
 */
public class ClientSide {
    public static void main(String[] args) {
        ClientConnecte client = new ClientConnecte();
        client.connexion("192.168.137.1", 10000);
        while (true) {
            try {
                client.recevoir();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
