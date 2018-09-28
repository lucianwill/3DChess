import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Polygon;
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
	boolean checkmate = false;
	boolean draw = false;
	boolean titleScreen = true;
	boolean escapeScreen = false;
	Player winner;
	PieceType pieceChoice = PieceType.QUEEN;
	public static boolean hidePawns = false;
	public static boolean hideBarricades = false;
	ArrayList<Integer> record;
	
	Timer tm = new Timer(5, this);
	ThreeDimBoard game;
	
	public void paintComponent(Graphics g) {
		
		Font f = new Font("Gameover", Font.BOLD, 100);
		
		Font f2 = new Font("TitleScreen", Font.ITALIC, 200);
		
		Font f3 = new Font("Buttons", Font.PLAIN, 30);
		
		Font f4 = new Font("Coming Soon", Font.BOLD, 40);
		
		if (titleScreen) {
			super.paintComponent(g);
			g.setColor(new Color(100,0,0));
			g.fillRect(0, 0, 1920, 1080);
			
			g.setColor(new Color(20,20,0));
			
			g.fillRect(555, 395, 200, 100);
			g.fillRect(1155, 395, 300, 100);
			g.fillRect(855, 395, 200, 100);
			g.fillRect(855, 595, 200, 100);
			
			g.setColor(new Color(50,50,100));
			
			g.fillRect(560, 400, 200, 100);
			g.fillRect(1160, 400, 300, 100);
			g.fillRect(860, 400, 200, 100);
			g.fillRect(860, 600, 200, 100);
			
			g.setColor(new Color(20,20,0));
			
			g.setFont(f3);
			
			g.drawString("2-Player", 578, 458);
			g.drawString("4-Player", 878, 458);
			g.drawString("2-Player Advanced", 1178, 458);
			g.drawString("Quit", 878, 658);
			
			g.setFont(f2);
			
			g.setColor(new Color(20,20,0));
			
			g.drawString("3D Chess", 545, 195);
			
			g.setColor(Color.WHITE);
			
			g.drawString("3D Chess", 550, 200);
			
			g.setFont(f3);
			
			g.drawString("2-Player", 580, 460);
			g.drawString("4-Player", 880, 460);
			g.drawString("2-Player Advanced", 1180, 460);
			g.drawString("Quit", 880, 660);
			
			g.setColor(new Color(20,20,0));
			
			Polygon p1 = new Polygon();
			p1.addPoint(785, 385);
			p1.addPoint(805, 345);
			p1.addPoint(955, 420);
			p1.addPoint(1105, 345);
			p1.addPoint(1125, 385);
			p1.addPoint(1005, 445);
			p1.addPoint(1125, 505);
			p1.addPoint(1105, 545);
			p1.addPoint(955, 470);
			p1.addPoint(805, 545);
			p1.addPoint(785, 505);
			p1.addPoint(905, 445);
			g.fillPolygon(p1);
			p1 = new Polygon();
			p1.addPoint(1135, 385);
			p1.addPoint(1155, 345);
			p1.addPoint(1305, 420);
			p1.addPoint(1455, 345);
			p1.addPoint(1475, 385);
			p1.addPoint(1355, 445);
			p1.addPoint(1475, 505);
			p1.addPoint(1455, 545);
			p1.addPoint(1305, 470);
			p1.addPoint(1155, 545);
			p1.addPoint(1135, 505);
			p1.addPoint(1255, 445);
			g.fillPolygon(p1);
			
			g.setColor(new Color(150,50,0));
			
			p1 = new Polygon();
			p1.addPoint(790, 390);
			p1.addPoint(810, 350);
			p1.addPoint(960, 425);
			p1.addPoint(1110, 350);
			p1.addPoint(1130, 390);
			p1.addPoint(1010, 450);
			p1.addPoint(1130, 510);
			p1.addPoint(1110, 550);
			p1.addPoint(960, 475);
			p1.addPoint(810, 550);
			p1.addPoint(790, 510);
			p1.addPoint(910, 450);
			g.fillPolygon(p1);
			p1 = new Polygon();
			p1.addPoint(1140, 390);
			p1.addPoint(1160, 350);
			p1.addPoint(1310, 425);
			p1.addPoint(1460, 350);
			p1.addPoint(1480, 390);
			p1.addPoint(1360, 450);
			p1.addPoint(1480, 510);
			p1.addPoint(1460, 550);
			p1.addPoint(1310, 475);
			p1.addPoint(1160, 550);
			p1.addPoint(1140, 510);
			p1.addPoint(1260, 450);
			g.fillPolygon(p1);
			
			g.setColor(new Color(20,20,0));
			
			g.setFont(f4);
			
			g.drawString("Coming Soon", 828, 458);
			g.drawString("Coming Soon", 1178, 458);
			
			g.setColor(new Color(250,60,20));
			
			g.drawString("Coming Soon", 830, 460);
			g.drawString("Coming Soon", 1180, 460);
		}
		else if (escapeScreen) {
			g.setColor(new Color(80,80,40));
			g.setFont(f3);
			
			g.fillRect(0, 0, 1920, 1080);
			
			g.setColor(new Color(20,20,0));
			
			g.fillRect(855, 195, 200, 100);
			g.fillRect(855, 395, 200, 100);
			g.fillRect(855, 595, 200, 100);
			
			g.setColor(new Color(200,200,160));
			
			g.fillRect(860, 200, 200, 100);
			g.fillRect(860, 400, 200, 100);
			g.fillRect(860, 600, 200, 100);
			
			g.setColor(new Color(20,20,0));
			
			g.drawString("Resume", 878, 258);
			g.drawString("Back to Menu", 868, 458);
			g.drawString("Quit", 878, 658);
			
			g.setColor(Color.RED);
			
			g.drawString("Resume", 880, 260);
			g.drawString("Back to Menu", 870, 460);
			g.drawString("Quit", 880, 660);
		}
		else {
			super.paintComponent(g);
			g.setColor(new Color(100,0,0));
			g.fillRect(0, 0, 1920, 1080);
			g.setColor(Color.BLACK);
			
			g.setFont(f);
			
			drawBoard(g);
			if (checkmate == true) {
				if (winner == Player.WHITE) {
					g.setColor(Color.WHITE);
					g.drawString("White Wins", 700, 480);
				}
				if (winner == Player.BLACK) {
					g.setColor(Color.WHITE);
					g.drawString("Black Wins", 700, 480);
				}
			}
			if (draw == true) {
				g.setColor(Color.WHITE);
				g.drawString("Draw", 800, 480);
			}
			
			g.setColor(new Color(20,20,0));
			
			g.fillRect(1455, 395, 200, 100);
			g.fillRect(1455, 595, 200, 100);
			g.fillRect(1455, 795, 200, 100);
			
			g.setColor(new Color(50,50,100));
			
			g.fillRect(1460, 400, 200, 100);
			g.fillRect(1460, 600, 200, 100);
			g.fillRect(1460, 800, 200, 100);
			
			g.setFont(f3);
			
			g.setColor(new Color(20,20,0));
			
			g.drawString("Restart", 1478, 458);
			g.drawString("Offer Draw", 1478, 658);
			g.drawString("Resign", 1478, 858);
			
			g.setColor(new Color(20,20,0));
			
			g.drawString("Restart", 1480, 460);
			g.drawString("Offer Draw", 1480, 660);
			g.drawString("Resign", 1480, 860);
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
		tm.start();
	}
	
	public static void main(String[] args){
		ThreeDimChessRunner p = new ThreeDimChessRunner();
		JFrame frame = new JFrame("3D Chess");	
		frame.setSize(1920,1080);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(p);
	}
	
	public void actionPerformed(ActionEvent e) {
		
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
					p1.addPoint((int)Math.round(960 + ((k-2) * 140 * x[0]) + ((((j-1) * 140)-70) * y[0])), 
							(int)Math.round(540 + ((((j-1) * 140)-70) * y[1]) + ((k-2) * 140 * x[1]) - (280 * z)));
					p1.addPoint((int)Math.round(960 + ((((k-2) * 140) + 70) * x[0]) + ((((j-1) * 140)-70) * y[0])), 
							(int)Math.round(540 + ((((j-1) * 140)-70) * y[1]) + ((((k-2) * 140) + 70) * x[1]) - (280 * z)));
					p1.addPoint((int)Math.round(960 + ((((k-2) * 140) + 70) * x[0]) + ((j-1) * 140 * y[0])), 
							(int)Math.round(540 + ((j-1) * 140 * y[1]) + ((((k-2) * 140) + 70) * x[1]) - (280 * z)));
					p1.addPoint((int)Math.round(960 + ((k-2) * 140 * x[0]) + ((j-1) * 140 * y[0])), 
							(int)Math.round(540 + ((j-1) * 140 * y[1]) + ((k-2) * 140 * x[1]) - (280 * z)));
					p2.addPoint((int)Math.round(960 + ((k-1) * 140 * x[0]) + ((((j-2) * 140) + 70) * y[0])), 
							(int)Math.round(540 + ((((j-2) * 140) + 70) * y[1]) + ((k-1) * 140 * x[1]) - (280 * z)));
					p2.addPoint((int)Math.round(960 + ((((k-1) * 140) - 70) * x[0]) + ((((j-2) * 140) + 70) * y[0])), 
							(int)Math.round(540 + ((((j-2) * 140) + 70) * y[1]) + ((((k-1) * 140) - 70) * x[1]) - (280 * z)));
					p2.addPoint((int)Math.round(960 + ((((k-1) * 140) - 70) * x[0]) + ((j-2) * 140 * y[0])), 
							(int)Math.round(540 + ((j-2) * 140 * y[1]) + ((((k-1) * 140) - 70) * x[1]) - (280 * z)));
					p2.addPoint((int)Math.round(960 + ((k-1) * 140 * x[0]) + ((j-2) * 140 * y[0])), 
							(int)Math.round(540 + ((j-2) * 140 * y[1]) + ((k-1) * 140 * x[1]) - (280 * z)));
					g.fillPolygon(p1);
					g.fillPolygon(p2);
					p1 = new Polygon();
					p2 = new Polygon();
					g.setColor(Color.BLACK);
					p1.addPoint((int)Math.round(960 + ((k-2) * 140 * x[0]) + ((j-2) * 140 * y[0])), 
							(int)Math.round(540 + ((j-2) * 140 * y[1]) + ((k-2) * 140 * x[1]) - (280 * z)));
					p1.addPoint((int)Math.round(960 + ((((k-2) * 140) + 70) * x[0]) + ((j-2) * 140 * y[0])), 
							(int)Math.round(540 + ((j-2) * 140 * y[1]) + ((((k-2) * 140) + 70) * x[1]) - (280 * z)));
					p1.addPoint((int)Math.round(960 + ((((k-2) * 140) + 70) * x[0]) + ((((j-2) * 140) + 70) * y[0])), 
							(int)Math.round(540 + ((((j-2) * 140) + 70) * y[1]) + ((((k-2) * 140) + 70) * x[1]) - (280 * z)));
					p1.addPoint((int)Math.round(960 + ((k-2) * 140 * x[0]) + ((((j-2) * 140) + 70) * y[0])), 
							(int)Math.round(540 + ((((j-2) * 140) + 70) * y[1]) + ((k-2) * 140 * x[1]) - (280 * z)));
					p2.addPoint((int)Math.round(960 + ((k-1) * 140 * x[0]) + ((j-1) * 140 * y[0])), 
							(int)Math.round(540 + ((j-1) * 140 * y[1]) + ((k-1) * 140 * x[1]) - (280 * z)));
					p2.addPoint((int)Math.round(960 + ((((k-1) * 140) - 70) * x[0]) + ((j-1) * 140 * y[0])), 
							(int)Math.round(540 + ((j-1) * 140 * y[1]) + ((((k-1) * 140) - 70) * x[1]) - (280 * z)));
					p2.addPoint((int)Math.round(960 + ((((k-1) * 140) - 70) * x[0]) + ((((j-1) * 140) - 70) * y[0])), 
							(int)Math.round(540 + ((((j-1) * 140) - 70) * y[1]) + ((((k-1) * 140) - 70) * x[1]) - (280 * z)));
					p2.addPoint((int)Math.round(960 + ((k-1) * 140 * x[0]) + ((((j-1) * 140) - 70) * y[0])), 
							(int)Math.round(540 + ((((j-1) * 140) - 70) * y[1]) + ((k-1) * 140 * x[1]) - (280 * z)));
					g.fillPolygon(p1);
					g.fillPolygon(p2);
				}
			}
			
			for (int k = -280; k < 350; k += 560) {
				g.drawLine((int)Math.round(960 + (k * x[0]) + (280 * y[0])), 
						(int)Math.round(540 + (280 * y[1]) + (k * x[1]) - (280 * z)), 
						(int)Math.round(960 + (k * x[0]) - (280 * y[0])), 
						(int)Math.round(540 - (280 * y[1]) + (k * x[1]) - (280 * z))); // Draw bottom length board lines.
				g.drawLine((int)Math.round(960 - (280 * x[0]) + (k * y[0])), 
						(int)Math.round(540 + (k * y[1]) - (280 * x[1]) - (280 * z)), 
						(int)Math.round(960 + (280 * x[0]) + (k * y[0])), 
						(int)Math.round(540 + (k * y[1]) + (280 * x[1]) - (280 * z))); // Draw bottom width board lines.
			}
		}
		
		else {
			for (int k = 0; k < 4; k++) {
				for (int j = 0; j < 4; j++) {
					Polygon p1 = new Polygon();
					Polygon p2 = new Polygon();
					g.setColor(Color.BLACK);
					p1.addPoint((int)Math.round(960 + ((k-2) * 140 * x[0]) + ((((j-1) * 140)-70) * y[0])), 
							(int)Math.round(540 + ((((j-1) * 140)-70) * y[1]) + ((k-2) * 140 * x[1]) + (280 * z)));
					p1.addPoint((int)Math.round(960 + ((((k-2) * 140) + 70) * x[0]) + ((((j-1) * 140)-70) * y[0])), 
							(int)Math.round(540 + ((((j-1) * 140)-70) * y[1]) + ((((k-2) * 140) + 70) * x[1]) + (280 * z)));
					p1.addPoint((int)Math.round(960 + ((((k-2) * 140) + 70) * x[0]) + ((j-1) * 140 * y[0])), 
							(int)Math.round(540 + ((j-1) * 140 * y[1]) + ((((k-2) * 140) + 70) * x[1]) + (280 * z)));
					p1.addPoint((int)Math.round(960 + ((k-2) * 140 * x[0]) + ((j-1) * 140 * y[0])), 
							(int)Math.round(540 + ((j-1) * 140 * y[1]) + ((k-2) * 140 * x[1]) + (280 * z)));
					p2.addPoint((int)Math.round(960 + ((k-1) * 140 * x[0]) + ((((j-2) * 140) + 70) * y[0])), 
							(int)Math.round(540 + ((((j-2) * 140) + 70) * y[1]) + ((k-1) * 140 * x[1]) + (280 * z)));
					p2.addPoint((int)Math.round(960 + ((((k-1) * 140) - 70) * x[0]) + ((((j-2) * 140) + 70) * y[0])), 
							(int)Math.round(540 + ((((j-2) * 140) + 70) * y[1]) + ((((k-1) * 140) - 70) * x[1]) + (280 * z)));
					p2.addPoint((int)Math.round(960 + ((((k-1) * 140) - 70) * x[0]) + ((j-2) * 140 * y[0])), 
							(int)Math.round(540 + ((j-2) * 140 * y[1]) + ((((k-1) * 140) - 70) * x[1]) + (280 * z)));
					p2.addPoint((int)Math.round(960 + ((k-1) * 140 * x[0]) + ((j-2) * 140 * y[0])), 
							(int)Math.round(540 + ((j-2) * 140 * y[1]) + ((k-1) * 140 * x[1]) + (280 * z)));
					g.fillPolygon(p1);
					g.fillPolygon(p2);
					p1 = new Polygon();
					p2 = new Polygon();
					g.setColor(new Color(160,160,120));
					p1.addPoint((int)Math.round(960 + ((k-2) * 140 * x[0]) + ((j-2) * 140 * y[0])), 
							(int)Math.round(540 + ((j-2) * 140 * y[1]) + ((k-2) * 140 * x[1]) + (280 * z)));
					p1.addPoint((int)Math.round(960 + ((((k-2) * 140) + 70) * x[0]) + ((j-2) * 140 * y[0])), 
							(int)Math.round(540 + ((j-2) * 140 * y[1]) + ((((k-2) * 140) + 70) * x[1]) + (280 * z)));
					p1.addPoint((int)Math.round(960 + ((((k-2) * 140) + 70) * x[0]) + ((((j-2) * 140) + 70) * y[0])), 
							(int)Math.round(540 + ((((j-2) * 140) + 70) * y[1]) + ((((k-2) * 140) + 70) * x[1]) + (280 * z)));
					p1.addPoint((int)Math.round(960 + ((k-2) * 140 * x[0]) + ((((j-2) * 140) + 70) * y[0])), 
							(int)Math.round(540 + ((((j-2) * 140) + 70) * y[1]) + ((k-2) * 140 * x[1]) + (280 * z)));
					p2.addPoint((int)Math.round(960 + ((k-1) * 140 * x[0]) + ((j-1) * 140 * y[0])), 
							(int)Math.round(540 + ((j-1) * 140 * y[1]) + ((k-1) * 140 * x[1]) + (280 * z)));
					p2.addPoint((int)Math.round(960 + ((((k-1) * 140) - 70) * x[0]) + ((j-1) * 140 * y[0])), 
							(int)Math.round(540 + ((j-1) * 140 * y[1]) + ((((k-1) * 140) - 70) * x[1]) + (280 * z)));
					p2.addPoint((int)Math.round(960 + ((((k-1) * 140) - 70) * x[0]) + ((((j-1) * 140) - 70) * y[0])), 
							(int)Math.round(540 + ((((j-1) * 140) - 70) * y[1]) + ((((k-1) * 140) - 70) * x[1]) + (280 * z)));
					p2.addPoint((int)Math.round(960 + ((k-1) * 140 * x[0]) + ((((j-1) * 140) - 70) * y[0])), 
							(int)Math.round(540 + ((((j-1) * 140) - 70) * y[1]) + ((k-1) * 140 * x[1]) + (280 * z)));
					g.fillPolygon(p1);
					g.fillPolygon(p2);
					g.setColor(Color.BLACK);
				}
			}
			
			for (int k = -280; k < 350; k += 560) {
				g.drawLine((int)Math.round(960 + (k * x[0]) + (280 * y[0])), 
						(int)Math.round(540 + (280 * y[1]) + (k * x[1]) + (280 * z)), 
						(int)Math.round(960 + (k * x[0]) - (280 * y[0])), 
						(int)Math.round(540 - (280 * y[1]) + (k * x[1]) + (280 * z))); // Draw top length board lines.
				g.drawLine((int)Math.round(960 - (280 * x[0]) + (k * y[0])), 
						(int)Math.round(540 + (k * y[1]) - (280 * x[1]) + (280 * z)), 
						(int)Math.round(960 + (280 * x[0]) + (k * y[0])), 
						(int)Math.round(540 + (k * y[1]) + (280 * x[1]) + (280 * z))); // Draw top width board lines.
			}
		}
		
		if (viewAngle < Math.PI) {
			for (int k = -280; k < 280; k+=140) {
				for (int j = -280; j < 280; j+=140) {
					Polygon p1 = new Polygon();
					Polygon p2 = new Polygon();
					g.setColor(Color.BLACK);
					p1.addPoint((int)Math.round(960 - (280 * x[0]) + (k * y[0])), 
							(int)Math.round(540 + (k * y[1]) - (280 * x[1]) + (j * z)));
					p1.addPoint((int)Math.round(960 - (280 * x[0]) + ((k+70) * y[0])), 
							(int)Math.round(540 + ((k+70) * y[1]) - (280 * x[1]) + (j * z)));
					p1.addPoint((int)Math.round(960 - (280 * x[0]) + ((k+70) * y[0])), 
							(int)Math.round(540 + ((k+70) * y[1]) - (280 * x[1]) + ((j+70) * z)));
					p1.addPoint((int)Math.round(960 - (280 * x[0]) + (k * y[0])), 
							(int)Math.round(540 + (k * y[1]) - (280 * x[1]) + ((j+70) * z)));
					p2.addPoint((int)Math.round(960 - (280 * x[0]) + ((k+70) * y[0])), 
							(int)Math.round(540 + ((k+70) * y[1]) - (280 * x[1]) + ((j+70) * z)));
					p2.addPoint((int)Math.round(960 - (280 * x[0]) + ((k+140) * y[0])), 
							(int)Math.round(540 + ((k+140) * y[1]) - (280 * x[1]) + ((j+70) * z)));
					p2.addPoint((int)Math.round(960 - (280 * x[0]) + ((k+140) * y[0])), 
							(int)Math.round(540 + ((k+140) * y[1]) - (280 * x[1]) + ((j+140) * z)));
					p2.addPoint((int)Math.round(960 - (280 * x[0]) + ((k+70) * y[0])), 
							(int)Math.round(540 + ((k+70) * y[1]) - (280 * x[1]) + ((j+140) * z)));
					g.fillPolygon(p1);
					g.fillPolygon(p2);
					p1 = new Polygon();
					p2 = new Polygon();
					g.setColor(new Color(160,160,120));
					p1.addPoint((int)Math.round(960 - (280 * x[0]) + ((k+70) * y[0])), 
							(int)Math.round(540 + ((k+70) * y[1]) - (280 * x[1]) + (j * z)));
					p1.addPoint((int)Math.round(960 - (280 * x[0]) + ((k+140) * y[0])), 
							(int)Math.round(540 + ((k+140) * y[1]) - (280 * x[1]) + (j * z)));
					p1.addPoint((int)Math.round(960 - (280 * x[0]) + ((k+140) * y[0])), 
							(int)Math.round(540 + ((k+140) * y[1]) - (280 * x[1]) + ((j+70) * z)));
					p1.addPoint((int)Math.round(960 - (280 * x[0]) + ((k+70) * y[0])), 
							(int)Math.round(540 + ((k+70) * y[1]) - (280 * x[1]) + ((j+70) * z)));
					p2.addPoint((int)Math.round(960 - (280 * x[0]) + (k * y[0])), 
							(int)Math.round(540 + (k * y[1]) - (280 * x[1]) + ((j+70) * z)));
					p2.addPoint((int)Math.round(960 - (280 * x[0]) + ((k+70) * y[0])), 
							(int)Math.round(540 + ((k+70) * y[1]) - (280 * x[1]) + ((j+70) * z)));
					p2.addPoint((int)Math.round(960 - (280 * x[0]) + ((k+70) * y[0])), 
							(int)Math.round(540 + ((k+70) * y[1]) - (280 * x[1]) + ((j+140) * z)));
					p2.addPoint((int)Math.round(960 - (280 * x[0]) + (k * y[0])), 
							(int)Math.round(540 + (k * y[1]) - (280 * x[1]) + ((j+140) * z)));
					g.fillPolygon(p1);
					g.fillPolygon(p2);
					g.setColor(Color.BLACK);
				}
			}
			
			for (int k = -280; k < 350; k += 560) {
				g.drawLine((int)Math.round(960 - (280 * x[0]) + (k * y[0])), 
						(int)Math.round(540 + (k * y[1]) - (280 * x[1]) + (280 * z)), 
						(int)Math.round(960 - (280 * x[0]) + (k * y[0])), 
						(int)Math.round(540 + (k * y[1]) - (280 * x[1]) - (280 * z))); // Draw back vertical board lines.
				g.drawLine((int)Math.round(960 - (280 * x[0]) + (280 * y[0])), 
						(int)Math.round(540 + (280 * y[1]) - (280 * x[1]) + (k * z)), 
						(int)Math.round(960 - (280 * x[0]) - (280 * y[0])), 
						(int)Math.round(540 - (280 * y[1]) - (280 * x[1]) + (k * z))); // Draw back horizontal board lines.
			}
		}
		
		else {
			for (int k = -280; k < 280; k+=140) {
				for (int j = -280; j < 280; j+=140) {
					Polygon p1 = new Polygon();
					Polygon p2 = new Polygon();
					g.setColor(new Color(160,160,120));
					p1.addPoint((int)Math.round(960 + (280 * x[0]) + (k * y[0])), 
							(int)Math.round(540 + (k * y[1]) + (280 * x[1]) + (j * z)));
					p1.addPoint((int)Math.round(960 + (280 * x[0]) + ((k+70) * y[0])), 
							(int)Math.round(540 + ((k+70) * y[1]) + (280 * x[1]) + (j * z)));
					p1.addPoint((int)Math.round(960 + (280 * x[0]) + ((k+70) * y[0])), 
							(int)Math.round(540 + ((k+70) * y[1]) + (280 * x[1]) + ((j+70) * z)));
					p1.addPoint((int)Math.round(960 + (280 * x[0]) + (k * y[0])), 
							(int)Math.round(540 + (k * y[1]) + (280 * x[1]) + ((j+70) * z)));
					p2.addPoint((int)Math.round(960 + (280 * x[0]) + ((k+70) * y[0])), 
							(int)Math.round(540 + ((k+70) * y[1]) + (280 * x[1]) + ((j+70) * z)));
					p2.addPoint((int)Math.round(960 + (280 * x[0]) + ((k+140) * y[0])), 
							(int)Math.round(540 + ((k+140) * y[1]) + (280 * x[1]) + ((j+70) * z)));
					p2.addPoint((int)Math.round(960 + (280 * x[0]) + ((k+140) * y[0])), 
							(int)Math.round(540 + ((k+140) * y[1]) + (280 * x[1]) + ((j+140) * z)));
					p2.addPoint((int)Math.round(960 + (280 * x[0]) + ((k+70) * y[0])), 
							(int)Math.round(540 + ((k+70) * y[1]) + (280 * x[1]) + ((j+140) * z)));
					g.fillPolygon(p1);
					g.fillPolygon(p2);
					p1 = new Polygon();
					p2 = new Polygon();
					g.setColor(Color.BLACK);
					p1.addPoint((int)Math.round(960 + (280 * x[0]) + ((k+70) * y[0])), 
							(int)Math.round(540 + ((k+70) * y[1]) + (280 * x[1]) + (j * z)));
					p1.addPoint((int)Math.round(960 + (280 * x[0]) + ((k+140) * y[0])), 
							(int)Math.round(540 + ((k+140) * y[1]) + (280 * x[1]) + (j * z)));
					p1.addPoint((int)Math.round(960 + (280 * x[0]) + ((k+140) * y[0])), 
							(int)Math.round(540 + ((k+140) * y[1]) + (280 * x[1]) + ((j+70) * z)));
					p1.addPoint((int)Math.round(960 + (280 * x[0]) + ((k+70) * y[0])), 
							(int)Math.round(540 + ((k+70) * y[1]) + (280 * x[1]) + ((j+70) * z)));
					p2.addPoint((int)Math.round(960 + (280 * x[0]) + (k * y[0])), 
							(int)Math.round(540 + (k * y[1]) + (280 * x[1]) + ((j+70) * z)));
					p2.addPoint((int)Math.round(960 + (280 * x[0]) + ((k+70) * y[0])), 
							(int)Math.round(540 + ((k+70) * y[1]) + (280 * x[1]) + ((j+70) * z)));
					p2.addPoint((int)Math.round(960 + (280 * x[0]) + ((k+70) * y[0])), 
							(int)Math.round(540 + ((k+70) * y[1]) + (280 * x[1]) + ((j+140) * z)));
					p2.addPoint((int)Math.round(960 + (280 * x[0]) + (k * y[0])), 
							(int)Math.round(540 + (k * y[1]) + (280 * x[1]) + ((j+140) * z)));
					g.fillPolygon(p1);
					g.fillPolygon(p2);
				}
			}
			
			for (int k = -280; k < 350; k += 560) {
				g.drawLine((int)Math.round(960 + (280 * x[0]) + (k * y[0])), 
						(int)Math.round(540 + (k * y[1]) + (280 * x[1]) + (280 * z)), 
						(int)Math.round(960 + (280 * x[0]) + (k * y[0])), 
						(int)Math.round(540 + (k * y[1]) + (280 * x[1]) - (280 * z))); // Draw back vertical board lines.
				g.drawLine((int)Math.round(960 + (280 * x[0]) + (280 * y[0])), 
						(int)Math.round(540 + (280 * y[1]) + (280 * x[1]) + (k * z)), 
						(int)Math.round(960 + (280 * x[0]) - (280 * y[0])), 
						(int)Math.round(540 - (280 * y[1]) + (280 * x[1]) + (k * z))); // Draw back horizontal board lines.
			}
		}
		
		if (viewAngle < Math.PI / 2 || viewAngle > 3 * Math.PI / 2) {
			for (int k = -280; k < 280; k+=140) {
				for (int j = -280; j < 280; j+=140) {
					Polygon p1 = new Polygon();
					Polygon p2 = new Polygon();
					g.setColor(new Color(160,160,120));
					p1.addPoint((int)Math.round(960 + (k * x[0]) + (280 * y[0])),
							(int)Math.round(540 + (280 * y[1]) + (k * x[1]) + (j * z)));
					p1.addPoint((int)Math.round(960 + ((k+70) * x[0]) + (280 * y[0])),
							(int)Math.round(540 + (280 * y[1]) + ((k+70) * x[1]) + (j * z)));
					p1.addPoint((int)Math.round(960 + ((k+70) * x[0]) + (280 * y[0])),
							(int)Math.round(540 + (280 * y[1]) + ((k+70) * x[1]) + ((j+70) * z)));
					p1.addPoint((int)Math.round(960 + (k * x[0]) + (280 * y[0])),
							(int)Math.round(540 + (280 * y[1]) + (k * x[1]) + ((j+70) * z)));
					p2.addPoint((int)Math.round(960 + ((k+70) * x[0]) + (280 * y[0])),
							(int)Math.round(540 + (280 * y[1]) + ((k+70) * x[1]) + ((j+70) * z)));
					p2.addPoint((int)Math.round(960 + ((k+140) * x[0]) + (280 * y[0])),
							(int)Math.round(540 + (280 * y[1]) + ((k+140) * x[1]) + ((j+70) * z)));
					p2.addPoint((int)Math.round(960 + ((k+140) * x[0]) + (280 * y[0])),
							(int)Math.round(540 + (280 * y[1]) + ((k+140) * x[1]) + ((j+140) * z)));
					p2.addPoint((int)Math.round(960 + ((k+70) * x[0]) + (280 * y[0])),
							(int)Math.round(540 + (280 * y[1]) + ((k+70) * x[1]) + ((j+140) * z)));
					g.fillPolygon(p1);
					g.fillPolygon(p2);
					p1 = new Polygon();
					p2 = new Polygon();
					g.setColor(Color.BLACK);
					p1.addPoint((int)Math.round(960 + ((k+70) * x[0]) + (280 * y[0])),
							(int)Math.round(540 + (280 * y[1]) + ((k+70) * x[1]) + (j * z)));
					p1.addPoint((int)Math.round(960 + ((k+140) * x[0]) + (280 * y[0])),
							(int)Math.round(540 + (280 * y[1]) + ((k+140) * x[1]) + (j * z)));
					p1.addPoint((int)Math.round(960 + ((k+140) * x[0]) + (280 * y[0])),
							(int)Math.round(540 + (280 * y[1]) + ((k+140) * x[1]) + ((j+70) * z)));
					p1.addPoint((int)Math.round(960 + ((k+70) * x[0]) + (280 * y[0])),
							(int)Math.round(540 + (280 * y[1]) + ((k+70) * x[1]) + ((j+70) * z)));
					p2.addPoint((int)Math.round(960 + (k * x[0]) + (280 * y[0])),
							(int)Math.round(540 + (280 * y[1]) + (k * x[1]) + ((j+70) * z)));
					p2.addPoint((int)Math.round(960 + ((k+70) * x[0]) + (280 * y[0])),
							(int)Math.round(540 + (280 * y[1]) + ((k+70) * x[1]) + ((j+70) * z)));
					p2.addPoint((int)Math.round(960 + ((k+70) * x[0]) + (280 * y[0])),
							(int)Math.round(540 + (280 * y[1]) + ((k+70) * x[1]) + ((j+140) * z)));
					p2.addPoint((int)Math.round(960 + (k * x[0]) + (280 * y[0])),
							(int)Math.round(540 + (280 * y[1]) + (k * x[1]) + ((j+140) * z)));
					g.fillPolygon(p1);
					g.fillPolygon(p2);
				}
			}
			
			for (int k = -280; k < 350; k += 560) {
				g.drawLine((int)Math.round(960 + (k * x[0]) + (280 * y[0])), 
						(int)Math.round(540 + (280 * y[1]) + (k * x[1]) + (280 * z)), 
						(int)Math.round(960 + (k * x[0]) + (280 * y[0])), 
						(int)Math.round(540 + (280 * y[1]) + (k * x[1]) - (280 * z))); // Draw other back vertical board lines.
				g.drawLine((int)Math.round(960 - (280 * x[0]) + (280 * y[0])), 
						(int)Math.round(540 + (280 * y[1]) - (280 * x[1]) + (k * z)), 
						(int)Math.round(960 + (280 * x[0]) + (280 * y[0])), 
						(int)Math.round(540 + (280 * y[1]) + (280 * x[1]) + (k * z))); // Draw other back horizontal board lines.
			}
		}
		
		else {
			for (int k = -280; k < 280; k+=140) {
				for (int j = -280; j < 280; j+=140) {
					Polygon p1 = new Polygon();
					Polygon p2 = new Polygon();
					g.setColor(new Color(160,160,120));
					p1.addPoint((int)Math.round(960 + (k * x[0]) - (280 * y[0])),
							(int)Math.round(540 - (280 * y[1]) + (k * x[1]) + (j * z)));
					p1.addPoint((int)Math.round(960 + ((k+70) * x[0]) - (280 * y[0])),
							(int)Math.round(540 - (280 * y[1]) + ((k+70) * x[1]) + (j * z)));
					p1.addPoint((int)Math.round(960 + ((k+70) * x[0]) - (280 * y[0])),
							(int)Math.round(540 - (280 * y[1]) + ((k+70) * x[1]) + ((j+70) * z)));
					p1.addPoint((int)Math.round(960 + (k * x[0]) - (280 * y[0])),
							(int)Math.round(540 - (280 * y[1]) + (k * x[1]) + ((j+70) * z)));
					p2.addPoint((int)Math.round(960 + ((k+70) * x[0]) - (280 * y[0])),
							(int)Math.round(540 - (280 * y[1]) + ((k+70) * x[1]) + ((j+70) * z)));
					p2.addPoint((int)Math.round(960 + ((k+140) * x[0]) - (280 * y[0])),
							(int)Math.round(540 - (280 * y[1]) + ((k+140) * x[1]) + ((j+70) * z)));
					p2.addPoint((int)Math.round(960 + ((k+140) * x[0]) - (280 * y[0])),
							(int)Math.round(540 - (280 * y[1]) + ((k+140) * x[1]) + ((j+140) * z)));
					p2.addPoint((int)Math.round(960 + ((k+70) * x[0]) - (280 * y[0])),
							(int)Math.round(540 - (280 * y[1]) + ((k+70) * x[1]) + ((j+140) * z)));
					g.fillPolygon(p1);
					g.fillPolygon(p2);
					p1 = new Polygon();
					p2 = new Polygon();
					g.setColor(Color.BLACK);
					p1.addPoint((int)Math.round(960 + ((k+70) * x[0]) - (280 * y[0])),
							(int)Math.round(540 - (280 * y[1]) + ((k+70) * x[1]) + (j * z)));
					p1.addPoint((int)Math.round(960 + ((k+140) * x[0]) - (280 * y[0])),
							(int)Math.round(540 - (280 * y[1]) + ((k+140) * x[1]) + (j * z)));
					p1.addPoint((int)Math.round(960 + ((k+140) * x[0]) - (280 * y[0])),
							(int)Math.round(540 - (280 * y[1]) + ((k+140) * x[1]) + ((j+70) * z)));
					p1.addPoint((int)Math.round(960 + ((k+70) * x[0]) - (280 * y[0])),
							(int)Math.round(540 - (280 * y[1]) + ((k+70) * x[1]) + ((j+70) * z)));
					p2.addPoint((int)Math.round(960 + (k * x[0]) - (280 * y[0])),
							(int)Math.round(540 - (280 * y[1]) + (k * x[1]) + ((j+70) * z)));
					p2.addPoint((int)Math.round(960 + ((k+70) * x[0]) - (280 * y[0])),
							(int)Math.round(540 - (280 * y[1]) + ((k+70) * x[1]) + ((j+70) * z)));
					p2.addPoint((int)Math.round(960 + ((k+70) * x[0]) - (280 * y[0])),
							(int)Math.round(540 - (280 * y[1]) + ((k+70) * x[1]) + ((j+140) * z)));
					p2.addPoint((int)Math.round(960 + (k * x[0]) - (280 * y[0])),
							(int)Math.round(540 - (280 * y[1]) + (k * x[1]) + ((j+140) * z)));
					g.fillPolygon(p1);
					g.fillPolygon(p2);
				}
			}
			
			for (int k = -280; k < 350; k += 560) {
				g.drawLine((int)Math.round(960 + (k * x[0]) - (280 * y[0])), 
						(int)Math.round(540 - (280 * y[1]) + (k * x[1]) + (280 * z)), 
						(int)Math.round(960 + (k * x[0]) - (280 * y[0])), 
						(int)Math.round(540 - (280 * y[1]) + (k * x[1]) - (280 * z))); // Draw other back vertical board lines.
				g.drawLine((int)Math.round(960 - (280 * x[0]) - (280 * y[0])), 
						(int)Math.round(540 - (280 * y[1]) - (280 * x[1]) + (k * z)), 
						(int)Math.round(960 + (280 * x[0]) - (280 * y[0])), 
						(int)Math.round(540 - (280 * y[1]) + (280 * x[1]) + (k * z))); // Draw other right horizontal board lines.
			}
		}
		
		game.drawPieces(pieceChoice, g, viewAngle, viewElevation);
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
			if (c == KeyEvent.VK_ESCAPE && !titleScreen) {
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
		
		int xpos = e.getXOnScreen();
		int ypos = e.getYOnScreen();
		
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
								double xfact = 70 * (game.square[k][j][m].location[1] - 3.5);
								double yfact = 70 * (game.square[k][j][m].location[2] - 3.5);
								double zfact = 70 * (game.square[k][j][m].location[0] - 3.5);
								if ((game.square[k][j][m].pt != PieceType.EMPTY && game.square[k][j][m].pt != PieceType.PAWN && 
										game.square[k][j][m].pt != PieceType.BARRICADE) || 
										(game.square[k][j][m].pt == PieceType.PAWN && hidePawns == false) || 
										(game.square[k][j][m].pt == PieceType.BARRICADE && hideBarricades == false)) {
									if (xpos >= (int)Math.round(935 + (xfact * x[0]) + (yfact * y[0])) && 
											xpos <= (int)Math.round(985 + (xfact * x[0]) + (yfact * y[0])) && 
											ypos >= (int)Math.round(560 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)) && 
											ypos <= (int)Math.round(600 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z))) {
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
							double xfact = 70 * (game.square[k][j][m].location[1] - 3.5);
							double yfact = 70 * (game.square[k][j][m].location[2] - 3.5);
							double zfact = 70 * (game.square[k][j][m].location[0] - 3.5);
							if (game.square[k][j][m].targeted) {
								if (xpos >= (int)Math.round(935 + (xfact * x[0]) + (yfact * y[0])) && 
										xpos <= (int)Math.round(985 + (xfact * x[0]) + (yfact * y[0])) && 
										ypos >= (int)Math.round(560 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)) && 
										ypos <= (int)Math.round(600 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z))) {
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
				double xfact = 70 * (game.toPromote.location[1] - 3.5);
				double yfact = 70 * (game.toPromote.location[2] - 3.5);
				double zfact = 70 * (game.toPromote.location[0] - 3.5);
				if (xpos >= (int)Math.round(935 + (xfact * x[0]) + (yfact * y[0])) && 
						xpos <= (int)Math.round(985 + (xfact * x[0]) + (yfact * y[0])) && 
						ypos >= (int)Math.round(560 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)) && 
						ypos <= (int)Math.round(600 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z))) {
					game.promote(pieceChoice);
					pieceChoice = PieceType.QUEEN;
				}
			}
			
			if (xpos >= 1460 && xpos <= 1660 && 
					ypos >= 400 && ypos <= 500 && 
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
			
			if (xpos >= 1460 && xpos <= 1660 && 
					ypos >= 600 && ypos <= 700 && 
					!checkmate)
				draw = true;
			
			if (xpos >= 1460 && xpos <= 1660 && 
					ypos >= 800 && ypos <= 900 && !draw) {
				checkmate = true;
				if (game.turn == 0)
					winner = Player.BLACK;
				else
					winner = Player.WHITE;
			}
		}
		else if (titleScreen == true) {
			
			if (xpos >= 560 && xpos <= 760 && 
					ypos >= 400 && ypos <= 500) {
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
			
			if (xpos >= 860 && xpos <= 1060 && 
					ypos >= 600 && ypos <= 700)
				System.exit(0);
			
		}
		else if (escapeScreen == true) {
			
			if (xpos >= 860 && xpos <= 1060 && 
					ypos >= 200 && ypos <= 300)
				escapeScreen = false;
			
			if (xpos >= 860 && xpos <= 1060 && 
					ypos >= 400 && ypos <= 500) {
				titleScreen = true;
				escapeScreen = false;
			}
			
			if (xpos >= 860 && xpos <= 1060 && 
					ypos >= 600 && ypos <= 700)
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
