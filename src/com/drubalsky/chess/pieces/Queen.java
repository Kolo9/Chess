package com.drubalsky.chess.pieces;

import java.util.List;

import com.drubalsky.chess.Board;
import com.drubalsky.chess.util.ChessPoint;

public class Queen extends Piece {

	public Queen(Board b, ChessPoint loc, boolean isWhite) {
		super(b, loc, isWhite);
	}

	@Override
	public List<ChessPoint> getPossibleMoves() {
		List<ChessPoint> possibleMoves = getVertAndHorizLineMoves();
		possibleMoves.addAll(getDiagonalLineMoves());
		return possibleMoves;
	}

}
