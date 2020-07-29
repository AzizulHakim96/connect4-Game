//-----------------------------------------
// NAME: Azizul Hakim
// STUDENT NUMBER: 7848052
// COURSE: COMP 2150, SECTION: A02
// INSTRUCTOR: Mike Domaratzki
// ASSIGNMENT: assignment #3,
//
// REMARKS: The purpose of this code is to build a connect4 game
//-----------------------------------------
import java.util.Random;

// CLASS: gameLogicClass
//
// Author: Azizul Hakim, 7848052
//
// Remarks : This class is for the game logic to implement
//-----------------------------------------
public class gameLogicClass implements GameLogic {
    private int boardDimension;
    private Human humanP;
    private CompPlayer aiPlayer;
    private Status selectedPlayer;

    //constructor
    public gameLogicClass(){
         humanP = new HumanPlayer();
         aiPlayer = new CompPlayer();
         boardDimension = randomNumber(12,6);
    }

    //generates random number
    public int randomNumber(int maxSize, int minSize){
        Random ran = new Random();
        return ran.nextInt((maxSize - minSize)+1)+minSize;
    }

    /**
     * setAnswer - The method setAnswer will be called by a Player when the Player (either Human or AI) has
     * determined their move
     * @param col - gets the col everytime to execute the move of the next player

     */
    public void setAnswer(int col) {
        if(aiPlayer.winCheck() != null){
            aiPlayer.gameOverHelper(aiPlayer.winCheck());
        }
                if (selectedPlayer == Status.TWO) {
                    selectedPlayer = Status.ONE;
                    ((HumanPlayer) humanP).lastMove(col);
                } else if (selectedPlayer == Status.ONE) {
                    selectedPlayer = Status.TWO;
                    aiPlayer.lastMove(col);
                }
    }
    /**
     * playerSelection - selects the player randomly at the beginning of the game
     */
    public void playerSelection(){
        int randomPlayer = randomNumber(1,0);
        if(randomPlayer == 0){
            selectedPlayer = Status.ONE;
            ((HumanPlayer)humanP).lastMove(-1);
        }else{
            selectedPlayer = Status.TWO;
            aiPlayer.lastMove(-1);
        }
    }
    /**
     * startGame - starts the game from the main class
     */
    public void startGame(){
        ((HumanPlayer)humanP).setInfo(boardDimension,this);
        aiPlayer.setInfo(boardDimension,this);
        playerSelection();
    }

}
