package demo1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Worker {

	private Random random = new Random();
	private List<Integer> list1 = new ArrayList<Integer>();
	private List<Integer> list2 = new ArrayList<Integer>();
	
	private Object lock1=new Object();
	private Object lock2=new Object();

	public void stageone() {
		
		synchronized (lock2) {
		
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list2.add(random.nextInt(100));
		}

		
	}

	public void stagetwo() {

		synchronized (lock1) {
		
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			list1.add(random.nextInt(100));
		}
		
	}

	public void process() {
		for (int i = 0; i < 1000; i++) {
			stageone();
			stagetwo();
		}
	}

	public void main() {
		System.out.println("Starting.........");

		long start = System.currentTimeMillis();

		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				process();

			}
		});

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				process();

			}
		});

		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long end = System.currentTimeMillis();

		System.out.println("Time Take: " + (end - start));

		System.out.println("List1: " + list1.size() + "; List2: " + list2.size());

	}

}
