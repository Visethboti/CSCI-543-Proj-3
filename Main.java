import java.io.File;
import java.util.Scanner;


public class Main {
	public static void main(String[] args){
		final int[][] sudokuProblem = 
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
		
		// EA For Sudoku Instances
		EA_Sudoku ea_sudoku = new EA_Sudoku(sudokuProblem);
		
		// Run the EA
		ea_sudoku.runEA(1);
		
	}
}