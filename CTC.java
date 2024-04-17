public class CTC {

	public static void main(String[] args) {
		printArr(BF_generateGrid(5233));
		System.out.println("SINGLE ROW EXTRACTION");
		printArr(BF_extractRow(5233,BF_generateGrid(5233)));
		System.out.println("ROW 5233 column 6: "+BF_extractElement(5233,6, BF_generateGrid(5250)));
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
				//System.out.println("row: " + row + " col: " + col + " VALUE: " + i);
				grid[row][col] = i;
				i++;

				/* Step 8 */
				hPtr++;
				col++;
				row--;
			} else {
				/* Step 9 */
				//System.out.println("RESET");
				col = 0;
				hPtr = 1;
				row += 2;
			}
		}

		return grid;
	}

	public static int [] BF_extractRow (int rowNr, int[][] grid) {
		int [] out = new int[6];
		for (int i = 0; i<6; i++){
			out[i]= grid[rowNr-1][i];
		}
		return out;
	}

	public static int BF_extractElement (int rowNr, int colNr, int[][] grid){
		int [] row = BF_extractRow(rowNr, grid);
		return row[colNr-1];
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
			System.out.print("ROW: " + (r+1) + "\t|");
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

}