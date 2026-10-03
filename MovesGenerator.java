import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MovesGenerator {
    public static int count =0;

    public static Random random = new Random();
    public static List<Integer> from = new ArrayList<>();
    public static List<Integer> who = new ArrayList<>();
    public static List<Integer> where = new ArrayList<>();

    public static int PosInAray = 0;
    public static int bestMove = 0;
    public static int bestMoveEvaluation = 0;

    public static int depth = 3;

    public static List<Move> generateBlackMoves(){
        List<Move> moves = new ArrayList<>();
        PosInAray = 0;
        bestMove = 0;
        bestMoveEvaluation = Integer.MAX_VALUE;

        MovementManager.updateBlackBoard();
        saveState();
        for (int i =0 ;i<64;i++){
            if (((Board.blackBoard >>> i) & 1L) != 0){

                
                Board.atackSquares = 0;
                Board.captureSquares = 0;
                int pieceType = MovementManager.returnPiece(i);
                if (pieceType == 7){
                    MovementManager.BPMouvement(i);
                }
                if (pieceType == 8) {
                    MovementManager.BBMouvement(i);
                }
                if (pieceType == 9) {
                    MovementManager.BRMouvement(i);
                }
                if (pieceType == 10) {
                    MovementManager.BQMouvement(i);
                }
                if (pieceType == 11) {
                    MovementManager.BKNMovement(i);
                }
                if (pieceType == 12) {
                    MovementManager.BKMovement(i);
                }

                if (Board.atackSquares ==0){
                    continue;
                }

                for (int r = 0; r < 64; r++) {
                    if (((Board.atackSquares >>> r) & 1L) != 0) {

                        PosInAray++;
                        moves.add(new Move(i, r, pieceType));
                        resetState();

                    }

                }

            }
        }
        return moves;
        
    }

    public static List<Move> generateWhiteMoves(){
        List<Move> moves = new ArrayList<>();
        PosInAray =0;
        bestMove = 0;
        bestMoveEvaluation=Integer.MIN_VALUE;

        MovementManager.updateWhiteBoard();
        saveState();
        for (int i = 0 ; i<64 ; i++){
            Board.atackSquares =0;
            if (((Board.whiteBoard >>> i)& 1L) ==1){
                int piece = MovementManager.returnPiece(i);
                if (piece == 1){
                    MovementManager.WPMouvement(i);
                }if (piece == 2){
                    MovementManager.WBMouvement(i);
                }if (piece == 3){
                    MovementManager.WRMouvement(i);
                }if (piece == 4){
                    MovementManager.WQMouvement(i);
                }if (piece == 5){
                    MovementManager.WKNMovement(i);
                }if (piece == 6){
                    MovementManager.WKMovement(i);
                }
                
                if (Board.atackSquares != 0) {

                    for (int r = 0; r < 64; r++) {
                        if (((Board.atackSquares >>> r) & 1L) != 0) {
                            PosInAray++;
                            moves.add(new Move(i,r,piece));

                            resetState();
                        }
                    }

                }
            }
        }
        return moves;
        
    }

    public static int shearch(int depth , int alpha , int beta){
        if (depth == 0 || MovementManager.gameFinished){
            return EvaluationFuncion.Evaluate();
        }

        boolean maximizing =MovementManager.turn;

        List<Move> candidates;

        int best = maximizing ?Integer.MIN_VALUE :Integer.MAX_VALUE  ;

        if (maximizing) {
            candidates = generateWhiteMoves();
        } else {
            candidates = generateBlackMoves();
        }

        for (Move move : candidates){
            
            Board.State boardState = Board.captureState();
            MovementManager.State managerState = MovementManager.captureState();

            try{
                Move(move);
                int score = shearch(depth - 1 , alpha , beta);


                if (maximizing){
                    best = Math.max(best, score);
                    alpha = Math.max(alpha , best);
                }else{
                    best = Math.min(best , score);
                    beta = Math.min(beta , best);
                }

            }finally{
                MovementManager.resetState(managerState);
                Board.resetState(boardState);
            }

            if (alpha > beta){break;}
        }
        return best;
    }

    public static void play(){
        Move(findBestMove(depth));

    }

    public static Move findBestMove(int depth){
        boolean maximizing =MovementManager.turn;

        List<Move> candidates;
        int score ;
        Move best = null;

        if (maximizing) {
            candidates = generateWhiteMoves();
        } else {
            candidates = generateBlackMoves();
        }
        int bestScore = maximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (Move move : candidates){
            System.out.println("working");
            Board.State boardState = Board.captureState();
            MovementManager.State managerState = MovementManager.captureState();

            try{
                Move(move);
                score =shearch(depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE);

            }finally{
                MovementManager.resetState(managerState);
                Board.resetState(boardState);
            }

            if ( maximizing && (score > bestScore) || !maximizing && (score <bestScore)){
                bestScore = score;
                best = move;
            }

        }
        System.out.println("finished ");
        return best;
    }

    public static void saveState(){
        MovementManager.saveState();
        Board.saveState();
    }

    public static void resetState(MovementManager.State mmState, Board.State boardState){
        MovementManager.resetState(mmState);
        Board.resetState(boardState);
    }

    public static void resetState(){
        MovementManager.resetState();
        Board.resetState();
    }

    public static void Move(Move move){

        MovementManager.showAtackSquare(move.piece(),move.from());
        MovementManager.movePiece(move.to());

    }


}