package com.logicaalternativa.forcomprehensions.option;

import com.logicaalternativa.futures.FunctionMapper;
import com.logicaalternativa.futures.Monad;

public final class AlterSome<T> implements AlterOption<T> {
	
	private final T value;

	protected AlterSome( final T value ) {
		
		if ( value == null ) {
			
			throw new IllegalArgumentException( "Value can't be null" );
			
		}
		
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

	@Override
	public Boolean isEmpty() {
		return true;
	}

	@Override
	public T get() {
		
		return value;
	}	

}
