package com.logicaalternativa.forcomprehensions.option;

import com.logicaalternativa.futures.FunctionMapper;
import com.logicaalternativa.futures.Monad;

public class AlterSome<T> implements AlterOption<T> {
	
	private final T value;

	public T getValue() {
		return value;
	}

	public AlterSome( final T value ) {
		this.value = value;		
		
	}

	@Override
	public <U> Monad<U> flatMap(FunctionMapper<T, Monad<U>> fun) {
		return fun.map( value );
	}

	@Override
	public <U> Monad<U> map(FunctionMapper<T, U> fun) {
		return new AlterSome<U>(fun.map( value ) );
	}

	@Override
	public <U> Monad<U> pure(U arg0) {
		return new AlterSome<U>( arg0 );
	}	

}
