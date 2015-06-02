package Visualization.Text;

import Data.InSituDataSet;
import processing.core.*;
import wordcram.*;

/**
 * Created by Jake on 5/21/2015.
 */
public class WordCloudSketch extends PApplet{

    private InSituDataSet dataSet;

    public WordCloudSketch(InSituDataSet data) {
        this.dataSet = data;
    }

    public void setup(){
        size(700, 400);
        background(255);
    }

    public void draw() {

        Word[] words = new Word[dataSet.getData().size()];

        int max = 0;

        for(int i =0; i < dataSet.getData().size(); i++){
            if(max < (Integer)dataSet.getData().get(i).getField(1)){
                max = dataSet.getData().get(i).getField(1);
            }
        }

        for(int i = 0; i < dataSet.getData().size(); i++){
            String currentWord = dataSet.getData().get(i).getField(0);
            float weight = (float)((int)dataSet.getData().get(i).getField(1))/(float)max;
            words[i] = new Word(currentWord, weight);
            System.out.println(currentWord);
            System.out.println("!!!!!!!!!!!!!!" + i);
        }

        // Pass in the sketch, so WordCram can draw to it.
        WordCram wordcram = new WordCram(this).fromWords(words);

        //Automatically stops drawing when space is used up
        wordcram.drawAll();

        for (Word word :wordcram.getSkippedWords()) {
            if(word.wasSkippedBecause() == WordSkipReason.SHAPE_WAS_TOO_SMALL){
                println(word.word + ": shape was too small");
            }
            else if(word.wasSkippedBecause() == WordSkipReason.WAS_OVER_MAX_NUMBER_OF_WORDS){
                println(word.word + ": was over max # of words");
            }
            else if(word.wasSkippedBecause() == WordSkipReason.NO_SPACE){
                println(word.word + ": no room to place it");
            }
        }

        noLoop();
    }
}
