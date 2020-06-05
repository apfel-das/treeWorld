package trees;
import treeUtillities.Node;

/**
 * A dynamic implementation of a Binary Search Tree(BST), where each Node holds information concerning it's right,left, parent linkage.
 * @author dasApfel
 *
 */


public class BinarySearchTreeDynamic implements BinarySearchTree 
{
	// the BST's root
	private Node root;
	private int nodeCount;
	
	
	//stats
	private float insTime;
	private float searchTime;
	private int insComparisons;
	private int searchComparisons;
	private int totalComparisons; 
	
	//class constructor, similar to BSTstatic.
	public BinarySearchTreeDynamic() 
	{
		root = null;
	}
	
	public Node getRoot() 
	{
		return root;
	}
	
	
	public float getInsTime() {
		return insTime;
	}


	public float getSearchTime() {
		return searchTime;
	}


	public float getInsComparisons() {
		return insComparisons;
	}


	public float getSearchComparisons() {
		return searchComparisons;
	}


	public void setInsTime(float insTime) {
		this.insTime = insTime;
	}


	public void setSearchTime(float searchTime) {
		this.searchTime = searchTime;
	}


	public void setInsComparisons(int insComparisons) {
		this.insComparisons = insComparisons;
	}


	public void setSearchComparisons(int searchComparisons) {
		this.searchComparisons = searchComparisons;
	}

	
	
	/**
	 * An interface method for insertion of an Integer key in the BST.
	 * @param key
	 */
	public void insert(int key) 
	{
		nodeCount++;
		root = insertRec(root, key);
	}
	
	/**
	 *  A recursive function to insert a new key in BST 
	 * @param root the root Node.
	 * @param key the key to be inserted.
	 * @return
	 */
    private Node insertRec(Node root, int key) 
    { 
  
    	insComparisons++;
        /* If the tree is empty, return a new node */
        if (root == null) 
        { 
            root = new Node(key); 
            return root; 
        } 
        
       
        //traverse left.
        if (key < root.getKey()) 
            root.setLeft(insertRec(root.getLeft(), key)); 
        //traverse right.
        else if (key > root.getKey()) 
            root.setRight(insertRec(root.getRight(), key)); 
  
        /* return the (unchanged) node pointer */
        return root; 
    } 
	
    /**
     * Search the existance of a given key.
     * @param curr
     * @param key
     * @return
     */
    
    public int search(Object c, int key) 
    {
    	searchComparisons++;
    	
    	Node curr = (Node) c;
    	
    	//empty case.
    	
    	 if (curr == null )
    		 return -1;
    	 
    	 //base case.
    	 if(curr.getKey() == key) 
    	        return key; 
    	  
	    //traverse left.
	    if (curr.getKey() > key) 
	        return search(curr.getLeft(), key); 
	  
	    //traverse right.
	    return search(curr.getRight(), key); 
    }
    
    
    /**
     * Inorder traversal of a BST, implemented via recursion.
     * @param curr The initial Node (usually the root).
     */
    public void inorder(Object  c) 
    {
    	Node curr = (Node )c;
    	if (curr != null) 
        { 
    		//traverse left
            inorder(curr.getLeft());
            
            //print current
            System.out.println(curr.toString()); 
            
            //traverse right.
            inorder(curr.getRight()); 
        } 
    }
	
    /**
	 * Print the keys in range(key1, key2) given the rootLine. 
	 * @param rootLine Line of the BST's root.
	 * @param k1 Low key as Node.
	 * @param k2 High key as Node.
	 * @return  void
	 */
    public void range(Object c, int k1, int k2, boolean isSilent) 
    {
    	Node curr = (Node )c;
    	//traverse.
		if(curr != null) 
		{
			searchComparisons++;
			//traverse left.
			if(curr.getKey() > k1)
				range(curr.getLeft(), k1, k2, isSilent);
			
			//print current
			if(curr.getKey() > k1 && curr.getKey() < k2) 
			{
				if(!isSilent)
					System.out.println(curr.getKey());
					
			}
			
				
			//traverse right
			if(curr.getKey() < k2)
				range(curr.getRight(), k1, k2, isSilent);
				
			
		}
    }
    
    
    /**
	 * Prints the statistics for the surveilanced actions.
	 * 
	 */
	
	public void printStats() 
	{
		System.out.println("-----------------------------------------------------------------");
		System.out.println("\tDynamic BST.");
		System.out.println("\tInsertion time: "+insTime);
		System.out.println("\tMean insertion comparisons per key: "+insComparisons/nodeCount);
	}

}
