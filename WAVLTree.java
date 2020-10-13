/**
 *
 * WAVLTree
 *
 * An implementation of a WAVL Tree.
 * (Haupler, Sen & Tarajan ‘15)
 *
 *implemented by:
 *Ohad Gazit
 */

public class WAVLTree {

	
private WAVLNode root=null;
private static WAVLNode EXTLeaf;

  /**
   * creating a new WAVlTree with external leaf
   */
  public WAVLTree () {
	EXTLeaf = new WAVLNode(-1);
  }
  
  
  /**
   * public boolean empty()
   *
   * returns true if and only if the tree is empty
   *
   */
  public boolean empty() {
	  if (root == null) {
		  return true;
	  }
	  else {
		  return false; // to be replaced by student code
	  }
  }
/**
 * 	
 * @param k the of the node searched
 * @return the node with the key k if one exist and null otherwise
 */
  private WAVLNode node_Search(int k){
	  WAVLNode cur = root;                                          
      while (cur != null && cur.getKey() != -1) {
      	if (k == cur.getKey()) {
      		return cur;
      	}
      	if (k > cur.getKey()) {
      		cur = cur.getRight();
      	}
      	else {
      		if(k < cur.getKey()) {
      			cur = cur.getLeft();
      		}
      	}
      }
      return null;
	  
  }

 /**
   * public String search(int k)
   *
   * returns the info of an item with key k if it exists in the tree
   * otherwise, returns null
   */
  public String search(int k)
  {
        if(node_Search(k)==null) {
        	return null;
        }
        return node_Search(k).getValue();
  }


  /**
   * public int insert(int k, String i)
   *
   * inserts an item with key k and info i to the WAVL tree.
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were necessary.
   * returns -1 if an item with key k already exists in the tree.
   */
   public int insert(int k, String i) {
	   if (root == null) {                            /*insert node to emtpy tree*/
		   root = new WAVLNode(k,i,0,1);
		   root.right = EXTLeaf;
		   /*root.right.parent = root;*/
		   root.left = EXTLeaf;
		   /*root.left.parent = root;*/
		   return 0;
	   }
	   else {
		   WAVLNode cur = root;
		   boolean inserted = false;
		   while (inserted == false){
			   if (cur.getKey() == k) {                           /* return -1 if k already in tree*/
				   return -1;
			   }
			   if (k > cur.getKey()) {
				   if (cur.getRight().rank == -1) {
					   cur.addLeaf(k,i,"right");
					   inserted = true;
				   }
				   else {
				   cur = cur.getRight();
				   }
			   }
			   else {
				   if(k<cur.getKey()) {
					   if (cur.getLeft().rank==-1) {
						   cur.addLeaf(k, i, "left");
						   inserted = true;
					   }
					   else {
					   cur = cur.getLeft();
					   }
				   }
			   }   
		   }
		   return insertRebalance (cur);    /*cur now is the parent of the inserted node*/
	   }
   }	
   	     /**making the rebalance procces
   	      * updating subtreesize of all the nodes in the tree
   	      * @return the number of rebalance operations
   	      * @param cur the parent of the inserted node*/
		  private int insertRebalance (WAVLNode cur) {		   		   
		   if (cur.rank == 0){                                      /* rank problem created - parent is a leaf*/
			   int rankDifference;
			   int numOfOperations =0;
			   boolean rotated = false;                        /*checking if rotated */
			   while (cur != null) {
				   cur.subTreeSize ++;
				   if (rotated == false) {                 /*if false: checking if rotate needed, if true: keep climbing to update subrtreesizes*/
				   /*boolean rotated = false;*/                        /*checking if rotated */
				   rankDifference = cur.rankDifference();
				   if (Math.abs(rankDifference) == 1 && (cur.rank == cur.left.rank || cur.rank == cur.right.rank) ) {             /*case 1*/
					   cur.rank++;
					   numOfOperations ++;
				   }
				   else {
					   int sonRankDifference;
					   if(rankDifference == -2) {                           /*case 2 or case 3*/ 
						   sonRankDifference = cur.left.rankDifference();
						   if (sonRankDifference == -1) {                   /*case 2 rotate right*/
							   cur = cur.rotate("right");                   /*cur = new root of subtree*/
							   cur.right.rank--;
							   rotated = true;
							   numOfOperations +=2;
						   }
						   if (sonRankDifference == 1) {		           /* case 3 double rotate right*/
							   cur = cur.doublerotate("right");            /*cur = new root of subtree*/
							   cur.rank++;
							   cur.left.rank--;
							   cur.right.rank--;
							   rotated = true;
							   numOfOperations +=5;       
						   }
					   }
					   if(rankDifference == 2) {                           /*case 2 or case 3*/
						   sonRankDifference = cur.right.rankDifference();
						   if (sonRankDifference == 1) {                  /*case 2 rotate left*/
						   	   cur = cur.rotate("left");                    /*cur = new root of subtree*/
						   	   cur.left.rank--;
						   	   rotated = true;
						   	   numOfOperations +=2; 
						   }
						   if(sonRankDifference == -1) {                  /*case 3 double rotate left*/
							   cur = cur.doublerotate("left");             /*cur = new root of subtree*/
							   cur.rank++;
							   cur.left.rank--;
							   cur.right.rank--;
							   rotated = true;
							   numOfOperations +=5;
						   }
					   }
					   if (rotated == true) {
						   if (cur.left == root || cur.right == root) {    /*updating root if the parent was the tree root*/
							   root = cur;
						   }						   
					   }
						   
				   }
				   }
				   cur = cur.parent;   
			   }
			   return numOfOperations;
		   }
		   else {
			   while(cur!=null) {
			   cur.subTreeSize++;
			   cur = cur.parent;
			   }
			   return 0; /*parent of the inserted node is not a leaf,  no problem was created*/
		   }
		  }

  /**
   * delete wavltree node.
   * called after the original to_del node and its succesor have been switched
   * @param to_del
   * @param isFollow
   */
   private void reg_delete_switched(WAVLNode to_del, boolean isFollow) {
	   if (isFollow == true) {    /*to_del amd replacing are parent and son*/
		   if(!(to_del.left.isInnerNode() || to_del.right.isInnerNode())){   
				  to_del.parent.right=EXTLeaf;                                /*to_del is a leaf*/
				  this.delReb(to_del.parent, "leaf");
		   }
		   else if(!(to_del.left.isInnerNode())) {    /*to del have right son*/
			   to_del.parent.right=to_del.right;
			   to_del.right.parent = to_del.parent;
			   this.delReb(to_del.parent, "unary");
		   }
		   
	   }
	   else {                         
		   if(!(to_del.left.isInnerNode() || to_del.right.isInnerNode())){             /*to_del is a leaf*/    
			   to_del.parent.left = EXTLeaf;
			   this.delReb(to_del.parent, "leaf");
		   }
		   else if(!(to_del.left.isInnerNode())) {    /*to_del have right son */
			   to_del.parent.left=to_del.right;
			   to_del.right.parent = to_del.parent;
			   this.delReb(to_del.parent, "unary");
		   }
		   
	   }
   }
   /**
    * changing between do_del and its succesor
    * @param to_del
    * @param replacing = successor
    * @return true if and only if the were parent and son
    */
   private boolean change (WAVLNode to_del, WAVLNode replacing) {
	   boolean isFollow = false;
	   if (to_del.right == replacing) {
		   isFollow = true;
	   }
	   WAVLNode left = replacing.left;
	   WAVLNode right = replacing.right;
	   WAVLNode parent = replacing.parent;
	   int rank = replacing.rank;
	   int size = replacing.subTreeSize;
	   replacing.left = to_del.left;
	   replacing.left.parent = replacing;
	   replacing.right = to_del.right;
	   replacing.subTreeSize = to_del.subTreeSize;
	   replacing.rank = to_del.rank;
	   if(to_del.parent != null) {
		   replacing.parent = to_del.parent;
		   if(to_del.parent.right.getKey() == to_del.getKey()) {
			   to_del.parent.right = replacing;
		   }
		   else {
			   to_del.parent.left = replacing;
		   }
	   }
	   else {
		   root = replacing;
		   replacing.parent = null;
	   }
	   to_del.left = left;
	   to_del.right = right;
	   to_del.subTreeSize= size;
	   to_del.rank = rank;
	   if (isFollow == true) {
		   to_del.parent = replacing;
		   replacing.right = to_del;
	   }
	   else {
		   to_del.parent = parent;
		   to_del.parent.left = to_del;
		   replacing.right.parent = replacing;
	   }
	   return isFollow;
   }
   
 /**private int delReb(WAVLNode cur, String status)
  *    
  * rebalances the tree and updutes to subTreesizes after deletion  
  * @param cur the parent the deleted node
  * @param status the status of the deleted node (leaf or unary)
  * @return the number of rebalance operations done
  */
    int delReb(WAVLNode cur, String status) {
	   boolean finished = false;
	   boolean rotated = false;
	   String stp;
	   int numOfOp= 0 ;
	   while (cur != null) {
		   cur.subTreeSize--;
		   if (finished == false) {
			   stp = cur.caseFirstFind(status);
			   switch (stp) {
			   case ("finished"):
				   finished = true;
				   break;
			   case ("demote"):
				   cur.rank--;
		   	   	   numOfOp++;
		   	   	   break;
			   case ("doubleDemoteright"):
				   cur.rank--;
		   	   	   cur.right.rank--;
		   	   	   numOfOp +=2;
		   	   	   break;
			   case ("doubleDemoteleft"):
				   cur.rank--;
		   	   	   cur.left.rank--;
		   	   	   numOfOp+= 2 ;
		   	   	   break;
			   case ("rotateRight"):
				   cur = cur.rotate("right");
			   	   cur.rank++;
			   	   if (cur.right.left.isInnerNode() == true || cur.right.right.isInnerNode() == true || cur.right.rank != 2) {
			   		cur.right.rank--;
			   	   }
			   	   else {
			   		cur.right.rank-=2; 
			   	   }
		       	   numOfOp+=3;
		       	   finished = true;
		       	   rotated = true;
		       	   break;
			   case ("rotateLeft"):
				   cur = cur.rotate("left");
			   	   cur.rank++;
		   	       if (cur.left.left.isInnerNode() == true || cur.left.right.isInnerNode() == true || cur.left.rank != 2) {
		   	    	cur.left.rank--;
		   	       }
		   	       else {
		   	    	cur.left.rank-=2; 
		   	       }
		       	   numOfOp+=3;
		       	   finished = true;
		       	   rotated = true;
		       	   break;
			   case("doubleRotateRight"):
				   cur = cur.doublerotate("right");
			   	   cur.rank+=2;
			   	   if(cur.rank-cur.right.rank==0) {
			   		cur.right.rank-=2;			   	   }
			   	   else {
				   	cur.right.rank--;
			   	   }
			   	   cur.left.rank--;
		       	   numOfOp+=4;
		       	   finished = true;
		       	   rotated = true;
		       	   break;
			   case("doubleRotateLeft"):
				   cur = cur.doublerotate("left");
			   	   cur.rank+=2;
			   	   if(cur.rank-cur.left.rank==0) {
			   		cur.left.rank-=2;   
			   	   }
			   	   else {
				   	cur.left.rank--;
			   	   }
			   	   cur.right.rank--;
		       	   numOfOp+=4;
		       	   finished = true;
		       	   rotated = true;
		       	   break;
			   }
			   if (rotated == true) {
				   if (cur.left == root || cur.right == root) {    /*updating root if the parent was the tree root*/
					   root = cur;
				   }						   
			   }
		   }
		   cur = cur.parent;
		   status = "rebalance";
	   }
	   return numOfOp;
   }
   
   
   /**
   * public int delete(int k)
   *
   * deletes an item with key k from the binary tree, if it is there;
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were needed.
   * returns -1 if an item with key k was not found in the tree.
   */
   public int delete(int k)
   {
	   WAVLNode to_del=node_Search(k);
	   if(!this.empty()) {
		   if(this.size()==1) {
			   root=null;
		   }
		   else if(to_del!=null) {
				  String side=null;
				  if(to_del.parent!=null) {
				   	if(to_del.parent.key>to_del.key) {            
					   side="left";                               
				   	}
				   	else {
				   		side="right";
				   	}
				   }
				if(!(to_del.left.isInnerNode() || to_del.right.isInnerNode())){
					switch(side) {
					case("right"):
						to_del.parent.right=EXTLeaf;
						break;
					case("left"):
						to_del.parent.left=EXTLeaf;
						break;
					}
					return this.delReb(to_del.parent, "leaf");
				}  
				else if(!(to_del.left.isInnerNode())) {
					to_del.right.parent=to_del.parent;
					if(to_del.parent!=null) {
						switch(side) {
						case("right"):
							to_del.parent.right=to_del.right;
							break;
						case("left"):
							to_del.parent.left=to_del.right;
							break;
						}
						to_del.right.parent = to_del.parent;
					}
					else {
						to_del.right.parent=null;
						root=to_del.right;
					}
					return this.delReb(to_del.parent, "unary");
					}
					else if(!(to_del.right.isInnerNode())) {
					to_del.right.parent=to_del.parent;
					if(to_del.parent!=null) {
						switch(side) {
						case("right"):
							to_del.parent.right=to_del.left;
							break;
						case("left"):
							to_del.parent.left=to_del.left;
							break;
						}
						to_del.left.parent = to_del.parent;
					}
					else {
						to_del.left.parent=null;
						root=to_del.left;
					}
					return this.delReb(to_del.parent, "unary");
					}
				else {
					WAVLNode replacing=to_del.successor();
					boolean isFollow = this.change(to_del, replacing);   /*checikng if succesor and to_del are parent and son*/
					this.reg_delete_switched(to_del,isFollow);
					}
		   		}
			}
	   return 0;
   }

   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty
    */
   public String min()
   {
           if(this.empty()) {
        	   return null;
           }
           WAVLNode min_node=this.root;
           while(min_node.left!=null && min_node.left.rank!=-1) {
        	   min_node=min_node.left;
           }
           return min_node.value;
   }

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty
    */
   public String max()
   {
	   if(this.empty()) {
    	   return null;
       }
       WAVLNode max_node=this.root;
       while(max_node.right!=null && max_node.right.rank!=-1) {
    	   max_node=max_node.right;
       }
       return max_node.value;
   }

   
   static private int in_order_ind=0;//the index in the in order passes in keysToArray() and infoToArray()
   /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   */
   public int[] keysToArray()
   {
        if(this.empty()) {
        	return new int[0];
        }
        int[] arr=new int[this.size()];
        in_order_ind=0;
        this.root.ToArrayHelper(arr,null,true);
        return arr;
        
   }

   /**
   * public String[] infoToArray()
   *
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   */
   public String[] infoToArray()
   {
	   if(this.empty()) {
       	return new String[0];
       }
       String[] arr=new String[this.size()];
       in_order_ind=0;
       this.root.ToArrayHelper(null,arr,false);
       return arr;
   }

   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    *
    */
   public int size()
   {
           if(this.empty()) {
        	   return 0;
           }
           return this.root.subTreeSize;
   }
   
   
     /**
    * public WAVLNode getRoot()
    *
    * Returns the root WAVL node, or null if the tree is empty
    *
    */
   public WAVLNode getRoot()
   {
           if(this.empty()) {
        	   return null;
           }
           return this.root;
   }
     /**
    * public int select(int i)
    *
    * Returns the value of the i'th smallest key (return -1 if tree is empty)
    * Example 1: select(1) returns the value of the node with minimal key 
        * Example 2: select(size()) returns the value of the node with maximal key 
        * Example 3: select(2) returns the value 2nd smallest minimal node, i.e the value of the node minimal node's successor  
    *
    */   
   public String select(int i)
   {
           if(i<=0 || this.empty() || i>this.size()) {
        	   return null;
           }
           WAVLNode curr_node=this.root;
           while(curr_node.left.getSubtreeSize()!=i-1) {
        	   if(curr_node.left.getSubtreeSize()>i-1) {
        		   curr_node=curr_node.left;
        	   }
        	   else {
        		   i-=(curr_node.left.getSubtreeSize()+1);
        		   curr_node=curr_node.right;
        	   }
           }
           return curr_node.value;
   }

   /**
   * public class WAVLNode
   */
  public class WAVLNode{
	  
	  			private String value ;
	  			private int key;
	  			private WAVLNode right;
	  			private WAVLNode left;
	  			private WAVLNode parent;
	  			private int subTreeSize;
	  			private int  rank;
	  			
	  			
	  			/**
	  			 * constructor for the external leaf
	  			 * @param rank
	  			 */
	  			private WAVLNode (int rank) {
	  				this.rank = rank;
	  			}
	  			/**
	  			 * creating a new wavlnode
	  			 * @param key
	  			 * @param value
	  			 * @param rank
	  			 * @param subTreeSize
	  			 */
	  			private WAVLNode(int key, String value , int rank, int subTreeSize) {
	  				this.key = key;
	  				this.value = value;
	  				this.rank = rank;
	  				this.subTreeSize = subTreeSize;
	  				
	  				
	  			}
	  			/**
	  			 * side have to be "left" or "right"
	  			 * creating a new wavlnode leaf 
	  			 * @param k
	  			 * @param value
	  			 * @param side
	  			 */
	  		   private void addLeaf(int k, String value, String side) {
	  			   if (side.equals("right") == true) {
	  				   right = new WAVLNode(k,value,0,1);
	  				   right.parent = this;
	  				   /*subTreeSize ++;*/
	  				   right.right = EXTLeaf;
	  				   right.left = EXTLeaf;;
	  			   }
	  			   else {
	  				   if (side.equals("left") == true){
	  				   left = new WAVLNode(k,value,0,1);
	  				   left.parent = this;
	  				   /*subTreeSize ++;*/
	  				   left.right = EXTLeaf;
	  				   left.left = EXTLeaf;
	  				   }
	  			   }

	  			   
	  		   }
	  		   /**
	  		    * 
	  		    * @return the rank differences of the node
	  		    */
	  		 private int rankDifference() {
	  			   return (rank-left.rank) -(rank-right.rank);
	  		   }
	  		   /**
	  		    * this. is  the root of the subtree
	  		    * @param side = right if rotate to the right
	  		    * @param delete = true if rotate after deleting
	  		    * @return
	  		    */
	  		private WAVLNode rotate(String side) {  
	  			   WAVLNode newParent;
	  			   if (side.equals("right")) {       /*rotating to the right*/
	  				   this.rebalanceSizeUpdate("right");
	  				   newParent = left;
	  				   left.right.parent = this;
	  				   left = left.right;
	  				   newParent.right = this;
	  				   if (parent != null) {
	  					   if (parent.left.getKey() == this.getKey()) {
	  						 parent.left = newParent;
	  					   }
	  					   else {
	  						   parent.right = newParent;
	  					   } 
	  					   newParent.parent = parent;
	  				   }
	  				   else {
	  				   newParent.parent = null;
	  				   }
	  				   
	  			   }
	  			   else {      /*rotating to the left*/
	  				   this.rebalanceSizeUpdate("left");
	  				   newParent = right;
	  				   right.left.parent = this;
	  				   right = right.left;
	  				   newParent.left= this;
	  				   if (parent != null) {
	  					   if (parent.left.getKey() == this.getKey()) {
	  						 parent.left = newParent;
	  					   }
	  					   else {
	  						   parent.right = newParent;
	  					   } 
	  					   newParent.parent = parent;
	  				   }
	  				   else {
	  				   newParent.parent = null;
	  				   }
	  			   }   
	  			   parent = newParent;
	  			   return newParent;
	  			   
	  		   }
	  		   /**
	  		    * make a double rotation to given side
	  		    * return the new subtree parent
	  		    * @param side ="left" or "right"
	  		    * @return
	  		    */
	  		private WAVLNode doublerotate(String side) {
	  			   if (side.equals("right")){
	  				   left.rotate("left");
	  				   this.rotate("right");
	  			   }
	  			   else {
	  				   right.rotate("right");
	  				   this.rotate("left");
	  			   }
	  			   return parent;    /*return the new root of the subtree*/
	  		   }
	  		   /**
	  		    * getting a side of balancing
	  		    * must called before the rotation
	  		    * updating the sizes of the nodes in the subtree
	  		    * @param side the side of the rotation
	  		    */
	  		/**
	  		 * update the sizes of the nodes that got rotated
	  		 * @param side = "right" or "left"
	  		 */
	  		private void rebalanceSizeUpdate (String side) {
	  			   if (side.equals("right")) {
	  				   this.subTreeSize = right.subTreeSize+left.right.subTreeSize +1;
	  				   left.subTreeSize = left.left.subTreeSize + this.subTreeSize +1;
	  			   }
	  			   if (side.equals("left")){
	  				   this.subTreeSize = left.subTreeSize+right.left.subTreeSize +1;
	  				   right.subTreeSize= right.right.subTreeSize +this.subTreeSize +1;
	  			   }
	  		   }
	  		/**
	  		 * private void ToArrayHelper(int[] int_arr,String str_arr[],boolean is_int)
	  		 * 
	  		 * does recursive in order traversal of the subtree in which for each node inserting either the key to the int array 
	  		 * or info to the string array in in_order_ind position and the increments the in in_order_ind by 1.
	  		 * @param int_arr  if is_int=true elements with an index between in_order_ind and in_order_ind+subTreeSize-1
	  		 *   will become the keys of sub tree in sorted order otherwise the array will remain unchanged
	  		 * @param str_arr if is_int=false elements with an index between in_order_ind and in_order_ind+subTreeSize-1
	  		 *   will become the info of sub tree in sorted key order otherwise the array will remain unchanged
	  		 * @param is_int if true the method will change the int array and if false
	  		 * the method will change the string array 
	  		 */
	  		private void ToArrayHelper(int[] int_arr,String str_arr[],boolean is_int) {//if is_int==true the method will change the int array otherwise the method will change the String array.  
	  			if(this!=null && this.rank!=-1) {
	  				this.left.ToArrayHelper(int_arr,str_arr,is_int);
	  				if(is_int) {
	  					int_arr[in_order_ind]=this.key;
	  				}
	  				else {
	  					str_arr[in_order_ind]=this.value;
	  				}
	  				in_order_ind++;
	  				this.right.ToArrayHelper(int_arr,str_arr,is_int);
	  			}
	  	   }
	  		/**
	  		 * 
	  		 * @return the succesor of the node
	  		 */
	  		private WAVLNode successor () {
            	WAVLNode cur = this;
            	if (this.right.rank == -1 ) {
            		while (cur.parent != null && cur.parent.right.getKey() == cur.getKey()) {
            			cur = cur.parent;
            		}
            		if (cur.parent == null) {
            			return null;
            		}
            		return cur.parent;	
            	}
            	else {
            		cur = cur.right;
            		while (cur.left.rank != -1) {
            			cur = cur.left;
            		}
            		return cur;		
            	}
            }
	  		/**getting the node needed to be checked for rebalancing
	  		 * find the rebalance case after delete
	  		 * @param status = "leaf" or "unary"
	  		 * @return rebalance step needed
	  		 */
	  		private String caseFirstFind(String status) {
	  			int leftDif = this.rank-this.left.rank;
	  			int rightDif = this.rank - this.right.rank;
	  			if (status.equals("leaf")) {
	  				if (leftDif == 2 && rightDif == 2) {   /*2nd leaf case*/
	  					return "demote";   
	  				}
	  				if (this.rank == 1) {                  /*1st leaf case*/
	  					return "finished";
	  				}
	  				return this.caseFind();                 /*3rd leaf case */
	  			}
	  			if (status.equals("unary")){
	  				if (leftDif == 2 && rightDif == 2) {
	  					return "finished";
	  				}
	  				if ((leftDif == 2 && rightDif == 1) || (leftDif == 1 && rightDif == 2 )) {
	  					return "finished";
	  				}
	  				return this.caseFind();
	  			}
	  			if (leftDif == 3 || rightDif == 3)
	  				return this.caseFind();  
	  			else 
	  				return "";
	  		}
	  		
	
	  			/**
	  			 * getting the node needed to be checked por rebalancing;
	  			 * 
	  			 * 
	  			 * 
	  			 * @return  next step of rebalancing demote , double demote+side, rotate, double rotate
	  			 */
	  		private String caseFind () {
	  			int leftDif = this.rank-this.left.rank;
	  			int rightDif = this.rank - this.right.rank;
	  			if (Math.abs(leftDif -rightDif) == 1 ) {    /*first case of rebalancing*/
	  				if (leftDif == 3 || rightDif == 3)
	  					return "demote";
	  			}
	  			if (leftDif == 2 && rightDif == 2 )
	  				return "finished";
	  			String side;
	  			if (leftDif > rightDif ) {   /*checking balancing sides*/
	  				side = "right";
	  			}
	  			else {
	  				side = "left";
	  			}
	  			WAVLNode son;   /*the lower node needed to be checked in rebalancing cases */
	  			if (side.equals("left")) {
	  				son = this.left;
	  			}
	  			else {
	  				son = this.right;
	  			}
		
	  			int newLD = son.rank-son.left.rank;
	  			int newRD = son.rank-son.right.rank;
	  			if (newLD == 2 && newRD == 2) {   /*2nd case of rebalancing*/
	  				return "doubleDemote"+side;
	  			}
	  			switch (side) {
	  			case ("right"):
	  				if (newRD == 2) {
	  					return "doubleRotateLeft";  /*4rd case of rebalancing*/
	  				}
	  			return "rotateLeft";           /*3rd case of rebalancing*/
	  			case ("left"):
	  				if (newLD == 2) {
	  					return "doubleRotateRight";        /*4rd case of rebalancing*/
	  				}
	  			return "rotateRight";           /*3rd case of rebalancing*/
	  			}

	  			return "";
	  		}            
	  			
	  		    /**
	  		     * 
	  		     * @return key of the node. -1 if external leaf
	  		     */
                public int getKey()  /*return key of internal node*/
                {
                	if (rank == -1) {
                		return -1;
                	}
                	else {
                		return key;
                	}
                }
                /**
                 * 
                 * @return value of node. null if external leaf
                 */
                public String getValue() /*return value of internal node*/
                {
                	if (rank == -1) {
                		return null;
                	}
                	else {
                		return value;
                	}

                }
                /**
                 * 
                 * @return left children of the node. null if not existe
                 */
                public WAVLNode getLeft() /*return left son*/
                {
                	if (left == null) {
                		return null;
                	}
                	else {
                		return left;
                	}
                }
                /**
                 * 
                 * @return right children of the node. null if not existe
                 */
                public WAVLNode getRight() /*return right son*/
                {
                	if (right == null) {
                		return null;
                	}
                	else {
                		return right;
                	}
                }
                /**
                 * 
                 * @return true if innder node. false if not
                 */
                public boolean isInnerNode()
                {
                	if (rank == -1) {
                		return false;
                	}
                	else {
                        return true; // to be replaced by student code
                	}
                }
                	
                /**
                 * 
                 * @return subtree size of the node. 0 if external leaf
                 */
                public int getSubtreeSize()
                {
                	if(rank==-1) {
                		return 0;
                	}
                    return subTreeSize;
                }
                
  }
  
  
  

}
