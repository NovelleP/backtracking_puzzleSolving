import java.util.HashSet;

public class Piece implements Cloneable {
	private int id;
	private int[] sides = new int[4];

	public Piece(int id, int[] sides) {
		this.id = id;
		this.sides = sides;
	}

	public int getId() {
		return id;
	}

	public int[] getSides() {
		return sides;
	}

	public boolean isCorner() {
		int zeros = 0;
		for (int n : sides)
			if (n == 0)
				zeros++;

		return (zeros == 2) ? true : false;
	}

	public boolean isBorder() {
		int zeros = 0;
		for (int n : sides)
			if (n == 0)
				zeros++;

		return (zeros == 1) ? true : false;
	}

	public boolean isInternal() {
		for (int n : sides)
			if (n == 0)
				return false;

		return true;
	}

	public int[] rotate() {
		int act = sides[0];
		int next;
		for (int i = 0; i < sides.length; i++) {
			next = sides[(i + 1) % sides.length];
			sides[(i + 1) % sides.length] = act;
			act = next;
		}
		return sides;
	}

	public Piece clone() {
		Piece clone = null;
		try {
			clone = (Piece) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		clone.sides = (int[]) clone.sides.clone();
		return clone;
	}

	public boolean hasDuplicateNumbers() {
		HashSet<Integer> set = new HashSet<>();
		for (Integer n : sides)
			if (set.add(n) == false)
				return false;
		return true;
	}

}