package com.logicaalternativa.forcomprehensions.poc.dummy;

import com.logicaalternativa.futures.AlternativeFuture;

public interface Dummy {
	
	public  AlternativeFuture<String> dummy( String arg1 );
	
	public AlternativeFuture<String> dummyConcat( String arg1, String arg2 );
	
	public Integer dummyConvert( String arg1, String arg2, String arg3);

}