import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class Piece {
	
	int[] location;
	Player p;
	PieceType pt;
	boolean inCheck;
	boolean captured;
	boolean highlighted;
	boolean targeted;
	boolean castleValid; // Can only be true for kings and rooks
	boolean enPassantValid; // Can only be true for pawns
	boolean twoStepsValid; // Can only be true for pawns
	
	public Piece() {
		
	}
	
	public Piece(PieceType pt, Player p, int z, int x, int y) {
		this.pt = pt;
		this.p = p;
		location = new int[3];
		location[0] = z;
		location[1] = x;
		location[2] = y;
		highlighted = false;
		captured = false;
		twoStepsValid = true;
		switch (pt) {
			case KING:
				castleValid = true;
				break;
			case ROOK:
				castleValid = true;
				break;
		default:
			castleValid = false;
			break;
		}
		
		enPassantValid = false;
		
		inCheck = false;
	}
	
	public void Draw(Graphics g, double viewAngle, double viewElevation, int squareSize, int screenWidth, int screenHeight) {
		double[] x = {Math.cos(viewAngle), Math.sin(viewAngle) * Math.sin(viewElevation)};
		double[] y = {Math.sin(viewAngle), - (Math.cos(viewAngle) * Math.sin(viewElevation))};
		double z = - Math.cos(viewElevation);
		double xfact = squareSize * (location[1] - 3.5);
		double yfact = squareSize * (location[2] - 3.5);
		double zfact = squareSize * (location[0] - 3.5);
		
		Color temp;
		
		Color white = new Color(225,230,160);
		Color black = new Color(40,20,20);
		Color red = new Color(225,10,10);
		Color blue = new Color(20,10,200);
		
		Color whiteH = new Color(245,250,160);
		Color blackH = new Color(60,40,20);
		Color redH = new Color(245,30,10);
		Color blueH = new Color(40,30,200);
		
		Color whiteC = new Color(225,190,120);
		Color blackC = new Color(80,20,20);
		Color redC = new Color(255,0,0);
		Color blueC = new Color(60,10,160);
		
		Color whiteHC = new Color(245,210,120);
		Color blackHC = new Color(100,40,20);
		Color redHC = new Color(255,40,40);
		Color blueHC = new Color(80,30,160);
		
		if (highlighted == false && inCheck == false) {
			switch (p) {
				case WHITE:
					g.setColor(white);
					break;
				case BLACK:
					g.setColor(black);
					break;
				case RED:
					g.setColor(red);
					break;
				case BLUE:
					g.setColor(blue);
					break;
			default:
				break;
			}
		}
		else if (highlighted == true && inCheck == false){
			switch (p) {
				case WHITE:
					g.setColor(whiteH);
					break;
				case BLACK:
					g.setColor(blackH);
					break;
				case RED:
					g.setColor(redH);
					break;
				case BLUE:
					g.setColor(blueH);
					break;
			default:
				break;
			}
		}
		else if (highlighted == false && inCheck == true){
			switch (p) {
				case WHITE:
					g.setColor(whiteC);
					break;
				case BLACK:
					g.setColor(blackC);
					break;
				case RED:
					g.setColor(redC);
					break;
				case BLUE:
					g.setColor(blueC);
					break;
			default:
				break;
			}
		}
		else if (highlighted == true && inCheck == true){
			switch (p) {
				case WHITE:
					g.setColor(whiteHC);
					break;
				case BLACK:
					g.setColor(blackHC);
					break;
				case RED:
					g.setColor(redHC);
					break;
				case BLUE:
					g.setColor(blueHC);
					break;
			default:
				break;
			}
		}
		temp = g.getColor();
		
		if (pt == PieceType.KING) {
			Polygon p1= new Polygon();
			
			if (p == Player.WHITE)
				g.setColor(new Color(20,20,0));
			if (p == Player.BLACK)
				g.setColor(new Color(240,240,180));
			
			p1.addPoint((int)Math.round(screenWidth/2 - 23 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 33 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 23 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 21 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 10 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 2 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 18 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 10 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 2 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 23 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 21 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 23 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 33 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			g.fillPolygon(p1);
			
			g.setColor(temp);
			
			p1= new Polygon();
			p1.addPoint((int)Math.round(screenWidth/2 - 20 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 30 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 20 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 10 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 10 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 10 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 10 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 10 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 10 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 20 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 10 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 20 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 30 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			g.fillPolygon(p1);
		}
		
		if (pt == PieceType.QUEEN) {
			Polygon p1= new Polygon();
			
			if (p == Player.WHITE)
				g.setColor(new Color(20,20,0));
			if (p == Player.BLACK)
				g.setColor(new Color(240,240,180));
			
			p1.addPoint((int)Math.round(screenWidth/2 - 18 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 33 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 32 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 15 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 13 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 2 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 13 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 20 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 13 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 20 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 13 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 2 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 32 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 15 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 18 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 33 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			g.fillPolygon(p1);
			
			g.setColor(temp);
			
			p1= new Polygon();
			p1.addPoint((int)Math.round(screenWidth/2 - 15 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 30 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 25 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 5 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 10 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 10 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 10 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 10 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 8 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 10 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 10 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 10 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 10 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 25 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 5 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 15 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 30 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			g.fillPolygon(p1);
		}
		
		if (pt == PieceType.PRINCE || pt == PieceType.PRINCESS) {
			Polygon p1= new Polygon();
			
			if (p == Player.WHITE)
				g.setColor(new Color(20,20,0));
			if (p == Player.BLACK)
				g.setColor(new Color(240,240,180));
			
			p1.addPoint((int)Math.round(screenWidth/2 - 13+ (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 33 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 13 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 7 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 5 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 13 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 7 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 13 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 33 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			g.fillPolygon(p1);
			
			g.setColor(temp);
			
			p1= new Polygon();
			p1.addPoint((int)Math.round(screenWidth/2 - 10 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 30 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 10 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 10 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 10 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 10 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 30 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			g.fillPolygon(p1);
		}
		
		if (pt == PieceType.BISHOP) {
			Polygon p1= new Polygon();
			
			if (p == Player.WHITE)
				g.setColor(new Color(20,20,0));
			if (p == Player.BLACK)
				g.setColor(new Color(240,240,180));
			
			p1.addPoint((int)Math.round(screenWidth/2 - 19 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 33 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 14 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 10 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 19 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 5 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 25 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 19 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 5 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 14 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 10 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 19 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 33 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			g.fillPolygon(p1);
			
			g.setColor(temp);
			
			p1= new Polygon();
			p1.addPoint((int)Math.round(screenWidth/2 - 15 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 30 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 10 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 10 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 15 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 5 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 20 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 15 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 5 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 10 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 10 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 15 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 30 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			g.fillPolygon(p1);
		}
		
		if (pt == PieceType.KNIGHT) {
			Polygon p1= new Polygon();
			
			if (p == Player.WHITE)
				g.setColor(new Color(20,20,0));
			if (p == Player.BLACK)
				g.setColor(new Color(240,240,180));
			
			p1.addPoint((int)Math.round(screenWidth/2 - 19 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 +33 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 12 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 8 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 31 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 6 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 33 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 7 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 22 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 19 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 5 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 14 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 10 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 19 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 33 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			g.fillPolygon(p1);
			
			g.setColor(temp);
			
			p1= new Polygon();
			p1.addPoint((int)Math.round(screenWidth/2 - 15 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 30 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 8 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 5 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 29 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 3 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 30 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 5 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 18 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 15 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 5 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 10 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 10 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 15 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 30 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			g.fillPolygon(p1);
		}
		
		if (pt == PieceType.ROOK) {
			Polygon p1= new Polygon();
			
			if (p == Player.WHITE)
				g.setColor(new Color(20,20,0));
			if (p == Player.BLACK)
				g.setColor(new Color(240,240,180));
			
			p1.addPoint((int)Math.round(screenWidth/2 - 19 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 33 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 14 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 13 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 18 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 13 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 18 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 8 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 18 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 8 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 18 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 13 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 14 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 13 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 19 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 33 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			g.fillPolygon(p1);
			
			g.setColor(temp);
			
			p1= new Polygon();
			p1.addPoint((int)Math.round(screenWidth/2 - 15 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 30 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 10 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 10 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 15 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 10 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 15 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 5 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 9 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 5 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 9 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 3 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 3 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 5 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 3 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 5 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 3 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 9 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 9 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 5 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 15 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 5 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 15 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 10 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 10 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 10 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 15 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 30 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			g.fillPolygon(p1);
		}
		
		if (pt == PieceType.PAWN && ThreeDimChessRunner.hidePawns == false) {
			Polygon p1= new Polygon();
			
			if (p == Player.WHITE)
				g.setColor(new Color(20,20,0));
			if (p == Player.BLACK)
				g.setColor(new Color(240,240,180));
			
			p1.addPoint((int)Math.round(screenWidth/2 - 14 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 33 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 12 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 15 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			g.fillOval((int)Math.round(screenWidth/2 - 13 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 3 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)), 26, 26);
			p1.addPoint((int)Math.round(screenWidth/2 + 12 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 15 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 14 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 33 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			g.fillPolygon(p1);
			
			g.setColor(temp);
			
			p1= new Polygon();
			p1.addPoint((int)Math.round(screenWidth/2 - 10 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 30 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 8 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 15 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			g.fillOval((int)Math.round(screenWidth/2 - 10 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)), 20, 20);
			p1.addPoint((int)Math.round(screenWidth/2 + 8 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 15 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 10 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 30 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			g.fillPolygon(p1);
		}
		
		if (pt == PieceType.BARRICADE && ThreeDimChessRunner.hideBarricades == false) {
			Polygon p1= new Polygon();
			
			if (p == Player.WHITE)
				g.setColor(new Color(20,20,0));
			if (p == Player.BLACK)
				g.setColor(new Color(240,240,180));
			
			p1.addPoint((int)Math.round(screenWidth/2 - 24 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 33 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 21 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 12 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 21 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 12 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 24 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 33 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			g.fillPolygon(p1);
			
			g.setColor(temp);
			
			p1= new Polygon();
			p1.addPoint((int)Math.round(screenWidth/2 - 20 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 30 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 - 18 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 15 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 18 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 15 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(screenWidth/2 + 20 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 + 30 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			g.fillPolygon(p1);
		}
		g.setColor(new Color(120, 120, 80));
		
		if (targeted) {
			g.fillOval((int)Math.round(screenWidth/2 - 20 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(screenHeight/2 - 20 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)), 40, 40);
		}
		
		g.setColor(Color.BLACK);
	}
}
