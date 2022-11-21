package Server;

public class Store {
  //A private volatile String to store the game state
  public volatile String strGameState;

  public String state = "waiting....";
  public int round = 0;
  public int step = 0;
  public boolean player1On = true;
  public boolean player2On = true;
  public boolean serverOn = true;
}
