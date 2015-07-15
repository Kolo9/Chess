package com.drubalsky.chess;

import java.util.ArrayList;
import java.util.List;

import com.drubalsky.chess.pieces.Bishop;
import com.drubalsky.chess.pieces.King;
import com.drubalsky.chess.pieces.Knight;
import com.drubalsky.chess.pieces.Pawn;
import com.drubalsky.chess.pieces.Piece;
import com.drubalsky.chess.pieces.Queen;
import com.drubalsky.chess.pieces.Rook;
import com.drubalsky.chess.util.ChessPoint;

public class Board {

	private Piece[][] board = new Piece[8][8];
	private ChessPoint whiteKingLoc, blackKingLoc;
	
	public Board(Board b) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (b.board[i][j] != null) {
					if (b.board[i][j] instanceof Bishop) {
						board[i][j] = new Bishop(this, new ChessPoint(j, i), b.board[i][j].isWhite());
					} else if (b.board[i][j] instanceof King) {
						board[i][j] = new King(this, new ChessPoint(j, i), b.board[i][j].isWhite());
					} else if (b.board[i][j] instanceof Knight) {
						board[i][j] = new Knight(this, new ChessPoint(j, i), b.board[i][j].isWhite());
					} else if (b.board[i][j] instanceof Pawn) {
						board[i][j] = new Pawn(this, new ChessPoint(j, i), b.board[i][j].isWhite());
					} else if (b.board[i][j] instanceof Queen) {
						board[i][j] = new Queen(this, new ChessPoint(j, i), b.board[i][j].isWhite());
					} else if (b.board[i][j] instanceof Rook) {
						board[i][j] = new Rook(this, new ChessPoint(j, i), b.board[i][j].isWhite());
					} else {
						System.err.println("Board copy constructor - what's going on");
						System.exit(1);
					}
				}
			}
		}
		
		whiteKingLoc = new ChessPoint(b.whiteKingLoc.x, b.whiteKingLoc.y);
		blackKingLoc = new ChessPoint(b.blackKingLoc.x, b.blackKingLoc.y);
	}
	
	public Board() {
		//BLACK
		int y = 0;
		int x = 0;
		board[y][x] = new Rook(this, new ChessPoint(x, y), false);
		x++;
		board[y][x] = new Knight(this, new ChessPoint(x, y), false);
		x++;
		board[y][x] = new Bishop(this, new ChessPoint(x, y), false);
		x++;
		board[y][x] = new Queen(this, new ChessPoint(x, y), false);
		x++;
		board[y][x] = new King(this, blackKingLoc = new ChessPoint(x, y), false);
		x++;
		board[y][x] = new Bishop(this, new ChessPoint(x, y), false);
		x++;
		board[y][x] = new Knight(this, new ChessPoint(x, y), false);
		x++;
		board[y][x] = new Rook(this, new ChessPoint(x, y), false);
		y = 1;
		for (x = 0; x < 8; x++) {
			board[y][x] = new Pawn(this, new ChessPoint(x, y), false);
		}
		
		
		//WHITE
		y = 7;
		x = 0;
		board[y][x] = new Rook(this, new ChessPoint(x, y), true);
		x++;
		board[y][x] = new Knight(this, new ChessPoint(x, y), true);
		x++;
		board[y][x] = new Bishop(this, new ChessPoint(x, y), true);
		x++;
		board[y][x] = new Queen(this, new ChessPoint(x, y), true);
		x++;
		board[y][x] = new King(this, whiteKingLoc = new ChessPoint(x, y), true);
		x++;
		board[y][x] = new Bishop(this, new ChessPoint(x, y), true);
		x++;
		board[y][x] = new Knight(this, new ChessPoint(x, y), true);
		x++;
		board[y][x] = new Rook(this, new ChessPoint(x, y), true);
		y = 6;
		for (x = 0; x < 8; x++) {
			board[y][x] = new Pawn(this, new ChessPoint(x, y), true);
		}
	};
	
	public void move(ChessPoint start, ChessPoint end) {
		board[end.y][end.x] = board[start.y][start.x];
		board[start.y][start.x] = null;
		
		if (board[end.y][end.x] instanceof King) {
			if (board[end.y][end.x].isWhite()) {
				whiteKingLoc.x = end.x;
				whiteKingLoc.y = end.y;
			} else {
				blackKingLoc.x = end.x;
				blackKingLoc.y = end.y;
			}
		}
	}

	public boolean isValidEmpty(ChessPoint cp) {
		return isValidEmpty(cp.x, cp.y);
	}
	public boolean isValidEmpty(int x, int y) {
		if (!isValid(x, y)) return false;
		return board[y][x] == null;
	}
	
	public boolean isValidFilled(ChessPoint cp) {
		return isValidFilled(cp.x, cp.y);
	}
	public boolean isValidFilled(int x, int y) {
		if (!isValid(x, y)) return false;
		return board[y][x] != null;
	}
	
	public boolean isValid(ChessPoint cp) {
		return isValid(cp.x, cp.y);
	}
	public boolean isValid(int x, int y) {
		return x >= 0 && y >= 0 && x < 8 && y < 8;
	}
	
	public Piece getPiece(ChessPoint cp) {
		return getPiece(cp.x, cp.y);
	}
	public Piece getPiece(int x, int y) {
		return board[y][x];
	}
	
	public ChessPoint getWhiteKingLoc() {
		return whiteKingLoc;
	}
	
	public ChessPoint getBlackKingLoc() {
		return blackKingLoc;
	}
	
	public boolean amIInCheck(boolean isWhite) {
		ChessPoint kingLoc = (isWhite) ? whiteKingLoc : blackKingLoc;
		Piece p;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				p = board[i][j];
				if (p != null && p.isWhite() != isWhite && p.getPossibleMoves().contains(kingLoc)) return true;
			}
		}
		
		return false;
	}
	
	//FOR TESTING
	public String whoAmIInCheckBy(boolean isWhite) {
		ChessPoint kingLoc = (isWhite) ? whiteKingLoc : blackKingLoc;
		Piece p;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				p = board[i][j];
				if (p != null && p.isWhite() != isWhite && p.getPossibleMoves().contains(kingLoc)) return p + " - (" + j + ", " + i + ")";
			}
		}
		
		return "";
	}
	
	public List<Piece> getMyPieces(boolean isWhite) {
		List<Piece> myPieces = new ArrayList<Piece>(16);
		Piece p;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				p = board[i][j];
				if (p != null && p.isWhite() == isWhite) myPieces.add(p);
			}
		}
		
		return myPieces;
	}
	
	@Override
	public String toString() {
		String s = "";
		/*
		s += "POSSIBLE MOVES:\n";
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] != null) s += board[i][j] + " - (" + j + ", " + i + "): " + board[i][j].getPossibleMoves() + "\n";
			}
		}
		
		s += "\n\n";
		*/
		
		s += "---|---|---|---|---|---|---|---\n";
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] == null) s += "   |";
				else s += board[i][j] + "|";
			}
			s += "\n---|---|---|---|---|---|---|---\n";
		}
		return s;
	}
}
