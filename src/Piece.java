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
				castleValid = false;
				break;
		default:
			break;
		}
		
		enPassantValid = false;
		
		inCheck = false;
	}
	
	public void Draw(Graphics g, double viewAngle, double viewElevation) {
		double[] x = {Math.cos(viewAngle), Math.sin(viewAngle) * Math.sin(viewElevation)};
		double[] y = {Math.sin(viewAngle), - (Math.cos(viewAngle) * Math.sin(viewElevation))};
		double z = - Math.cos(viewElevation);
		double xfact = 70 * (location[1] - 3.5);
		double yfact = 70 * (location[2] - 3.5);
		double zfact = 70 * (location[0] - 3.5);
		if (highlighted == false && inCheck == false) {
			switch (p) {
				case WHITE:
					g.setColor(new Color(225,230,160));
					break;
				case BLACK:
					g.setColor(new Color(40,20,20));
					break;
				case RED:
					g.setColor(new Color(225,10,10));
					break;
				case BLUE:
					g.setColor(new Color(20,10,200));
					break;
			default:
				break;
			}
		}
		else if (highlighted == true && inCheck == false){
			switch (p) {
				case WHITE:
					g.setColor(new Color(245,250,160));
					break;
				case BLACK:
					g.setColor(new Color(60,40,20));
					break;
				case RED:
					g.setColor(new Color(245,30,10));
					break;
				case BLUE:
					g.setColor(new Color(40,30,200));
					break;
			default:
				break;
			}
		}
		else if (highlighted == false && inCheck == true){
			switch (p) {
				case WHITE:
					g.setColor(new Color(225,190,120));
					break;
				case BLACK:
					g.setColor(new Color(80,20,20));
					break;
				case RED:
					g.setColor(new Color(255,0,0));
					break;
				case BLUE:
					g.setColor(new Color(60,10,160));
					break;
			default:
				break;
			}
		}
		else if (highlighted == true && inCheck == true){
			switch (p) {
				case WHITE:
					g.setColor(new Color(245,210,120));
					break;
				case BLACK:
					g.setColor(new Color(100,40,20));
					break;
				case RED:
					g.setColor(new Color(255,40,40));
					break;
				case BLUE:
					g.setColor(new Color(80,30,160));
					break;
			default:
				break;
			}
		}
		
		
		if (pt == PieceType.KING) {
			g.fillRect((int)Math.round(940 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(550 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)), 
					40, 20);
			Polygon p1= new Polygon();
			p1.addPoint((int)Math.round(940 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(550 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(940 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(530 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(950 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(550 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(960 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(530 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(970 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(550 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(980 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(530 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(980 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(550 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			g.fillPolygon(p1);
		}
		
		if (pt == PieceType.QUEEN) {
			Polygon p1= new Polygon();
			p1.addPoint((int)Math.round(945 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(570 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(935 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(535 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(950 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(550 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(950 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(530 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(960 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(548 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(970 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(530 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(970 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(550 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(985 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(535 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(975 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(570 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			g.fillPolygon(p1);
		}
		
		if (pt == PieceType.PRINCE || pt == PieceType.PRINCESS) {
			Polygon p1= new Polygon();
			p1.addPoint((int)Math.round(950 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(570 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(950 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(540 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(960 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(550 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(970 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(540 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(970 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(570 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			g.fillPolygon(p1);
		}
		
		if (pt == PieceType.BISHOP) {
			Polygon p1= new Polygon();
			p1.addPoint((int)Math.round(945 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(570 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(950 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(550 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(945 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(535 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(960 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(520 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(975 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(535 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(970 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(550 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(975 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(570 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			g.fillPolygon(p1);
		}
		
		if (pt == PieceType.KNIGHT) {
			Polygon p1= new Polygon();
			p1.addPoint((int)Math.round(945 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(570 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(952 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(545 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(931 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(543 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(930 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(535 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(960 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(522 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(975 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(535 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(970 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(550 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(975 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(570 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			g.fillPolygon(p1);
		}
		
		if (pt == PieceType.ROOK) {
			Polygon p1= new Polygon();
			p1.addPoint((int)Math.round(945 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(570 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(950 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(550 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(945 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(550 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(945 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(535 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(951 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(535 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(951 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(540 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(957 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(540 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(957 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(535 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(963 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(535 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(963 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(540 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(969 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(540 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(969 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(535 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(975 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(535 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(975 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(550 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(970 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(550 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(975 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(570 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			g.fillPolygon(p1);
		}
		
		if (pt == PieceType.PAWN && ThreeDimChessRunner.hidePawns == false) {
			Polygon p1= new Polygon();
			p1.addPoint((int)Math.round(950 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(570 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(952 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(555 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			g.fillOval((int)Math.round(950 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(540 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)), 20, 20);
			p1.addPoint((int)Math.round(968 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(555 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(970 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(570 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			g.fillPolygon(p1);
		}
		
		if (pt == PieceType.BARRICADE && ThreeDimChessRunner.hideBarricades == false) {
			Polygon p1= new Polygon();
			p1.addPoint((int)Math.round(940 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(570 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(942 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(555 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(978 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(555 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			p1.addPoint((int)Math.round(980 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(570 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)));
			g.fillPolygon(p1);
		}
		g.setColor(new Color(120, 120, 80));
		
		if (targeted) {
			g.fillOval((int)Math.round(940 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(520 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)), 40, 40);
		}
		
		g.setColor(Color.BLACK);
	}
}
