package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

    private ChessMatch chessMatch;

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public String toString() {
        return "K";
    }

    private boolean canMove(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piece(position);

        return p == null || p.getColor() != getColor();
    }

    private boolean testRookCastling(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piece(position);
        return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        // Cima
        p.setValues(position.getRow() - 1, position.getColumn());
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Diagonal superior esquerda
        p.setValues(position.getRow() - 1, position.getColumn() - 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Esquerda
        p.setValues(position.getRow(), position.getColumn() - 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Diagonal inferior esquerda
        p.setValues(position.getRow() + 1, position.getColumn() - 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Baixo
        p.setValues(position.getRow() + 1, position.getColumn());
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Diagonal inferior direita
        p.setValues(position.getRow() + 1, position.getColumn() + 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Direita
        p.setValues(position.getRow(), position.getColumn() + 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Diagonal superior direita
        p.setValues(position.getRow() - 1, position.getColumn() + 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Movimento especial Roque

        if (getMoveCount() == 0 && !chessMatch.getCheck()) {
        }
        // Lado do rei
        Position pos = new Position(position.getRow(), position.getColumn());
        if (testRookCastling(pos)) {
            pos.setColumn(pos.getColumn() + 1);
            if (getBoard().piece(pos) == null) {
                pos.setColumn(pos.getColumn() + 1);
                if (getBoard().piece(pos) == null) {
                    mat[pos.getRow()][pos.getColumn()] = true;
                }
            }
        }

        // Lado da Rainha
        Position pos2 = new Position(position.getRow(), position.getColumn() - 4);
        if (testRookCastling(pos2)) {
            pos2.setColumn(pos2.getColumn() - 1);
            if (getBoard().piece(pos2) == null) {
                pos2.setColumn(pos2.getColumn() - 1);
                if (getBoard().piece(pos2) == null) {
                    pos2.setColumn(pos2.getColumn() - 1);
                    if (getBoard().piece(pos2) == null) {
                        mat[pos2.getRow()][pos2.getColumn()] = true;
                    }
                }
            }
        }

        return mat;
    }

}
