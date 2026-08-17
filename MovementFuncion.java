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
        
        curentBoard ^= (1L << pieceMovedPos) ;

        curentBoard ^= (1L << arayPos) ;

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

        // Add cases for other piece types (WB, WR, WN, WQ, WK, etc.)

        Board.atackSquares = 0;
        pieceMoved = "";
        pieceMovedPos = 0;
    }
    
    public static void WPMouvement(int arayPos){
        //if its on the second row advance two
        if (arayPos>=8 && arayPos<=15){
            if (!checkIfSquareisUsed(arayPos+8)){Board.atackSquares |= (1L << arayPos+8) ;
                if (!checkIfSquareisUsed(arayPos+16)){Board.atackSquares |= (1L << arayPos+16) ;}}
            
        }else{
            if (!checkIfSquareisUsed(arayPos+8)){Board.atackSquares |= (1L << arayPos+8) ;}
        }

        //atack movement 

        if(checkIfSquareisUsedByEnemey(arayPos+7)){Board.atackSquares |= (1L << arayPos+7) ;}

        if(checkIfSquareisUsedByEnemey(arayPos+9)){Board.atackSquares |= (1L << arayPos+9) ;}
    }

    public static void WBMouvement(int arayPos){
        if ((arayPos )%8 !=0){
        //top right
            for (int i = 1 ; i < 10 ; i++){
                
                if(!checkIfSquareisUsed(arayPos+ i*7)){Board.atackSquares |= (1L << arayPos + i*7);}
                else{if (checkIfSquareisUsedByEnemey(arayPos + i*7)){Board.atackSquares |= (1L << arayPos + i*7) ;}break;}
                if ((arayPos +i*7)%8 ==0 || (63 - (arayPos +i*7))%8 ==0){
                    break;
                }
            }


            //down right
            for (int i = 1 ; i < 10 ; i++){
                
                if(!checkIfSquareisUsed(arayPos- i*9)){Board.atackSquares |= (1L << arayPos - i*9);}
                else{if (checkIfSquareisUsedByEnemey(arayPos - i*9)){Board.atackSquares |= (1L << arayPos - i*9) ;}break;}
                if ((arayPos -i*9)%8 ==0 || (63 - (arayPos -i*9))%8 ==0){
                    break;
                }
            }
            
        }
        if ((63 - (arayPos ))%8 !=0){
        //down left
            for (int i = 1 ; i < 10 ; i++){
                
                if(!checkIfSquareisUsed(arayPos- i*7)){Board.atackSquares |= (1L << arayPos - i*7);}
                else{if (checkIfSquareisUsedByEnemey(arayPos - i*7)){Board.atackSquares |= (1L << arayPos - i*7) ;}break;}
                if ((arayPos -i*7)%8 ==0 || (63 - (arayPos -i*7))%8 ==0){
                    break;
                }
                
            }
            //top left
            for (int i = 1; i < 10; i++) {

                if (!checkIfSquareisUsed(arayPos + i * 9)) {
                    Board.atackSquares |= (1L << arayPos + i * 9);
                } else {
                    if (checkIfSquareisUsedByEnemey(arayPos + i * 9)) {
                        Board.atackSquares |= (1L << arayPos + i * 9);
                    }
                    break;
                }
                if ((arayPos + i * 9) % 8 == 0 || (63 - (arayPos + i * 9)) % 8 == 0) {
                    break;
                }
            }
            
        } 
    }
    
    public static void WRMouvement(int arayPos){
        // top
        for (int i = 1 ; i < 9 ; i++){
            if(!checkIfSquareisUsed(arayPos+ i*8)){Board.atackSquares |= (1L << arayPos + i*8);}
            else{if (checkIfSquareisUsedByEnemey(arayPos+ i*8)){Board.atackSquares |= (1L << arayPos + i*8);}break;}
        }
        // down
        for (int i = 1 ; i < 9 ; i++){
            if(!checkIfSquareisUsed(arayPos- i*8)){Board.atackSquares |= (1L << arayPos - i*8);}
            else{if (checkIfSquareisUsedByEnemey(arayPos-i*8)){Board.atackSquares |= (1L << arayPos - i*8);}break;}
        }

        //left
        if ((63- arayPos )%8 !=0 ){
            for (int i = 1 ; i < 9 ; i++){
                
                if(!checkIfSquareisUsed(arayPos+ i)){Board.atackSquares |= (1L << arayPos + i);}
                else{if (checkIfSquareisUsedByEnemey(arayPos+i)){Board.atackSquares |= (1L << arayPos + i);}break;}
                if ((63 - (arayPos +i))%8 ==0){
                    break;
                }
            }
        }

        //right
        if ((arayPos)%8 !=0 ){
            for (int i = 1 ; i < 9 ; i++){
                
                if(!checkIfSquareisUsed(arayPos - i)){Board.atackSquares |= (1L << arayPos - i);}
                else{if (checkIfSquareisUsedByEnemey(arayPos-i)){Board.atackSquares |= (1L << arayPos - i);}break;}
                if ((arayPos -i)%8 ==0 ){
                    break;
                }
            }
        }
    }

    public static void WQMouvement(int arayPos){
        WBMouvement(arayPos);
        WRMouvement(arayPos);
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
    
}
