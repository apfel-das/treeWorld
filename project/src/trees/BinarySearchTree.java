package trees;

public abstract interface BinarySearchTree {

	
	
	public void insert(int key);
	
	public int search(Object c, int key);
	public void range(Object c, int k1, int k2, boolean isSilent);
	public void inorder(Object c);
	
	
	public void printStats();
	
}
