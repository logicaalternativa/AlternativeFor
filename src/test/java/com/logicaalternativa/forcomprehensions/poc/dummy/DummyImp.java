package com.logicaalternativa.forcomprehensions.poc.dummy;

import java.util.Random;

import com.logicaalternativa.futures.AlternativeFuture;

public class DummyImp implements Dummy {
	
	public DummyImp() {
		
		
	}
	
	public  AlternativeFuture<String> dummy( String arg1 ){
		
		waiting();
		
		System.out.println("Calling dummy value: " + arg1);
		
		return AlternativeFuture.successful( arg1 ); 
		
		
	}	
	
	public AlternativeFuture<String> dummyConcat( String arg1, String arg2 ){
		
		waiting();
		
		System.out.println("Calling dummyConcat value1: " + arg1 + ", value2: " + arg2);
		
		return AlternativeFuture.successful( arg1.concat(arg2)); 
		
		
	}	

	
	public Integer dummyConvert( String arg1, String arg2, String arg3){
		
		waiting();
		
		System.out.println("Calling dummyConvert value1: " + arg1 + ", value2: " + arg2 + ", value3: " + arg3 );
		
		return Integer.parseInt(arg1) + Integer.parseInt(arg2 ) + arg3.length(); 
		
	}
	
	private void waiting() {
		
		int slp = ( new Random() ).nextInt( 200 );
		
		try {
			Thread.sleep(slp);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
