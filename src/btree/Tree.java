/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package btree;

public class Tree {
    Page root;
    
    public Tree(){
        root = new Page();
    }
    
    public void add(Key k){
        root.addKey(k);
    }
}
