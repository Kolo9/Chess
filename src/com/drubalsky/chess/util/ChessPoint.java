package com.drubalsky.chess.util;

public class ChessPoint {
	
	public int x;
	public int y;
	
	public ChessPoint(int x, int y) {
		/*if (x > 7 || y > 7 || x < 0 || y < 0) {
			System.err.println("INVALID CHESSPOINT");
			System.exit(0);
		}*/
		this.x = x;
		this.y = y;
	}
	
	@Override
	public int hashCode() {
		return x * 8 + y;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ChessPoint)) return false;
		ChessPoint cp2 = (ChessPoint) obj;
		return x == cp2.x && y == cp2.y;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
