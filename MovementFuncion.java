public class MovementFuncion {
    public static String pieceMoved = "";
    public static int pieceMovedPos = 0;
    public static void showAtackSquare(String pieceType , int arayPos){
        Board.atackSquares = 0;
        pieceMoved = pieceType;
        pieceMovedPos = arayPos;
        if (pieceType.equals("WP")){
            WPMouvement(arayPos);
        }
    }
    public static void movePiece(int arayPos ){
        long curentBoard = PieceGetType(pieceMoved);
        curentBoard ^= (1L << pieceMovedPos) ;
        curentBoard ^= (1L << arayPos) ;
        // Update the board with the moved piece
        if (pieceMoved.equals("WP")){
            Board.whitePawns = curentBoard;
        }
        // Add cases for other piece types (WB, WR, WN, WQ, WK, etc.)
    }
    public static long PieceGetType(String pieceType){
        if (pieceType.equals("WP")){
            return Board.whitePawns;
        }
        return Board.atackSquares;
    }
    public static void WPMouvement(int arayPos){
        //if its on the second row advance two
        if (arayPos>=8 && arayPos<=15){
            Board.atackSquares |= (1L << arayPos+8) ;
            Board.atackSquares |= (1L << arayPos+16) ;
        }else{
            Board.atackSquares |= (1L << arayPos+8) ;
        }
    }
}
