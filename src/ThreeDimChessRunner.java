import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;

import java.util.ArrayList;

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
	int elevate = 0;
	int turning = 0;
	int drawError = 0;
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
	public static boolean hidePawns = false;
	public static boolean hideBarricades = false;
	ArrayList<Integer> record;
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
		record = new ArrayList<Integer>();
		winner = Player.NONE;
		game = new ThreeDimBoard(0);
		addMouseListener(this);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		screenWidth = screenSize.width;
		screenHeight = screenSize.height;
		//screenWidth = 800;
		//screenHeight = 680;
		titleSize = 200;
		buttonSize = 200;
		buttonGap = 100;
		squareSize = 70;
		if(screenHeight < 980 || screenWidth < 1200)
			squareSize = 60;
		if(screenHeight < 980 || screenWidth < 1024) {
			squareSize = 60;
			buttonSize = 100;
			buttonGap = 50;
			titleSize = 150;
		}
		if(screenHeight < 860 || screenWidth < 900) {
			squareSize = 50;
		}
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
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(p);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(escapeScreen)
			setBackground(new Color(80,80,40));
		else
			setBackground(new Color(100,0,0));
		
		if (drawError > 0)
			drawError--;
		if (record.size() == 6) {
			int[] t = new int[3];
			int[] v = new int[3];
			for (int k = 0; k < 3; k++) {
				t[k] = record.get(k);
				v[k] = record.get(k+3);
			}
			if (game.moveValid(t, v) && ((game.turn == 0 && game.square[t[0]][t[1]][t[2]].p == Player.WHITE) || 
					(game.turn == 1 && game.square[t[0]][t[1]][t[2]].p == Player.BLACK))) {
				
				if (!game.intoCheck(game.turn, t, v)) {
					game.kings[game.turn][0].inCheck = false;
					game.move(t, v);
					game.detarget();
					game.square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].highlighted = false;
					if (game.inCheck(game.turn))
						game.kings[game.turn][0].inCheck = true;
					else
						game.kings[game.turn][0].inCheck = false;
					if (game.checkmate(game.turn)) {
						checkmate = true;
						if (game.turn == 0) winner = Player.BLACK;
						else winner = Player.WHITE;
					}
					else if (game.stalemate(game.turn))
						draw = true;
				}
				else 
					drawError = 100;
			}
			else
				drawError = 100;
			record = new ArrayList<Integer>();
		}
		viewAngle += (double)turning * tm.getDelay()/160;
		if (viewElevation + (double)elevate * tm.getDelay()/160 > Math.PI/2)
			viewElevation = Math.PI/2;
		else if (viewElevation + (double)elevate * tm.getDelay()/160 < -Math.PI/2)
			viewElevation = -Math.PI/2;
		else
			viewElevation += (double)elevate * tm.getDelay()/160;
		if (viewAngle >= 2 * Math.PI)
			viewAngle -= 2 * Math.PI;
        if (viewAngle < 0)
        	viewAngle += 2 * Math.PI;
        
        if (game.moveCount == 50)
        	draw = true;

        repaint();
    }
	
	public void drawBoard(Graphics g) {
		double[] x = {Math.cos(viewAngle), Math.sin(viewAngle) * Math.sin(viewElevation)};
		double[] y = {Math.sin(viewAngle), - (Math.cos(viewAngle) * Math.sin(viewElevation))};
		double z = - Math.cos(viewElevation);
		
		g.setColor(Color.BLACK);
		
		if (viewElevation >= 0) {
			for (int k = 0; k < 4; k++) {
				for (int j = 0; j < 4; j++) {
					Polygon p1 = new Polygon();
					Polygon p2 = new Polygon();
					g.setColor(new Color(160,160,120));
					p1.addPoint((int)Math.round(screenWidth/2 + ((k-2) * squareSize * 2 * x[0]) + ((((j-1) * squareSize * 2)-squareSize) * y[0])), 
							(int)Math.round(screenHeight/2 + ((((j-1) * squareSize * 2)-squareSize) * y[1]) + ((k-2) * squareSize * 2 * x[1]) - (squareSize * 4 * z)));
					p1.addPoint((int)Math.round(screenWidth/2 + ((((k-2) * squareSize * 2) + squareSize) * x[0]) + ((((j-1) * squareSize * 2)-squareSize) * y[0])), 
							(int)Math.round(screenHeight/2 + ((((j-1) * squareSize * 2)-squareSize) * y[1]) + ((((k-2) * squareSize * 2) + squareSize) * x[1]) - (squareSize * 4 * z)));
					p1.addPoint((int)Math.round(screenWidth/2 + ((((k-2) * squareSize * 2) + squareSize) * x[0]) + ((j-1) * squareSize * 2 * y[0])), 
							(int)Math.round(screenHeight/2 + ((j-1) * squareSize * 2 * y[1]) + ((((k-2) * squareSize * 2) + squareSize) * x[1]) - (squareSize * 4 * z)));
					p1.addPoint((int)Math.round(screenWidth/2 + ((k-2) * squareSize * 2 * x[0]) + ((j-1) * squareSize * 2 * y[0])), 
							(int)Math.round(screenHeight/2 + ((j-1) * squareSize * 2 * y[1]) + ((k-2) * squareSize * 2 * x[1]) - (squareSize * 4 * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + ((k-1) * squareSize * 2 * x[0]) + ((((j-2) * squareSize * 2) + squareSize) * y[0])), 
							(int)Math.round(screenHeight/2 + ((((j-2) * squareSize * 2) + squareSize) * y[1]) + ((k-1) * squareSize * 2 * x[1]) - (squareSize * 4 * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + ((((k-1) * squareSize * 2) - squareSize) * x[0]) + ((((j-2) * squareSize * 2) + squareSize) * y[0])), 
							(int)Math.round(screenHeight/2 + ((((j-2) * squareSize * 2) + squareSize) * y[1]) + ((((k-1) * squareSize * 2) - squareSize) * x[1]) - (squareSize * 4 * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + ((((k-1) * squareSize * 2) - squareSize) * x[0]) + ((j-2) * squareSize * 2 * y[0])), 
							(int)Math.round(screenHeight/2 + ((j-2) * squareSize * 2 * y[1]) + ((((k-1) * squareSize * 2) - squareSize) * x[1]) - (squareSize * 4 * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + ((k-1) * squareSize * 2 * x[0]) + ((j-2) * squareSize * 2 * y[0])), 
							(int)Math.round(screenHeight/2 + ((j-2) * squareSize * 2 * y[1]) + ((k-1) * squareSize * 2 * x[1]) - (squareSize * 4 * z)));
					g.fillPolygon(p1); //White squares
					g.fillPolygon(p2); //White squares
					p1 = new Polygon();
					p2 = new Polygon();
					g.setColor(Color.BLACK);
					p1.addPoint((int)Math.round(screenWidth/2 + ((k-2) * squareSize * 2 * x[0]) + ((j-2) * squareSize * 2 * y[0])), 
							(int)Math.round(screenHeight/2 + ((j-2) * squareSize * 2 * y[1]) + ((k-2) * squareSize * 2 * x[1]) - (squareSize * 4 * z)));
					p1.addPoint((int)Math.round(screenWidth/2 + ((((k-2) * squareSize * 2) + squareSize) * x[0]) + ((j-2) * squareSize * 2 * y[0])), 
							(int)Math.round(screenHeight/2 + ((j-2) * squareSize * 2 * y[1]) + ((((k-2) * squareSize * 2) + squareSize) * x[1]) - (squareSize * 4 * z)));
					p1.addPoint((int)Math.round(screenWidth/2 + ((((k-2) * squareSize * 2) + squareSize) * x[0]) + ((((j-2) * squareSize * 2) + squareSize) * y[0])), 
							(int)Math.round(screenHeight/2 + ((((j-2) * squareSize * 2) + squareSize) * y[1]) + ((((k-2) * squareSize * 2) + squareSize) * x[1]) - (squareSize * 4 * z)));
					p1.addPoint((int)Math.round(screenWidth/2 + ((k-2) * squareSize * 2 * x[0]) + ((((j-2) * squareSize * 2) + squareSize) * y[0])), 
							(int)Math.round(screenHeight/2 + ((((j-2) * squareSize * 2) + squareSize) * y[1]) + ((k-2) * squareSize * 2 * x[1]) - (squareSize * 4 * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + ((k-1) * squareSize * 2 * x[0]) + ((j-1) * squareSize * 2 * y[0])), 
							(int)Math.round(screenHeight/2 + ((j-1) * squareSize * 2 * y[1]) + ((k-1) * squareSize * 2 * x[1]) - (squareSize * 4 * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + ((((k-1) * squareSize * 2) - squareSize) * x[0]) + ((j-1) * squareSize * 2 * y[0])), 
							(int)Math.round(screenHeight/2 + ((j-1) * squareSize * 2 * y[1]) + ((((k-1) * squareSize * 2) - squareSize) * x[1]) - (squareSize * 4 * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + ((((k-1) * squareSize * 2) - squareSize) * x[0]) + ((((j-1) * squareSize * 2) - squareSize) * y[0])), 
							(int)Math.round(screenHeight/2 + ((((j-1) * squareSize * 2) - squareSize) * y[1]) + ((((k-1) * squareSize * 2) - squareSize) * x[1]) - (squareSize * 4 * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + ((k-1) * squareSize * 2 * x[0]) + ((((j-1) * squareSize * 2) - squareSize) * y[0])), 
							(int)Math.round(screenHeight/2 + ((((j-1) * squareSize * 2) - squareSize) * y[1]) + ((k-1) * squareSize * 2 * x[1]) - (squareSize * 4 * z)));
					g.fillPolygon(p1); //Black squares
					g.fillPolygon(p2); //Black squares
				}
			}
			
			for (int k = -squareSize * 4; k < squareSize * 5; k += squareSize * 5) {
				g.drawLine((int)Math.round(screenWidth/2 + (k * x[0]) + (squareSize * 4 * y[0])), 
						(int)Math.round(screenHeight/2 + (squareSize * 4 * y[1]) + (k * x[1]) - (squareSize * 4 * z)), 
						(int)Math.round(screenWidth/2 + (k * x[0]) - (squareSize * 4 * y[0])), 
						(int)Math.round(screenHeight/2 - (squareSize * 4 * y[1]) + (k * x[1]) - (squareSize * 4 * z))); // Draw bottom length board lines.
				g.drawLine((int)Math.round(screenWidth/2 - (squareSize * 4 * x[0]) + (k * y[0])), 
						(int)Math.round(screenHeight/2 + (k * y[1]) - (squareSize * 4 * x[1]) - (squareSize * 4 * z)), 
						(int)Math.round(screenWidth/2 + (squareSize * 4 * x[0]) + (k * y[0])), 
						(int)Math.round(screenHeight/2 + (k * y[1]) + (squareSize * 4 * x[1]) - (squareSize * 4 * z))); // Draw bottom width board lines.
			}
		}
		
		else {
			for (int k = 0; k < 4; k++) {
				for (int j = 0; j < 4; j++) {
					Polygon p1 = new Polygon();
					Polygon p2 = new Polygon();
					g.setColor(Color.BLACK);
					p1.addPoint((int)Math.round(screenWidth/2 + ((k-2) * squareSize * 2 * x[0]) + ((((j-1) * squareSize * 2)-squareSize) * y[0])), 
							(int)Math.round(screenHeight/2 + ((((j-1) * squareSize * 2)-squareSize) * y[1]) + ((k-2) * squareSize * 2 * x[1]) + (squareSize * 4 * z)));
					p1.addPoint((int)Math.round(screenWidth/2 + ((((k-2) * squareSize * 2) + squareSize) * x[0]) + ((((j-1) * squareSize * 2)-squareSize) * y[0])), 
							(int)Math.round(screenHeight/2 + ((((j-1) * squareSize * 2)-squareSize) * y[1]) + ((((k-2) * squareSize * 2) + squareSize) * x[1]) + (squareSize * 4 * z)));
					p1.addPoint((int)Math.round(screenWidth/2 + ((((k-2) * squareSize * 2) + squareSize) * x[0]) + ((j-1) * squareSize * 2 * y[0])), 
							(int)Math.round(screenHeight/2 + ((j-1) * squareSize * 2 * y[1]) + ((((k-2) * squareSize * 2) + squareSize) * x[1]) + (squareSize * 4 * z)));
					p1.addPoint((int)Math.round(screenWidth/2 + ((k-2) * squareSize * 2 * x[0]) + ((j-1) * squareSize * 2 * y[0])), 
							(int)Math.round(screenHeight/2 + ((j-1) * squareSize * 2 * y[1]) + ((k-2) * squareSize * 2 * x[1]) + (squareSize * 4 * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + ((k-1) * squareSize * 2 * x[0]) + ((((j-2) * squareSize * 2) + squareSize) * y[0])), 
							(int)Math.round(screenHeight/2 + ((((j-2) * squareSize * 2) + squareSize) * y[1]) + ((k-1) * squareSize * 2 * x[1]) + (squareSize * 4 * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + ((((k-1) * squareSize * 2) - squareSize) * x[0]) + ((((j-2) * squareSize * 2) + squareSize) * y[0])), 
							(int)Math.round(screenHeight/2 + ((((j-2) * squareSize * 2) + squareSize) * y[1]) + ((((k-1) * squareSize * 2) - squareSize) * x[1]) + (squareSize * 4 * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + ((((k-1) * squareSize * 2) - squareSize) * x[0]) + ((j-2) * squareSize * 2 * y[0])), 
							(int)Math.round(screenHeight/2 + ((j-2) * squareSize * 2 * y[1]) + ((((k-1) * squareSize * 2) - squareSize) * x[1]) + (squareSize * 4 * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + ((k-1) * squareSize * 2 * x[0]) + ((j-2) * squareSize * 2 * y[0])), 
							(int)Math.round(screenHeight/2 + ((j-2) * squareSize * 2 * y[1]) + ((k-1) * squareSize * 2 * x[1]) + (squareSize * 4 * z)));
					g.fillPolygon(p1);
					g.fillPolygon(p2);
					p1 = new Polygon();
					p2 = new Polygon();
					g.setColor(new Color(160,160,120));
					p1.addPoint((int)Math.round(screenWidth/2 + ((k-2) * squareSize * 2 * x[0]) + ((j-2) * squareSize * 2 * y[0])), 
							(int)Math.round(screenHeight/2 + ((j-2) * squareSize * 2 * y[1]) + ((k-2) * squareSize * 2 * x[1]) + (squareSize * 4 * z)));
					p1.addPoint((int)Math.round(screenWidth/2 + ((((k-2) * squareSize * 2) + squareSize) * x[0]) + ((j-2) * squareSize * 2 * y[0])), 
							(int)Math.round(screenHeight/2 + ((j-2) * squareSize * 2 * y[1]) + ((((k-2) * squareSize * 2) + squareSize) * x[1]) + (squareSize * 4 * z)));
					p1.addPoint((int)Math.round(screenWidth/2 + ((((k-2) * squareSize * 2) + squareSize) * x[0]) + ((((j-2) * squareSize * 2) + squareSize) * y[0])), 
							(int)Math.round(screenHeight/2 + ((((j-2) * squareSize * 2) + squareSize) * y[1]) + ((((k-2) * squareSize * 2) + squareSize) * x[1]) + (squareSize * 4 * z)));
					p1.addPoint((int)Math.round(screenWidth/2 + ((k-2) * squareSize * 2 * x[0]) + ((((j-2) * squareSize * 2) + squareSize) * y[0])), 
							(int)Math.round(screenHeight/2 + ((((j-2) * squareSize * 2) + squareSize) * y[1]) + ((k-2) * squareSize * 2 * x[1]) + (squareSize * 4 * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + ((k-1) * squareSize * 2 * x[0]) + ((j-1) * squareSize * 2 * y[0])), 
							(int)Math.round(screenHeight/2 + ((j-1) * squareSize * 2 * y[1]) + ((k-1) * squareSize * 2 * x[1]) + (squareSize * 4 * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + ((((k-1) * squareSize * 2) - squareSize) * x[0]) + ((j-1) * squareSize * 2 * y[0])), 
							(int)Math.round(screenHeight/2 + ((j-1) * squareSize * 2 * y[1]) + ((((k-1) * squareSize * 2) - squareSize) * x[1]) + (squareSize * 4 * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + ((((k-1) * squareSize * 2) - squareSize) * x[0]) + ((((j-1) * squareSize * 2) - squareSize) * y[0])), 
							(int)Math.round(screenHeight/2 + ((((j-1) * squareSize * 2) - squareSize) * y[1]) + ((((k-1) * squareSize * 2) - squareSize) * x[1]) + (squareSize * 4 * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + ((k-1) * squareSize * 2 * x[0]) + ((((j-1) * squareSize * 2) - squareSize) * y[0])), 
							(int)Math.round(screenHeight/2 + ((((j-1) * squareSize * 2) - squareSize) * y[1]) + ((k-1) * squareSize * 2 * x[1]) + (squareSize * 4 * z)));
					g.fillPolygon(p1);
					g.fillPolygon(p2);
					g.setColor(Color.BLACK);
				}
			}
			
			for (int k = -squareSize * 4; k < squareSize * 5; k += squareSize * 5) {
				g.drawLine((int)Math.round(screenWidth/2 + (k * x[0]) + (squareSize * 4 * y[0])), 
						(int)Math.round(screenHeight/2 + (squareSize * 4 * y[1]) + (k * x[1]) + (squareSize * 4 * z)), 
						(int)Math.round(screenWidth/2 + (k * x[0]) - (squareSize * 4 * y[0])), 
						(int)Math.round(screenHeight/2 - (squareSize * 4 * y[1]) + (k * x[1]) + (squareSize * 4 * z))); // Draw top length board lines.
				g.drawLine((int)Math.round(screenWidth/2 - (squareSize * 4 * x[0]) + (k * y[0])), 
						(int)Math.round(screenHeight/2 + (k * y[1]) - (squareSize * 4 * x[1]) + (squareSize * 4 * z)), 
						(int)Math.round(screenWidth/2 + (squareSize * 4 * x[0]) + (k * y[0])), 
						(int)Math.round(screenHeight/2 + (k * y[1]) + (squareSize * 4 * x[1]) + (squareSize * 4 * z))); // Draw top width board lines.
			}
		}
		
		if (viewAngle < Math.PI) {
			for (int k = -squareSize * 4; k < squareSize * 4; k+=squareSize * 2) {
				for (int j = -squareSize * 4; j < squareSize * 4; j+=squareSize * 2) {
					Polygon p1 = new Polygon();
					Polygon p2 = new Polygon();
					g.setColor(Color.BLACK);
					p1.addPoint((int)Math.round(screenWidth/2 - (squareSize * 4 * x[0]) + (k * y[0])), 
							(int)Math.round(screenHeight/2 + (k * y[1]) - (squareSize * 4 * x[1]) + (j * z)));
					p1.addPoint((int)Math.round(screenWidth/2 - (squareSize * 4 * x[0]) + ((k+squareSize) * y[0])), 
							(int)Math.round(screenHeight/2 + ((k+squareSize) * y[1]) - (squareSize * 4 * x[1]) + (j * z)));
					p1.addPoint((int)Math.round(screenWidth/2 - (squareSize * 4 * x[0]) + ((k+squareSize) * y[0])), 
							(int)Math.round(screenHeight/2 + ((k+squareSize) * y[1]) - (squareSize * 4 * x[1]) + ((j+squareSize) * z)));
					p1.addPoint((int)Math.round(screenWidth/2 - (squareSize * 4 * x[0]) + (k * y[0])), 
							(int)Math.round(screenHeight/2 + (k * y[1]) - (squareSize * 4 * x[1]) + ((j+squareSize) * z)));
					p2.addPoint((int)Math.round(screenWidth/2 - (squareSize * 4 * x[0]) + ((k+squareSize) * y[0])), 
							(int)Math.round(screenHeight/2 + ((k+squareSize) * y[1]) - (squareSize * 4 * x[1]) + ((j+squareSize) * z)));
					p2.addPoint((int)Math.round(screenWidth/2 - (squareSize * 4 * x[0]) + ((k+squareSize * 2) * y[0])), 
							(int)Math.round(screenHeight/2 + ((k+squareSize * 2) * y[1]) - (squareSize * 4 * x[1]) + ((j+squareSize) * z)));
					p2.addPoint((int)Math.round(screenWidth/2 - (squareSize * 4 * x[0]) + ((k+squareSize * 2) * y[0])), 
							(int)Math.round(screenHeight/2 + ((k+squareSize * 2) * y[1]) - (squareSize * 4 * x[1]) + ((j+squareSize * 2) * z)));
					p2.addPoint((int)Math.round(screenWidth/2 - (squareSize * 4 * x[0]) + ((k+squareSize) * y[0])), 
							(int)Math.round(screenHeight/2 + ((k+squareSize) * y[1]) - (squareSize * 4 * x[1]) + ((j+squareSize * 2) * z)));
					g.fillPolygon(p1);
					g.fillPolygon(p2);
					p1 = new Polygon();
					p2 = new Polygon();
					g.setColor(new Color(160,160,120));
					p1.addPoint((int)Math.round(screenWidth/2 - (squareSize * 4 * x[0]) + ((k+squareSize) * y[0])), 
							(int)Math.round(screenHeight/2 + ((k+squareSize) * y[1]) - (squareSize * 4 * x[1]) + (j * z)));
					p1.addPoint((int)Math.round(screenWidth/2 - (squareSize * 4 * x[0]) + ((k+squareSize * 2) * y[0])), 
							(int)Math.round(screenHeight/2 + ((k+squareSize * 2) * y[1]) - (squareSize * 4 * x[1]) + (j * z)));
					p1.addPoint((int)Math.round(screenWidth/2 - (squareSize * 4 * x[0]) + ((k+squareSize * 2) * y[0])), 
							(int)Math.round(screenHeight/2 + ((k+squareSize * 2) * y[1]) - (squareSize * 4 * x[1]) + ((j+squareSize) * z)));
					p1.addPoint((int)Math.round(screenWidth/2 - (squareSize * 4 * x[0]) + ((k+squareSize) * y[0])), 
							(int)Math.round(screenHeight/2 + ((k+squareSize) * y[1]) - (squareSize * 4 * x[1]) + ((j+squareSize) * z)));
					p2.addPoint((int)Math.round(screenWidth/2 - (squareSize * 4 * x[0]) + (k * y[0])), 
							(int)Math.round(screenHeight/2 + (k * y[1]) - (squareSize * 4 * x[1]) + ((j+squareSize) * z)));
					p2.addPoint((int)Math.round(screenWidth/2 - (squareSize * 4 * x[0]) + ((k+squareSize) * y[0])), 
							(int)Math.round(screenHeight/2 + ((k+squareSize) * y[1]) - (squareSize * 4 * x[1]) + ((j+squareSize) * z)));
					p2.addPoint((int)Math.round(screenWidth/2 - (squareSize * 4 * x[0]) + ((k+squareSize) * y[0])), 
							(int)Math.round(screenHeight/2 + ((k+squareSize) * y[1]) - (squareSize * 4 * x[1]) + ((j+squareSize * 2) * z)));
					p2.addPoint((int)Math.round(screenWidth/2 - (squareSize * 4 * x[0]) + (k * y[0])), 
							(int)Math.round(screenHeight/2 + (k * y[1]) - (squareSize * 4 * x[1]) + ((j+squareSize * 2) * z)));
					g.fillPolygon(p1);
					g.fillPolygon(p2);
					g.setColor(Color.BLACK);
				}
			}
			
			for (int k = -squareSize * 4; k < squareSize * 5; k += squareSize * 5) {
				g.drawLine((int)Math.round(screenWidth/2 - (squareSize * 4 * x[0]) + (k * y[0])), 
						(int)Math.round(screenHeight/2 + (k * y[1]) - (squareSize * 4 * x[1]) + (squareSize * 4 * z)), 
						(int)Math.round(screenWidth/2 - (squareSize * 4 * x[0]) + (k * y[0])), 
						(int)Math.round(screenHeight/2 + (k * y[1]) - (squareSize * 4 * x[1]) - (squareSize * 4 * z))); // Draw back vertical board lines.
				g.drawLine((int)Math.round(screenWidth/2 - (squareSize * 4 * x[0]) + (squareSize * 4 * y[0])), 
						(int)Math.round(screenHeight/2 + (squareSize * 4 * y[1]) - (squareSize * 4 * x[1]) + (k * z)), 
						(int)Math.round(screenWidth/2 - (squareSize * 4 * x[0]) - (squareSize * 4 * y[0])), 
						(int)Math.round(screenHeight/2 - (squareSize * 4 * y[1]) - (squareSize * 4 * x[1]) + (k * z))); // Draw back horizontal board lines.
			}
		}
		
		else {
			for (int k = -squareSize * 4; k < squareSize * 4; k+=squareSize * 2) {
				for (int j = -squareSize * 4; j < squareSize * 4; j+=squareSize * 2) {
					Polygon p1 = new Polygon();
					Polygon p2 = new Polygon();
					g.setColor(new Color(160,160,120));
					p1.addPoint((int)Math.round(screenWidth/2 + (squareSize * 4 * x[0]) + (k * y[0])), 
							(int)Math.round(screenHeight/2 + (k * y[1]) + (squareSize * 4 * x[1]) + (j * z)));
					p1.addPoint((int)Math.round(screenWidth/2 + (squareSize * 4 * x[0]) + ((k+squareSize) * y[0])), 
							(int)Math.round(screenHeight/2 + ((k+squareSize) * y[1]) + (squareSize * 4 * x[1]) + (j * z)));
					p1.addPoint((int)Math.round(screenWidth/2 + (squareSize * 4 * x[0]) + ((k+squareSize) * y[0])), 
							(int)Math.round(screenHeight/2 + ((k+squareSize) * y[1]) + (squareSize * 4 * x[1]) + ((j+squareSize) * z)));
					p1.addPoint((int)Math.round(screenWidth/2 + (squareSize * 4 * x[0]) + (k * y[0])), 
							(int)Math.round(screenHeight/2 + (k * y[1]) + (squareSize * 4 * x[1]) + ((j+squareSize) * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + (squareSize * 4 * x[0]) + ((k+squareSize) * y[0])), 
							(int)Math.round(screenHeight/2 + ((k+squareSize) * y[1]) + (squareSize * 4 * x[1]) + ((j+squareSize) * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + (squareSize * 4 * x[0]) + ((k+squareSize * 2) * y[0])), 
							(int)Math.round(screenHeight/2 + ((k+squareSize * 2) * y[1]) + (squareSize * 4 * x[1]) + ((j+squareSize) * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + (squareSize * 4 * x[0]) + ((k+squareSize * 2) * y[0])), 
							(int)Math.round(screenHeight/2 + ((k+squareSize * 2) * y[1]) + (squareSize * 4 * x[1]) + ((j+squareSize * 2) * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + (squareSize * 4 * x[0]) + ((k+squareSize) * y[0])), 
							(int)Math.round(screenHeight/2 + ((k+squareSize) * y[1]) + (squareSize * 4 * x[1]) + ((j+squareSize * 2) * z)));
					g.fillPolygon(p1);
					g.fillPolygon(p2);
					p1 = new Polygon();
					p2 = new Polygon();
					g.setColor(Color.BLACK);
					p1.addPoint((int)Math.round(screenWidth/2 + (squareSize * 4 * x[0]) + ((k+squareSize) * y[0])), 
							(int)Math.round(screenHeight/2 + ((k+squareSize) * y[1]) + (squareSize * 4 * x[1]) + (j * z)));
					p1.addPoint((int)Math.round(screenWidth/2 + (squareSize * 4 * x[0]) + ((k+squareSize * 2) * y[0])), 
							(int)Math.round(screenHeight/2 + ((k+squareSize * 2) * y[1]) + (squareSize * 4 * x[1]) + (j * z)));
					p1.addPoint((int)Math.round(screenWidth/2 + (squareSize * 4 * x[0]) + ((k+squareSize * 2) * y[0])), 
							(int)Math.round(screenHeight/2 + ((k+squareSize * 2) * y[1]) + (squareSize * 4 * x[1]) + ((j+squareSize) * z)));
					p1.addPoint((int)Math.round(screenWidth/2 + (squareSize * 4 * x[0]) + ((k+squareSize) * y[0])), 
							(int)Math.round(screenHeight/2 + ((k+squareSize) * y[1]) + (squareSize * 4 * x[1]) + ((j+squareSize) * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + (squareSize * 4 * x[0]) + (k * y[0])), 
							(int)Math.round(screenHeight/2 + (k * y[1]) + (squareSize * 4 * x[1]) + ((j+squareSize) * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + (squareSize * 4 * x[0]) + ((k+squareSize) * y[0])), 
							(int)Math.round(screenHeight/2 + ((k+squareSize) * y[1]) + (squareSize * 4 * x[1]) + ((j+squareSize) * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + (squareSize * 4 * x[0]) + ((k+squareSize) * y[0])), 
							(int)Math.round(screenHeight/2 + ((k+squareSize) * y[1]) + (squareSize * 4 * x[1]) + ((j+squareSize * 2) * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + (squareSize * 4 * x[0]) + (k * y[0])), 
							(int)Math.round(screenHeight/2 + (k * y[1]) + (squareSize * 4 * x[1]) + ((j+squareSize * 2) * z)));
					g.fillPolygon(p1);
					g.fillPolygon(p2);
				}
			}
			
			for (int k = -squareSize * 4; k < squareSize * 5; k += squareSize * 5) {
				g.drawLine((int)Math.round(screenWidth/2 + (squareSize * 4 * x[0]) + (k * y[0])), 
						(int)Math.round(screenHeight/2 + (k * y[1]) + (squareSize * 4 * x[1]) + (squareSize * 4 * z)), 
						(int)Math.round(screenWidth/2 + (squareSize * 4 * x[0]) + (k * y[0])), 
						(int)Math.round(screenHeight/2 + (k * y[1]) + (squareSize * 4 * x[1]) - (squareSize * 4 * z))); // Draw back vertical board lines.
				g.drawLine((int)Math.round(screenWidth/2 + (squareSize * 4 * x[0]) + (squareSize * 4 * y[0])), 
						(int)Math.round(screenHeight/2 + (squareSize * 4 * y[1]) + (squareSize * 4 * x[1]) + (k * z)), 
						(int)Math.round(screenWidth/2 + (squareSize * 4 * x[0]) - (squareSize * 4 * y[0])), 
						(int)Math.round(screenHeight/2 - (squareSize * 4 * y[1]) + (squareSize * 4 * x[1]) + (k * z))); // Draw back horizontal board lines.
			}
		}
		
		if (viewAngle < Math.PI / 2 || viewAngle > 3 * Math.PI / 2) {
			for (int k = -squareSize * 4; k < squareSize * 4; k+=squareSize * 2) {
				for (int j = -squareSize * 4; j < squareSize * 4; j+=squareSize * 2) {
					Polygon p1 = new Polygon();
					Polygon p2 = new Polygon();
					g.setColor(new Color(160,160,120));
					p1.addPoint((int)Math.round(screenWidth/2 + (k * x[0]) + (squareSize * 4 * y[0])),
							(int)Math.round(screenHeight/2 + (squareSize * 4 * y[1]) + (k * x[1]) + (j * z)));
					p1.addPoint((int)Math.round(screenWidth/2 + ((k+squareSize) * x[0]) + (squareSize * 4 * y[0])),
							(int)Math.round(screenHeight/2 + (squareSize * 4 * y[1]) + ((k+squareSize) * x[1]) + (j * z)));
					p1.addPoint((int)Math.round(screenWidth/2 + ((k+squareSize) * x[0]) + (squareSize * 4 * y[0])),
							(int)Math.round(screenHeight/2 + (squareSize * 4 * y[1]) + ((k+squareSize) * x[1]) + ((j+squareSize) * z)));
					p1.addPoint((int)Math.round(screenWidth/2 + (k * x[0]) + (squareSize * 4 * y[0])),
							(int)Math.round(screenHeight/2 + (squareSize * 4 * y[1]) + (k * x[1]) + ((j+squareSize) * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + ((k+squareSize) * x[0]) + (squareSize * 4 * y[0])),
							(int)Math.round(screenHeight/2 + (squareSize * 4 * y[1]) + ((k+squareSize) * x[1]) + ((j+squareSize) * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + ((k+squareSize * 2) * x[0]) + (squareSize * 4 * y[0])),
							(int)Math.round(screenHeight/2 + (squareSize * 4 * y[1]) + ((k+squareSize * 2) * x[1]) + ((j+squareSize) * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + ((k+squareSize * 2) * x[0]) + (squareSize * 4 * y[0])),
							(int)Math.round(screenHeight/2 + (squareSize * 4 * y[1]) + ((k+squareSize * 2) * x[1]) + ((j+squareSize * 2) * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + ((k+squareSize) * x[0]) + (squareSize * 4 * y[0])),
							(int)Math.round(screenHeight/2 + (squareSize * 4 * y[1]) + ((k+squareSize) * x[1]) + ((j+squareSize * 2) * z)));
					g.fillPolygon(p1);
					g.fillPolygon(p2);
					p1 = new Polygon();
					p2 = new Polygon();
					g.setColor(Color.BLACK);
					p1.addPoint((int)Math.round(screenWidth/2 + ((k+squareSize) * x[0]) + (squareSize * 4 * y[0])),
							(int)Math.round(screenHeight/2 + (squareSize * 4 * y[1]) + ((k+squareSize) * x[1]) + (j * z)));
					p1.addPoint((int)Math.round(screenWidth/2 + ((k+squareSize * 2) * x[0]) + (squareSize * 4 * y[0])),
							(int)Math.round(screenHeight/2 + (squareSize * 4 * y[1]) + ((k+squareSize * 2) * x[1]) + (j * z)));
					p1.addPoint((int)Math.round(screenWidth/2 + ((k+squareSize * 2) * x[0]) + (squareSize * 4 * y[0])),
							(int)Math.round(screenHeight/2 + (squareSize * 4 * y[1]) + ((k+squareSize * 2) * x[1]) + ((j+squareSize) * z)));
					p1.addPoint((int)Math.round(screenWidth/2 + ((k+squareSize) * x[0]) + (squareSize * 4 * y[0])),
							(int)Math.round(screenHeight/2 + (squareSize * 4 * y[1]) + ((k+squareSize) * x[1]) + ((j+squareSize) * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + (k * x[0]) + (squareSize * 4 * y[0])),
							(int)Math.round(screenHeight/2 + (squareSize * 4 * y[1]) + (k * x[1]) + ((j+squareSize) * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + ((k+squareSize) * x[0]) + (squareSize * 4 * y[0])),
							(int)Math.round(screenHeight/2 + (squareSize * 4 * y[1]) + ((k+squareSize) * x[1]) + ((j+squareSize) * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + ((k+squareSize) * x[0]) + (squareSize * 4 * y[0])),
							(int)Math.round(screenHeight/2 + (squareSize * 4 * y[1]) + ((k+squareSize) * x[1]) + ((j+squareSize * 2) * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + (k * x[0]) + (squareSize * 4 * y[0])),
							(int)Math.round(screenHeight/2 + (squareSize * 4 * y[1]) + (k * x[1]) + ((j+squareSize * 2) * z)));
					g.fillPolygon(p1);
					g.fillPolygon(p2);
				}
			}
			
			for (int k = -squareSize * 4; k < squareSize * 5; k += squareSize * 5) {
				g.drawLine((int)Math.round(screenWidth/2 + (k * x[0]) + (squareSize * 4 * y[0])), 
						(int)Math.round(screenHeight/2 + (squareSize * 4 * y[1]) + (k * x[1]) + (squareSize * 4 * z)), 
						(int)Math.round(screenWidth/2 + (k * x[0]) + (squareSize * 4 * y[0])), 
						(int)Math.round(screenHeight/2 + (squareSize * 4 * y[1]) + (k * x[1]) - (squareSize * 4 * z))); // Draw other back vertical board lines.
				g.drawLine((int)Math.round(screenWidth/2 - (squareSize * 4 * x[0]) + (squareSize * 4 * y[0])), 
						(int)Math.round(screenHeight/2 + (squareSize * 4 * y[1]) - (squareSize * 4 * x[1]) + (k * z)), 
						(int)Math.round(screenWidth/2 + (squareSize * 4 * x[0]) + (squareSize * 4 * y[0])), 
						(int)Math.round(screenHeight/2 + (squareSize * 4 * y[1]) + (squareSize * 4 * x[1]) + (k * z))); // Draw other back horizontal board lines.
			}
		}
		
		else {
			for (int k = -squareSize * 4; k < squareSize * 4; k+=squareSize * 2) {
				for (int j = -squareSize * 4; j < squareSize * 4; j+=squareSize * 2) {
					Polygon p1 = new Polygon();
					Polygon p2 = new Polygon();
					g.setColor(new Color(160,160,120));
					p1.addPoint((int)Math.round(screenWidth/2 + (k * x[0]) - (squareSize * 4 * y[0])),
							(int)Math.round(screenHeight/2 - (squareSize * 4 * y[1]) + (k * x[1]) + (j * z)));
					p1.addPoint((int)Math.round(screenWidth/2 + ((k+squareSize) * x[0]) - (squareSize * 4 * y[0])),
							(int)Math.round(screenHeight/2 - (squareSize * 4 * y[1]) + ((k+squareSize) * x[1]) + (j * z)));
					p1.addPoint((int)Math.round(screenWidth/2 + ((k+squareSize) * x[0]) - (squareSize * 4 * y[0])),
							(int)Math.round(screenHeight/2 - (squareSize * 4 * y[1]) + ((k+squareSize) * x[1]) + ((j+squareSize) * z)));
					p1.addPoint((int)Math.round(screenWidth/2 + (k * x[0]) - (squareSize * 4 * y[0])),
							(int)Math.round(screenHeight/2 - (squareSize * 4 * y[1]) + (k * x[1]) + ((j+squareSize) * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + ((k+squareSize) * x[0]) - (squareSize * 4 * y[0])),
							(int)Math.round(screenHeight/2 - (squareSize * 4 * y[1]) + ((k+squareSize) * x[1]) + ((j+squareSize) * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + ((k+squareSize * 2) * x[0]) - (squareSize * 4 * y[0])),
							(int)Math.round(screenHeight/2 - (squareSize * 4 * y[1]) + ((k+squareSize * 2) * x[1]) + ((j+squareSize) * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + ((k+squareSize * 2) * x[0]) - (squareSize * 4 * y[0])),
							(int)Math.round(screenHeight/2 - (squareSize * 4 * y[1]) + ((k+squareSize * 2) * x[1]) + ((j+squareSize * 2) * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + ((k+squareSize) * x[0]) - (squareSize * 4 * y[0])),
							(int)Math.round(screenHeight/2 - (squareSize * 4 * y[1]) + ((k+squareSize) * x[1]) + ((j+squareSize * 2) * z)));
					g.fillPolygon(p1);
					g.fillPolygon(p2);
					p1 = new Polygon();
					p2 = new Polygon();
					g.setColor(Color.BLACK);
					p1.addPoint((int)Math.round(screenWidth/2 + ((k+squareSize) * x[0]) - (squareSize * 4 * y[0])),
							(int)Math.round(screenHeight/2 - (squareSize * 4 * y[1]) + ((k+squareSize) * x[1]) + (j * z)));
					p1.addPoint((int)Math.round(screenWidth/2 + ((k+squareSize * 2) * x[0]) - (squareSize * 4 * y[0])),
							(int)Math.round(screenHeight/2 - (squareSize * 4 * y[1]) + ((k+squareSize * 2) * x[1]) + (j * z)));
					p1.addPoint((int)Math.round(screenWidth/2 + ((k+squareSize * 2) * x[0]) - (squareSize * 4 * y[0])),
							(int)Math.round(screenHeight/2 - (squareSize * 4 * y[1]) + ((k+squareSize * 2) * x[1]) + ((j+squareSize) * z)));
					p1.addPoint((int)Math.round(screenWidth/2 + ((k+squareSize) * x[0]) - (squareSize * 4 * y[0])),
							(int)Math.round(screenHeight/2 - (squareSize * 4 * y[1]) + ((k+squareSize) * x[1]) + ((j+squareSize) * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + (k * x[0]) - (squareSize * 4 * y[0])),
							(int)Math.round(screenHeight/2 - (squareSize * 4 * y[1]) + (k * x[1]) + ((j+squareSize) * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + ((k+squareSize) * x[0]) - (squareSize * 4 * y[0])),
							(int)Math.round(screenHeight/2 - (squareSize * 4 * y[1]) + ((k+squareSize) * x[1]) + ((j+squareSize) * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + ((k+squareSize) * x[0]) - (squareSize * 4 * y[0])),
							(int)Math.round(screenHeight/2 - (squareSize * 4 * y[1]) + ((k+squareSize) * x[1]) + ((j+squareSize * 2) * z)));
					p2.addPoint((int)Math.round(screenWidth/2 + (k * x[0]) - (squareSize * 4 * y[0])),
							(int)Math.round(screenHeight/2 - (squareSize * 4 * y[1]) + (k * x[1]) + ((j+squareSize * 2) * z)));
					g.fillPolygon(p1);
					g.fillPolygon(p2);
				}
			}
			
			for (int k = -squareSize * 4; k < squareSize * 5; k += squareSize * 5) {
				g.drawLine((int)Math.round(screenWidth/2 + (k * x[0]) - (squareSize * 4 * y[0])), 
						(int)Math.round(screenHeight/2 - (squareSize * 4 * y[1]) + (k * x[1]) + (squareSize * 4 * z)), 
						(int)Math.round(screenWidth/2 + (k * x[0]) - (squareSize * 4 * y[0])), 
						(int)Math.round(screenHeight/2 - (squareSize * 4 * y[1]) + (k * x[1]) - (squareSize * 4 * z))); // Draw other back vertical board lines.
				g.drawLine((int)Math.round(screenWidth/2 - (squareSize * 4 * x[0]) - (squareSize * 4 * y[0])), 
						(int)Math.round(screenHeight/2 - (squareSize * 4 * y[1]) - (squareSize * 4 * x[1]) + (k * z)), 
						(int)Math.round(screenWidth/2 + (squareSize * 4 * x[0]) - (squareSize * 4 * y[0])), 
						(int)Math.round(screenHeight/2 - (squareSize * 4 * y[1]) + (squareSize * 4 * x[1]) + (k * z))); // Draw other right horizontal board lines.
			}
		}
		
		game.drawPieces(pieceChoice, g, viewAngle, viewElevation, squareSize, screenWidth, screenHeight);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int c = e.getKeyCode();
		
		if (!titleScreen && !escapeScreen) {
			if (c == KeyEvent.VK_LEFT) {
				turning = 1;
			}
			if (c == KeyEvent.VK_RIGHT) {
				turning = -1;
			}
			if (c == KeyEvent.VK_UP) {
				elevate = 1;
			}
			if (c == KeyEvent.VK_DOWN) {
				elevate = -1;
			}
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
		int c = e.getKeyCode();
		if (c == KeyEvent.VK_LEFT || c == KeyEvent.VK_RIGHT) {
			turning = 0;
		}
		if (c == KeyEvent.VK_UP || c == KeyEvent.VK_DOWN) {
			elevate = 0;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		xpos = e.getXOnScreen();
		ypos = e.getYOnScreen();
		
		if (!titleScreen && !escapeScreen) {
			double[] x = {Math.cos(viewAngle), Math.sin(viewAngle) * Math.sin(viewElevation)};
			double[] y = {Math.sin(viewAngle), - (Math.cos(viewAngle) * Math.sin(viewElevation))};
			double z = - Math.cos(viewElevation);
			double[] layer = {Math.sin(viewAngle) * Math.cos(viewElevation), 
					- (Math.cos(viewAngle) * Math.cos(viewElevation)), Math.sin(viewElevation)};
			Piece closestClickedPiece = null;
			double closestPoint = -50000;
			
			if (record.size() != 3 && game.toPromote == null) {
				for (int k = 0; k < 8; k++) {
					for (int j = 0; j < 8; j++) {
						for (int m = 0; m < 8; m++) {
							if (game.square[k][j][m].pt != PieceType.EMPTY) {
								double xfact = squareSize * (game.square[k][j][m].location[1] - 3.5);
								double yfact = squareSize * (game.square[k][j][m].location[2] - 3.5);
								double zfact = squareSize * (game.square[k][j][m].location[0] - 3.5);
								if ((game.square[k][j][m].pt != PieceType.EMPTY && game.square[k][j][m].pt != PieceType.PAWN && 
										game.square[k][j][m].pt != PieceType.BARRICADE) || 
										(game.square[k][j][m].pt == PieceType.PAWN && hidePawns == false) || 
										(game.square[k][j][m].pt == PieceType.BARRICADE && hideBarricades == false)) {
									if (xpos >= (int)Math.round(screenWidth/2 - 25 + (xfact * x[0]) + (yfact * y[0])) && 
											xpos <= (int)Math.round(screenWidth/2 + 25 + (xfact * x[0]) + (yfact * y[0])) && 
											ypos >= (int)Math.round(screenHeight/2 - 20 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)) && 
											ypos <= (int)Math.round(screenHeight/2 + 30 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z))) {
										if ((closestPoint < (xfact * layer[0]) + (yfact * layer[1]) + (zfact * layer[2]))) {
											closestPoint = (xfact * layer[0]) + (yfact * layer[1]) + (zfact * layer[2]);
											closestClickedPiece = game.square[k][j][m];
										}
									}
								}
							}
						}
					}
				}
			}
			
			if (record.size() == 3 && game.toPromote == null) {
				for (int k = 0; k < 8; k++) {
					for (int j = 0; j < 8; j++) {
						for (int m = 0; m < 8; m++) {
							double xfact = squareSize * (game.square[k][j][m].location[1] - 3.5);
							double yfact = squareSize * (game.square[k][j][m].location[2] - 3.5);
							double zfact = squareSize * (game.square[k][j][m].location[0] - 3.5);
							if (game.square[k][j][m].targeted) {
								if (xpos >= (int)Math.round(screenWidth/2 - 25 + (xfact * x[0]) + (yfact * y[0])) && 
										xpos <= (int)Math.round(screenWidth/2 + 25 + (xfact * x[0]) + (yfact * y[0])) && 
										ypos >= (int)Math.round(screenHeight/2 - 20 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)) && 
										ypos <= (int)Math.round(screenHeight/2 + 30 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z))) {
									if ((closestPoint < (xfact * layer[0]) + (yfact * layer[1]) + (zfact * layer[2]))) {
										closestPoint = (xfact * layer[0]) + (yfact * layer[1]) + (zfact * layer[2]);
										closestClickedPiece = game.square[k][j][m];
									}
								}
							}
						}
					}
				}
			}
			if (closestClickedPiece != null && game.toPromote == null) {
				
				if (record.size() != 3) {
					
					for (int k = 0; k < 8; k++)
						for (int j = 0; j < 8; j++)
							for (int m = 0; m < 8; m++)
								game.square[k][j][m].highlighted = false;
					game.detarget();
					closestClickedPiece.highlighted = true;
					game.target(closestClickedPiece);
					record = new ArrayList<Integer>();
					record.add(closestClickedPiece.location[0]);
					record.add(closestClickedPiece.location[1]);
					record.add(closestClickedPiece.location[2]);
				}
				
				else if (record.size() == 3 && game.toPromote == null) {
					for (int k = 0; k < 8; k++)
						for (int j = 0; j < 8; j++)
							for (int m = 0; m < 8; m++)
								game.square[k][j][m].highlighted = false;
					game.detarget();
					record.add(closestClickedPiece.location[0] - record.get(0));
					record.add(closestClickedPiece.location[1] - record.get(1));
					record.add(closestClickedPiece.location[2] - record.get(2));
				}
				
			}
			else {
				for (int k = 0; k < 8; k++)
					for (int j = 0; j < 8; j++)
						for (int m = 0; m < 8; m++)
							game.square[k][j][m].highlighted = false;
				game.detarget();
				record = new ArrayList<Integer>();
			}
			if (game.toPromote != null) {
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
				elevate = 0;
				turning = 0;
				drawError = 0;
				inputNeg = 1;
				checkmate = false;
				draw = false;
				pieceChoice = PieceType.QUEEN;
				hidePawns = false;
				hideBarricades = false;
				record = new ArrayList<Integer>();
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
				elevate = 0;
				turning = 0;
				drawError = 0;
				inputNeg = 1;
				checkmate = false;
				draw = false;
				pieceChoice = PieceType.QUEEN;
				hidePawns = false;
				hideBarricades = false;
				record = new ArrayList<Integer>();
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
