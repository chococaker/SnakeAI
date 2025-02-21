public class SpeedTester {
  public SpeedTester() {}
  
  public long deepCloneS() {
      long startTime = System.nanoTime();
      SnakeBoard board = new SnakeBoard();
      SnakeBoard temp;
      for(int i = 0; i < 100000; i++) {
          temp = board.deepClone();
      }
      return System.nanoTime()-startTime;
  }
  public long deepCloneE() {
      SnakeBoard board = new SnakeBoard();
      Node lengthAdvancer = new Node(board);
      while(board.score < 80) {
          if(!board.doTick(lengthAdvancer.iterDSolve(10), false)) {
              System.out.println(board.score);
              board = new SnakeBoard();
          }
      }
      long startTime = System.nanoTime();
      SnakeBoard temp;
      for(int i = 0; i < 100000; i++) {
          temp = board.deepClone();
      }
      return System.nanoTime()-startTime;
  }
  //public long doTickS() {}
  //public long doTickE() {}
  //public long getBestMoveS() {}
  //public long getBestMoveE() {}
}
