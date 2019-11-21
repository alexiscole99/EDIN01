import java.io.*;
import java.math.* ;

public class Factor {
    public static void main(String[] args) throws IOException{
        String number = "31741649" ;
        BigInteger x = Helper.factorFromString(number) ;
        BigInteger y = Helper.getOtherFactor(number, x) ;
        System.out.println(x) ;
        System.out.println(y) ;
    }
}