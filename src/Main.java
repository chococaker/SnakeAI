// main class
public class Main {
	public static void main(String[] args) {
	    int bestMove;
	    SnakeBoard game = new SnakeBoard();
	    Node solver = new Node(game);
	    // solver chooses what it thinks is the best move until the game is over
	    do {
	        System.out.println(game);
	        bestMove = solver.iterDSolve(100);
	    }
	    while(game.doTick(bestMove, false));
	    System.out.println(game);
	    System.out.println("Score: " + game.score);
	}
}
