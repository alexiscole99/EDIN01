import java.math.* ;
import java.io.* ;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


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
    
    private static ArrayList<Integer> primeFactors(BigInteger x){
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

    private static LinkedHashMap<Integer,Integer> primeMap(ArrayList<Integer> primeFactors){
        LinkedHashMap<Integer,Integer> primeMap = new LinkedHashMap<Integer,Integer>();
        for(int i=0; i<primeFactors.size(); i++){
            int count = primeMap.containsKey(primeFactors.get(i)) ? primeMap.get(word) : 0;
            primeMap.put(word, count + 1);
        }
        return primeMap;
    }

    private static int[] toBinary(int[] row){
        for (int i = 0; i<fLength;i++) {
            row[i] = row[i]%2;
        }
        return row;
    }

    private static boolean arrayEqual(int[] x, int[] y){
        for (int i = 0; i < fLength; i++) 
            if (x[i] != y[i]) 
                return false;  
        return true; 
    }

    public static factor(BigInteger n) {
        //matrix holds values of exponents
        //gaussian elimination then converts matrix to binary
        int[][] matrix = new int[rLength][fLength];
        int[] tempRow = new int[fLength];
        int[] tempRowBinary = new int[fLength];
        int currentRowIndex = 0;
        int j,k = 1;
        BigInteger r;
        ArrayList<Integer> pFactors = new ArrayList<Integer>();
        LinkedHashMap<Integer,Integer> pMap = new LinkedHashMap<Integer,Integer>();

        //increment j when want to test a new r, until == sqrt(n), then reset j and increment k
        //increment k when found an r that works
        while(currentRowIndex < rLength){
            r = floor(squareRoot(n.multiply(BigInteger.valueOf(k)))) + j;
            pFactors = primeFactors((r.pow(2)).mod(n));

            
            //case 1: not B-smooth, increment j
            if(pFactors.get(pFactors.size()-1) > factorBase[-1]){
                if(BigInteger.valueOf(j).compareTo(squareRoot(n)) == -1){
                    j++;
                }
                else{
                    k++;
                    j = 1;
                }
                pMap.clear();
                pFactors.clear();
                continue;
            }
            
            //create map of prime factors to exponents
            pMap = primeMap(pFactors);

            //create new row in matrix
            for(int i=0; i<fLength;i++){
               if(pMap.containsKey(factorBase[i])){
                   tempRow[i] = pMap.get(factorBase[i]);
               }else{
                   tempRow[i] = 0;
               }
            }

            //case 2: not a unique binary row in matrix, increment j
            //must test the binary row (using toBinary) against all other binary rows in matrix
            if(currentRowIndex != 0){
                tempRowBinary = toBinary(tempRow);
                for(int i = 0; i<currentRowIndex; i++){
                    //if equal, clear everything and break to get to next iteration of while loop
                    if(arrayEqual(tempRowBinary, toBinary(matrix[i]))){
                        if(BigInteger.valueOf(j).compareTo(squareRoot(n)) == -1){
                            j++;
                        }
                        else{
                            k++;
                            j = 1;
                        }
                        pMap.clear();
                        pFactors.clear();
                        break;
                    }
                }
            }
            
            //case 3: valid row, add to matrix, increment k and currentRow
            for(int i=0;i<fLength;i++){
                matrix[currentRowIndex][i] = tempRow[i];
            }
            k++;
            currentRowIndex++;
            pMap.clear();
            pFactors.clear();
        }

    }
}