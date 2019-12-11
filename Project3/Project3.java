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
        System.out.println(given.length());

        CorrAttack c = new CorrAttack(given);
        c.generatePossibleKeys();

        long start = System.currentTimeMillis();
        Pair p1k1 = c.initialState(given, c.getK1(), c.getcd1());
        Pair p2k2 = c.initialState(given, c.getK2(), c.getcd2());
        Pair p3k3 = c.initialState(given, c.getK3(), c.getcd3());
        long finish = System.currentTimeMillis();

        System.out.println("Time elapsed: "+ (finish - start) + " ms");
        System.out.println("Checking for equality...");
        System.out.println(c.check(given, p1k1.getFirst(), p2k2.getFirst(), p3k3.getFirst()));
        System.out.println("K1: " + p1k1.getFirst() + ", p1: "+ p1k1.getSecond());
        System.out.println("K2: " + p2k2.getFirst() + ", p2: "+ p2k2.getSecond());
        System.out.println("K3: " + p3k3.getFirst() + ", p3: "+ p3k3.getSecond());
    }
}