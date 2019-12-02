import java.io.*;
import java.math.* ;

public class Factor {
    public static void main(String[] args) throws IOException{
        String number = "172553592925213423665977" ;
        //String number = "307561" ;
        BigInteger x = Helper.factorFromString(number) ;
        BigInteger y = Helper.getOtherFactor(number, x) ;
        System.out.println(x) ;
        System.out.println(y) ;
    }
}