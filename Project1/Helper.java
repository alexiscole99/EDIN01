import java.math.* ;
import java.io.* ;


public class Helper {
    private int fLength = 900 ;
    private int[] factorBase = generateFactorBase(fLength);
    private int smoothness = factorBase[fLength-1] + 1 ;
    private int rLength = fLength + 100 ;
    private int[] generateFactorBase(int size) {
        int n = 0, i, j, k;
        int[] primes = new int[size];
        for (i = 2; n < size; i++) {
            k = Math.sqrt(i) + 1 ;
            for (j = 0; j < n && primes[j]<=k; j++) {
                if (!(i % primes[j])) {
                    j = 0;
                    break ;
                }
            }
            if (j || !n) {
                primes[n++] = i ;
            }
        }
        
        return primes ;
    }
    
    private BigInteger squareRoot(BigInteger x) {
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
    
    public static factor(BigInteger n) {
        
    }
}