import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class App {

	private static final int height = 8;
	private static final int width = 8;
	private static final String path = "files\\\\8x8.txt";

	public static void main(String[] args) {
		Integer h = height, w = width;
		ArrayList<Piece> p = new ArrayList<Piece>();
		try {
			readPieces(p, h, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

		PuzzleSolving ps = new PuzzleSolving(w, h, p);

		long startTime = System.currentTimeMillis();

		ArrayList<Piece[][]> solutions = ps.solvePuzzle();

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("Time (ms): " + elapsedTime + "\n");

		for (Piece[][] sol : solutions) {
			printMatrix(sol);
			System.out.println("----------------------------------------------------");
		}

	}

	private static void printMatrix(Piece[][] sol) {
		for (Piece[] row : sol) {
			for (Piece p : row)
				System.out.print(p.getId() + " ");
			System.out.println();
		}
	}

	private static void readPieces(ArrayList<Piece> p, Integer h, Integer w) throws IOException {
		File file = new File(path);
		BufferedReader br = new BufferedReader(new FileReader(file));

		int index = 0;
		String st;
		while ((st = br.readLine()) != null) {
			if (index != 0)
				createPiece(st, p, index);
			else
				getHeightAndWidth(st, h, w);
			index++;
		}
	}

	private static void getHeightAndWidth(String st, Integer h, Integer w) {
		int i = 0;
		while (i < st.length() && st.charAt(i) != ' ') {
			h = h * 10 + (st.charAt(i) - '0');
			i++;
		}
		while (i < st.length() && st.charAt(i) == ' ')
			i++;
		while (i < st.length() && st.charAt(i) != ' ') {
			w = w * 10 + (st.charAt(i) - '0');
			i++;
		}

	}

	private static void createPiece(String st, ArrayList<Piece> p, int index) {
		int i = 0;
		int side1 = 0, side2 = 0, side3 = 0, side4 = 0;

		while (i < st.length() && st.charAt(i) != ' ') {
			side1 = side1 * 10 + (st.charAt(i) - '0');
			i++;
		}
		while (i < st.length() && st.charAt(i) == ' ')
			i++;

		while (i < st.length() && st.charAt(i) != ' ') {
			side2 = side2 * 10 + (st.charAt(i) - '0');
			i++;
		}
		while (i < st.length() && st.charAt(i) == ' ')
			i++;

		while (i < st.length() && st.charAt(i) != ' ') {
			side3 = side3 * 10 + (st.charAt(i) - '0');
			i++;
		}
		while (i < st.length() && st.charAt(i) == ' ')
			i++;

		while (i < st.length() && st.charAt(i) != ' ') {
			side4 = side4 * 10 + (st.charAt(i) - '0');
			i++;
		}

		int sides[] = { side1, side2, side3, side4 };

		p.add(new Piece(index, sides));

	}
}
