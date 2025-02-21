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
        while((end-start) / 1000000 < timeMS && curDepth < 1000) {
            curMove = getBestMove(curDepth);
            curDepth += curDepth/8+1;
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
                curScore = child.board.score>>17+child.board.moves>>8;
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
        if(depth == 0) return 100000000+n.board.score>>17+n.board.moves>>8;
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
                bestScore = Math.max(bestScore, child.board.score>>17+child.board.moves>>8);
            }
        }
        return bestScore+n.board.score;
    }
    // returns value in ms
    public int speedTest(int depth) {
        long startTime = System.nanoTime();
        getBestMove(depth);
        long endTime = System.nanoTime();
        return (int)((endTime-startTime) / 1000000);
    }
}
