package Server;

import java.io.PrintStream;

public class BeforeEnd {//程序退出事件处理

  public BeforeEnd(Store myServer) {
    myServer.serverOn = false;
  }

  public BeforeEnd(Store myServer, int which, boolean state) {
    //模拟处理时间
    Thread t = new Thread(() -> {
      System.out.println("beforeend");
      if (which == 1) {
        myServer.player1On = state;
      } else {
        myServer.player2On = state;
      }

    });
    Runtime.getRuntime().addShutdownHook(t);
  }

  public BeforeEnd(PrintStream responseOutput) {
    //模拟处理时间
    Thread t = new Thread(() -> {
      System.out.println("tttttttbeforeend");
//      responseOutput = new PrintStream(socket.getOutputStream(), true);
      responseOutput.println("playeroffline");

    });
    Runtime.getRuntime().addShutdownHook(t);
  }
}

