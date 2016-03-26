/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package btree;

public class Tree {
    Page root; 

    public Tree(){
        root = new Page(null);
    }

    public Page findPos(Page root, int key){
        int i = 0;
        while(i < root.key_count && key > root.keys[i]){
            i++;
        }
        
        if(i <= root.key_count && key == root.keys[i]){
            return root;
        }

        if(root.leaf){
            return null;
        }

        else{
            return findPos(root.getChild(i),key);
        }
    }
    
    public void split(Page leftChild, int newPos, Page rightChild){
            Page parent = new Page(null);
            parent.leaf = rightChild.leaf;

            parent.key_count = 2;
            for(int i = 0; i < 2; i++){
                    parent.keys[i] = rightChild.keys[i+3];
            }
            if(!rightChild.leaf){
                for(int i = 0; i < 3; i++){
                    parent.children[i] = rightChild.children[i+3]; 
                }
            }

            rightChild.key_count = 2;
            for(int i = leftChild.key_count ; i> i ; i--){
                leftChild.children[i+1] = leftChild.children[i]; //cambio los hijos
            }
            leftChild.children[newPos+1] = parent; 

            for(int i = leftChild.key_count; i > newPos; i--){
                leftChild.keys[i+1] = leftChild.keys[i]; 
            }
            
            leftChild.keys[newPos] = rightChild.keys[2];
            rightChild.keys[2] = 0; 

            for(int j = 0; j < 2; j++){
                rightChild.keys[j+3] = 0; 
            }

            leftChild.key_count ++; 
    }

    public void directInsertion(Page page, int key){
            int num_keys = page.key_count; //i is number of keys in node x

            if(page.leaf){
                while(num_keys >= 1 && key < page.keys[num_keys-1]){
                    page.keys[num_keys] = page.keys[num_keys-1];
                    num_keys--;
                }

                page.keys[num_keys] = key;
                page.key_count ++; 
            }


            else{
                    int pos = 0;
                    while(pos < page.key_count  && key > page.keys[pos]){	
                            pos++;
                    }

                    if(page.children[pos].key_count == 5){
                        split(page,pos,page.children[pos]);

                        if(key > page.keys[pos]){
                                pos++;
                        }
                    }
                    directInsertion(page.children[pos],key);//recurse
            }
    }

    public void insert(int key){

        if(this.root.key_count == 5) {
            Page newPage = new Page(null);
            this.root = newPage;  
            newPage.leaf = false;
            newPage.key_count = 0; 
            newPage.children[0] = this.root;
            split(newPage,0,this.root);
            directInsertion(newPage, key); 
        }
        
        else{
           directInsertion(this.root,key);
        }
    }

    public void print(Page n)
    {
            for(int i = 0; i < n.key_count; i++)
            {
                    System.out.print(n.getValue(i)+" " );//this part prints root node
            }

            if(!n.leaf)//this is called when root is not leaf;
            {

                    for(int j = 0; j <= n.key_count  ; j++)//in this loop we recurse
                    {				  //to print out tree in
                            if(n.getChild(j) != null) //preorder fashion.
                            {			  //going from left most
                            System.out.println();	  //child to right most
                            print(n.getChild(j));     //child.
                            }
                    }
            }
    }

// ------------------------------------------------------------
// this will be method to print out a node                    |
// ------------------------------------------------------------

    public void SearchPrintNode(Tree T,int x)
    {
            Page temp= new Page();

            temp= findPos(T.root,x);

            if (temp==null)
            {

            System.out.println("The Key does not exist in this tree");
            }

            else
            {

            print(temp);
            }


    }

   public void deleteKey(Tree t, int key){

        Page temp = new Page();
        temp = findPos(t.root,key);
        if(temp.leaf && temp.key_count > 2)
        {
                int i = 0;

                while( key > temp.getValue(i))
                {
                        i++;
                }
                for(int j = i; j < 4; j++)
                {
                        temp.keys[j] = temp.getValue(j+1);
                }
                temp.key_count --;

        }
        else
        {
                System.out.println("This node is either not a leaf or has less than order - 1 keys.");
        }
   }
}
