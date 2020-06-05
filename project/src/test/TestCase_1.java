package test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import trees.*;

/**
 * A class to instantiate test scenarios.
 * 
 * <br><br>  <strong>Common Test Scenario:</strong>
 * <ul>
 * 		<li>Serial Search on the RandomAccessFile for {@code num} keys, non existent.</li>	
 * 		<li>Binary Search on the RandomAccessFile for {@code num} keys, non existent.</li>
 * </ul>
 * Mean disk accesses for each technique and other metrics will be printed.
 * @author dasApfel
 *
 */
public class TestCase_1 {
	
	int n, min, max;
	ArrayList<Integer>	keys;
	BinarySearchTreeStatic 	sBST;
	BinarySearchTreeDynamic dBST;
	int[] tKeys;
	int arrComparisons;
	
	
	
	//Class constructor.
	
	/**
	 * 
	 * @param min	Minimum character count for the random generated keys
	 * @param max	Maximum character count for the random generated keys
	 * @param n		The number of random keys to be produced
	 * @param kNum The number of keys in each Tree.
	 * 
	 */
	public TestCase_1(int n, int min, int max, int kNum , BinarySearchTreeStatic s, BinarySearchTreeDynamic d) {
		
		this.keys = randomKeyFill(n,min, max);
		this.min = min;
		this.max = max;
		this.n = n;
		sBST = s;
		dBST = d;
		tKeys = sBST.getKeys();
	}
	
	
	
	public void init_testing() 
	{
		
		
		
		//random testing.
		searchRandom(keys, dBST,sBST, tKeys);
		
		
		
	}
	
	//methods.
	
	private void searchRandom(ArrayList<Integer> keys ,  BinarySearchTreeDynamic d, BinarySearchTreeStatic s, int[] arr) 
	{
		
		float sComp = 0, dComp = 0;
		float sTime, dTime;
		//time calculators.
		long start, end;
		
		
		start = System.currentTimeMillis();
		//search N random keys.
		for(Integer k : keys) 
		{
			
			
			s.search(s.getRoot(),k);
			
			sComp += s.getSearchComparisons();
	
			
		}
		end = System.currentTimeMillis();
		sTime = (end -start)/1000F;
		
		
		start = System.currentTimeMillis();
		//search N random keys.
		for(Integer k : keys) 
		{
		
			
			d.search(d.getRoot(), k);
			
			dComp += d.getSearchComparisons();
		}
		
		end = System.currentTimeMillis();
		dTime = (end -start)/1000F;
		
		//calibrate stats
		
		s.setSearchComparisons(0);
		d.setSearchComparisons(0);
		
		System.out.println("Comparison rate for static  BST when searching[100 keys] "+sComp/keys.size()+"["+sTime+"] sec.");
		System.out.println("Comparison rate for dynamic BST when searching[100 keys] "+dComp/keys.size()+"["+dTime+"] sec.");
		
		sComp = 0;
		dComp = 0;
		//range search
		for(Integer k : keys) 
		{
			s.range(s.getRoot(), k, k+100, true);
			sComp += d.getSearchComparisons();
			
			d.range(d.getRoot(), k, k+100, true);
			dComp += d.getSearchComparisons();
		}
		
		//calibrate stats
		
		s.setSearchComparisons(0);
		d.setSearchComparisons(0);
		
		System.out.println("Comparison rate for static  BST when range searching(key, key+100)[100 keys] "+sComp/keys.size()+"["+sTime+"] sec.");
		System.out.println("Comparison rate for dynamic BST when range searching(key, key+100)[100 keys] "+dComp/keys.size()+"["+dTime+"] sec.");
		
		sComp = 0;
		dComp = 0;
		//range search
		for(Integer k : keys) 
		{
			s.range(s.getRoot(), k, k+1000, true);
			sComp += d.getSearchComparisons();
			
			d.range(d.getRoot(), k, k+1000, true);
			dComp += d.getSearchComparisons();
		}
		
		//calibrate stats
		
		s.setSearchComparisons(0);
		d.setSearchComparisons(0);
		
		System.out.println("Comparison rate for static  BST when range searching(key, key+1000)[100 keys] "+sComp/keys.size()+"["+sTime+"] sec.");
		System.out.println("Comparison rate for dynamic BST when range searching(key, key+1000)[100 keys] "+dComp/keys.size()+"["+dTime+"] sec.");
		
		
		/**
		 * SORTED ARRAY TESTING. 
		 * 
		 * */
		
		
		//sort the actual keys.
		Arrays.sort(tKeys);
		
		
		start = System.currentTimeMillis();	
		//range search on sorted Array.
		for(Integer k : keys) 
		{
			findIndex(tKeys,0 ,tKeys.length-1, k);
		}
		
		end = System.currentTimeMillis();
		
		sTime = (end-start)/1000F;
		System.out.println("Comparison rate for sorted Array when  searching[100 keys] "+arrComparisons/keys.size()+"["+sTime+"] sec.");
		
		start = System.currentTimeMillis();	
		arrComparisons = 0;
		
		//range search on sorted Array.
		for(Integer k : keys) 
		{
			range(tKeys,k, k+100, true);
		}
		
		end = System.currentTimeMillis();
		
		sTime = (end-start)/1000F;
		System.out.println("Comparison rate for sorted Array when range searching(key, key+100)[100 keys] "+arrComparisons/keys.size()+"["+sTime+"] sec.");
		
		arrComparisons = 0;
		

		start = System.currentTimeMillis();	
		//range search on sorted Array.
		for(Integer k : keys) 
		{
			range(tKeys,k, k+1000, true);
		}
		
		end = System.currentTimeMillis();
		
		sTime = (end-start)/1000F;
		System.out.println("Comparison rate for sorted Array when range searching(key, key+1000)[100 keys] "+arrComparisons/keys.size()+"["+sTime+"] sec.");
		
		
		
		
	}
	
	
	/**
	 * Range search on a sorted Array.
	 * @param arr A sorted Array.
	 * @param k1  Lower bound.
	 * @param k2  Upper bound.
	 */
	
	private void range(int arr[], int k1, int k2, boolean isSilent) 
	{
		
		//distinguihs the indexes of the given keys
		int lowIndex = findIndex(arr, 0, arr.length-1, k1);
		int highIndex = findIndex(arr, 0, arr.length-1, k2);
		
		int currIndex = lowIndex+1;
		
		//itterate over keys.
		while(currIndex < highIndex) 
		{
			if(!isSilent)
				System.out.println("Array:"+arr[currIndex]);
			currIndex++;
		}
		
		
	}
	
	
	
	/**
	 * Given a key in Integer format, the following method finds the last Occurence of it.
	 * @param arr 	A sorted array.
	 * @param low 	The initial index.
	 * @param high	The end index.
	 * @param x     The key to seeek for in Integer format. 
	 * @return		Key index or -1.
	 */
	
	private int findIndex(int arr[], int low, int high, int x) 
    { 
		int n = arr.length;
        if (high >= low) 
        { 
        	arrComparisons++;
            int mid = low + (high - low)/2; 
            if (( mid == n-1 || x < arr[mid+1]) && arr[mid] == x) 
                 return mid; 
            else if (x < arr[mid]) 
                return findIndex(arr, low, (mid -1), x); 
            else
                return findIndex(arr, (mid + 1), high, x); 
        } 
    return -1; 
    } 
	 
	 
	
	/**
	 * Fills an ArrayList with n - randomly generated keys in the range [min, max]
	 * 
	 * @param n 	Dictates the number of keys to be generated
	 * @param min	The minimum size of a key
	 * @param max	The maximum size of a key
	 * @return An ArrayList with the keys generated.
	 * @see randStringGen
	 */
	private ArrayList<Integer> randomKeyFill(int n, int min, int max)
	{
		ArrayList<Integer> keys = new ArrayList<Integer>();
		
		Random rn = new Random();
		int range = max - min + 1;
		
		//fill the given ArrayList with n random keys.
		
		for(int i =0; i<n; i++) 
		{
			keys.add(rn.nextInt(range) + min);
		}
		
		
		/*
		 * A print module.
		 */
		System.out.println(n+" Random Keys Generated: OK.");
		
		
		
		return keys;
		
		
	}
	

}
