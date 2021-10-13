import java.io.File;
import java.util.Scanner;

public class EA_Sudoku {
	private final int[][] sudokuProblem;
	private int numGenerationsToRun;
	
	// Parameters
	private final int popsSize = 10;
	
	// Initialization
	private int[][][] populations;
	
	// *** Constructor ***
	public EA_Sudoku(int[][] sudokuProblem){
		this.sudokuProblem = sudokuProblem;
	}
	
	// *** Run ***
	public void runEA(int numGenerationsToRun){
		this.numGenerationsToRun = numGenerationsToRun;

		printProblem();
		
		initialization();
	}
	
	// EA Methods
	private void initialization(){
		populations = new int[popsSize][9][9];
		
		for(int i = 0; i < popsSize; i++){
			for(int j = 0; j < 9; j++){
				for(int k = 0; k < 9; k++){
					
				}
			}
		}
		
		return;
	}
		
	private void fitnessCalculation(){
		return;
	}

	private void parentSelection(){
		return;
	}

	private void crossOver(){
		return;
	}
		
	private void replaceGeneration(){
		return;
	}

	private void mutation(){
		return;
	}
	
	// Utilities Method
	public void printProblem(){
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				System.out.print(sudokuProblem[i][j]);
			}
			System.out.println("");
		}
	}
}