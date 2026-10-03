public class EvaluationFuncion {
    public static final int PAWN_VALUE = 100;
    public static final int BISHOP_VALUE = 320;
    public static final int KNIGHT_VALUE = 300;
    public static final int ROOK_VALUE = 500;
    public static final int QUEEN_VALUE = 900;
    public static final int KING_VALUE = 99999;

    public static long board =0L;

    public static int whiteScores = 0;
    public static int blackScores = 0;

    public static int blackAtackSquaresCount = 0 ;
    public static int blackCapureSquaresCount = 0 ;

    public static int whiteAtackSquaresCount = 0 ;
    public static int whiteCapureSquaresCount = 0 ; 

    public static int Evaluate(){
        updateBoard();
        whiteScores =0;
        blackScores = 0;
        blackAtackSquaresCount = 0 ;
        blackCapureSquaresCount = 0 ;
        whiteAtackSquaresCount = 0 ;
        whiteCapureSquaresCount = 0 ; 
        
        for (int i=0; i<64; i++){
            if (((board >>> i) & 1L)==1){
                int piece = returnPiece(i);
                Board.atackSquares = 0L;
                Board.captureSquares =0L;
                if (piece <7){
                    if (piece == 1){
                        whiteScores += PAWN_VALUE + PAWN_PQT[63 - i];
                        MovementManager.WPMouvement(i);
                    }if (piece == 2){
                        whiteScores += BISHOP_VALUE + BISHOP_PQT[63 - i];
                        MovementManager.WBMouvement(i);
                    }if (piece==3){
                        whiteScores += ROOK_VALUE + ROOK_MG_PQT[63 -i];
                        MovementManager.WRMouvement(i);
                    }if (piece== 4){ 
                        whiteScores += QUEEN_VALUE + QUEEN_MG_PQT[63 - i];
                        MovementManager.WQMouvement(i);
                    }if (piece == 5){
                        whiteScores +=KNIGHT_VALUE + KNIGHT_PQT[63 - i];
                        MovementManager.WKNMovement(i);
                    }if (piece == 6){
                        whiteScores += KING_VALUE + KING_MIDDLE_GAME_PQT[63 - i];
                        MovementManager.WKMovement(i);
                    }
                    
                    whiteAtackSquaresCount += Long.bitCount(Board.atackSquares);
                    whiteCapureSquaresCount += Long.bitCount(Board.captureSquares);


                }else {
                    if (piece == 7){
                        blackScores += PAWN_VALUE + PAWN_PQT[i];
                        MovementManager.BPMouvement(i);
                    }if (piece == 8){
                        blackScores += BISHOP_VALUE + BISHOP_PQT[i];
                        MovementManager.BBMouvement(i);
                    }if (piece == 9){
                        blackScores += ROOK_VALUE + ROOK_MG_PQT[i];
                        MovementManager.BRMouvement(i);
                    }if (piece == 10){
                        blackScores += QUEEN_VALUE + QUEEN_MG_PQT [i];
                        MovementManager.BQMouvement(i);
                    }if (piece == 11){
                        blackScores += KNIGHT_VALUE + KNIGHT_PQT[i];
                        MovementManager.BKNMovement(i);
                    }if (piece == 12){
                        blackScores += KING_VALUE+ KING_MIDDLE_GAME_PQT[i];
                        MovementManager.BKMovement(i);
                    }  



                    blackAtackSquaresCount += Long.bitCount(Board.atackSquares);
                    blackCapureSquaresCount += Long.bitCount(Board.captureSquares);
                }
            }

        }
        if (MovementManager.blackIsInCheck){
            blackScores -= 90000;
        }
        if (MovementManager.whiteIsInCheck){
            whiteScores -= 90000;
        }

        if (MovementManager.checkWhiteMateCheck()){
            blackScores += 100000000;
        }
        if (MovementManager.checkBlackMateCheck()){
            whiteScores +=100000000;
        }

        whiteScores += whiteCapureSquaresCount * 10 + whiteAtackSquaresCount * 2;
        blackScores += blackCapureSquaresCount * 10 + blackAtackSquaresCount * 2;


        return whiteScores - blackScores;
    }

    public static void updateBoard(){
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
}
