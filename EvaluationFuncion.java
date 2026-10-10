public class EvaluationFuncion {
    private final MovementManager movementManager = MovementManager.INSTANCE;

    public static final int PAWN_VALUE = 100;
    public static final int BISHOP_VALUE = 320;
    public static final int KNIGHT_VALUE = 300;
    public static final int ROOK_VALUE = 500;
    public static final int QUEEN_VALUE = 900;
    public static final int KING_VALUE = 1000;
    public static final int PST_MULTIPLIER = 100;

    private long board = 0L;

    private int whiteScores = 0;
    private int blackScores = 0;

    private int blackAtackSquaresCount = 0;
    private int blackCapureSquaresCount = 0;

    private int whiteAtackSquaresCount = 0;
    private int whiteCapureSquaresCount = 0;

    private final int[] whitePawnsPerFile = new int[8];
    private final int[] blackPawnsPerFile = new int[8];


    public static int Evaluate(){
        return new EvaluationFuncion().evaluate();
    }

    private int evaluate(){
        if (movementManager.won == 1) {
            return 9_000_000;
        }
        if (movementManager.won == 2) {
            return -9_000_000;
        }
        if (movementManager.won == 3 || movementManager.gameFinished) {
            return 0;
        }

        updateBoard();
        whiteScores =0;
        blackScores = 0;
        blackAtackSquaresCount = 0 ;
        blackCapureSquaresCount = 0 ;
        whiteAtackSquaresCount = 0 ;
        whiteCapureSquaresCount = 0 ; 

        java.util.Arrays.fill(whitePawnsPerFile, 0);
        java.util.Arrays.fill(blackPawnsPerFile, 0);
        
        for (int i=0; i<64; i++){
            if (((board >>> i) & 1L)==1){
                int piece = returnPiece(i);
                Board.atackSquares = 0L;
                Board.captureSquares =0L;
                if (piece < 7) {
                    switch (piece) {
                        case 1 -> {
                            whiteScores += PAWN_VALUE + PAWN_PQT[63 - i] * PST_MULTIPLIER;
                            overlapedPawns(i);
                            movementManager.WPMouvement(i);
                        }
                        case 2 -> {
                            whiteScores += BISHOP_VALUE + BISHOP_PQT[63 - i] * PST_MULTIPLIER;
                            movementManager.WBMouvement(i);
                        }
                        case 3 -> {
                            whiteScores += ROOK_VALUE + ROOK_MG_PQT[63 - i] * PST_MULTIPLIER;
                            movementManager.WRMouvement(i);
                        }
                        case 4 -> {
                            whiteScores += QUEEN_VALUE + QUEEN_MG_PQT[63 - i] * PST_MULTIPLIER;
                            movementManager.WQMouvement(i);
                        }
                        case 5 -> {
                            whiteScores += KNIGHT_VALUE + KNIGHT_PQT[63 - i] * PST_MULTIPLIER;
                            movementManager.WKNMovement(i);
                        }
                        case 6 -> {
                            whiteScores += KING_VALUE + KING_MIDDLE_GAME_PQT[63 - i] * PST_MULTIPLIER;
                            movementManager.WKMovement(i);
                        }
                        default -> { }
                    }

                    whiteAtackSquaresCount += Long.bitCount(Board.atackSquares);
                    whiteCapureSquaresCount += Long.bitCount(Board.captureSquares);
                } else {
                    switch (piece) {
                        case 7 -> {
                            blackScores += PAWN_VALUE + PAWN_PQT[i] * PST_MULTIPLIER;
                            BoverlapedPawns(i);
                            movementManager.BPMouvement(i);
                        }
                        case 8 -> {
                            blackScores += BISHOP_VALUE + BISHOP_PQT[i] * PST_MULTIPLIER;
                            movementManager.BBMouvement(i);
                        }
                        case 9 -> {
                            blackScores += ROOK_VALUE + ROOK_MG_PQT[i] * PST_MULTIPLIER;
                            movementManager.BRMouvement(i);
                        }
                        case 10 -> {
                            blackScores += QUEEN_VALUE + QUEEN_MG_PQT[i] * PST_MULTIPLIER;
                            movementManager.BQMouvement(i);
                        }
                        case 11 -> {
                            blackScores += KNIGHT_VALUE + KNIGHT_PQT[i] * PST_MULTIPLIER;
                            movementManager.BKNMovement(i);
                        }
                        case 12 -> {
                            blackScores += KING_VALUE + KING_MIDDLE_GAME_PQT[i] * PST_MULTIPLIER;
                            movementManager.BKMovement(i);
                        }
                        default -> { }
                    }

                    blackAtackSquaresCount += Long.bitCount(Board.atackSquares);
                    blackCapureSquaresCount += Long.bitCount(Board.captureSquares);
                }
            }
        }
        if (movementManager.blackIsInCheck){
            blackScores -= 1000;
        }
        if (movementManager.whiteIsInCheck){
            whiteScores -= 1000;
        }

        if (movementManager.SpieceMoved != 0) {
            if (movementManager.SpieceMoved >= 7){
                blackScores += EvaluateBlackMove();
            }else{
                whiteScores += EvaluateWhiteMove();
            }
        }

        

        whiteScores += whiteCapureSquaresCount * 50 + whiteAtackSquaresCount * 20;
        blackScores += blackCapureSquaresCount * 50 + blackAtackSquaresCount * 20;

        whiteScores += PawnOverlapScore();

 
        return whiteScores - blackScores;
    }

    private void updateBoard(){
        Board.board = 0L;
        Board.board = Board.blackBishops | Board.blackKing | Board.blackKnights | Board.blackPawns | Board.blackQueen | Board.blackRoocks |
        Board.whiteBishops | Board.whiteKing | Board.whiteKnights | Board.whitePawns | Board.whiteQueen | Board.whiteRoocks;
        board =Board.board;
    }

    public static int returnPiece(int i){
        long mask = 1L << i;

        if ((Board.whitePawns & mask) != 0L) return 1;
        if ((Board.whiteBishops & mask) != 0L) return 2;
        if ((Board.whiteKnights & mask) != 0L) return 5;
        if ((Board.whiteRoocks & mask) != 0L) return 3;
        if ((Board.whiteQueen & mask) != 0L) return 4;
        if ((Board.whiteKing & mask) != 0L) return 6;

        if ((Board.blackPawns & mask) != 0L) return 7;
        if ((Board.blackBishops & mask) != 0L) return 8;
        if ((Board.blackKnights & mask) != 0L) return 11;
        if ((Board.blackRoocks & mask) != 0L) return 9;
        if ((Board.blackQueen & mask) != 0L) return 10;
        if ((Board.blackKing & mask) != 0L) return 12;
        
        return 0;
    }

    public static int returnPieceValue(int piece){
        return switch (piece) {
            case 1, 7 -> PAWN_VALUE;
            case 2, 8 -> BISHOP_VALUE;
            case 3, 9 -> ROOK_VALUE;
            case 4, 10 -> QUEEN_VALUE;
            case 5, 11 -> KNIGHT_VALUE;
            case 6, 12 -> KING_VALUE;
            default -> 0;
        };
    }

    public static final int[] PAWN_PQT = {
         0,  0,  0,  0,  0,  0,  0,  0,
        50, 50, 50, 50, 50, 50, 50, 50,
        10, 10, 20, 30, 30, 20, 10, 10,
         5,  5, 10, 25, 25, 10,  5,  5,
         0,  0,  0, 20, 20,  0,  0,  0,
         5, -5,-10,  0,  0,-10, -5,  5,
         5, 10, 10,-20,-20, 10, 10,  5,
        99, 99, 99, 99, 99, 99, 99, 99
    };

    // Knight Heat Map: Penalizes edges ("Knights on the rim are dim") and favors the center
    public static final int[] KNIGHT_PQT = {
        -50, -40, -30, -30, -30, -30, -40, -50,
        -40, -20,   0,   0,   0,   0, -20, -40,
        -30,   0,  10,  15,  15,  10,   0, -30,
        -30,   5,  15,  20,  20,  15,   5, -30,
        -30,   0,  15,  20,  20,  15,   0, -30,
        -30,   5,  10,  15,  15,  10,   5, -30,
        -40, -20,   0,   5,   5,   0, -20, -40,
        -50, -40, -30, -30, -30, -30, -40, -50
    };

    // Bishop Heat Map: Encourages long diagonals and central presence
    public static final int[] BISHOP_PQT = {
        -20, -10, -10, -10, -10, -10, -10, -20,
        -10,   0,   0,   0,   0,   0,   0, -10,
        -10,   0,   5,  10,  10,   5,   0, -10,
        -10,   5,   5,  10,  10,   5,   5, -10,
        -10,   0,  10,  10,  10,  10,   0, -10,
        -10,  10,  10,  10,  10,  10,  10, -10,
        -10,   5,   0,   0,   0,   0,   5, -10,
        -20, -10, -10, -10, -10, -10, -10, -20
    };

    // King Middle-Game Heat Map: Encourages castling and pawn-shield safety
    public static final int[] KING_MIDDLE_GAME_PQT = {
        -30, -40, -40, -50, -50, -40, -40, -30,
        -30, -40, -40, -50, -50, -40, -40, -30,
        -30, -40, -40, -50, -50, -40, -40, -30,
        -30, -40, -40, -50, -50, -40, -40, -30,
        -20, -30, -30, -40, -40, -30, -30, -20,
        -10, -20, -20, -20, -20, -20, -20, -10,
         20,  20,   0,   0,   0,   0,  20,  20,
         40,  50,  10,   0,   0,  10,  50,  40
    };

    public static final int[] ROOK_MG_PQT = {
        32,  42,  32,  51,  63,  9,  31,  43,  // Rank 8
        27,  32,  58,  62,  80, 67,  26,  44,  // Rank 7 (7th rank invasion)
        -5,  19,  26,  36,  17, 45,  61,  16,  // Rank 6
        -24, -11,   7,  26,  24, 35,  -8, -20,  // Rank 5
        -36, -26,  -6,   1,  -2, -4, -15, -40,  // Rank 4
        -45, -25, -16, -17,  -3,  1,  -5, -33,  // Rank 3
        -44, -16, -20,  -9,  -1, 11,  -6, -71,  // Rank 2
        -19, -13,   1,  17,  16,  7, -37, -26   // Rank 1
    };
 
    public static final int[] QUEEN_MG_PQT = {
    -28, -20, -12,  -1,  -1, -12, -20, -28,  // Rank 8
    -18, -13,  -4,   0,   0,  -4, -13, -18,  // Rank 7
    -10,  -8,  -1,   4,   4,  -1,  -8, -10,  // Rank 6
     -9,  -4,   0,   6,   6,   0,  -4,  -9,  // Rank 5
    -10,  -4,   0,   6,   6,   0,  -4, -10,  // Rank 4
    -10,  -1,   6,   4,   4,   6,  -1, -10,  // Rank 3
    -16, -11,   0,   2,   2,   0, -11, -16,  // Rank 2
    -28, -17, -13,  -2,  -2, -13, -17, -28   // Rank 1
    };

    private void overlapedPawns(int square){
        whitePawnsPerFile[square % 8]++;
    }

    private void BoverlapedPawns(int square){
        blackPawnsPerFile[square % 8]++;
    }

    private int PawnOverlapScore(){
        int score = 0;
        for (int file = 0; file < 8; file++){
            score += Math.max(0, blackPawnsPerFile[file] - 1) * 25;
            score -= Math.max(0, whitePawnsPerFile[file] - 1) * 25;
        }

        return score;
    }

    private int EvaluateBlackMove(){
        int pieceMoved = movementManager.SpieceMoved;
        int pieceMovedPos = movementManager.SpieceMovedPos;
        boolean isCapture = movementManager.isCapture;
        int pieceCapured = movementManager.pieceCapured;
        int score = 0;

        if (((Board.BlackAtackSquares >>> pieceMovedPos )& 1L)==1){
            score +=2000;
        }else{
            score -=20;
        }
        if (((Board.whiteAtackSquares >>>  pieceMovedPos)& 1L)==1){
            score -=2000;
        }else{
            score +=40;
        }

        if (isCapture){
            score += returnPieceValue(pieceCapured) - returnPieceValue(pieceMoved);
        }



        return score;
    }
    private int EvaluateWhiteMove(){
        int pieceMoved = movementManager.SpieceMoved;
        int pieceMovedPos = movementManager.SpieceMovedPos;
        boolean isCapture = movementManager.isCapture;
        int pieceCapured = movementManager.pieceCapured;
        int score = 0;

        if (((Board.BlackAtackSquares >>> pieceMovedPos )& 1L)==1){
            score -=2000;
        }else{
            score +=20;
        }
        if (((Board.whiteAtackSquares >>>  pieceMovedPos)& 1L)==1){
            score +=2000;
        }else{
            score -=40;
        }

        if (isCapture){
            score += returnPieceValue(pieceCapured) - returnPieceValue(pieceMoved);
        }




        return score;
    }
}
