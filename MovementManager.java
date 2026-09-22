public class MovementManager {

    public static int pieceMoved = 0;
    public static int pieceMovedPos = 0;

    public static boolean WhiteKingHasBeenMoved = false;
    public static boolean blackKingHasBeenMoved = false;

    public static boolean WhiteKingHasBeenChecked = false;
    public static boolean blackKingHasBeenChecked = false;

    public static int whiteKingPos = 3;
    public static int blackKingPos = 59;

    public static boolean whiteIsInCheck = false ;
    public static boolean blackIsInCheck = false ;

    public static boolean turn = true ;

    public static boolean gameFinished = false ;

    public static boolean whitePromotionUI = false;
    public static boolean blackPromotionUI = false;

    public static int promotionPiece = 0;
    public static int promotionsquare = 0;

    public static boolean WhiteLeftRookHasBeenMoved = false;
    public static boolean WhiteRightRookHasBeenMoved = false;

    public static boolean BlackLeftRookHasBeenMoved = false;
    public static boolean BlackRightRookHasBeenMoved = false;

    public static boolean BlackAi = true;

    public static int count = 0;

    public static void showAtackSquare(int pieceType , int arayPos){
        if (gameFinished){
            System.out.println("the game is finished " + turn + " lost");
            return;
        }
        Board.atackSquares = 0;
        pieceMoved = pieceType;
        pieceMovedPos = arayPos;
        Board.captureSquares = 0;
        
        if (turn == true){
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
        } else {

            if (pieceType == 7) {
                BPMouvement(arayPos);
            }
            if (pieceType == 8) {
                BBMouvement(arayPos);
            }
            if (pieceType == 9) {
                BRMouvement(arayPos);
            }
            if (pieceType == 10) {
                BQMouvement(arayPos);
            }
            if (pieceType == 11) {
                BKNMovement(arayPos);
            }
            if (pieceType == 12) {
                BKMovement(arayPos);
            }

        }

    }
    
    public static void movePiece(int arayPos ){

        if (((Board.atackSquares >>> arayPos) & 1L) == 0){

            return;
        }
        long curentBoard = 0 ;

        if (turn == true){
            
            if (pieceMoved == 1){
                curentBoard = Board.whitePawns ;
                if (!Board.WEmpassentSquares[arayPos]){
                    java.util.Arrays.fill(Board.WEmpassentSquares, false);
                }
            }
            if (pieceMoved == 2){
                curentBoard = Board.whiteBishops ;
            }
            if (pieceMoved == 3){
                if (pieceMovedPos == 0){
                    WhiteRightRookHasBeenMoved=true;
                }
                if (pieceMovedPos == 7){
                    WhiteLeftRookHasBeenMoved=true;
                }
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
                    if (arayPos == 1&& !WhiteRightRookHasBeenMoved){
                        Board.whiteRoocks ^= (1L) ;

                        Board.whiteRoocks ^= (1L << 2) ;
                    }
                    if (arayPos == 5 && !WhiteLeftRookHasBeenMoved){
                        Board.whiteRoocks ^= (1L << 7) ;

                        Board.whiteRoocks ^= (1L << 4) ;
                    }
                }
                whiteKingPos = arayPos;
                WhiteKingHasBeenMoved = true;
                curentBoard = Board.whiteKing ; 
            }
        }else{
            
            if (pieceMoved == 7){
                curentBoard = Board.blackPawns ;
                if (!Board.BEmpassentSquares[arayPos]){
                    java.util.Arrays.fill(Board.BEmpassentSquares, false);
                }
            }
            if (pieceMoved == 8){
                curentBoard = Board.blackBishops ;
            }
            if (pieceMoved == 9){
                if (pieceMovedPos == 56){
                    BlackRightRookHasBeenMoved=true;
                }
                if (pieceMovedPos == 63){
                    BlackLeftRookHasBeenMoved=true;
                }
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
                    if (arayPos == 61 && !BlackLeftRookHasBeenMoved){
                        Board.blackRoocks ^= (1L << 63) ;

                        Board.blackRoocks ^= (1L << 60) ;
                    }
                    if (arayPos == 57 && !BlackRightRookHasBeenMoved){
                        Board.blackRoocks ^= (1L << 56) ;

                        Board.blackRoocks ^= (1L << 58) ;
                    }
                }
                blackKingHasBeenMoved = true;
                blackKingPos = arayPos;
                curentBoard = Board.blackKing ;
            }
        }


        if (pieceMoved != 1||pieceMoved !=7){
            count ++;
        }else{
            count = 0;
        }

        if (((Board.captureSquares >>> arayPos) & 1L) != 0) {
            count =0;
            if (turn){
                
                if (arayPos>= 8 &&  Board.BEmpassentSquares[arayPos-8] && pieceMoved == 1 && (pieceMovedPos == arayPos+1 ||pieceMovedPos == arayPos-1) ) {
                    java.util.Arrays.fill(Board.BEmpassentSquares, false);
                    removePiece(arayPos-8);
                }else{
                    java.util.Arrays.fill(Board.BEmpassentSquares, false);
                    removePiece(arayPos);
                }
            }else{
                if (arayPos <= 55 && Board.WEmpassentSquares[arayPos+8] && pieceMoved == 7 &&(pieceMovedPos == arayPos+1 ||pieceMovedPos == arayPos-1 )){
                    java.util.Arrays.fill(Board.WEmpassentSquares, false);
                    removePiece(arayPos+8);
                }else{
                    java.util.Arrays.fill(Board.BEmpassentSquares, false);
                    removePiece(arayPos);
                }
            }
        }

        curentBoard ^= (1L << pieceMovedPos) ;

        curentBoard ^= (1L << arayPos) ;


        if (pieceMoved == 1 && ((Board.EighthRow >>> arayPos ) &1L ) == 1 ){
            
            whitePromotionUI = true;
            promotionsquare = arayPos;
         
        }
        if (pieceMoved == 7 && ((Board.FirstRow >>> arayPos ) &1L ) == 1 ){
            blackPromotionUI = true;
            promotionsquare = arayPos;
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
        updateWhiteBoard();
        updateBlackBoard();
        if (turn == true){
            checkBlackCheck(blackKingPos);
            if (blackIsInCheck){
                blackKingHasBeenChecked = true;
            }
            WhiteAtackSquares();
            turn = false;
        }else{
            checkWhiteCheck(whiteKingPos);
            if (whiteIsInCheck){
                WhiteKingHasBeenChecked = true;
            }
            BlackAtackSquares();
            turn=true;
        }

        if (count >= 50){
            gameFinished = true;
        }

        
        if (whiteIsInCheck && checkWhiteMateCheck()){
            gameFinished = true;
            System.out.println("white lost");
            try {
            Thread.sleep(6000);
            } catch (InterruptedException e) {
               Thread.currentThread().interrupt();
            }
        }
        if (blackIsInCheck && checkBlackMateCheck()){
            System.out.println("black lost");
            try {
            Thread.sleep(6000);
            } catch (InterruptedException e) {
               Thread.currentThread().interrupt();
            }
            gameFinished = true;
        }

        
        Board.atackSquares = 0;
        Board.captureSquares = 0;
        pieceMoved = 0;
        pieceMovedPos = 0;

        System.out.println(count);
        
    }
    
    //white pieces movem ent 

    public static void WPMouvement(int arayPos){
        
        //empassant logic 
        if (((Board.FifthRow >>> arayPos) & 1L) == 1) {
            if (((Board.FirstColumn >>> arayPos) & 1L) == 0) {
                if ( returnPiece(arayPos-1) == 7 && Board.BEmpassentSquares[arayPos-1]){
                    Board.atackSquares |= (1L << arayPos + 7);
                    Board.captureSquares |= (1L << arayPos + 7);
                }
            }
        }
        if (((Board.FifthRow >>> arayPos) & 1L) == 1) {
            if (((Board.EighthColumn>>> arayPos) & 1L) == 0) {
                if ( returnPiece(arayPos+1) == 7 && Board.BEmpassentSquares[arayPos+1]){
                    Board.atackSquares |= (1L << arayPos + 9);
                    Board.captureSquares |= (1L << arayPos + 9);
                }
            }
        }
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
                        }else{
                            if (((Board.checkSquares >>>arayPos+9 ) & 1L ) == 1){
                                Board.atackSquares |= (1L << arayPos + 9);
                                Board.captureSquares |= (1L << arayPos + 9);
                            }
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
                        }else{if (((Board.checkSquares >>> arayPos+8)& 1L )==1){
                            Board.atackSquares |= (1L << arayPos + 8);
                        }}
                        
                    }else{
                        if (((Board.checkSquares >>> arayPos +8) &1L ) ==1 ){
                            Board.atackSquares |= (1L << arayPos + 8);
                        }
                    }
                    
                    if (!checkIfSquareisUsed(arayPos + 16)) {
                        if (!whiteIsInCheck){
                            if (((Board.checkSquares >>> arayPos) &1L ) ==0 ){
                                java.util.Arrays.fill(Board.WEmpassentSquares, false);
                                Board.atackSquares |= (1L << arayPos + 16);
                                Board.WEmpassentSquares[arayPos + 16 ] = true;
                            }else{
                                if (((Board.checkSquares >>> arayPos+16)& 1L )==1){
                                    Board.atackSquares |= (1L << arayPos + 16);
                                    Board.WEmpassentSquares[arayPos + 16 ] = true;
                                }
                            }
                        }else{
                            if (((Board.checkSquares >>> arayPos +16) &1L ) ==1 ){
                                java.util.Arrays.fill(Board.WEmpassentSquares, false);
                                Board.atackSquares |= (1L << arayPos + 16);
                                Board.WEmpassentSquares[arayPos + 16 ] = true;
                            }
                        }
                    }
                }
            } else if (((Board.EighthRow >>> arayPos) & 1L) == 0)  {

                if (!checkIfSquareisUsed(arayPos + 8)) {
                    if (!whiteIsInCheck){
                        if (((Board.checkSquares >>> arayPos) &1L ) ==0 ){
                            Board.atackSquares |= (1L << arayPos + 8);
                        }else{if (((Board.checkSquares >>> arayPos+8)& 1L )==1){
                                Board.atackSquares |= (1L << arayPos + 8);
                            }
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
    
    public static void WBM(int arayPos , int value , long bOne , long bTwo , int[] values){
        if (((bOne >>> arayPos) & 1L) == 0) {

            //top right  
            if (((bTwo >>> arayPos) & 1L) == 0) {
                for (int i = 1; i < 10; i++) {
                    
                    if (!checkIfSquareisUsed(arayPos + i * values[value] )) {
                        if (!whiteIsInCheck){
                            Board.atackSquares |= (1L << arayPos + i * values[value]);
                        }else{
                            if (((Board.checkSquares >>> arayPos+ i *values[value])& 1L ) == 1){
                                Board.atackSquares |= (1L << arayPos + i * values[value]);
                            }
                        }
                    } else {
                        if (checkIfSquareisUsedByEnemey(arayPos + i * values[value])) {
                            if (!whiteIsInCheck){
                                Board.atackSquares |= (1L << arayPos + i * values[value]);
                                Board.captureSquares |= (1L << arayPos + i * values[value]);
                            }else {
                                if (((Board.checkSquares >>> arayPos+ i *values[value])& 1L ) == 1){
                                    Board.atackSquares |= (1L << arayPos + i * values[value]);
                                    Board.captureSquares |= (1L << arayPos + i * values[value]);
                                }
                            }
                        }
                        break;
                    }
                    
                    if (((Board.boundaries >>> arayPos+i*values[value]) & 1L) != 0 ){
                        break;
                    }
                }
            }
        }
    }
    
    public static void WBMouvement(int arayPos) {

        int[] values = {7, -9 , -7 ,9};
        long boundarieOne =0;
        long boundarieTwo = 0 ;

        for (int i = 0; i < values.length ; i++){
            if (i ==0){
                boundarieOne = Board.FirstColumn;
                boundarieTwo = Board.EighthRow;
            }if(i == 1){
                boundarieOne = Board.FirstColumn;
                boundarieTwo = Board.FirstRow;
            }if (i ==2){
                boundarieOne = Board.EighthColumn;
                boundarieTwo = Board.FirstRow;
            }if(i == 3){
                boundarieOne = Board.EighthRow;
                boundarieTwo = Board.EighthColumn;
            }
            WBM(arayPos, i, boundarieOne, boundarieTwo, values);
        }        
    }

    public static void WRM(int arayPos , int value , long bOne , int[] values){
       if (((bOne >>> arayPos) & 1L) == 0) {
            for (int i = 1; i < 9; i++) {

                
                if (!checkIfSquareisUsed(arayPos + i *values[value])) {
                    
                    if (!whiteIsInCheck){
                        Board.atackSquares |= (1L << arayPos + i * values[value]);
                    }else {
                        if (((Board.checkSquares >>> arayPos+ i *values[value])& 1L ) == 1){
                            Board.atackSquares |= (1L << arayPos + i * values[value]);
                        }
                    }
                }else{
                    if (checkIfSquareisUsedByEnemey(arayPos + i * values[value])) {
                        if (!whiteIsInCheck){
                            Board.atackSquares |= (1L << arayPos + i * values[value]);
                            Board.captureSquares |= (1L << arayPos + i * values[value]);
                        }else {
                            if (((Board.checkSquares >>> arayPos+ i *values[value])& 1L ) == 1){
                                Board.atackSquares |= (1L << arayPos + i * values[value]);
                                Board.captureSquares |= (1L << arayPos + i * values[value]);
                            }
                        }
                    }
                    break;
                }
                if (((bOne >>> arayPos+i*values[value]) & 1L) != 0) {break;}
            }
        }
    }

    public static void WRMouvement(int arayPos) {
        int[] values = {8, -8 , 1 ,-1};
        long boundarieOne =0;

        for (int i = 0; i < values.length ; i++){
            if (i ==0){
                boundarieOne = Board.EighthRow; 
            }if(i == 1){
                boundarieOne =Board.FirstRow;
            }if (i ==2){
                boundarieOne = Board.EighthColumn;
            }if(i == 3){
                boundarieOne = Board.FirstColumn;
            }
            WRM(arayPos, i, boundarieOne,  values);
        }

    }

    public static void WQMouvement(int arayPos){
        WBMouvement(arayPos);
        WRMouvement(arayPos);
    }

    public static void WKNM(int arayPos , int value , long bOne , long bTwo ,long bThree, int[] values){
        if (((bOne >>> arayPos) & 1L) == 0&&((bTwo >>> arayPos) & 1L) == 0){
            
             if (((bThree >>> arayPos) & 1L) == 0){
                if (((Board.checkSquares >>> arayPos )& 1L ) == 1){
                    return;
                }
                if (!checkIfSquareisUsed(arayPos +values[value])) {
                    if (!whiteIsInCheck){
                        Board.atackSquares |= (1L << arayPos + values[value]);
                    }else {
                        if (((Board.checkSquares >>> arayPos + values[value] )& 1L ) ==1){
                            Board.atackSquares |= (1L << arayPos + values[value]);
                        }
                    }
                    
                }else{
                    if (checkIfSquareisUsedByEnemey(arayPos+values[value])){
                        if (!whiteIsInCheck){
                            Board.atackSquares |= (1L << arayPos + values[value]);
                            Board.captureSquares |= (1L << arayPos+values[value]);
                        }else {
                            if (((Board.checkSquares >>> arayPos + values[value] )& 1L ) ==1){
                                Board.atackSquares |= (1L << arayPos + values[value]);
                                Board.captureSquares |= (1L << arayPos+values[value]);
                            }
                        }
                        
                    }
                }
            }
        }
    }

    public static void WKNMovement(int arayPos){
        
        int[] values = {15, 17, -15, -17, 6, -10, 10, -6 }; 
        long boundarieOne =0;
        long boundarieTwo = 0 ;
        long boundarieThree = 0 ;

        for (int i = 0; i < values.length ; i++){
            //top two
            if (i ==0){//15
                boundarieOne = Board.EighthRow;
                boundarieTwo = Board.SeventhRow;
                boundarieThree = Board.FirstColumn ;
            }if(i == 1){//17
                boundarieOne = Board.EighthRow;
                boundarieTwo = Board.SeventhRow;
                boundarieThree = Board.EighthColumn;
            }
            //down two
            if (i ==2){//-15
                boundarieOne = Board.FirstRow;
                boundarieTwo = Board.SecondRow ;
                boundarieThree =Board.EighthColumn ;
            }if(i == 3){//-17
                boundarieOne = Board.FirstRow;
                boundarieTwo = Board.SecondRow ;
                boundarieThree = Board.FirstColumn;
            }
            //two right
            if (i ==4){//6
                boundarieOne = Board.FirstColumn;
                boundarieTwo = Board.SecondColumn ;
                boundarieThree =Board.EighthRow ;
            }if(i == 5){//-10
                boundarieOne = Board.FirstColumn;
                boundarieTwo = Board.SecondColumn ;
                boundarieThree = Board.FirstRow;
            }
            //two left
            if (i ==6){//10
                boundarieOne = Board.EighthColumn;
                boundarieTwo = Board.SeventhColumn;
                boundarieThree = Board.EighthRow ;
            }if(i == 7){//-6
                boundarieOne = Board.EighthColumn;
                boundarieTwo = Board.SeventhColumn;
                boundarieThree = Board.FirstRow;
            }
            WKNM(arayPos, i, boundarieOne, boundarieTwo , boundarieThree, values);
        }  
    }

    public static void WKM(int arayPos , int value , long bOne , long bTwo , int[] values){
        
        if (((bOne >>> arayPos) & 1L) == 0){

            if (((bTwo >>> arayPos) & 1L) == 0){
                if (!checkIfSquareisUsed(arayPos +values[value])) {
                    if (!whiteIsInCheck){
                        if (((Board.BlackAtackSquares >>> arayPos + values[value] )& 1L)==0) {
                            Board.atackSquares |= (1L << arayPos + values[value]); 
                        }
                    }else {
                        if (((Board.BlackAtackSquares >>> arayPos + values[value] )& 1L)==0) {
                           Board.atackSquares |= (1L << arayPos + values[value]);  
                        }
                    }
                    
                }else{
                    if (checkIfSquareisUsedByEnemey(arayPos+values[value])){
                        if (!whiteIsInCheck){
                            if (((Board.BlackAtackSquares >>> arayPos + values[value] )& 1L)==0) {
                                Board.atackSquares |= (1L << arayPos + values[value]);
                                Board.captureSquares |= (1L << arayPos+values[value]);
                            }
                        }else {
                            
                            if (((Board.checkSquares >>> arayPos + values[value] )& 1L)==0 && ((Board.BlackAtackSquares >>> arayPos + values[value] )& 1L)==0) {

                                Board.atackSquares |= (1L << arayPos + values[value]);
                                Board.captureSquares |= (1L << arayPos+values[value]);
                                
                            }
                        }
                        
                    }
                }
            }
        }
    }

    public static void WKMovement(int arayPos){

        BlackAtackSquares();

        int[] values = {1, -1 , 7 ,-7, 8 , -8 , 9 , -9};
        long boundarieOne =0;
        long boundarieTwo =0;

        for (int i = 0; i < values.length ; i++){
            if (i ==0){
                boundarieOne = Board.EighthColumn; 
                
            }if(i == 1){
                boundarieOne =Board.FirstColumn;
                
            }if (i ==2){
                boundarieOne = Board.EighthRow;
                boundarieTwo = Board.FirstColumn;
            }if(i == 3){
                boundarieOne = Board.FirstRow;
                boundarieTwo = Board.EighthColumn;
            }
            if (i ==4){
                boundarieOne = Board.EighthRow; 
                boundarieTwo = 0;

            }if(i == 5){
                boundarieOne =Board.FirstRow;
                boundarieTwo = 0;
            }if (i ==6){
                boundarieOne = Board.EighthRow;
                boundarieTwo = Board.EighthColumn;
            }if(i == 7){
                boundarieOne = Board.FirstRow;
                boundarieTwo =Board.FirstColumn;
            }
            WKM(arayPos, i, boundarieOne,boundarieTwo,  values);
        }

        if (!WhiteKingHasBeenChecked && !WhiteKingHasBeenMoved){
            checkWhiteCastling();
        }

        
    }

    //Black pieces movement

    public static void BPMouvement(int arayPos){
        //empassent 
        if (((Board.FourthRow >>> arayPos) & 1L) == 1) {
            if (((Board.FirstColumn >>> arayPos) & 1L) == 0) {
                if ( returnPiece(arayPos-1) == 1 && Board.WEmpassentSquares[arayPos-1]){
                    System.out.println("right");
                    Board.atackSquares |= (1L << arayPos - 9);
                    Board.captureSquares |= (1L << arayPos - 9);
                }
            }
        }
        if (((Board.FourthRow >>> arayPos) & 1L) == 1) {
            if (((Board.EighthRow >>> arayPos) & 1L) == 0) {
                if ( returnPiece(arayPos+1) == 1 && Board.WEmpassentSquares[arayPos+1]){
                    System.out.println("left");
                    Board.atackSquares |= (1L << arayPos - 7);
                    Board.captureSquares |= (1L << arayPos - 7);
                }
            }
        }
        //atack movement 
        if (((Board.FirstColumn >>> arayPos) & 1L) == 0) {
            if (!blackIsInCheck){
                if (checkIfSquareisUsedByEnemeyBlack(arayPos - 7)) {
                    Board.atackSquares |= (1L << arayPos - 7);
                    Board.captureSquares |= (1L << arayPos - 7);
                }   
            }else{
                if (checkIfSquareisUsedByEnemeyBlack(arayPos - 7)) {
                    if (((Board.checkSquares >>> arayPos - 7)& 1L)==1){
                        Board.atackSquares |= (1L << arayPos - 7);
                        Board.captureSquares |= (1L << arayPos - 7);
                    }
                }
            }
            
        }

        if (((Board.EighthColumn >>> arayPos) & 1L) == 0) {
            if (!blackIsInCheck){
                if (checkIfSquareisUsedByEnemeyBlack(arayPos - 9)) {
                    Board.atackSquares |= (1L << arayPos - 9);
                    Board.captureSquares |= (1L << arayPos - 9);
                }
            }else{
                if (checkIfSquareisUsedByEnemeyBlack(arayPos - 9)) {
                    if (((Board.checkSquares >>> arayPos - 9)& 1L)==1){
                        Board.atackSquares |= (1L << arayPos - 9);
                        Board.captureSquares |= (1L << arayPos - 9);
                    }
                }
            }
        }
        //if its on the second row advance two
        if (((Board.SeventhRow >>> arayPos) & 1L) != 0) {
                if (!checkIfSquareisUsed(arayPos - 8)) {
                    if (!blackIsInCheck){
                        if (((Board.checkSquares >>> arayPos) &1L ) ==0 ){
                            Board.atackSquares |= (1L << arayPos - 8);
                        }else{if (((Board.checkSquares >>> arayPos-8)& 1L )==1){
                            Board.atackSquares |= (1L << arayPos - 8);
                        }}
                        
                    }else{
                        if (((Board.checkSquares >>> arayPos -8) &1L ) ==1 ){
                            Board.atackSquares |= (1L << arayPos - 8);
                        }
                    }
                    
                    if (!checkIfSquareisUsed(arayPos - 16)) {
                        if (!blackIsInCheck){
                            if (((Board.checkSquares >>> arayPos) &1L ) ==0 ){
                                java.util.Arrays.fill(Board.BEmpassentSquares, false);
                                Board.atackSquares |= (1L << arayPos - 16);
                                Board.BEmpassentSquares[arayPos - 16 ] = true;
                            }else{
                                if (((Board.checkSquares >>> arayPos-16)& 1L )==1){
                                    java.util.Arrays.fill(Board.BEmpassentSquares, false);
                                    Board.atackSquares |= (1L << arayPos - 16);
                                    Board.BEmpassentSquares[arayPos - 16 ] = true;
                                }
                            }
                        }else{
                            if (((Board.checkSquares >>> arayPos -16) &1L ) ==1 ){
                                Board.atackSquares |= (1L << arayPos - 16);
                                Board.BEmpassentSquares[arayPos - 16 ] = true;
                            }
                        }
                    }
                }
            } else if (((Board.FirstRow >>> arayPos) & 1L) == 0) {
                if (!checkIfSquareisUsed(arayPos - 8)) {
                    if (!blackIsInCheck){
                        if (((Board.checkSquares >>> arayPos) &1L ) ==0 ){
                            Board.atackSquares |= (1L << arayPos - 8);
                        }else{if (((Board.checkSquares >>> arayPos-8)& 1L )==1){
                                Board.atackSquares |= (1L << arayPos - 8);
                            }
                        }
                    }else{
                        if (((Board.checkSquares >>> arayPos -8) &1L ) ==1 ){
                            Board.atackSquares |= (1L << arayPos - 8);
                        }
                    }
                }
            }

       
        
    }
    
    public static void BBM(int arayPos , int value , long bOne , long bTwo , int[] values){
        if (((bOne >>> arayPos) & 1L) == 0) { 
            //top right  
            if (((bTwo >>> arayPos) & 1L) == 0) {
                for (int i = 1; i < 10; i++) {
                    
                    if (!checkIfSquareisUsed(arayPos + i * values[value] )) {
                        if (!blackIsInCheck){
                            Board.atackSquares |= (1L << arayPos + i * values[value]);
                        }else{
                            if (((Board.checkSquares >>> arayPos+ i *values[value])& 1L ) == 1){
                                Board.atackSquares |= (1L << arayPos + i * values[value]);
                            }
                        }
                    } else {
                        if (checkIfSquareisUsedByEnemeyBlack(arayPos + i * values[value])) {
                            if (!blackIsInCheck){
                                Board.atackSquares |= (1L << arayPos + i * values[value]);
                                Board.captureSquares |= (1L << arayPos + i * values[value]);
                            }else {
                                if (((Board.checkSquares >>> arayPos+ i *values[value])& 1L ) == 1){
                                    Board.atackSquares |= (1L << arayPos + i * values[value]);
                                    Board.captureSquares |= (1L << arayPos + i * values[value]);
                                }
                            }
                        }
                        break;
                    }
                    
                    if (((Board.boundaries >>> arayPos+i*values[value]) & 1L) != 0 ){
                        break;
                    }
                }
            }
        }
    }
    
    public static void BBMouvement(int arayPos) {

        int[] values = {7, -9 , -7 ,9};
        long boundarieOne =0;
        long boundarieTwo = 0 ;

        for (int i = 0; i < values.length ; i++){
            if (i ==0){
                boundarieOne = Board.FirstColumn;
                boundarieTwo = Board.EighthRow;
            }if(i == 1){
                boundarieOne = Board.FirstColumn;
                boundarieTwo = Board.FirstRow;
            }if (i ==2){
                boundarieOne = Board.EighthColumn;
                boundarieTwo = Board.FirstRow;
            }if(i == 3){
                boundarieOne = Board.EighthRow;
                boundarieTwo = Board.EighthColumn;
            }
            BBM(arayPos, i, boundarieOne, boundarieTwo, values);
        }        
    }

    public static void BRM(int arayPos , int value , long bOne , int[] values){
        if (((bOne >>> arayPos) & 1L) == 0) {
            for (int i = 1; i < 9; i++) {
                if (!checkIfSquareisUsed(arayPos + i *values[value])) {
                    if (!blackIsInCheck){
                        Board.atackSquares |= (1L << arayPos + i * values[value]);
                    }else {
                        if (((Board.checkSquares >>> arayPos+ i *values[value])& 1L ) == 1){
                            Board.atackSquares |= (1L << arayPos + i * values[value]);
                        }
                    }
                }else{
                    if (checkIfSquareisUsedByEnemeyBlack(arayPos + i * values[value])) {
                        if (!blackIsInCheck){
                            Board.atackSquares |= (1L << arayPos + i * values[value]);
                            Board.captureSquares |= (1L << arayPos + i * values[value]);
                        }else {
                            if (((Board.checkSquares >>> arayPos+ i *values[value])& 1L ) == 1){
                                Board.atackSquares |= (1L << arayPos + i * values[value]);
                                Board.captureSquares |= (1L << arayPos + i * values[value]);
                            }
                        }
                    }
                    break;
                }
                if (((bOne >>> arayPos+i*values[value]) & 1L) != 0) {break;}
            }
        }
    }

    public static void BRMouvement(int arayPos) {
        int[] values = {8, -8 , 1 ,-1};
        long boundarieOne =0;

        for (int i = 0; i < values.length ; i++){
            if (i ==0){
                boundarieOne = Board.EighthRow; 
            }if(i == 1){
                boundarieOne =Board.FirstRow;
            }if (i ==2){
                boundarieOne = Board.EighthColumn;
            }if(i == 3){
                boundarieOne = Board.FirstColumn;
            }
            BRM(arayPos, i, boundarieOne,  values);
        }

    }

    public static void BQMouvement(int arayPos){
        BBMouvement(arayPos);
        BRMouvement(arayPos);
    }

    public static void BKNM(int arayPos , int value , long bOne , long bTwo ,long bThree, int[] values){
        if (((bOne >>> arayPos) & 1L) == 0&&((bTwo >>> arayPos) & 1L) == 0){
             if (((bThree >>> arayPos) & 1L) == 0){
                if (((Board.checkSquares >>> arayPos )& 1L ) == 1){
                    return;
                }
                if (!checkIfSquareisUsed(arayPos +values[value])) {
                    if (!blackIsInCheck){
                        Board.atackSquares |= (1L << arayPos + values[value]);
                    }else {
                        if (((Board.checkSquares >>> arayPos + values[value] )& 1L ) ==1){
                            Board.atackSquares |= (1L << arayPos + values[value]);
                        }
                    }
                    
                }else{
                    if (checkIfSquareisUsedByEnemeyBlack(arayPos+values[value])){
                        if (!blackIsInCheck){
                            Board.atackSquares |= (1L << arayPos + values[value]);
                            Board.captureSquares |= (1L << arayPos+values[value]);
                        }else {
                            if (((Board.checkSquares >>> arayPos + values[value] )& 1L ) ==1){
                                Board.atackSquares |= (1L << arayPos + values[value]);
                                Board.captureSquares |= (1L << arayPos+values[value]);
                            }
                        }
                        
                    }
                }
            }
        }
    }

    public static void BKNMovement(int arayPos){
        
        int[] values = {15, 17, -15, -17, 6, -10, 10, -6 }; 
        long boundarieOne =0;
        long boundarieTwo = 0 ;
        long boundarieThree = 0 ;

        for (int i = 0; i < values.length ; i++){
            //top two
            if (i ==0){//15
                boundarieOne = Board.EighthRow;
                boundarieTwo = Board.SeventhRow;
                boundarieThree = Board.FirstColumn ;
            }if(i == 1){//17
                boundarieOne = Board.EighthRow;
                boundarieTwo = Board.SeventhRow;
                boundarieThree = Board.EighthColumn;
            }
            //down two
            if (i ==2){//-15
                boundarieOne = Board.FirstRow;
                boundarieTwo = Board.SecondRow ;
                boundarieThree =Board.EighthColumn ;
            }if(i == 3){//-17
                boundarieOne = Board.FirstRow;
                boundarieTwo = Board.SecondRow ;
                boundarieThree = Board.FirstColumn;
            }
            //two right
            if (i ==4){//6
                boundarieOne = Board.FirstColumn;
                boundarieTwo = Board.SecondColumn ;
                boundarieThree =Board.EighthRow ;
            }if(i == 5){//-10
                boundarieOne = Board.FirstColumn;
                boundarieTwo = Board.SecondColumn ;
                boundarieThree = Board.FirstRow;
            }
            //two left
            if (i ==6){//10
                boundarieOne = Board.EighthColumn;
                boundarieTwo = Board.SeventhColumn;
                boundarieThree = Board.EighthRow ;
            }if(i == 7){//-6
                boundarieOne = Board.EighthColumn;
                boundarieTwo = Board.SeventhColumn;
                boundarieThree = Board.FirstRow;
            }
            BKNM(arayPos, i, boundarieOne, boundarieTwo , boundarieThree, values);
        }  
    }

    public static void BKM(int arayPos , int value , long bOne , long bTwo , int[] values){
        if (((bOne >>> arayPos) & 1L) == 0){
            if (((bTwo >>> arayPos) & 1L) == 0){
                if (!checkIfSquareisUsed(arayPos +values[value])) {
                    if (!blackIsInCheck){
                        if (((Board.whiteAtackSquares >>> arayPos + values[value] )& 1L)==0) {
                            Board.atackSquares |= (1L << arayPos + values[value]); 
                        }
                    }else {
                        if (((Board.whiteAtackSquares >>> arayPos + values[value] )& 1L)==0) {
                           Board.atackSquares |= (1L << arayPos + values[value]);  
                        }
                    }
                    
                }else{
                    if (checkIfSquareisUsedByEnemeyBlack(arayPos+values[value])){
                        if (!blackIsInCheck){
                            if (((Board.whiteAtackSquares >>> arayPos + values[value] )& 1L)==0) {
                                Board.atackSquares |= (1L << arayPos + values[value]);
                                Board.captureSquares |= (1L << arayPos+values[value]);
                            }
                        }else {
                            
                            if (((Board.checkSquares >>> arayPos + values[value] )& 1L)==0 &&((Board.whiteAtackSquares >>> arayPos + values[value] )& 1L)==0) {

                                Board.atackSquares |= (1L << arayPos + values[value]);
                                Board.captureSquares |= (1L << arayPos+values[value]);

                            }
                        }
                        
                    }
                }
            }
        }
    }

    public static void BKMovement(int arayPos){

        WhiteAtackSquares();
        int[] values = {1, -1 , 7 ,-7, 8 , -8 , 9 , -9};
        long boundarieOne =0;
        long boundarieTwo =0;

        for (int i = 0; i < values.length ; i++){
            if (i ==0){
                boundarieOne = Board.EighthColumn; 
                
            }if(i == 1){
                boundarieOne =Board.FirstColumn;
                
            }if (i ==2){
                boundarieOne = Board.EighthRow;
                boundarieTwo = Board.FirstColumn;
            }if(i == 3){
                boundarieOne = Board.FirstRow;
                boundarieTwo = Board.EighthColumn;
            }
            if (i ==4){
                boundarieOne = Board.EighthRow; 
                boundarieTwo = 0;

            }if(i == 5){
                boundarieOne =Board.FirstRow;
                boundarieTwo = 0;
            }if (i ==6){
                boundarieOne = Board.EighthRow;
                boundarieTwo = Board.EighthColumn;
            }if(i == 7){
                boundarieOne = Board.FirstRow;
                boundarieTwo =Board.FirstColumn;
            }
            BKM(arayPos, i, boundarieOne,boundarieTwo,  values);
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
        
        return ((Board.blackKing >>> i) & 1L) != 0;
        
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
    
        return((Board.whiteKing >>> i) & 1L) != 0 ;

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
        
        return ((Board.blackKing >>> i) & 1L) != 0;
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
        if ((Board.whiteRoocks & 1L ) != 0 && !checkIfSquareisUsed(1) && !checkIfSquareisUsed(2) && !WhiteRightRookHasBeenMoved) {
            Board.atackSquares |= (1L << 1);
        }
        if (((Board.whiteRoocks >>> 7) & 1L) != 0 && !checkIfSquareisUsed(6) && !checkIfSquareisUsed(5) && !checkIfSquareisUsed(4) && !WhiteLeftRookHasBeenMoved) {
            Board.atackSquares |= (1L << 5);
        }
    }
     
    public static void checkBlackCastling(){
       
        if (((Board.blackRoocks >>> 56) & 1L) != 0 && !checkIfSquareisUsed(57) && !checkIfSquareisUsed(58)&& !BlackLeftRookHasBeenMoved) {
            Board.atackSquares |= (1L << 57);
        }
        if (((Board.blackRoocks >>> 63) & 1L) != 0 && !checkIfSquareisUsed(62) && !checkIfSquareisUsed(61) && !checkIfSquareisUsed(60) && !BlackRightRookHasBeenMoved) {
            Board.atackSquares |= (1L << 61);
        }
    }

    public static void WCBM(int arayPos , int value , long bOne , long bTwo , int[] values , int piece ,long checkSquares , boolean piecePased ){
        if (((bOne >>> arayPos) & 1L) == 0) {
            //top right  
            if (((bTwo >>> arayPos) & 1L) == 0) {
                for (int i = 1; i < 10; i++) {
                    if (checkIfSquareisUsedByEnemeyBlack(arayPos + i*values[value])){
                        piecePased = true;
                        return;
                        
                    }
                    checkSquares |= (1L << arayPos + i*values[value]);
                    
                    
                    if (  checkIfSquareisUsedByEnemey(arayPos + i * values[value])) {
                        if (returnPiece(arayPos + i*values[value])== 8 ||returnPiece(arayPos + i*values[value]) ==10){
                            Board.checkSquares |= checkSquares;
                            if (!piecePased){
                                whiteIsInCheck = true;
                            }
                        }
                        break;
                    }
                    if (((Board.boundaries >>> arayPos+i*values[value]) & 1L) != 0 ){
                        break;
                    }
                }
            }
        }
    }
    
    public static void WCRM(int arayPos , int value , long bOne , int[] values , int piece ,long checkSquares , boolean piecePased ){
        if (((bOne >>> arayPos) & 1L) == 0) {
            for (int i = 1; i < 9; i++) {
                if (checkIfSquareisUsedByEnemeyBlack(arayPos + i * values[value])) {
                    piecePased = true;
                    return;
                     
                }
                checkSquares |= (1L << arayPos + i*values[value]);

                
                if (  checkIfSquareisUsedByEnemey(arayPos + i * values[value])) {
                    if (returnPiece(arayPos + i*values[value])== 9 ||returnPiece(arayPos + i*values[value]) ==10){
                        Board.checkSquares |= checkSquares;
                        if (!piecePased){
                            whiteIsInCheck = true;
                        }
                    }
                    break;
                }

                if (((bOne >>> arayPos+i*values[value]) & 1L) != 0) {break;}

            }
        }
    }

    public static void WCKNM(int arayPos , int value , long bOne , long bTwo ,long bThree, int[] values){
        if (((bOne >>> arayPos) & 1L) == 0&&((bTwo >>> arayPos) & 1L) == 0){
            if (((bThree >>> arayPos) & 1L) == 0){
                if (checkIfSquareisUsed(arayPos + values[value])){
                    if (returnPiece(arayPos + values[value]) == 11){
                        Board.checkSquares |= (1L << arayPos + values[value]);
                        Board.checkSquares |= (1L << arayPos );
                        whiteIsInCheck = true;
                    }
                }
            }
        }
    }

    public static void checkWhiteCheck(int arayPos){
        int piece =0 ;
        
        boolean piecePased = false;
        long checkSquares = 0;
        whiteIsInCheck = false;
        int[] Bvalues = {7, -9 , -7 ,9};
        int[] Rvalues = {8, -8 , 1 ,-1};
        int[] KNvalues = {15, 17, -15, -17, 6, -10, 10, -6 }; 
        long boundarieOne =0;
        long boundarieTwo = 0 ;
        long boundarieThree = 0 ;

        for (int i = 0; i < Bvalues.length ; i++){
            if (i ==0){
                boundarieOne = Board.FirstColumn;
                boundarieTwo = Board.EighthRow;
            }if(i == 1){
                boundarieOne = Board.FirstColumn;
                boundarieTwo = Board.FirstRow;
            }if (i ==2){
                boundarieOne = Board.EighthColumn;
                boundarieTwo = Board.FirstRow;
            }if(i == 3){
                boundarieOne = Board.EighthRow;
                boundarieTwo = Board.EighthColumn;
            }
            WCBM(arayPos , i,boundarieOne,boundarieTwo, Bvalues ,piece , checkSquares, piecePased);

            checkSquares = 0;
            piecePased = false;
        }  

        for (int i = 0; i < Rvalues.length ; i++){
            if (i ==0){
                boundarieOne = Board.EighthRow; 
                
            }if(i == 1){
                boundarieOne =Board.FirstRow;
                
            }if (i ==2){
                boundarieOne = Board.EighthColumn;
                
            }if(i == 3){
                boundarieOne = Board.FirstColumn;
                
            }
            WCRM(arayPos , i,boundarieOne, Rvalues ,piece , checkSquares, piecePased);
            checkSquares = 0;
            piecePased = false;
        }

        for (int i = 0; i < KNvalues.length ; i++){
            //top two
            if (i ==0){//15
                boundarieOne = Board.EighthRow;
                boundarieTwo = Board.SeventhRow;
                boundarieThree = Board.FirstColumn ;
            }if(i == 1){//17
                boundarieOne = Board.EighthRow;
                boundarieTwo = Board.SeventhRow;
                boundarieThree = Board.EighthColumn;
            }
            //down two
            if (i ==2){//-15
                boundarieOne = Board.FirstRow;
                boundarieTwo = Board.SecondRow ;
                boundarieThree =Board.EighthColumn ;
            }if(i == 3){//-17
                boundarieOne = Board.FirstRow;
                boundarieTwo = Board.SecondRow ;
                boundarieThree = Board.FirstColumn;
            }
            //two right
            if (i ==4){//6
                boundarieOne = Board.FirstColumn;
                boundarieTwo = Board.SecondColumn ;
                boundarieThree =Board.EighthRow ;
            }if(i == 5){//-10
                boundarieOne = Board.FirstColumn;
                boundarieTwo = Board.SecondColumn ;
                boundarieThree = Board.FirstRow;
            }
            //two left
            if (i ==6){//10
                boundarieOne = Board.EighthColumn;
                boundarieTwo = Board.SeventhColumn;
                boundarieThree = Board.EighthRow ;
            }if(i == 7){//-6
                boundarieOne = Board.EighthColumn;
                boundarieTwo = Board.SeventhColumn;
                boundarieThree = Board.FirstRow;
            }

            WCKNM(arayPos, i, boundarieOne, boundarieTwo , boundarieThree, KNvalues);
        }  
    
        if (((Board.FirstColumn >>> arayPos) & 1L) == 0) {
            if (checkIfSquareisUsed(arayPos + 7) && returnPiece(arayPos + 7) == 7) {
                Board.checkSquares |= (1L << arayPos +7 );
                whiteIsInCheck = true;
            }
        }

        if (((Board.EighthColumn >>> arayPos) & 1L) == 0) {
            if (checkIfSquareisUsed(arayPos + 9) && returnPiece(arayPos + 9) == 7) {
                Board.checkSquares |= (1L << arayPos +9);
                whiteIsInCheck = true;
            }
        }
        
    }
    
    public static void BCBM(int arayPos , int value , long bOne , long bTwo , int[] values , int piece ,long checkSquares , boolean piecePased ){
        if (((bOne >>> arayPos) & 1L) == 0) {
            //top right  
            if (((bTwo >>> arayPos) & 1L) == 0) {
                for (int i = 1; i < 10; i++) {
                    if (checkIfSquareisUsedByEnemey(arayPos + i*values[value])){
                        piecePased = true;
                        return;
                        
                    }
                    checkSquares |= (1L << arayPos + i*values[value]);
                    
                    
                    if (  checkIfSquareisUsedByEnemeyBlack(arayPos + i * values[value])) {
                        if (returnPiece(arayPos + i*values[value])== 2 ||returnPiece(arayPos + i*values[value]) ==4){
                            Board.checkSquares |= checkSquares;
                            if (!piecePased){
                                blackIsInCheck = true;
                            }
                        }
                        break;
                    }
                    if (((Board.boundaries >>> arayPos+i*values[value]) & 1L) != 0 ){
                        break;
                    }
                }
            }
        }
    }
    
    public static void BCRM(int arayPos , int value , long bOne , int[] values , int piece ,long checkSquares , boolean piecePased ){
        if (((bOne >>> arayPos) & 1L) == 0) {
            for (int i = 1; i < 9; i++) {
                if (checkIfSquareisUsedByEnemey(arayPos + i * values[value])) {
                    piecePased = true;
                    return;
                }
                checkSquares |= (1L << arayPos + i*values[value]);

                
                if (  checkIfSquareisUsedByEnemeyBlack(arayPos + i * values[value])) {
                    if (returnPiece(arayPos + i*values[value])== 3 ||returnPiece(arayPos + i*values[value]) ==4){
                        Board.checkSquares |= checkSquares;
                        if (!piecePased){
                            blackIsInCheck = true;
                        }
                    }
                    break;
                }

                if (((bOne >>> arayPos+i*values[value]) & 1L) != 0) {break;}

            }
        }
    }

    public static void BCKNM(int arayPos , int value , long bOne , long bTwo ,long bThree, int[] values){
        if (((bOne >>> arayPos) & 1L) == 0&&((bTwo >>> arayPos) & 1L) == 0){
            if (((bThree >>> arayPos) & 1L) == 0){
                if (checkIfSquareisUsed(arayPos + values[value])){
                    if (returnPiece(arayPos + values[value]) == 5){
                        Board.checkSquares |= (1L << arayPos + values[value]);
                        Board.checkSquares |= (1L << arayPos );
                        blackIsInCheck = true;
                    }
                }
            }
        }
    }

    public static void checkBlackCheck(int arayPos){
        int piece =0 ;
        
        boolean piecePased = false;
        long checkSquares = 0;
        blackIsInCheck = false;
        int[] Bvalues = {7, -9 , -7 ,9};
        int[] Rvalues = {8, -8 , 1 ,-1};
        int[] KNvalues = {15, 17, -15, -17, 6, -10, 10, -6 }; 
        long boundarieOne =0;
        long boundarieTwo = 0 ;
        long boundarieThree = 0 ;

        for (int i = 0; i < Bvalues.length ; i++){
            if (i ==0){
                boundarieOne = Board.FirstColumn;
                boundarieTwo = Board.EighthRow;
            }if(i == 1){
                boundarieOne = Board.FirstColumn;
                boundarieTwo = Board.FirstRow;
            }if (i ==2){
                boundarieOne = Board.EighthColumn;
                boundarieTwo = Board.FirstRow;
            }if(i == 3){
                boundarieOne = Board.EighthRow;
                boundarieTwo = Board.EighthColumn;
            }
            BCBM(arayPos , i,boundarieOne,boundarieTwo, Bvalues ,piece , checkSquares, piecePased);

            checkSquares = 0;
            piecePased = false;
        }  

        for (int i = 0; i < Rvalues.length ; i++){
            if (i ==0){
                boundarieOne = Board.EighthRow; 
                
            }if(i == 1){
                boundarieOne =Board.FirstRow;
                
            }if (i ==2){
                boundarieOne = Board.EighthColumn;
                
            }if(i == 3){
                boundarieOne = Board.FirstColumn;
                
            }
            BCRM(arayPos , i,boundarieOne, Rvalues ,piece , checkSquares, piecePased);
            checkSquares = 0;
            piecePased = false;
        }

        for (int i = 0; i < KNvalues.length ; i++){
            //top two
            if (i ==0){//15
                boundarieOne = Board.EighthRow;
                boundarieTwo = Board.SeventhRow;
                boundarieThree = Board.FirstColumn ;
            }if(i == 1){//17
                boundarieOne = Board.EighthRow;
                boundarieTwo = Board.SeventhRow;
                boundarieThree = Board.EighthColumn;
            }
            //down two
            if (i ==2){//-15
                boundarieOne = Board.FirstRow;
                boundarieTwo = Board.SecondRow ;
                boundarieThree =Board.EighthColumn ;
            }if(i == 3){//-17
                boundarieOne = Board.FirstRow;
                boundarieTwo = Board.SecondRow ;
                boundarieThree = Board.FirstColumn;
            }
            //two right
            if (i ==4){//6
                boundarieOne = Board.FirstColumn;
                boundarieTwo = Board.SecondColumn ;
                boundarieThree =Board.EighthRow ;
            }if(i == 5){//-10
                boundarieOne = Board.FirstColumn;
                boundarieTwo = Board.SecondColumn ;
                boundarieThree = Board.FirstRow;
            }
            //two left
            if (i ==6){//10
                boundarieOne = Board.EighthColumn;
                boundarieTwo = Board.SeventhColumn;
                boundarieThree = Board.EighthRow ;
            }if(i == 7){//-6
                boundarieOne = Board.EighthColumn;
                boundarieTwo = Board.SeventhColumn;
                boundarieThree = Board.FirstRow;
            }

            BCKNM(arayPos, i, boundarieOne, boundarieTwo , boundarieThree,KNvalues);
        }  
        
        if (((Board.FirstColumn >>> arayPos) & 1L) == 0) {
            if (checkIfSquareisUsed(arayPos-7) && returnPiece(arayPos-7) == 1){
                Board.checkSquares |= (1L << arayPos -7 );
                blackIsInCheck = true;
            }
        }

        if (((Board.EighthColumn >>> arayPos) & 1L) == 0) {
            if (checkIfSquareisUsed(arayPos-9) && returnPiece(arayPos-9) == 1){
                Board.checkSquares |= (1L << arayPos -9 );
                blackIsInCheck = true;
            }
        }
    }

    public static boolean checkWhiteMateCheck(){
        updateBlackBoard();

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
                        return false;
                    }

                }

            }
        }
        return true;
    }

    public static boolean checkBlackMateCheck(){
        updateWhiteBoard();
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
                            return false;
                        }
                    }

                }
            }
        }
        return true;
    }

    public static void WhiteAtackSquares(){
        Board.whiteAtackSquares= 0;
        updateWhiteBoard();
        boolean piecePased = false;
        long checkSquares = 0;
        int[] Bvalues = {7, -9 , -7 ,9};
        int[] Rvalues = {8, -8 , 1 ,-1};
        int[] KNvalues = {15, 17, -15, -17, 6, -10, 10, -6 }; 
        int[] Kvalues = {1, -1 , 7 ,-7, 8 , -8 , 9 , -9};
        long boundarieOne =0;
        long boundarieTwo = 0 ;
        long boundarieThree = 0 ;

        for (int i = 0 ; i<64 ; i++){

            int piece ;
            Board.atackSquares =0;
           
            if (((Board.whiteBoard >>> i)& 1L) ==1){
                piece = returnPiece(i);
                if (piece == 1){
                    if (((Board.EighthColumn >>> i) & 1L) == 0) {
                        Board.whiteAtackSquares |= (1L << i + 9);
                    }
                    if (((Board.FirstColumn >>> i) & 1L) == 0) {
                        Board.whiteAtackSquares|= (1L << i + 7);
                    }
                }if (piece == 2){
                    for (int r = 0; r < Bvalues.length ; r++){
                        if (r ==0){
                            boundarieOne = Board.FirstColumn;
                            boundarieTwo = Board.EighthRow;
                        }if(r == 1){
                            boundarieOne = Board.FirstColumn;
                            boundarieTwo = Board.FirstRow;
                        }if (r ==2){
                            boundarieOne = Board.EighthColumn;
                            boundarieTwo = Board.FirstRow;
                        }if(r == 3){
                            boundarieOne = Board.EighthRow;
                            boundarieTwo = Board.EighthColumn;
                        }
                        EBM(i , r,boundarieOne,boundarieTwo, Bvalues ,piece , checkSquares, piecePased);

                        checkSquares = 0;
                        piecePased = false;
                    }  
                    Board.whiteAtackSquares |= Board.allAtackSquares;
                }if (piece == 3){
                    for (int r = 0; r < Rvalues.length ; r++){
                        if (r ==0){
                            boundarieOne = Board.EighthRow; 
                            
                        }if(r == 1){
                            boundarieOne =Board.FirstRow;
                            
                        }if (r ==2){
                            boundarieOne = Board.EighthColumn;
                            
                        }if(r == 3){
                            boundarieOne = Board.FirstColumn;
                            
                        }
                        ERM(i , r,boundarieOne, Rvalues ,piece , checkSquares, piecePased);
                        checkSquares = 0;
                        piecePased = false;
                    }
                    Board.whiteAtackSquares |= Board.allAtackSquares;
                }if (piece == 5){
                    for (int r = 0; r < KNvalues.length ; r++){
                        //top two
                        if (r ==0){//15
                            boundarieOne = Board.EighthRow;
                            boundarieTwo = Board.SeventhRow;
                            boundarieThree = Board.FirstColumn ;
                        }if(r == 1){//17
                            boundarieOne = Board.EighthRow;
                            boundarieTwo = Board.SeventhRow;
                            boundarieThree = Board.EighthColumn;
                        }
                        //down two
                        if (r ==2){//-15
                            boundarieOne = Board.FirstRow;
                            boundarieTwo = Board.SecondRow ;
                            boundarieThree =Board.EighthColumn ;
                        }if(r == 3){//-17
                            boundarieOne = Board.FirstRow;
                            boundarieTwo = Board.SecondRow ;
                            boundarieThree = Board.FirstColumn;
                        }
                        //two right
                        if (r ==4){//6
                            boundarieOne = Board.FirstColumn;
                            boundarieTwo = Board.SecondColumn ;
                            boundarieThree =Board.EighthRow ;
                        }if(r == 5){//-10
                            boundarieOne = Board.FirstColumn;
                            boundarieTwo = Board.SecondColumn ;
                            boundarieThree = Board.FirstRow;
                        }
                        //two left
                        if (r ==6){//10
                            boundarieOne = Board.EighthColumn;
                            boundarieTwo = Board.SeventhColumn;
                            boundarieThree = Board.EighthRow ;
                        }if(r == 7){//-6
                            boundarieOne = Board.EighthColumn;
                            boundarieTwo = Board.SeventhColumn;
                            boundarieThree = Board.FirstRow;
                        }

                        EKNM(i, r, boundarieOne, boundarieTwo , boundarieThree, KNvalues);
                    } 
        
                    Board.whiteAtackSquares |= Board.allAtackSquares;
                }if (piece == 4){
                    for (int r = 0; r < Rvalues.length ; r++){
                        if (r ==0){
                            boundarieOne = Board.EighthRow; 
                            
                        }if(r == 1){
                            boundarieOne =Board.FirstRow;
                            
                        }if (r ==2){
                            boundarieOne = Board.EighthColumn;
                            
                        }if(r == 3){
                            boundarieOne = Board.FirstColumn;
                            
                        }
                        ERM(i , r,boundarieOne, Rvalues ,piece , checkSquares, piecePased);
                        checkSquares = 0;
                        piecePased = false;
                    }
                    for (int r = 0; r < Bvalues.length ; r++){
                        if (r ==0){
                            boundarieOne = Board.FirstColumn;
                            boundarieTwo = Board.EighthRow;
                        }if(r == 1){
                            boundarieOne = Board.FirstColumn;
                            boundarieTwo = Board.FirstRow;
                        }if (r ==2){
                            boundarieOne = Board.EighthColumn;
                            boundarieTwo = Board.FirstRow;
                        }if(r == 3){
                            boundarieOne = Board.EighthRow;
                            boundarieTwo = Board.EighthColumn;
                        }
                        EBM(i , r,boundarieOne,boundarieTwo, Bvalues ,piece , checkSquares, piecePased);

                        checkSquares = 0;
                        piecePased = false;
                    }  
                    Board.whiteAtackSquares |= Board.allAtackSquares;
                }if (piece == 6){
                    for (int r = 0; r < Kvalues.length ; r++){
                        if (r ==0){
                            boundarieOne = Board.EighthColumn; 
                            
                        }if(r == 1){
                            boundarieOne =Board.FirstColumn;
                            
                        }if (r ==2){
                            boundarieOne = Board.EighthRow;
                            boundarieTwo = Board.FirstColumn;
                        }if(r == 3){
                            boundarieOne = Board.FirstRow;
                            boundarieTwo = Board.EighthColumn;
                        }
                        if (r ==4){
                            boundarieOne = Board.EighthRow; 
                            boundarieTwo = 0;

                        }if(r == 5){
                            boundarieOne =Board.FirstRow;
                            boundarieTwo = 0;
                        }if (r ==6){
                            boundarieOne = Board.EighthRow;
                            boundarieTwo = Board.EighthColumn;
                        }if(r == 7){
                            boundarieOne = Board.FirstRow;
                            boundarieTwo =Board.FirstColumn;
                        }
                        EKM(i, r, boundarieOne,boundarieTwo,  Kvalues);
                    }
                    Board.whiteAtackSquares |= Board.allAtackSquares;
                }
            }
        }
        Board.allAtackSquares =0;
        Board.atackSquares =0;
        Board.captureSquares = 0;
    }

    public static void BlackAtackSquares(){
        Board.BlackAtackSquares = 0;
        
        boolean piecePased = false;
        long checkSquares = 0;
        int[] Bvalues = {7, -9 , -7 ,9};
        int[] Rvalues = {8, -8 , 1 ,-1};
        int[] KNvalues = {15, 17, -15, -17, 6, -10, 10, -6 }; 
        int[] Kvalues = {1, -1 , 7 ,-7, 8 , -8 , 9 , -9};
        long boundarieOne =0;
        long boundarieTwo = 0 ;
        long boundarieThree = 0 ;

        for (int i = 0 ; i<64 ; i++){

            int piece ;
            Board.atackSquares =0;
           
            if (((Board.blackBoard >>> i)& 1L) ==1){
                piece = returnPiece(i);
                if (piece == 7){
                    if (((Board.EighthColumn >>> i) & 1L) == 0) {
                        Board.BlackAtackSquares |= (1L << i - 7);
                    }
                    if (((Board.FirstColumn >>> i) & 1L) == 0) {
                        Board.BlackAtackSquares |= (1L << i - 9);
                    }
                }if (piece == 8){
                    for (int r = 0; r < Bvalues.length ; r++){
                        if (r ==0){
                            boundarieOne = Board.FirstColumn;
                            boundarieTwo = Board.EighthRow;
                        }if(r == 1){
                            boundarieOne = Board.FirstColumn;
                            boundarieTwo = Board.FirstRow;
                        }if (r ==2){
                            boundarieOne = Board.EighthColumn;
                            boundarieTwo = Board.FirstRow;
                        }if(r == 3){
                            boundarieOne = Board.EighthRow;
                            boundarieTwo = Board.EighthColumn;
                        }
                        EBM(i , r,boundarieOne,boundarieTwo, Bvalues ,piece , checkSquares, piecePased);

                        checkSquares = 0;
                        piecePased = false;
                    }  
                    Board.BlackAtackSquares |= Board.allAtackSquares;
                }if (piece == 9){
                    for (int r = 0; r < Rvalues.length ; r++){
                        if (r ==0){
                            boundarieOne = Board.EighthRow; 
                            
                        }if(r == 1){
                            boundarieOne =Board.FirstRow;
                            
                        }if (r ==2){
                            boundarieOne = Board.EighthColumn;
                            
                        }if(r == 3){
                            boundarieOne = Board.FirstColumn;
                            
                        }
                        ERM(i , r,boundarieOne, Rvalues ,piece , checkSquares, piecePased);
                        checkSquares = 0;
                        piecePased = false;
                    }
                    Board.BlackAtackSquares |= Board.allAtackSquares;
                }if (piece == 11){
                    for (int r = 0; r < KNvalues.length ; r++){
                        //top two
                        if (r ==0){//15
                            boundarieOne = Board.EighthRow;
                            boundarieTwo = Board.SeventhRow;
                            boundarieThree = Board.FirstColumn ;
                        }if(r == 1){//17
                            boundarieOne = Board.EighthRow;
                            boundarieTwo = Board.SeventhRow;
                            boundarieThree = Board.EighthColumn;
                        }
                        //down two
                        if (r ==2){//-15
                            boundarieOne = Board.FirstRow;
                            boundarieTwo = Board.SecondRow ;
                            boundarieThree =Board.EighthColumn ;
                        }if(r == 3){//-17
                            boundarieOne = Board.FirstRow;
                            boundarieTwo = Board.SecondRow ;
                            boundarieThree = Board.FirstColumn;
                        }
                        //two right
                        if (r ==4){//6
                            boundarieOne = Board.FirstColumn;
                            boundarieTwo = Board.SecondColumn ;
                            boundarieThree =Board.EighthRow ;
                        }if(r == 5){//-10
                            boundarieOne = Board.FirstColumn;
                            boundarieTwo = Board.SecondColumn ;
                            boundarieThree = Board.FirstRow;
                        }
                        //two left
                        if (r ==6){//10
                            boundarieOne = Board.EighthColumn;
                            boundarieTwo = Board.SeventhColumn;
                            boundarieThree = Board.EighthRow ;
                        }if(r == 7){//-6
                            boundarieOne = Board.EighthColumn;
                            boundarieTwo = Board.SeventhColumn;
                            boundarieThree = Board.FirstRow;
                        }

                        EKNM(i, r, boundarieOne, boundarieTwo , boundarieThree, KNvalues);
                    } 
        
                    Board.BlackAtackSquares |= Board.allAtackSquares;
                }if (piece == 10){
                    for (int r = 0; r < Rvalues.length ; r++){
                        if (r ==0){
                            boundarieOne = Board.EighthRow; 
                            
                        }if(r == 1){
                            boundarieOne =Board.FirstRow;
                            
                        }if (r ==2){
                            boundarieOne = Board.EighthColumn;
                            
                        }if(r == 3){
                            boundarieOne = Board.FirstColumn;
                            
                        }
                        ERM(i , r,boundarieOne, Rvalues ,piece , checkSquares, piecePased);
                        checkSquares = 0;
                        piecePased = false;
                    }
                    for (int r = 0; r < Bvalues.length ; r++){
                        if (r ==0){
                            boundarieOne = Board.FirstColumn;
                            boundarieTwo = Board.EighthRow;
                        }if(r == 1){
                            boundarieOne = Board.FirstColumn;
                            boundarieTwo = Board.FirstRow;
                        }if (r ==2){
                            boundarieOne = Board.EighthColumn;
                            boundarieTwo = Board.FirstRow;
                        }if(r == 3){
                            boundarieOne = Board.EighthRow;
                            boundarieTwo = Board.EighthColumn;
                        }
                        EBM(i , r,boundarieOne,boundarieTwo, Bvalues ,piece , checkSquares, piecePased);

                        checkSquares = 0;
                        piecePased = false;
                    }  
                    Board.BlackAtackSquares |= Board.atackSquares;
                }if (piece == 12){
                    for (int r = 0; r < Kvalues.length ; r++){
                        if (r ==0){
                            boundarieOne = Board.EighthColumn; 
                            
                        }if(r == 1){
                            boundarieOne =Board.FirstColumn;
                            
                        }if (r ==2){
                            boundarieOne = Board.EighthRow;
                            boundarieTwo = Board.FirstColumn;
                        }if(r == 3){
                            boundarieOne = Board.FirstRow;
                            boundarieTwo = Board.EighthColumn;
                        }
                        if (r ==4){
                            boundarieOne = Board.EighthRow; 
                            boundarieTwo = 0;

                        }if(r == 5){
                            boundarieOne =Board.FirstRow;
                            boundarieTwo = 0;
                        }if (r ==6){
                            boundarieOne = Board.EighthRow;
                            boundarieTwo = Board.EighthColumn;
                        }if(r == 7){
                            boundarieOne = Board.FirstRow;
                            boundarieTwo =Board.FirstColumn;
                        }
                        EKM(i, r, boundarieOne,boundarieTwo,  Kvalues);
                    }
                    Board.BlackAtackSquares |= Board.allAtackSquares;
                }
            }
        }
        Board.allAtackSquares =0;
        Board.atackSquares =0;
        Board.captureSquares = 0;
    }

    public static void EBM(int arayPos , int value , long bOne , long bTwo , int[] values , int piece ,long checkSquares , boolean piecePased ){
        if (((bOne >>> arayPos) & 1L) == 0) {
            //top right  
            if (((bTwo >>> arayPos) & 1L) == 0) {
                for (int i = 1; i < 10; i++) {
                    Board.allAtackSquares |= (1L << arayPos + i*values[value]);
                    if (  checkIfSquareisUsed(arayPos + i * values[value])) {
                        break;
                    }
                    if (((Board.boundaries >>> arayPos+i*values[value]) & 1L) != 0 ){
                        break;
                    }
                }
            }
        }
    }
    
    public static void ERM(int arayPos , int value , long bOne , int[] values , int piece ,long checkSquares , boolean piecePased ){
        if (((bOne >>> arayPos) & 1L) == 0) {
            for (int i = 1; i < 9; i++) {
                Board.allAtackSquares |= (1L << arayPos + i*values[value]);
                if (  checkIfSquareisUsed(arayPos + i * values[value])) {
                    break;
                }
                if (((bOne >>> arayPos+i*values[value]) & 1L) != 0) {break;}
            }
        }
    }

    public static void EKNM(int arayPos , int value , long bOne , long bTwo ,long bThree, int[] values){
        if (((bOne >>> arayPos) & 1L) == 0&&((bTwo >>> arayPos) & 1L) == 0){
            if (((bThree >>> arayPos) & 1L) == 0){
                Board.allAtackSquares |= (1L << arayPos + values[value]);
            }
        }
    }

    public static void EKM(int arayPos , int value , long bOne , long bTwo , int[] values){
        if (((bOne >>> arayPos) & 1L) == 0){
            if (((bTwo >>> arayPos) & 1L) == 0){
                Board.allAtackSquares |= (1L << arayPos+values[value]);
            }
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

    public static void updateWhiteBoard(){
        Board.whiteBoard = 0;
        Board.whiteBoard = Board.whiteBishops | Board.whiteKing | Board.whiteKnights | Board.whitePawns | Board.whiteQueen | Board.whiteRoocks;
        
    }

    public static void updateBlackBoard(){
        Board.blackBoard = 0;
        Board.blackBoard = Board.blackBishops | Board.blackKing | Board.blackKnights | Board.blackPawns | Board.blackQueen | Board.blackRoocks;
    }

    public static void blackPromotion(int arayPos) {
        Board.blackPawns ^= (1L << arayPos);

        switch (promotionPiece) {
            case 1 -> Board.blackKnights ^= (1L << arayPos);
            case 2 -> Board.blackRoocks ^= (1L << arayPos);
            case 3 -> Board.blackQueen ^= (1L << arayPos);
            default -> Board.blackBishops ^= (1L << arayPos);
        }
        blackPromotionUI = false;

        promotionPiece = 0;
    }

    public static void whitePromotion(int arayPos) {
        Board.whitePawns ^= (1L << arayPos);

        switch (promotionPiece) {
            case 1 -> Board.whiteKnights ^= (1L << arayPos);
            case 2 -> Board.whiteRoocks ^= (1L << arayPos);
            case 3 -> Board.whiteQueen ^= (1L << arayPos);
            default -> Board.whiteBishops ^= (1L << arayPos);
        }

        promotionPiece = 0;
        whitePromotionUI = false;
    }

    public record State(
            int pieceMoved,
            int pieceMovedPos,
            boolean WhiteKingHasBeenMoved,
            boolean blackKingHasBeenMoved,
            boolean WhiteKingHasBeenChecked,
            boolean blackKingHasBeenChecked,
            int whiteKingPos,
            int blackKingPos,
            boolean whiteIsInCheck,
            boolean blackIsInCheck,
            boolean turn,
            boolean gameFinished,
            boolean whitePromotionUI,
            boolean blackPromotionUI,
            int promotionPiece,
            int promotionsquare,
            boolean WhiteLeftRookHasBeenMoved,
            boolean WhiteRightRookHasBeenMoved,
            boolean BlackLeftRookHasBeenMoved,
            boolean BlackRightRookHasBeenMoved,
            boolean BlackAi,
            int count
    ) {
    }

    private static State savedState;

    public static State captureState() {
        return new State(
                pieceMoved,
                pieceMovedPos,
                WhiteKingHasBeenMoved,
                blackKingHasBeenMoved,
                WhiteKingHasBeenChecked,
                blackKingHasBeenChecked,
                whiteKingPos,
                blackKingPos,
                whiteIsInCheck,
                blackIsInCheck,
                turn,
                gameFinished,
                whitePromotionUI,
                blackPromotionUI,
                promotionPiece,
                promotionsquare,
                WhiteLeftRookHasBeenMoved,
                WhiteRightRookHasBeenMoved,
                BlackLeftRookHasBeenMoved,
                BlackRightRookHasBeenMoved,
                BlackAi,
                count
        );
    }

    public static void saveState() {
        savedState = captureState();
    }

    public static void resetState(State state) {
        if (state == null) {
            return;
        }

        pieceMoved = state.pieceMoved();
        pieceMovedPos = state.pieceMovedPos();

        WhiteKingHasBeenMoved = state.WhiteKingHasBeenMoved();
        blackKingHasBeenMoved = state.blackKingHasBeenMoved();

        WhiteKingHasBeenChecked = state.WhiteKingHasBeenChecked();
        blackKingHasBeenChecked = state.blackKingHasBeenChecked();

        whiteKingPos = state.whiteKingPos();
        blackKingPos = state.blackKingPos();

        whiteIsInCheck = state.whiteIsInCheck();
        blackIsInCheck = state.blackIsInCheck();

        turn = state.turn();
        gameFinished = state.gameFinished();

        whitePromotionUI = state.whitePromotionUI();
        blackPromotionUI = state.blackPromotionUI();

        promotionPiece = state.promotionPiece();
        promotionsquare = state.promotionsquare();

        WhiteLeftRookHasBeenMoved = state.WhiteLeftRookHasBeenMoved();
        WhiteRightRookHasBeenMoved = state.WhiteRightRookHasBeenMoved();
        BlackLeftRookHasBeenMoved = state.BlackLeftRookHasBeenMoved();
        BlackRightRookHasBeenMoved = state.BlackRightRookHasBeenMoved();

        BlackAi = state.BlackAi();
        count = state.count();
    }

    public static void resetState() {
        resetState(savedState);
    }
   
}