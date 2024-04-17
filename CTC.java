import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CTC {

	/**
	 * Some Abbreviations used:
	 * 		BFM -> Brute Force Method
	 * 		PM --> Parallel Method
	 * 		OCM -> Optimized Column Method
	 * @param args
	 */
	public static void main(String[] args) {
		int[] startingNumbers = { 0, 1, 4, 5, 8, 9 };
		int row = 5233;
		int col = 6;
		StopwatchCPU sw;

		/*BRUTE FORCE TESTING for SMSPortal Code. TODO: Separate testing for these */
		int [][] BF_grid = BF_generateGrid(row);
		sw = new StopwatchCPU();
		System.out.println ("Code found via BFM: "+ extractElement(row, col, BF_grid));
		System.out.println("Time Taken: "+ sw.elapsedTime());

		/*PARALLEL FIND TESTING */
		int [][] Parallel_grid = Parallel_generateGrid(row, startingNumbers);
		sw = new StopwatchCPU();
		System.out.println ("Code found via PM: "+ extractElement(row, col, Parallel_grid));
		System.out.println("Time Taken: "+ sw.elapsedTime());

		/*OPTIMIZED COLUMN TESTING */
		sw = new StopwatchCPU();
		System.out.println("Code found via OCM: "+ valueAt_row(row, startingNumbers[col-1], true));
		System.out.println("Time Taken: "+ sw.elapsedTime());
	}

	/*******************************************************************
	 * Optimised Column Method
	 *******************************************************************/
	/**
	 * Optimises space usage by not generating an array
	 * @param rowToFind desired row (given that the row we are starting at is row 1)
	 * @param row1Value
	 * @param first if it is the first row then set the add10 variable to true otherwise false
	 * so the algorithm knows whent to add 10 or add 2 to follow the pattern and crack the code
	 * @return
	 */
	 public static int valueAt_row(int rowToFind, int row1Value, boolean first){
		int insert = row1Value;
		boolean add10 = true;
		for (int row = 1; row <= rowToFind; row++) {
			if (row == 1) {
				continue;
			} else if (add10) {
				insert += 10;
			} else {
				insert += 2;
			}
			add10 = !add10; /* flip the switch */
		}
		return insert;
	 }

	/*******************************************************************
	 * Parallel Generation
	 *******************************************************************/
	public static int[][] Parallel_generateGrid(int rowCount, int[] startingNumbers) {
		/* INITIALIZATION of algorithm */
		int nRows = rowCount;
		if (rowCount % 2 != 0)
			nRows = rowCount + 1;
		int[][] out = new int[nRows][6];

		ExecutorService executor = Executors.newFixedThreadPool(6);
		for (int col = 1; col <= 6; col++) {
			final int currentCol = col;
			executor.execute((() -> genColumn(out, currentCol, startingNumbers[currentCol - 1])));
		}

		// Shutdown the executor
		executor.shutdown();

		try {
			// Wait for all tasks to finish
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return out;
	}

	public static void genColumn(int[][] grid, int col, int startingNumber) {
		int insert = startingNumber;
		boolean add10 = true;
		for (int row = 0; row < grid.length; row++) {
			if (row == 0) {
				grid[row][col - 1] = insert;
			} else if (add10) {
				insert += 10;
				grid[row][col - 1] = insert;
			} else {
				insert += 2;
				grid[row][col - 1] = insert;
			}
			add10 = !add10; /* flip the switch */
		}
	}

	/*******************************************************************
	 * Brute Force Generation Methods
	 *******************************************************************/
	public static int[][] BF_generateGrid(int rowCount) {
		int hPtr = 1; /* Ranges 1-3 */
		int vPtr = 1; /* Ranges 1-2 */
		int row = 0, col = 0; /* Track the index in the grid */
		int i = 0;

		/* Code should populate 2 rows at a time */

		/* INITIALIZATION of algorithm */
		int nRows = rowCount;
		if (rowCount % 2 != 0)
			nRows = rowCount + 1;
		int[][] grid = init2D(nRows, 6);

		/* Population of Grid */
		System.out.println("nRows: " + nRows);
		while (row + 1 < nRows) {
			/*
			 * Algorithm
			 * 1. Fill Cell with I (grid[row][col] = i)
			 * <i++>
			 * 2. col ++;
			 * 3. Fill Cell with I
			 * <i++>
			 * 4. col --; row++;
			 * 5. Fill Cell with I
			 * <i++>
			 * 6. col ++;
			 * 7. Fill Cell with I
			 * <i++>
			 * 8. -- Set up for next section: hPtr++; col++; row--;
			 * 9. IF (hPtr >=3){
			 * Reset col to 0
			 * hPtr = 1;
			 * Row+=2 (to start a new double row and to undo the -- in 8 while adding 1)}
			 * 10. Return to 1.
			 */

			/* This if statement is in a way step 8.5 */
			if (hPtr <= 3) {
				/* Step 1- 7 */
				grid[row][col] = i;
				i++;
				col++;
				grid[row][col] = i;
				i++;
				col--;
				row++;
				grid[row][col] = i;
				i++;
				col++;
				// System.out.println("row: " + row + " col: " + col + " VALUE: " + i);
				grid[row][col] = i;
				i++;

				/* Step 8 */
				hPtr++;
				col++;
				row--;
			} else {
				/* Step 9 */
				// System.out.println("RESET");
				col = 0;
				hPtr = 1;
				row += 2;
			}
		}

		return grid;
	}

	public static int[] BF_extractRow(int rowNr, int[][] grid) {
		int[] out = new int[6];
		for (int i = 0; i < 6; i++) {
			out[i] = grid[rowNr - 1][i];
		}
		return out;
	}



	/*******************************************************************
	 * General Matrix/2D array methods
	 *******************************************************************/

	public static int[][] init2D(int rows, int cols) {
		int[][] out = new int[rows][cols];
		for (int r = 0; r < out.length; r++) {
			for (int c = 0; c < out[0].length; c++) {
				out[r][c] = 0;
			}
		}
		return out;
	}

	public static void printArr(int[][] array) {
		for (int r = 0; r < array.length; r++) {
			System.out.print("ROW: " + (r + 1) + "\t|");
			for (int c = 0; c < array[0].length; c++) {
				System.out.print(array[r][c] + "\t|");
			}
			System.out.println();
		}
	}

	public static void printArr(int[] array) {
		for (int c = 0; c < array.length; c++) {
			System.out.print(array[c] + "\t|");
		}
		System.out.println();
	}

	public static int extractElement(int rowNr, int colNr, int[][] grid) {
		int[] row = BF_extractRow(rowNr, grid);
		return row[colNr - 1];
	}

}