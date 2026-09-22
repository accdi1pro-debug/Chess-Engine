import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MovesGenerator {
    public static int count =0;

    public static Random random = new Random();
    public static List<Integer> from = new ArrayList<>();
    public static List<Integer> who = new ArrayList<>();
    public static List<Integer> where = new ArrayList<>();

    public static List<Move> moves = new ArrayList<>();

    public static void generateBlackMoves(){
        moves.clear();

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

                        moves.add(new Move(i,r,pieceType));

                        resetState();
                    }

                }

            }
        }
        animateMoves();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        MovementManager.turn = true;


    }

    public static void generateWhiteMoves(){
        moves.clear();

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
                            moves.add(new Move(i,r,piece));

                            resetState();
                        }
                    }

                }
            }
        }
        animateMoves(); 
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        MovementManager.turn = false;
        
    }

    public static void animateMoves() {

        int randint ;
        if (!moves.isEmpty()){
            randint = random.nextInt(moves.size());
        }else{
            return;
        }
        Move(randint);

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

    public static void Move(int posInList){

        MovementManager.showAtackSquare(moves.get(posInList).piece(),moves.get(posInList).from());
        MovementManager.movePiece(moves.get(posInList).to());

        if (MovementManager.whitePromotionUI){
            MovementManager.promotionPiece = 3;
            MovementManager.whitePromotion(MovementManager.promotionsquare);
        }if (MovementManager.blackPromotionUI){
            MovementManager.promotionPiece = 3;
            MovementManager.blackPromotion(MovementManager.promotionsquare);
        }
    }
}