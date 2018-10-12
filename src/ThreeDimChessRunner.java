import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;

import javax.swing.Timer;
import javax.swing.event.MouseInputListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ThreeDimChessRunner extends JPanel implements KeyListener, ActionListener, MouseInputListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	double viewAngle = 0;
	double viewElevation = 0;
	int inputNeg = 1;
	int screenWidth;
	int screenHeight;
	int xpos;
	int ypos;
	int squareSize;
	int buttonSize;
	int buttonGap;
	int titleSize;
	boolean checkmate = false;
	boolean draw = false;
	boolean titleScreen = true;
	boolean escapeScreen = false;
	Player winner;
	PieceType pieceChoice = PieceType.QUEEN;
	Piece pieceSelected;
	Piece pieceTargeted;
	public static boolean hidePawns = false;
	public static boolean hideBarricades = false;
	public static boolean rightMouse = false;
	static Dimension screenSize;
	
	Timer tm = new Timer(5, this);
	ThreeDimBoard game;
	
	public void paintComponent(Graphics g) {
		
		Font f = new Font("Gameover", Font.BOLD, titleSize/2);
		
		Font f2 = new Font("TitleScreen", Font.ITALIC, titleSize);
		
		Font f3 = new Font("Buttons", Font.PLAIN, buttonSize*3/20);
		
		Font f4 = new Font("Coming Soon", Font.BOLD, buttonSize/5);
		
		if (titleScreen) {
			super.paintComponent(g);
			g.setColor(new Color(100,0,0));
			g.fillRect(0, 0, screenWidth, screenHeight);
			
			g.setColor(new Color(20,20,0));
			
			g.fillRect((screenWidth/2) - (buttonSize * 3/2) - buttonGap - 5,
					(screenHeight/2) - buttonSize * 3/4 - 5, buttonSize, buttonSize/2);
			g.fillRect((screenWidth/2) + buttonSize/2 + buttonGap - 5,
					(screenHeight/2) - buttonSize * 3/4 - 5, buttonSize * 3/2, buttonSize/2);
			g.fillRect((screenWidth/2) - buttonSize/2 - 5,
					(screenHeight/2) - buttonSize * 3/4 - 5, buttonSize, buttonSize/2);
			g.fillRect((screenWidth/2) - buttonSize/2 - 5,
					(screenHeight/2) - buttonSize/4 + buttonGap - 5, buttonSize, buttonSize/2);
			
			g.setColor(new Color(50,50,100));
			
			g.fillRect((screenWidth/2) - (buttonSize * 3/2) - buttonGap,
					(screenHeight/2) - buttonSize * 3/4, buttonSize, buttonSize/2);
			g.fillRect((screenWidth/2) + buttonSize/2 + buttonGap,
					(screenHeight/2) - buttonSize * 3/4, buttonSize * 3/2, buttonSize/2);
			g.fillRect((screenWidth/2) - buttonSize/2,
					(screenHeight/2) - buttonSize * 3/4, buttonSize, buttonSize/2);
			g.fillRect((screenWidth/2) - buttonSize/2,
					(screenHeight/2) - buttonSize/4 + buttonGap, buttonSize, buttonSize/2);
			
			g.setColor(new Color(20,20,0));
			
			g.setFont(f3);
			
			g.drawString("2-Player", (screenWidth/2) - (buttonSize * 3/2) - buttonGap + buttonSize/10 - buttonSize/100,
					(screenHeight/2) - buttonSize * 3/4 + buttonSize*3/10 - buttonSize/100);
			g.drawString("4-Player", (screenWidth/2) - buttonSize/2 + buttonSize/10 - buttonSize/100,
					(screenHeight/2) - buttonSize * 3/4 + buttonSize*3/10 - buttonSize/100);
			g.drawString("2-Player Advanced", (screenWidth/2) + buttonSize/2 + buttonGap + buttonSize/10 - buttonSize/100,
					(screenHeight/2) - buttonSize * 3/4 + buttonSize*3/10 - buttonSize/100);
			g.drawString("Quit", (screenWidth/2) - buttonSize/2 + buttonSize/10 - buttonSize/100,
					(screenHeight/2) - buttonSize/4 + buttonGap + buttonSize*3/10 - buttonSize/100);
			
			g.setFont(f2);
			
			g.setColor(new Color(20,20,0));
			
			g.drawString("3D Chess", (screenWidth/2) - (titleSize * 2) - 5, titleSize - 5);
			
			g.setColor(Color.WHITE);
			
			g.drawString("3D Chess", (screenWidth/2) - (titleSize * 2), titleSize);
			
			g.setFont(f3);
			
			g.drawString("2-Player", (screenWidth/2) - (buttonSize * 3/2) - buttonGap + buttonSize/10,
					(screenHeight/2) - buttonSize * 3/4 + buttonSize*3/10);
			g.drawString("4-Player", (screenWidth/2) - buttonSize/2 + buttonSize/10,
					(screenHeight/2) - buttonSize * 3/4 + buttonSize*3/10);
			g.drawString("2-Player Advanced", (screenWidth/2) + buttonSize/2 + buttonGap + buttonSize/10,
					(screenHeight/2) - buttonSize * 3/4 + buttonSize*3/10);
			g.drawString("Quit", (screenWidth/2) - buttonSize/2 + buttonSize/10,
					(screenHeight/2) - buttonSize/4 + buttonGap + buttonSize*3/10);
			
			g.setColor(new Color(20,20,0));
			
			Polygon p1 = new Polygon();
			p1.addPoint((screenWidth/2) - buttonSize * 17/20 - 5, (screenHeight/2)  - buttonSize * 3/4 - 5);
			p1.addPoint((screenWidth/2) - buttonSize * 3/4 - 5, (screenHeight/2) - buttonSize * 3/4 - buttonSize/5 - 5);
			p1.addPoint((screenWidth/2) - 5, (screenHeight/2) - buttonSize * 3/4 + buttonSize * 7/40 - 5);
			p1.addPoint((screenWidth/2) + buttonSize * 3/4 - 5, (screenHeight/2) - buttonSize * 3/4 - buttonSize/5 - 5);
			p1.addPoint((screenWidth/2) + buttonSize * 17/20 - 5, (screenHeight/2) - buttonSize * 3/4 - 5);
			p1.addPoint((screenWidth/2) + buttonSize/4 - 5, (screenHeight/2) - buttonSize * 3/4 + buttonSize * 3/10 - 5);
			p1.addPoint((screenWidth/2) + buttonSize * 17/20 - 5, (screenHeight/2) - buttonSize * 3/4 + buttonSize * 3/5 - 5);
			p1.addPoint((screenWidth/2) + buttonSize * 3/4 - 5, (screenHeight/2) - buttonSize * 3/4 + buttonSize * 4/5 - 5);
			p1.addPoint((screenWidth/2) - 5, (screenHeight/2) - buttonSize * 3/4 + buttonSize * 17/40 - 5);
			p1.addPoint((screenWidth/2) - buttonSize * 3/4 - 5, (screenHeight/2) - buttonSize * 3/4 + buttonSize * 4/5 - 5);
			p1.addPoint((screenWidth/2) - buttonSize * 17/20 - 5, (screenHeight/2) - buttonSize * 3/4 + buttonSize * 3/5 - 5);
			p1.addPoint((screenWidth/2) - buttonSize/4 - 5, (screenHeight/2) - buttonSize * 3/4 + buttonSize * 3/10 - 5);
			g.fillPolygon(p1);
			p1 = new Polygon();
			p1.addPoint((screenWidth/2) - buttonSize * 17/20 - 5 + buttonSize * 5/4 + buttonGap,
					(screenHeight/2)  - buttonSize * 3/4 - 5);
			p1.addPoint((screenWidth/2) - buttonSize * 3/4 - 5 + buttonSize * 5/4 + buttonGap,
					(screenHeight/2) - buttonSize * 3/4 - buttonSize/5 - 5);
			p1.addPoint((screenWidth/2) - 5 + buttonSize * 5/4 + buttonGap,
					(screenHeight/2) - buttonSize * 3/4 + buttonSize * 7/40 - 5);
			p1.addPoint((screenWidth/2) + buttonSize * 3/4 - 5 + buttonSize * 5/4 + buttonGap,
					(screenHeight/2) - buttonSize * 3/4 - buttonSize/5 - 5);
			p1.addPoint((screenWidth/2) + buttonSize * 17/20 - 5 + buttonSize * 5/4 + buttonGap,
					(screenHeight/2) - buttonSize * 3/4 - 5);
			p1.addPoint((screenWidth/2) + buttonSize/4 - 5 + buttonSize * 5/4 + buttonGap,
					(screenHeight/2) - buttonSize * 3/4 + buttonSize * 3/10 - 5);
			p1.addPoint((screenWidth/2) + buttonSize * 17/20 - 5 + buttonSize * 5/4 + buttonGap,
					(screenHeight/2) - buttonSize * 3/4 + buttonSize * 3/5 - 5);
			p1.addPoint((screenWidth/2) + buttonSize * 3/4 - 5 + buttonSize * 5/4 + buttonGap,
					(screenHeight/2) - buttonSize * 3/4 + buttonSize * 4/5 - 5);
			p1.addPoint((screenWidth/2) - 5 + buttonSize * 5/4 + buttonGap,
					(screenHeight/2) - buttonSize * 3/4 + buttonSize * 17/40 - 5);
			p1.addPoint((screenWidth/2) - buttonSize * 3/4 - 5 + buttonSize * 5/4 + buttonGap,
					(screenHeight/2) - buttonSize * 3/4 + buttonSize * 4/5 - 5);
			p1.addPoint((screenWidth/2) - buttonSize * 17/20 - 5 + buttonSize * 5/4 + buttonGap,
					(screenHeight/2) - buttonSize * 3/4 + buttonSize * 3/5 - 5);
			p1.addPoint((screenWidth/2) - buttonSize/4 - 5 + buttonSize * 5/4 + buttonGap,
					(screenHeight/2) - buttonSize * 3/4 + buttonSize * 3/10 - 5);
			g.fillPolygon(p1);
			
			g.setColor(new Color(150,50,0));
			
			p1 = new Polygon();
			p1.addPoint((screenWidth/2) - buttonSize * 17/20, (screenHeight/2)  - buttonSize * 3/4);
			p1.addPoint((screenWidth/2) - buttonSize * 3/4, (screenHeight/2) - buttonSize * 3/4 - buttonSize/5);
			p1.addPoint((screenWidth/2), (screenHeight/2) - buttonSize * 3/4 + buttonSize * 7/40);
			p1.addPoint((screenWidth/2) + buttonSize * 3/4, (screenHeight/2) - buttonSize * 3/4 - buttonSize/5);
			p1.addPoint((screenWidth/2) + buttonSize * 17/20, (screenHeight/2) - buttonSize * 3/4);
			p1.addPoint((screenWidth/2) + buttonSize/4, (screenHeight/2) - buttonSize * 3/4 + buttonSize * 3/10);
			p1.addPoint((screenWidth/2) + buttonSize * 17/20, (screenHeight/2) - buttonSize * 3/4 + buttonSize * 3/5);
			p1.addPoint((screenWidth/2) + buttonSize * 3/4, (screenHeight/2) - buttonSize * 3/4 + buttonSize * 4/5);
			p1.addPoint((screenWidth/2), (screenHeight/2) - buttonSize * 3/4 + buttonSize * 17/40);
			p1.addPoint((screenWidth/2) - buttonSize * 3/4, (screenHeight/2) - buttonSize * 3/4 + buttonSize * 4/5);
			p1.addPoint((screenWidth/2) - buttonSize * 17/20, (screenHeight/2) - buttonSize * 3/4 + buttonSize * 3/5);
			p1.addPoint((screenWidth/2) - buttonSize/4, (screenHeight/2) - buttonSize * 3/4 + buttonSize * 3/10);
			g.fillPolygon(p1);
			p1 = new Polygon();
			p1.addPoint((screenWidth/2) - buttonSize * 17/20 + buttonSize * 5/4 + buttonGap,
					(screenHeight/2)  - buttonSize * 3/4);
			p1.addPoint((screenWidth/2) - buttonSize * 3/4 + buttonSize * 5/4 + buttonGap,
					(screenHeight/2) - buttonSize * 3/4 - buttonSize/5);
			p1.addPoint((screenWidth/2) + buttonSize * 5/4 + buttonGap,
					(screenHeight/2) - buttonSize * 3/4 + buttonSize * 7/40);
			p1.addPoint((screenWidth/2) + buttonSize * 3/4 + buttonSize * 5/4 + buttonGap,
					(screenHeight/2) - buttonSize * 3/4 - buttonSize/5);
			p1.addPoint((screenWidth/2) + buttonSize * 17/20 + buttonSize * 5/4 + buttonGap,
					(screenHeight/2) - buttonSize * 3/4);
			p1.addPoint((screenWidth/2) + buttonSize/4 + buttonSize * 5/4 + buttonGap,
					(screenHeight/2) - buttonSize * 3/4 + buttonSize * 3/10);
			p1.addPoint((screenWidth/2) + buttonSize * 17/20 + buttonSize * 5/4 + buttonGap,
					(screenHeight/2) - buttonSize * 3/4 + buttonSize * 3/5);
			p1.addPoint((screenWidth/2) + buttonSize * 3/4 + buttonSize * 5/4 + buttonGap,
					(screenHeight/2) - buttonSize * 3/4 + buttonSize * 4/5);
			p1.addPoint((screenWidth/2) + buttonSize * 5/4 + buttonGap,
					(screenHeight/2) - buttonSize * 3/4 + buttonSize * 17/40);
			p1.addPoint((screenWidth/2) - buttonSize * 3/4 + buttonSize * 5/4 + buttonGap,
					(screenHeight/2) - buttonSize * 3/4 + buttonSize * 4/5);
			p1.addPoint((screenWidth/2) - buttonSize * 17/20 + buttonSize * 5/4 + buttonGap,
					(screenHeight/2) - buttonSize * 3/4 + buttonSize * 3/5);
			p1.addPoint((screenWidth/2) - buttonSize/4 + buttonSize * 5/4 + buttonGap,
					(screenHeight/2) - buttonSize * 3/4 + buttonSize * 3/10);
			g.fillPolygon(p1);
			
			g.setColor(new Color(20,20,0));
			
			g.setFont(f4);
			
			g.drawString("Coming Soon", (screenWidth/2) - buttonSize * 13/20 - buttonSize/100,
					(screenHeight/2) - buttonSize * 3/4 + buttonSize/2 - buttonSize/100);
			g.drawString("Coming Soon", (screenWidth/2) - buttonSize * 13/20 + buttonSize * 5/4 + buttonGap - buttonSize/100,
					(screenHeight/2) - buttonSize * 3/4 + buttonSize/2 - buttonSize/100);
			
			g.setColor(new Color(250,60,20));
			
			g.drawString("Coming Soon", (screenWidth/2) - buttonSize * 13/20,
					(screenHeight/2) - buttonSize * 3/4 + buttonSize/2);
			g.drawString("Coming Soon", (screenWidth/2) - buttonSize * 13/20 + buttonSize * 5/4 + buttonGap,
					(screenHeight/2) - buttonSize * 3/4 + buttonSize/2);
		}
		else if (escapeScreen) {
			g.setColor(new Color(80,80,40));
			g.setFont(f3);
			
			g.fillRect(0, 0, screenWidth, screenHeight);
			
			g.setColor(new Color(20,20,0));
			
			g.fillRect(screenWidth/2 - buttonSize/2 - 5, 200 - 5, buttonSize, buttonSize/2);
			g.fillRect(screenWidth/2 - buttonSize/2 - 5, 200 + buttonSize/2 + buttonGap - 5, buttonSize, buttonSize/2);
			g.fillRect(screenWidth/2 - buttonSize/2 - 5, 200 + buttonSize + buttonGap * 2 - 5, buttonSize, buttonSize/2);
			
			g.setColor(new Color(200,200,160));
			
			g.fillRect(screenWidth/2 - buttonSize/2, 200, buttonSize, buttonSize/2);
			g.fillRect(screenWidth/2 - buttonSize/2, 200 + buttonSize/2 + buttonGap, buttonSize, buttonSize/2);
			g.fillRect(screenWidth/2 - buttonSize/2, 200 + buttonSize + buttonGap * 2, buttonSize, buttonSize/2);
			
			g.setColor(new Color(20,20,0));
			
			g.drawString("Resume", screenWidth/2 - buttonSize * 4/10 - buttonSize/100, 200 + buttonSize * 3/10 - buttonSize/100);
			g.drawString("Back to Menu", screenWidth/2 - buttonSize * 9/20 - buttonSize/100, 200 + buttonSize * 8/10 + buttonGap - buttonSize/100);
			g.drawString("Quit", screenWidth/2 - buttonSize * 4/10 - buttonSize/100, 200 + buttonSize * 13/10 + buttonGap * 2 - buttonSize/100);
			
			g.setColor(Color.RED);
			
			g.drawString("Resume", screenWidth/2 - buttonSize * 4/10, 200 + buttonSize * 3/10);
			g.drawString("Back to Menu", screenWidth/2 - buttonSize * 9/20, 200 + buttonSize * 8/10 + buttonGap);
			g.drawString("Quit", screenWidth/2 - buttonSize * 4/10, 200 + buttonSize * 13/10 + buttonGap * 2);
		}
		else {
			super.paintComponent(g);
			g.setColor(new Color(100,0,0));
			g.fillRect(0, 0, screenWidth, screenHeight);
			g.setColor(Color.BLACK);
			
			g.setFont(f);
			
			drawBoard(g);
			if (checkmate == true) {
				if (winner == Player.WHITE) {
					g.setColor(Color.WHITE);
					g.drawString("White Wins", screenWidth/2 - titleSize*13/10, screenHeight/2 - titleSize*3/10);
				}
				if (winner == Player.BLACK) {
					g.setColor(Color.WHITE);
					g.drawString("Black Wins", screenWidth/2 - titleSize*13/10, screenHeight/2 - titleSize*3/10);
				}
			}
			if (draw == true) {
				g.setColor(Color.WHITE);
				g.drawString("Draw", screenWidth/2 - titleSize*8/10, screenHeight/2 - titleSize*3/10);
			}
			
			g.setColor(new Color(20,20,0));
			
			g.fillRect(screenWidth/2 + squareSize * 6 - 5, 200, buttonSize - 5, buttonSize/2);
			g.fillRect(screenWidth/2 + squareSize * 6 - 5, 200 + buttonSize/2 + buttonGap - 5, buttonSize, buttonSize/2);
			g.fillRect(screenWidth/2 + squareSize * 6 - 5, 200 + buttonSize + buttonGap * 2 - 5, buttonSize, buttonSize/2);
			
			g.setColor(new Color(50,50,100));
			
			g.fillRect(screenWidth/2 + squareSize * 6, 200, buttonSize, buttonSize/2);
			g.fillRect(screenWidth/2 + squareSize * 6, 200 + buttonSize/2 + buttonGap, buttonSize, buttonSize/2);
			g.fillRect(screenWidth/2 + squareSize * 6, 200 + buttonSize + buttonGap * 2, buttonSize, buttonSize/2);
			
			g.setFont(f3);
			
			g.setColor(new Color(20,20,0));
			
			g.drawString("Restart", screenWidth/2 + squareSize * 6 + buttonSize/10 - buttonSize/100, 200 + buttonSize * 3/10 - buttonSize/100);
			g.drawString("Offer Draw", screenWidth/2 + squareSize * 6 + buttonSize/10 - buttonSize/100, 200 + buttonSize/2 + buttonGap + buttonSize * 3/10 - buttonSize/100);
			g.drawString("Resign", screenWidth/2 + squareSize * 6 + buttonSize/10 - buttonSize/100, 200 + buttonSize + buttonGap * 2 + buttonSize * 3/10 - buttonSize/100);
			
			g.setColor(new Color(20,20,0));
			
			g.drawString("Restart", screenWidth/2 + squareSize * 6 + buttonSize/10, 200 + buttonSize * 3/10);
			g.drawString("Offer Draw", screenWidth/2 + squareSize * 6 + buttonSize/10, 200 + buttonSize/2 + buttonGap + buttonSize * 3/10);
			g.drawString("Resign", screenWidth/2 + squareSize * 6 + buttonSize/10, 200 + buttonSize + buttonGap * 2 + buttonSize * 3/10);
		}
	}
	
	public ThreeDimChessRunner() {
		winner = Player.NONE;
		game = new ThreeDimBoard(0);
		screenWidth = screenSize.width;
		screenHeight = screenSize.height;
		//screenWidth = 800;
		//screenHeight = 680;
		titleSize = 200;
		buttonSize = 200;
		buttonGap = 100;
		squareSize = 70;
		pieceSelected = null;
		pieceTargeted = null;
		if(screenHeight < 980 || screenWidth < 1200)
			squareSize = 60;
		if(screenHeight < 980 || screenWidth < 1024) {
			squareSize = 60;
			buttonSize = 100;
			buttonGap = 50;
			titleSize = 150;
		}
		if(screenHeight < 860 || screenWidth < 900)
			squareSize = 50;
		if(screenHeight < 700 || screenWidth < 800) {
			squareSize = 40;
			titleSize = 100;
			buttonGap = 30;
		}
		tm.start();
	}
	
	public static void main(String[] args){
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		ThreeDimChessRunner p = new ThreeDimChessRunner();
		JFrame frame = new JFrame("3D Chess");	
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		//frame.setSize(800, 680);
		frame.addMouseListener(p);
		frame.addMouseMotionListener(p);
		frame.addKeyListener(p);
		frame.setFocusable(true);
		frame.setFocusTraversalKeysEnabled(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(p);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(escapeScreen)
			setBackground(new Color(80,80,40));
		else
			setBackground(new Color(100,0,0));
		
		if (pieceTargeted != null) {
			int[] t = new int[3];
			int[] v = new int[3];
			for (int k = 0; k < 3; k++) {
				t[k] = pieceSelected.location[k];
				v[k] = pieceTargeted.location[k] - pieceSelected.location[k];
			}
			game.kings[game.turn][0].inCheck = false;
			game.move(t, v);
			if (game.inCheck(game.turn))
				game.kings[game.turn][0].inCheck = true;
			if (game.checkmate(game.turn)) {
				checkmate = true;
				if (game.turn == 0) winner = Player.BLACK;
				else winner = Player.WHITE;
			}
			else if (game.stalemate(game.turn))
				draw = true;
			pieceSelected = null;
			pieceTargeted = null;
			
		}
		
		if (viewElevation > Math.PI/2)
			viewElevation = Math.PI/2;
		
		if (viewElevation < -Math.PI/2)
			viewElevation = -Math.PI/2;
		
		if (viewAngle >= 2 * Math.PI)
			viewAngle -= 2 * Math.PI;
		
        if (viewAngle < 0)
        	viewAngle += 2 * Math.PI;
        
        if (game.moveCount == 50)
        	draw = true;

        repaint();
    }
	
	private void drawWallSection(Graphics g, int[] xValue, int[] yValue, int[] zValue) {
		double[] x = {Math.cos(viewAngle), Math.sin(viewAngle) * Math.sin(viewElevation)};
		double[] y = {Math.sin(viewAngle), - (Math.cos(viewAngle) * Math.sin(viewElevation))};
		double z = - Math.cos(viewElevation);
		int[] xMod;
		int[] yMod;
		int[] zMod;
		
		if (xValue.length == 1) {
			xMod = new int[] {0,0,0};
			yMod = new int[] {1,1,0};
			zMod = new int[] {0,1,1};
		}
		else if (yValue.length == 1) {
			xMod = new int[] {1,1,0};
			yMod = new int[] {0,0,0};
			zMod = new int[] {0,1,1};
		}
		else {
			xMod = new int[] {1,1,0};
			yMod = new int[] {0,1,1};
			zMod = new int[] {0,0,0};
		}
		
		for (int xnum : xValue) {
			for (int ynum : yValue) {
				for (int znum : zValue) {
					Polygon p1 = new Polygon();
					p1.addPoint((int)Math.round(screenWidth/2 + xnum*squareSize*x[0] + ynum*squareSize*y[0]), 
							(int)Math.round(screenHeight/2 + ynum*squareSize*y[1] + xnum*squareSize*x[1] + squareSize*znum*z));
					p1.addPoint((int)Math.round(screenWidth/2 + (xnum + xMod[0])*squareSize*x[0] + (ynum + yMod[0])*squareSize*y[0]), 
							(int)Math.round(screenHeight/2 + (ynum + yMod[0])*squareSize*y[1] + (xnum + xMod[0])*squareSize*x[1] + squareSize*(znum + zMod[0])*z));
					p1.addPoint((int)Math.round(screenWidth/2 + (xnum + xMod[1])*squareSize*x[0] + (ynum + yMod[1])*squareSize*y[0]), 
							(int)Math.round(screenHeight/2 + (ynum + yMod[1])*squareSize*y[1] + (xnum + xMod[1])*squareSize*x[1] + squareSize*(znum + zMod[1])*z));
					p1.addPoint((int)Math.round(screenWidth/2 + (xnum + xMod[2])*squareSize*x[0] + (ynum + yMod[2])*squareSize*y[0]), 
							(int)Math.round(screenHeight/2 + (ynum + yMod[2])*squareSize*y[1] + (xnum + xMod[2])*squareSize*x[1] + squareSize*(znum + zMod[2])*z));
					g.fillPolygon(p1);
				}
			}
		}
	}
	
	private void drawWall(Graphics g, int var, boolean top) {
		double[] x = {Math.cos(viewAngle), Math.sin(viewAngle) * Math.sin(viewElevation)};
		double[] y = {Math.sin(viewAngle), - (Math.cos(viewAngle) * Math.sin(viewElevation))};
		double z = - Math.cos(viewElevation);
		int[] xValue = null;
		int[] yValue = null;
		int[] zValue = null;
		
		if (top) {
			g.setColor(new Color(160,160,120));
			if (var == 0)
				xValue = new int[] {4};
			else if(var == 1)
				yValue = new int[] {4};
			else
				zValue = new int[] {4};
		}
		else {
			g.setColor(Color.BLACK);
			if (var == 0)
				xValue = new int[] {-4};
			else if (var == 1)
				yValue = new int[] {-4};
			else
				zValue = new int[] {-4};
		}
		
		if (xValue == null)
			xValue = new int[] {-4, -2, 0, 2};
		
		if (yValue == null)
			yValue = new int[] {-4, -2, 0, 2};
		
		if (zValue == null)
			zValue = new int[] {-4, -2, 0, 2};
		
		
		drawWallSection(g, xValue, yValue, zValue);
		
		if (xValue.length > 1)
			xValue = new int[] {-3, -1, 1, 3};
		
		if (yValue.length > 1)
			yValue = new int[] {-3, -1, 1, 3};
		
		if (zValue.length > 1)
			zValue = new int[] {-3, -1, 1, 3};
		
		drawWallSection(g, xValue, yValue, zValue);
		
		if (top)
			g.setColor(Color.BLACK);
		else
			g.setColor(new Color(160,160,120));
		
		if (xValue.length > 1 && yValue.length > 1) {
			xValue = new int[] {-4, -2, 0, 2};
			yValue = new int[] {-3, -1, 1, 3};
		}
		else if (yValue.length > 1 && zValue.length > 1) {
			yValue = new int[] {-4, -2, 0, 2};
			zValue = new int[] {-3, -1, 1, 3};
		}
		else {
			zValue = new int[] {-4, -2, 0, 2};
			xValue = new int[] {-3, -1, 1, 3};
		}
		
		drawWallSection(g, xValue, yValue, zValue);
		
		if (xValue.length > 1 && yValue.length > 1) {
			xValue = new int[] {-3, -1, 1, 3};
			yValue = new int[] {-4, -2, 0, 2};
		}
		else if (yValue.length > 1 && zValue.length > 1) {
			yValue = new int[] {-3, -1, 1, 3};
			zValue = new int[] {-4, -2, 0, 2};
		}
		else {
			zValue = new int[] {-3, -1, 1, 3};
			xValue = new int[] {-4, -2, 0, 2};
		}
		
		drawWallSection(g, xValue, yValue, zValue);
		
		if (xValue.length > 1)
			xValue = new int[] {-4, 4};
		
		if (yValue.length > 1)
			yValue = new int[] {-4, 4};
		
		if (zValue.length > 1)
			zValue = new int[] {-4, 4};
		
		Point[][] p = new Point[2][2];
		
		g.setColor(Color.BLACK);
		
		if (xValue.length == 1)
			for (int k = 0; k < yValue.length; k++) {
				for (int j = 0; j < zValue.length; j++) {
					p[k][j] = new Point((int)Math.round(screenWidth/2 + xValue[0]*squareSize*x[0] + yValue[k]*squareSize*y[0]), 
							(int)Math.round(screenHeight/2 + yValue[k]*squareSize*y[1] + xValue[0]*squareSize*x[1] + squareSize*zValue[j]*z));
				}
			}
		
		else if (yValue.length == 1)
			for (int k = 0; k < xValue.length; k++) {
				for (int j = 0; j < zValue.length; j++) {
					p[k][j] = new Point((int)Math.round(screenWidth/2 + squareSize*xValue[k]*x[0] + squareSize*yValue[0]*y[0]), 
							(int)Math.round(screenHeight/2 + squareSize*yValue[0]*y[1] + squareSize*xValue[k]*x[1] + squareSize*zValue[j]*z));
				}
			}
		
		else
			for (int k = 0; k < xValue.length; k++) {
				for (int j = 0; j < yValue.length; j++) {
					p[k][j] = new Point((int)Math.round(screenWidth/2 + squareSize*xValue[k]*x[0] + squareSize*yValue[j]*y[0]), 
							(int)Math.round(screenHeight/2 + squareSize*yValue[j]*y[1] + squareSize*xValue[k]*x[1] + squareSize*zValue[0]*z));
				}
			}
		
		g.drawLine(p[0][0].x, p[0][0].y, p[0][1].x, p[0][1].y);
		g.drawLine(p[0][0].x, p[0][0].y, p[1][0].x, p[1][0].y);
		g.drawLine(p[1][1].x, p[1][1].y, p[0][1].x, p[0][1].y);
		g.drawLine(p[1][1].x, p[1][1].y, p[1][0].x, p[1][0].y);
	}
	
	private Piece closestPiece(int xpos, int ypos, int argument) {
		Piece returnPiece = null;
		
		double[] x = {Math.cos(viewAngle), Math.sin(viewAngle) * Math.sin(viewElevation)};
		double[] y = {Math.sin(viewAngle), - (Math.cos(viewAngle) * Math.sin(viewElevation))};
		double z = - Math.cos(viewElevation);
		
		int xpref = 0;
		int ypref = 7;
		int zpref = 0;
		
		if (viewAngle < Math.PI)
			xpref = 7;
		if (viewAngle < Math.PI / 2 || viewAngle > 3 * Math.PI / 2)
			ypref = 0;
		if (viewElevation > 0)
			zpref = 7;
		
		for (int k = xpref; k != (-xpref*9/7) + 8; k-= xpref*2/7 - 1)
			for (int j = ypref; j != (-ypref*9/7) + 8; j-= ypref*2/7 - 1)
				for (int m = zpref; m != (-zpref*9/7) + 8; m-= zpref*2/7 - 1)
					if ((argument == 0 && ((game.square[m][k][j].pt != PieceType.EMPTY && game.square[m][k][j].pt != PieceType.PAWN && 
							game.square[m][k][j].pt != PieceType.BARRICADE) || 
							(game.square[m][k][j].pt == PieceType.PAWN && hidePawns == false) || 
							(game.square[m][k][j].pt == PieceType.BARRICADE && hideBarricades == false))) || 
							(argument == 1 && game.square[m][k][j].targeted)) {
						double xfact = squareSize * (game.square[m][k][j].location[1] - 3.5);
						double yfact = squareSize * (game.square[m][k][j].location[2] - 3.5);
						double zfact = squareSize * (game.square[m][k][j].location[0] - 3.5);
						if (xpos >= (int)Math.round(screenWidth/2 - 25 + (xfact * x[0]) + (yfact * y[0])) && 
								xpos <= (int)Math.round(screenWidth/2 + 25 + (xfact * x[0]) + (yfact * y[0])) && 
								ypos >= (int)Math.round(screenHeight/2 - 20 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)) && 
								ypos <= (int)Math.round(screenHeight/2 + 30 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z))) {
							returnPiece = game.square[m][k][j];
							return returnPiece;
						}
					}
		return returnPiece;
	}
	
	public void drawBoard(Graphics g) {
		g.setColor(Color.BLACK);
		
		if (viewElevation >= 0)
			drawWall(g,2,false);
		
		else
			drawWall(g,2,true);
		
		if (viewAngle < Math.PI)
			drawWall(g,0,false);
		
		else
			drawWall(g,0,true);
		
		if (viewAngle < Math.PI / 2 || viewAngle > 3 * Math.PI / 2)
			drawWall(g,1,true);
		
		else
			drawWall(g,1,false);
		
		game.drawPieces(pieceChoice, g, viewAngle, viewElevation, squareSize, screenWidth, screenHeight);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int c = e.getKeyCode();
		
		if (!titleScreen && !escapeScreen) {
			
			if (c == KeyEvent.VK_G) {
				if (hideBarricades == true)
					hideBarricades = false;
				else
					hideBarricades = true;
			}
			
			if (c == KeyEvent.VK_H) {
				if (hidePawns == true)
					hidePawns = false;
				else
					hidePawns = true;
			}
			
			if (c == KeyEvent.VK_SPACE) {
				if(pieceChoice == PieceType.QUEEN)
					pieceChoice = PieceType.PRINCE;
				else if(pieceChoice == PieceType.PRINCE)
					pieceChoice = PieceType.ROOK;
				else if(pieceChoice == PieceType.ROOK)
					pieceChoice = PieceType.BISHOP;
				else if(pieceChoice == PieceType.BISHOP)
					pieceChoice = PieceType.KNIGHT;
				else if(pieceChoice == PieceType.KNIGHT)
					pieceChoice = PieceType.QUEEN;
			}
		}
		if (!titleScreen) {
			if (c == KeyEvent.VK_ESCAPE) {
				escapeScreen = !escapeScreen;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		int c = e.getButton();
		xpos = e.getX();
		ypos = e.getY();
		
		if (c == MouseEvent.BUTTON1) {
			
			if (!titleScreen && !escapeScreen) {
				double[] x = {Math.cos(viewAngle), Math.sin(viewAngle) * Math.sin(viewElevation)};
				double[] y = {Math.sin(viewAngle), - (Math.cos(viewAngle) * Math.sin(viewElevation))};
				double z = - Math.cos(viewElevation);
				
				if (game.toPromote == null) {
					
					if (pieceSelected == null) {
						pieceSelected = closestPiece(xpos, ypos, 0);
						
						if (pieceSelected != null) {
							pieceSelected.highlighted = true;
							game.targetBasedOn(pieceSelected);
						}
					}
					else {
						pieceTargeted = closestPiece(xpos, ypos, 1);
						if (pieceTargeted == null) {
							pieceSelected = null;
						}
						for (int k = 0; k < 8; k++)
							for (int j = 0; j < 8; j++)
								for (int m = 0; m < 8; m++)
									game.square[k][j][m].highlighted = false;
						game.detarget();
					}
				}
				else {
					double xfact = squareSize * (game.toPromote.location[1] - 3.5);
					double yfact = squareSize * (game.toPromote.location[2] - 3.5);
					double zfact = squareSize * (game.toPromote.location[0] - 3.5);
					if (xpos >= (int)Math.round(screenWidth/2 - 25 + (xfact * x[0]) + (yfact * y[0])) && 
							xpos <= (int)Math.round(screenWidth/2 + 25 + (xfact * x[0]) + (yfact * y[0])) && 
							ypos >= (int)Math.round(screenHeight/2 - 20 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)) && 
							ypos <= (int)Math.round(screenHeight/2 + 30 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z))) {
						game.promote(pieceChoice);
						pieceChoice = PieceType.QUEEN;
					}
				}
				
				if (xpos >= screenWidth/2 + squareSize * 6 && xpos <= screenWidth/2 + squareSize * 6 + buttonSize && 
						ypos >= 200 && ypos <= 200 + buttonSize/2 && 
						(checkmate || draw)) {
					winner = Player.NONE;
					game = new ThreeDimBoard(0);
					inputNeg = 1;
					checkmate = false;
					draw = false;
					pieceChoice = PieceType.QUEEN;
					hidePawns = false;
					hideBarricades = false;
					pieceSelected = null;
					pieceTargeted = null;
				}
				
				if (xpos >= screenWidth/2 + squareSize * 6 && xpos <= screenWidth/2 + squareSize * 6 + buttonSize && 
						ypos >= 200 + buttonSize/2 + buttonGap && ypos <= 200 + buttonSize + buttonGap && 
						!checkmate)
					draw = true;
				
				if (xpos >= screenWidth/2 + squareSize * 6 && xpos <= screenWidth/2 + squareSize * 6 + buttonSize && 
						ypos >= 200 + buttonSize + buttonGap * 2 && ypos <= 200 + buttonSize * 3/2 + buttonGap * 2 && !draw) {
					checkmate = true;
					if (game.turn == 0)
						winner = Player.BLACK;
					else
						winner = Player.WHITE;
				}
			}
			else if (titleScreen == true) {
				
				if (xpos >= screenWidth/2 - buttonSize * 3/2 - buttonGap && xpos <= screenWidth/2 - buttonSize/2 - buttonGap && 
						ypos >= screenHeight/2 - buttonSize * 3/4 && ypos <= screenHeight/2 - buttonSize/4) {
					titleScreen = false;
					winner = Player.NONE;
					game = new ThreeDimBoard(0);
					viewAngle = 0;
					viewElevation = 0;
					inputNeg = 1;
					checkmate = false;
					draw = false;
					pieceChoice = PieceType.QUEEN;
					hidePawns = false;
					hideBarricades = false;
					pieceSelected = null;
					pieceTargeted = null;
				}
				
				if (xpos >= screenWidth/2 - buttonSize/2 && xpos <= screenWidth/2 + buttonSize/2 && 
						ypos >= screenHeight/2 - buttonSize/4 + buttonGap && ypos <= screenHeight/2 + buttonSize/4 + buttonGap)
					System.exit(0);
				
			}
			else if (escapeScreen == true) {
				
				if (xpos >= screenWidth/2 - buttonSize/2 && xpos <= screenWidth/2 + buttonSize/2 && 
						ypos >= 200 && ypos <= 200 + buttonSize/2)
					escapeScreen = false;
				
				if (xpos >= screenWidth/2 - buttonSize/2 && xpos <= screenWidth/2 + buttonSize/2 && 
						ypos >= 200 + buttonSize/2 + buttonGap && ypos <= 200 + buttonSize + buttonGap) {
					titleScreen = true;
					escapeScreen = false;
				}
				
				if (xpos >= screenWidth/2 - buttonSize/2 && xpos <= screenWidth/2 + buttonSize/2 && 
						ypos >= 200 + buttonSize + buttonGap * 2 && ypos <= 200 + buttonSize * 3/2 + buttonGap * 2)
					System.exit(0);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		xpos = e.getX();
		ypos = e.getY();
		int c = e.getButton();
		
		if (c == MouseEvent.BUTTON3)
			rightMouse = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		xpos = e.getX();
		ypos = e.getY();
		int c = e.getButton();
		
		if (c == MouseEvent.BUTTON3)
			rightMouse = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		if(rightMouse) {
			viewAngle += ((double)(xpos - e.getX()))/500;
			viewElevation -= ((double)(ypos - e.getY()))/500;
		}
		xpos = e.getX();
		ypos = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
}
