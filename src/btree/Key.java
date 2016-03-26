/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package btree;

public class Key {
    int value;
    
    public Key(int value){
        this.value = value;
    }
    
    public int getValue(){
        return this.value;
    }
    
    public void setValue(int n){
        this.value = n;
    }
}
