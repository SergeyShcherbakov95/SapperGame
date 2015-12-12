import javax.swing.*;
import java.util.Random;

/**
 * Created by Сергей on 08.05.2015.
 */
public class Bomb extends JButton{
    Integer thr, column, value;
    boolean isExist;
    public Bomb(int x, int y, int value){
        this.thr = x;
        this.column = y;
        this.value = value;
    }

    public String toString(){
        return this.value.toString();
    }

    public static int[][] getIndexArray(int height, int weight){
        int[][] indexArray = new int[height][weight];
        int indexOfBomb = 0;
        Random random = new Random();
        while(true){
            int valueX = random.nextInt(9);
            int valueY = random.nextInt(9);
            if( indexArray[valueX][valueY] == -1)
                continue;
            indexArray[valueX][valueY] = -1;
            indexOfBomb++;
            if(valueX-1 >= 0) {
                if(valueY-1 >= 0)
                    if(indexArray[valueX-1][valueY-1] != -1)
                        indexArray[valueX - 1][valueY - 1]++;
                if(indexArray[valueX-1][valueY] != -1)
                    indexArray[valueX - 1][valueY]++;
                if(valueY+1 < weight)
                    if(indexArray[valueX-1][valueY+1] != -1)
                        indexArray[valueX - 1][valueY + 1]++;
            }
            if(valueY-1 >= 0)
                if(indexArray[valueX][valueY-1] != -1)
                    indexArray[valueX][valueY-1]++;
            if(valueY+1 < weight)
                if(indexArray[valueX][valueY+1] != -1)
                    indexArray[valueX][valueY+1]++;
            if(valueX < height) {
                if(valueY-1 >= 0)
                    if(indexArray[valueX+1][valueY-1] != -1)
                        indexArray[valueX + 1][valueY - 1]++;
                if(indexArray[valueX+1][valueY] != -1)
                    indexArray[valueX + 1][valueY]++;
                if(valueY+1 < weight)
                    if(indexArray[valueX+1][valueY+1] != -1)
                    indexArray[valueX + 1][valueY + 1]++;
            }
            if(indexOfBomb >= (height * weight) / 10 )
                break;
        }
        return indexArray;
    }
}
