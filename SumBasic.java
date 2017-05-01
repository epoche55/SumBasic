package sumbasic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class SumBasic {
    private static final String FILENAME = "sample.txt";
    private Scanner x;
    private Hashtable<String, Double> wordHash;
    private static Hashtable<String, Double> commentHash;
    private int wordCount;
    private static Set<String> hset;
    
    /**
     * imports all words (no duplicates) into the wordHash hashtable
     */
    public void importComments(){
        
        try{
            Scanner x = new Scanner(new File(FILENAME));
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
                
                hset = new HashSet<>(Arrays.asList(sCurrentLine.split(" +")));
                for (String temp : hset) {
                    commentWeight += wordHash.get(temp);
                }
                commentWeight = commentWeight / hset.size();                
                commentHash.put(sCurrentLine, commentWeight);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recalcuate each comment weight 
     */
    public void updateCommentWeight(){
        double commentWeight; 
        Set<String> keys = commentHash.keySet();
        for(String key: keys){
            
            commentWeight=0.0;
            hset = new HashSet<>(Arrays.asList(key.split(" +")));
            for (String temp : hset) {
                commentWeight += wordHash.get(temp);
            }
            
            commentWeight = commentWeight / hset.size();  
            commentHash.put(key, commentWeight);
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
    
    /** 
     * update word probability from chosen comment
     * @param topComment 
     */
    public void updateWordProbability(String topComment){
        hset = new HashSet<>(Arrays.asList(topComment.split(" +")));
        double newProbability;
        for (String temp : hset) {
            newProbability = Math.pow(wordHash.get(temp), 2);
            wordHash.put(temp, newProbability );
        }
    }

    public static void main(String[] args) {
        System.out.println("How many comments? ");
        Scanner in = new Scanner(System.in);
        int numberOfComments = in.nextInt();
        
        SumBasic r =new SumBasic();
        r.importComments();
        r.calculateWordProbability();
        r.calculateCommentWeight();
        String maxKey;
        
        for(int i = 0; i<numberOfComments; i++)
        {
            if (commentHash.isEmpty())
                break;
            maxKey = r.findTopComment();
            
            System.out.println("Comment #" + (int)(i+1) +": "+ maxKey + " Value = "+commentHash.get(maxKey));
            r.updateWordProbability(maxKey);
            
            commentHash.remove(maxKey);
            r.updateCommentWeight();
        }
    }
}
