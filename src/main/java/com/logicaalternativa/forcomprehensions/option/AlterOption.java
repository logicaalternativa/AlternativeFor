package com.logicaalternativa.forcomprehensions.option;

import com.logicaalternativa.futures.Monad;

public interface AlterOption<T> extends Monad<T>{
	
	Boolean isEmpty();
	
	T get();
	
	public static <U> AlterOption<U> some( U value ) {
		
		return new AlterSome<U>( value );
		
	}
	
	public static <U> AlterOption<U> none( Class<U> type ) {
		
		return new AlterNone<U>(); 		
		
	}

}
