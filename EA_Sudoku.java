import java.io.File;
import java.util.Scanner;
import java.util.Random;

public class EA_Sudoku {
	private final int[][] sudokuProblem;
	private int numGenerationsToRun;
	private static Random random;
	
	// Parameters
	private final int popsSize = 10;
	
	// Initialization
	private int[][][] populations;
	
	// *** Constructor ***
	public EA_Sudoku(int[][] sudokuProblem){
		this.sudokuProblem = sudokuProblem;
		this.random = new Random();
	}
	
	// *** Run ***
	public void runEA(int numGenerationsToRun){
		this.numGenerationsToRun = numGenerationsToRun;
		
		initialization();
		printPopulations();
		
		
	}
	
	// EA Methods
	private void initialization(){
		populations = new int[popsSize][9][9];
		int randomValue;
		
		for(int i = 0; i < popsSize; i++){
			// add the pre-filled
			for(int j = 0; j < 9; j++){
				for(int k = 0; k < 9; k++){
					if(sudokuProblem[j][k] != 0){
						populations[i][j][k] = sudokuProblem[j][k];
					}
				}
			}
			
			// fill in the empty 
			for(int j = 0; j < 9; j++){
				for(int k = 0; k < 9; k++){
					if(sudokuProblem[j][k] == 0){
						do{
							randomValue = random.nextInt(9)+1;
						}while(notExistIn(populations[i][j], randomValue));
						populations[i][j][k] = randomValue;
					}
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
	
	private void printPopulations(){
		for(int i = 0; i < popsSize; i++){
			// add the pre-filled
			for(int j = 0; j < 9; j++){
				for(int k = 0; k < 9; k++){
					System.out.print(populations[i][j][k]);
				}
				System.out.println("");
			}
			System.out.println("================");
		}
	}
	
	private boolean notExistIn(int[] array, int x){
		for(int i = 0; i < 9; i++){
			if(array[i] == x)
				return true;
		}
		return false;
	}
}