public class MovementFuncion {

    public static int pieceMoved = 0;
    public static int pieceMovedPos = 0;

    public static boolean WhiteKingHasBeenMoved = false;
    public static boolean blackKingHasBeenMoved = false;

    public static boolean WhiteKingHasBeenChecked = false;
    public static boolean blackKingHasBeenChecked = false;

    public static int whiteKingPos = 3;

    public static boolean whiteIsInCheck = false ;

    public static void showAtackSquare(int pieceType , int arayPos){
        Board.atackSquares = 0;
        pieceMoved = pieceType;
        pieceMovedPos = arayPos;
        Board.captureSquares = 0;
        if (pieceType == 1){
            WPMouvement(arayPos);
        }
        if (pieceType == 2){
            WBMouvement(arayPos);
        }
        if (pieceType == 3){
            WRMouvement(arayPos);
        }
        if (pieceType == 4){
            WQMouvement(arayPos);
        }
        if (pieceType == 5){
            WKNMovement(arayPos);
        }
        if (pieceType == 6){
            WKMovement(arayPos);
        }

        if (pieceType == 7){
            BPMouvement(arayPos);
        }
        if (pieceType == 8){
            BBMouvement(arayPos);
        }
        if (pieceType == 9){
            BRMouvement(arayPos);
        }
        if (pieceType == 10){
            BQMouvement(arayPos);
        }
        if (pieceType == 11){
            BKNMovement(arayPos);
        }
        if (pieceType == 12){
            BKMovement(arayPos);
        }
        

    }
    
    public static void movePiece(int arayPos ){

        if (((Board.atackSquares >>> arayPos) & 1L) == 0){

            return;
        }
        long curentBoard = 0 ;


        if (pieceMoved == 1){
           curentBoard = Board.whitePawns ;
        }
        if (pieceMoved == 2){
            curentBoard = Board.whiteBishops ;
        }
        if (pieceMoved == 3){
            curentBoard = Board.whiteRoocks ;
        }
        if (pieceMoved == 4){
            curentBoard = Board.whiteQueen ;
        }
        if (pieceMoved == 5){
            curentBoard = Board.whiteKnights ;
        }
        if (pieceMoved == 6){
            if (!WhiteKingHasBeenChecked && !WhiteKingHasBeenMoved){
                if (arayPos == 1){
                    Board.whiteRoocks ^= (1L) ;

                    Board.whiteRoocks ^= (1L << 2) ;
                }
                if (arayPos == 5){
                    Board.whiteRoocks ^= (1L << 7) ;

                    Board.whiteRoocks ^= (1L << 4) ;
                }
            }
            whiteKingPos = arayPos;
            WhiteKingHasBeenMoved = true;
            curentBoard = Board.whiteKing ; 
        }

        if (pieceMoved == 7){
           curentBoard = Board.blackPawns ;
        }
        if (pieceMoved == 8){
            curentBoard = Board.blackBishops ;
        }
        if (pieceMoved == 9){
            curentBoard = Board.blackRoocks ;
        }
        if (pieceMoved == 10){
            curentBoard = Board.blackQueen ;
        }
        if (pieceMoved == 11){
            curentBoard = Board.blackKnights ;
        }
        if (pieceMoved == 12){

            
            if (!blackKingHasBeenChecked && !blackKingHasBeenMoved){
                if (arayPos == 61){
                    Board.blackRoocks ^= (1L << 63) ;

                    Board.blackRoocks ^= (1L << 60) ;
                }
                if (arayPos == 57){
                    Board.blackRoocks ^= (1L << 56) ;

                    Board.blackRoocks ^= (1L << 58) ;
                }
            }
            blackKingHasBeenMoved = true;
            curentBoard = Board.blackKing ;
        }
        
        curentBoard ^= (1L << pieceMovedPos) ;

        curentBoard ^= (1L << arayPos) ;

        if (((Board.captureSquares >>> arayPos) & 1L) != 0) {
           removePiece(arayPos);
        }

        // Update the board with the moved piece
        if (pieceMoved == 1){
            Board.whitePawns = curentBoard;
        }
        if (pieceMoved == 2){
            Board.whiteBishops = curentBoard;
        }
        if (pieceMoved == 3){
            Board.whiteRoocks = curentBoard;
        }
        if (pieceMoved == 4){
            Board.whiteQueen = curentBoard;
        }
        if (pieceMoved == 5){
            Board.whiteKnights = curentBoard;
        }
        if (pieceMoved == 6){
            Board.whiteKing = curentBoard;
        }

        if (pieceMoved == 7){
            Board.blackPawns = curentBoard;
        }
        if (pieceMoved == 8){
            Board.blackBishops =curentBoard  ;
        }
        if (pieceMoved == 9){
            Board.blackRoocks=curentBoard   ;
        }
        if (pieceMoved == 10){
            Board.blackQueen  =curentBoard ;
        }
        if (pieceMoved == 11){
            Board.blackKnights  =curentBoard ;
        }
        if (pieceMoved == 12){
            Board.blackKing =curentBoard  ;
        }

        Board.checkSquares = 0;
        checkWhiteCheck(whiteKingPos);
        Board.atackSquares = 0;
        Board.captureSquares = 0;
        pieceMoved = 0;
        pieceMovedPos = 0;
    }
    
    //white pieces movement 

    public static void WPMouvement(int arayPos){
        //atack movement 
        //if (((Board.checkSquares >>>arayPos ) &1L ) == 0){
            if (((Board.FirstColumn >>> arayPos) & 1L) == 0) {
                if (checkIfSquareisUsedByEnemey(arayPos + 7)) {
                    if (!whiteIsInCheck){
                        
                        Board.atackSquares |= (1L << arayPos + 7);
                        Board.captureSquares |= (1L << arayPos + 7);
                    }else{
                        if (((Board.checkSquares >>> arayPos+7)& 1L )==1){
                            Board.atackSquares |= (1L << arayPos + 7);
                            Board.captureSquares |= (1L << arayPos + 7);
                        }
                    }
                    
                }
            }

            if (((Board.EighthColumn >>> arayPos) & 1L) == 0) {
                if (checkIfSquareisUsedByEnemey(arayPos + 9)) {
                    if (!whiteIsInCheck){
                        if (((Board.checkSquares >>>arayPos ) & 1L ) == 0){
                            Board.atackSquares |= (1L << arayPos + 9);
                            Board.captureSquares |= (1L << arayPos + 9);
                        }
                    }else{
                        if (((Board.checkSquares >>> arayPos+9)& 1L )==1){
                            Board.atackSquares |= (1L << arayPos + 9);
                            Board.captureSquares |= (1L << arayPos + 9);
                        }
                    }
                    
                }
            }
            //if its on the second row advance two
            if (((Board.SecondRow >>> arayPos) & 1L) != 0) {
                if (!checkIfSquareisUsed(arayPos + 8)) {
                    if (!whiteIsInCheck){
                        if (((Board.checkSquares >>> arayPos) &1L ) ==0 ){
                            Board.atackSquares |= (1L << arayPos + 8);
                        }
                    }else{
                        if (((Board.checkSquares >>> arayPos +8) &1L ) ==1 ){
                            Board.atackSquares |= (1L << arayPos + 8);
                        }
                    }
                    
                    if (!checkIfSquareisUsed(arayPos + 16)) {
                        if (!whiteIsInCheck){
                            if (((Board.checkSquares >>> arayPos) &1L ) ==0 ){
                                Board.atackSquares |= (1L << arayPos + 16);
                            }
                        }else{
                            if (((Board.checkSquares >>> arayPos +16) &1L ) ==1 ){
                                Board.atackSquares |= (1L << arayPos + 16);
                            }
                        }
                    }
                }
            } else {
                if (!checkIfSquareisUsed(arayPos + 8)) {
                    if (!whiteIsInCheck){
                        if (((Board.checkSquares >>> arayPos) &1L ) ==0 ){
                            Board.atackSquares |= (1L << arayPos + 8);
                        }
                    }else{
                        if (((Board.checkSquares >>> arayPos +8) &1L ) ==1 ){
                            Board.atackSquares |= (1L << arayPos + 8);
                        }
                    }
                }
            }
        //}
        
    }

    public static void WBMouvement(int arayPos) {
        if (((Board.FirstColumn >>> arayPos) & 1L) == 0) {

            //top right  
            if (((Board.EighthRow >>> arayPos) & 1L) == 0) {
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
                    if (((Board.FirstColumn >>> arayPos+i*7) & 1L) != 0||((Board.EighthColumn >>> arayPos + i*7) & 1L) != 0 || ((Board.EighthRow >>> arayPos + i*7) & 1L) != 0 ){
                        break;
                    }
                }
            }

            //down right
            if (((Board.FirstRow >>> arayPos) & 1L) == 0) {

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

                    if (((Board.FirstColumn >>> arayPos-i*9) & 1L) != 0 ||((Board.EighthColumn >>> arayPos-i*9) & 1L) != 0 || ((Board.FirstRow >>> arayPos-i*9) & 1L) != 0) {
                        break;
                    }
                }
            }

        }
        if (((Board.EighthColumn >>> arayPos) & 1L) == 0) {
            //down left
            if (((Board.FirstRow >>> arayPos) & 1L) == 0) {
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
                    if (((Board.FirstColumn >>> arayPos-i*7) & 1L) != 0||((Board.EighthColumn >>> arayPos - i*7) & 1L) != 0 ||((Board.FirstRow >>> arayPos -i*7)& 1L) != 0) {
                        break;
                    }

                }
            }

            //top left
            if (((Board.EighthRow >>> arayPos )& 1L)==0) {
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
                    if (((Board.FirstColumn >>> arayPos+i*9) & 1L) != 0 ||((Board.EighthColumn >>> arayPos+i*9) & 1L) != 0 || ((Board.EighthRow >>> arayPos+i*9) & 1L) != 0) {
                        break;
                    }
                }
            }

        }
    }

    public static void WRMouvement(int arayPos) {
        // top
        if (((Board.EighthRow >>> arayPos) & 1L) == 0) {
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
                if (((Board.EighthRow >>> arayPos+i*8) & 1L) != 0) {break;}

            }
        }
        // down
        if (((Board.FirstRow >>> arayPos) & 1L) == 0) {
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
                if (((Board.FirstRow >>> arayPos - i *8) & 1L) != 0) {break;}
            }
        }
        //left
        if (((Board.EighthColumn >>> arayPos) & 1L) == 0) {
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
                if (((Board.EighthColumn >>> arayPos+i) & 1L) != 0) {
                    break;
                }
            }
        }

        //right
        if (((Board.FirstColumn >>> arayPos) & 1L) == 0) {
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
                if (((Board.FirstColumn >>> arayPos-i) & 1L) != 0) {
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
        //top two 
        if (Math.floor(arayPos / 8) != 7 && Math.floor(arayPos / 8) != 6){
            if (((Board.FirstColumn >>> arayPos) & 1L) == 0){
                if (!checkIfSquareisUsed(arayPos +15)) {
                    Board.atackSquares |= (1L << arayPos + 15); 
                }else{
                    if (checkIfSquareisUsedByEnemey(arayPos+15)){
                        Board.atackSquares |= (1L << arayPos + 15);
                        Board.captureSquares |= (1L << arayPos+15);
                    }
                }
            }
            if (((Board.EighthColumn >>> arayPos) & 1L) == 0){
                if (!checkIfSquareisUsed(arayPos +17)) {
                    Board.atackSquares |= (1L << arayPos + 17);
                }else{
                    if (checkIfSquareisUsedByEnemey(arayPos+17)){
                        Board.atackSquares |= (1L << arayPos + 17);
                        Board.captureSquares |= (1L << arayPos+17);
                    }
                }
            }
        }
        //down two

        if (Math.floor(arayPos / 8) != 0 && Math.floor(arayPos / 8) != 1){

            if (((Board.EighthColumn >>> arayPos) & 1L) == 0){
                if (!checkIfSquareisUsed(arayPos -15)) {
                    Board.atackSquares |= (1L << arayPos - 15); 
                }else{
                    if (checkIfSquareisUsedByEnemey(arayPos-15)){
                        Board.atackSquares |= (1L << arayPos - 15);
                        Board.captureSquares |= (1L << arayPos-15);
                    }
                }
            }
            if (((Board.FirstColumn >>> arayPos) & 1L) == 0){
                if (!checkIfSquareisUsed(arayPos -17)) {
                    Board.atackSquares |= (1L << arayPos - 17);
                }else{
                    if (checkIfSquareisUsedByEnemey(arayPos-17)){
                        Board.atackSquares |= (1L << arayPos - 17);
                        Board.captureSquares |= (1L << arayPos-17);
                    }
                }
            }
            
        }

        //two right
        if (((Board.FirstColumn >>> arayPos) & 1L) == 0&&((Board.SecondColumn >>> arayPos) & 1L) == 0){
            
            if (Math.floor(arayPos / 8) != 7){
                if (!checkIfSquareisUsed(arayPos +6)) {
                    Board.atackSquares |= (1L << arayPos + 6); 
                }else{
                    if (checkIfSquareisUsedByEnemey(arayPos+6)){
                        Board.atackSquares |= (1L << arayPos + 6);
                        Board.captureSquares |= (1L << arayPos+6);
                    }
                }
            }

            if (Math.floor(arayPos / 8) != 0){
                if (!checkIfSquareisUsed(arayPos -10)) {
                Board.atackSquares |= (1L << arayPos - 10);
                }
                else{
                    if (checkIfSquareisUsedByEnemey(arayPos-10)){
                        Board.atackSquares |= (1L << arayPos - 10);
                        Board.captureSquares |= (1L << arayPos-10);
                    }
                }
            }
            
        }

        //two left
        if (((Board.SeventhColumn >>> arayPos) & 1L) == 0&&((Board.EighthColumn >>> arayPos) & 1L) == 0){
            
            if (Math.floor(arayPos %8) != 0){
                if (!checkIfSquareisUsed(arayPos +10)) {
                    Board.atackSquares |= (1L << arayPos + 10); 
                }else{
                    if (checkIfSquareisUsedByEnemey(arayPos+10)){
                        Board.atackSquares |= (1L << arayPos + 10);
                        Board.captureSquares |= (1L << arayPos+10);
                    }
                }
            }

            if (Math.floor(arayPos / 8) != 0){
                if (!checkIfSquareisUsed(arayPos -6)) {
                    Board.atackSquares |= (1L << arayPos - 6);
                }else{
                    if (checkIfSquareisUsedByEnemey(arayPos-6)){
                        Board.atackSquares |= (1L << arayPos - 6);
                        Board.captureSquares |= (1L << arayPos-6);
                    }
                }
            }
        }


    }

    public static void WKMovement(int arayPos){
        //right
        if (((Board.FirstColumn >>> arayPos) & 1L) == 0){
            if (!checkIfSquareisUsed(arayPos -1)) {
                Board.atackSquares |= (1L << arayPos - 1); 
            }else{
                if (checkIfSquareisUsedByEnemey(arayPos-1)){
                    Board.atackSquares |= (1L << arayPos - 1);
                    Board.captureSquares |= (1L << arayPos-1);
                }
            }

            if (Math.floor(arayPos/8) !=7){
                if (!checkIfSquareisUsed(arayPos +7)) {
                    Board.atackSquares |= (1L << arayPos + 7); 
                }else{
                    if (checkIfSquareisUsedByEnemey(arayPos+7)){
                        Board.atackSquares |= (1L << arayPos + 7);
                        Board.captureSquares |= (1L << arayPos+7);
                    }
                }
            }

            if (Math.floor(arayPos/8) !=0){
                if (!checkIfSquareisUsed(arayPos -9)) {
                    Board.atackSquares |= (1L << arayPos -9); 
                }else{
                    if (checkIfSquareisUsedByEnemey(arayPos-9)){
                        Board.atackSquares |= (1L << arayPos -9);
                        Board.captureSquares |= (1L << arayPos-9);
                    }
                }
            }

            
        }

        //left
        if (((Board.EighthColumn >>> arayPos) & 1L) == 0){
            if (!checkIfSquareisUsed(arayPos + 1)) {
                Board.atackSquares |= (1L << arayPos + 1); 
            }else{
                if (checkIfSquareisUsedByEnemey(arayPos + 1)){
                    Board.atackSquares |= (1L << arayPos + 1);
                    Board.captureSquares |= (1L << arayPos + 1);
                } 
            }

            if (Math.floor(arayPos/8) !=0){
                if (!checkIfSquareisUsed(arayPos -7)) {
                    Board.atackSquares |= (1L << arayPos - 7); 
                }else{
                    if (checkIfSquareisUsedByEnemey(arayPos-7)){
                        Board.atackSquares |= (1L << arayPos - 7);
                        Board.captureSquares |= (1L << arayPos-7);
                    }
                }
            }

            if (Math.floor(arayPos/8) !=7){
                if (!checkIfSquareisUsed(arayPos +9)) {
                    Board.atackSquares |= (1L << arayPos +9); 
                }else{
                    if (checkIfSquareisUsedByEnemey(arayPos+9)){
                        Board.atackSquares |= (1L << arayPos +9);
                        Board.captureSquares |= (1L << arayPos+9);
                    }
                }
            }
        }

        if (Math.floor(arayPos/8) !=7){
            if (!checkIfSquareisUsed(arayPos +8)) {
                Board.atackSquares |= (1L << arayPos +8); 
            }else{
                if (checkIfSquareisUsedByEnemey(arayPos+8)){
                    Board.atackSquares |= (1L << arayPos +8);
                    Board.captureSquares |= (1L << arayPos+8);
                }
            }
        }

        if (Math.floor(arayPos/8) !=0){
            if (!checkIfSquareisUsed(arayPos - 8)) {
                Board.atackSquares |= (1L << arayPos - 8); 
            }else{
                if (checkIfSquareisUsedByEnemey(arayPos - 8)){
                    Board.atackSquares |= (1L << arayPos - 8);
                    Board.captureSquares |= (1L << arayPos - 8);
                } 
            }
        }

        if (!WhiteKingHasBeenChecked && !WhiteKingHasBeenMoved){
            checkWhiteCastling();
        }

        
    }

    //Black pieces movement

    public static void BPMouvement(int arayPos){
        //atack movement 
        if (((Board.FirstColumn >>> arayPos) & 1L) == 0) {
            if (checkIfSquareisUsedByEnemeyBlack(arayPos - 7)) {
                Board.atackSquares |= (1L << arayPos - 7);
                Board.captureSquares |= (1L << arayPos - 7);
            }
        }

        if (((Board.EighthColumn >>> arayPos) & 1L) == 0) {
            if (checkIfSquareisUsedByEnemeyBlack(arayPos - 9)) {
                Board.atackSquares |= (1L << arayPos - 9);
                Board.captureSquares |= (1L << arayPos - 9);
            }
        }
        //if its on the second row advance two
        if (((Board.SeventhRow >>> arayPos) & 1L) != 0) {
            if (!checkIfSquareisUsed(arayPos - 8)) {
                Board.atackSquares |= (1L << arayPos - 8);
                if (!checkIfSquareisUsed(arayPos - 16)) {
                    Board.atackSquares |= (1L << arayPos - 16);
                }
            }

        } else {
            if (!checkIfSquareisUsed(arayPos - 8)) {
                Board.atackSquares |= (1L << arayPos - 8);
            }
        }

       
        
    }

    public static void BBMouvement(int arayPos) {
        if (((Board.FirstColumn >>> arayPos) & 1L) == 0) {

            //top right  
            if (((Board.EighthRow >>> arayPos) & 1L) == 0) {
                for (int i = 1; i < 10; i++) {

                    if (!checkIfSquareisUsed(arayPos + i * 7)) {
                        Board.atackSquares |= (1L << arayPos + i * 7);
                    } else {
                        if (checkIfSquareisUsedByEnemeyBlack(arayPos + i * 7)) {
                            Board.atackSquares |= (1L << arayPos + i * 7);
                            Board.captureSquares |= (1L << arayPos + i * 7);
                        }
                        break;
                    }
                    if (((Board.FirstColumn >>> arayPos+i*7) & 1L) != 0||((Board.EighthColumn >>> arayPos + i*7) & 1L) != 0 || ((Board.EighthRow >>> arayPos + i*7) & 1L) != 0 ) {
                        break;
                    }
                }
            }

            //down right
            if (((Board.FirstRow >>> arayPos) & 1L) == 0) {

                for (int i = 1; i < 10; i++) {
                    if (!checkIfSquareisUsed(arayPos - i * 9)) {
                        Board.atackSquares |= (1L << arayPos - i * 9);
                    } else {
                        if (checkIfSquareisUsedByEnemeyBlack(arayPos - i * 9)) {
                            Board.atackSquares |= (1L << arayPos - i * 9);
                            Board.captureSquares |= (1L << arayPos - i * 9);
                        }
                        break;
                    }

                    if (((Board.FirstColumn >>> arayPos-i*9) & 1L) != 0 ||((Board.EighthColumn >>> arayPos-i*9) & 1L) != 0 || ((Board.FirstRow >>> arayPos-i*9) & 1L) != 0) {
                        break;
                    }
                }
            }

        }

        if (((Board.EighthColumn >>> arayPos) & 1L) == 0) {
            //down left
            if (((Board.FirstRow >>> arayPos) & 1L) == 0) {
                for (int i = 1; i < 10; i++) {

                    if (!checkIfSquareisUsed(arayPos - i * 7)) {
                        Board.atackSquares |= (1L << arayPos - i * 7);
                    } else {
                        if (checkIfSquareisUsedByEnemeyBlack(arayPos - i * 7)) {
                            Board.atackSquares |= (1L << arayPos - i * 7);
                            Board.captureSquares |= (1L << arayPos - i * 7);
                        }
                        break;
                    }
                    if (((Board.FirstColumn >>> arayPos-i*7) & 1L) != 0||((Board.EighthColumn >>> arayPos - i*7) & 1L) != 0 || ((Board.EighthRow >>> arayPos - i*7) & 1L) != 0 ) {
                        break;
                    }

                }
            }

            //top left
            if (((Board.EighthRow >>> arayPos) & 1L) == 0) {
                for (int i = 1; i < 10; i++) {

                    if (!checkIfSquareisUsed(arayPos + i * 9)) {
                        Board.atackSquares |= (1L << arayPos + i * 9);
                    } else {
                        if (checkIfSquareisUsedByEnemeyBlack(arayPos + i * 9)) {
                            Board.atackSquares |= (1L << arayPos + i * 9);
                            Board.captureSquares |= (1L << arayPos + i * 9);
                        }
                        break;
                    }
                    if (((Board.FirstColumn >>> arayPos+i*9) & 1L) != 0 ||((Board.EighthColumn >>> arayPos+i*9) & 1L) != 0 || ((Board.FirstRow >>> arayPos+i*9) & 1L) != 0) {
                        break;
                    }
                }
            }

        }
    }

    public static void BRMouvement(int arayPos) {
        // top
        if (((Board.EighthRow >>> arayPos) & 1L) == 0) {
            for (int i = 1; i < 9; i++) {
                if (!checkIfSquareisUsed(arayPos + i * 8)) {
                    Board.atackSquares |= (1L << arayPos + i * 8);
                } else {
                    if (checkIfSquareisUsedByEnemeyBlack(arayPos + i * 8)) {
                        Board.atackSquares |= (1L << arayPos + i * 8);
                        Board.captureSquares |= (1L << arayPos + i * 8);
                    }
                    break;
                }
                if (((Board.EighthRow >>> arayPos+i*8) & 1L) != 0) {break;}

            }
        }
        // down
        if (((Board.FirstRow >>> arayPos) & 1L)==0) {
            for (int i = 1; i < 9; i++) {
                if (!checkIfSquareisUsed(arayPos - i * 8)) {
                    Board.atackSquares |= (1L << arayPos - i * 8);
                } else {
                    if (checkIfSquareisUsedByEnemeyBlack(arayPos - i * 8)) {
                        Board.atackSquares |= (1L << arayPos - i * 8);
                        Board.captureSquares |= (1L << arayPos - i * 8);
                    }
                    break;
                }
                if (((Board.FirstRow >>> arayPos - i *8) & 1L) != 0) {break;}
            }
        }
        //left
        if (((Board.EighthColumn >>> arayPos) & 1L) == 0) {
            for (int i = 1; i < 9; i++) {

                if (!checkIfSquareisUsed(arayPos + i)) {
                    Board.atackSquares |= (1L << arayPos + i);
                } else {
                    if (checkIfSquareisUsedByEnemeyBlack(arayPos + i)) {
                        Board.atackSquares |= (1L << arayPos + i);
                        Board.captureSquares |= (1L << arayPos + i);
                    }
                    break;
                }
                if (((Board.EighthColumn >>> arayPos+i) & 1L) != 0) {
                    break;
                }
            }
        }

        //right
        if (((Board.FirstColumn >>> arayPos) & 1L)==0) {
            for (int i = 1; i < 9; i++) {

                if (!checkIfSquareisUsed(arayPos - i)) {
                    Board.atackSquares |= (1L << arayPos - i);
                } else {
                    if (checkIfSquareisUsedByEnemeyBlack(arayPos - i)) {
                        Board.atackSquares |= (1L << arayPos - i);
                        Board.captureSquares |= (1L << arayPos - i);
                    }
                    break;
                }
                if (((Board.FirstColumn >>> arayPos-i) & 1L)!=0) {
                    break;
                }
            }
        }
    }

    public static void BQMouvement(int arayPos){
        BBMouvement(arayPos);
        BRMouvement(arayPos);
    }

    public static void BKNMovement(int arayPos){
        //top two 
        if (Math.floor(arayPos / 8) != 7 && Math.floor(arayPos / 8) != 6){
            if (((Board.FirstColumn >>> arayPos) & 1L) == 0){
                if (!checkIfSquareisUsed(arayPos +15)) {
                    Board.atackSquares |= (1L << arayPos + 15); 
                }else{
                    if (checkIfSquareisUsedByEnemeyBlack(arayPos+15)){
                        Board.atackSquares |= (1L << arayPos + 15);
                        Board.captureSquares |= (1L << arayPos+15);
                    }
                }
            }
            if (((Board.EighthColumn >>> arayPos) & 1L) == 0){
                if (!checkIfSquareisUsed(arayPos +17)) {
                    Board.atackSquares |= (1L << arayPos + 17);
                }else{
                    if (checkIfSquareisUsedByEnemeyBlack(arayPos+17)){
                        Board.atackSquares |= (1L << arayPos + 17);
                        Board.captureSquares |= (1L << arayPos+17);
                    }
                }
            }
        }
        //down two

        if (Math.floor(arayPos / 8) != 0 && Math.floor(arayPos / 8) != 1){

            if (((Board.EighthColumn >>> arayPos) & 1L) == 0){
                if (!checkIfSquareisUsed(arayPos -15)) {
                    Board.atackSquares |= (1L << arayPos - 15); 
                }else{
                    if (checkIfSquareisUsedByEnemeyBlack(arayPos-15)){
                        Board.atackSquares |= (1L << arayPos - 15);
                        Board.captureSquares |= (1L << arayPos-15);
                    }
                }
            }
            if (((Board.FirstColumn >>> arayPos) & 1L) == 0){
                if (!checkIfSquareisUsed(arayPos -17)) {
                    Board.atackSquares |= (1L << arayPos - 17);
                }else{
                    if (checkIfSquareisUsedByEnemeyBlack(arayPos-17)){
                        Board.atackSquares |= (1L << arayPos - 17);
                        Board.captureSquares |= (1L << arayPos-17);
                    }
                }
            }
            
        }

        //two right
        if (((Board.FirstColumn >>> arayPos) & 1L) == 0&&((Board.SecondColumn >>> arayPos) & 1L) == 0){
            if (Math.floor(arayPos / 8) != 7){
                if (!checkIfSquareisUsed(arayPos +6)) {
                    Board.atackSquares |= (1L << arayPos + 6); 
                }else{
                    if (checkIfSquareisUsedByEnemeyBlack(arayPos+6)){
                        Board.atackSquares |= (1L << arayPos + 6);
                        Board.captureSquares |= (1L << arayPos+6);
                    }
                }
            }

            if (Math.floor(arayPos / 8) != 0){
                if (!checkIfSquareisUsed(arayPos -10)) {
                Board.atackSquares |= (1L << arayPos - 10);
                }
                else{
                    if (checkIfSquareisUsedByEnemeyBlack(arayPos-10)){
                        Board.atackSquares |= (1L << arayPos - 10);
                        Board.captureSquares |= (1L << arayPos-10);
                    }
                }
            }
            
        }

        //two left
        if (((Board.SeventhColumn >>> arayPos) & 1L) == 0&&((Board.EighthColumn >>> arayPos) & 1L) == 0){
            
            if (Math.floor(arayPos / 8) != 7){
                if (!checkIfSquareisUsed(arayPos +10)) {
                    Board.atackSquares |= (1L << arayPos + 10); 
                }else{
                    if (checkIfSquareisUsedByEnemeyBlack(arayPos+10)){
                        Board.atackSquares |= (1L << arayPos + 10);
                        Board.captureSquares |= (1L << arayPos+10);
                    }
                }
            }

            if (Math.floor(arayPos / 8) != 0){
                if (!checkIfSquareisUsed(arayPos -6)) {
                    Board.atackSquares |= (1L << arayPos - 6);
                }else{
                    if (checkIfSquareisUsedByEnemeyBlack(arayPos-6)){
                        Board.atackSquares |= (1L << arayPos - 6);
                        Board.captureSquares |= (1L << arayPos-6);
                    }
                }
            }
        }


    }

    public static void BKMovement(int arayPos){
        //right
        if (((Board.FirstColumn >>> arayPos) & 1L) == 0){
            if (!checkIfSquareisUsed(arayPos -1)) {
                Board.atackSquares |= (1L << arayPos - 1); 
            }else{
                if (checkIfSquareisUsedByEnemeyBlack(arayPos-1)){
                    Board.atackSquares |= (1L << arayPos - 1);
                    Board.captureSquares |= (1L << arayPos-1);
                }
            }

            if (Math.floor(arayPos/8) !=7){
                if (!checkIfSquareisUsed(arayPos +7)) {
                    Board.atackSquares |= (1L << arayPos + 7); 
                }else{
                    if (checkIfSquareisUsedByEnemeyBlack(arayPos+7)){
                        Board.atackSquares |= (1L << arayPos + 7);
                        Board.captureSquares |= (1L << arayPos+7);
                    }
                }
            }

            if (Math.floor(arayPos/8) !=0){
                if (!checkIfSquareisUsed(arayPos -9)) {
                    Board.atackSquares |= (1L << arayPos -9); 
                }else{
                    if (checkIfSquareisUsedByEnemeyBlack(arayPos-9)){
                        Board.atackSquares |= (1L << arayPos -9);
                        Board.captureSquares |= (1L << arayPos-9);
                    }
                }
            }
        }
        //left
        if (((Board.EighthColumn >>> arayPos) & 1L) == 0){
            if (!checkIfSquareisUsed(arayPos + 1)) {
                Board.atackSquares |= (1L << arayPos + 1); 
            }else{
                if (checkIfSquareisUsedByEnemeyBlack(arayPos + 1)){
                    Board.atackSquares |= (1L << arayPos + 1);
                    Board.captureSquares |= (1L << arayPos + 1);
                } 
            }

            if (Math.floor(arayPos/8) !=0){
                if (!checkIfSquareisUsed(arayPos -7)) {
                    Board.atackSquares |= (1L << arayPos - 7); 
                }else{
                    if (checkIfSquareisUsedByEnemeyBlack(arayPos-7)){
                        Board.atackSquares |= (1L << arayPos - 7);
                        Board.captureSquares |= (1L << arayPos-7);
                    }
                }
            }

            if (Math.floor(arayPos/8) !=7){
                if (!checkIfSquareisUsed(arayPos +9)) {
                    Board.atackSquares |= (1L << arayPos +9); 
                }else{
                    if (checkIfSquareisUsedByEnemeyBlack(arayPos+9)){
                        Board.atackSquares |= (1L << arayPos +9);
                        Board.captureSquares |= (1L << arayPos+9);
                    }
                }
            }
        }

        if (Math.floor(arayPos/8) !=7){
            if (!checkIfSquareisUsed(arayPos +8)) {
                Board.atackSquares |= (1L << arayPos +8); 
            }else{
                if (checkIfSquareisUsedByEnemeyBlack(arayPos+8)){
                    Board.atackSquares |= (1L << arayPos +8);
                    Board.captureSquares |= (1L << arayPos+8);
                }
            }
        }

        if (Math.floor(arayPos/8) !=0){
            if (!checkIfSquareisUsed(arayPos - 8)) {
                Board.atackSquares |= (1L << arayPos - 8); 
            }else{
                if (checkIfSquareisUsedByEnemeyBlack(arayPos - 8)){
                    Board.atackSquares |= (1L << arayPos - 8);
                    Board.captureSquares |= (1L << arayPos - 8);
                } 
            }
        }

        if (!blackKingHasBeenChecked && !blackKingHasBeenMoved){
            checkBlackCastling();
        }
    }

    //mooving funcions 

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

    public static boolean checkIfSquareisUsedByEnemeyBlack(int i){
        
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

    public static void checkWhiteCastling(){
        if ((Board.whiteRoocks & 1L ) != 0 && !checkIfSquareisUsed(1) && !checkIfSquareisUsed(2)) {
            Board.atackSquares |= (1L << 1);
        }
        if (((Board.whiteRoocks >>> 7) & 1L) != 0 && !checkIfSquareisUsed(6) && !checkIfSquareisUsed(5) && !checkIfSquareisUsed(4)) {
            Board.atackSquares |= (1L << 5);
        }
    }
    
    public static void checkBlackCastling(){
       
        if (((Board.blackRoocks >>> 56) & 1L) != 0 && !checkIfSquareisUsed(57) && !checkIfSquareisUsed(58)) {
            Board.atackSquares |= (1L << 57);
        }
        if (((Board.blackRoocks >>> 63) & 1L) != 0 && !checkIfSquareisUsed(62) && !checkIfSquareisUsed(61) && !checkIfSquareisUsed(60)) {
            Board.atackSquares |= (1L << 61);
        }
    }

    public static void checkWhiteCheck(int arayPos){
        int piece ;
        boolean piecePased = false;
        long checkSquares = 0;
        whiteIsInCheck = false;
        // Check bishop check
        if (((Board.FirstColumn >>> arayPos) & 1L) == 0) {
            
            //top right  
            if (((Board.EighthRow >>> arayPos) & 1L) == 0) {
                for (int i = 1; i < 10; i++) {

                    
                    checkSquares |= (1L << arayPos + i*7);
                    
                    
                    if (checkIfSquareisUsedByEnemeyBlack(arayPos + i*7)){
                        piecePased = true;
                    }

                    if (  checkIfSquareisUsedByEnemey(arayPos + i * 7)) {
                        
                        if (returnPiece(arayPos + i*7)== 8 ||returnPiece(arayPos + i*7) ==10){
                            Board.checkSquares += checkSquares;
                            if (!piecePased){
                                whiteIsInCheck = true;
                            }
                        }
                        
                        break;
                    }
                    if (((Board.FirstColumn >>> arayPos+i*7) & 1L) != 0||((Board.EighthColumn >>> arayPos + i*7) & 1L) != 0 || ((Board.EighthRow >>> arayPos + i*7) & 1L) != 0 ){
                        break;
                    }
                }
            }
            checkSquares = 0;
            piecePased = false;
            //down right
            if (((Board.FirstRow >>> arayPos) & 1L) == 0) {

                for (int i = 1; i < 10; i++) {

                    
                    checkSquares |= (1L << arayPos -i*9);
                    

                    if (checkIfSquareisUsedByEnemeyBlack(arayPos - i*9)){
                        piecePased = true;
                    }

                    if (checkIfSquareisUsed(arayPos - i * 9) && checkIfSquareisUsedByEnemey(arayPos - i * 9)) {
                        piece = returnPiece(arayPos -i*9);
                        if (piece ==8 || piece ==10){
                            Board.checkSquares += checkSquares;
                            if (!piecePased){
                                whiteIsInCheck = true;
                            }

                        }
                        break;
                    }

                    if (((Board.FirstColumn >>> arayPos-i*9) & 1L) != 0 ||((Board.EighthColumn >>> arayPos-i*9) & 1L) != 0 || ((Board.FirstRow >>> arayPos-i*9) & 1L) != 0) {
                        break;
                    }
                }
            }

            checkSquares = 0;
            piecePased = false;

        }
        if (((Board.EighthColumn >>> arayPos) & 1L) == 0) {
            //down left
            if (((Board.FirstRow >>> arayPos) & 1L) == 0) {
                for (int i = 1; i < 10; i++) {
                
                    
                    checkSquares  |= (1L << arayPos - i*7);
                    

                    if (checkIfSquareisUsedByEnemeyBlack(arayPos - i*7)){
                        piecePased = true;
                    }

                    if ( checkIfSquareisUsedByEnemey(arayPos - i * 7)) {
                        piece = returnPiece(arayPos - i*7);
                        if (piece == 8 || piece == 10 ){
                            Board.checkSquares += checkSquares;
                            if (!piecePased){
                                whiteIsInCheck = true;
                            }
                        }
                        break;
                    }
                    if (((Board.FirstColumn >>> arayPos-i*7) & 1L) != 0||((Board.EighthColumn >>> arayPos - i*7) & 1L) != 0 ||((Board.FirstRow >>> arayPos -i*7)& 1L) != 0) {
                        break;
                    }

                }
            }

            checkSquares = 0;
            piecePased = false;

            //top left
            if (((Board.EighthRow >>> arayPos )& 1L)==0) {
                for (int i = 1; i < 10; i++) {

                    
                    checkSquares |= (1L << arayPos +i*9);
                    

                    if (checkIfSquareisUsedByEnemeyBlack(arayPos +i*9)){
                        piecePased = true;
                    } 

                    if ( checkIfSquareisUsedByEnemey(arayPos + i * 9)) {
                        piece = returnPiece(arayPos+ i*9) ;

                        if (piece == 8 || piece == 10){
                            Board.checkSquares += checkSquares;
                            if (!piecePased){
                                whiteIsInCheck = true;
                            }
                        }
                        
                        break;
                    }
                    if (((Board.FirstColumn >>> arayPos+i*9) & 1L) != 0 ||((Board.EighthColumn >>> arayPos+i*9) & 1L) != 0 || ((Board.EighthRow >>> arayPos+i*9) & 1L) != 0) {
                        break;
                    }
                }
            }
            System.out.println(whiteIsInCheck);

        }
    }

    public static int returnPiece(int i ){

        if (((Board.whitePawns >>> i) & 1L) != 0) {
            return 1;
        }
        if (((Board.whiteBishops >>> i) & 1L) != 0) {
            return 2;
        }
        if (((Board.whiteRoocks >>> i) & 1L) != 0) {
            return 3;
        }
        if (((Board.whiteKnights >>> i) & 1L) != 0) {
            return 5;
        }
        if (((Board.whiteQueen >>> i) & 1L) != 0) {
            return 4;
        }
        if (((Board.whiteKing >>> i) & 1L) != 0) {
            return 6;
        }
    
        

        if (((Board.blackPawns >>> i) & 1L) != 0) {
            return 7;
        }
        if (((Board.blackBishops >>> i) & 1L) != 0) {
            return 8;
        }
        if (((Board.blackRoocks >>> i) & 1L) != 0) {
            return 9;
        }
        if (((Board.blackKnights >>> i) & 1L) != 0) {
            return 11;
        }
        if (((Board.blackQueen >>> i) & 1L) != 0) {
            return 10;
        }
        if (((Board.blackKing >>> i) & 1L) != 0) {
            return 12;
        }
        
        return 0;
    }

}


