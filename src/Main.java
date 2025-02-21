public class Main {
    public static int getHighestScore(int trials, long timePerMove) {
        int hScore = 0;
        int bMove;
        SnakeBoard board;
        Node solver;
        for(int i = 0; i < trials; i++) {
            board = new SnakeBoard();
            solver = new Node(board);
            do {
                bMove = solver.iterDSolve(timePerMove);
            } while(board.doTick(bMove, false));
            System.out.println("Generation: " + i + "\nScore: " + board.score + "\n");
            hScore = Math.max(hScore, board.score);
        } return hScore;
    }
	public static void main(String[] args) {
	    //System.out.println("Best Score: " + getHighestScore(20, 50));
	    
	    
	    int bestMove;
	    SnakeBoard game = new SnakeBoard();
	    Node solver = new Node(game);
	   // for(int i = 0; i < 10; i++)
	   //     System.out.println(solver.speedTest(13) + "ms");
	    do {
	        System.out.println(game);
	        //if(game.score < 20) bestMove = solver.iterDSolve(1);
	        //else if(game.score < 40) bestMove = solver.iterDSolve(30);
	        //else if(game.score < 60)
	        bestMove = solver.iterDSolve(10);
	    }
	    while(game.doTick(bestMove, false));
	    System.out.println(game);
	    System.out.println("Score: " + game.score);
	}
}
