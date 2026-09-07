public class Board{
    //board for each white pieces 
    public static long whitePawns = 0x0000_0000_0000_ff00L ;
    public static long whiteBishops = 0x0000_0000_0000_0024L ;
    public static long whiteKnights = 0x0000_0000_0000_0042L ;
    public static long whiteRoocks = 0x0000_0000_0000_0081L ;
    public static long whiteQueen = 0x0000_0000_0000_0010L ;
    public static long whiteKing=  0x0000_0000_0000_0008L ;

    //board for each black pieces 
    public static long blackPawns = 0x00ff_0000_0000_0000L ;
    public static long blackBishops = 0x2400_0000_0000_0000L ;
    public static long blackKnights = 0x4200_0000_0000_0000L ;
    public static long blackRoocks = 0x8100_0000_0000_0000L ;
    public static long blackQueen = 0x1000_0000_0000_0000L ;
    public static long blackKing = 0x0800_0000_0000_0000L ;

    public static long atackSquares = 0x0000_0000_0000_0000L ;
    public static long captureSquares = 0x0000_0000_0000_0000L ;

    //boards for each combined pieces by color
    public static long whiteBoard;
    public static long blackBoard;
    
    //combined bords of all pieces
    public static long board;

    //all check squares
    public static long checkSquares;
    public static long kingCheckSquares;

    public static long whiteAtackSquares;
    public static long BlackAtackSquares;

    public static long allAtackSquares;

    // column
    public static long FirstColumn = 0x0101_0101_0101_0101L ;
    public static long SecondColumn = 0x0202_0202_0202_0202L ;
    public static long ThirdColumn = 0x0404_0404_0404_0404L ;
    public static long FourthColumn = 0x0808_0808_0808_0808L ;
    public static long FifthColumn = 0x1010_1010_1010_1010L ;
    public static long SixthColumn = 0x2020_2020_2020_2020L ;
    public static long SeventhColumn = 0x4040_4040_4040_4040L ;
    public static long EighthColumn = 0x8080_8080_8080_8080L ;

    //rows
    public static long FirstRow = 0x0000_0000_0000_00ffL ;
    public static long SecondRow = 0x0000_0000_0000_ff00L ;
    public static long ThirdCRow = 0x0000_0000_00ff_0000L ;
    public static long FourthRow = 0x0000_0000_ff00_0000L ;
    public static long FifthRow = 0x0000_00ff_0000_0000L ;
    public static long SixthRow = 0x0000_ff00_0000_0000L ;
    public static long SeventhRow = 0x00ff_0000_0000_0000L ;
    public static long EighthRow = 0xff00_0000_0000_0000L ;

    public static long boundaries = 0xff81_8181_8181_81ffL ;

    public static boolean[] WEmpassentSquares = new boolean[64];
    public static boolean[] BEmpassentSquares = new boolean[64];
}