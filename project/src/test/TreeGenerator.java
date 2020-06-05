package test;


import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Scanner;

import trees.*;



public class TreeGenerator {

	

	public static void main(String[] args) throws NullPointerException, IOException 
	{
		BinarySearchTreeStatic bstStatic = null;
		BinarySearchTreeDynamic bstDynamic = new BinarySearchTreeDynamic();
		Scanner in = new Scanner(System.in);
		String ch;

		
		//time calculators.
		long start, end;
		
		
		do
		{
			int param = -1;
			menuPrint();
			System.out.println();
			System.out.print("CMD> ");
			
			// use newline as delimiter
			in.useDelimiter("\\n"); 
			
			//get trimmed input
			ch = in.nextLine().trim();
			String arg0 = ch.split(" ")[0];
			String arg1 = "";
			
			//Attempt to read arg1.Only stats, help calls havew no arg1.
			if(ch.compareTo("stats") == 0 || ch.compareTo("help") == 0)
				arg1 = "";
			else if(isValidArg(ch))
				arg1 = ch.split(" ")[1];
			else 
				continue;
			
			//distinguish command
			switch(arg0) 
			{
				case "tree":
					
					if(arg1.compareTo("-s") == 0)
					{
						while (param < 1)
						{
							System.out.println("Type the maximum depth of the Tree: ");
							
							try 
							{
								param = Integer.parseInt(in.nextLine().trim());
							}
							catch (NumberFormatException e) 
							{
								
								continue;
							}
							
							bstStatic = new BinarySearchTreeStatic(param);
							
							
						}
					}
					
					
					break;
					
			
				case "con":
					
					System.out.println("Type the number of elements you wish to be inserted:");
					param = Integer.parseInt(in.nextLine().trim());
					ArrayList<Integer> values;
				
					
					if(arg1.compareTo("-s") == 0)
					{
						
						//static approach
						if(bstStatic == null) 
							break;

						if(param > bstStatic.getMaxNodes())
							param = bstStatic.getMaxNodes();
						//read some values.
						values = constructValues(param, in);
						
						//just to be safe, noobody insert more than actually fitting in a STATIC BST.
						if(param > bstStatic.getMaxNodes())
							break;
						
						for(Integer val : values) 
						{
							bstStatic.insert(val);
						}
						
					
					}
					else if(arg1.compareTo("-d") == 0) 
					{
						
						//read some values.
						values = constructValues(param, in);
						
						//insert them
						for(Integer val : values) 
						{
							bstDynamic.insert(val);
						}
						
						
					}
					
					
					
					
					break;
					
				case "print":
					if(arg1.compareTo("-s") == 0)
					{
						try 
						{
							bstStatic.printTreeArray();
						}
						catch (NullPointerException e) 
						{
							System.out.println("Bad Command. User tried to print a non- initialized tree.");
						}
					}
					else if(arg1.compareTo("-d") == 0)
						bstDynamic.inorder(bstDynamic.getRoot());
					
					break;
				
				case "file":
					
					// modify as needed.
					String prefix = "C:\\Users\\user\\eclipse-workspace\\treeWorld\\testData\\testnumbers_";
					String postfix = "_be.bin";
					String filePath = null;
					
					while (param != 50 && param != 10 && param != 100 && param != 1000 && param != 10000 && param != 100000 && param != 1000000 && param != 10000000)
					{
						System.out.println("Choose a set of keys (50, 100, 1000, 10000, 100000, 1000000, 10000000)");
						param = Integer.parseInt(in.nextLine().trim());
						
						
					}
					//formulate filePath.
					filePath = prefix+param+postfix;
					
					//construct the tree..
					if(arg1.compareTo("-s") == 0)
					{
						
						//static implementation
						bstStatic = new BinarySearchTreeStatic(param);
						
						
						//calculate exec. time.
						start = System.currentTimeMillis();
						
						//exec.
						storeKeys(param , filePath, bstStatic);
						
						//end exec. time.
						end = System.currentTimeMillis();
						
						bstStatic.setInsTime((end-start)/1000F);
						
						
						
					}
					else if(arg1.compareTo("-d") == 0) 
					{
						
						//calculate exec. time.
						start = System.currentTimeMillis();
						
						//exec.
						storeKeys(param,filePath,bstDynamic);
						
						//end exec. time.
						end = System.currentTimeMillis();
						

						bstDynamic.setInsTime((end-start)/1000F);
						
						
					}
						
									
					break;
				
				case "inorder":
					
					if(bstStatic != null && arg1.compareTo("-s") == 0)
						bstStatic.inorder(bstStatic.getRoot());
					else if(arg1.compareTo("-d") == 0)
						bstDynamic.inorder(bstDynamic.getRoot());
					
					break;
				
				case "search":
					
					
					int query = 0;
					
					
					System.out.println("Type a key to be searched:");
					try 
					{
							//retrieve input from console. Todo: Maybe apply defence logic for dummies!!!
							query = in.nextInt();
							in.nextLine();
							
					}
					catch (Exception e)
					{
							System.out.println("Input was too long.");
							break;
					}
					
					
					//search the typed key.
					int retVal =-1;
					
							
					if(arg1.compareTo("-s") == 0) 
						retVal = bstStatic.search(bstStatic.getRoot(), query);
					else
						retVal = bstDynamic.search(bstDynamic.getRoot(), query);
					
					//prompt user.
					if(retVal == -1)
						System.out.println("Key not found");
					else if(retVal != query)
						System.out.println("Key "+query+" found at line: "+retVal+" of BST array [static].");
					else 
						System.out.println("Key "+query+" found at BST[dynamic]");
					
					break;
					
					
				case "range":
					
					
					String queries = null;
					
					//catch a dummy user's attempts to break things down.
					while(queries == null || queries.split(" ").length != 2) 
					{
						System.out.println("Type key1, key2 (range limits):");
						//retrieve input from console. Todo: Maybe apply defence logic for dummies!!!
						queries = in.nextLine().trim();	
					}
					
					//range search.
					if(arg1.compareTo("-s") == 0)
						bstStatic.range(bstStatic.getRoot(), Integer.parseInt(queries.split(" ")[0]), Integer.parseInt(queries.split(" ")[1]), false);
					else if(arg1.compareTo("-d") == 0)
						bstDynamic.range(bstDynamic.getRoot(), Integer.parseInt(queries.split(" ")[0]), Integer.parseInt(queries.split(" ")[1]), false);
					
					break;
				case "stats":
					
					//don't care about argument1.
					
					TestCase_1 t = new TestCase_1(100,0 ,2100000000, 1000000, bstStatic, bstDynamic);
					t.init_testing();
					
					if( bstStatic != null)
						bstStatic.printStats();
					if(bstDynamic.getRoot() != null)
						bstDynamic.printStats();
					break;				
					
				case "help":
					
					printHelp();
					break;
				case "q":
					break;
					
					
				default:
					System.out.println("Bad Command");
				
			}
	
			
		}while(ch.compareTo("q") != 0);
		
		//close the resources used.
		in.close();
		
	}
	
	/**
	 * Check the validity of arg[1].
	 * @param args
	 * @return
	 */
	private static boolean isValidArg(String args) 
	{
		try
		{	
			String arg = args.split(" ")[1];
			if(arg.compareTo("-s") == 0 || arg.compareTo("-d") == 0)
				return true;
			else 
				throw new NullPointerException();
		}
		catch(Exception e) 
		{
			
			System.out.println("Bad Command.");
			System.out.println("Command has wrong arguments, or arguments less than needed. Type \"help\" for more!");
			return false;
		}
		
		
	}
	
	//prints the available commands in a menu - style printing.
	private static void menuPrint() 
	{
			
			
			System.out.println("\nType a command or q (Exit):");
			System.out.println("\"tree\"   	- Create a BST.");
			System.out.println("\"con\"     	- Initialize BST from Console.");
			System.out.println("\"file\"    	- Initialize BST from File.");
			System.out.println("\"print\"   	- Print the tree Array of the static BST.");
			System.out.println("\"inorder\" 	- Inorder BST traversal.");
			System.out.println("\"search\"  	- Search the BST for a key.");
			System.out.println("\"stats\"  	- Print stats about an active [initialized] trees (No argument neeeded).");
			System.out.println("\"help\"  	- See help about commands.");
			
			
			
			
	}
	
	private static void printHelp() 
	{
		System.out.println("command format general: <cmd> -s OR <cmd> -d");
		System.out.println("command format1: <cmd> -s   	- Command applied on the static BST.");
		System.out.println("command format2: <cmd> -d   	- Command applied on the dynamic BST.");
		System.out.println("command format2: <cmd>    		- Command applied on both trees [$ \"stats\"].");
		
		
	}
	
	/**
	 * Reads m - Integer values, typed by the user (via Console - STDIN). Then appends those on an ArrayList.
	 * @param m - Integers to be read from Console.
	 * @return
	 */
	
	private static ArrayList<Integer> constructValues(int m, Scanner in)
	{
		ArrayList<Integer> data = new ArrayList<Integer>();
		
	
	
		for(int i = 0; i < m; i++) 
		{
			System.out.print("Type an integer value:");
			data.add(Integer.parseInt(in.nextLine().trim()));
		}
		
		
		
		
		
		return data;
	}
	
	/**
	 * Reads a file with keys - Integers (page-wise for efficiency), and inserts those in a static BST.
	 * @param keys
	 * @param fName
	 * @param tree
	 * @throws NullPointerException
	 * @throws EOFException
	 * @throws IOException
	 * @see readFile() for more
	 */
	
	private static void storeKeys(int keys, String fName, BinarySearchTree tree) throws NullPointerException, EOFException, IOException 
	{
		
		/*
		 * THIS Implementation (dataPage spliting) is a time booster when it comes on dealing with realatively big data [10^6 keys etc.]
		 */
		
		//the size of a data page, and the page split.
	
		int pages = (keys < 1000) ?  10 : 1000;  // low key count doesn't worth much splitting.
		int pageKeys = keys/pages; 
		
		// a RandomAccessFile showing the file given, use of RAF is far more time convenient than a DataInputStream (yeah I tested it).
		RandomAccessFile in = new RandomAccessFile(fName, "r");
		
		//itterate over pages.
		for(int i = 0; i < pages; i++) 
		{
			
			ArrayList<Integer> keysRead = readFile(i+1, pageKeys, Integer.BYTES,in);
			
			//insert in tree.
			for(Integer k : keysRead) 
				tree.insert(k);
			
		}
		
		//close resources.
		in.close();
		
	}
	
	
	/**
	 * Reads a page of binary keys, transforms them to Integers and appends those in an ArrayList.
	 * @param pageNum
	 * @param pageKeys
	 * @param keySize
	 * @param in
	 * @return An ArrayList with the keys read.
	 * @throws IOException
	 * @throws NullPointerException
	 * @throws EOFException
	 */
	private static ArrayList<Integer> readFile(int pageNum ,int pageKeys,int keySize,RandomAccessFile in) throws IOException,NullPointerException, EOFException
	{
		
		
		//an arraylist of tuples to store results
		ArrayList<Integer> keys = new ArrayList<Integer>(); 
				
		//stats
		int pageSize = pageKeys*keySize;
		int read = 0;
		
		//a byte array to store info.
		byte[] buffer = new byte[pageSize];
		
		//set a file pointer.
		int fp = (pageNum-1)*pageSize;
		
		// seek the position wishing to be read.Then read a page on buffer
		in.seek(fp);
		in.read(buffer);

		//wrap it.
		ByteBuffer bb = ByteBuffer.wrap(buffer);
		
		
		// each page here is exactly sliced, so no need to worry about the last page.
		
		while(read < pageKeys) 
		{
			
			//get the Integer
			int someInt = bb.getInt();
			

			//append a new key in the list.
			keys.add(someInt);
		
			// inform count.
			read++;
			
		}
	
		
		// return an ArrayList with the data read.
		return keys;
		
	}	
		
	
	
	
}
