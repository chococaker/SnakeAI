public final class SpeedTester {
  private SpeedTester() {}
  
  public static long deepCloneS() {
      SnakeBoard board = new SnakeBoard();
      SnakeBoard temp;
      long startTime = System.nanoTime();
      for(int i = 0; i < 1000000; i++) {
          temp = board.deepClone();
      }
      return System.nanoTime()-startTime;
  }
  public static long doTickS() {
      SnakeBoard board = new SnakeBoard();
      SnakeBoard temp;
      long totalTime = 0;
      long startTime = System.nanoTime();
      long startTime2;
      for(int i = 0; i < 1000000; i++) {
          startTime2 = System.nanoTime();
          temp = board.deepClone();
          totalTime += startTime2-System.nanoTime();
          temp.doTick(1, true);
      }
      return totalTime + (System.nanoTime()-startTime);
  }
  public static long doTickE() {
      SnakeBoard board = new SnakeBoard();
      Node lengthAdvancer = new Node(board);
      while(board.score < 80) {
          if(!board.doTick(lengthAdvancer.iterDSolve(10), false)) {
              board = new SnakeBoard();
              lengthAdvancer = new Node(board);
          }
      }
      SnakeBoard temp;
      long totalTime = 0;
      long startTime = System.nanoTime();
      long startTime2;
      for(int i = 0; i < 1000000; i++) {
          startTime2 = System.nanoTime();
          temp = board.deepClone();
          totalTime += startTime2-System.nanoTime();
          temp.doTick(1, true);
      }
      return totalTime + (System.nanoTime()-startTime);
  }
  public static long getBestMoveS() {
      SnakeBoard board = new SnakeBoard();
      Node test = new Node(board);
      long startTime = System.nanoTime();
      test.getBestMove(17);
      return System.nanoTime()-startTime;
  }
  public static long getBestMoveE() {
      SnakeBoard board = new SnakeBoard();
      Node lengthAdvancer = new Node(board);
      while(board.score < 80) {
          if(!board.doTick(lengthAdvancer.iterDSolve(10), false)) {
              board = new SnakeBoard();
              lengthAdvancer = new Node(board);
          }
      }
      Node test = new Node(board);
      long startTime = System.nanoTime();
      test.getBestMove(70);
      return System.nanoTime()-startTime;
  }
}
