package Server;

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.util.NoSuchElementException;
import java.util.Date;

public class ServerHandler2 implements Runnable {

  private Socket socket = null;
  private BufferedReader requestInput = null;
  private PrintStream responseOutput = null;
  private Store myServer;

  public ServerHandler2(Socket socket, Store myServer) throws IOException {
    this.socket = socket;
    requestInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    responseOutput = new PrintStream(socket.getOutputStream(), true);
    this.myServer = myServer;
    System.out.println("Connected Player 2 in init");

  }

  @Override
  public void run() {
    new BeforeEnd(myServer);
    try {
      myServer.state = "p";
      responseOutput = new PrintStream(socket.getOutputStream(), true);
      responseOutput.println("p2in" + myServer.round);

      System.out.println("player2" + myServer.state);
      System.out.println("Connected Player 2");
      String strLine;
      String strPrev = "eeeeeeeee";
      while (true) {

        if (myServer.step % 2 != 0) {

          if ((strLine = requestInput.readLine()) != null) {
            if (strLine.equals("playeroffline")) {
              System.out.println("player2 offline");
              myServer.player2On = false;

            }

            System.out.println("player2-----" + strLine);
            myServer.strGameState = strLine;
            System.out.println("p2 strGame updated!");
            responseOutput = new PrintStream(socket.getOutputStream(), true);
            responseOutput.println("lock");
            strPrev = strLine;
            System.out.println(myServer.step);
            myServer.step++;
            String checkWin = checkForWin(strPrev);

            if (checkWin.equals("continue")) {

            } else {
              responseOutput = new PrintStream(socket.getOutputStream(), true);
              responseOutput.println("lock");
              responseOutput = new PrintStream(socket.getOutputStream(), true);
              responseOutput.println(checkWin);
              break;
            }

          }
        } else {
          if (myServer.player1On == false) {
            System.out.println("detect player1 offline");
            responseOutput = new PrintStream(socket.getOutputStream(), true);
            responseOutput.println("lock");
            responseOutput = new PrintStream(socket.getOutputStream(), true);
            responseOutput.println("gameover");
            break;
          }
          if (strPrev != myServer.strGameState) {

            System.out.println("p2 update detected!");

            responseOutput = new PrintStream(socket.getOutputStream(), true);

            responseOutput.println(myServer.strGameState);
            strPrev = myServer.strGameState;
            String checkWin = checkForWin(strPrev);
            if (checkWin.equals("continue")) {

            } else {
              responseOutput = new PrintStream(socket.getOutputStream(), true);
              responseOutput.println("lock");
              responseOutput = new PrintStream(socket.getOutputStream(), true);
              responseOutput.println(checkWin);
              break;
            }
          }
        }


      }
    } catch (IOException e) {

      System.out.println("player2 offline");
      myServer.player2On = false;
    } catch (Exception e) {

    }

  }

  public String checkForWin(String str) {
    for (int i = 0; i <= 6; i = i + 3) {
      if (str.charAt(0 + i) == 'x' && str.charAt(1 + i) == 'x' && str.charAt(2 + i) == 'x') {
        return "loss";
      } else if (str.charAt(0 + i) == 'o' && str.charAt(1 + i) == 'o' && str.charAt(2 + i) == 'o') {
        return "win";
      }
    }
    for (int i = 0; i <= 2; i++) {
      if (str.charAt(0 + i) == 'x' && str.charAt(3 + i) == 'x' && str.charAt(6 + i) == 'x') {
        return "loss";
      } else if (str.charAt(0 + i) == 'o' && str.charAt(3 + i) == 'o' && str.charAt(6 + i) == 'o') {
        return "win";
      }
    }
    if (str.charAt(0) == 'x' && str.charAt(4) == 'x' && str.charAt(8) == 'x') {
      return "loss";
    } else if (str.charAt(0) == 'o' && str.charAt(4) == 'o' && str.charAt(8) == 'o') {
      return "win";
    }
    if (str.charAt(2) == 'x' && str.charAt(4) == 'x' && str.charAt(6) == 'x') {
      return "loss";
    } else if (str.charAt(2) == 'o' && str.charAt(4) == 'o' && str.charAt(6) == 'o') {
      return "win";
    }
    boolean checkFull = true;
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == 'e') {
        checkFull = false;
      }
    }
    if (checkFull) {
      return "draw";
    }
    return "continue";
  }

}