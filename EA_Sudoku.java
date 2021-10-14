import java.io.File;
import java.util.Scanner;
import java.util.Random;

public class EA_Sudoku {
	private final int[][] sudokuProblem;
	private int numGenerationsToRun;
	private static Random random;
	
	// Parameters
	private final int popsSize = 1000;
	private final double parentSizePercentage = 0.4; 
	private final double offspringSizePercentage = 0.9;
	private final double elitismSizePercentage = 0.1;
	private final double mutationProbality = 0.05;
	private final int kTournamentSize = 3;
	
	//
	private final int parentSize = (int) (popsSize * parentSizePercentage);
	private final int offspringSize = (int) (popsSize * offspringSizePercentage);
	private final int elitismSize = popsSize - offspringSize;
	
	// Initialization
	private int[][][] populations;
	
	// Fitness Calculation
	private int[] popFitness;
	
	// Parents SelectionEvent
	private int[] parentPool;
	
	// Crossover
	private int[][][] offspringPool;
	
	// Elitism
	private int[][][] elitismPool;
	
	// New generation
	private int[][][] nextGenPopulation;
	
	// *** Constructor ***
	public EA_Sudoku(int[][] sudokuProblem){
		this.sudokuProblem = sudokuProblem;
		
		this.popFitness = new int[popsSize];
		this.random = new Random();
		this.parentPool = new int[parentSize];
		this.offspringPool = new int[offspringSize][9][9];
		this.elitismPool = new int[elitismSize][9][9];
	}
	
	// *** Run ***
	public void runEA(int numGenerationsToRun){
		this.numGenerationsToRun = numGenerationsToRun;
		
		initialization();
		//printPopulations();
		for(int i = 0; i < numGenerationsToRun; i++){
			fitnessCalculation();
			parentSelection();
			crossOver();
			//printOffspringPool();
			//printPopFitness();
			elitism();
			replaceGeneration();
		}
		fitnessCalculation();
		//printPopFitness();
		//printPopulations();
		printRandomPopulation();
		printRandomPopulation();
		printRandomPopulation();
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
			fitness = 81;
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
							fitness -= 1;
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
		int[] tournamentPool = new int[kTournamentSize];
		int indexSelected = 0, highestFitness, randomValue;
		
		for(int i = 0; i < parentSize; i++){
			// Randomly select K number from populations
			for(int j = 0; j < kTournamentSize; j++){
				// select random pop that not already in tournamentPool and not already selected as a parent
				do{
					randomValue = random.nextInt(popsSize);
				}while(existIn(tournamentPool, randomValue) || existIn(parentPool, randomValue));
				
				// put in tournamentPool
				tournamentPool[j] = randomValue;
			}
			
			// Select the highest fitness pop in the tournamentPool
			highestFitness = 0;
			for(int j = 0; j < kTournamentSize; j++){
				if(highestFitness < popFitness[tournamentPool[j]]){
					highestFitness = popFitness[tournamentPool[j]];
					indexSelected = j; // j is index in tournamentPool, which store index of populations
				}
			}
			
			// Put it into parentPool
			parentPool[i] = tournamentPool[indexSelected];
			//System.out.println("Parent: " + parentPool[i]); // for debugging
		}
		
		return;
	}

	private void crossOver(){
		int index1, index2, randomValue1, randomValue2, randomPickedParent;
		for(int i = 0; i < offspringSize; i++){
			// randomly select two parent from parent pool
			randomValue1 = random.nextInt(parentSize);
			do{
				randomValue2 = random.nextInt(parentSize);
			}while(randomValue1 == randomValue2);
			
			index1 = parentPool[randomValue1];
			index2 = parentPool[randomValue2];
			
			//System.out.println(randomValue1 + " = " + randomValue2); // For debugging
			
			// create a new offspring from both parent
			for(int j = 0; j < 9; j++){
				//randomly pick a row from one of the two parent
				if(random.nextBoolean()){ // parent 1
					randomPickedParent = index1;
				} else { // parent 2
					randomPickedParent = index2;
				}
				
				//place the row in to the offspring
				for(int k = 0; k < 9; k++){
					offspringPool[i][j][k] = populations[randomPickedParent][j][k];
				}
			}
		}
		
		return;
	}
	
	private void elitism(){
		int[] elitismIndex = new int[elitismSize];
		int indexHighestFitness = 0;
		int highestFitness = 0;
		boolean notExist = true;
		
		// get all the indexs
		for(int i = 0; i < elitismSize; i++){
			indexHighestFitness = 0;
			highestFitness = 0;		
			for(int j = 0; j < popsSize; j++){
				if(existIn(elitismIndex, j)){
					notExist = false;
				}
				
				if(highestFitness < popFitness[j] && notExist){
					highestFitness = popFitness[j];
					indexHighestFitness = j;
				}
				notExist = true;
			}
			elitismIndex[i] = indexHighestFitness;
		}
		
		// put the index pop into elitismPool
		for(int i = 0; i < elitismSize; i++){
			for(int j = 0; j < 9; j++){
				for(int k = 0; k < 9; k++){
					elitismPool[i][j][k] = populations[elitismIndex[i]][j][k];
				}
			}
			//System.out.println("Elitism - " + elitismIndex[i]);// for debugging
		}
		
		return;
	}
		
	private void replaceGeneration(){
		int counter = 0;
		
		// Combine offspring and elitismPool to make the next generation
		for(int i = 0; i < offspringSize; i++){
			for(int j = 0; j < 9; j++){
				for(int k = 0; k < 9; k++){
					populations[counter][j][k] = offspringPool[i][j][k];
				}
			}
			counter++;
		}
		
		for(int i = 0; i < elitismSize; i++){
			for(int j = 0; j < 9; j++){
				for(int k = 0; k < 9; k++){
					populations[counter][j][k] = elitismPool[i][j][k];
				}
			}
			counter++;
		}
		
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
	
	private void printRandomPopulation(){
		int randomValue = random.nextInt(popsSize);
		System.out.print(randomValue);
		System.out.print("| Fitness: ");
		System.out.println(popFitness[randomValue]);
		for(int j = 0; j < 9; j++){
			for(int k = 0; k < 9; k++){
				System.out.print(populations[randomValue][j][k]);
			}
			System.out.println("");
		}
		System.out.println("================");
	}
	
	private void printPopFitness(){
		for(int i = 0; i < popsSize; i++){
			System.out.println(i+"|Fitness="+popFitness[i]);
		}
	}
	
	private void printOffspringPool(){
		for(int i = 0; i < offspringSize; i++){
			// add the pre-filled
			System.out.println(i + " === ");
			for(int j = 0; j < 9; j++){
				for(int k = 0; k < 9; k++){
					System.out.print(offspringPool[i][j][k]);
				}
				System.out.println("");
			}
			System.out.println("================");
		}
	}
	
	private boolean existIn(int[] array, int x){
		for(int i = 0; i < array.length; i++){
			if(array[i] == x)
				return true;
		}
		return false;
	}
	
	private boolean existMoreThanOnce(int[] array, int x){
		int counter = 0;
		for(int i = 0; i < array.length; i++){
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