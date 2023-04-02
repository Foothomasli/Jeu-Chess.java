import java.util.ArrayList;

public class Rook extends Piece {

    public String toString() {
        return "R";
    }

    @Override
    public boolean isValidMove(Position newPosition, Cell[][] board) {
        ArrayList<Position> possibleMoves = new ArrayList();

        // Generate all possible moves from the current position

        // Déplacement Vertical
        int columnCharToInt = this.position.getColumn() - 97;
        for (int row = 0; row <= board.length - 1; row++) {
            possibleMoves = tryCell(possibleMoves, board, row, columnCharToInt);
        }
        //Déplacement Horizontal
        int currentRow = 8 - this.position.getRow();
        for (int currentColumn = 0; currentColumn <= board.length - 1; currentColumn++) {
            possibleMoves = tryCell(possibleMoves, board, currentRow, currentColumn);
        }

        // Compare if the newPosition is part of all the possible moves
        boolean isInArray = false;
        for (Position p : possibleMoves) {
            if (p.getString().equals(newPosition.getString())) {
                isInArray = true;

            }
        }
        return isInArray;
    }
}
