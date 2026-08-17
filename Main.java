import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Main extends JPanel implements ActionListener  { 

    public Timer timer ;
    public Image whitePawn = new ImageIcon("src/Images/wp.png").getImage();
    public Image whiteBishop = new ImageIcon("src/Images/wb.png").getImage();
    public Image whiteKnight = new ImageIcon("src/Images/wkn.png").getImage();
    public Image whiteRook = new ImageIcon("src/Images/wr.png").getImage();
    public Image whiteQueen = new ImageIcon("src/Images/wq.png").getImage();
    public Image whiteKing = new ImageIcon("src/Images/wk.png").getImage();


    public Image blackPawn = new ImageIcon("src/Images/bp.png").getImage();
    public Image blackBishop = new ImageIcon("src/Images/bb.png").getImage();
    public Image blackKnight = new ImageIcon("src/Images/bkh.png").getImage();
    public Image blackRook = new ImageIcon("src/Images/br.png").getImage();
    public Image blackQueen = new ImageIcon("src/Images/bq.png").getImage();
    public Image blackKing = new ImageIcon("src/Images/bK.png").getImage();


    public static void main(String[] args) {
        JFrame frame = new JFrame("Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create an instance of your custom class
        Main gamePanel = new Main();
        
        gamePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                float x = e.getX();
                float y = e.getY();

                float newX = x -900;
                float newY = y -900;

                if ((x>900 || y>900 )||(x<100 ||y<100)){

                    System.out.println("out of bound");

                }else{

                    int row = (int) Math.floor(-newY/100);

                    int column = (int) Math.floor(-newX/100);

                    int posInAray = row * 8 + column;
                    

                    if ((((Board.atackSquares >>> posInAray) & 1L) != 0)) {
                        System.out.println("Moving to position: " + posInAray);
                        MovementFuncion.movePiece(posInAray);
                    }else{MovementFuncion.showAtackSquare(gamePanel.returnPiece(posInAray), posInAray);}

                    
                    
                    // First show attack squares for the clicked piece

                    //System.out.println(posInAray);

                }
            }
        });
        frame.add(gamePanel);
        frame.pack(); // Shrinks the window perfectly around your 600x600 panel
        frame.setLocationRelativeTo(null); // Centers the window on your screen
        frame.setVisible(true);
        gamePanel.setBackground(Color.DARK_GRAY);

        
    }

    public Main (){
        this.setPreferredSize(new Dimension(1000, 1000));

        timer = new Timer(10, this); 
        //test();
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Timer tick: update game state if needed and repaint
        
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Clears the screen
        for (int c = 0; c < 8; c++) {
            for (int r = 0; r < 8; r++) {
                int a =200;
                int araypos =63 -( (c)*8 + r);

                


                if (r %2 ==0){
                    g.setColor(new Color(119, 149, 86));
                    if (c %2 ==0){
                        g.setColor(new Color(235, 236, 208));
                    }
                }
                if (r %2 !=0){
                    g.setColor(new Color(235, 236, 208));
                    if (c %2 ==0){
                        g.setColor(new Color(119, 149, 86));
                    }
                }

                
                

                g.fillRect(r*100 +100, c *100 +100, 100, 100);
                
               g.setColor(new Color(0,0,0 , 70));

                if (((Board.atackSquares >>>  araypos) & 1L) != 0){g.fillOval(r*100 + 125, c*100 + 125, 50, 50);}

                g.setColor(new Color(0,0,0));

                g.drawString(String.valueOf(araypos), (r ) *100 +100, (c ) * 100 + 200);
            }
        }
        int count = 0;

        int x = 800;
        int y = 800;

        for (int i = 0; i < 64; i++) {

            drawWhitePieces(x, y, i , g);
            
            drawBlackPieces(x, y, i , g);

            
            x = x - 100;
            if (count == 7){
                y = y - 100;
                x = 800;
                count = 0;
            }else{count++;}
            
        }
        
        
    }

    public void drawWhitePieces(int x,int y,  int i , Graphics g ){

        if (((Board.whitePawns >>> i) & 1L) != 0) {
            g.drawImage(whitePawn, x, y, 100, 100, this);
        }
        if (((Board.whiteBishops >>> i) & 1L) != 0) {
            g.drawImage(whiteBishop, x, y, 100, 100, this);
        }
        if (((Board.whiteRoocks >>> i) & 1L) != 0) {
            g.drawImage(whiteRook, x, y, 100, 100, this);
        }
        if (((Board.whiteKnights >>> i) & 1L) != 0) {
            g.drawImage(whiteKnight, x, y, 100, 100, this);
        }
        if (((Board.whiteQueen >>> i) & 1L) != 0) {
            g.drawImage(whiteQueen, x, y, 100, 100, this);
        }
        if (((Board.whiteKing >>> i) & 1L) != 0) {
            g.drawImage(whiteKing, x, y, 100, 100, this);
        }
    }

    public void drawBlackPieces(int x,int y,  int i , Graphics g ){

        if (((Board.blackPawns >>> i) & 1L) != 0) {
            g.drawImage(blackPawn, x, y, 100, 100, this);
        }
        if (((Board.blackBishops >>> i) & 1L) != 0) {
            g.drawImage(blackBishop, x, y, 100, 100, this);
        }
        if (((Board.blackRoocks >>> i) & 1L) != 0) {
            g.drawImage(blackRook, x, y, 100, 100, this);
        }
        if (((Board.blackKnights >>> i) & 1L) != 0) {
            g.drawImage(blackKnight, x, y, 100, 100, this);
        }
        if (((Board.blackQueen >>> i) & 1L) != 0) {
            g.drawImage(blackQueen, x, y, 100, 100, this);
        }
        if (((Board.blackKing >>> i) & 1L) != 0) {
            g.drawImage(blackKing, x, y, 100, 100, this);
        }
    }

    public String returnPiece(int i ){

        if (((Board.whitePawns >>> i) & 1L) != 0) {
            return "WP";
        }
        if (((Board.whiteBishops >>> i) & 1L) != 0) {
            return "WB";
        }
        if (((Board.whiteRoocks >>> i) & 1L) != 0) {
            return "WR";
        }
        if (((Board.whiteKnights >>> i) & 1L) != 0) {
            return "WKN";
        }
        if (((Board.whiteQueen >>> i) & 1L) != 0) {
            return "WQ";
        }
        if (((Board.whiteKing >>> i) & 1L) != 0) {
            return "WK";
        }
    
        

        if (((Board.blackPawns >>> i) & 1L) != 0) {
            return "BP";
        }
        if (((Board.blackBishops >>> i) & 1L) != 0) {
            return "BB";
        }
        if (((Board.blackRoocks >>> i) & 1L) != 0) {
            return "BR";
        }
        if (((Board.blackKnights >>> i) & 1L) != 0) {
            return "BKN";
        }
        if (((Board.blackQueen >>> i) & 1L) != 0) {
            return "BQ";
        }
        if (((Board.blackKing >>> i) & 1L) != 0) {
            return "BK";
        }
        
        return "";
    }

}
