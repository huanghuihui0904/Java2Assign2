package Client;

import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
  //A private Socket variable used for creating sockets for the server
  private Socket socket = null;
  private BufferedReader requestInput = null;
  private DataOutputStream responseOutput = null;
  private TicTacToeGame myGame;

  public ClientHandler(Socket socket, TicTacToeGame myGame) throws IOException {
    this.socket = socket;
    requestInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    responseOutput = new DataOutputStream(socket.getOutputStream());
    this.myGame = myGame;

  }

  public void run() {
    String strLine;
    while (true) {

      try {
        if ((strLine = requestInput.readLine()) != null) {
         if (strLine.startsWith("p1in")) {
            int room = Integer.parseInt(strLine.replace("p1in", ""));
            myGame.updateState(room);
            myGame.updateTurnOn();
            myGame.whichPlayer(1);
            myGame.C00.setDisable(false);
            myGame.C01.setDisable(false);
            myGame.C02.setDisable(false);
            myGame.C10.setDisable(false);
            myGame.C11.setDisable(false);
            myGame.C12.setDisable(false);
            myGame.C20.setDisable(false);
            myGame.C21.setDisable(false);
            myGame.C22.setDisable(false);
          } else if (strLine.startsWith("p2in")) {
            int room = Integer.parseInt(strLine.replace("p2in", ""));

            myGame.updateState(room);
            myGame.updateTurnOff();
            myGame.whichPlayer(2);
            myGame.C00.setDisable(true);
            myGame.C01.setDisable(true);
            myGame.C02.setDisable(true);
            myGame.C10.setDisable(true);
            myGame.C11.setDisable(true);
            myGame.C12.setDisable(true);
            myGame.C20.setDisable(true);
            myGame.C21.setDisable(true);
            myGame.C22.setDisable(true);

          } else if (strLine.equals("p1lock")) {
            myGame.waiting();
            myGame.C00.setDisable(true);
            myGame.C01.setDisable(true);
            myGame.C02.setDisable(true);
            myGame.C10.setDisable(true);
            myGame.C11.setDisable(true);
            myGame.C12.setDisable(true);
            myGame.C20.setDisable(true);
            myGame.C21.setDisable(true);
            myGame.C22.setDisable(true);
          } else if (strLine == "eeeeeeeee") {
            System.out.println("Reset Instruction Received.");
            //Closing the connection with the client once the board is empty (since new game starts now)
            System.exit(0);
          } else if (strLine.equals("lock")) {
            System.out.println(strLine);
            myGame.updateTurnOff();

            myGame.C00.setDisable(true);
            myGame.C01.setDisable(true);
            myGame.C02.setDisable(true);
            myGame.C10.setDisable(true);
            myGame.C11.setDisable(true);
            myGame.C12.setDisable(true);
            myGame.C20.setDisable(true);
            myGame.C21.setDisable(true);
            myGame.C22.setDisable(true);

          } else if (strLine.equals("win")) {
            myGame.alertPlayerWin();

          } else if (strLine.equals("loss")) {
            myGame.alertPlayerLoss();

          } else if (strLine.equals("draw")) {
            myGame.alertPlayerDraw();
          } else if (strLine.equals("gameover")) {
            myGame.alertPlayerGoodbye();
          } else {
            //todo 收到更新
            System.out.println("Update Received: " + strLine);
            myGame.update(strLine);
            myGame.updateTurnOn();

            if (myGame.chractive == "x") {
              myGame.chractive = "o";
            } else {
              myGame.chractive = "x";
            }
            myGame.C00.setDisable(false);
            myGame.C01.setDisable(false);
            myGame.C02.setDisable(false);
            myGame.C10.setDisable(false);
            myGame.C11.setDisable(false);
            myGame.C12.setDisable(false);
            myGame.C20.setDisable(false);
            myGame.C21.setDisable(false);
            myGame.C22.setDisable(false);


          }
        }
      } catch (IOException e) {
     break;

      } catch (InterruptedException e) {
        break;
      }catch (Exception e){
        break;
      }

    }
    myGame.alertPlayerServiceGoodbye();
  }
}