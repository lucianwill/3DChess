import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Color;

public class ThreeDimBoard {
	
	Piece[][][] square; // The array of all positions on the board.
	int gametype;
	int turn;
	int[] kCount = new int[2]; //King
	int[] qCount = new int[2]; //Queen
	int[] sCount = new int[2]; //Prince/Princess
	int[] bCount = new int[2]; //Bishop
	int[] nCount = new int[2]; //Knight
	int[] rCount = new int[2]; //Rook
	int[] pCount = new int[2]; //Pawn
	int[] cCount = new int[2]; //Barricade
	
	public ThreeDimBoard(int gt) {
		gametype = gt;
		turn = 0;
		
		if(gt == 0) {
			square = new Piece[8][8][8]; // 1st number is height up the board 1-8. 2nd is width a-h and 3rd is depth a-h.
			// For instance, the white king starts at ee1 or [0][4][4] and the queen starts at de1 or [0][3][4].
			kCount[0] = 1; qCount[0] = 1; sCount[0] = 2; bCount[0] = 4; nCount[0] = 4; rCount[0] = 4; pCount[0] = 64; cCount[0] = 48;
			kCount[1] = 1; qCount[1] = 1; sCount[1] = 2; bCount[1] = 4; nCount[1] = 4; rCount[1] = 4; pCount[1] = 64; cCount[1] = 48;
			
			for(int k = 0; k < 8; k++)
				for(int j = 0; j < 8; j++)
					for(int m = 0; m < 8; m++) {
						square[k][j][m] = new Piece(-1,0,k,j,m);
					}
			
			
			for(int k = 0; k < 8; k++) {
				for(int j = 0; j < 8; j++) {
					square[1][k][j] = new Piece(6,0,1,k,j); // Pawns
					square[6][k][j] = new Piece(6,1,6,k,j);
					square[0][k][j] = new Piece(7,0,0,k,j); // Barricades, some of which are later overwritten.
					square[7][k][j] = new Piece(7,1,7,k,j);
				}
			}
			
			for(int k = 0; k < 2; k++) {
				
				for(int j = 0; j < 2; j++) {
					square[0][k*7][j*7] = new Piece(5,0,0,k*7,j*7); // Rooks
					square[7][k*7][j*7] = new Piece(5,1,7,k*7,j*7);
					square[0][(k*5)+1][(j*5)+1] = new Piece(4,0,0,(k*5)+1,(j*5)+1); // Knights
					square[7][(k*5)+1][(j*5)+1] = new Piece(4,1,7,(k*5)+1,(j*5)+1);
					square[0][(k*3)+2][(j*3)+2] = new Piece(3,0,0,(k*3)+2,(j*3)+2); // Bishops
					square[7][(k*3)+2][(j*3)+2] = new Piece(3,1,7,(k*3)+2,(j*3)+2);
				}
				
				square[k*7][4][4] = new Piece(0,k,k*7,4,4); // King
				square[k*7][3][4] = new Piece(1,k,k*7,3,4); // Queen
				square[k*7][3][3] = new Piece(2,k,k*7,3,3); // Prince
				square[k*7][4][3] = new Piece(2,k,k*7,4,3); // Princess
			}
		}
		
		if(gt == 1) {
			square = new Piece[8][8][8];
		}
	}
	
	public boolean moveValid(int[] t, int[] v) {
		
		if(gametype == 0 && (t[0] + v[0] >= 0 && t[0] + v[0] < 8) && 
				(t[1] + v[1] >= 0 && t[1] + v[1] < 8) && (t[2] + v[2] >= 0 && t[2] + v[2] < 8) && 
				square[t[0]][t[1]][t[2]].piecetype != -1) {
			if(turn == square[t[0]][t[1]][t[2]].side) {
				if(square[t[0]][t[1]][t[2]].piecetype == 0) {
				
					if(square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].piecetype == -1) {
						if((Math.abs(v[0]) == 1 || Math.abs(v[1]) == 1 || Math.abs(v[2]) == 1) && 
								(Math.abs(v[0]) <= 1 && Math.abs(v[1]) <= 1 && Math.abs(v[2]) <= 1))
							return true;
					}
					else if((Math.abs(v[0]) == 1 || Math.abs(v[1]) == 1 || Math.abs(v[2]) == 1) && 
							(Math.abs(v[0]) <= 1 && Math.abs(v[1]) <= 1 && Math.abs(v[2]) <= 1) && 
							(square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].side != square[t[0]][t[1]][t[2]].side))
						return true;
				}
				
				if(square[t[0]][t[1]][t[2]].piecetype == 1) {
					
					if((Math.abs(v[0]) == Math.abs(v[1]) || Math.abs(v[0]) == 0 || Math.abs(v[1]) == 0) && 
							(Math.abs(v[1]) == Math.abs(v[2]) || Math.abs(v[1]) == 0 || Math.abs(v[2]) == 0) &&
							(Math.abs(v[0]) == Math.abs(v[2]) || Math.abs(v[0]) == 0 || Math.abs(v[2]) == 0) &&
							(Math.abs(v[0]) != 0 || Math.abs(v[1]) != 0 || Math.abs(v[2]) != 0)) {
						boolean blocked = false;
						boolean friendlyBarricaded = true;
						boolean hostileBarricaded = true;
						int maxv;
						if(Math.abs(v[0]) != 0)
							maxv = Math.abs(v[0]);
						else if(Math.abs(v[1]) != 0)
							maxv = Math.abs(v[1]);
						else
							maxv = Math.abs(v[2]);
						
						for(int k = 1; k < maxv; k++) {
							if(square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].piecetype != -1)
								blocked = true;
							if(square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].piecetype != 7 || 
									square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].side != square[t[0]][t[1]][t[2]].side)
								friendlyBarricaded = false;
							if(square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].piecetype != 7 || 
									square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].side == square[t[0]][t[1]][t[2]].side)
								hostileBarricaded = false;
						}
						
						if(((square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].side != square[t[0]][t[1]][t[2]].side || 
								square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].piecetype == -1) && !blocked) || 
								(hostileBarricaded && square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].piecetype == 7 && 
								square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].side != square[t[0]][t[1]][t[2]].side) || 
								(friendlyBarricaded && square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].side != square[t[0]][t[1]][t[2]].side && 
								square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].piecetype != -1)) {
							return true;
						}
					}
				}
				
				if(square[t[0]][t[1]][t[2]].piecetype == 2) {
					
					if((Math.abs(v[0]) == Math.abs(v[1]) || Math.abs(v[0]) == 0 || Math.abs(v[1]) == 0) && 
							(Math.abs(v[1]) == Math.abs(v[2]) || Math.abs(v[1]) == 0 || Math.abs(v[2]) == 0) &&
							(Math.abs(v[0]) == Math.abs(v[2]) || Math.abs(v[0]) == 0 || Math.abs(v[2]) == 0) &&
							((Math.abs(v[0]) != 0 && Math.abs(v[1]) != 0) || 
									(Math.abs(v[1]) != 0 && Math.abs(v[2]) != 0) || 
									(Math.abs(v[0]) != 0 && Math.abs(v[2]) != 0)) && 
							!(Math.abs(v[0]) == Math.abs(v[1]) && Math.abs(v[1]) == Math.abs(v[2]))) {
						boolean blocked = false;
						boolean friendlyBarricaded = true;
						boolean hostileBarricaded = true;
						int maxv;
						if(Math.abs(v[0]) != 0)
							maxv = Math.abs(v[0]);
						else if(Math.abs(v[1]) != 0)
							maxv = Math.abs(v[1]);
						else
							maxv = Math.abs(v[2]);
						
						for(int k = 1; k < maxv; k++) {
							if(square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].piecetype != -1)
								blocked = true;
							if(square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].piecetype != 7 || 
									square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].side != square[t[0]][t[1]][t[2]].side)
								friendlyBarricaded = false;
							if(square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].piecetype != 7 || 
									square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].side == square[t[0]][t[1]][t[2]].side)
								hostileBarricaded = false;
						}
						
						if(((square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].side != square[t[0]][t[1]][t[2]].side || 
								square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].piecetype == -1) && !blocked) || 
								(hostileBarricaded && square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].piecetype == 7 && 
								square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].side != square[t[0]][t[1]][t[2]].side) || 
								(friendlyBarricaded && square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].side != square[t[0]][t[1]][t[2]].side && 
								square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].piecetype != -1)) {
							return true;
						}
					}
				}
				
				if(square[t[0]][t[1]][t[2]].piecetype == 3) {
					
					if((Math.abs(v[0]) == Math.abs(v[1]) && Math.abs(v[1]) == Math.abs(v[2])) && 
							Math.abs(v[0]) != 0) {
						boolean blocked = false;
						boolean friendlyBarricaded = true;
						boolean hostileBarricaded = true;
						int maxv;
						if(Math.abs(v[0]) != 0)
							maxv = Math.abs(v[0]);
						else if(Math.abs(v[1]) != 0)
							maxv = Math.abs(v[1]);
						else
							maxv = Math.abs(v[2]);
						
						for(int k = 1; k < maxv; k++) {
							if(square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].piecetype != -1)
								blocked = true;
							if(square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].piecetype != 7 || 
									square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].side != square[t[0]][t[1]][t[2]].side)
								friendlyBarricaded = false;
							if(square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].piecetype != 7 || 
									square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].side == square[t[0]][t[1]][t[2]].side)
								hostileBarricaded = false;
						}
						
						if(((square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].side != square[t[0]][t[1]][t[2]].side || 
								square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].piecetype == -1) && !blocked) || 
								(hostileBarricaded && square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].piecetype == 7 && 
								square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].side != square[t[0]][t[1]][t[2]].side) || 
								(friendlyBarricaded && square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].side != square[t[0]][t[1]][t[2]].side && 
								square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].piecetype != -1)) {
							return true;
						}
					}
				}
				
				if(square[t[0]][t[1]][t[2]].piecetype == 6) {
				
					if(square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].piecetype == -1 || square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].piecetype == 7) {
						if(v[0] == -((2 * square[t[0]][t[1]][t[2]].side)-1) && v[1] == 0 && v[2] == 0)
							return true;
						if(square[t[0]][t[1]+v[1]][t[2]+v[2]].piecetype != -1) {
							if(v[0] == -((2 * square[t[0]][t[1]][t[2]].side)-1) && (Math.abs(v[1]) == 1 || Math.abs(v[2]) == 1) && 
									(Math.abs(v[1]) <= 1 && Math.abs(v[2]) <= 1) && square[t[0]][t[1]+v[1]][t[2]+v[2]].enPassantValid == true) {
								return true;
							}
						}
						if(v[0] == -((4 * square[t[0]][t[1]][t[2]].side)-2) && v[1] == 0 && v[2] == 0 && 
								square[t[0]][t[1]][t[2]].twoStepsValid == true && square[t[0]+(v[0]/2)][t[1]][t[2]].piecetype == -1) {
							return true;
						}
					}
					else if(v[0] == -((2 * square[t[0]][t[1]][t[2]].side)-1) && (Math.abs(v[1]) == 1 || Math.abs(v[2]) == 1) && 
							(Math.abs(v[1]) <= 1 && Math.abs(v[2]) <= 1) && 
							square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].side != square[t[0]][t[1]][t[2]].side && 
							square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].piecetype != 7)
						return true;
				}
			}
		}
		
		return false;
	}
	
	public void move(int[] t, int[] v) { // t is location, v is vector.
		
		if(square[t[0]][t[1]][t[2]].piecetype == 6) {
			if(v[0] == -((4 * square[t[0]][t[1]][t[2]].side)-2)) {
				square[t[0]][t[1]][t[2]].twoStepsValid = false;
				square[t[0]][t[1]][t[2]].enPassantValid = true;
			}
			
			if(square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].piecetype == -1) {
				if(v[0] == -((2 * square[t[0]][t[1]][t[2]].side)-1) && (Math.abs(v[1]) == 1 || Math.abs(v[2]) == 1) && 
						(Math.abs(v[1]) <= 1 && Math.abs(v[2]) <= 1) && square[t[0]][t[1]+v[1]][t[2]+v[2]].enPassantValid == true)
					square[t[0]][t[1]+v[1]][t[2]+v[2]] = new Piece(-1,0,t[0],t[1]+v[1],t[2]+v[2]);
			}
		}
		
		square[t[0] + v[0]][t[1] + v[1]][t[2] + v[2]] = square[t[0]][t[1]][t[2]];
		square[t[0] + v[0]][t[1] + v[1]][t[2] + v[2]].location[0] += v[0];
		square[t[0] + v[0]][t[1] + v[1]][t[2] + v[2]].location[1] += v[1];
		square[t[0] + v[0]][t[1] + v[1]][t[2] + v[2]].location[2] += v[2];
		square[t[0]][t[1]][t[2]] = new Piece(-1,0,t[0],t[1],t[2]);
		
		if((t[0] == 0) && (t[1] != t[2]) && (t[1] != 7 - t[2]) && (gametype == 0))
			square[t[0]][t[1]][t[2]] = new Piece(7,0,t[0],t[1],t[2]);
		if((t[0] == 7) && (t[1] != t[2]) && (t[1] != 7 - t[2]) && (gametype == 0))
			square[t[0]][t[1]][t[2]] = new Piece(7,1,t[0],t[1],t[2]); // To replace barricades, which cannot be captured, only displaced.
		if(turn == 0)
			turn++;
		else if(turn == 1)
			turn--;
		
		for(int k = 0; k < 8; k++)
			for(int j = 0; j < 8; j++)
				for(int m = 0; m < 8; m++) {
					if(square[k][j][m].side == turn)
						square[k][j][m].enPassantValid = false;
				}
		
	}
	
	public void target(Piece highlighted) {
		for(int k = 0; k < 8; k++)
			for(int j = 0; j < 8; j++)
				for(int m = 0; m < 8; m++) {
					int[] t = new int[3];
					int[] v = new int[3];
					t[0] = highlighted.location[0]; t[1] = highlighted.location[1]; t[2] = highlighted.location[2];
					v[0] = k - t[0]; v[1] = j - t[1]; v[2] = m - t[2];
					if(moveValid(t, v))
						square[k][j][m].targeted = true;
				}
	}
	
	public void detarget() {
		for(int k = 0; k < 8; k++)
			for(int j = 0; j < 8; j++)
				for(int m = 0; m < 8; m++) {
					square[k][j][m].targeted = false;
				}
	}
	
	public void drawPieces(Graphics g, double viewAngle, double viewElevation) {
		int xpref = 7;
		int ypref = 0;
		int zpref = 7;
		
		if(viewAngle < Math.PI)
			xpref = 0;
		if(viewAngle < Math.PI / 2 || viewAngle > 3 * Math.PI / 2)
			ypref = 7;
		if(viewElevation > 0)
			zpref = 0;
		
		int xfact = -((2 * xpref / 7) - 1);
		int yfact = -((2 * ypref / 7) - 1);
		int zfact = -((2 * zpref / 7) - 1);
		
		for(int k = 0; k < 8; k++) {
			for(int j = 0; j < 8; j++) {
				for(int m = 0; m < 8; m++)
					square[(k * zfact) + zpref][(j * xfact) + xpref][(m * yfact) + ypref].Draw(g, viewAngle, viewElevation);
			}
		}
	}
}

class Piece {
	
	int side; // 0 = white, 1 = black, 2 = red, 3 = blue
	int piecetype; // -1 = empty, 0 = king, 1 = queen, 2 = prince/princess,
	// 3 = Bishop, 4 = Knight, 5 = Rook, 6 = Pawn, 7 = Barricade
	int[] location;
	boolean highlighted;
	boolean targeted;
	boolean castleValid; // Can only be true for kings and rooks
	boolean enPassantValid; // Can only be true for pawns
	boolean twoStepsValid; // Can only be true for pawns
	
	public Piece() {
		
	}
	
	public Piece(int pt, int s, int z, int x, int y) {
		piecetype = pt;
		side = s;
		location = new int[3];
		location[0] = z;
		location[1] = x;
		location[2] = y;
		highlighted = false;
		
		if(pt == 0 || pt == 5)
			castleValid = true;
		else
			castleValid = false;
		
		if(pt == 6)
			twoStepsValid = true;
		else
			twoStepsValid = true;
		
		enPassantValid = false;
	}
	
	public void Draw(Graphics g, double viewAngle, double viewElevation) {
		double[] x = {Math.cos(viewAngle), Math.sin(viewAngle) * Math.sin(viewElevation)};
		double[] y = {Math.sin(viewAngle), - (Math.cos(viewAngle) * Math.sin(viewElevation))};
		double z = - Math.cos(viewElevation);
		double xfact = 70 * (location[1] - 3.5);
		double yfact = 70 * (location[2] - 3.5);
		double zfact = 70 * (location[0] - 3.5);
		if(highlighted == false) {
			if(side == 0)
				g.setColor(new Color(225,230,160));
			if(side == 1)
				g.setColor(new Color(40,20,20));
			if(side == 2)
				g.setColor(new Color(225,10,10));
			if(side == 3)
				g.setColor(new Color(20,10,200));
		}
		else {
			if(side == 0)
				g.setColor(new Color(245,250,160));
			if(side == 1)
				g.setColor(new Color(60,40,20));
			if(side == 2)
				g.setColor(new Color(245,30,10));
			if(side == 3)
				g.setColor(new Color(40,30,200));
		}
		
		if(piecetype == 0) {
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
		
		if(piecetype == 1) {
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
		
		if(piecetype == 2) {
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
		
		if(piecetype == 3) {
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
		
		if(piecetype == 4) {
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
		
		if(piecetype == 5) {
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
		
		if(piecetype == 6 && ThreeDimChessRunner.hidePawns == false) {
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
		
		if(piecetype == 7 && ThreeDimChessRunner.hideBarricades == false) {
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
		
		if(targeted) {
			g.fillOval((int)Math.round(940 + (xfact * x[0]) + (yfact * y[0])), 
					(int)Math.round(520 + (xfact * x[1]) + (yfact * y[1]) + (zfact * z)), 40, 40);
		}
		
		g.setColor(Color.BLACK);
	}
}