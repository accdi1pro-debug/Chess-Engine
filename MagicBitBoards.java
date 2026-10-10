import java.util.Arrays;
import java.util.Random;

public class MagicBitBoards {

    // 1. DECLARE TABLES & ARRAYS AT THE TOP
    public static final long[][] rookTables = new long[64][];
    public static final long[][] bishopTables = new long[64][];

    public static final long[] RookMasks = new long[64];
    public static final long[] BishopMasks = new long[64];

    private static final long[] rookMagics = new long[64];
    private static final int[] rookShifts = new int[64];

    private static final long[] bishopMagics = new long[64];
    private static final int[] bishopShifts = new int[64];

    public static final long[] KNIGHT_ATTACKS = new long[64];

    // File masks to prevent edge wrapping
    private static final long FILE_A  = 0x0101010101010101L;
    private static final long FILE_B  = 0x0202020202020202L;
    private static final long FILE_G  = 0x4040404040404040L;
    private static final long FILE_H  = 0x8080808080808080L;
    private static final long FILE_AB = FILE_A | FILE_B;
    private static final long FILE_GH = FILE_G | FILE_H;

    public static final long[] KING_ATTACKS = new long[64];

    public static final long[][] PAWN_ATTACKS = new long[2][64];

    public static final long[][] PAWN_PUSHES = new long[2][64];

    public static void initPawnTables() {
        for (int sq = 0; sq < 64; sq++) {
            long bb = 1L << sq;

            if (sq < 56) { // Below Rank 8
                PAWN_PUSHES[0][sq] |= (bb << 8); // Single push (+8)

                // Rank 2 check (squares 8 to 15): add double push (+16)
                if (sq >= 8 && sq <= 15) {
                    PAWN_PUSHES[0][sq] |= (bb << 16);
                }
            }
            PAWN_ATTACKS[0][sq] = ((bb & ~FILE_A) << 7) | ((bb & ~FILE_H) << 9);

            // --- BLACK PAWNS (Index 1) ---
            if (sq >= 8) { // Above Rank 1
                PAWN_PUSHES[1][sq] |= (bb >>> 8); // Single push (-8)

                // Rank 7 check (squares 48 to 55): add double push (-16)
                if (sq >= 48 && sq <= 55) {
                    PAWN_PUSHES[1][sq] |= (bb >>> 16);
                }
            }
            PAWN_ATTACKS[1][sq] = ((bb & ~FILE_H) >>> 7) | ((bb & ~FILE_A) >>> 9);
        }
    }


    // 2. INITIALIZATION METHOD WITH RUNTIME MAGIC GENERATOR
    public static void init() {
        for (int sq = 0; sq < 64; sq++) {
            RookMasks[sq] = generateRookMask(sq);
            BishopMasks[sq] = generateBishopMask(sq);
        }

        initPawnTables();
        initKingAttacks();
        initKnightAttacks();

        for (int sq = 0; sq < 64; sq++) {
            // --- ROOKS ---
            int rookBits = Long.bitCount(RookMasks[sq]);
            rookShifts[sq] = 64 - rookBits;
            rookMagics[sq] = findMagicNumber(sq, rookBits, true);

            int rookTableSize = 1 << rookBits;
            rookTables[sq] = new long[rookTableSize];

            long currentRookOcc = 0L;
            do {
                long attackBb = generateRookAttacksOnTheFly(sq, currentRookOcc);
                int index = (int) ((currentRookOcc * rookMagics[sq]) >>> rookShifts[sq]);
                rookTables[sq][index] = attackBb;

                currentRookOcc = (currentRookOcc - RookMasks[sq]) & RookMasks[sq];
            } while (currentRookOcc != 0L);

            // --- BISHOPS ---
            int bishopBits = Long.bitCount(BishopMasks[sq]);
            bishopShifts[sq] = 64 - bishopBits;
            bishopMagics[sq] = findMagicNumber(sq, bishopBits, false);

            int bishopTableSize = 1 << bishopBits;
            bishopTables[sq] = new long[bishopTableSize];

            long currentBishopOcc = 0L;
            do {
                long attackBb = generateBishopAttacksOnTheFly(sq, currentBishopOcc);
                int index = (int) ((currentBishopOcc * bishopMagics[sq]) >>> bishopShifts[sq]);
                bishopTables[sq][index] = attackBb;

                currentBishopOcc = (currentBishopOcc - BishopMasks[sq]) & BishopMasks[sq];
            } while (currentBishopOcc != 0L);
        }
    }

    // Runtime generator to find collision-free magic numbers automatically
    private static long findMagicNumber(int sq, int bits, boolean isRook) {
        long mask = isRook ? RookMasks[sq] : BishopMasks[sq];
        int n = 1 << bits;
        long[] used = new long[n];
        Random rand = new Random(1991 + sq + (isRook ? 0 : 500));

        while (true) {
            long magic = rand.nextLong() & rand.nextLong() & rand.nextLong();
            if (Long.bitCount((mask * magic) & 0xFF00000000000000L) < 6) continue;

            Arrays.fill(used, 0L);
            boolean fail = false;

            long occupancy = 0L;
            do {
                int index = (int) ((occupancy * magic) >>> (64 - bits));
                long attacks = isRook ? generateRookAttacksOnTheFly(sq, occupancy) : generateBishopAttacksOnTheFly(sq, occupancy);
                
                if (used[index] != 0 && used[index] != attacks) {
                    fail = true;
                    break;
                }
                used[index] = attacks;
                occupancy = (occupancy - mask) & mask;
            } while (occupancy != 0L);

            if (!fail) return magic;
        }
    }
    private static void initKingAttacks() {
        for (int sq = 0; sq < 64; sq++) {
            long bb = 1L << sq;
            long attacks = 0L;

            // North, South, East, West
            attacks |= bb << 8;                      // North
            attacks |= bb >>> 8;                     // South
            attacks |= (bb & ~FILE_H) << 1;          // East
            attacks |= (bb & ~FILE_A) >>> 1;         // West

            // Diagonals
            attacks |= (bb & ~FILE_H) << 9;          // North-East
            attacks |= (bb & ~FILE_A) << 7;          // North-West
            attacks |= (bb & ~FILE_H) >>> 7;         // South-East
            attacks |= (bb & ~FILE_A) >>> 9;         // South-West

            KING_ATTACKS[sq] = attacks;
        }
    }

    private static void initKnightAttacks() {
        for (int sq = 0; sq < 64; sq++) {
            long bb = 1L << sq;
            long attacks = 0L;

            // 1 Up, 2 Right / 1 Up, 2 Left
            attacks |= (bb & ~FILE_GH) << 10;
            attacks |= (bb & ~FILE_AB) << 6;
            
            // 2 Up, 1 Right / 2 Up, 1 Left
            attacks |= (bb & ~FILE_H)  << 17;
            attacks |= (bb & ~FILE_A)  << 15;
            
            // 1 Down, 2 Right / 1 Down, 2 Left
            attacks |= (bb & ~FILE_GH) >>> 6;
            attacks |= (bb & ~FILE_AB) >>> 10;
            
            // 2 Down, 1 Right / 2 Down, 1 Left
            attacks |= (bb & ~FILE_H)  >>> 15;
            attacks |= (bb & ~FILE_A)  >>> 17;

            KNIGHT_ATTACKS[sq] = attacks;
        }
    }
    // 3. HELPER & ATTACK METHODS
    private static long generateRookMask(int sq) {
        long mask = 0L;
        int r = sq / 8, f = sq % 8;
        for (int row = r + 1; row <= 6; row++) mask |= (1L << (row * 8 + f));
        for (int row = r - 1; row >= 1; row--) mask |= (1L << (row * 8 + f));
        for (int col = f + 1; col <= 6; col++) mask |= (1L << (r * 8 + col));
        for (int col = f - 1; col >= 1; col--) mask |= (1L << (r * 8 + col));
        return mask;
    }

    private static long generateBishopMask(int sq) {
        long mask = 0L;
        int r = sq / 8, f = sq % 8;
        for (int rr = r + 1, ff = f + 1; rr <= 6 && ff <= 6; rr++, ff++) mask |= (1L << (rr * 8 + ff));
        for (int rr = r + 1, ff = f - 1; rr <= 6 && ff >= 1; rr++, ff--) mask |= (1L << (rr * 8 + ff));
        for (int rr = r - 1, ff = f + 1; rr >= 1 && ff <= 6; rr++, ff++) mask |= (1L << (rr * 8 + ff));
        for (int rr = r - 1, ff = f - 1; rr >= 1 && ff >= 1; rr--, ff--) mask |= (1L << (rr * 8 + ff));
        return mask;
    }

    public static long getRookAttacks(int square, long occupancy) {
        occupancy &= RookMasks[square];
        int index = (int) ((occupancy * rookMagics[square]) >>> rookShifts[square]);
        return rookTables[square][index];
    }

    public static long getBishopAttacks(int square, long occupancy) {
        occupancy &= BishopMasks[square];
        int index = (int) ((occupancy * bishopMagics[square]) >>> bishopShifts[square]);
        return bishopTables[square][index];
    }

    public static long getQueenAttacks(int square, long occupancy) {
        return getRookAttacks(square, occupancy) | getBishopAttacks(square, occupancy);
    }

    public static int countBits(long bb) {
        return Long.bitCount(bb);
    }

    public static int popLSB(long bb) {
        return Long.numberOfTrailingZeros(bb);
    }

    private static long generateRookAttacksOnTheFly(int sq, long occupancy) {
        long attacks = 0L;
        int r = sq / 8, f = sq % 8;
        for (int rr = r + 1; rr <= 7; rr++) { int t = rr * 8 + f; attacks |= (1L << t); if ((occupancy & (1L << t)) != 0) break; }
        for (int rr = r - 1; rr >= 0; rr--) { int t = rr * 8 + f; attacks |= (1L << t); if ((occupancy & (1L << t)) != 0) break; }
        for (int ff = f + 1; ff <= 7; ff++) { int t = r * 8 + ff; attacks |= (1L << t); if ((occupancy & (1L << t)) != 0) break; }
        for (int ff = f - 1; ff >= 0; ff--) { int t = r * 8 + ff; attacks |= (1L << t); if ((occupancy & (1L << t)) != 0) break; }
        return attacks;
    }

    private static long generateBishopAttacksOnTheFly(int sq, long occupancy) {
        long attacks = 0L;
        int r = sq / 8, f = sq % 8;
        for (int rr = r + 1, ff = f + 1; rr <= 7 && ff <= 7; rr++, ff++) { int t = rr * 8 + ff; attacks |= (1L << t); if ((occupancy & (1L << t)) != 0) break; }
        for (int rr = r + 1, ff = f - 1; rr <= 7 && ff >= 0; rr++, ff--) { int t = rr * 8 + ff; attacks |= (1L << t); if ((occupancy & (1L << t)) != 0) break; }
        for (int rr = r - 1, ff = f + 1; rr >= 0 && ff <= 7; rr--, ff++) { int t = rr * 8 + ff; attacks |= (1L << t); if ((occupancy & (1L << t)) != 0) break; }
        for (int rr = r - 1, ff = f - 1; rr >= 0 && ff >= 0; rr--, ff--) { int t = rr * 8 + ff; attacks |= (1L << t); if ((occupancy & (1L << t)) != 0) break; }
        return attacks;
    }
}