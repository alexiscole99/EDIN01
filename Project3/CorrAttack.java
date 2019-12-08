import java.util.ArrayList;

public class CorrAttack{
    private int L1 = 13;
    private int[] cd1 = new int[] {1,1,0,1,0,1,1,0,0,1,1,0,1};
    private int L2 = 15;
    private int cd2 = new int[] {0,1,0,1,0,1,1,0,0,1,1,0,1,0,1};
    private int L3 = 17;
    private int cd3 = new int[] {0,1,0,1,1,0,0,1,0,1,0,0,1,0,0,1,1};
    private String givenSeq;
    private int seqLength;
    private ArrayList<String> K1;
    private ArrayList<String> K2;
    private ArrayList<String> K3;

    public CorrAttack(String sequence){
        givenSeq  = new ArrayList<String>();
        K1 = new ArrayList<String>();
        K2 = new ArrayList<String>();
        K3 = new ArrayList<String>();
        givenSeq = sequence;
        seqLength = givenSeq.size();
    }

    public void generatePossibleKeys(){
        String s;
        for(int i = 0; i<(int)Math.pow(i,13); i++){
            s = Integer.toBinaryString(i);
            K1.add(s);
        }
        for(int i = 0; i<(int)Math.pow(i,15); i++){
            s = Integer.toBinaryString(i);
            K2.add(s);
        }
        for(int i = 0; i<(int)Math.pow(i,17); i++){
            s = Integer.toBinaryString(i);
            K3.add(s);
        }

    }

    public int hammingDistance(String x, String y){
        int dist = 0;
        for(int i=0;i<x.size();i++){
            if(x.charAt(i)!=y.charAt(i)){
                dist++;
            }
        }
        return dist;
    }
    
    /*
    1. generate all possible sequences of LFSRs
    2. generate remaining digits for each possible sequence
    2b. must be separate from initial state bc only return initial state
    3. calculate Hamming Distance
    4. calculate p*
    5. return sequence that creates maximum dif bwn p* and .5 for each LFSR
    */
}

