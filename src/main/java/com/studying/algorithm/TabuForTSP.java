package com.studying.algorithm;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * Solve TSP problem based on tabu search algorithm
 * article: https://titanwolf.org/Network/Articles/Article?AID=4ec2e04a-3be9-4d31-90ef-1346575da21d
 * dataset: https://people.sc.fsu.edu/~jburkardt/datasets/tsp/tsp.html
 */
public class TabuForTSP {

	private int MAX_GEN;//Number of iterations
	private int N;//Number of neighbors searched each time
	private int ll;//taboo length
	private int cityNum;//Number of cities, code length

	private int [] [] distance;//distance matrix
	private int bestT;//Best modern number

	private int [] Ghh;//The initial path encoding
	private int [] bestGh;//The best path encoding
	private int bestEvaluation;
	private int [] LocalGhh;//Contemporary best coding
	private int localEvaluation;
	private int [] tempGhh;//store temporary code
	private int tempEvaluation;

	private int [] [] jinji;//tabu list

	private int t;//Current algebra

	private Random random;

	public TabuForTSP() {

	}

	/**
	 * constructor of GA
	 * 
	 * @param n
	 * Number of cities
	 * @param g
	 * Running algebra
	 * @param c
	 * Number of neighbors searched each time
	 * @param m
	 * Taboo length
	 * 
	 **/
	public TabuForTSP(int n, int g, int c, int m) {
		cityNum = n;
		MAX_GEN = g;
		N = c;
		ll = m;
	}

	//Give the compiler an instruction telling it to keep silent about certain warnings inside the annotated code element
	@SuppressWarnings ("resource")
	/**
	 * Initialize Tabu algorithm class
	 * @param filename Data file name, this file stores all city node coordinate data
	 * @throws IOException
	 */
	private void init (String filename) throws IOException {
		//read data
		int [] x;
		int [] y;
		String strbuff;
		BufferedReader data = new BufferedReader (new InputStreamReader (
				new FileInputStream (filename)));
		distance = new int [cityNum] [cityNum];
		x = new int [cityNum];
		y = new int [cityNum];
		for (int i = 0; i <cityNum; i ++) {
			//Read one line of data, data format 1 6734 1453
			strbuff = data.readLine ();
			//character segmentation
			String [] strcol = strbuff.split (" ");
			x [i] = Integer.valueOf (strcol [1]);//x coordinate
			y [i] = Integer.valueOf (strcol [2]);//y coordinate
		}
		//Calculate the distance matrix
		//For specific problems, the distance calculation method is also different. Att48 is used as a case here, it has 48 cities, the distance calculation method is pseudo Euclidean distance, and the optimal value is 10628
		for (int i = 0; i <cityNum-1; i ++) {
			distance [i] [i] = 0;//Diagonal line is 0
			for (int j = i + 1; j <cityNum; j ++) {
				double rij = Math
						.sqrt (((x [i]-x [j]) * (x [i]-x [j]) + (y [i]-y [j])
								* (y [i]-y [j]))/10.0);
				//rounded, rounded
				int tij = (int) Math.round (rij);
				if (tij <rij) {
					distance [i] [j] = tij + 1;
					distance [j] [i] = distance [i] [j];
				} else {
					distance [i] [j] = tij;
					distance [j] [i] = distance [i] [j];
				}
			}
		}
		distance [cityNum-1] [cityNum-1] = 0;

		Ghh = new int [cityNum];
		bestGh = new int [cityNum];
		bestEvaluation = Integer.MAX_VALUE;
		LocalGhh = new int [cityNum];
		localEvaluation = Integer.MAX_VALUE;
		tempGhh = new int [cityNum];
		tempEvaluation = Integer.MAX_VALUE;

		jinji = new int [ll] [cityNum];
		bestT = 0;
		t = 0;

		random = new Random (System.currentTimeMillis ());
		/*
		 * for (int i = 0; i <cityNum; i ++) {for (int j = 0; j <cityNum; j ++) {
		 * System.out.print (distance [i] [j] + ",");} System.out.println ();}
		 */

	}

	//Initialization code Ghh
	void initGroup () {
		int i, j;
		Ghh [0] = random.nextInt (65535)% cityNum;
		for (i = 1; i <cityNum;)//code length
		{
			Ghh [i] = random.nextInt (65535)% cityNum;
			for (j = 0; j <i; j ++) {
				if (Ghh [i] == Ghh [j]) {
					break;
				}
			}
			if (j == i) {
				i ++;
			}
		}
	}

	//Copy code body, copy code Gha to Ghb
	public void copyGh (int [] Gha, int [] Ghb) {
		for (int i = 0; i <cityNum; i ++) {
			Ghb [i] = Gha [i];
		}
	}

	public int evaluate (int [] chr) {
		//0123
		int len = 0;
		//Encoding, starting city, city 1, city 2 ... city n
		for (int i = 1; i <cityNum; i ++) {
			len += distance [chr [i-1]] [chr [i]];
		}
		//City n, starting city
		len += distance [chr [cityNum-1]] [chr [0]];
		return len;
	}

	//Neighbor exchange, get neighbor
	public void Linju (int [] Gh, int [] tempGh) {
		int i, temp;
		int ran1, ran2;

		for (i = 0; i <cityNum; i ++) {
			tempGh [i] = Gh [i];
		}
		ran1 = random.nextInt (65535)% cityNum;
		ran2 = random.nextInt (65535)% cityNum;
		while (ran1 == ran2) {
			ran2 = random.nextInt (65535)% cityNum;
		}
		temp = tempGh [ran1];
		tempGh [ran1] = tempGh [ran2];
		tempGh [ran2] = temp;
	}

	//judge whether the code is in the taboo list
	public int panduan (int [] tempGh) {
		int i, j;
		int flag = 0;
		for (i = 0; i <ll; i ++) {
			flag = 0;
			for (j = 0; j <cityNum; j ++) {
				if (tempGh [j] != jinji[i][j]) {
					flag = 1;//Not the same
					break;
				}
			}
			if (flag == 0)//same, return the same
			{
				//return 1;
				break;
			}
		}
		if (i == ll)//Not equal
		{
			return 0;//does not exist
		} else {
			return 1;//exists
		}
	}

	//Remove taboos and join taboos
	public void jiejinji (int [] tempGh) {
		int i, j, k;
		//Delete the first code of the tabu list, and move the code forward
		for (i = 0; i <ll-1; i ++) {
			for (j = 0; j <cityNum; j ++) {
				jinji [i] [j] = jinji [i + 1] [j];
			}
		}

		//New code added to the tabu list
		for (k = 0; k <cityNum; k ++) {
			jinji [ll-1] [k] = tempGh [k];
		}

	}

	public void solve () {
		int nn;
		//Initialization code Ghh
		initGroup ();
		copyGh (Ghh, bestGh);//Copy the current encoding Ghh to the best encoding bestGh
		bestEvaluation = evaluate (Ghh);

		while (t <MAX_GEN) {
			nn = 0;
			localEvaluation = Integer.MAX_VALUE;
			while (nn <N) {
				Linju (Ghh, tempGhh);//Get the neighborhood code tempGhh of the current code Ghh
				if (panduan (tempGhh) == 0)//judge whether the code is in the tabu list
				{
					//not in
					tempEvaluation = evaluate (tempGhh);
					if (tempEvaluation <localEvaluation) {
						copyGh (tempGhh, LocalGhh);
						localEvaluation = tempEvaluation;
					}
					nn ++;
				}
			}
			if (localEvaluation <bestEvaluation) {
				bestT = t;
				copyGh (LocalGhh, bestGh);
				bestEvaluation = localEvaluation;
			}
			copyGh (LocalGhh, Ghh);

			//Remove the taboo list, add LocalGhh to the taboo list
			jiejinji (LocalGhh);
			t ++;
		}

		System.out.println ("The best length out of modern numbers:");
		System.out.println (bestT);
		System.out.println ("Optimum length");
		System.out.println (bestEvaluation);
		System.out.println ("Best path:");
		for (int i = 0; i <cityNum; i ++) {
			System.out.print (bestGh [i] + ",");
		}
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main (String [] args) throws IOException {
		System.out.println ("Start ....");
		TabuForTSP tabu = new TabuForTSP(48, 1000, 200, 20);
		tabu.init ("/Users/junweizhang/workspace/me/github/spring_use/src/main/resources/dataset/algorithm/att48.txt");
		tabu.solve ();
	}
}