package lse;

import java.io.FileNotFoundException;

public class LSETesting {
    public static void main(String[] args) throws FileNotFoundException{
        LittleSearchEngine l = new LittleSearchEngine();

        String docsFile       = "docs.txt";
        String noiseWordsFile = "noisewords.txt";
        l.makeIndex(docsFile, noiseWordsFile);

        System.out.println(l.getKeyword("hello."));
        System.out.println(l.getKeyword("hel.lo."));
        System.out.println(l.getKeyword("hello.?!"));
        System.out.println(l.getKeyword("HelLo!?."));
        System.out.println(l.getKeyword("HE.LL.O"));
        System.out.println(l.getKeyword("Through"));

        System.out.println();

        String test = "nice";
        System.out.println(l.keywordsIndex.containsKey(test));
        System.out.println(l.keywordsIndex.get(test));
    }
    
}
