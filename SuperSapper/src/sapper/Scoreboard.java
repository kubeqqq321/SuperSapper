package sapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Scoreboard {


    private static ScoreboardElements[] beginnerRanking = new ScoreboardElements[5] ;
    private static ScoreboardElements[] intermediateRanking = new ScoreboardElements[5];
    private static ScoreboardElements[] expertRanking = new ScoreboardElements[5];

    public Scoreboard() throws FileNotFoundException, NoSuchElementException{

        for(int i = 0; i < 5; i++){
            beginnerRanking[i] = new ScoreboardElements(" " , -1);
            intermediateRanking[i] = new ScoreboardElements(" " , -1);
            expertRanking[i] = new ScoreboardElements(" " , -1);

        }

        Scanner data = new Scanner(new File("src/sapper/dane.txt"));
        String[] temp = null;
        String line;

        for(int i = 0; i < 5; i++) {
            line = data.nextLine();
            temp = line.split("~");
            beginnerRanking[i] = new ScoreboardElements(temp[0],Integer.parseInt(temp[1]));

            line = data.nextLine();
            temp = line.split("~");
            intermediateRanking[i] = new ScoreboardElements(temp[0],Integer.parseInt(temp[1]));

            line = data.nextLine();
            temp = line.split("~");
            expertRanking[i] = new ScoreboardElements(temp[0],Integer.parseInt(temp[1]));

        }
        data.close();

    }



    public static int checkPosition(int time, int gameMode){

        if (gameMode == 0) {
            for (int i = 0; i < 5; i++) {
                if (time < beginnerRanking[i].gainedTime || beginnerRanking[i].gainedTime == -1) {
                    return i;
                }
            }
        }
        if (gameMode == 1) {
            for (int i = 0; i < 5; i++) {
                if (time < intermediateRanking[i].gainedTime || intermediateRanking[i].gainedTime == -1) {
                    return i;
                }
            }
        }
        if (gameMode == 2) {
            for (int i = 0; i < 5; i++) {
                if (time < expertRanking[i].gainedTime || expertRanking[i].gainedTime == -1) {
                    return i;
                }
            }
        }

        return 5;
    }


    public static void addResult(ScoreboardElements player, int position, int gameMode){

        if (gameMode == 0) {
            for (int i = 4; i > position; i--) {
               beginnerRanking[i] = beginnerRanking[i-1];
            }
            beginnerRanking[position]=player;
        }
        if (gameMode == 1) {
            for (int i = 4; i > position; i--) {
                intermediateRanking[i] = intermediateRanking[i-1];
            }
            intermediateRanking[position]=player;
        }
        if (gameMode == 2) {
            for (int i = 4; i > position; i--) {
                expertRanking[i] = expertRanking[i-1];
            }
            expertRanking[position]=player;
        }

    }


    public static void saveData() throws FileNotFoundException {

        PrintWriter save = new PrintWriter("src/sapper/dane.txt");

        for (int i = 0; i < 5; i++){
           if (beginnerRanking[i].gainedTime == -1 || beginnerRanking[i] == null){
               save.println("~-1");
           }
           else{
                save.println(beginnerRanking[i].name + "~" + beginnerRanking[i].gainedTime);
           }

            if (intermediateRanking[i].gainedTime == -1){
                save.println("~-1");
            }
            else{
                save.println(intermediateRanking[i].name + "~" + intermediateRanking[i].gainedTime);
            }

            if (expertRanking[i].gainedTime == -1){
                save.println("~-1");
            }
            else{
                save.println(expertRanking[i].name + "~" + expertRanking[i].gainedTime);
            }
        }
        save.close();
    }




    public static String getRanking(int gameMode){
        String result = "";

        if (gameMode == 0) {
            result="Łatwy \n";
            for (int i = 0; i < 5; i++) {
                if (beginnerRanking[i].gainedTime ==-1){
                    result += i+1 + ". \n";
                }
                else{
                    result += i + 1 + ". " + beginnerRanking[i] + "\n";
                }
            }
        }
        else if (gameMode == 1) {
            result="Średni \n";
            for (int i = 0; i < 5; i++) {
                if (intermediateRanking[i].gainedTime ==-1){
                    result += i+1 + ". \n";
                }
                else{
                    result += i + 1 + ". " + intermediateRanking[i] + "\n";
                }
            }
        }
        else if (gameMode == 2) {
            result="Trudny \n";
            for (int i = 0; i < 5; i++) {
                if (expertRanking[i].gainedTime ==-1){
                    result += i+1 + ". \n";
                }
                else{
                    result += i + 1 + ". " + expertRanking[i] + "\n";
                }
            }
        }

        return result;
    }


}