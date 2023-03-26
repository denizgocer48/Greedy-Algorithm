import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class main {
	public static int Gold_Amount;
	public static int Max_Level_Allowed ;
	public static int number_of_avaýble_pieces_per_level;

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter gold amount");
		Gold_Amount= sc.nextInt();
		System.out.println("Please enter max level allowed");
		Max_Level_Allowed= sc.nextInt();
		System.out.println("Please enter number of avaýble pieces per level");
		number_of_avaýble_pieces_per_level= sc.nextInt();
		
		Piece[] pieces = new Piece[90];
		Piece[] pieces2 = new Piece[90];
		pieces = readfile();
		long startTime = System.nanoTime();
		pieces2 = greedy(pieces);
		long endtime = System.nanoTime();
		long totaltime = endtime - startTime;
		int sum = 0;
		for (int i = 0; i < pieces2.length; i++) {
			if (pieces2[i] != null) {
				sum += pieces2[i].getAttack_power();
			}

		}
		System.out.println("Greedy:");
		System.out.println("Total attack power: " + sum);
		System.out.println("Total time is: " + totaltime);
	}

	static Piece[] readfile() throws FileNotFoundException {// reading a file
		Piece[] pieces = new Piece[90];
		int i = 0;
		Scanner x = new Scanner(new File("input_1.csv"));
		x.nextLine();
		while (x.hasNextLine()) {
			String line = x.nextLine();
			String[] sc = line.split(",");
			Piece piece = new Piece(sc[0], sc[1], Integer.parseInt(sc[2]), Integer.parseInt(sc[3]));
			pieces[i] = piece;
			i++;
		}
		x.close();
		return pieces;

	}

	static Piece[] greedy(Piece[] pieces) {
		int level = Max_Level_Allowed * 10;
		int[] indexes = new int[Max_Level_Allowed * 10];
		int[] bigest = new int[Max_Level_Allowed * 10];
		for (int i = 0; i < level; i++) {// finding values of pieces
			int value = pieces[i].getAttack_power() / pieces[i].getCost();
			bigest[i] = value;
		}
		for (int j = 0; j < bigest.length; j++) {// to find biggest value on array
			int max = bigest[0];
			int big = 0;
			for (int i = 1; i < bigest.length; i++)
				if (bigest[i] > max) {
					max = bigest[i];
					big = i;
				}
			bigest[big] = -1;
			indexes[j] = big;
		}
		int a = 0;
		Piece[] piece = new Piece[number_of_avaýble_pieces_per_level * Max_Level_Allowed];
		int gold = Gold_Amount;
		// adding pieces to array according to our money
		for (int i = 0; i < indexes.length; i++) {
			if (gold > pieces[indexes[i]].getCost() && a < number_of_avaýble_pieces_per_level * Max_Level_Allowed
					&& number_of_pieces(pieces[indexes[i]].getType(), piece)) {
				gold = gold - pieces[indexes[i]].getCost();
				piece[a] = pieces[indexes[i]];
				a++;
			}
		}
		return piece;

	}

	static boolean number_of_pieces(String type, Piece[] pieces) {// we check the number of pieces we can use for each
																	// level
		int piecenumber = 0;
		for (int i = 0; i < pieces.length; i++) {
			if (pieces[i] == null) {
				break;
			}
			if (pieces[i].getType() == type) {
				piecenumber++;
			}
		}
		if (piecenumber < number_of_avaýble_pieces_per_level) {
			return true;
		} else {
			return false;
		}

	}
}

class Piece {
	private String name;
	private String type;
	private int cost;
	private int attack_power;

	public Piece(String name, String type, int cost, int attack_power) {
		super();
		this.name = name;
		this.type = type;
		this.cost = cost;
		this.attack_power = attack_power;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getAttack_power() {
		return attack_power;
	}

	public void setAttack_power(int attack_power) {
		this.attack_power = attack_power;
	}

}
