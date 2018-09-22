import java.awt.Color;
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
	int turn = 0;
	int drawError = 0;
	int inputNeg = 1;
	public static boolean hidePawns = false;
	public static boolean hideBarricades = false;
	ArrayList<Integer> record;
	
	Timer tm = new Timer(5, this);
	ThreeDimBoard game;
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(100,0,0));
		g.fillRect(0, 0, 1920, 1080);
		g.setColor(Color.BLACK);
		
		if(drawError > 0)
			g.drawString("Invalid Move", 200, 300);
		
		String temp = new String();
		
		if(record.size() > 0) {
			temp = temp + Integer.toString(record.get(0)+1);
			if(record.size() > 1) {
				temp = temp + ", " + Integer.toString(record.get(1)+1);
				if(record.size() > 2) {
					temp = temp + ", " + Integer.toString(record.get(2)+1);
					if(record.size() > 3) {
						temp = temp + " : " + Integer.toString(record.get(3));
						if(record.size() > 4) {
							temp = temp + ", " + Integer.toString(record.get(4));
							if(record.size() > 5) {
								temp = temp + ", " + Integer.toString(record.get(5));
							}
						}
					}
				}
			}
		}
		g.drawString(temp, 200, 280);
		
		drawBoard(g);
	}
	
	public ThreeDimChessRunner() {
		record = new ArrayList<Integer>();
		game = new ThreeDimBoard(0);
		tm.start();
		addMouseListener(this);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}
	
	public static void main(String[] args){
		ThreeDimChessRunner p = new ThreeDimChessRunner();
		JFrame frame= new JFrame("3D Chess");	
		frame.setSize(1920, 1080);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(p);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(drawError > 0)
			drawError--;
		if(record.size() == 6) {
			int[] t = new int[3];
			int[] v = new int[3];
			for(int k = 0; k < 3; k++) {
				t[k] = record.get(k);
				v[k] = record.get(k+3);
			}
			if(game.moveValid(t, v)) {
				game.move(t, v);
				game.detarget();
				game.square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].highlighted = false;
			}
			else {
				drawError = 100;
				
			}
			record = new ArrayList<Integer>();
		}
		viewAngle += (double)turn * tm.getDelay()/160;
		if (viewElevation + (double)elevate * tm.getDelay()/160 > Math.PI/2)
			viewElevation = Math.PI/2;
		else if (viewElevation + (double)elevate * tm.getDelay()/160 < -Math.PI/2)
			viewElevation = -Math.PI/2;
		else
			viewElevation += (double)elevate * tm.getDelay()/160;
		if(viewAngle >= 2 * Math.PI)
			viewAngle -= 2 * Math.PI;
        if(viewAngle < 0)
        	viewAngle += 2 * Math.PI;

        repaint();
    }
	
	public void drawBoard(Graphics g) {
		double[] x = {Math.cos(viewAngle), Math.sin(viewAngle) * Math.sin(viewElevation)};
		double[] y = {Math.sin(viewAngle), - (Math.cos(viewAngle) * Math.sin(viewElevation))};
		double z = - Math.cos(viewElevation);
		
		g.setColor(Color.BLACK);
		
		if(viewElevation >= 0) {
			for(int k = 0; k < 4; k++) {
				for(int j = 0; j < 4; j++) {
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
			
			for(int k = -280; k < 350; k += 560) {
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
			for(int k = 0; k < 4; k++) {
				for(int j = 0; j < 4; j++) {
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
			
			for(int k = -280; k < 350; k += 560) {
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
		
		if(viewAngle < Math.PI) {
			for(int k = -280; k < 280; k+=140) {
				for(int j = -280; j < 280; j+=140) {
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
			
			for(int k = -280; k < 350; k += 560) {
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
			for(int k = -280; k < 280; k+=140) {
				for(int j = -280; j < 280; j+=140) {
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
			
			for(int k = -280; k < 350; k += 560) {
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
		
		if(viewAngle < Math.PI / 2 || viewAngle > 3 * Math.PI / 2) {
			for(int k = -280; k < 280; k+=140) {
				for(int j = -280; j < 280; j+=140) {
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
			
			for(int k = -280; k < 350; k += 560) {
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
			for(int k = -280; k < 280; k+=140) {
				for(int j = -280; j < 280; j+=140) {
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
			
			for(int k = -280; k < 350; k += 560) {
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
		
		game.drawPieces(g, viewAngle, viewElevation);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int c = e.getKeyCode();
		if (c == KeyEvent.VK_LEFT) {
			turn = 1;
		}
		if (c == KeyEvent.VK_RIGHT) {
			turn = -1;
		}
		if (c == KeyEvent.VK_UP) {
			elevate = 1;
		}
		if (c == KeyEvent.VK_DOWN) {
			elevate = -1;
		}
		if (c == KeyEvent.VK_G) {
			if(hideBarricades == true)
				hideBarricades = false;
			else
				hideBarricades = true;
		}
		if (c == KeyEvent.VK_H) {
			if(hidePawns == true)
				hidePawns = false;
			else
				hidePawns = true;
		}
		if (c == KeyEvent.VK_MINUS) {
			inputNeg = -1;
		}
		if (c == KeyEvent.VK_0) {
			if(record.size() > 2)
				record.add(0);
		}
		if (c == KeyEvent.VK_1) {
			if(record.size() < 3)
				record.add(0);
			else
				record.add(1 * inputNeg);
			inputNeg = 1;
		}
		if (c == KeyEvent.VK_2) {
			if(record.size() < 3)
				record.add(1);
			else
				record.add(2 * inputNeg);
			inputNeg = 1;
		}
		if (c == KeyEvent.VK_3) {
			if(record.size() < 3)
				record.add(2);
			else
				record.add(3 * inputNeg);
			inputNeg = 1;
		}
		if (c == KeyEvent.VK_4) {
			if(record.size() < 3)
				record.add(3);
			else
				record.add(4 * inputNeg);
			inputNeg = 1;
		}
		if (c == KeyEvent.VK_5) {
			if(record.size() < 3)
				record.add(4);
			else
				record.add(5 * inputNeg);
			inputNeg = 1;
		}
		if (c == KeyEvent.VK_6) {
			if(record.size() < 3)
				record.add(5);
			else
				record.add(6 * inputNeg);
			inputNeg = 1;
		}
		if (c == KeyEvent.VK_7) {
			if(record.size() < 3)
				record.add(6);
			else
				record.add(7 * inputNeg);
			inputNeg = 1;
		}
		if (c == KeyEvent.VK_8) {
			if(record.size() < 3)
				record.add(7);
		}
		if (c == KeyEvent.VK_9) {
			record = new ArrayList<Integer>();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int c = e.getKeyCode();
		if (c == KeyEvent.VK_LEFT || c == KeyEvent.VK_RIGHT) {
			turn = 0;
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
		double[] x = {Math.cos(viewAngle), Math.sin(viewAngle) * Math.sin(viewElevation)};
		double[] y = {Math.sin(viewAngle), - (Math.cos(viewAngle) * Math.sin(viewElevation))};
		double z = - Math.cos(viewElevation);
		double[] layer = {Math.sin(viewAngle) * Math.cos(viewElevation), 
				- (Math.cos(viewAngle) * Math.cos(viewElevation)), Math.sin(viewElevation)};
		Piece closestClickedPiece = null;
		double closestPoint = -50000;
		
		if(record.size() != 3) {
			for(int k = 0; k < 8; k++) {
				for(int j = 0; j < 8; j++) {
					for(int m = 0; m < 8; m++) {
						if(game.square[k][j][m].piecetype != -1) {
							double xfact = 70 * (game.square[k][j][m].location[1] - 3.5);
							double yfact = 70 * (game.square[k][j][m].location[2] - 3.5);
							double zfact = 70 * (game.square[k][j][m].location[0] - 3.5);
							if((game.square[k][j][m].piecetype >= 0 && game.square[k][j][m].piecetype < 6) || 
									(game.square[k][j][m].piecetype == 6 && hidePawns == false) || 
									(game.square[k][j][m].piecetype == 7 && hideBarricades == false)) {
								if(xpos >= (int)Math.round(935 + (xfact * x[0]) + (yfact * y[0])) && 
										xpos <= (int)Math.round(985 + (xfact * x[0]) + (yfact * y[0])) && 
										ypos >= (int)Math.round(560 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)) && 
										ypos <= (int)Math.round(600 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z))) {
									if((closestPoint < (xfact * layer[0]) + (yfact * layer[1]) + (zfact * layer[2]))) {
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
		
		if(record.size() == 3) {
			for(int k = 0; k < 8; k++) {
				for(int j = 0; j < 8; j++) {
					for(int m = 0; m < 8; m++) {
						double xfact = 70 * (game.square[k][j][m].location[1] - 3.5);
						double yfact = 70 * (game.square[k][j][m].location[2] - 3.5);
						double zfact = 70 * (game.square[k][j][m].location[0] - 3.5);
						if(game.square[k][j][m].targeted) {
							if(xpos >= (int)Math.round(935 + (xfact * x[0]) + (yfact * y[0])) && 
									xpos <= (int)Math.round(985 + (xfact * x[0]) + (yfact * y[0])) && 
									ypos >= (int)Math.round(560 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)) && 
									ypos <= (int)Math.round(600 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z))) {
								if((closestPoint < (xfact * layer[0]) + (yfact * layer[1]) + (zfact * layer[2]))) {
									closestPoint = (xfact * layer[0]) + (yfact * layer[1]) + (zfact * layer[2]);
									closestClickedPiece = game.square[k][j][m];
								}
							}
						}
					}
				}
			}
		}
		if(closestClickedPiece != null) {
			
			if(record.size() != 3) {
				
				for(int k = 0; k < 8; k++)
					for(int j = 0; j < 8; j++)
						for(int m = 0; m < 8; m++)
							game.square[k][j][m].highlighted = false;
				game.detarget();
				closestClickedPiece.highlighted = true;
				game.target(closestClickedPiece);
				record = new ArrayList<Integer>();
				System.out.println(closestPoint + " " + closestClickedPiece.location[0]);
				record.add(closestClickedPiece.location[0]);
				record.add(closestClickedPiece.location[1]);
				record.add(closestClickedPiece.location[2]);
			}
			
			else if(record.size() == 3) {
				for(int k = 0; k < 8; k++)
					for(int j = 0; j < 8; j++)
						for(int m = 0; m < 8; m++)
							game.square[k][j][m].highlighted = false;
				game.detarget();
				System.out.println(closestPoint + " " + closestClickedPiece.location[0]);
				record.add(closestClickedPiece.location[0] - record.get(0));
				record.add(closestClickedPiece.location[1] - record.get(1));
				record.add(closestClickedPiece.location[2] - record.get(2));
			}
			
		}
		else {
			for(int k = 0; k < 8; k++)
				for(int j = 0; j < 8; j++)
					for(int m = 0; m < 8; m++)
						game.square[k][j][m].highlighted = false;
			game.detarget();
			record = new ArrayList<Integer>();
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
