import java.util.ArrayList;
import java.util.List;

public class MovesGenerator {
    private static final int DEFAULT_DEPTH = 8;

    public static List<Move> generateBlackMoves(){
        List<Move> moves = new ArrayList<>();

        MovementManager.INSTANCE.updateBlackBoard();
        saveState();
        for (int i =0 ;i<64;i++){
            if (((Board.blackBoard >>> i) & 1L) != 0){

                
                Board.atackSquares = 0;
                Board.captureSquares = 0;
                int pieceType = MovementManager.INSTANCE.returnPiece(i);
                switch (pieceType) {
                    case 7 -> MovementManager.INSTANCE.BPMouvement(i);
                    case 8 -> MovementManager.INSTANCE.BBMouvement(i);
                    case 9 -> MovementManager.INSTANCE.BRMouvement(i);
                    case 10 -> MovementManager.INSTANCE.BQMouvement(i);
                    case 11 -> MovementManager.INSTANCE.BKNMovement(i);
                    case 12 -> MovementManager.INSTANCE.BKMovement(i);
                    default -> { }
                }

                if (Board.atackSquares ==0){
                    continue;
                }

                for (int r = 0; r < 64; r++) {
                    if (((Board.atackSquares >>> r) & 1L) != 0) {
                        if (((Board.captureSquares >>> r) & 1L) != 0){
                            moves.add(0,new Move(i, r, pieceType));
                            resetState();
                        }else{
                            moves.add(new Move(i, r, pieceType));
                            resetState();
                        }
                        
                    }
                }

            }
        }
        return moves;
        
    }

    public static List<Move> generateWhiteMoves(){
        List<Move> moves = new ArrayList<>();

        MovementManager.INSTANCE.updateWhiteBoard();
        saveState();
        for (int i = 0 ; i<64 ; i++){
            Board.atackSquares =0;
            if (((Board.whiteBoard >>> i)& 1L) ==1){
                int piece = MovementManager.INSTANCE.returnPiece(i);
                switch (piece) {
                    case 1 -> MovementManager.INSTANCE.WPMouvement(i);
                    case 2 -> MovementManager.INSTANCE.WBMouvement(i);
                    case 3 -> MovementManager.INSTANCE.WRMouvement(i);
                    case 4 -> MovementManager.INSTANCE.WQMouvement(i);
                    case 5 -> MovementManager.INSTANCE.WKNMovement(i);
                    case 6 -> MovementManager.INSTANCE.WKMovement(i);
                    default -> { }
                }
                
                if (Board.atackSquares != 0) {

                    for (int r = 0; r < 64; r++) {
                        if (((Board.atackSquares >>> r) & 1L) != 0) {
                            if (((Board.captureSquares >>> r) & 1L) != 0){
                                moves.add(0,new Move(i, r, piece));
                                resetState();
                            }else{
                                moves.add(new Move(i, r, piece));
                                resetState();
                            }
                        }
                    }

                }
            }
        }
        return moves;
        
    }

    public static int shearch(int depth , int alpha , int beta){
        if (depth == 0 || MovementManager.INSTANCE.gameFinished){
            return EvaluationFuncion.Evaluate();
        }

        boolean maximizing =MovementManager.INSTANCE.turn;

        List<Move> candidates;

        int best = maximizing ?Integer.MIN_VALUE :Integer.MAX_VALUE  ;

        if (maximizing) {
            candidates = generateWhiteMoves();
        } else {
            candidates = generateBlackMoves();
        }

          for (Move move : candidates){
            
            Board.State boardState = Board.captureState();
            MovementManager.State managerState = MovementManager.INSTANCE.captureState();

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
                MovementManager.INSTANCE.resetState(managerState);
                Board.resetState(boardState);
            }

            if (alpha >= beta){break;}
        }
        return best;
    }

    public static void play(){
        Move(findBestMove(DEFAULT_DEPTH));
    }

    public static Move findBestMove(int depth){
        boolean maximizing =MovementManager.INSTANCE.turn;

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
            MovementManager.State managerState = MovementManager.INSTANCE.captureState();

            try{
                Move(move);
                score =shearch(depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE);

            }finally{
                MovementManager.INSTANCE.resetState(managerState);
                Board.resetState(boardState);
            }

            if ( maximizing && (score > bestScore) || !maximizing && (score < bestScore)){
                bestScore = score;
                best = move;
            }
        }
        System.out.println("finished ");
        return best;
    }

    public static void saveState(){
        MovementManager.INSTANCE.saveState();
        Board.saveState();
    }

    public static void resetState(MovementManager.State mmState, Board.State boardState){
        MovementManager.INSTANCE.resetState(mmState);
        Board.resetState(boardState);
    }

    public static void resetState(){
        MovementManager.INSTANCE.resetState();
        Board.resetState();
    }

    public static void Move(Move move){

        MovementManager.INSTANCE.showAtackSquare(move.piece(),move.from());
        MovementManager.INSTANCE.movePiece(move.to());

    }


}