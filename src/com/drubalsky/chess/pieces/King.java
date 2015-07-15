package com.drubalsky.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.drubalsky.chess.Board;
import com.drubalsky.chess.util.ChessPoint;

public class King extends Piece {

	public King(Board b, ChessPoint loc, boolean isWhite) {
		super(b, loc, isWhite);
	}

	@Override
	public List<ChessPoint> getPossibleMoves() {
		List<ChessPoint> possibleMoves = new ArrayList<ChessPoint>(4);
		int x, y;
		
		//Up
		x = loc.x;
		y = loc.y - 1;
		if (isOpenOrEnemySpot(x, y)) possibleMoves.add(new ChessPoint(x, y));
		
		//Down
		x = loc.x;
		y = loc.y + 1;
		if (isOpenOrEnemySpot(x, y)) possibleMoves.add(new ChessPoint(x, y));
		
		//Left
		x = loc.x - 1;
		y = loc.y;
		if (isOpenOrEnemySpot(x, y)) possibleMoves.add(new ChessPoint(x, y));
		
		//Right
		x = loc.x + 1;
		y = loc.y;
		if (isOpenOrEnemySpot(x, y)) possibleMoves.add(new ChessPoint(x, y));
		
		//Up right
		x = loc.x + 1;
		y = loc.y - 1;
		if (isOpenOrEnemySpot(x, y)) possibleMoves.add(new ChessPoint(x, y));
		
		//Down right
		x = loc.x + 1;
		y = loc.y + 1;
		if (isOpenOrEnemySpot(x, y)) possibleMoves.add(new ChessPoint(x, y));
		
		//Down left
		x = loc.x - 1;
		y = loc.y + 1;
		if (isOpenOrEnemySpot(x, y)) possibleMoves.add(new ChessPoint(x, y));
		
		//Up left
		x = loc.x - 1;
		y = loc.y - 1;
		if (isOpenOrEnemySpot(x, y)) possibleMoves.add(new ChessPoint(x, y));
		
		return possibleMoves;
	}

}
