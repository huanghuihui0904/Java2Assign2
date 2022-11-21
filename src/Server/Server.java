package Server;

import javafx.application.Application;

import javafx.stage.Stage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server extends Application {
  private Socket socket1 = null;
  private Socket socket2 = null;
  private ServerSocket serverSocket = null;


  @Override
  public void start(Stage primaryStage) throws Exception {
    new Thread(() -> {
      threadShop();
    }).start();

  }


  public static void main(String[] args) {
    launch(args);
  }

  public void threadShop() {
    try {
      ServerSocket serverSocket = new ServerSocket(888);
      int i = 0;
      while (true) {

        i++;

        //infinite loop like example in tutorial
        Store storage = new Store();
        storage.strGameState = "eeeeeeeee";
        storage.round = i;
        boolean player1 = true;
        boolean player2 = false;
        while (player1) {
          System.out.println("listen player1");
          Socket socket = serverSocket.accept();
          System.out.println("player1 accept");
          ServerHandler handler = new ServerHandler(socket, storage);
          Thread handlerThread = new Thread(handler);
          handlerThread.start();
          player2 = true;
          break;
        }
        while (player2) {
          System.out.println("listen player2");
          Socket socket = serverSocket.accept();
          System.out.println("player2 accept");

          ServerHandler2 handler2 = new ServerHandler2(socket, storage);
          Thread handlerThread2 = new Thread(handler2);
          handlerThread2.start();
          break;
        }
      }


    } catch (IOException e) {

      System.out.println("cccatch");

    }

  }

}
