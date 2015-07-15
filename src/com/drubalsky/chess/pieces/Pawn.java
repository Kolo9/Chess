package com.drubalsky.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.drubalsky.chess.Board;
import com.drubalsky.chess.util.ChessPoint;

public class Pawn extends Piece {

	public Pawn(Board b, ChessPoint loc, boolean isWhite) {
		super(b, loc, isWhite);
	}

	@Override
	public List<ChessPoint> getPossibleMoves() {
		List<ChessPoint> possibleMoves = new ArrayList<ChessPoint>(4);
		int moveRow;
		if (isWhite) {
			moveRow = loc.y - 1;
			if (loc.y == 6 || b.isValidEmpty(loc.x, 4)) possibleMoves.add(new ChessPoint(loc.x, 4));
		} else {
			moveRow = loc.y + 1;
			if (loc.y == 1 || b.isValidEmpty(loc.x, 3)) possibleMoves.add(new ChessPoint(loc.x, 3));
		}
		
		if (b.isValidEmpty(loc.x, moveRow)) possibleMoves.add(new ChessPoint(loc.x, moveRow));
		if (b.isValidFilled(loc.x+1, moveRow) && b.getPiece(loc.x+1, moveRow).isWhite != isWhite) possibleMoves.add(new ChessPoint(loc.x+1, moveRow));
		if (b.isValidFilled(loc.x-1, moveRow) && b.getPiece(loc.x-1, moveRow).isWhite != isWhite) possibleMoves.add(new ChessPoint(loc.x-1, moveRow));
		
		return possibleMoves;
	}

}
