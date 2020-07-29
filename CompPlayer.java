//-----------------------------------------
// NAME: Azizul Hakim
// STUDENT NUMBER: 7848052
// COURSE: COMP 2150, SECTION: A02
// INSTRUCTOR: Mike Domaratzki
// ASSIGNMENT: assignment #3,
//
// REMARKS: The purpose of this code is to build a connect4 game
//-----------------------------------------
import java.util.Arrays;

// CLASS: CompPlayer
//
// Author: Azizul Hakim, 7848052
//
// REMARKS: A CompPlayer class which implements the Player class and used as the opponent
// in our game.
//
//-----------------------------------------
public class CompPlayer implements Player {
    private int boardDimension;
    private GameLogic gameL;
    private int lastPosition;
    private int movement;
    private int statusCount;
    private Status[][]connect4Board;
    /**
     * CompPlayer constructor initialize the variables
     */
    public CompPlayer(){
        this.boardDimension = 0;
        this.gameL = null;
        this.movement=0;
    }
    /**
     * lastMove - called to indicate the last move of the opponent. See the
     * assignment for more detail.
     * @param lastCol - column where the opponent played.
     */
    @Override
    public void lastMove(int lastCol) {
        if (lastCol != -1){
            lastPosition = drop(lastCol);
            connect4Board[lastPosition][lastCol] = Status.ONE;

        }
        // show board
        System.out.println("BOARD");
        printBoard();

        movement = aiMove(lastCol);
        if(verifyCol(movement)){
            statusCount++;
            int nextMove = drop(movement);
            connect4Board[nextMove][movement] = Status.TWO;
            gameL.setAnswer(movement);

        }
        else if((connect4Board.length*connect4Board.length)/2 == statusCount){
            System.out.println("Board full! from comp");
            gameOver(Status.NEITHER);
            System.exit(0);
        }
    }

    /**
     * verifyCol - private helper method to determine if an integer is a valid
     * column that still has spots left.
     * @param col - integer (potential column number)
     * @return - is the column valid?
     */
    private boolean verifyCol(int col) {
        return (col >= 0 && col < connect4Board[0].length && connect4Board[0][col] == Status.NEITHER);
    }
    /**
     * aiMove - this method returns the ai move. Ai first must be defensive, then offensive. If both
     * the cases don't exist then it will make a random move.
     * @param col - column where the opponent played.
     * @return - returns the Ai's move
     */
    public int aiMove(int col){
        int compMove = -1;
        if(defensiveMove(col) != -1){
            compMove = defensiveMove(col);
        }else
            if(offensiveMove() != -1){
            compMove = offensiveMove();
        }else{
            compMove = randomMove();
        }
        return compMove;
    }
    /**
     * defensiveMove - checks three possible cases if the human has the chance to win the game or not
     * then using those cases the Ai tries to defend.
     * this method tries to defend that.
     * @param col - column where the opponent played.
     *  @return - returns the Ai's move
     */
    public int defensiveMove(int col){
        int compMove = -1;
        if (verticalCheck(col) != -1){
            compMove = verticalCheck(col);
        }else if(horizontalCheck(col) != -1){
            compMove = horizontalCheck(col);
        }else
            if(diagonalCheck(col) != -1){
            compMove = diagonalCheck(col);
        }
        return compMove;
    }
    /**
     * verticalCheck - checks vertically if the human player has the chance to win or not
     * this method tries to defend that.
     * @param col - column where the opponent played.
     *  @return - returns the Ai's move
     */
    public int verticalCheck(int col){
        int compMove = -1;
        if(connect4Board.length - lastPosition>2 && lastPosition != 0) {
            if ((connect4Board[lastPosition + 1][col] == Status.ONE) &&
                    (connect4Board[lastPosition + 2][col] == Status.ONE)) {
                compMove = col;
            }
        }
        return compMove;
    }
    /**
     * horizontalCheck - checks horizontally if the human player has the chance to win or not
     * this method tries to defend that.
     * @param col - column where the opponent played.
     *  @return - returns the Ai's move
     */
    public int horizontalCheck(int col){
        int compMove = -1;
        int checkStart;
        if(col>=3){
            checkStart = col-3;
        }else{
            checkStart =0;
        }
        Status[]neighbour = new Status[4];
        while(checkStart-3<col && checkStart+3<connect4Board.length){
            for(int i = 0; i<neighbour.length; i++) {
                neighbour[i] = connect4Board[lastPosition][checkStart+i];
            }

             if (neighbour[0] == Status.NEITHER && neighbour[1] == Status.ONE && neighbour[2] == Status.ONE && neighbour[3] == Status.ONE) {
                if(lastPosition>=0 && lastPosition<connect4Board.length -1){
                    if(connect4Board[lastPosition + 1][checkStart] != Status.NEITHER){
                        compMove = checkStart;
                        break;
                    }
                }else{
                    compMove = checkStart;
                    break;
                }
            }
            else if (neighbour[0] == Status.ONE && neighbour[1] == Status.ONE && neighbour[2] == Status.ONE && neighbour[3] == Status.NEITHER) {

                if(lastPosition>=0 && lastPosition<connect4Board.length -1){
                    if(connect4Board[lastPosition + 1][checkStart + 3] != Status.NEITHER){
                        compMove = checkStart + 3;
                        break;
                    }
                }else{
                    compMove = checkStart + 3;
                    break;
                }
            }
            else if (neighbour[0] == Status.ONE && neighbour[1] == Status.NEITHER && neighbour[2] == Status.ONE && neighbour[3] == Status.ONE) {
                if(lastPosition>=0 && lastPosition<connect4Board.length -1){
                    if(connect4Board[lastPosition + 1][checkStart + 1] != Status.NEITHER){
                        compMove = checkStart + 1;
                        break;
                    }
                }else{
                    compMove = checkStart + 1;
                    break;
                }
            }
            else if (neighbour[0] == Status.ONE && neighbour[1] == Status.ONE && neighbour[2] == Status.NEITHER && neighbour[3] == Status.ONE) {
                 if(lastPosition>=0 && lastPosition<connect4Board.length -1){
                     if(connect4Board[lastPosition + 1][checkStart + 2] != Status.NEITHER){
                         compMove = checkStart + 2;
                         break;
                     }
                 }else{
                     compMove = checkStart + 2;
                     break;
                 }
             }
            checkStart++;
        }
        return compMove;
    }
    /**
     * diagonalCheck - checks diagonally if the human player has the chance to win or not
     * this method tries to defend that.
     * @param col - column where the opponent played.
     *   @return - returns the Ai's move
     */
    public int diagonalCheck(int col){
        int compMove = -1;
        int checkStart;
        int rowCount;
        if(col>=3){
            checkStart = col-3;
        }else{
            checkStart =0;
        }
        if(lastPosition>=3){
            rowCount = lastPosition-3;
        }else{
            rowCount =0;
        }
        Status[]neighbour = new Status[4];
        while(checkStart-col<3 && connect4Board.length-checkStart>3 && rowCount-3<lastPosition && rowCount+3<connect4Board.length) {
            for(int i = 0; i<neighbour.length; i++) {
                neighbour[i] = connect4Board[rowCount+i][checkStart+i];
            }
            if (neighbour[0] == Status.ONE && neighbour[1] == Status.ONE && neighbour[2] == Status.ONE && neighbour[3] == Status.NEITHER) {
                compMove = checkStart + 3;
                break;
            }else if (neighbour[0] == Status.ONE && neighbour[1] == Status.ONE && neighbour[2] == Status.NEITHER && neighbour[3] == Status.ONE) {
                compMove = checkStart + 2;
                break;
            }else if (neighbour[0] == Status.ONE && neighbour[1] == Status.NEITHER && neighbour[2] == Status.ONE && neighbour[3] == Status.ONE) {
                compMove = checkStart + 1;
                break;
            }else if (neighbour[0] == Status.NEITHER && neighbour[1] == Status.ONE && neighbour[2] == Status.ONE && neighbour[3] == Status.ONE) {
                compMove = checkStart;
                break;
            }
            checkStart++;
            rowCount++;
        }
        return compMove;
    }
    /**
     * offensiveMove - this method is used when Ai will play offensively. If the defensiveMove doesn't
     * work then this will be called.
     *  @return - returns the Ai's move
     */
    public int offensiveMove(){
        int compMove = -1;
        if(offensiveVerticalCheck() != -1){
            compMove = offensiveVerticalCheck();
        }else if(offensiveHorizontalCheck() !=-1){
            compMove = offensiveHorizontalCheck();
        }
        return compMove;
    }
    /**
     * offensiveVerticalCheck - creates opportunities for Ai player vertically.
     *    If the defensive condition doesn't work then AI will play offensively.
     *     @return - returns the Ai's move
     */
    public int offensiveVerticalCheck(){
       int compMove =-1;
       for(int col = 0; col<connect4Board.length;col++){
           int compCount = 0;
           for(int row = 0; row<connect4Board.length;row++){
               if (connect4Board[row][col] == Status.TWO) {
                   compCount++;
                   if(compCount == 3){
                       return col;
                   }
               }
           }
       }
        return compMove;
    }
    /**
     * offensiveHorizontalCheck - creates opportunities for Ai player horizontally.
     * If the defensive condition doesn't work then AI will play offensively.
     *  @return - returns the Ai's move
     */
    public int offensiveHorizontalCheck(){
        int compMove =-1;
        int position = connect4Board.length-1;

        while (position>=0) {
            int newCol = 1;
            while(newCol>0 && newCol<connect4Board.length-3){
            if ((connect4Board[position][newCol] == Status.TWO) &&
                    (connect4Board[position][newCol + 1] == Status.TWO) &&
                    (connect4Board[position][newCol + 2] == Status.TWO)) {

                if (connect4Board[position][newCol + 3] == Status.NEITHER) {
                    compMove = newCol + 3;
                    break;
                } else if (connect4Board[position][newCol - 1] == Status.NEITHER) {
                    compMove = newCol - 1;
                    break;
                }
            } else if ((connect4Board[position][newCol] == Status.TWO) &&
                    (connect4Board[position][newCol + 1] == Status.NEITHER) &&
                    (connect4Board[position][newCol + 2] == Status.TWO) &&
                    (connect4Board[position][newCol + 3] == Status.TWO)) {
                compMove = newCol + 1;
                break;
            } else if ((connect4Board[position][newCol] == Status.TWO) &&
                    (connect4Board[position][newCol + 1] == Status.TWO) &&
                    (connect4Board[position][newCol + 2] == Status.NEITHER) &&
                    (connect4Board[position][newCol + 3] == Status.TWO)) {
                compMove = newCol + 2;
                break;
            }

            newCol++;
        }
            position--;
        }
        return compMove;
    }
    /**
     *randomMove - generates random values for the computer move when other 2 conditions will not
     * satify
     *  @return - returns the Ai's move randomly
     */
    public int randomMove(){
        //generating random value from 0 to board length - 1
        int compMove = ((int)(Math.random()*(connect4Board.length)));
        if(!verifyCol(compMove)){
            int newCol = 0;
            while(newCol<connect4Board.length){
                if(connect4Board[0][newCol]==Status.NEITHER) {
                    compMove = newCol;
                    break;
                }
                newCol++;
            }
        }
        return compMove;
    }
    /**
     * printBoard - private helper method to print the board.
     */
    private void printBoard() {
        for (Status[] s : connect4Board) {
            System.out.println(rowToString(s));
        }
    }
    /**
     * rowToString - private helper method to print a single
     * row of the board
     * @param s - an array from the board to be printed.
     * @return - String representation of the board.
     */
    private String rowToString(Status[] s) {
        String out = "";
        for (Status curr : s) {
            switch (curr) {
                case ONE:
                    out += "O";
                    break;
                case TWO:
                    out += "X";
                    break;
                case NEITHER:
                    out += ".";
                    break;
            }
        }
        return out;
    }
    /**
     * gameOver - called when the game is over. See assignment
     * for more details
     * @param winner - who won the game or NEITHER if the game is a draw.
     */
    @Override
    public void gameOver(Status winner) {
        System.out.println("Game over!");
        if (winner == Status.NEITHER) {
            System.out.println("Game is a draw.");
        } else if (winner == Status.ONE) {
            System.out.println("You win.");
        } else {
            System.out.println("Computer wins.");
        }
    }
    /**
     * gameOverHelper - a helper method to call from the gameLogicClass to finish the program
     */
    public void gameOverHelper(Status winner){
        gameOver(winner);
        System.exit(0);
    }
    /**
     * setInfo - called before any other action. Sets the Human and size
     * of the board. See assignment for more details.
     * @param gl - gamelogic at present
     * @param size - size of the board.
     */
    @Override
    public void setInfo(int size, GameLogic gl) {
        this.gameL = gl;
        connect4Board = new Status[size][size];
        for (Status[]s : connect4Board){
            Arrays.fill(s,Status.NEITHER);
        }
    }
    /**
     * drop - a private helper method that finds the position of a marker
     * when it is dropped in a column.
     * @param col the column where the piece is dropped
     * @return the row where the piece lands
     */
    private int drop(int col) {
        int position = 0;
        while (position < connect4Board.length && connect4Board[position][col] == Status.NEITHER) {
            position ++;
        }
        return position-1;
    }
    /**
     *winCheck - the method which checks the winning condition of the players
     *  @return - returns the status winner
     */
    public Status winCheck(){
        Status returnWinner = null;
        Status humanWinner = Status.ONE;
        Status compWinner = Status.TWO;

        //horizontal check
        for(int row = 0; row<connect4Board.length;row++){
            for(int col = 0; col<connect4Board.length-3;col++){
                    if((connect4Board[row][col] == Status.ONE&&connect4Board[row][col+1] == Status.ONE
                            && connect4Board[row][col+2] == Status.ONE
                            &&connect4Board[row][col+3] == Status.ONE )){
                        return humanWinner;
                    }
                    if((connect4Board[row][col] == Status.TWO&&connect4Board[row][col+1] == Status.TWO
                            && connect4Board[row][col+2] == Status.TWO
                            &&connect4Board[row][col+3] == Status.TWO )) {
                        return compWinner;
                    }
                    }
            }

        //vertical check
        for(int col = 0; col<connect4Board.length;col++){
            for(int row = 0; row<connect4Board.length-3;row++){
                if (connect4Board[row][col] == Status.ONE && connect4Board[row+1][col] == Status.ONE
                        && connect4Board[row+2][col] == Status.ONE&&connect4Board[row+3][col] == Status.ONE) {
                  return humanWinner;
                }else if(connect4Board[row][col] == Status.TWO && connect4Board[row+1][col] == Status.TWO
                        && connect4Board[row+2][col] == Status.TWO&&connect4Board[row+3][col] == Status.TWO){
                    return compWinner;

                }

            }
        }

        for(int i=3;i<connect4Board.length;i++){
            for(int j=3;j<connect4Board.length;j++){
                if(connect4Board[i][j]==Status.ONE && connect4Board[i-1][j-1]==Status.ONE&&
                connect4Board[i-2][j-2]==Status.ONE&& connect4Board[i-3][j-3]==Status.ONE){
                    return humanWinner;
                }else if(connect4Board[i][j]==Status.TWO && connect4Board[i-1][j-1]==Status.TWO &&
                connect4Board[i-2][j-2]==Status.TWO&& connect4Board[i-3][j-3]==Status.TWO){
                    return compWinner;
                }
            }
        }
        for(int i=3;i<connect4Board.length;i++){
            for(int j=0;j<connect4Board.length-3;j++){
                if(connect4Board[i][j]==Status.ONE && connect4Board[i-1][j+1]==Status.ONE&&
                        connect4Board[i-2][j+2]==Status.ONE&& connect4Board[i-3][j+3]==Status.ONE){
                    return humanWinner;
                }else if(connect4Board[i][j]==Status.TWO && connect4Board[i-1][j+1]==Status.TWO &&
                        connect4Board[i-2][j+2]==Status.TWO&& connect4Board[i-3][j+3]==Status.TWO){
                    return compWinner;
                }
            }
        }
        return returnWinner;
    }
}
