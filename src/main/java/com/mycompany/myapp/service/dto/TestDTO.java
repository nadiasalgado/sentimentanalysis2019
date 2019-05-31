package com.mycompany.myapp.service.dto;
import java.io.Serializable;
import java.util.Objects;
import twitter4j.*;
import twitter4j.api.*;
import twitter4j.conf.*;
import java.util.*;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileReader;
import java.io.FileNotFoundException;
import javax.script.Invocable;
import org.springframework.core.io.ClassPathResource;
import java.io.InputStream;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.IOException;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.ie.util.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.semgraph.*;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.*;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import org.ejml.simple.SimpleMatrix;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.ling.CoreAnnotations;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
/**
 * A DTO for the Test entity.
 */
public class TestDTO implements Serializable {

    private String VposTxt;
    private String posTxt;
    private String neuTxt;
    private String negTxt;
    private String VnegTxt;
    private String statText;
    private String type;
    private int VposCount = 0;
    private int posCount = 0;
    private int neuCount = 0;
    private int negCount = 0;
    private int VnegCount = 0;
    private int num;
    static final String CONSUMER_KEY = "8SnGnIiZFm8kEHrmeLndpHiZD";
    static final String CONSUMER_SECRET = "2GtcWlDslfdemvOKL3i09T0p6HqbZ3JEO9ix8032HepyLs2hKj";
    static final String ACCESS_TOKEN = "1105194942815707136-jNcbwDQR6hfyh0MuLY1gVeszR01hy3";
    static final String ACCESS_TOKEN_SECRET = "BklBxUF4fsCQwEKNCBmBxFMqcqhFWGhrJEygbrJbnlMzP";
    
    public String getstatText() {
        return statText;
    }
    
    public String getneuTxt() {
        return neuTxt;
    }
    
    public String getnegTxt() {
        return negTxt;
    }
    
    public String getVnegTxt() {
        return VnegTxt;
    }
    
    public String getposTxt() {
        return posTxt;
    }
    
    public String getVposTxt() {
        return VposTxt;
    }
    
    public void setstatText(String txt) {
        this.statText = txt;
    }
    
    public int getneuCount() {
        return neuCount;
    }
    
    public String gettype() {
        return type;
    }
    
    public int getposCount() {
        return posCount;
    }
    
    public int getnegCount() {
        return negCount;
    }
    
    public int getVnegCount() {
        return VnegCount;
    }
    
    public int getVposCount() {
        return VposCount;
    }
    
    public int getnum() {
        return num;
    }
    
    public void getTweet(String word, int c, String typ) throws TwitterException{
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
        .setOAuthConsumerKey(CONSUMER_KEY)
        .setOAuthConsumerSecret(CONSUMER_SECRET)
        .setOAuthAccessToken(ACCESS_TOKEN)
        .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
        Map<String,String> params=new LinkedHashMap<String,String>();
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        Query query = new Query(word);
        query.setCount(c);
        if(typ == "popular"){
            query.setResultType(Query.POPULAR);
        }else if(typ == "recent"){
            query.setResultType(Query.RECENT);
        }
        query.lang("en");
        QueryResult result;
        result = twitter.search(query);
        List<Status> tweets = result.getTweets();
        
        for(int i = 0; i < tweets.size(); i = i + 1){
            Status out = tweets.get(i);
            String line = out.getText();
            this.statText = out.getText();
            
            //Sentiment Test
            double setVeryPositive = 0;
            double setPositive = 0;
            double setNeutral = 0;
            double setNegative = 0;
            double setVeryNegative = 0;
            
            Properties props = new Properties();
            props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
            StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
            if (line != null && line.length() > 0) {
                int longest = 0;
                Annotation annotation = pipeline.process(line);
                for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                    Tree tree = sentence.get(SentimentCoreAnnotations.AnnotatedTree.class);
                    int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                    SimpleMatrix sm = RNNCoreAnnotations.getPredictions(tree);
                    String partText = sentence.toString();
                    
                    setVeryPositive = (double)Math.round(sm.get(4) * 100d);
                    setPositive = (double)Math.round(sm.get(3) * 100d);
                    setNeutral = (double)Math.round(sm.get(2) * 100d);
                    setNegative = (double)Math.round(sm.get(1) * 100d);
                    setVeryNegative = (double)Math.round(sm.get(0) * 100d);
                }
                
            }
            if (setVeryPositive > setVeryNegative && setVeryPositive > setPositive && setVeryPositive > setNeutral && setVeryPositive > setNegative){
                this.VposCount = VposCount + 1;
                this.VposTxt = line;
            }else if(setPositive > setVeryPositive && setPositive > setVeryNegative && setPositive > setNeutral && setPositive > setNegative){
                this.posCount = posCount + 1;
                this.posTxt = line;
            }else if(setNeutral > setVeryPositive && setNeutral > setVeryNegative && setNeutral > setPositive && setNeutral > setNegative){
                this.neuCount = neuCount + 1;
                this.neuTxt = line;
            }else if(setNegative > setVeryPositive && setNegative > setVeryNegative && setNegative > setNeutral && setNegative > setPositive){
                this.negCount = negCount + 1;
                this.negTxt = line;
            }else{
                this.VnegCount = VnegCount + 1;
                this.VnegTxt = line;
            }
        }
    }
}
 
