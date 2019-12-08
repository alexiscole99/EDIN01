import java.util.ArrayList;

public class CorrAttack{
    private int L1 = 13;
    private int[] cd1 = new int[] {1,1,0,1,0,1,1,0,0,1,1,0,1};
    private int L2 = 15;
    private int cd2 = new int[] {0,1,0,1,0,1,1,0,0,1,1,0,1,0,1};
    private int L3 = 17;
    private int cd3 = new int[] {0,1,0,1,1,0,0,1,0,1,0,0,1,0,0,1,1};
    private ArrayList<Integer> givenSeq;
    private int seqLength;
    private ArrayList<ArrayList<Integer>> K;

    public CorrAttack(String sequence){
        givenSeq  = new ArrayList<Integer>();
        K = new ArrayList<ArrayList<Integer>>();
        for(int i=0;i<sequence.length();i++){
            givenSeq.add(Character.getNumericValue(sequence.charAt(i)));
        }
        seqLength = givenSeq.size();
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

