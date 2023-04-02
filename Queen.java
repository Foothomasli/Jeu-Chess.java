import java.util.ArrayList;

public class Queen extends Piece {

    public String toString() {
        return "Q";
    }

    @Override
    public boolean isValidMove(Position newPosition, Cell[][] board) {
        ArrayList<Position> possibleMoves = new ArrayList();

        // Generate all possible moves from the current position

        // Déplacement Vertical
        int currentColumn = this.position.getColumn() - 97;
        for (int row = 0; row <= board.length - 1; row++) {
            possibleMoves = tryCell(possibleMoves, board, row, currentColumn);
        }
        //Déplacement Horizontal
        int currentRow = 8 - this.position.getRow();
        for (currentColumn = 0; currentColumn <= board.length - 1; currentColumn++) {
            possibleMoves = tryCell(possibleMoves, board, currentRow, currentColumn);
        }

        // Déplacement Diagonaux x+1
        currentRow = 8 - this.position.getRow();
        currentColumn = this.position.getColumn() - 97;

        for (int i = 0; i <= board.length - 1; i++) {
            possibleMoves = tryCell(possibleMoves, board, currentRow + i, currentColumn + i);
            possibleMoves = tryCell(possibleMoves, board, currentRow - i, currentColumn + i);
            possibleMoves = tryCell(possibleMoves, board, currentRow + i, currentColumn - i);
            possibleMoves = tryCell(possibleMoves, board, currentRow - i, currentColumn - i);
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
