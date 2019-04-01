package fuyao;

import java.util.Random;

public class Main {
    public static void main(String[] arg) {
        myDataStructure my = new myDataStructure(8);
        Random random = new Random();
        for(int i=0;i<8;i++) my.insert(random.nextInt(100));
        System.out.println(my);
        my.decreasekey(1,0);
        System.out.println(my);
        System.out.println("second max=" + my.extractSecondMax());
    }
}
