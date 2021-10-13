import java.io.File;
import java.util.Scanner;
import java.util.Random;

public class EA_Sudoku {
	private final int[][] sudokuProblem;
	private int numGenerationsToRun;
	private static Random random;
	
	// Parameters
	private final int popsSize = 2;
	
	// Initialization
	private int[][][] populations;
	
	// Fitness Calculation
	private int[] popFitness;
	
	// *** Constructor ***
	public EA_Sudoku(int[][] sudokuProblem){
		this.sudokuProblem = sudokuProblem;
		
		this.popFitness = new int[popsSize];
		this.random = new Random();
	}
	
	// *** Run ***
	public void runEA(int numGenerationsToRun){
		this.numGenerationsToRun = numGenerationsToRun;
		
		initialization();
		fitnessCalculation();
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
						}while(existIn(populations[i][j], randomValue));
						populations[i][j][k] = randomValue;
					}
				}
			}
		}
		
		return;
	}
		
	private void fitnessCalculation(){
		int fitness;
		int[] array = new int[9];
		
		int indexR, indexC, counter;
		for(int i = 0; i < popsSize; i++){ // each pop
			fitness = 81*3;
			for(int j = 0; j < 9; j++){ // column
				for(int k = 0; k < 9 && fitness > 0; k++){ // row
					if(sudokuProblem[j][k] != populations[i][j][k] && sudokuProblem[j][k] != 0){
						fitness = 0;
					}else{ // check all "out of place" digits
						// row
						if(existMoreThanOnce(populations[i][j], populations[i][j][k])){
							fitness -= 1;
						}
								
						// column
						// put in array
						for(int x = 0; x < 9; x++){
							array[x] = populations[i][x][k];
						}
						if(existMoreThanOnce(array, populations[i][j][k])){
							//fitness -= 1;
						}
						
						// 3 by 3
						// find which 3 by 3 box it is in
						indexR = (k/3)*3;
						indexC = (j/3)*3;
						
						// put in array
						counter = 0;
						for(int x = indexC; x < indexC+3; x++){
							for(int y = indexR; y < indexR+3; y++){
								array[counter] = populations[i][x][y];
								counter++;
							}
						}
						if(existMoreThanOnce(array, populations[i][j][k])){
							fitness -= 1;
						}
					}
				}
			}
			popFitness[i] = fitness;
		}
		
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
			System.out.print(i);
			System.out.print("| Fitness: ");
			System.out.println(popFitness[i]);
			for(int j = 0; j < 9; j++){
				for(int k = 0; k < 9; k++){
					System.out.print(populations[i][j][k]);
				}
				System.out.println("");
			}
			System.out.println("================");
		}
	}
	
	private boolean existIn(int[] array, int x){
		for(int i = 0; i < 9; i++){
			if(array[i] == x)
				return true;
		}
		return false;
	}
	
	private boolean existMoreThanOnce(int[] array, int x){
		int counter = 0;
		for(int i = 0; i < 9; i++){
			if(array[i] == x){
				counter++;
			}
			if(counter>1){
				return true;
			}
		}
		return false;
	}
}