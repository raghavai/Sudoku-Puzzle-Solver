import sys

sudoku_size = 81
sudoku = [sudoku_size]
    
# read sudoku from the given file
def readPuzzle (file, sud_puzzle):   
 with open (file, "r") as thisfile:
  sud_puzzle = thisfile.read().replace('\n', '') # concat into same line
  print("\nInput Puzzle: ")
  printPuzzle(sud_puzzle) 
 sud_puzzle = list(sud_puzzle)  # convert to list
 return sud_puzzle
 
 # print in sudoku format
def printPuzzle(sud_puzzle):
  temp = []
  for i in range(1, 82):
    temp.append(str(sud_puzzle[i-1]))

    if i % 3 == 0:
      temp.append('|')

    if i % 9 == 0:  # sudoku format
      print temp
      temp = []
      if i == 27 or i == 54 or i == 81:
        for j in range(1, 13):
          temp.append('-')
        print temp
        temp = []

 
# find empty position i.e. position with '.'
def findEmptyPositions(sud_puzzle):
  for position in range(81):
    if sud_puzzle[position] == '.':
      return position

# checks surround, row and column of each blank position
def checkPosition(inp_value, empty_pos, sud_puzzle):  
  # check surround
  cols = 0
  for eachSq in range(9):
    trialSq = [ pointer + cols for pointer in range(3) ] + [ pointer + 9 + cols for pointer in range(3) ] + [ pointer + 18 + cols for pointer in range(3) ]
    cols = cols + 3
    if cols in [9, 36]:
      cols = cols + 18
    if validatePos(sud_puzzle, empty_pos, inp_value, trialSq) == False:
      return False
  
  # check row
  for eachRow in range(9):
    testRow = [ pointer+(9*eachRow) for pointer in range (9) ]
    if validatePos(sud_puzzle, empty_pos, inp_value, testRow) == False:
      return False

  # check column
  for eachCol in range(9):
    testCol = [ (9*pointer)+eachCol for pointer in range (9) ]
    if validatePos(sud_puzzle, empty_pos, inp_value, testCol) == False:
      return False
  return True

# validates the positon of the assumed value
def validatePos(sud_puzzle, empty_pos, inp_value, tempSq):
  if empty_pos in tempSq:
    for i in tempSq:
      if sud_puzzle[i] != '.':
        if int(sud_puzzle[i]) == inp_value:
          return False

def sudokuSolve(sud_puzzle):
  # run until all blanks have been filled
  if sud_puzzle.count('.') == 0:
    return True
  else:
    empty_pos = findEmptyPositions(sud_puzzle)  # find empty positions
    inp_value = 1
    soln_bol = False
    while ( soln_bol != True) and (inp_value < 10):
      if checkPosition(inp_value, empty_pos, sud_puzzle):
        sud_puzzle[empty_pos] = inp_value
        if sudokuSolve (sud_puzzle) == True:
          soln_bol = True
          return True
        else:
          sud_puzzle[empty_pos] = '.'
      inp_value = inp_value + 1
  return soln_bol

def main (args):
 sudoku_temp = readPuzzle(sys.argv[1], [81])
 solution = sudokuSolve(sudoku_temp)

 if solution == False:
  print("No solvable..")
 else:
  print("\nSolved Sudoku:")
  printPuzzle(sudoku_temp)
  
if __name__ == "__main__": main(sys.argv)
