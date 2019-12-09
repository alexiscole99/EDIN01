import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Project3{
    public static void main(String[] args) throws FileNotFoundException{
        String given = "";
        File file = new File("sequence.txt");
        Scanner s = new Scanner(file);
        while(s.hasNextLine()){
            given += s.nextLine();
        }
        s.close();

        //String given = "0010001000111110000001101000100110001010000010110111001110011010110000010010011111001111101101101000001110100110100101010011110100010011010110001111011101011101010010010001101010001100001010011";
        CorrAttack c = new CorrAttack(given);
        c.generatePossibleKeys();
        
        /*ArrayList<String> K1 = new ArrayList<String>(c.getK1());
        ArrayList<String> K2 = new ArrayList<String>(c.getK2());
        ArrayList<String> K3 = new ArrayList<String>(c.getK3());
        int[] cd1 = c.getcd1();
        int[] cd2 = c.getcd2();
        int[] cd3 = c.getcd3();*/

        Pair p1k1 = c.initialState(given, c.getK1(), c.getcd1());
        Pair p2k2 = c.initialState(given, c.getK2(), c.getcd2());
        Pair p3k3 = c.initialState(given, c.getK3(), c.getcd3());
        //System.out.println(c.getcd1().length);
        //c.check(given, p1k1.getfirst, p2k2.first, p2k2.first);
        System.out.println("Checking for equality...");
        System.out.println(c.check(given, p1k1.getFirst(), p2k2.getFirst(), p3k3.getFirst()));
        System.out.println("K1: " + p1k1.getFirst() + ", p1: "+ p1k1.getSecond());
        System.out.println("K2: " + p2k2.getFirst() + ", p2: "+ p2k2.getSecond());
        System.out.println("K3: " + p3k3.getFirst() + ", p3: "+ p3k3.getSecond());
    }
}