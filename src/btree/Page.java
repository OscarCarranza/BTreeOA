/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package btree;

import static java.lang.StrictMath.max;
import static java.lang.StrictMath.min;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Page {
    //Keys 
    Key k1, k2;
    //Sons
    Page leftSon, rightSon, middleSon, parent;
    
    public Page(){
        
    }
    
    public Page(Key value){
        addKey(value);
    }
    
    public void addKey(Key newKey){
        
        //Blank page
        if(k1 == null)
            k1 = newKey;
        else if(k2 == null)
            k2 = newKey;
        
        else if(k1 != null && k2 != null){
            int median = max(min(k1.getValue(),k2.getValue()), min(max(k1.getValue(),k2.getValue()),newKey.getValue()));
            int max = Math.max(k1.getValue(),Math.max(k2.getValue(),newKey.getValue()));
            int min = Math.min(k1.getValue(),Math.min(k2.getValue(),newKey.getValue()));
            
            ArrayList <Integer> nums = new ArrayList();
            nums.add(max);
            nums.add(median);
            nums.add(min);
            
            for(int i = 0; i < 3; i++){
                if(i == 0)
                    k1.setValue(max);
                else if(i == 1)
                    newKey.setValue(median);
                else if(i == 2)
                    k2.setValue(min);
            }
            SplitPromote(k1,newKey,k2);
            try {
                this.finalize();
            } catch (Throwable ex) {
                Logger.getLogger(Page.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void SplitPromote(Key max, Key median, Key min){
        Page parentPage = new Page(median);
        Page leftPage = new Page(min);
        Page rightPage = new Page(max);
        
        /*if(k2 == null){
            parentPage.leftSon = leftPage;
        }*/
        
        
        
        //delete old page
        try {
            this.finalize();
        } catch (Throwable ex) {
            Logger.getLogger(Page.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
}
