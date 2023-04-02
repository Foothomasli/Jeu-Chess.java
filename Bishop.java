import java.util.ArrayList;

public class Bishop extends Piece {

    public String toString() {
        return "B";
    }

    @Override
    public boolean isValidMove(Position newPosition, Cell[][] board) {
        ArrayList<Position> possibleMoves = new ArrayList();

        int currentRow = 8 - this.position.getRow();
        int currentColumn = this.position.getColumn() - 97;
        // 斜线移动

        // 创建一个递归
        for (int i = 0; i <= board.length - 1; i++) {
            possibleMoves = tryCell(possibleMoves, board, currentRow + i, currentColumn + i);
            possibleMoves = tryCell(possibleMoves, board, currentRow - i, currentColumn + i);
            possibleMoves = tryCell(possibleMoves, board, currentRow + i, currentColumn - i);
            possibleMoves = tryCell(possibleMoves, board, currentRow - i, currentColumn - i);

        }

        boolean isInArray = false;
        for (Position p : possibleMoves) {
            if (p.getString().equals(newPosition.getString())) {
                isInArray = true;

            }
        }
        return isInArray;
    }
}
