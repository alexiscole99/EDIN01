import java.math.* ;
import java.io.* ;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Helper {
    private static int fLength = 1024;
    private static int[] factorBase = generateFactorBase(fLength) ;
    private static int smoothness = factorBase[fLength-1] + 1;
    private static int rLength = fLength + fLength/10 ;
    private static BigInteger[] rValues = new BigInteger[rLength] ;
    private static int rPart1 = 1 ;
    private static int rPart2 = 1 ;
    private static int times_looped = 0 ;
    
    private static int[] generateFactorBase(int size) {
        int n = 0, i, j, k;
        int[] primes = new int[size];
        for (i = 2; n < size; i++) {
            k = (int) Math.floor(Math.sqrt(i) + 1) ;
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
    
    private static int[] primeFactors(BigInteger x){
        int[] primeFactors = new int[fLength];
        for(int i = 0; i < fLength; i++){
            while(x.mod(BigInteger.valueOf(factorBase[i])).equals(BigInteger.ZERO)){
                primeFactors[i]++;
                x = x.divide(BigInteger.valueOf(factorBase[i])) ;
            }
        }
        if (!x.equals(BigInteger.ONE)) {
            primeFactors[0] = -1 ;
        }
        return primeFactors;
    }
    
/*
    private static LinkedHashMap<Integer,Integer> primeMap(ArrayList<Integer> primeFactors){
        LinkedHashMap<Integer,Integer> primeMap = new LinkedHashMap<Integer,Integer>();
        for(int i=0; i<primeFactors.size(); i++){
            int count = primeMap.containsKey(primeFactors.get(i)) ? primeMap.get(primeFactors.get(i)) : 0;
            primeMap.put(primeFactors.get(i), count + 1);
        }
        return primeMap;
    }
*/
    private static int[] toBinary(int[] row){
        int[] newRow = new int[fLength] ;
        for (int i = 0; i<fLength;i++) {
            newRow[i] = row[i]%2;
        }
        return newRow;
    }

    private static boolean arrayEqual(int[] x, int[] y){
        for (int i = 0; i < fLength; i++) {
            if (x[i] != y[i]) 
                return false;  
        }
        return true; 
    }
    
    // Call this function from main() with BigInteger as a string
    public static BigInteger factorFromString (String n) throws IOException {
        BigInteger x = new BigInteger(n) ;
        int[][] exponents = getExponents(x) ;
        int[][] possibles = eliminate() ;
        
        for (int i = 0; i < possibles.length; i++) {
            BigInteger combinedR = BigInteger.ONE ;
            int[] combined_exp = new int[fLength] ;
            for (int j = 0; j < rLength; j++) {
                if (possibles[i][j] == 1) {
                    combinedR = combinedR.multiply(rValues[j]) ;
                    //System.out.print(rValues[j]+" ") ;
                    for (int k = 0; k < fLength; k++) {
                        combined_exp[k] = combined_exp[k] + exponents[j][k] ;
                        //System.out.print(exponents[j][k] + "; ") ;
                    }
                    //System.out.println() ;
                }
            }
            
            BigInteger combinedFactors = BigInteger.ONE;
            for (int j = 0; j < fLength; j++) {
                combinedFactors = combinedFactors.multiply(BigInteger.valueOf(factorBase[j]).pow(combined_exp[j]/2)) ;
            }
            //System.out.println() ;
            //System.out.println(combinedR.pow(2).mod(x) + " " + combinedFactors.pow(2).mod(x)) ;
            
            // test(int r, int factors) is not yet implemented
            // Should just do the gcd of the two numbers
            BigInteger result = test(combinedR, combinedFactors, x) ;
            if (!result.equals(BigInteger.ONE)) {
                return result ;
            }
        }
        exponents = new int[0][0] ;
        possibles = new int[0][0] ;
        //System.out.println(times_looped++) ;
        return factorFromString(n) ;
    }
    
    public static BigInteger getOtherFactor (String n, BigInteger factor) {
        BigInteger x = new BigInteger(n) ;
        return x.divide(factor) ;
        
    }
    
    private static BigInteger test (BigInteger r, BigInteger factors, BigInteger n) {
        /*System.out.print("Test: ") ;
        if (!r.pow(2).mod(n).equals(factors.pow(2).mod(n))) {
            System.out.println("Lies") ;
        } else {
            System.out.println() ;
        }*/
        return gcd (factors.subtract(r), n) ;
    }
    
    private static BigInteger gcd (BigInteger a, BigInteger b) {
        ArrayList<BigInteger> r = new ArrayList<BigInteger>() ;
        r.add(0, a) ;
        r.add(1, b) ;
        int i = 1 ;
        while (!r.get(i).equals(BigInteger.ZERO)) {
            r.add(r.get(i-1).mod(r.get(i))) ;
            i++ ;
        }
        return r.get(i-1) ;
    }
        

    private static int[][] getExponents(BigInteger n) throws IOException {
        //matrix holds values of exponents
        //gaussian elimination then converts matrix to binary
        int[][] matrix = new int[rLength][fLength];
        int[] tempRow = new int[fLength];
        int[] tempRowBinary = new int[fLength];
        int currentRowIndex = 0;
        BigInteger r;
        int[] pFactors = new int[fLength];
        
        FileWriter fw = new FileWriter("gauss.in",true);
        PrintWriter pw = new PrintWriter(fw);
        pw.println(rLength + " " + fLength);

        //increment j when want to test a new r, until == sqrt(n), then reset j and increment k
        //increment k when found an r that works
        while(currentRowIndex < rLength){
            if (rPart2 > rPart1) {
                rPart2 = 1 ;
                rPart1++ ;
            }
            r = squareRoot(n.multiply(BigInteger.valueOf(rPart2))).add(BigInteger.valueOf(rPart1));
            pFactors = primeFactors((r.pow(2)).mod(n));
            
            //case 1: not B-smooth, increment k
            if(pFactors[0] == -1){
                rPart2++ ;
                continue ;
            }
            
            //create new row in matrix
            for(int i=0; i<fLength;i++){
                tempRow[i] = pFactors[i];
            }
            
            //case 2: not a unique binary row in matrix, increment k
            //must test the binary row (using toBinary) against all other binary rows in matrix
            /*if(currentRowIndex != 0){
                tempRowBinary = toBinary(tempRow);
                boolean equalArray = false ;
                for(int i = 0; i<currentRowIndex; i++){
                    //if equal, clear everything and break to get to next iteration of while loop
                    if(arrayEqual(tempRowBinary, toBinary(matrix[i]))){
                        rPart2++ ;
                        equalArray = true ;
                        break;
                    }
                }
                if(equalArray) {
                    continue ;
                }
            }*/
            
            //case 3: valid row, add to matrix, increment k and currentRow
            for(int i=0;i<fLength;i++){
                matrix[currentRowIndex][i] = tempRow[i];
                pw.print(tempRow[i] + " ") ;
            }
            pw.println() ;
            rValues[currentRowIndex] = r ;
            rPart2++;
            currentRowIndex++;
        }
        
        pw.close() ;
        return matrix ;
    }
    
    private static int[][] eliminate() throws IOException {
        // Write matrix to file
        
/*        for (int i = 0; i < m; i++) {
            matrix = matrix + "" + exponents[i][0] ;
            for (int j = 1; j < n; j++) {
                matrix = matrix + " " + exponents[i][j] ;
            }
            matrix = matrix + "\n" ;
        }
        System.out.println("Checkpoint") ;
        FileWriter fileWriter = new FileWriter("gauss.in") ;
        fileWriter.write(matrix) ;
        fileWriter.close() ;*/

        // Run GaussBin.exe to get the binary matrix
        try {
            ProcessBuilder builder = new ProcessBuilder("GaussBin.exe", "gauss.in", "gauss.out") ;
            builder.redirectErrorStream(true) ;
            Process gaussBin = builder.start() ;
            gaussBin.waitFor() ;
        }
        catch (InterruptedException e) { }
        
        // Output matrix from gauss.out
        BufferedReader reader = new BufferedReader(new FileReader("gauss.out")) ;
        int m = Integer.parseInt(reader.readLine()) ; // get number of rows for output
        int n = rLength ;
        int[][] result = new int[m][n] ;
        for (int i = 0; i < m; i++) {
            String line = reader.readLine() ;
            String[] temp = line.split(" ") ;
            for (int j = 0; j < n; j++) {
                result[i][j] = Integer.parseInt(temp[j]) ;
            }
        }
        reader.close() ;
        File file1 = new File("gauss.in") ;
        File file2 = new File("gauss.out") ;
        file1.delete() ;
        file2.delete() ;
        return result ;
    }
    
}