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
        if (pieceType.equals("WB")){
            WBMouvement(arayPos);
        }
        if (pieceType.equals("WR")){
            WRMouvement(arayPos);
        }
        if (pieceType.equals("WQ")){
            WQMouvement(arayPos);
        }
        if (pieceType.equals("WKN")){
            WKNMovement(arayPos);
        }

    }
    public static void movePiece(int arayPos ){
        if (((Board.atackSquares >>> arayPos) & 1L) == 0){

            return;
        }
        long curentBoard = 0 ;

        if (pieceMoved.equals("WP")){
           curentBoard = Board.whitePawns ;
        }
        if (pieceMoved.equals("WB")){
            curentBoard = Board.whiteBishops ;
        }
        if (pieceMoved.equals("WR")){
            curentBoard = Board.whiteRoocks ;
        }
        if (pieceMoved.equals("WQ")){
            curentBoard = Board.whiteQueen ;
        }
        if (pieceMoved.equals("WKN")){
            curentBoard = Board.whiteKnights ;
        }
        
        curentBoard ^= (1L << pieceMovedPos) ;

        curentBoard ^= (1L << arayPos) ;

        if (((Board.captureSquares >>> arayPos) & 1L) != 0) {
           removePiece(arayPos);
        }

        // Update the board with the moved piece
        if (pieceMoved.equals("WP")){
            Board.whitePawns = curentBoard;
        }
        if (pieceMoved.equals("WB")){
            Board.whiteBishops = curentBoard;
        }
        if (pieceMoved.equals("WR")){
            Board.whiteRoocks = curentBoard;
        }
        if (pieceMoved.equals("WQ")){
            Board.whiteQueen = curentBoard;
        }
        if (pieceMoved.equals("WKN")){
            Board.whiteKnights = curentBoard;
        }

        // Add cases for other piece types (WB, WR, WN, WQ, WK, etc.)

        Board.atackSquares = 0;
        Board.captureSquares = 0;
        pieceMoved = "";
        pieceMovedPos = 0;
    }
    
    public static void WPMouvement(int arayPos){//check manque la en passent
        //atack movement 
        if ((63 - arayPos) % 8 != 0) {
            if (checkIfSquareisUsedByEnemey(arayPos + 7)) {
                Board.atackSquares |= (1L << arayPos + 7);
                Board.captureSquares |= (1L << arayPos + 7);
            }
        }

        if ((arayPos) % 8!= 0) {
            if (checkIfSquareisUsedByEnemey(arayPos + 9)) {
                Board.atackSquares |= (1L << arayPos + 9);
                Board.captureSquares |= (1L << arayPos + 9);
            }
        }
        //if its on the second row advance two
        if (arayPos >= 8 && arayPos <= 15) {
            if (!checkIfSquareisUsed(arayPos + 8)) {
                Board.atackSquares |= (1L << arayPos + 8);
                if (!checkIfSquareisUsed(arayPos + 16)) {
                    Board.atackSquares |= (1L << arayPos + 16);
                }
            }

        } else {
            if (!checkIfSquareisUsed(arayPos + 8)) {
                Board.atackSquares |= (1L << arayPos + 8);
            }
        }

       
        
    }

    public static void WBMouvement(int arayPos) {
        if ((arayPos) % 8 != 0) {

            //top right  
            if (Math.floor(arayPos / 8) != 7) {
                for (int i = 1; i < 10; i++) {

                    if (!checkIfSquareisUsed(arayPos + i * 7)) {
                        Board.atackSquares |= (1L << arayPos + i * 7);
                    } else {
                        if (checkIfSquareisUsedByEnemey(arayPos + i * 7)) {
                            Board.atackSquares |= (1L << arayPos + i * 7);
                            Board.captureSquares |= (1L << arayPos + i * 7);
                        }
                        break;
                    }
                    if ((arayPos + i * 7) % 8 == 0 || (63 - (arayPos + i * 7)) % 8 == 0 || Math.floor((arayPos + i * 7) / 8) == 7) {
                        break;
                    }
                }
            }

            //down right
            if (Math.floor(arayPos / 8) != 0) {

                for (int i = 1; i < 10; i++) {
                    if (!checkIfSquareisUsed(arayPos - i * 9)) {
                        Board.atackSquares |= (1L << arayPos - i * 9);
                    } else {
                        if (checkIfSquareisUsedByEnemey(arayPos - i * 9)) {
                            Board.atackSquares |= (1L << arayPos - i * 9);
                            Board.captureSquares |= (1L << arayPos - i * 9);
                        }
                        break;
                    }

                    if ((arayPos - i * 9) % 8 == 0 || (63 - (arayPos - i * 9)) % 8 == 0 || Math.floor((arayPos - i * 9) / 8) == 0) {
                        break;
                    }
                }
            }

        }
        if ((63 - (arayPos)) % 8 != 0) {
            //down left
            if (Math.floor(arayPos / 8) != 0) {
                for (int i = 1; i < 10; i++) {

                    if (!checkIfSquareisUsed(arayPos - i * 7)) {
                        Board.atackSquares |= (1L << arayPos - i * 7);
                    } else {
                        if (checkIfSquareisUsedByEnemey(arayPos - i * 7)) {
                            Board.atackSquares |= (1L << arayPos - i * 7);
                            Board.captureSquares |= (1L << arayPos - i * 7);
                        }
                        break;
                    }
                    if ((arayPos - i * 7) % 8 == 0 || (63 - (arayPos - i * 7)) % 8 == 0 || Math.floor((arayPos - i * 7) / 8) == 0) {
                        break;
                    }

                }
            }

            //top left
            if (Math.floor(arayPos / 8) != 7) {
                for (int i = 1; i < 10; i++) {

                    if (!checkIfSquareisUsed(arayPos + i * 9)) {
                        Board.atackSquares |= (1L << arayPos + i * 9);
                    } else {
                        if (checkIfSquareisUsedByEnemey(arayPos + i * 9)) {
                            Board.atackSquares |= (1L << arayPos + i * 9);
                            Board.captureSquares |= (1L << arayPos + i * 9);
                        }
                        break;
                    }
                    if ((arayPos + i * 9) % 8 == 0 || (63 - (arayPos + i * 9)) % 8 == 0 || Math.floor((arayPos - i * 9) / 8) == 7) {
                        break;
                    }
                }
            }

        }
    }

    public static void WRMouvement(int arayPos) {
        // top
        if (Math.floor(arayPos / 8) != 7) {
            for (int i = 1; i < 9; i++) {
                if (!checkIfSquareisUsed(arayPos + i * 8)) {
                    Board.atackSquares |= (1L << arayPos + i * 8);
                } else {
                    if (checkIfSquareisUsedByEnemey(arayPos + i * 8)) {
                        Board.atackSquares |= (1L << arayPos + i * 8);
                        Board.captureSquares |= (1L << arayPos + i * 8);
                    }
                    break;
                }
                if (Math.floor((arayPos +i*8)/ 8) == 7) {break;}

            }
        }
        // down
        if (Math.floor(arayPos / 8) != 0) {
            for (int i = 1; i < 9; i++) {
                if (!checkIfSquareisUsed(arayPos - i * 8)) {
                    Board.atackSquares |= (1L << arayPos - i * 8);
                } else {
                    if (checkIfSquareisUsedByEnemey(arayPos - i * 8)) {
                        Board.atackSquares |= (1L << arayPos - i * 8);
                        Board.captureSquares |= (1L << arayPos - i * 8);
                    }
                    break;
                }
                if (Math.floor((arayPos -i*8)/ 8) == 0) {break;}
            }
        }
        //left
        if ((63 - arayPos) % 8 != 0) {
            for (int i = 1; i < 9; i++) {

                if (!checkIfSquareisUsed(arayPos + i)) {
                    Board.atackSquares |= (1L << arayPos + i);
                } else {
                    if (checkIfSquareisUsedByEnemey(arayPos + i)) {
                        Board.atackSquares |= (1L << arayPos + i);
                        Board.captureSquares |= (1L << arayPos + i);
                    }
                    break;
                }
                if ((63 - (arayPos + i)) % 8 == 0) {
                    break;
                }
            }
        }

        //right
        if ((arayPos) % 8 != 0) {
            for (int i = 1; i < 9; i++) {

                if (!checkIfSquareisUsed(arayPos - i)) {
                    Board.atackSquares |= (1L << arayPos - i);
                } else {
                    if (checkIfSquareisUsedByEnemey(arayPos - i)) {
                        Board.atackSquares |= (1L << arayPos - i);
                        Board.captureSquares |= (1L << arayPos - i);
                    }
                    break;
                }
                if ((arayPos - i) % 8 == 0) {
                    break;
                }
            }
        }
    }

    public static void WQMouvement(int arayPos){
        WBMouvement(arayPos);
        WRMouvement(arayPos);
    }

    public static void WKNMovement(int arayPos){
        //top two things
        if (Math.floor(arayPos / 8) != 7 || Math.floor(arayPos / 8) != 6){
            if (!checkIfSquareisUsed(arayPos +15)) {
                Board.atackSquares |= (1L << arayPos + 15); 
            }else{
                if (checkIfSquareisUsedByEnemey(arayPos+15)){
                    Board.atackSquares |= (1L << arayPos + 15);
                    Board.captureSquares |= (1L << arayPos+15);
                }
            }
            if (!checkIfSquareisUsed(arayPos +17)) {
                Board.atackSquares |= (1L << arayPos + 17);
            }else{
                if (checkIfSquareisUsedByEnemey(arayPos+17)){
                    Board.atackSquares |= (1L << arayPos + 17);
                    Board.captureSquares |= (1L << arayPos+17);
                }
            }
        }
        //two down
        if (Math.floor(arayPos / 8) != 1 || Math.floor(arayPos / 8) != 0){
            if (!checkIfSquareisUsed(arayPos -15)) {
                Board.atackSquares |= (1L << arayPos - 15); 
            }else{
                if (checkIfSquareisUsedByEnemey(arayPos-15)){
                    Board.atackSquares |= (1L << arayPos - 15);
                    Board.captureSquares |= (1L << arayPos-15);
                }
            }
            if (!checkIfSquareisUsed(arayPos -17)) {
                Board.atackSquares |= (1L << arayPos - 17);
            }else{
                if (checkIfSquareisUsedByEnemey(arayPos-17)){
                    Board.atackSquares |= (1L << arayPos - 17);
                    Board.captureSquares |= (1L << arayPos-17);
                }
            }
        }

        //two left
        


    }

    public static boolean checkIfSquareisUsedByEnemey(int i){
        
        if (((Board.blackPawns >>> i) & 1L) != 0) {
            return true;
        }
        if (((Board.blackBishops >>> i) & 1L) != 0) {
            return true;
        }
        if (((Board.blackRoocks >>> i) & 1L) != 0) {
            return true;
        }
        if (((Board.blackKnights >>> i) & 1L) != 0) {
            return true;
        }
        if (((Board.blackQueen >>> i) & 1L) != 0) {
            return true;
        }
        if (((Board.blackKing >>> i) & 1L) != 0) {
            return true;
        }
        
        return false;
    }

    public static boolean checkIfSquareisUsed(int i){
        
        if (((Board.whitePawns >>> i) & 1L) != 0) {
            return true;
        }
        if (((Board.whiteBishops >>> i) & 1L) != 0) {
            return true;
        }
        if (((Board.whiteRoocks >>> i) & 1L) != 0) {
            return true;
        }
        if (((Board.whiteKnights >>> i) & 1L) != 0) {
            return true;
        }
        if (((Board.whiteQueen >>> i) & 1L) != 0) {
            return true;
        }
        if (((Board.whiteKing >>> i) & 1L) != 0) {
            return true;
        }

        if (((Board.blackPawns >>> i) & 1L) != 0) {
            return true;
        }
        if (((Board.blackBishops >>> i) & 1L) != 0) {
            return true;
        }
        if (((Board.blackRoocks >>> i) & 1L) != 0) {
            return true;
        }
        if (((Board.blackKnights >>> i) & 1L) != 0) {
            return true;
        }
        if (((Board.blackQueen >>> i) & 1L) != 0) {
            return true;
        }
        if (((Board.blackKing >>> i) & 1L) != 0) {
            return true;
        }
        
        return false;
    }

    public static void removePiece(int i){
        if (((Board.whitePawns >>> i) & 1L) != 0) {
            Board.whitePawns ^= (1L << i) ;
        }
        if (((Board.whiteBishops >>> i) & 1L) != 0) {
            Board.whiteBishops ^= (1L << i) ;
        }
        if (((Board.whiteRoocks >>> i) & 1L) != 0) {
            Board.whiteRoocks ^= (1L << i) ;
        }
        if (((Board.whiteKnights >>> i) & 1L) != 0) {
            Board.whiteKnights ^= (1L << i) ;
        }
        if (((Board.whiteQueen >>> i) & 1L) != 0) {
            Board.whiteQueen ^= (1L << i) ;
        }
        if (((Board.whiteKing >>> i) & 1L) != 0) {
            Board.whiteKing ^= (1L << i) ;
        }

        if (((Board.blackPawns >>> i) & 1L) != 0) {
            Board.blackPawns ^= (1L << i) ;
        }
        if (((Board.blackBishops >>> i) & 1L) != 0) {
            Board.blackBishops ^= (1L << i) ;
        }
        if (((Board.blackRoocks >>> i) & 1L) != 0) {
            Board.blackRoocks ^= (1L << i) ;
        }
        if (((Board.blackKnights >>> i) & 1L) != 0) {
            Board.blackKnights ^= (1L << i) ;
        }
        if (((Board.blackQueen >>> i) & 1L) != 0) {
            Board.blackQueen ^= (1L << i) ;
        }
        if (((Board.blackKing >>> i) & 1L) != 0) {
            Board.blackKing ^= (1L << i) ;
        }
    }
}
