package com.logicaalternativa.forcomprehensions.option;

import java.util.function.Function;

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
	public <U> Monad<U> flatMap(Function<T, Monad<U>> fun) {
		return fun.apply( value );
	}

	@Override
	public <U> Monad<U> map(Function<T, U> fun) {
		return new AlterSome<U>(fun.apply( value ) );
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
