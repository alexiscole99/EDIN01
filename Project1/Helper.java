import java.math.* ;
import java.io.* ;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Helper {
    private static int fLength = 8192 ;
    private static int[] factorBase = generateFactorBase(fLength);
    private static int smoothness = factorBase[fLength-1] + 1 ;
    private static int rLength = fLength+10;
    private static BigInteger[] rValues = new BigInteger[rLength] ;
    private static int rPart1 = 1 ;
    private static int rPart2 = 1 ;

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
                primeFactors[i] = factorBase[i];
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
        for (int i = 0; i<fLength;i++) {
            row[i] = row[i]%2;
        }
        return row;
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
        int[][] possibles = eliminate(exponents) ;
        
        for (int i = 0; i < possibles.length; i++) {
            BigInteger combinedR = BigInteger.ONE ;
            int[] combined_exp = new int[fLength] ;
            for (int j = 0; j < rLength; j++) {
                if (possibles[i][j] == 1) {
                    combinedR = combinedR.multiply(rValues[j]) ;
                    for (int k = 0; k < fLength; k++) {
                        combined_exp[k] = exponents[j][k] ;
                    }
                }
            }
            
            BigInteger combinedFactors = BigInteger.ONE;
            for (int j = 0; j < fLength; j++) {
                combinedFactors = combinedFactors.multiply(BigInteger.valueOf(factorBase[j]).pow(combined_exp[j]/2)) ;
            }
            
            // test(int r, int factors) is not yet implemented
            // Should just do the gcd of the two numbers
            BigInteger result = test(combinedR, combinedFactors, x) ;
            if (!result.equals(BigInteger.ONE)) {
                return result ;
            }
        }
        System.out.println("Check " + rPart1 + " " + rPart2) ;
        return factorFromString(n) ;
    }
    
    public static BigInteger getOtherFactor (String n, BigInteger factor) {
        BigInteger x = new BigInteger(n) ;
        return x.divide(factor) ;
        
    }
    
    private static BigInteger test (BigInteger r, BigInteger factors, BigInteger n) {
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
        

    private static int[][] getExponents(BigInteger n) {
        //matrix holds values of exponents
        //gaussian elimination then converts matrix to binary
        int[][] matrix = new int[rLength][fLength];
        int[] tempRow = new int[fLength];
        int[] tempRowBinary = new int[fLength];
        int currentRowIndex = 0;
        BigInteger r;
        int[] pFactors = new int[fLength];
        LinkedHashMap<Integer,Integer> pMap = new LinkedHashMap<Integer,Integer>();

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
            if(currentRowIndex != 0){
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
            }
            
            //case 3: valid row, add to matrix, increment k and currentRow
            for(int i=0;i<fLength;i++){
                matrix[currentRowIndex][i] = tempRow[i];
            }
            rValues[currentRowIndex] = r ;
            rPart2++;
            currentRowIndex++;
        }
        return matrix ;
    }
    
    private static int[][] eliminate(int[][] exponents) throws IOException {
        // Write matrix to file
        int m = rLength ;
        int n = fLength ;
        String firstLine = "" + m + " " + n ;
        String matrix = firstLine + "\n";
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
        FileWriter fw = new FileWriter("gauss.in",true);
        PrintWriter pw = new PrintWriter(fw);
        pw.println(matrix);
        for(int i = 0; i<m; i++){
            matrix = "" + exponents[i][0];
            for(int j = 1; j<n; j++){
                matrix = matrix + " " + exponents[i][j];
            }
            matrix = matrix + "\n" ;
            pw.println(matrix);
        }
        pw.close();

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
        m = Integer.parseInt(reader.readLine()) ; // get number of rows for output
        n = rLength ;
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
    
    public static int[][] eliminateTester(int[][] exponents) throws IOException {
        return eliminate(exponents) ;
    }
}