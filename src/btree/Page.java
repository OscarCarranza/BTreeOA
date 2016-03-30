/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package btree;

public class Page {
    
    static int order; 
    int keys[]; 
    int keycount;
    Page children[]; 
    Page parent;
    boolean isLeaf; 
    boolean isVisited;
    
    public Page(){

    }
    
        @Override
    public String toString() {
        
        String s = "";
        
        if(isVisited == false){
            s += "---PAGE NODE ---\n";
            for(int i = 0; i < keycount; i++){
                if(i >= 1 && keys[i] != keys[i-1]){
                    s += "KEY[" + i + "]: ";
                    s+= keys[i];
                    s += "\n";
                }
                if(i == 0){
                    s += "KEY[" + i + "]: ";
                    s+= keys[i];
                    s += "\n";
                }
            }
            isVisited = true;
        }
        
        return s;    
    }
    
    public Page(int order, Page parent){
            this.order = order; 
            this.parent = parent;
            this.keys = new int[2*order - 1];  
            this.children = new Page[2*order]; 
            this.isLeaf = true; 
            this.keycount = 0; 
    }

    public int getValue(int loc){
            return keys[loc];
    }

    public Page getParent() {
        return parent;
    }

    public void setParent(Page parent) {
        this.parent = parent;
    }  

    public Page getChildAt(int pos){
            return children[pos];
    }
    
    
}
