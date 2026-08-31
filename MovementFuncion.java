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
                                Board.atackSquares |= (1L << arayPos + 16);
                            }else{
                                if (((Board.checkSquares >>> arayPos+16)& 1L )==1){
                                    Board.atackSquares |= (1L << arayPos + 16);
                                }
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
                        Board.atackSquares |= (1L << arayPos + i * values[value]);
                    } else {
                        if (checkIfSquareisUsedByEnemey(arayPos + i * values[value])) {
                            Board.atackSquares |= (1L << arayPos + i * values[value]);
                            Board.captureSquares |= (1L << arayPos + i * values[value]);
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
                    Board.atackSquares |= (1L << arayPos + i * values[value]);
                } else {
                    if (checkIfSquareisUsedByEnemey(arayPos + i * values[value])) {
                        Board.atackSquares |= (1L << arayPos + i * values[value]);
                        Board.captureSquares |= (1L << arayPos + i * values[value]);
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
                if (!checkIfSquareisUsed(arayPos +values[value])) {
                    Board.atackSquares |= (1L << arayPos + values[value]);
                }else{
                    if (checkIfSquareisUsedByEnemey(arayPos+values[value])){
                        Board.atackSquares |= (1L << arayPos + values[value]);
                        Board.captureSquares |= (1L << arayPos+values[value]);
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
                    Board.atackSquares |= (1L << arayPos + values[value]); 
                }else{
                    if (checkIfSquareisUsedByEnemey(arayPos+values[value])){
                        Board.atackSquares |= (1L << arayPos + values[value]);
                        Board.captureSquares |= (1L << arayPos+values[value]);
                    }
                }
            }
        }
    }

    public static void WKMovement(int arayPos){

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
    
    public static void BBM(int arayPos , int value , long bOne , long bTwo , int[] values){
        if (((bOne >>> arayPos) & 1L) == 0) {

            //top right  
            if (((bTwo >>> arayPos) & 1L) == 0) {
                for (int i = 1; i < 10; i++) {

                    if (!checkIfSquareisUsed(arayPos + i * values[value] )) {
                        Board.atackSquares |= (1L << arayPos + i * values[value]);
                    } else {
                        if (checkIfSquareisUsedByEnemeyBlack(arayPos + i * values[value])) {
                            Board.atackSquares |= (1L << arayPos + i * values[value]);
                            Board.captureSquares |= (1L << arayPos + i * values[value]);
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
                    Board.atackSquares |= (1L << arayPos + i * values[value]);
                } else {
                    if (checkIfSquareisUsedByEnemeyBlack(arayPos + i * values[value])) {
                        Board.atackSquares |= (1L << arayPos + i * values[value]);
                        Board.captureSquares |= (1L << arayPos + i * values[value]);
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
                if (!checkIfSquareisUsed(arayPos +values[value])) {
                    Board.atackSquares |= (1L << arayPos + values[value]);
                }else{
                    if (checkIfSquareisUsedByEnemeyBlack(arayPos+values[value])){
                        Board.atackSquares |= (1L << arayPos + values[value]);
                        Board.captureSquares |= (1L << arayPos+values[value]);
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
                    Board.atackSquares |= (1L << arayPos + values[value]); 
                }else{
                    if (checkIfSquareisUsedByEnemeyBlack(arayPos+values[value])){
                        Board.atackSquares |= (1L << arayPos + values[value]);
                        Board.captureSquares |= (1L << arayPos+values[value]);
                    }
                }
            }
        }
    }

    public static void BKMovement(int arayPos){

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

        if (!WhiteKingHasBeenChecked && !WhiteKingHasBeenMoved){
            checkWhiteCastling();
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

    public static void WCBM(int arayPos , int value , long bOne , long bTwo , int[] values , int piece ,long checkSquares , boolean piecePased ){
        if (((bOne >>> arayPos) & 1L) == 0) {
            //top right  
            if (((bTwo >>> arayPos) & 1L) == 0) {
                for (int i = 1; i < 10; i++) {
                    
                    checkSquares |= (1L << arayPos + i*values[value]);
                    
                    if (checkIfSquareisUsedByEnemeyBlack(arayPos + i*values[value])){
                        piecePased = true;
                    }
                    if (  checkIfSquareisUsedByEnemey(arayPos + i * values[value])) {
                        if (returnPiece(arayPos + i*values[value])== 8 ||returnPiece(arayPos + i*values[value]) ==10){
                            Board.checkSquares += checkSquares;
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

                checkSquares |= (1L << arayPos + i*values[value]);


                if (checkIfSquareisUsedByEnemeyBlack(arayPos + i * values[value])) {
                    piecePased = true;
                }
                if (  checkIfSquareisUsedByEnemey(arayPos + i * values[value])) {
                    if (returnPiece(arayPos + i*values[value])== 9 ||returnPiece(arayPos + i*values[value]) ==10){
                        Board.checkSquares += checkSquares;
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

    public static void checkWhiteCheck(int arayPos){
        int piece =0 ;
        boolean piecePased = false;
        long checkSquares = 0;
        whiteIsInCheck = false;
        int[] Bvalues = {7, -9 , -7 ,9};
        int[] Rvalues = {8, -8 , 1 ,-1};
        long boundarieOne =0;
        long boundarieTwo = 0 ;

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
                boundarieTwo = 0;
            }if(i == 1){
                boundarieOne =Board.FirstRow;
                boundarieTwo = 0;
            }if (i ==2){
                boundarieOne = Board.EighthColumn;
                boundarieTwo = 0;
            }if(i == 3){
                boundarieOne = Board.FirstColumn;
                boundarieTwo = 0;
            }
            WCRM(arayPos , i,boundarieOne, Rvalues ,piece , checkSquares, piecePased);
            checkSquares = 0;
            piecePased = false;
        }


        System.out.println(whiteIsInCheck);
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