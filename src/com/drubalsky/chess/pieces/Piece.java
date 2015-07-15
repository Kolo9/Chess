package com.drubalsky.chess.pieces;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.drubalsky.chess.Board;
import com.drubalsky.chess.util.ChessPoint;

public abstract class Piece {
	protected Board b;
	protected ChessPoint loc;
	protected boolean isWhite;
	
	Piece(Board b, ChessPoint loc, boolean isWhite) {
		this.b = b;
		this.loc = loc;
		this.isWhite = isWhite;
	}
	
	public abstract List<ChessPoint> getPossibleMoves();
	
	protected final List<ChessPoint> getVertAndHorizLineMoves() {
		List<ChessPoint> possibleMoves = new ArrayList<ChessPoint>(14);
		int i;
		ChessPoint cp;
		for (i = loc.x-1; i >= 0; i--) { //left
			cp = new ChessPoint(i, loc.y);
			if (b.isValidEmpty(cp)) { //Empty
				possibleMoves.add(cp);
			} else if (b.isValidFilled(cp) && b.getPiece(cp).isWhite != isWhite) { //Enemy piece
				possibleMoves.add(cp);
				break;
			} else { //My piece
				break;
			}
		}
		
		for (i = loc.x+1; i < 8; i++) { //right
			cp = new ChessPoint(i, loc.y);
			if (b.isValidEmpty(cp)) { //Empty
				possibleMoves.add(cp);
			} else if (b.isValidFilled(cp) && b.getPiece(cp).isWhite != isWhite) { //Enemy piece
				possibleMoves.add(cp);
				break;
			} else { //My piece
				break;
			}
		}
		
		for (i = loc.y-1; i >= 0; i--) { //up
			cp = new ChessPoint(loc.x, i);
			if (b.isValidEmpty(cp)) { //Empty
				possibleMoves.add(cp);
			} else if (b.isValidFilled(cp) && b.getPiece(cp).isWhite != isWhite) { //Enemy piece
				possibleMoves.add(cp);
				break;
			} else { //My piece
				break;
			}
		}
		
		for (i = loc.y+1; i < 8; i++) { //down
			cp = new ChessPoint(loc.x, i);
			if (b.isValidEmpty(cp)) { //Empty
				possibleMoves.add(cp);
			} else if (b.isValidFilled(cp) && b.getPiece(cp).isWhite != isWhite) { //Enemy piece
				possibleMoves.add(cp);
				break;
			} else { //My piece
				break;
			}
		}
		
		return possibleMoves;
	}
	
	protected final List<ChessPoint> getDiagonalLineMoves() {
		List<ChessPoint> possibleMoves = new ArrayList<ChessPoint>(14);
		int x, y;
		ChessPoint cp;
		
		x = loc.x;
		y = loc.y;
		while (b.isValid(x, y)) { //up left -- don't need the while check, oh well
			x--;
			y--;
			cp = new ChessPoint(x, y);
			if (b.isValidEmpty(cp)) { //Empty
				possibleMoves.add(cp);
			} else if (b.isValidFilled(cp) && b.getPiece(cp).isWhite != isWhite) { //Enemy piece
				possibleMoves.add(cp);
				break;
			} else { //My piece or not valid
				break;
			}
		}
		
		x = loc.x;
		y = loc.y;
		while (b.isValid(x, y)) { //up right -- don't need the while check, oh well
			x++;
			y--;
			cp = new ChessPoint(x, y);
			if (b.isValidEmpty(cp)) { //Empty
				possibleMoves.add(cp);
			} else if (b.isValidFilled(cp) && b.getPiece(cp).isWhite != isWhite) { //Enemy piece
				possibleMoves.add(cp);
				break;
			} else { //My piece or not valid
				break;
			}
		}
		
		x = loc.x;
		y = loc.y;
		while (b.isValid(x, y)) { //down left -- don't need the while check, oh well
			x--;
			y++;
			cp = new ChessPoint(x, y);
			if (b.isValidEmpty(cp)) { //Empty
				possibleMoves.add(cp);
			} else if (b.isValidFilled(cp) && b.getPiece(cp).isWhite != isWhite) { //Enemy piece
				possibleMoves.add(cp);
				break;
			} else { //My piece or not valid
				break;
			}
		}
		
		x = loc.x;
		y = loc.y;
		while (b.isValid(x, y)) { //down right -- don't need the while check, oh well
			x++;
			y++;
			cp = new ChessPoint(x, y);
			if (b.isValidEmpty(cp)) { //Empty
				possibleMoves.add(cp);
			} else if (b.isValidFilled(cp) && b.getPiece(cp).isWhite != isWhite) { //Enemy piece
				possibleMoves.add(cp);
				break;
			} else { //My piece or not valid
				break;
			}
		}
		
		return possibleMoves;
	}
	
	private boolean isBlockingCheck() {
		if (isInDiagonalWithKing()) {
			ChessPoint kingLoc = (isWhite) ? b.getWhiteKingLoc() : b.getBlackKingLoc();
			int dx = (loc.x > kingLoc.x) ? 1 : -1;
			int dy = (loc.y > kingLoc.y) ? 1 : -1;
			int x, y;
			for (x = kingLoc.x, y = kingLoc.y; b.isValidEmpty(x, y); x+=dx, y+=dy) {} //go to first invalid/piece
			if (b.isValidFilled(x, y)
					&& (b.getPiece(x, y) instanceof Bishop || b.getPiece(x, y) instanceof Queen)
					&& b.getPiece(x, y).isWhite != isWhite) return true;
			return false;
		} else if (isInVertHorizLineWithKing()) {
			ChessPoint kingLoc = (isWhite) ? b.getWhiteKingLoc() : b.getBlackKingLoc();
			int dx = (loc.x > kingLoc.x) ? 1 : (loc.x < kingLoc.x) ? -1 : 0;
			int dy = (loc.y > kingLoc.y) ? 1 : (loc.y < kingLoc.y) ? -1 : 0;
			int x, y;
			for (x = kingLoc.x, y = kingLoc.y; b.isValidEmpty(x, y); x+=dx, y+=dy) {} //go to first invalid/piece
			if (b.isValidFilled(x, y)
					&& (b.getPiece(x, y) instanceof Rook || b.getPiece(x, y) instanceof Queen)
					&& b.getPiece(x, y).isWhite != isWhite) return true;
			return false;
		}
		
		return false;
	}
	
	private boolean isInDiagonalWithKing() {
		ChessPoint kingLoc = (isWhite) ? b.getWhiteKingLoc() : b.getBlackKingLoc();
		if (Math.abs(loc.x - kingLoc.x) == Math.abs(loc.y - kingLoc.y)) return true;
		return false;
	}
	
	private boolean isInVertHorizLineWithKing() {
		ChessPoint kingLoc = (isWhite) ? b.getWhiteKingLoc() : b.getBlackKingLoc();
		if (loc.x == kingLoc.x || loc.y == kingLoc.y) return true;
		return false;
	}
	
	public final boolean moveRandomly() {
		if (isBlockingCheck()) return false;
		
		List<ChessPoint> moves = getPossibleMoves();
		
		Board copyBoard;
		Iterator<ChessPoint> iter = moves.iterator();
		while (iter.hasNext()) {
			ChessPoint move = iter.next();
			copyBoard = new Board(b);
			copyBoard.move(loc, move);
			
			if (copyBoard.amIInCheck(isWhite)) {
				//System.err.println(this + " to " + move + " would leave in check by " + copyBoard.whoAmIInCheckBy(isWhite) + ".");
				iter.remove();
			} else {
				//System.err.println(this + " to " + move + " would not leave in check.");
			}
			//System.err.println("    Current piece there: " + b.getPiece(move));
		}
		
		if (moves.size() == 0) return false;
		
		ChessPoint move = moves.get((int) (Math.random() * moves.size()));
		b.move(loc, move);
		loc = move; //Have to update in Board for King, keeping loc pointing to same object will not work due to above check.
		return true;
	}
	
	protected final boolean isOpenOrEnemySpot(int x, int y) {
		ChessPoint cp = new ChessPoint(x, y);
		return b.isValidEmpty(cp) || (b.isValidFilled(cp) && b.getPiece(cp).isWhite != isWhite);
	}
	
	public final boolean isWhite() {
		return isWhite;
	}
	
	@Override
	public String toString() {
		return ((isWhite) ? "w" : "b") + this.getClass().getSimpleName().substring(0, 2);
	}
}
