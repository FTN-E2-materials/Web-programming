package primer02;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

  public static final int TCP_PORT = 9000;

  public static void main(String[] args) {
    try {
      // odredi adresu racunara sa kojim se povezujemo
      InetAddress addr = InetAddress.getByName("127.0.0.1");

      // otvori socket prema drugom racunaru
      Socket sock = new Socket(addr, TCP_PORT);

      // inicijalizuj ulazni stream
      DataInputStream in =
        new DataInputStream(
          new BufferedInputStream(
            sock.getInputStream()));

      // inicijalizuj izlazni stream
      ObjectOutputStream out = new ObjectOutputStream(
          sock.getOutputStream());

      Auto auto = new Auto();
      auto.upali();

      // posalji zahtev
      System.out.println("[Client sent]: Auto");
      out.writeObject(auto);

      // procitaj odgovor
      boolean rez = in.readBoolean();
      System.out.println("[Client received]: " + rez);

      // zatvori konekciju
      in.close();
      out.close();
      sock.close();
    } catch (UnknownHostException e1) {
      e1.printStackTrace();
    } catch (IOException e2) {
      e2.printStackTrace();
    }
  }

}