package com.logicaalternativa.forcomprehensions.option;

import com.logicaalternativa.futures.Monad;

public interface AlterOption<T> extends Monad<T>{
	
	public static <U> AlterOption<U> some( U value ) {
		
		return new AlterSome<U>( value );
		
	}
	
	public static <U> AlterOption<U> none( Class<U> type, String error ) {
		
		return new AlterNone<U>( error ); 
		
		
	}

}
