import java.math.* ;
import java.io.* ;
import java.util.ArrayList;


public class Helper {
    private static int fLength = 1024 ;
    private static int[] factorBase = generateFactorBase(fLength);
    private static int smoothness = factorBase[fLength-1] + 1 ;
    private static int rLength = fLength + 10;

    private static int[] generateFactorBase(int size) {
        int n = 0, i, j, k;
        int[] primes = new int[size];
        for (i = 2; n < size; i++) {
            k = Math.sqrt(i) + 1 ;
            for (j = 0; j < n && primes[j]<=k; j++) {
                if ((i % primes[j])==0) {
                    j = 0;
                    break ;
                }
            }
            if (j!=0 || n==0) {
                primes[n++] = i ;
            }
        }
        
        return primes ;
    }
    
    private static BigInteger squareRoot(BigInteger x) {
        BigInteger right = x, left = BigInteger.ZERO, mid;
        while(right.subtract(left).compareTo(BigInteger.ONE) > 0) {
            mid = (right.add(left)).shiftRight(1);
            if(mid.multiply(mid).compareTo(x) > 0)
                right = mid;
            else
                left = mid;
        }
        return left;
    }
    
    public static ArrayList<Integer> primeFactors(BigInteger x){
        ArrayList<Integer> primeFactors = new ArrayList<Integer>();
        for(BigInteger i=BigInteger.valueOf(2); i.compareTo(x) == -1; i++){
            while(x.mod(i)==0){
                primeFactors.add(i.intValue());
            }
        }if(x.intValue()>2){
            primeFactors.add(x.intValue());
        }
        return primeFactors;
    }

    public int[] toBinary(int[] row){
        for (int i = 0; i<fLength;i++) {
            row[i] = row[i]%2;
        }
        return row;
    }

    public static factor(BigInteger n) {
        //matrix holds values of exponents
        //gaussian elimination then converts matrix to binary
        int[][] matrix = new int[rLength][fLength];
        int[] temp = new int[fLength];
        int currentRow = 0;
        int j,k = 1;
        BigInteger r;
        ArrayList<Integer> pFactors = new ArrayList<Integer>();

        //increment j when want to test a new r, until == sqrt(n), then reset j and increment k
        //increment k when found an r that works
        while(currentRow < rLength){
            r = floor(squareRoot(n.multiply(BigInteger.valueOf(k)))) + j;
            pFactors = primeFactors(r);
            
            //case 1: not B-smooth, increment j
            if(pFactors.get(pFactors.size()-1) > factorBase[-1]){
                if(BigInteger.valueOf(j).compareTo(n) == -1){
                    j++;
                }
                else{
                    k++;
                    j = 1;
                }
                continue;
            }
            //case 2: not a unique binary row in matrix, increment j
            //must test the binary row (using toBinary) against all other binary rows in matrix

            //case 3: valid row, add to matrix, increment k and currentRow
        }

    }
}