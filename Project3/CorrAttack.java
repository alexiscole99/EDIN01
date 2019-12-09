import java.util.ArrayList;

public class CorrAttack{
    private int[] cd1 = new int[] {1,0,1,1,0,0,1,1,0,1,0,1,1};
    private int[] cd2 = new int[] {1,0,1,0,1,1,0,0,1,1,0,1,0,1,0};
    private int[] cd3 = new int[] {1,1,0,0,1,0,0,1,0,1,0,0,1,1,0,1,0};
    private ArrayList<String> K1;
    private ArrayList<String> K2;
    private ArrayList<String> K3;

    public CorrAttack(String sequence){
        K1 = new ArrayList<String>();
        K2 = new ArrayList<String>();
        K3 = new ArrayList<String>();
    }

    public String intToBinaryString(int num, int digits) {
		if (digits > 0) {
			return String.format("%" + digits + "s", Integer.toBinaryString(num)).replaceAll(" ", "0");
		}
		return null;
	}

    public void generatePossibleKeys(){
        String s;
        for(int i = 0; i<(int)Math.pow(2,13); i++){
            s = intToBinaryString(i,13);
            K1.add(s);
        }
        for(int i = 0; i<(int)Math.pow(2,15); i++){
            s = intToBinaryString(i,15);
            K2.add(s);
        }
        for(int i = 0; i<(int)Math.pow(2,17); i++){
            s = intToBinaryString(i,17);
            K3.add(s);
        }
    }

    public String generateStream(String K, int[] cd, int n){
        ArrayList<Integer> register = new ArrayList<Integer>();
        String stream = "";
        int toAppend = 0;
        for(int i=0; i<K.length();i++){
            char ch = K.charAt(i);
            int x = Integer.parseInt(String.valueOf(ch));
            register.add(x);
        }

        for(int i=0;i<n;i++){
            toAppend = 0;
            for(int j=0;j<register.size();j++){
                toAppend += register.get(j) * cd[j];
            }
            toAppend = toAppend % 2;
            stream += register.get(0);
            register.remove(0);
            register.add(toAppend);
        }
        return stream;
    }


    public int hammingDistance(String x, String y){
        int dist = 0;
        for(int i=0;i<x.length();i++){
            if(x.charAt(i)!=y.charAt(i)){
                dist++;
            }
        }
        return dist;
    }

    public Pair initialState(String given, ArrayList<String> K, int[] cd){
        int n = given.length();
        double p, maxP, dif, maxDif;
        maxDif=0;
        maxP=0;
        double hDist;
        String initK = "";
        String generated = "";
        for(int i=0; i<K.size();i++){
            generated = generateStream(K.get(i), cd, n);
            hDist = (double)hammingDistance(generated, given);
            p = 1 - (hDist/n);
            dif = Math.abs(.5 - p);
            if (dif > maxDif){
                maxDif = dif;
                maxP = p;
                initK = K.get(i);
            }
        }
        Pair initKPair = new Pair(initK,maxP);
        return initKPair;
    }

    public ArrayList<String> getK1(){
        return K1;
    }

    public ArrayList<String> getK2(){
        return K2;
    }

    public ArrayList<String> getK3(){
        return K3;
    }

    public int[] getcd1(){
        return cd1;
    }

    public int[] getcd2(){
        return cd2;
    }

    public int[] getcd3(){
        return cd3;
    }

    public boolean check(String given, String K1, String K2, String K3){
        int n = given.length();
        String stream1 = generateStream(K1, cd1, n);
        String stream2 = generateStream(K2, cd2, n);
        String stream3 = generateStream(K3, cd3, n);
        String generated = "";

        for(int i=0; i<n; i++){
            if(stream1.charAt(i)=='1' && stream2.charAt(i)=='1'){
                generated+="1";
            }else if(stream1.charAt(i)=='1' && stream3.charAt(i)=='1'){
                generated+="1";
            }else if(stream2.charAt(i)=='1' && stream3.charAt(i)=='1'){
                generated+="1";
            }else{
                generated+="0";
            }
        }  
        if(given.equals(generated)){
            return true;
        }
        return false;
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

