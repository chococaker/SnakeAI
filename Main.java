import java.util.ArrayList;
import java.util.Scanner;

class SnakeBoard {
	// gameboard:
	// 0 = empty 1 = apple 2 = body 3 = head
	int[][] board;
	// direction the snake is facing 0 = N 1 = E 2 = S 3 = W
	int direction, headX, headY, appleX, appleY, score, moves;
	// arraylist for the body
	ArrayList<int[]> body = new ArrayList<int[]>();
	// initializes the snakeboard to starting position
	public SnakeBoard() {
		board = new int[10][9];
		headX = 3;
		headY = 4;
		body.add(new int[] {3, 4});
		board[3][4] = 3;
		board[7][4] = 1;
		appleX = 7;
		appleY = 4;
		direction = 1;
		score = 0; 
		moves = 0;
	}
	
	private SnakeBoard(int[] vals, ArrayList<int[]> bodyCopy, int[][] boardCopy) {
	    board = boardCopy;
	    body = bodyCopy;
	    direction = vals[0];
	    headX = vals[1];
	    headY = vals[2];
	    appleX = vals[3];
	    appleY = vals[4];
	    score = vals[5];
	    moves = vals[6];
	}
	
	public SnakeBoard deepClone() {
	    int[][] boardCopy = new int[10][];
	    for(int i = 0; i < 10; i++) {
	        boardCopy[i] = board[i].clone();
	    }
	    ArrayList<int[]> bodyCopy = new ArrayList<int[]>();
	    for(int[] tile : body) {
	        bodyCopy.add(tile.clone());
	    }
	    int[] v = {direction, headX, headY, appleX, appleY, score, moves};
	    return new SnakeBoard(v, bodyCopy, boardCopy);
	}

	// prints out the snakeboard for the user
	public String toString() {
		String s = "\n     Score: " + score + "\n";
		for(int y = 8; y > -1; y--) {
			for(int x = 0; x < 10; x++) {
				switch(board[x][y]) {
				case 0:
					s += "- ";
					break;
				case 1:
					s += "a ";
					break;
				case 2:
					s += "o ";
					break;
				case 3:
					s += "h ";
					break;
				}
			}
			s += "\n";
		}
		return s;
	}

	public boolean doTick(int nDirection, boolean isCalc) {
	    if(score == 89) return false;
	    direction = nDirection;
		board[headX][headY] = 2;
		switch(direction) {
		case 0:
			headY++;
			break;
		case 1:
			headX++;
			break;
		case 2:
			headY--;
			break;
		case 3:
			headX--;
		}
		body.add(new int[] {headX, headY});
		if(headX > 9 || headX < 0 || headY > 8 || headY < 0) return false;
		if(board[headX][headY] == 2 && !(body.get(0)[0] == headX && body.get(0)[1] == headY)) return false;
		if(board[headX][headY] == 1) {
		    score++;
		    if(!isCalc && score < 89) {
			    do {
				    appleX = (int)(Math.random()*10);
				    appleY = (int)(Math.random()*9);
			    }
			    while(board[appleX][appleY] != 0);
			    board[appleX][appleY] = 1;
		    }
		}
		else {
			int[] tail = body.remove(0);
			board[tail[0]][tail[1]] = 0;
		}
		board[headX][headY] = 3;
		moves++;
		return true;
	}
}

class Node {
    SnakeBoard board;
    public Node(SnakeBoard b) {
        board = b;
    }
     // iterative deepening for time managament
    // finishes layer after time is exceeded
    public int iterDSolve(long timeMS) {
        long start = System.nanoTime();
        long end = System.nanoTime();
        int curDepth = 1;
        int curMove = 0;
        while((end-start) / 1000000 < timeMS) {
            curMove = getBestMove(curDepth);
            curDepth++;
            end = System.nanoTime();
        }
        System.out.println(curDepth-1);
        return curMove;
    }

    public int getBestMove(int depth) {
        int curScore;
        int bestScore = -1;
        int bestMove = 0;
        int dSkip = -1;
        switch(this.board.direction) {
            case 0: dSkip = 2; break;
            case 1: dSkip = 3; break;
            case 2: dSkip = 0; break;
            case 3: dSkip = 1; break;
        }
        for(int i = 0; i < 4; i++) {
            if(i == dSkip) continue;
            Node child = new Node(this.board.deepClone());
            if(child.board.doTick(i, true)) {
                // System.out.println("Simulated Survival: " + i);
                // System.out.println(child.board);
                curScore = solve(child, depth-1);
            }
            else {
                // System.out.println("Simulated Death: " + i);
                // System.out.println(child.board);
                curScore = child.board.score*100000+child.board.moves*100;
            }
            if(curScore > bestScore) {
                bestScore = curScore;
                bestMove = i;
            }
            else if(curScore == bestScore && Math.random() < 0.4) {
                bestScore = curScore;
                bestMove = i;
            }
        }
        //System.out.println(bestMove + " " + bestScore);
        return bestMove;
    }
    
    public static int solve(Node n, int depth) {
        if(depth == 0) return 10000000+n.board.score*100000+n.board.moves*100;
        int bestScore = -1;
        int dSkip = -1;
        switch(n.board.direction) {
            case 0: dSkip = 2; break;
            case 1: dSkip = 3; break;
            case 2: dSkip = 0; break;
            case 3: dSkip = 1; break;
        }
        for(int i = 0; i < 4; i++) {
            if(i == dSkip) continue;
            Node child = new Node(n.board.deepClone());
            if(child.board.doTick(i, true)) {
                bestScore = Math.max(bestScore, solve(child, depth-1));
            }
            else {
                bestScore = Math.max(bestScore, child.board.score*100000+child.board.moves*100);
            }
        }
        return bestScore+n.board.score;
    }
}
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
	    Scanner s = new Scanner(System.in);
	    do {
	        System.out.println(game);
	        //if(game.score < 20) bestMove = solver.iterDSolve(1);
	        //else if(game.score < 40) bestMove = solver.iterDSolve(30);
	        //else if(game.score < 60)
	        if(game.score < 85) bestMove = solver.iterDSolve(10);
	        else bestMove = solver.getBestMove(1000);
	    }
	    while(game.doTick(bestMove, false));
	    System.out.println(game);
	    System.out.println("Score: " + game.score);
	    s.close();
	}
}

