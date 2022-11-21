package Client;

import Server.BeforeEnd;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.net.Socket;
import java.util.Optional;

class TicTacToeGame extends Application {

  //Creating private variable to store the Print Stream and DataInputStream for the server and client communication
  private PrintStream printStream;
  //Creating a Socket variable used for creating sockets and threads.
  public Socket socket;

  //Setting the server address and server port.
  public static String localhost = "localhost";
  public static int serverPort = 888;

  public String chractive;
  public String[][] chrstate;
  public Canvas[][] canvas;
  public Stage mystage;

  public static int who = 0;

  public int player;

  public Canvas C00;
  public Canvas C01;
  public Canvas C02;
  public Canvas C10;
  public Canvas C11;
  public Canvas C12;
  public Canvas C20;
  public Canvas C21;
  public Canvas C22;

  public Label label;

  public Label labelTurn;
  public Label labelPlayer;


  //todo game init
  @Override
  public void start(Stage stage) throws Exception {
    System.out.println("who" + who);

    if (who == 0) {

      this.player = 0;

      who++;
      System.out.println("this.player" + this.player);
    } else {
      this.player = 1;
      System.out.println("this.player" + this.player);

    }
    mystage = stage;
    mystage.setOnCloseRequest(new EventHandler<WindowEvent>() {
      @Override
      public void handle(WindowEvent e) {
        Platform.exit();
        System.exit(0);
      }
    });
    //Initializing the canvas objects to represent the squares on the board.
    C00 = new Canvas(100, 100);
    C01 = new Canvas(100, 100);
    C02 = new Canvas(100, 100);
    C10 = new Canvas(100, 100);
    C11 = new Canvas(100, 100);
    C12 = new Canvas(100, 100);
    C20 = new Canvas(100, 100);
    C21 = new Canvas(100, 100);
    C22 = new Canvas(100, 100);

    //Setting the active player to 'x' (so human player goes first).
    chractive = "x";
    //An array of characters that represent the state of the board (so what squares are filled)
    chrstate = new String[][]{{"e", "e", "e"}, {"e", "e", "e"}, {"e", "e", "e"}};

    //A grid pane to place the canvas squares that make up the board.
    GridPane pane = new GridPane();
    pane.add(C00, 0, 0);
    pane.add(C01, 1, 0);
    pane.add(C02, 2, 0);
    pane.add(C10, 0, 1);
    pane.add(C11, 1, 1);
    pane.add(C12, 2, 1);
    pane.add(C20, 0, 2);
    pane.add(C21, 1, 2);
    pane.add(C22, 2, 2);

    //Initializing the socket and input and output streams
    socket = new Socket(localhost, serverPort);
    printStream = new PrintStream(socket.getOutputStream(), true);


    /**
     * To set the actions for each square that is clicked.
     */

    C00.setOnMouseClicked(event -> {
      if (chrstate[0][0] == "e") {
        chrstate[0][0] = chractive;
        try {
          makeMoveGraphic(C00);
          makeMoveBackEnd();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });

    C01.setOnMouseClicked(event -> {
      if (chrstate[0][1] == "e") {
        chrstate[0][1] = chractive;
        try {
          makeMoveGraphic(C01);
          makeMoveBackEnd();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });

    C02.setOnMouseClicked(event -> {
      if (chrstate[0][2] == "e") {
        chrstate[0][2] = chractive;
        try {
          makeMoveGraphic(C02);
          makeMoveBackEnd();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });

    C10.setOnMouseClicked(event -> {
      if (chrstate[1][0] == "e") {
        chrstate[1][0] = chractive;
        try {
          makeMoveGraphic(C10);
          makeMoveBackEnd();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });

    C11.setOnMouseClicked(event -> {
      if (chrstate[1][1] == "e") {
        chrstate[1][1] = chractive;
        try {
          makeMoveGraphic(C11);
          makeMoveBackEnd();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });

    C12.setOnMouseClicked(event -> {
      if (chrstate[1][2] == "e") {
        chrstate[1][2] = chractive;
        try {
          makeMoveGraphic(C12);
          makeMoveBackEnd();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });

    C20.setOnMouseClicked(event -> {
      if (chrstate[2][0] == "e") {
        chrstate[2][0] = chractive;
        try {
          makeMoveGraphic(C20);
          makeMoveBackEnd();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });

    C21.setOnMouseClicked(event -> {
      if (chrstate[2][1] == "e") {
        chrstate[2][1] = chractive;
        try {
          makeMoveGraphic(C21);
          makeMoveBackEnd();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });

    C22.setOnMouseClicked(event -> {
      if (chrstate[2][2] == "e") {
        chrstate[2][2] = chractive;
        try {
          makeMoveGraphic(C22);
          makeMoveBackEnd();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

    });

    canvas = new Canvas[][]{{C00, C01, C02}, {C10, C11, C12}, {C20, C21, C22}};

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        initCanvasBox(canvas[i][j].getGraphicsContext2D());
      }
    }

    stage.setTitle("Player vs Player");
    label = new Label("     waiting......");
    pane.add(label, 3, 1);
    labelTurn = new Label("");
    pane.add(labelTurn, 3, 2);
    labelPlayer = new Label("");
    pane.add(labelPlayer, 3, 0);
    Scene scene = new Scene(pane, 500, 300, Color.WHITE);


    stage.setScene(scene);

    stage.show();

    new Thread(() -> {

      try {
        ThreadShop();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

    }).start();

  }

  public void initCanvasBox(GraphicsContext gc) {
    gc.setFill(Color.BLACK);
    gc.setStroke(Color.BLACK);
    gc.setLineWidth(5);

    gc.strokeLine(0, 0, 0, 100);
    gc.strokeLine(0, 100, 100, 100);
    gc.strokeLine(100, 100, 100, 0);
    gc.strokeLine(100, 0, 0, 0);
  }


  public boolean setX(GraphicsContext gc) {
    gc.setFill(Color.BLACK);
    gc.setStroke(Color.BLACK);
    gc.setLineWidth(5);

    gc.strokeLine(20, 20, 80, 80);
    gc.strokeLine(80, 20, 20, 80);
    return true;
  }

  public boolean setO(GraphicsContext gc) {

    gc.setFill(Color.BLACK);
    gc.setStroke(Color.BLACK);
    gc.setLineWidth(5);

    gc.strokeOval(20, 20, 60, 60);


    return true;
  }


  //todo change char
  private void makeMoveGraphic(Canvas canvas) throws IOException {

    if (chractive == "x") {
      chractive = "o";
      setX(canvas.getGraphicsContext2D());
    } else {
      chractive = "x";
      setO(canvas.getGraphicsContext2D());
    }

  }




//todo alert who win
  public void alertPlayerWin() {
    Platform.runLater(() -> {
      Alert alert= new Alert(Alert.AlertType.CONFIRMATION, "You win!"
            , ButtonType.OK);


      Optional<ButtonType> result = alert.showAndWait();
      System.out.println("000000");


      if(result.get() == ButtonType.OK){

        System.exit(1);
      }


    });
  }
    public void alertPlayerLoss() {
      Platform.runLater(() -> {
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION, "You lose!"
              , ButtonType.OK);


        Optional<ButtonType> result = alert.showAndWait();




        if(result.get() == ButtonType.OK){
          System.exit(1);
        }
      });}
      public void alertPlayerDraw() {
        Platform.runLater(() -> {
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You draw!"
                , ButtonType.OK);


          Optional<ButtonType> result = alert.showAndWait();


            if(result.get() == ButtonType.OK){
              try {
                Goodbye();
              } catch (IOException e) {
                throw new RuntimeException(e);
              }
              System.exit(1);
            }



        });

  }
  public void alertPlayerGoodbye() {
    Platform.runLater(() -> {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Your opponent is offline!\nClick to exit!"
          , ButtonType.OK);


      Optional<ButtonType> result = alert.showAndWait();


      if(result.get() == ButtonType.OK){
        try {
          Goodbye();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        System.exit(1);
      }



    });

  }
  public void Goodbye() throws IOException {
    printStream = new PrintStream(socket.getOutputStream());
    printStream.println("Goodbye");
  }

  public void alertPlayerServiceGoodbye() {
    Platform.runLater(() -> {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The service is offline!\nClick to exit!"
          , ButtonType.OK);


      Optional<ButtonType> result = alert.showAndWait();


      if(result.get() == ButtonType.OK){
        try {
          Goodbye();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        System.exit(1);
      }



    });

  }

  //todo 得到服务器发过来的更新，之后更新自己本地的棋盘
  public void makeMoveBackEnd() throws IOException {
    printStream = new PrintStream(socket.getOutputStream());
    printStream.println(chrstate[0][0] + chrstate[0][1] + chrstate[0][2] + chrstate[1][0] + chrstate[1][1] +
        chrstate[1][2] + chrstate[2][0] + chrstate[2][1] + chrstate[2][2]);
  }

  public void updateState(int round) {
    Platform.runLater(() -> {
      label.setText("     playing in room " + round);
    });

  }

  public void updateTurnOn() {
    Platform.runLater(() -> {
      labelTurn.setText("     It is your turn!");
    });

  }

  public void updateTurnOff() {
    Platform.runLater(() -> {
      labelTurn.setText("     It is not your turn!");
    });

  }
  public void whichPlayer(int i) {
    Platform.runLater(() -> {
      labelPlayer.setText("     You are Player "+i);
    });

  }

  public void update(String strNewState) throws InterruptedException {
    if (strNewState == "eeeeeeeee") {
      //System.exit(0);
    }

    if (strNewState.charAt(0) == 'o') {
      chrstate[0][0] = "o";
      setO(canvas[0][0].getGraphicsContext2D());
    } else if (strNewState.charAt(0) == 'x') {
      chrstate[0][0] = "x";
      setX(canvas[0][0].getGraphicsContext2D());
    }

    if (strNewState.charAt(1) == 'o') {
      chrstate[0][1] = "o";
      setO(canvas[0][1].getGraphicsContext2D());
    } else if (strNewState.charAt(1) == 'x') {
      chrstate[0][1] = "x";
      setX(canvas[0][1].getGraphicsContext2D());
    }

    if (strNewState.charAt(2) == 'o') {
      chrstate[0][2] = "o";
      setO(canvas[0][2].getGraphicsContext2D());
    } else if (strNewState.charAt(2) == 'x') {
      chrstate[0][2] = "x";
      setX(canvas[0][2].getGraphicsContext2D());
    }

    if (strNewState.charAt(3) == 'o') {
      chrstate[1][0] = "o";
      setO(canvas[1][0].getGraphicsContext2D());
    } else if (strNewState.charAt(3) == 'x') {
      chrstate[1][0] = "x";
      setX(canvas[1][0].getGraphicsContext2D());
    }

    if (strNewState.charAt(4) == 'o') {
      chrstate[1][1] = "o";
      setO(canvas[1][1].getGraphicsContext2D());
    } else if (strNewState.charAt(4) == 'x') {
      chrstate[1][1] = "x";
      setX(canvas[1][1].getGraphicsContext2D());
    }

    if (strNewState.charAt(5) == 'o') {
      chrstate[1][2] = "o";
      setO(canvas[1][2].getGraphicsContext2D());
    } else if (strNewState.charAt(5) == 'x') {
      chrstate[1][2] = "x";
      setX(canvas[1][2].getGraphicsContext2D());
    }

    if (strNewState.charAt(6) == 'o') {
      chrstate[2][0] = "o";
      setO(canvas[2][0].getGraphicsContext2D());
    } else if (strNewState.charAt(6) == 'x') {
      chrstate[2][0] = "x";
      setX(canvas[2][0].getGraphicsContext2D());
    }

    if (strNewState.charAt(7) == 'o') {
      chrstate[2][1] = "o";
      setO(canvas[2][1].getGraphicsContext2D());
    } else if (strNewState.charAt(7) == 'x') {
      chrstate[2][1] = "x";
      setX(canvas[2][1].getGraphicsContext2D());
    }

    if (strNewState.charAt(8) == 'o') {
      chrstate[2][2] = "o";
      setO(canvas[2][2].getGraphicsContext2D());
    } else if (strNewState.charAt(8) == 'x') {
      chrstate[2][2] = "x";
      setX(canvas[2][2].getGraphicsContext2D());
    }

//    checkForWin();
  }

  //todo new my game
  public void ThreadShop() throws IOException {
    printStream = new PrintStream(socket.getOutputStream());
    new BeforeEnd(printStream);

    TicTacToeGame a = this;

    try {
      ClientHandler handler = new ClientHandler(socket, a);

      Thread handlerThread = new Thread(handler);
      handlerThread.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }



}
