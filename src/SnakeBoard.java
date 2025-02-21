// class for a snakeboard
class SnakeBoard {
	// gameboard + body
	// 0 = empty 1 = apple 2 = body 3 = head
	// body in form [x][y] with a head and tail pointer
	int[][] board, body;
	int bodyPoint1, bodyPoint2;
	// direction the snake is facing 0 = N 1 = E 2 = S 3 = W
	int direction, headX, headY, appleX, appleY, score, moves;
	// initializes the snakeboard to starting position
	public SnakeBoard() {
		board = new int[10][9];
		body = new int[91][2];
		bodyPoint1 = 0;
		bodyPoint2 = 1;
		body[0] = new int[]{3, 4};
		headX = 3;
		headY = 4;
		board[3][4] = 3;
		board[7][4] = 1;
		appleX = 7;
		appleY = 4;
		direction = 1;
		score = 0; 
		moves = 0;
	}
	
	// private constructor for deep cloning
	private SnakeBoard(int[] vals, int[][] bodyCopy, int[][] boardCopy) {
	    board = boardCopy;
	    body = bodyCopy;
	    direction = vals[0];
	    headX = vals[1];
	    headY = vals[2];
	    appleX = vals[3];
	    appleY = vals[4];
	    score = vals[5];
	    moves = vals[6];
	    bodyPoint1 = vals[7];
	    bodyPoint2 = vals[8];
	}
	
	public SnakeBoard deepClone() {
	    int[][] boardCopy = new int[10][];
	    for(int i = 0; i < 10; i++) {
	        boardCopy[i] = board[i].clone();
	    }
	    int[][] bodyCopy = new int[91][];
	    for(int i = 0; i < 91; i++) {
	        bodyCopy[i] = body[i].clone();
	    }
	    int[] v = {direction, headX, headY, appleX, appleY, score, moves, bodyPoint1, bodyPoint2};
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
	    bodyPoint2++;
	    if(bodyPoint2 > 90) bodyPoint2 = 0;
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
		body[bodyPoint2] = new int[]{headX, headY};
		if(headX > 9 || headX < 0 || headY > 8 || headY < 0) return false;
		if(board[headX][headY] == 2 && !(body[bodyPoint1][0] == headX && body[bodyPoint1][1] == headY)) return false;
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
		    //System.out.println(bodyPoint1 + "  " + bodyPoint2);
			board[body[bodyPoint1][0]][body[bodyPoint1][1]] = 0;
			bodyPoint1++;
			if(bodyPoint1 > 90) bodyPoint1 = 0;
		}
		board[headX][headY] = 3;
		moves++;
		return true;
	}
}
