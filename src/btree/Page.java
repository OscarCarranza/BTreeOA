/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package btree;

public class Page {
    
    int key_count; //numero de llaves/pag
    int keys[];  
    Page children[]; 
    boolean leaf; 
    Page parent; 

    public Page(){
    }

    public Page(Page parent){
        this.parent = parent;
        keys = new int[5]; 
        children = new Page[6]; 
        leaf = true; 
        key_count = 0;
    }

    public int getValue(int index){
            return keys[index];
    }

    public Page getChild(int index){
            return children[index];
    }
    
}
