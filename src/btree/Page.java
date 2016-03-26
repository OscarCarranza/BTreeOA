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
    
    public Page(){

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

    public Page getChildAt(int pos)
    {
            return children[pos];
    }
}
