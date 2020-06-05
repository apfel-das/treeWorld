package trees;

/**
 * A static implementation of a Binary Search Tree(BST) with three 1 - dim arrays holding keys,left,right index pointers.
 * @author dasApfel
 *
 */

public class BinarySearchTreeStatic implements BinarySearchTree{
	
	/*
	 * Member Variables
	 * root:		Reprsesents the index of the root Node.
	 * activeNodes:	Represents actual Nodes with some valid info inside the tree. 
	 * totalNodes: 	Reprsents the N - parameter, total keys that can be stored inside the tree.
	 *
	 */
	private int root; 
	private int actualNodes;
	private final int maxNodes; 
	private int availPos;
	private int treeKeys[];
	private int treeLeft[];
	private int treeRight[];
	public boolean isInit;
	
	
	//stats
	private float insTime;
	private float searchTime;
	private float insComparisons;
	private float searchComparisons;
	
	
	

	public BinarySearchTreeStatic(int N) 
	{
		treeKeys = new int[N];
		treeLeft = new int[N];
		treeRight = new int[N];
		maxNodes = N;
		actualNodes = 0;
		root = -1; // empty tree sign.
		isInit = true;
		this.init();
		
	}
	
	public int[] getKeys() 
	{
		return treeKeys;
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


	public void setInsComparisons(float insComparisons) {
		this.insComparisons = insComparisons;
	}


	public void setSearchComparisons(float searchComparisons) {
		this.searchComparisons = searchComparisons;
	}


	public int getMaxNodes() 
	{
		return maxNodes;
	}
	
	//Setters,Getters.
	public int getRoot() 
	{
		return this.root;
	}
	
	public void setRoot(int r) 
	{
		root = r;
	}
	
	/**
	 * Initialize the Array to simulate the BSTree.
	 */
	private void init() 
	{
		availPos = 0;
		
		
		// initialize arrays.
		for(int i = 0; i < treeKeys.length; i++) 
		{
			treeKeys[i] = -1;
			treeLeft[i] = -1;
			treeRight[i] = (i == maxNodes - 1) ? -1 : i+1;
		}
		
	}
	
	/**
	 * Inserts the given key, in the tree Array, informing and updating metrics.
	 * @param key
	 */
	public void insert(int key) 
	{
		int curr = root;
		actualNodes++;
		
		
		
		if(root == -1) 
		{
			
			treeKeys[availPos] = key;
			root = availPos;
			availPos = treeRight[availPos];
			treeRight[root] = -1;
			
			return;
		}
		
		while(true) 
		{
			insComparisons++;
			
			//left insertion
			if(key < treeKeys[curr]) 
			{
				if(treeLeft[curr] == -1) 
				{
					int oldPos = availPos;
					
					// update child reference.
					treeLeft[curr] = availPos;
				
					//insert the new child.
					treeKeys[availPos] = key;
										
					//get the next available pos.
					availPos = treeRight[availPos];
								
					//wipe the right children linkage of the child.			
					treeRight[oldPos] =  -1;
					
					
					return;
				}
				
				// move left.
				curr = treeLeft[curr];
				
			}
			else
			{
				// right insertion.
				if(treeRight[curr] == -1) 
				{
					
					//mark the creation index.
					int oldPos = availPos;
					

					// update child reference of father.
					treeRight[curr] = availPos;
					
					//insert the new child, children at the left side, wipe right children of this child coming from init().
					treeKeys[availPos] = key;
					
					
					// get the next available position.
					availPos = treeRight[availPos];
					
					
					//wipe any child leftovers from init, in the new child.
					treeRight[oldPos] = -1;
					
					return;
				}
				// move right.
				curr = treeRight[curr];
			}
		}
		
		
	}
	
	/**
	 * Given a key and the rootLine, searches for the given key.
	 * @param rootLine
	 * @param key
	 * @return
	 */
	
	public int search(Object c, int key) 
	{
		
		int rootLine = (Integer )c;
		
		while(rootLine != -1) 
		{
			searchComparisons++;
			// key found
			if(treeKeys[rootLine] == key) 
				return rootLine;
			
			//left movement
			if(treeKeys[rootLine] > key) 
				rootLine = treeLeft[rootLine];
			else
				rootLine = treeRight[rootLine];
			
			
		}
		return -1;
	}
	
	/**
	 *  Inorder Traversal of the BST, implemented as a recursive one.
	 * @param rootLine The line of the root in the BST array.
	 */
	public void inorder(Object c) 
	{
		int rootLine = (Integer) c;
		if(rootLine != -1) 
		{	
			//traverse left.
			inorder(treeLeft[rootLine]); 		
			//print current
			System.out.println(treeKeys[rootLine]);
			
			//traverse right
			inorder(treeRight[rootLine]);
		}
	}
	/**
	 * Count the keys in range(key1, key2) given the rootLine. 
	 * @param rootLine Line of the BST's root.
	 * @param k1 Low key.
	 * @param k2 High key.
	 * @return  Key count 
	 */
	
	public void range(Object c, int k1, int k2, boolean isSilent) 
	{
		int rootLine = (Integer) c;
		//traverse.
		if(rootLine != -1) 
		{
			searchComparisons++;
			//traverse left.
			if(treeKeys[rootLine] > k1) 
			{
				range(treeLeft[rootLine], k1, k2, isSilent);
			}
			//print current
			if(treeKeys[rootLine] > k1 && treeKeys[rootLine] < k2) 
			{
				if(!isSilent)
					System.out.println(treeKeys[rootLine]);
			}
			
				
			//traverse right
			if(treeKeys[rootLine] < k2)
				range(treeRight[rootLine], k1, k2, isSilent);
				
			
		}
			
		
		
		
	}
	
	/**
	 * Print the array that simulates a static-implementation of a BSTree.
	 */
	public void printTreeArray() 
	{
		//output should fit a console
		int l = treeKeys.length > 100 ? 100 : treeKeys.length;
		
		
		System.out.format("%s\n", "--------Tree Array--------");
		System.out.format("%s\t   %s\t     %s  %s\n", "Index", "Key", "Left", "Right");
		
		for(int i = 0; i < l; i++) 
		{
			System.out.format("%s%-2d%s  %s%12d %s%4d %s%4d\n","[",i,"]", "|",treeKeys[i], "|",treeLeft[i], "|",treeRight[i]);
		}
		System.out.println("\n     ROOT: "+root+" AVAIL: "+availPos);
		System.out.format("%s\n", "--------------------------");
		
		//prompt the user for the change in printing.
		if(treeKeys.length > 100000)
			System.out.println("\n**Warning** Array was too big and DEPRECEATED [print limit is: 100, actual is:"+treeKeys.length+"]");
	}
	
	/**
	 * Prints the statistics for the surveilanced actions.
	 * 
	 */
	
	public void printStats() 
	{
		System.out.println("-----------------------------------------------------------------");
		System.out.println("\tStatic BST with: "+treeKeys.length+" nodes.");
		System.out.println("\tInsertion time: "+insTime);
		System.out.println("\tMean insertion comparisons per key: "+insComparisons/treeKeys.length);
		
	}
	
	
}
