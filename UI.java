//-----------------------------------------
// NAME: Azizul Hakim
// STUDENT NUMBER: 7848052
// COURSE: COMP 2150, SECTION: A02
// INSTRUCTOR: Mike Domaratzki
// ASSIGNMENT: assignment #3,
//
// REMARKS: The purpose of this code is to build a connect4 game
//-----------------------------------------
public interface UI {
    void lastMove(int lastCol);
    void gameOver(Status winner);
    void setInfo(Human h, int size);
}
