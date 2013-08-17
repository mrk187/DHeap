package alda.heap;

import java.util.PriorityQueue;
import java.util.Random;

public class MyMain {

	
	
	public static void main(String[]args){
		
		DHeap<Integer> heap = new DHeap<Integer>();
		PriorityQueue<Integer> oracle = new PriorityQueue<Integer>();
		Random rnd = new Random();
		Random bool = new Random();
		
		
		
		for(int i=0; i<100; i++){
			int x = rnd.nextInt(1000);
			heap.insert(x);
			oracle.add(x);
			
			
			while(!heap.isEmpty() && bool.nextBoolean()){
				System.out.println("Heap:" + heap.deleteMin() +" Oracle:" + oracle.poll());
		}
		}

		System.out.println("DeletedHeap: " + heap.deleteMin());
		System.out.println("DeletedOrac: " +oracle.poll());
		
		System.out.println(heap.toString());
		System.out.println(oracle.toString());
	
	}
}
