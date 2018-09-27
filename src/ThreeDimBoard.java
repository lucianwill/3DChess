import java.awt.Graphics;

public class ThreeDimBoard {
	
	Piece[][][] square; // The array of all positions on the board.
	int gametype; // The type of game you want to play. Only one functional as of right now.
	int turn;
	Piece[][] kings;
	Piece[][] queens;
	Piece[][] princes;
	Piece[][] princesses;
	Piece[][] bishops;
	Piece[][] knights;
	Piece[][] rooks;
	Piece[][] pawns;
	
	private boolean noLegalMoves(int side) {
		boolean returnValue = true;
		
		for (int g = 0; g < 64; g++) {
			for (int k = 0; k < 8; k++)
				for (int j = 0; j < 8; j++)
					for (int m = 0; m < 8; m++) {
						int[] t = new int[3];
						int[] v = new int[3];
						t[0] = pawns[side][g].location[0]; t[1] = pawns[side][g].location[1]; t[2] = pawns[side][g].location[2];
						v[0] = k - t[0]; v[1] = j - t[1]; v[2] = m - t[2];
						if (moveValid(t, v) && !intoCheck(side, t, v) && !pawns[side][g].captured)
							returnValue = false;
					}
		}
		
		for (int g = 0; g < 4; g++) {
			for (int k = 0; k < 8; k++)
				for (int j = 0; j < 8; j++)
					for (int m = 0; m < 8; m++) {
						int[] t = new int[3];
						int[] v = new int[3];
						t[0] = rooks[side][g].location[0]; t[1] = rooks[side][g].location[1]; t[2] = rooks[side][g].location[2];
						v[0] = k - t[0]; v[1] = j - t[1]; v[2] = m - t[2];
						if (moveValid(t, v) && !intoCheck(side, t, v) && !rooks[side][g].captured)
							returnValue = false;
						t[0] = knights[side][g].location[0]; t[1] = knights[side][g].location[1]; t[2] = knights[side][g].location[2];
						v[0] = k - t[0]; v[1] = j - t[1]; v[2] = m - t[2];
						if (moveValid(t, v) && !intoCheck(side, t, v) && !knights[side][g].captured)
							returnValue = false;
						t[0] = bishops[side][g].location[0]; t[1] = bishops[side][g].location[1]; t[2] = bishops[side][g].location[2];
						v[0] = k - t[0]; v[1] = j - t[1]; v[2] = m - t[2];
						if (moveValid(t, v) && !intoCheck(side, t, v) && !bishops[side][g].captured)
							returnValue = false;
					}
		}
		
		for (int k = 0; k < 8; k++)
			for (int j = 0; j < 8; j++)
				for (int m = 0; m < 8; m++) {
					int[] t = new int[3];
					int[] v = new int[3];
					t[0] = kings[side][0].location[0]; t[1] = kings[side][0].location[1]; t[2] = kings[side][0].location[2];
					v[0] = k - t[0]; v[1] = j - t[1]; v[2] = m - t[2];
					if (moveValid(t, v) && !intoCheck(side, t, v))
						returnValue = false;
					t[0] = queens[side][0].location[0]; t[1] = queens[side][0].location[1]; t[2] = queens[side][0].location[2];
					v[0] = k - t[0]; v[1] = j - t[1]; v[2] = m - t[2];
					if (moveValid(t, v) && !intoCheck(side, t, v) && !queens[side][0].captured)
						returnValue = false;
					t[0] = princes[side][0].location[0]; t[1] = princes[side][0].location[1]; t[2] = princes[side][0].location[2];
					v[0] = k - t[0]; v[1] = j - t[1]; v[2] = m - t[2];
					if (moveValid(t, v) && !intoCheck(side, t, v) && !princes[side][0].captured)
						returnValue = false;
					t[0] = princesses[side][0].location[0]; t[1] = princesses[side][0].location[1]; t[2] = princesses[side][0].location[2];
					v[0] = k - t[0]; v[1] = j - t[1]; v[2] = m - t[2];
					if (moveValid(t, v) && !intoCheck(side, t, v) && !princesses[side][0].captured)
						returnValue = false;
				}
		return returnValue;
	}
	
	public ThreeDimBoard(int gt) {
		gametype = gt;
		turn = 0;
		
		if (gt == 0) {
			square = new Piece[8][8][8]; // 1st number is height up the board 1-8. 2nd is width a-h and 3rd is depth a-h.
			// For instance, the white king starts at ee1 or [0][4][4] and the queen starts at de1 or [0][3][4].
			
			kings = new Piece[2][1];
			queens = new Piece[2][1];
			princes = new Piece[2][1];
			princesses = new Piece[2][1];
			bishops = new Piece[2][4];
			knights = new Piece[2][4];
			rooks = new Piece[2][4];
			pawns = new Piece[2][64];
			
			for (int k = 0; k < 8; k++)
				for (int j = 0; j < 8; j++)
					for (int m = 0; m < 8; m++) {
						square[k][j][m] = new Piece(PieceType.EMPTY,Player.WHITE,k,j,m);
					}
			
			
			for (int k = 0; k < 8; k++) {
				for (int j = 0; j < 8; j++) {
					square[1][k][j] = new Piece(PieceType.PAWN,Player.WHITE,1,k,j);
					square[6][k][j] = new Piece(PieceType.PAWN,Player.BLACK,6,k,j);
					pawns[0][(8 * k) + j] = square[1][k][j];
					pawns[1][(8 * k) + j] = square[6][k][j];
					square[0][k][j] = new Piece(PieceType.BARRICADE,Player.WHITE,0,k,j);
					square[7][k][j] = new Piece(PieceType.BARRICADE,Player.BLACK,7,k,j);
				}
			}
			
			for (int k = 0; k < 2; k++) {
				
				for (int j = 0; j < 2; j++) {
					square[0][k*7][j*7] = new Piece(PieceType.ROOK,Player.WHITE,0,k*7,j*7);
					square[7][k*7][j*7] = new Piece(PieceType.ROOK,Player.BLACK,7,k*7,j*7);
					rooks[0][(2 * k) + j] = square[0][k*7][j*7];
					rooks[1][(2 * k) + j] = square[7][k*7][j*7];
					square[0][(k*5)+1][(j*5)+1] = new Piece(PieceType.KNIGHT,Player.WHITE,0,(k*5)+1,(j*5)+1);
					square[7][(k*5)+1][(j*5)+1] = new Piece(PieceType.KNIGHT,Player.BLACK,7,(k*5)+1,(j*5)+1);
					knights[0][(2 * k) + j] = square[0][(k*5)+1][(j*5)+1];
					knights[1][(2 * k) + j] = square[7][(k*5)+1][(j*5)+1];
					square[0][(k*3)+2][(j*3)+2] = new Piece(PieceType.BISHOP,Player.WHITE,0,(k*3)+2,(j*3)+2);
					square[7][(k*3)+2][(j*3)+2] = new Piece(PieceType.BISHOP,Player.BLACK,7,(k*3)+2,(j*3)+2);
					bishops[0][(2 * k) + j] = square[0][(k*3)+2][(j*3)+2];
					bishops[1][(2 * k) + j] = square[7][(k*3)+2][(j*3)+2];
				}
				
				
			}
			square[0][4][4] = new Piece(PieceType.KING,Player.WHITE,0,4,4);
			kings[0][0] = square[0][4][4];
			square[0][3][4] = new Piece(PieceType.QUEEN,Player.WHITE,0,3,4);
			queens[0][0] = square[0][3][4];
			square[0][3][3] = new Piece(PieceType.PRINCE,Player.WHITE,0,3,3);
			princes[0][0] = square[0][3][3];
			square[0][4][3] = new Piece(PieceType.PRINCESS,Player.WHITE,0,4,3);
			princesses[0][0] = square[0][4][3];
			square[7][4][4] = new Piece(PieceType.KING,Player.BLACK,7,4,4);
			kings[1][0] = square[7][4][4];
			square[7][3][4] = new Piece(PieceType.QUEEN,Player.BLACK,7,3,4);
			queens[1][0] = square[7][3][4];
			square[7][3][3] = new Piece(PieceType.PRINCE,Player.BLACK,7,3,3);
			princes[1][0] = square[7][3][3];
			square[7][4][3] = new Piece(PieceType.PRINCESS,Player.BLACK,7,4,3);
			princesses[1][0] = square[7][4][3];
		}
		
		if (gt == 1) {
			square = new Piece[8][8][8];
		}
	}
	
	public int pieceCount(int side) {
		int total = 0;
		for (int k = 0; k < 64; k++) {
			if (pawns[side][k].captured == false)
				total++;
		}
		for (int k = 0; k < 4; k++) {
			if (rooks[side][k].captured == false)
				total++;
			if (knights[side][k].captured == false)
				total++;
			if (bishops[side][k].captured == false)
				total++;
		}
		total++;
		if (queens[side][0].captured == false)
			total++;
		if (princes[side][0].captured == false)
			total++;
		if (princesses[side][0].captured == false)
			total++;
		return total;
	}
	
	public int advantage() {
		int whitePoints = 0;
		for (int k = 0; k < 64; k++) {
			if (pawns[0][k].captured == false) {
				if (pawns[0][k].pt == PieceType.PAWN)
					whitePoints += 1;
				if (pawns[0][k].pt == PieceType.ROOK)
					whitePoints += 6;
				if (pawns[0][k].pt == PieceType.KNIGHT)
					whitePoints += 3;
				if (pawns[0][k].pt == PieceType.BISHOP)
					whitePoints += 3;
				if (pawns[0][k].pt == PieceType.PRINCE)
					whitePoints += 4;
				if (pawns[0][k].pt == PieceType.QUEEN)
					whitePoints += 12;
			}
		}
		for (int k = 0; k < 4; k++) {
			if (rooks[0][k].captured == false)
				whitePoints += 6;
			if (knights[0][k].captured == false)
				whitePoints += 3;
			if (bishops[0][k].captured == false)
				whitePoints += 3;
		}
		whitePoints+=1000;
		if (queens[0][0].captured == false)
			whitePoints += 12;
		if (princes[0][0].captured == false)
			whitePoints += 4;
		if (princesses[0][0].captured == false)
			whitePoints += 4;
		int blackPoints = 0;
		for (int k = 0; k < 64; k++) {
			if (pawns[1][k].captured == false) {
				if (pawns[1][k].pt == PieceType.PAWN)
					blackPoints += 1;
				if (pawns[1][k].pt == PieceType.ROOK)
					blackPoints += 6;
				if (pawns[1][k].pt == PieceType.KNIGHT)
					blackPoints += 3;
				if (pawns[1][k].pt == PieceType.BISHOP)
					blackPoints += 3;
				if (pawns[1][k].pt == PieceType.PRINCE)
					blackPoints += 4;
				if (pawns[1][k].pt == PieceType.QUEEN)
					blackPoints += 12;
			}
		}
		for (int k = 0; k < 4; k++) {
			if (rooks[1][k].captured == false)
				blackPoints += 6;
			if (knights[1][k].captured == false)
				blackPoints += 3;
			if (bishops[1][k].captured == false)
				blackPoints += 3;
		}
		blackPoints+=1000;
		if (queens[1][0].captured == false)
			blackPoints += 12;
		if (princes[1][0].captured == false)
			blackPoints += 4;
		if (princesses[1][0].captured == false)
			blackPoints += 4;
		return whitePoints - blackPoints;
	}
	
	public boolean intoCheck(int side, int[] t, int[] v) {
		Piece temp = null;
		Piece temp2 = null;
		int[] tReverse = {t[0] + v[0],t[1] + v[1], t[2] + v[2]};
		int[] vReverse = {- v[0], - v[1], - v[2]};
		boolean enPassantMove = false;
		boolean castleShort = false;
		boolean castleLong = false;
		boolean returnValue = false;
		temp = square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]];
		
		/*if (square[t[0]][t[1]][t[2]].pt == PieceType.KING && t[1] == 4) {
			if (v[0] == 0 && v[1] == 2 && v[2] == 2) {
				temp2 = square[t[0]][5][5];
				castleShort = true;
			}
			else if (v[0] == 0 && v[1] == -2 && v[2] == -2) {
				temp2 = square[t[0]][3][3];
				castleLong = true;
			}
		}*/
		
		if (square[t[0]][t[1]][t[2]].pt == PieceType.PAWN) {
			if (square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].pt == PieceType.EMPTY) {
				if ((Math.abs(v[1]) == 1 || Math.abs(v[2]) == 1) && 
						(Math.abs(v[1]) <= 1 && Math.abs(v[2]) <= 1)) {
					temp = square[t[0]][t[1]+v[1]][t[2]+v[2]];
					enPassantMove = true;
				}
			}
		}
		
		testMove(t, v);
		
		if (inCheck(side))
			returnValue = true;
		
		testMove(tReverse, vReverse);
		
		if (enPassantMove) {
			square[t[0]][t[1]+v[1]][t[2]+v[2]] = temp;
			temp.captured = false;
		}
		else
			square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]] = temp;
		
		if (castleShort)
			square[t[0]][5][5] = temp2;
		else if (castleLong)
			square[t[0]][3][3] = temp2;
		
		return returnValue;
	}
	
	public boolean inCheck(int side) {
		int[] ttemp = new int[3];
		int[] vtemp = new int[3];
		boolean out = false;
		for (int k = 0; k < 64; k++) {
			ttemp = pawns[1 - side][k].location;
			vtemp[0] = kings[side][0].location[0] - pawns[1 - side][k].location[0];
			vtemp[1] = kings[side][0].location[1] - pawns[1 - side][k].location[1];
			vtemp[2] = kings[side][0].location[2] - pawns[1 - side][k].location[2];
			if (!pawns[1 - side][k].captured && moveValid(ttemp, vtemp))
				out = true;
		}
		
		for (int k = 0; k < 4; k++) {
			ttemp = rooks[1 - side][k].location;
			vtemp[0] = kings[side][0].location[0] - rooks[1 - side][k].location[0];
			vtemp[1] = kings[side][0].location[1] - rooks[1 - side][k].location[1];
			vtemp[2] = kings[side][0].location[2] - rooks[1 - side][k].location[2];
			if (!rooks[1 - side][k].captured && moveValid(ttemp, vtemp))
				out = true;
			ttemp = knights[1 - side][k].location;
			vtemp[0] = kings[side][0].location[0] - knights[1 - side][k].location[0];
			vtemp[1] = kings[side][0].location[1] - knights[1 - side][k].location[1];
			vtemp[2] = kings[side][0].location[2] - knights[1 - side][k].location[2];
			if (!knights[1 - side][k].captured && moveValid(ttemp, vtemp))
				out = true;
			ttemp = bishops[1 - side][k].location;
			vtemp[0] = kings[side][0].location[0] - bishops[1 - side][k].location[0];
			vtemp[1] = kings[side][0].location[1] - bishops[1 - side][k].location[1];
			vtemp[2] = kings[side][0].location[2] - bishops[1 - side][k].location[2];
			if (!bishops[1 - side][k].captured && moveValid(ttemp, vtemp))
				out = true;
		}
		ttemp = kings[1 - side][0].location;
		vtemp[0] = kings[side][0].location[0] - kings[1 - side][0].location[0];
		vtemp[1] = kings[side][0].location[1] - kings[1 - side][0].location[1];
		vtemp[2] = kings[side][0].location[2] - kings[1 - side][0].location[2];
		if (!kings[1 - side][0].captured && moveValid(ttemp, vtemp))
			out = true;
		ttemp = queens[1 - side][0].location;
		vtemp[0] = kings[side][0].location[0] - queens[1 - side][0].location[0];
		vtemp[1] = kings[side][0].location[1] - queens[1 - side][0].location[1];
		vtemp[2] = kings[side][0].location[2] - queens[1 - side][0].location[2];
		if (!queens[1 - side][0].captured && moveValid(ttemp, vtemp))
			out = true;
		ttemp = princes[1 - side][0].location;
		vtemp[0] = kings[side][0].location[0] - princes[1 - side][0].location[0];
		vtemp[1] = kings[side][0].location[1] - princes[1 - side][0].location[1];
		vtemp[2] = kings[side][0].location[2] - princes[1 - side][0].location[2];
		if (!princes[1 - side][0].captured && moveValid(ttemp, vtemp))
			out = true;
		ttemp = princesses[1 - side][0].location;
		vtemp[0] = kings[side][0].location[0] - princesses[1 - side][0].location[0];
		vtemp[1] = kings[side][0].location[1] - princesses[1 - side][0].location[1];
		vtemp[2] = kings[side][0].location[2] - princesses[1 - side][0].location[2];
		if (!princesses[1 - side][0].captured && moveValid(ttemp, vtemp))
			out = true;
		return out;
	}
	
	
	public boolean moveValid(int[] t, int[] v) {
		
			if (gametype == 0 && (t[0] + v[0] >= 0 && t[0] + v[0] < 8) && 
					(t[1] + v[1] >= 0 && t[1] + v[1] < 8) && (t[2] + v[2] >= 0 && t[2] + v[2] < 8) && 
					square[t[0]][t[1]][t[2]].pt != PieceType.EMPTY) {
				if (square[t[0]][t[1]][t[2]].pt == PieceType.KING) {
					
					int s;
					if(square[t[0]][t[1]][t[2]].p == Player.WHITE)
						s = 0;
					else
						s = 1;
					
					if (square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].pt == PieceType.EMPTY) {
						if ((Math.abs(v[0]) == 1 || Math.abs(v[1]) == 1 || Math.abs(v[2]) == 1) && 
								(Math.abs(v[0]) <= 1 && Math.abs(v[1]) <= 1 && Math.abs(v[2]) <= 1))
							return true;
						if (square[t[0]][t[1]][t[2]].castleValid) {
							if (v[0] == 0 && v[1] == 2 && v[2] == 2 && 
									square[t[0]][t[1] + 3][t[2] + 3].castleValid && 
									square[t[0]][t[1] + 1][t[2] + 1].pt == PieceType.EMPTY && !inCheck(s)) {
								return true;
							}
							else if (v[0] == 0 && v[1] == -2 && v[2] == -2 && 
									square[t[0]][t[1] - 4][t[2] - 4].castleValid && 
									square[t[0]][t[1] - 1][t[2] - 1].pt == PieceType.EMPTY && 
									square[t[0]][t[1] - 3][t[2] - 3].pt == PieceType.EMPTY && !inCheck(s)) {
								return true;
							}
						}
					}
					else if ((Math.abs(v[0]) == 1 || Math.abs(v[1]) == 1 || Math.abs(v[2]) == 1) && 
							(Math.abs(v[0]) <= 1 && Math.abs(v[1]) <= 1 && Math.abs(v[2]) <= 1) && 
							(square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].p != square[t[0]][t[1]][t[2]].p))
						return true;
				}
				
				if (square[t[0]][t[1]][t[2]].pt == PieceType.QUEEN) {
					
					if ((Math.abs(v[0]) == Math.abs(v[1]) || Math.abs(v[0]) == 0 || Math.abs(v[1]) == 0) && 
							(Math.abs(v[1]) == Math.abs(v[2]) || Math.abs(v[1]) == 0 || Math.abs(v[2]) == 0) &&
							(Math.abs(v[0]) == Math.abs(v[2]) || Math.abs(v[0]) == 0 || Math.abs(v[2]) == 0) &&
							(Math.abs(v[0]) != 0 || Math.abs(v[1]) != 0 || Math.abs(v[2]) != 0)) {
						boolean blocked = false;
						boolean friendlyBarricaded = true;
						boolean hostileBarricaded = true;
						int maxv;
						if (Math.abs(v[0]) != 0)
							maxv = Math.abs(v[0]);
						else if (Math.abs(v[1]) != 0)
							maxv = Math.abs(v[1]);
						else
							maxv = Math.abs(v[2]);
						
						for (int k = 1; k < maxv; k++) {
							if (square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].pt != PieceType.EMPTY)
								blocked = true;
							if (square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].pt != PieceType.BARRICADE || 
									square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].p != square[t[0]][t[1]][t[2]].p)
								friendlyBarricaded = false;
							if (square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].pt != PieceType.BARRICADE || 
									square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].p == square[t[0]][t[1]][t[2]].p)
								hostileBarricaded = false;
						}
						
						if (((square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].p != square[t[0]][t[1]][t[2]].p || 
								square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].pt == PieceType.EMPTY) && !blocked) || 
								(hostileBarricaded && square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].pt == PieceType.BARRICADE && 
								square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].p != square[t[0]][t[1]][t[2]].p) || 
								(friendlyBarricaded && square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].p != square[t[0]][t[1]][t[2]].p && 
								square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].pt != PieceType.EMPTY)) {
							return true;
						}
					}
				}
				
				if (square[t[0]][t[1]][t[2]].pt == PieceType.PRINCE || square[t[0]][t[1]][t[2]].pt == PieceType.PRINCESS) {
					
					if ((Math.abs(v[0]) == Math.abs(v[1]) || Math.abs(v[0]) == 0 || Math.abs(v[1]) == 0) && 
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
						if (Math.abs(v[0]) != 0)
							maxv = Math.abs(v[0]);
						else if (Math.abs(v[1]) != 0)
							maxv = Math.abs(v[1]);
						else
							maxv = Math.abs(v[2]);
						
						for (int k = 1; k < maxv; k++) {
							if (square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].pt != PieceType.EMPTY)
								blocked = true;
							if (square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].pt != PieceType.BARRICADE || 
									square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].p != square[t[0]][t[1]][t[2]].p)
								friendlyBarricaded = false;
							if (square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].pt != PieceType.BARRICADE || 
									square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].p == square[t[0]][t[1]][t[2]].p)
								hostileBarricaded = false;
						}
						
						if (((square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].p != square[t[0]][t[1]][t[2]].p || 
								square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].pt == PieceType.EMPTY) && !blocked) || 
								(hostileBarricaded && square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].pt == PieceType.BARRICADE && 
								square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].p != square[t[0]][t[1]][t[2]].p) || 
								(friendlyBarricaded && square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].p != square[t[0]][t[1]][t[2]].p && 
								square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].pt != PieceType.EMPTY)) {
							return true;
						}
					}
				}
				
				if (square[t[0]][t[1]][t[2]].pt == PieceType.BISHOP) {
					
					if ((Math.abs(v[0]) == Math.abs(v[1]) && Math.abs(v[1]) == Math.abs(v[2])) && 
							Math.abs(v[0]) != 0) {
						boolean blocked = false;
						boolean friendlyBarricaded = true;
						boolean hostileBarricaded = true;
						int maxv;
						if (Math.abs(v[0]) != 0)
							maxv = Math.abs(v[0]);
						else if (Math.abs(v[1]) != 0)
							maxv = Math.abs(v[1]);
						else
							maxv = Math.abs(v[2]);
						
						for (int k = 1; k < maxv; k++) {
							if (square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].pt != PieceType.EMPTY)
								blocked = true;
							if (square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].pt != PieceType.BARRICADE || 
									square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].p != square[t[0]][t[1]][t[2]].p)
								friendlyBarricaded = false;
							if (square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].pt != PieceType.BARRICADE || 
									square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].p == square[t[0]][t[1]][t[2]].p)
								hostileBarricaded = false;
						}
						
						if (((square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].p != square[t[0]][t[1]][t[2]].p || 
								square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].pt == PieceType.EMPTY) && !blocked) || 
								(hostileBarricaded && square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].pt == PieceType.BARRICADE && 
								square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].p != square[t[0]][t[1]][t[2]].p) || 
								(friendlyBarricaded && square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].p != square[t[0]][t[1]][t[2]].p && 
								square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].pt != PieceType.EMPTY)) {
							return true;
						}
					}
				}
				
				if (square[t[0]][t[1]][t[2]].pt == PieceType.KNIGHT) {
					
					if ((Math.abs(v[0]) == 2 * Math.abs(v[1]) && (Math.abs(v[1]) == Math.abs(v[2])) || 
							Math.abs(v[1]) == 2 * Math.abs(v[0]) && (Math.abs(v[0]) == Math.abs(v[2])) || 
							Math.abs(v[2]) == 2 * Math.abs(v[0]) && (Math.abs(v[0]) == Math.abs(v[1]))) && 
							Math.max(Math.max(Math.abs(v[0]), Math.abs(v[1])), Math.abs(v[2])) == 2) {
						
						if (square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].p != square[t[0]][t[1]][t[2]].p || 
								square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].pt == PieceType.EMPTY) {
							return true;
						}
					}
				}
				
				if (square[t[0]][t[1]][t[2]].pt == PieceType.ROOK) {
					
					if ((v[0] != 0 && v[1] == 0 && v[2] == 0) || 
							(v[0] == 0 && v[1] != 0 && v[2] == 0) || 
							(v[0] == 0 && v[1] == 0 && v[2] != 0) ||
							(v[0] == 0 && Math.abs(v[1]) == Math.abs(v[2]) && v[1] != 0)) {
						boolean blocked = false;
						boolean friendlyBarricaded = true;
						boolean hostileBarricaded = true;
						int maxv;
						if (Math.abs(v[0]) != 0)
							maxv = Math.abs(v[0]);
						else if (Math.abs(v[1]) != 0)
							maxv = Math.abs(v[1]);
						else
							maxv = Math.abs(v[2]);
						
						for (int k = 1; k < maxv; k++) {
							if (square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].pt != PieceType.EMPTY)
								blocked = true;
							if (square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].pt != PieceType.BARRICADE || 
									square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].p != square[t[0]][t[1]][t[2]].p)
								friendlyBarricaded = false;
							if (square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].pt != PieceType.BARRICADE || 
									square[t[0]+(k*v[0]/maxv)][t[1]+(k*v[1]/maxv)][t[2]+(k*v[2]/maxv)].p == square[t[0]][t[1]][t[2]].p)
								hostileBarricaded = false;
						}
						
						if (((square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].p != square[t[0]][t[1]][t[2]].p || 
								square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].pt == PieceType.EMPTY) && !blocked) || 
								(hostileBarricaded && square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].pt == PieceType.BARRICADE && 
								square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].p != square[t[0]][t[1]][t[2]].p) || 
								(friendlyBarricaded && square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].p != square[t[0]][t[1]][t[2]].p && 
								square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].pt != PieceType.EMPTY)) {
							return true;
						}
					}
				}
				
				if (square[t[0]][t[1]][t[2]].pt == PieceType.PAWN) {
				
					if (square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].pt == PieceType.EMPTY || 
							square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].pt == PieceType.BARRICADE) {
						if (((v[0] == -1 && square[t[0]][t[1]][t[2]].p == Player.BLACK) || 
								(v[0] == 1 && square[t[0]][t[1]][t[2]].p == Player.WHITE)) && v[1] == 0 && v[2] == 0)
							return true;
						if (square[t[0]][t[1]+v[1]][t[2]+v[2]].pt != PieceType.EMPTY) {
							if (((v[0] == -1 && square[t[0]][t[1]][t[2]].p == Player.BLACK) || 
									(v[0] == 1 && square[t[0]][t[1]][t[2]].p == Player.WHITE)) && 
									(Math.abs(v[1]) == 1 || Math.abs(v[2]) == 1) && 
									(Math.abs(v[1]) <= 1 && Math.abs(v[2]) <= 1) && square[t[0]][t[1]+v[1]][t[2]+v[2]].enPassantValid) {
								return true;
							}
						}
						if (((v[0] == -2 && square[t[0]][t[1]][t[2]].p == Player.BLACK) || 
								(v[0] == 2 && square[t[0]][t[1]][t[2]].p == Player.WHITE)) && v[1] == 0 && v[2] == 0 && 
								square[t[0]][t[1]][t[2]].twoStepsValid == true && square[t[0]+(v[0]/2)][t[1]][t[2]].pt == PieceType.EMPTY) {
							return true;
						}
					}
					else if (((v[0] == -1 && square[t[0]][t[1]][t[2]].p == Player.BLACK) || 
							(v[0] == 1 && square[t[0]][t[1]][t[2]].p == Player.WHITE)) && (Math.abs(v[1]) == 1 || Math.abs(v[2]) == 1) && 
							(Math.abs(v[1]) <= 1 && Math.abs(v[2]) <= 1) && 
							square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].p != square[t[0]][t[1]][t[2]].p && 
							square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].pt != PieceType.BARRICADE)
						return true;
				}
			}
		return false;
	}
	
	public void testMove(int[] t, int[] v) {
		
		if (square[t[0]][t[1]][t[2]].pt == PieceType.PAWN) {
			
			if (square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].pt == PieceType.EMPTY) {
				
				if (((v[0] == -1 && square[t[0]][t[1]][t[2]].p == Player.BLACK) || 
						(v[0] == 1 && square[t[0]][t[1]][t[2]].p == Player.WHITE)) && (Math.abs(v[1]) == 1 || Math.abs(v[2]) == 1) && 
						(Math.abs(v[1]) <= 1 && Math.abs(v[2]) <= 1)) {
					square[t[0]][t[1]+v[1]][t[2]+v[2]].captured = true;
					square[t[0]][t[1]+v[1]][t[2]+v[2]] = new Piece(PieceType.EMPTY,Player.WHITE,t[0],t[1]+v[1],t[2]+v[2]);
				}
			}
		}
		
		if (square[t[0]][t[1]][t[2]].pt == PieceType.KING && t[1] == 4) {
			if (v[1] == 2) {
				int[] t2 = {t[0], t[1] + 3, t[2] + 3};
				int[] v2 = {0, -2, -2};
				testMove(t2, v2);
			}
			else if (v[1] == -2) {
				int[] t2 = {t[0], t[1] - 4, t[2] - 4};
				int[] v2 = {0, 3, 3};
				testMove(t2, v2);
			}
		}
		else if (square[t[0]][t[1]][t[2]].pt == PieceType.KING && t[1] != 4) {
			if (v[1] == -2) {
				int[] t2 = {t[0], t[1] - 1, t[2] - 1};
				int[] v2 = {0, 2, 2};
				square[t[0] + v[0]][t[1] + v[1]][t[2] + v[2]] = square[t[0]][t[1]][t[2]];
				square[t[0] + v[0]][t[1] + v[1]][t[2] + v[2]].location[0] += v[0];
				square[t[0] + v[0]][t[1] + v[1]][t[2] + v[2]].location[1] += v[1];
				square[t[0] + v[0]][t[1] + v[1]][t[2] + v[2]].location[2] += v[2];
				square[t[0]][t[1]][t[2]] = new Piece(PieceType.EMPTY,Player.WHITE,t[0],t[1],t[2]);
				testMove(t2, v2);
				return;
			}
			else if (v[1] == 2) {
				int[] t2 = {t[0], t[1] + 1, t[2] + 1};
				int[] v2 = {0, -3, -3};
				square[t[0] + v[0]][t[1] + v[1]][t[2] + v[2]] = square[t[0]][t[1]][t[2]];
				square[t[0] + v[0]][t[1] + v[1]][t[2] + v[2]].location[0] += v[0];
				square[t[0] + v[0]][t[1] + v[1]][t[2] + v[2]].location[1] += v[1];
				square[t[0] + v[0]][t[1] + v[1]][t[2] + v[2]].location[2] += v[2];
				square[t[0]][t[1]][t[2]] = new Piece(PieceType.EMPTY,Player.WHITE,t[0],t[1],t[2]);
				testMove(t2, v2);
				return;
			}
		}
		
		square[t[0] + v[0]][t[1] + v[1]][t[2] + v[2]] = square[t[0]][t[1]][t[2]];
		square[t[0] + v[0]][t[1] + v[1]][t[2] + v[2]].location[0] += v[0];
		square[t[0] + v[0]][t[1] + v[1]][t[2] + v[2]].location[1] += v[1];
		square[t[0] + v[0]][t[1] + v[1]][t[2] + v[2]].location[2] += v[2];
		square[t[0]][t[1]][t[2]] = new Piece(PieceType.EMPTY,Player.WHITE,t[0],t[1],t[2]);
	}
	
	
	public void move(int[] t, int[] v) { // t is location vector, v is change vector.
		
		if (square[t[0] + v[0]][t[1] + v[1]][t[2] + v[2]].pt != PieceType.EMPTY) {
			square[t[0] + v[0]][t[1] + v[1]][t[2] + v[2]].captured = true;
		}
		
		if (square[t[0]][t[1]][t[2]].pt == PieceType.PAWN) {
			
			
			square[t[0]][t[1]][t[2]].twoStepsValid = false;
			if (((v[0] == -2 && square[t[0]][t[1]][t[2]].p == Player.BLACK) || 
					(v[0] == 2 && square[t[0]][t[1]][t[2]].p == Player.WHITE))) {
				square[t[0]][t[1]][t[2]].enPassantValid = true;
			}
			
			if (square[t[0]+v[0]][t[1]+v[1]][t[2]+v[2]].pt == PieceType.EMPTY) {
				
				if (((v[0] == -1 && square[t[0]][t[1]][t[2]].p == Player.BLACK) || 
						(v[0] == 1 && square[t[0]][t[1]][t[2]].p == Player.WHITE)) && (Math.abs(v[1]) == 1 || Math.abs(v[2]) == 1) && 
						(Math.abs(v[1]) <= 1 && Math.abs(v[2]) <= 1)) {
					square[t[0]][t[1]+v[1]][t[2]+v[2]].captured = true;
					square[t[0]][t[1]+v[1]][t[2]+v[2]] = new Piece(PieceType.EMPTY,Player.WHITE,t[0],t[1]+v[1],t[2]+v[2]);
				}
			}
		}
		
		if (square[t[0]][t[1]][t[2]].pt == PieceType.KING || square[t[0]][t[1]][t[2]].pt == PieceType.ROOK) {
			square[t[0]][t[1]][t[2]].castleValid = false;
		}
		
		if (square[t[0]][t[1]][t[2]].pt == PieceType.KING && t[1] == 4) {
			if (v[1] == 2) {
				int[] t2 = {t[0], t[1] + 3, t[2] + 3};
				int[] v2 = {0, -2, -2};
				move(t2, v2);
				turn = 1 - turn;
			}
			else if (v[1] == -2) {
				int[] t2 = {t[0], t[1] - 4, t[2] - 4};
				int[] v2 = {0, 3, 3};
				move(t2, v2);
				turn = 1 - turn;
			}
		}
		
		square[t[0] + v[0]][t[1] + v[1]][t[2] + v[2]] = square[t[0]][t[1]][t[2]];
		square[t[0] + v[0]][t[1] + v[1]][t[2] + v[2]].location[0] += v[0];
		square[t[0] + v[0]][t[1] + v[1]][t[2] + v[2]].location[1] += v[1];
		square[t[0] + v[0]][t[1] + v[1]][t[2] + v[2]].location[2] += v[2];
		square[t[0]][t[1]][t[2]] = new Piece(PieceType.EMPTY,Player.WHITE,t[0],t[1],t[2]);
		
		if ((t[0] == 0) && (t[1] != t[2]) && (t[1] != 7 - t[2]) && (gametype == 0))
			square[t[0]][t[1]][t[2]] = new Piece(PieceType.BARRICADE,Player.WHITE,t[0],t[1],t[2]);
		if ((t[0] == 7) && (t[1] != t[2]) && (t[1] != 7 - t[2]) && (gametype == 0))
			square[t[0]][t[1]][t[2]] = new Piece(PieceType.BARRICADE,Player.BLACK,t[0],t[1],t[2]); // To replace barricades, which cannot be captured, only displaced.
		turn = 1 - turn;
		
		for (int k = 0; k < 8; k++)
			for (int j = 0; j < 8; j++)
				for (int m = 0; m < 8; m++) {
					if ((square[k][j][m].p == Player.WHITE && turn == 0) || (square[k][j][m].p == Player.BLACK && turn == 1))
						square[k][j][m].enPassantValid = false;
				}
		
	}
	
	public boolean checkmate(int side) {

		if (inCheck(side) && noLegalMoves(side)) {
			return true;
		}
		
		return false;
	}
	
	public boolean stalemate(int side) {

		if (!inCheck(side) && noLegalMoves(side)) {
			return true;
		}
		
		return false;
	}
	
	public void target(Piece highlighted) {
		if ((highlighted.p == Player.WHITE && turn == 0) || (highlighted.p == Player.BLACK && turn == 1))
			for (int k = 0; k < 8; k++)
				for (int j = 0; j < 8; j++)
					for (int m = 0; m < 8; m++) {
						int[] t = new int[3];
						int[] v = new int[3];
						t[0] = highlighted.location[0]; t[1] = highlighted.location[1]; t[2] = highlighted.location[2];
						v[0] = k - t[0]; v[1] = j - t[1]; v[2] = m - t[2];
						if (highlighted.pt == PieceType.KING && k == 0 && j == 6 && m == 6) {
							System.out.println();
						}
						if (moveValid(t, v) && !intoCheck(turn, t, v)) {
							square[k][j][m].targeted = true;
						}
						if (highlighted.pt == PieceType.KING && Math.abs(v[1]) == 2) {
							int[] v2 = {0, v[1]/2, v[2]/2};
							int s;
							if (square[t[0]][t[1]][t[2]].p == Player.WHITE)
								s = 0;
							else
								s = 1;
							if (intoCheck(s, t, v2))
								square[k][j][m].targeted = false;
						}
					}

	}
	
	public void detarget() {
		for (int k = 0; k < 8; k++)
			for (int j = 0; j < 8; j++)
				for (int m = 0; m < 8; m++) {
					square[k][j][m].targeted = false;
				}
	}
	
	public void drawPieces(Graphics g, double viewAngle, double viewElevation) {
		int xpref = 7;
		int ypref = 0;
		int zpref = 7;
		
		if (viewAngle < Math.PI)
			xpref = 0;
		if (viewAngle < Math.PI / 2 || viewAngle > 3 * Math.PI / 2)
			ypref = 7;
		if (viewElevation > 0)
			zpref = 0;
		
		int xfact = -((2 * xpref / 7) - 1);
		int yfact = -((2 * ypref / 7) - 1);
		int zfact = -((2 * zpref / 7) - 1);
		
		for (int k = 0; k < 8; k++) {
			for (int j = 0; j < 8; j++) {
				for (int m = 0; m < 8; m++)
					square[(k * zfact) + zpref][(j * xfact) + xpref][(m * yfact) + ypref].Draw(g, viewAngle, viewElevation);
			}
		}
	}
}