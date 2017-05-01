package termfrequency;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class TermFrequency {
    
    private static final String FILENAME = "sample.txt";
    private Scanner x;
    private Hashtable<String, Double> wordHash;
    private static Hashtable<String, Double> commentHash;
    private int wordCount;
    private static Set<String> commentLine;
    

    /**
     * import all words (no duplicates) into the wordHash hashtable with the number of occurences within the dataset
     */
    public void importWords(){
        try{
            x=new Scanner(new File(FILENAME));
        }
        catch(Exception e){
            System.out.println("Could not find file"); 
        }

        wordHash = new Hashtable<String, Double>();
        String a;
        wordCount = 0;
        while (x.hasNext()) {
            a = x.next();
            a = a.toLowerCase();
            wordCount++;
            if(wordHash.containsKey(a))
                wordHash.put(a, wordHash.get(a) + 1);
            else
                wordHash.put(a,1.0);
        }

        x.close();
    }
    
    /**
     * Calculate each word probability
     */
    public void calculateWordProbability(){
        double probability;
        for(String key: wordHash.keySet()){
            probability = wordHash.get(key)/wordCount;
           // System.out.println(wordCount);
            wordHash.put(key, probability);
        }
    }
    
    /**
     * Calculates the weight of each comment
     */
    public void calculateCommentWeight(){
        commentHash = new Hashtable<String, Double>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(FILENAME));            
            String sCurrentLine;   
            String [] arrayWord = null;
            double commentWeight;            
            
            while ((sCurrentLine = br.readLine()) != null) {
                commentWeight = 0.0;
                sCurrentLine = sCurrentLine.toLowerCase();
                
                commentLine = new HashSet<>(Arrays.asList(sCurrentLine.split(" +")));
                for (String temp : commentLine) {
                    commentWeight += wordHash.get(temp);
                }
                commentHash.put(sCurrentLine, commentWeight);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Find the comment with highest weight
     * @return maxKey - comment string value
     */
    public String findTopComment(){
        String maxKey=null;
        Double maxValue = Double.MIN_VALUE; 
        for(Map.Entry<String,Double> entry : commentHash.entrySet()) {
             if(entry.getValue() > maxValue) {
                 maxValue = entry.getValue();
                 maxKey = entry.getKey();
             }
        }
        return maxKey;
    }    
    
    public static void main(String[] args) {
        
        System.out.println("How many top comments? ");
        Scanner in = new Scanner(System.in);
        int numberOfComments = in.nextInt();
        
        TermFrequency r =new TermFrequency();
        r.importWords();
        r.calculateWordProbability();
        r.calculateCommentWeight();
        String maxKey;
        
        for(int i = 0; i<numberOfComments; i++)
        {
            if (commentHash.isEmpty())
                break;
            
            maxKey = r.findTopComment();
            
            System.out.println("Comment #" + (int)(i+1) +": "+ maxKey + " | Weight = "+commentHash.get(maxKey));
            commentHash.remove(maxKey);
            
        }
        
    }
    
}
