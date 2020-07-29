//-----------------------------------------
// NAME: Azizul Hakim
// STUDENT NUMBER: 7848052
// COURSE: COMP 2150, SECTION: A02
// INSTRUCTOR: Mike Domaratzki
// ASSIGNMENT: assignment #3,
//
// REMARKS: The purpose of this code is to build a connect4 game
//-----------------------------------------

//This class is for the human player
public class HumanPlayer implements Player,Human {
    private  GameLogic gameL;
    private  SwingGUI user;
    private  int boardDimension;
    private  int statusCount;

    public HumanPlayer(){
        this.boardDimension = 0;
        this.gameL = null;
        user = new SwingGUI();
    }
    @Override
    public void setAnswer(int col) {
        gameL.setAnswer(col);
    }

    @Override
    /**
     * lastMove - called to indicate the last move of the opponent. See the
     * assignment for more detail.
     * @param lastCol - column where the opponent played.
     */
    public void lastMove(int lastCol) {
        System.out.println("dimension"+boardDimension);
        statusCount++;
        if((boardDimension*boardDimension)/2 == statusCount-1){
            System.out.println("Board full! from human");
            gameOver(Status.NEITHER);
            System.exit(0);
        }
        user.lastMove(lastCol);
    }

    @Override
    /**
     * gameOver - called when the game is over. See assignment
     * for more details
     * @param winner - who won the game or NEITHER if the game is a draw.
     */
    public void gameOver(Status winner) {
        user.gameOver(winner);
    }


    /**
     * setInfo - called before any other action. Sets the Human and size
     * of the board. See assignment for more details.
     * @param gl - present gamelogic
     * @param size - size of the board.
     */
    @Override
    public void setInfo(int size, GameLogic gl) {
        this.boardDimension = size;
        this.gameL = gl;
        user.setInfo(this,boardDimension);

    }
}
