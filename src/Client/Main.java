package Client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


import java.io.*;
import java.util.Optional;
import java.util.Scanner;

public class Main extends Application {

    private Canvas canvas;
    private double screenWidth = 320;
    private double screenHeight = 500;

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        Scene mainScene;
        Group root = new Group();

        FileInputStream input = new FileInputStream("D:\\文件资料\\计算机系统设计与应用\\assi2\\others\\Multithreaded-Tic-Tac-Toe-Game\\GroupAssignment\\resources\\images\\gumbo.png");
        javafx.scene.image.Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(200);

        canvas = new Canvas();
        canvas.widthProperty().bind(primaryStage.widthProperty());
        canvas.heightProperty().bind(primaryStage.heightProperty());
        root.getChildren().add(canvas);

        FileInputStream inputLogo = new FileInputStream("D:\\文件资料\\计算机系统设计与应用\\assi2\\others\\Multithreaded-Tic-Tac-Toe-Game\\GroupAssignment\\resources\\images\\images.png");
        javafx.scene.image.Image icon = new Image(inputLogo);
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Ultimate Tic-Tac-Toe Game");

        GridPane grid = new GridPane();

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        javafx.scene.control.Button login = new javafx.scene.control.Button("Login");
        login.setPrefWidth(200);

        javafx.scene.control.Button exit = new javafx.scene.control.Button("Exit");
        exit.setPrefWidth(200);


        grid.add(imageView, 0,0);
        grid.add(login, 0,5);

        grid.add(exit, 0,6);



        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField name = new TextField();
        name.setPromptText("Enter your name.");
        grid.add(name, 0, 2);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 3);

        TextField passwordField = new TextField();
        passwordField.setPromptText("Enter your password.");
        grid.add(passwordField, 0, 4);

        grid.setStyle("-fx-background-color: #a52424;");


        mainScene = new Scene(grid, screenWidth, screenHeight);

        login.setOnAction(new EventHandler<ActionEvent>() {
            //todo new tictactoegame
            @Override
            public void handle(ActionEvent actionEvent) {
                if(check(name.getText().trim(),passwordField.getText().trim())){
                    //Calling the TicTacToeGame class to start the player vs player game
                    TicTacToeGame mainScene = new TicTacToeGame();
                    try {
                        mainScene.start(primaryStage);
                        //mainScene.start(primaryStage);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }else {
                    alertPlayerLoginAgain();
                }




            }
        });


        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Closing the stage when called to close the window
                primaryStage.close();
            }
        });
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });






        primaryStage.setScene(mainScene);
        primaryStage.show();

    }
    public void alertPlayerLoginAgain() {
        Platform.runLater(() -> {
            Alert alert= new Alert(Alert.AlertType.CONFIRMATION,
                "The user does not exist or the password is incorrect!\nClick for login again!", ButtonType.OK);

            Optional<ButtonType> result = alert.showAndWait();
            System.out.println("000000");


            if(result.get() == ButtonType.OK){


            }


        });
    }
    public boolean check(String name, String password){
        System.out.println(name);
        System.out.println(password);

        String fileName = "D:\\文件资料\\计算机系统设计与应用\\assi2\\others\\Multithreaded-Tic-Tac-Toe-Game\\GroupAssignment\\resources\\login";
        try (Scanner sc = new Scanner(new FileReader(fileName))) {
            while (sc.hasNextLine()) {  //按行读取字符串
                String line = sc.nextLine();
                String[] pair=line.split(":");
                if(pair[0].equals(name)&&pair[1].equals(password)){
                    return true;
                }
                System.out.println(line+"99");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
    /**
     * A main function to run the java application
     * @param args  A string array that holds the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }


}


