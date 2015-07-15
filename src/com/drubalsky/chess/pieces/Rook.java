package com.drubalsky.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.drubalsky.chess.Board;
import com.drubalsky.chess.util.ChessPoint;

public class Rook extends Piece {

	public Rook(Board b, ChessPoint loc, boolean isWhite) {
		super(b, loc, isWhite);
	}

	@Override
	public List<ChessPoint> getPossibleMoves() {
		return getVertAndHorizLineMoves();
	}

}
