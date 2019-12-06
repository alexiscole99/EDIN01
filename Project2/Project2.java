import java.util.ArrayList;
import java.io.*;

public class Project2{
    public static void main(String[] args) throws IOException{
        //construct 2 db sequences
        //use a function to map to Z_10 i.e. 5x+y or x+2y or 5x+2y(mod10)
        
        //construct Z2
        int[] coef2 = new int[] {1,0,0,1};
        int[] start2 = new int[] {0,0,0,1};
        int[] end2Array = new int[] {1,0,0,0};
        ArrayList<Integer> end2 = new ArrayList<Integer>();
        for(int i=0;i<4;i++){
            end2.add(end2Array[i]);
        }
        DeBruijn s2 = new DeBruijn(coef2, 2, start2, end2);
        ArrayList s2Test = new ArrayList<Integer>();

        //construct Z5
        int[] coef5 = new int[] {2,2,2,2};
        int[] start5 = new int[] {0,0,0,1};
        int[] end5Array = new int[] {1,0,0,0};
        ArrayList<Integer> end5 = new ArrayList<Integer>();
        for(int i=0;i<4;i++){
            end5.add(end5Array[i]);
        }
        DeBruijn s5 = new DeBruijn(coef5, 5, start5, end5);
        ArrayList s5Test = new ArrayList<Integer>();


        //write result to file
        File file = new File("sequence.out");

        FileWriter fw = new FileWriter(file,true);
        PrintWriter pw = new PrintWriter(fw);
        pw.flush();
        int x, y, toAdd;
        for(int i=0; i<10003; i++){
            x = s2.getNextVal();
            y = s5.getNextVal();
            toAdd = 5*x + y;
            pw.print(toAdd);
        }
        pw.close();

        //check if valid with executable

        /*for(int i=0; i<625; i++){
            s5Test.add(s5.getNextVal());
        }
        System.out.println(s5Test.size());
        System.out.println(s5Test);
        for(int i=0; i<16; i++){
            s2Test.add(s2.getNextVal());
        }
        System.out.println(s2Test.size());
        System.out.println(s2Test);*/
        
        
    }
}