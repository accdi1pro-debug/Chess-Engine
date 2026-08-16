public class Board{
    //board for each white pieces 
    public static long whitePawns = 0x0000_0000_0000_ff00L ;
    public static long whiteBishops = 0x0000_0000_0000_0024L ;
    public static long whiteKnights = 0x0000_0000_0000_0042L ;
    public static long whiteRoocks = 0x0000_0000_0000_0081L ;
    public static long whiteQueen = 0x0000_0000_0000_0008L ;
    public static long whiteKing= 0x0000_0000_0000_0010L ;

    //board for each black pieces 
    public static long blackPawns = 0x00ff_0000_0000_0000L ;
    public static long blackBishops = 0x4200_0000_0000_0000L ;
    public static long blackKnights = 0x2400_0000_0000_0000L ;
    public static long blackRoocks = 0x8100_0000_0000_0000L ;
    public static long blackQueen = 0x0800_0000_0000_0000L ;
    public static long blackKing = 0x1000_0000_0000_0000L ;

    public static long atackSquares = 0x1000_0000_0f00_0000L ;

    //boards for each combined pieces by color
    public static long whiteBoard;
    public static long blackBoard;
    
    //combined bords of all pieces
    public static long board;

}