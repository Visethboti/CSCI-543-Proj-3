import java.io.File;
import java.util.Scanner;


public class Main {
	public static void main(String[] args){
		final int[][] sudokuProblem1 = 
		{
			{5,3,0,0,7,0,0,0,0},
			{6,0,0,1,9,5,0,0,0},
			{0,9,8,0,0,0,0,6,0},
			{8,0,0,0,6,0,0,0,3},
			{4,0,0,8,0,3,0,0,1},
			{7,0,0,0,2,0,0,0,6},
			{0,6,0,0,0,0,2,8,0},
			{0,0,0,4,1,9,0,0,5},
			{0,0,0,0,8,0,0,7,9},
		};
		
		final int[][] sudokuProblem2 = 
		{
			{0,0,0,2,6,0,7,0,1},
			{6,8,0,0,0,0,0,9,0},
			{1,9,0,0,0,0,5,0,0},
			{8,2,0,1,0,0,0,4,0},
			{0,0,4,6,0,2,9,0,0},
			{0,5,0,0,0,3,0,2,8},
			{0,0,9,3,0,0,0,7,4},
			{0,4,0,0,5,0,0,3,6},
			{7,0,3,0,1,8,0,0,0},
		};
		
		final int[][] sudokuProblem3 = 
		{
			{5,0,1,0,0,0,6,0,4},
			{0,9,0,3,0,6,0,5,0},
			{0,0,0,0,9,0,0,0,0},
			{4,0,0,0,0,0,0,0,9},
			{0,0,0,1,0,9,0,0,0},
			{7,0,0,0,0,0,0,0,6},
			{0,0,0,0,2,0,0,0,0},
			{0,8,0,5,0,7,0,6,0},
			{1,0,3,0,0,0,7,0,2},
		};
		
		final int[][] sudokuProblem0 = 
		{
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0},
		};
		
		
		// EA For Sudoku Instances
		EA_Sudoku ea_sudoku = new EA_Sudoku(sudokuProblem1);
		
		long start = System.currentTimeMillis();
		
		// Run the EA
		ea_sudoku.runEA(500);
		
		long finished = System.currentTimeMillis();
		double timeElapsed = (finished - start) / (double)1000;
		System.out.println("================");
		System.out.println("It took " + timeElapsed + " seconds");
	}
}