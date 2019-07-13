import java.util.ArrayList;

public class PuzzleSolving {

	private int height;
	private int width;
	private ArrayList<Piece> pieces = new ArrayList<Piece>();
	private ArrayList<Piece> corners;
	private ArrayList<Piece> borders;
	private ArrayList<Piece> internals;
	private ArrayList<Piece[][]> solutions = new ArrayList<>();

	public PuzzleSolving(int w, int h, ArrayList<Piece> p) {
		height = h;
		width = w;
		pieces = p;
		
		corners = getCorners();
		borders = getBorders();
		internals = getInternals();
	}

	public ArrayList<Piece[][]> solvePuzzle() {
		Piece[][] actual = new Piece[height][width];
		solve(0, 0, actual);

		return solutions;
	}

	private void solve(int row, int column, Piece[][] actual) {
		if (row >= height) {
			if (solutions.size() == 0 || !contains(solutions, actual)) {
				solutions.add(cloneMatrix(actual, height - 1, width - 1));
			}
		}

		else {
			if (isCorner(row, column)) {
				for (int i = 0; i < corners.size(); i++) {
					Piece corner = corners.remove(i);
					actual[row][column] = corner;
					if (isCornerCorrect(actual, row, column)) {
						int column1 = (column + 1) % width;
						int row1 = row + ((column1 == 0) ? 1 : 0);
						solve(row1, column1, actual);
					}
					corners.add(i, corner);
				}

			}

			else if (isBorder(row, column)) {
				for (int i = 0; i < borders.size(); i++) {
					Piece border = borders.remove(i);
					actual[row][column] = border;
					if (isBorderCorrect(actual, row, column)) {
						int column1 = (column + 1) % width;
						int row1 = row + ((column1 == 0) ? 1 : 0);
						solve(row1, column1, actual);
					}
					borders.add(i, border);
				}
			}

			else {
				for (int i = 0; i < internals.size(); i++) {
					Piece internal = internals.remove(i);
					actual[row][column] = internal;
					int cont = 0;
					while (cont < 4) {
						if (isInternalCorrect(actual, row, column)) {
							int column1 = (column + 1) % width;
							int row1 = row + ((column1 == 0) ? 1 : 0);
							solve(row1, column1, actual);
						}
						actual[row][column].rotate();
						cont++;
					}
					internals.add(i, internal);
				}
			}
		}

	}

	private boolean contains(ArrayList<Piece[][]> solutions, Piece[][] actual) {
		int count = 0;
		for (Piece[][] solution : solutions) {
			while (count < 4) {
				if (containsAux(solution, actual) == true) {
					return true;
				}
				actual = rotateMatrix(actual);
				count++;
			}
			count = 0;
		}
		return false;
	}
	
	private boolean containsAux(Piece[][] solution, Piece[][] actual) {
		for (int i = 0; i < solution.length; i++) {
			for (int j = 0; j < solution[i].length; j++) {
				if (solution[i][j].getId() != actual[i][j].getId())
					return false;
			}
		}
		return true;
	}

	private Piece[][] rotateMatrix(Piece[][] actual) { // change row for columns
		Piece[][] rotated = new Piece[actual.length][actual[0].length];
		for (int i = 0; i < actual.length; i++) {
			Piece[] invertedColumn = getColumn(actual, actual[i].length - i - 1);
			rotated[actual.length - i - 1] = invertedColumn;
		}
		return rotated;
	}

	private Piece[] getColumn(Piece[][] actual, int column) {
		Piece[] col = new Piece[actual[column].length];

		for (int j = 0, i = actual.length - 1; i >= 0; i--, j++)
			col[j] = actual[i][column];
		return col;
	}

	private boolean isCornerCorrect(Piece[][] actual, int row, int column) {
		int[] sides = actual[row][column].getSides();
		if (row == 0 && column == 0) {
			while (sides[0] != 0 || sides[1] != 0) {
				sides = actual[row][column].rotate();
			}
			return true;
		}

		else if (row == 0 && column == width - 1) {
			while (sides[1] != 0 || sides[2] != 0) {
				sides = actual[row][column].rotate();
			}
			return (actual[row][column - 1].getSides()[2] == actual[row][column].getSides()[0]);
		}

		else if (row == height - 1 && column == 0) {
			while (sides[0] != 0 || sides[3] != 0) {
				sides = actual[row][column].rotate();
			}
			return (actual[row - 1][column].getSides()[3] == actual[row][column].getSides()[1]);
		}

		else {
			while (sides[2] != 0 || sides[3] != 0) {
				sides = actual[row][column].rotate();
			}
			return (actual[row - 1][column].getSides()[3] == actual[row][column].getSides()[1]
					&& actual[row][column - 1].getSides()[2] == actual[row][column].getSides()[0]);
		}
	}

	private boolean isBorderCorrect(Piece[][] actual, int row, int column) {
		if (row == 0) {
			while (actual[row][column].getSides()[1] != 0) {
				actual[row][column].rotate();
			}
			return (actual[row][column - 1].getSides()[2] == actual[row][column].getSides()[0]);
		}

		else if (row == height - 1) {
			while (actual[row][column].getSides()[3] != 0) {
				actual[row][column].rotate();
			}
			return (actual[row][column - 1].getSides()[2] == actual[row][column].getSides()[0]
					&& actual[row - 1][column].getSides()[3] == actual[row][column].getSides()[1]);
		}

		else if (column == 0) {
			while (actual[row][column].getSides()[0] != 0) {
				actual[row][column].rotate();
			}
			return (actual[row - 1][column].getSides()[3] == actual[row][column].getSides()[1]);
		}

		else {
			while (actual[row][column].getSides()[2] != 0) {
				actual[row][column].rotate();
			}
			return (actual[row - 1][column].getSides()[3] == actual[row][column].getSides()[1]
					&& actual[row][column - 1].getSides()[2] == actual[row][column].getSides()[0]);
		}
	}

	private boolean isInternalCorrect(Piece[][] actual, int row, int column) {
		if (actual[row - 1][column].getSides()[3] == actual[row][column].getSides()[1]
				&& actual[row][column - 1].getSides()[2] == actual[row][column].getSides()[0])
			return true;
		return false;
	}
	
	private Piece[][] cloneMatrix(Piece[][] actual, int row, int column) {
		Piece[][] result = new Piece[actual.length][actual[0].length];
		for (int i = 0; i <= row; i++)
			for (int j = 0; j <= column; j++)
				result[i][j] = actual[i][j].clone();
		return result;
	}

	private boolean isCorner(int row, int column) {
		if (row == 0 && column == 0)
			return true;
		else if (row == 0 && column == width - 1)
			return true;
		else if (row == height - 1 && column == 0)
			return true;
		else if (row == height - 1 && column == width - 1)
			return true;
		else
			return false;
	}

	private boolean isBorder(int row, int column) {
		if (row == 0 ^ column == 0)
			return true;
		else if (row == height - 1 ^ column == width - 1)
			return true;
		else
			return false;
	}

	private ArrayList<Piece> getCorners() {
		ArrayList<Piece> corners = new ArrayList<Piece>();
		for (int i = 0; i < pieces.size(); i++) {
			if (pieces.get(i).isCorner())
				corners.add(pieces.get(i));
		}
		return corners;
	}

	private ArrayList<Piece> getBorders() {
		ArrayList<Piece> borders = new ArrayList<Piece>();
		for (int i = 0; i < pieces.size(); i++) {
			if (pieces.get(i).isBorder())
				borders.add(pieces.get(i));
		}
		return borders;
	}

	private ArrayList<Piece> getInternals() {
		ArrayList<Piece> internals = new ArrayList<Piece>();
		for (int i = 0; i < pieces.size(); i++) {
			if (pieces.get(i).isInternal())
				internals.add(pieces.get(i));
		}
		return internals;
	}

}