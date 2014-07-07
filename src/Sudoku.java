//import java.io.BufferedReader;
import java.io.BufferedReader;
import java.io.FileReader;


public class Sudoku {

    public static void main(String[] args) {
    	long startTime = System.currentTimeMillis();
    	String filePath = "C:/Users/aarth_000/Desktop/Sudoku-Puzzle-Solver/src/";
    	String [] files = new String[10];
    	files[0] = filePath + "state0-1sol.txt";
    	files[1] = filePath + "state1-1sol.txt";
    	files[2] = filePath + "state2-1sol.txt";
    	files[3] = filePath + "state3-0sol.txt";
    	files[4] = filePath + "state4-4sol.txt";
    	files[5] = filePath + "state5-1sol.txt";
    	files[6] = filePath + "state6-4sol.txt";
    	files[7] = filePath + "state7-1sol.txt";
    	files[8] = filePath + "state8-24sol.txt";
    	files[9] = filePath + "state9-1sol.txt";
    	
    	    	   	
    for(int counter=0;counter<files.length;counter++){
    	long startTimeEach = System.currentTimeMillis();
		int[][] matrix = parseProblem(readInputFromFile(files[counter]));
		writeMatrix(matrix);
		if (solve(0,0,matrix)){   
			System.out.println("Found Solution!");
		    writeMatrix(matrix);
		}
		else {
		    System.out.println("No Solution Found!");
		}
		long endTimeEach   = System.currentTimeMillis();
		long totalTimeEach = endTimeEach - startTimeEach;
		System.out.println("Total running time for each puzzle= "+totalTimeEach+ " milliseconds");
	    
	 }
	long endTime   = System.currentTimeMillis();
	long totalTime = endTime - startTime;
	System.out.println("Total running time= "+totalTime+ " milliseconds");
    
    }

    static boolean solve(int i, int j, int[][] cells) {
	if (i == 9) {
	    i = 0;
	    if (++j == 9) 
		return true; 
	}
	if (cells[i][j] != 0)  
	    return solve(i+1,j,cells);
	
	for (int val = 1; val <= 9; ++val) {
	    if (legal(i,j,val,cells)) {  
		cells[i][j] = val;       
		if (solve(i+1,j,cells))  
		    return true;
	    }
	}
	cells[i][j] = 0; 
	return false;
    }

    static boolean legal(int i, int j, int val, int[][] cells) {
	for (int k = 0; k < 9; ++k)  
	    if (val == cells[k][j])
		return false;

	for (int k = 0; k < 9; ++k) 
	    if (val == cells[i][k])
		return false;

	int boxRowOffset = (i / 3)*3;
	int boxColOffset = (j / 3)*3;
	for (int k = 0; k < 3; ++k) 
	    for (int m = 0; m < 3; ++m)
		if (val == cells[boxRowOffset+k][boxColOffset+m])
		    return false;

	return true; 
    }

    static int[][] parseProblem(String[] args) {
	int[][] problem = new int[9][9]; 
	int n = 0;
	for (int i = 0; i <9; i++) {
		for(int j=0; j<9; j++){			
		//	System.out.println(args[n]);
			problem[i][j] = Integer.parseInt(args[n]);
			n++;
		}	   
	}
	return problem;
    }

    static void writeMatrix(int[][] solution) {
    	for (int i = 0; i < 9; ++i) {
	    if (i % 3 == 0)
		System.out.println(" -----------------------");
	    for (int j = 0; j < 9; ++j) {
		if (j % 3 == 0) System.out.print("| ");
		System.out.print(solution[i][j] == 0
				 ? " "
				 : Integer.toString(solution[i][j]));
		
		System.out.print(' ');
	    }
	    System.out.println("|");
	}
	System.out.println(" -----------------------");
    }
    
    private static String[] readInputFromFile (String fileName){
    	String[] input = new String [81];
        try{
        FileReader inputFile = new FileReader(fileName);
        BufferedReader bufferReader = new BufferedReader(inputFile);
        String line;
        StringBuffer strLine = new StringBuffer();
        while ((line = bufferReader.readLine()) != null)   {
              //  System.out.println(line);
                strLine.append(line);
        }
       String strMatrix = strLine.toString();
       
        for (int len=0; len<strMatrix.length();len++){
        	String value = strMatrix.substring(len,len+1);
        	if(".".equals(value)){
        		value = "0";
        	}
        	input[len] = value;
        }
        
        bufferReader.close();
        }catch(Exception e){
                System.out.println("Error while reading file line by line:" + e.getMessage());                      
        }
        return input;
    }

}


