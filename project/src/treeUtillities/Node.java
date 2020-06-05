package treeUtillities;


/**
 * A class depicitng a Node of a dynamic BST.
 * @author dasApfel
 *
 */
public class Node {

	/**
	 * Int key 		- The actual information stored in the Node.
	 * Node left 	- Left child Node of this
	 * Node right 	- Right child Node og this.
	 */
	private int key;
	private Node left;
	private Node right;
	
	
	public Node(int k) 
	{
		key = k;
		left = null;
		right = null;
		
	}


	public int getKey() {
		return key;
	}


	public void setKey(int key) {
		this.key = key;
	}


	public Node getLeft() {
		return left;
	}


	public void setLeft(Node left) {
		this.left = left;
	}


	public Node getRight() {
		return right;
	}


	public void setRight(Node right) {
		this.right = right;
	}
	
	public void print() 
    {
    	System.out.println(this.toString());
    }
    
    @Override
    public String toString() 
    {
    	return String.valueOf(this.key);
    }
    

}
