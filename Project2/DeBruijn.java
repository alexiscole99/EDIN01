import java.util.LinkedList; 
import java.util.Queue;
import java.util.Arrays;
import java.util.ArrayList;;

public class DeBruijn{
    //C(D): D^4+D+1 gives x values
    //C(D): 2D^4+2D^3+2D^2+2D+1 gives y values
    private int[] coef;
    private int z;
    private int n = 4;
    private int[] start;
    private ArrayList<Integer> end;
    private ArrayList<Integer> current;

    public DeBruijn(int[] coef, int z, int[] start, ArrayList<Integer> end){
        this.coef = coef;
        this.z = z;
        this.start = start;
        this.end = end;
        this.current = new ArrayList<Integer>();
        for(int i=0; i<start.length; i++){
            current.add(start[i]);
        }
    }

    public int getNextVal(){
        int addToEnd = 0;
        int nextElem = 0;
        
        //shift into zero case
        int intoZero = 0;
        if(current.equals(end)){
            if(z==2){
                intoZero = 1;
            }
            if(z==5){
                intoZero = 3;
            }
        }

        //shift out of zero case
        int outOfZero = 0;
        ArrayList<Integer> zeros = new ArrayList<Integer>(4);
        for(int i=0; i<4;i++){
            zeros.add(0);
        }
        if(current.equals(zeros)){
            if(z==2){
                outOfZero = 1;
            }
            if(z==5){
                outOfZero = 2;
            }
        }

        for(int i=0; i<n; i++){
            addToEnd += current.get(i) * coef[i];
        }

        addToEnd += (intoZero+outOfZero);
        if(z-addToEnd >= 0){
            addToEnd = (z - addToEnd) % z;
        }else{
            int tmp = z-addToEnd;
            addToEnd = ((tmp % z)+z)%z;
        }
        nextElem = current.get(0);
        //System.out.println(current);
        current.remove(0);
        current.add(addToEnd);
        return nextElem;
    }

}