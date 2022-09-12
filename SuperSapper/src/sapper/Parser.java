package sapper;

import java.util.Random;

public class Parser {
    protected int width;
    protected int height;
    protected int bombs;
    protected short gameStatus;
    public final static short inProgress=0;
    public final static short youLose=1;
    public final static short youWin=2;
    protected int seconds;
    protected int bombsToDetect;

    protected Cell [][] board;

    // 0 - beginner
    // 1 - intermediate
    // 2 - expert
    // 3 - custom
    public static int gameMode;


    public Parser(int width, int height , int bombs){
        this.width = width;
        this.height = height;
        this.bombs = bombs;
        this.bombsToDetect = bombs;
        this.gameStatus = inProgress;
        this.seconds = 0;

        this.board = new Cell[width][height];
        for(int i = 0; i<width; i++){
            for(int j = 0; j<height; j++){
                this.board[i][j] = new Cell();
            }
        }

        //rozstawiammy bomby here
        Random random = new Random();
        for(int i = 0; i < bombs; i++){

            int randomPosX = random.nextInt(width);
            int randomPosY = random.nextInt(height);

            if(this.board[randomPosX][randomPosY].value >=9){
                i--;
            }
            else {
                this.board[randomPosX][randomPosY].value = 9;

                for (int k = -1; k <= 1; k++){
                    for (int l = -1; l <= 1; l++) {
                        try {
                            this.board[randomPosX + k][randomPosY + l].value++;
                        } catch (Exception ignored) {}
                    }
                }
            }
        }

    }

    public int checkDiscoveredFields(){

        int result = 0;

        for(int i = 0; i < this.width; i++){
            for(int j = 0; j < this.height; j++){
                if(this.board[i][j].Discovered)
                    result++;
            }
        }
        return result;
    }

    public Boolean checkWin(){
        if(checkDiscoveredFields()==(width*height)-bombs){
            gameStatus = youWin;
            return true;
        }
        return false;
    }


}



