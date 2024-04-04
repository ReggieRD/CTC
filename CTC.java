public class CTC {
	
	public static int[][] generate_grid (int rowCount){
		int hPtr = 1; /*Ranges 1-3*/
		int vPtr = 1; /*Ranges 1-2*/
		/*Code should populate 2 rows at a time*/
		int nRows;
		if (rowCount % 2 != 0) nRows = rowCount + 1;
		int [][] grid = init2D(nRows,6);
	}

	public static int [] extract_row (int rowNr, int[][] grid) {

	}

	public static int [][] init2D (int rows, int cols){
		int [][] out = new int [rows][cols];
		for (int r=0; r<out.length; r++){
			for (int c=0; c<out[0].length; c++){
				out[r][c] = 0;
			}
		}
		return out;
	}

	public static void printArr(int[][] array){
		for (int r=0; r< array.length; r++){
			for (int c=0; c<array[0].length; c++){
				System.out.print(array[r][c] + "\t|");
			}
			System.out.println();
		}
	}

	public static void main (String [] args) {
		System.out.println(extract_row(100, generate_grid(105));
	}

}
