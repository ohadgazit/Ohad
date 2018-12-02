/**
 *
 * WAVLTree
 *
 * An implementation of a WAVL Tree.
 * (Haupler, Sen & Tarajan ‘15)
 *
 */

public class WAVLTree {

	
WAVLNode root;
static WAVLNode EXTLeaf;

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
   * public String search(int k)
   *
   * returns the info of an item with key k if it exists in the tree
   * otherwise, returns null
   */
  public String search(int k)
  {
        WAVLNode cur = root;                                          
        while (cur != null && cur.getKey() != -1) {
        	if (k == cur.getKey()) {
        		return cur.getValue();
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
				   if (cur.getRight().getKey() == -1) {
					   cur.addLeaf(k,i,"right");
					   inserted = true;
				   }
				   else {
				   cur = cur.getRight();
				   }
			   }
			   else {
				   if(k<cur.getKey()) {
					   if (cur.getLeft().getKey() == -1) {
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
   	     /**getting the parent of the inserted node
   	      * making the rebalance procces
   	      * updating subtreesize of all the tree
   	      * return the number of rebalance operations            */
		  public int insertRebalance (WAVLNode cur) {		   		   
		   if (cur.rank == 0){                                      /* rank problem created - parent is a leaf*/
			   int rankDifference;
			   int numOfOperations =0;
			   boolean rotated = false;                        /*checking if rotated */
			   while (cur != null) {
				   cur.subTreeSize ++;
				   if (rotated == false) {                 /*if false: checking if rotate needed, if true: keep climbing to update subrtreesizes*/
				   /*boolean rotated = false;*/                        /*checking if rotated */
				   rankDifference = cur.rankDifference();
				   if (Math.abs(rankDifference) == 1) {             /*case 1*/
					   cur.rank++;
					   numOfOperations ++;
				   }
				   else {
					   int sonRankDifference;
					   if(rankDifference == -2) {                           /*case 2 or case 3*/ 
						   sonRankDifference = cur.left.rankDifference();
						   if (sonRankDifference == -1) {                   /*case 2 rotate right*/
							   cur = cur.rotate("right");                   /*cur = new root of subtree*/
							   rotated = true;
							   numOfOperations +=1;
						   }
						   if (sonRankDifference == 1) {		           /* case 3 double rotate right*/
							   cur = cur.doublerotate("right");            /*cur = new root of subtree   ---- need to check*/
							   rotated = true;
							   numOfOperations +=3;       
						   }
					   }
					   if(rankDifference == 2) {                           /*case 2 or case 3*/
						   sonRankDifference = cur.right.rankDifference();
						   if (sonRankDifference == 1) {                  /*case 2 rotate left*/
						   	   cur = cur.rotate("left");                    /*cur = new root of subtree*/
						   	   rotated = true;
						   	   numOfOperations +=1; 
						   }
						   if(sonRankDifference == -1) {                  /*case 3 double rotate left*/
							   cur = cur.doublerotate("left");             /*cur = new root of subtree  --- need to check*/
							   rotated = true;
							   numOfOperations +=3;
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
			   return 0; /*parent of the inserted node is not a leaf,  no problem was created*/
		   }
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
           return 42;   // to be replaced by student code
   }

   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty
    */
   public String min()
   {
           return "42"; // to be replaced by student code
   }

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty
    */
   public String max()
   {
           return "42"; // to be replaced by student code
   }

   /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   */
   public int[] keysToArray()
   {
        int[] arr = new int[42]; // to be replaced by student code
        return arr;              // to be replaced by student code
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
        String[] arr = new String[42]; // to be replaced by student code
        return arr;                    // to be replaced by student code
   }

   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    *
    */
   public int size()
   {
           return 42; // to be replaced by student code
   }
   
   
     /**
    * public WAVLNode getRoot()
    *
    * Returns the root WAVL node, or null if the tree is empty
    *
    */
   public WAVLNode getRoot()
   {
           return null;
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
           return null; 
   }

   /**
   * public class WAVLNode
   */
  public class WAVLNode{
	  
	  			String value ;
	  			int key;
	  			WAVLNode right;
	  			WAVLNode left;
	  			WAVLNode parent;
	  			int subTreeSize;
	  			int  rank;
	  			
	  			public WAVLNode (int rank) {
	  				this.rank = rank;
	  			}

	  			public WAVLNode(int key, String value , int rank, int subTreeSize) {
	  				this.key = key;
	  				this.value = value;
	  				this.rank = rank;
	  				this.subTreeSize = subTreeSize;
	  				
	  				
	  			}
	  		   public void addLeaf(int k, String value, String side) {
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
	  		   public int rankDifference() {
	  			   return (rank-left.rank) -(rank-right.rank);
	  		   }
	  		   
	  		   public WAVLNode rotate(String side) {
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
	  			   rank--;
	  			   return newParent;
	  			   
	  		   }
	  		   
	  		   public WAVLNode doublerotate(String side) {
	  			   if (side.equals("right")){
	  				   left.rotate("left");
	  				   this.rotate("right");
	  			   }
	  			   else {
	  				   right.rotate("right");
	  				   this.rotate("left");
	  			   }
	  			   parent.rank++;
	  			   return parent;    /*return the new root of the subtree*/
	  		   }
	  		   /**
	  		    * getting a side of balancing
	  		    * must called before the rotation
	  		    * updating the sizes of the nodes in the subtree
	  		    * @param cur
	  		    * @param side
	  		    */
	  		   public void rebalanceSizeUpdate (String side) {
	  			   if (side.equals("right")) {
	  				   this.subTreeSize = right.subTreeSize+left.right.subTreeSize +1;
	  				   left.subTreeSize = left.left.subTreeSize + this.subTreeSize +1;
	  			   }
	  			   if (side.equals("left")){
	  				   this.subTreeSize = left.subTreeSize+right.left.subTreeSize +1;
	  				   right.subTreeSize= right.right.subTreeSize +this.subTreeSize +1;
	  			   }
	  		   }
	  		   
	  
                public int getKey()  /*return key of internal node*/
                {
                	if (rank == -1) {
                		return -1;
                	}
                	else {
                		return key;
                	}
                }
                public String getValue() /*return value of internal node*/
                {
                	if (rank == -1) {
                		return null;
                	}
                	else {
                		return value;
                	}

                }
                public WAVLNode getLeft() /*return left son*/
                {
                	if (left == null) {
                		return null;
                	}
                	else {
                		return left;
                	}
                }
                public WAVLNode getRight() /*return right son*/
                {
                	if (right == null) {
                		return null;
                	}
                	else {
                		return right;
                	}
                }
                
                public boolean isInnerNode()
                {
                	if (rank == -1) {
                		return false;
                	}
                	else {
                        return true; // to be replaced by student code
                	}
                }
                	

                public int getSubtreeSize()
                {
                        return subTreeSize;
                }
                
                
                public int recSubTreeSize() {            /*additional recursion function to find subtreesize (dont know if needed)*/
                	if (rank == -1) {
                		return 0;           		
                	}
                	else {
                		return 1+left.recSubTreeSize()+right.recSubTreeSize();
                	}
                }
  }
  
  
  
  private static final boolean DISPLAY_SUBTREESIZE = true;

		  public void display() {
		  		display(!DISPLAY_SUBTREESIZE);
		  	}

		  	public void display(boolean displayRank) {
		  		final int height = root.rank*2+2, width = (root.subTreeSize + 1) * 12;

		  		int len = width * height * 2 + 2;
		  		StringBuilder sb = new StringBuilder(len);
		  		for (int i = 1; i <= len; i++)
		  			sb.append(i < len - 2 && i % width == 0 ? "\n" : ' ');

		  		displayR(sb, width / 2, 1, width / 4, width, root, " ", displayRank);
		  		System.out.println(sb);
		  	}

		  	private void displayR(StringBuilder sb, int c, int r, int d, int w, WAVLNode n, String edge, boolean displayRank) {
		  		if (n != null) {
		  			displayR(sb, c - d, r + 2, d / 2, w, n.left, " /", displayRank);

		  			String s = (displayRank) ? String.valueOf(n.key) + "[" + n.subTreeSize + "]" : String.valueOf(n.key) + "[" + n.rank + "]";
		  			int idx1 = r * w + c - (s.length() + 1) / 2;
		  			int idx2 = idx1 + s.length();
		  			int idx3 = idx1 - w;
		  			if (idx2 < sb.length())
		  				sb.replace(idx1, idx2, s).replace(idx3, idx3 + 2, edge);

		  			displayR(sb, c + d, r + 2, d / 2, w, n.right, "\\ ", displayRank);
		  		}

}
}
