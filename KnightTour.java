import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class KnightTour {
    private static final int BOARD_SIZE = 8;
    private static final char[] COLUMN_LETTERS = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};

    // The possible moves a knight can make
    private static final int[][] MOVES = {{2, -1}, {1, -2}, {-1, -2}, {-2, -1}, {-2, 1}, {-1, 2}, {1, 2}, {2, 1}};

    private static int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
    private static int moveNumber = 1;

    public static void main(String[] args) {
        // Create the main window frame
        JFrame frame = new JFrame("Chess Board");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel to hold the chess board
        JPanel boardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));

        // Create the squares of the chess board
        for (int i = 0; i < BOARD_SIZE * BOARD_SIZE; i++) {
            JPanel square = new JPanel();
            if ((i / BOARD_SIZE) % 2 == 0) {
                square.setBackground(i % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY);
            } else {
                square.setBackground(i % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);
            }
            JLabel label = new JLabel();
            square.add(label);
            boardPanel.add(square);
        }

        // Add the chess board to the main window frame
        frame.add(boardPanel);

        // Show the window
        frame.setVisible(true);

        // Run the Knight's Tour algorithm
        if (knightsTour(3, 0, 1, boardPanel)) {
            System.out.println("Knight's Tour found a solution:");
            printBoard();
        } else {
            System.out.println("Knight's Tour failed to find a solution.");
        }
    }

    // Recursive method to find the Knight's Tour
    private static boolean knightsTour(int row, int col, int moveNumber, JPanel boardPanel) {
        board[row][col] = moveNumber;
        updateBoard(boardPanel, new Point(row, col), moveNumber);
        if (moveNumber == BOARD_SIZE * BOARD_SIZE) {
            return true;
        }
        for (int[] move : MOVES) {
            int nextRow = row + move[0];
            int nextCol = col + move[1];
            if (isValidMove(nextRow, nextCol)) {
                if (knightsTour(nextRow, nextCol, moveNumber + 1, boardPanel)) {
                    return true;
                }
            }
        }
        board[row][col] = 0;
        updateBoard(boardPanel, new Point(row, col), 0);
        return false;
    }

    // Helper method to check if a move is valid
    private static boolean isValidMove(int row, int col) {
        return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE && board[row][col] == 0;
    }

    // Helper method to update the board with the current move number
    private static void updateBoard(JPanel boardPanel, Point current, int moveNumber) {
        JLabel label = (JLabel) ((JPanel) boardPanel.getComponent(current.x * BOARD_SIZE + current.y)).getComponent(0);
        label.setText(Integer.toString(moveNumber));
    }

    // Helper method to print the board
    private static void printBoard() {
        System.out.println("------------------");
        for (int[] row : board) {
            for (int num : row) {
                System.out.print(num + "\t");
            }
            System.out.println();
        }
        System.out.println("------------------");
    }

    // Helper method to convert a Point on the board to chess notation
    private static String toChessNotation(Point p) {
        return COLUMN_LETTERS[p.y] + Integer.toString(p.x + 1);
    }

}

