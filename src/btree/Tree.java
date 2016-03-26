/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package btree;

public class Tree {
    
	static int order = 3; 
	Page root; 


	public Tree(int order){
            root = new Page(order, null);
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

	public void split(Page leftChild, int new_key, Page rightChild){
            
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
            for(int i = leftChild.keycount ; i> new_key ; i--){
                leftChild.children[i+1] = leftChild.children[i]; 
            }
            leftChild.children[new_key+1] = parent; 

            for(int i = leftChild.keycount; i> new_key; i--){
                leftChild.keys[i+1] = leftChild.keys[i]; 
            }
            leftChild.keys[new_key] = rightChild.keys[order-1];
            rightChild.keys[order-1 ] = 0; 

            for(int j = 0; j < order - 1; j++){
                rightChild.keys[j + order] = 0; //borrar valores de ambas pgs
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
                    split(page,pos,page.children[pos]);
                    if(newKey > page.keys[pos]){
                        pos++;
                    }
                }

                directInsertion(page.children[pos],newKey);
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
                split(temp,0,currentPage);
                directInsertion(temp, key);
            }
            else
                directInsertion(currentPage,key);
	}

	public void print(Page currentPage){
            for(int i = 0; i < currentPage.keycount; i++){
                System.out.print(currentPage.getValue(i) + " ");
            }

            if(!currentPage.isLeaf){
                for(int i = 0; i <= currentPage.keycount; i++){				  
                    if(currentPage.getChildAt(i) != null){			 
                    System.out.println();	
                    print(currentPage.getChildAt(i));    
                    }
                }
            }
	}
//--------------------------------------------------------------
//this method will delete a key value from the leaf node it is |
//in.  We will use the search method to traverse through the   |
//tree to find the node where the key is in.  We will then     |
//iterated through key[] array until we get to node and will   |
//assign k[i] = k[i+1] overwriting key we want to delete and   |
//keeping blank spots out as well.  Note that this is the most |
//simple case of delete that there is and we will not have time|
//to implement all cases properly.                             |
//--------------------------------------------------------------

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
