package com.drubalsky.chess.pieces;

import java.util.List;

import com.drubalsky.chess.Board;
import com.drubalsky.chess.util.ChessPoint;

public class Bishop extends Piece {

	public Bishop(Board b, ChessPoint loc, boolean isWhite) {
		super(b, loc, isWhite);
	}

	@Override
	public List<ChessPoint> getPossibleMoves() {
		return getDiagonalLineMoves();
	}
}
