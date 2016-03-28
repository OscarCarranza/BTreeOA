/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package btree;

public class Tree {
    
    static int order = 3; 
    Page root; 
    int height;
    String drawing = "";


    public Tree(){
        root = new Page(order, null);
    }
    
    public void clearDrawing(){
        drawing = "";
    }
    
    public int calculateHeight(Page currentPage, int h){ //h = 1 
        if(currentPage.isLeaf == false){
            System.out.println("NO ES UNA HOJA");
            h++;
            return calculateHeight(currentPage.getChildAt(0),h);
        }
        else{
            return h;
        }
    }

    
    public void insert(int key){
        Page currentPage = this.root;
        if(currentPage.keycount == 2*order - 1){
            Page temp = new Page(order,null);
            this.root = temp;
            temp.isLeaf = false;
            temp.keycount = 0;  
            temp.children[0] = currentPage;
            split(temp,currentPage,0);
            directInsertion(temp, key);
        }
        else
            directInsertion(currentPage,key);
    }

    public void split(Page leftChild, Page rightChild, int new_key){

        Page parent = new Page(order,null);
        parent.isLeaf = rightChild.isLeaf;
        parent.keycount = order - 1;

        for(int i = 0; i < order - 1; i++){
            parent.keys[i] = rightChild.keys[i+order]; 
        }

        if(!rightChild.isLeaf){
            for(int i = 0; i < order; i++){
                parent.children[i] = rightChild.children[i+order]; 
            }
        }

        rightChild.keycount = order - 1;
        for(int i = leftChild.keycount ; i > new_key ; i--){
            leftChild.children[i+1] = leftChild.children[i]; 
        }
        leftChild.children[new_key+1] = parent; 

        for(int i = leftChild.keycount; i > new_key; i--){
            if(i < 4)
                leftChild.keys[i+1] = leftChild.keys[i]; 
        }

        leftChild.keys[new_key] = rightChild.keys[order-1];
        rightChild.keys[order-1] = 0; 

        for(int j = 0; j < order - 1; j++){
            rightChild.keys[j+order] = 0; //borrar valores de ambas pgs
        }

        leftChild.keycount ++;  //increase count of keys in x
    }

    public void directInsertion(Page page, int newKey){
        int pos = page.keycount;

        if(page.isLeaf){
            while(pos >= 1 && newKey < page.keys[pos-1]){
                page.keys[pos] = page.keys[pos-1];
                pos--;
            }

            page.keys[pos] = newKey;
            page.keycount++;      
        }

        else{

            pos = 0;
            while(pos < page.keycount  && newKey > page.keys[pos]){			             		
                    pos++;
            }

            if(page.children[pos].keycount == order*2 - 1){
                split(page,page.children[pos],pos);
                if(newKey > page.keys[pos]){
                    pos++;
                }
            }
            directInsertion(page.children[pos],newKey);
        }
    }

    public void draw(Page currentPage){
        
        for(int i = 0; i < currentPage.keycount; i++){
            drawing += currentPage.toString();
        }

        if(!currentPage.isLeaf){
            for(int i = 0; i <= currentPage.keycount; i++){	
                if(currentPage.getChildAt(i) != null){			
                    draw(currentPage.getChildAt(i));    
                }
            }
        }
    }
    
    public void markUnVisited(Page currentPage){
         for(int i = 0; i < currentPage.keycount; i++){
            currentPage.isVisited = false;
         }
        if(!currentPage.isLeaf){
            for(int i = 0; i <= currentPage.keycount; i++){				  
                if(currentPage.getChildAt(i) != null){			 
                  currentPage.isVisited = false; 
                }
            }
        }
    }
    
   
    public Page findPos(Page currentPage, int newKey){

        int pos = 0;

        while(pos < currentPage.keycount && newKey > currentPage.keys[pos]){
            pos++;
        }

        if(pos <= currentPage.keycount && newKey == currentPage.keys[pos]){
            return currentPage;
        }

        if(currentPage.isLeaf){
            return null ;
        }
        else{
            return findPos(currentPage.getChildAt(pos),newKey);
        }
    }
    
   public void deleteKey(int key){

        Page temp = new Page(order,null);
        temp = findPos(this.root,key);

        if(temp.isLeaf && temp.keycount > order - 1){
            int cont = 0;
            while( key > temp.getValue(cont)){
                cont++;
            }
            for(int i = cont; i < 2*order - 2; i++){
                    temp.keys[i] = temp.getValue(i+1);
            }
            temp.keycount--;
        }
        else{
                System.out.println("This node is either not a leaf or has less than order - 1 keys.");
        }
   }
}
