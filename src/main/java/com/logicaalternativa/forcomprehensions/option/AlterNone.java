package com.logicaalternativa.forcomprehensions.option;

import com.logicaalternativa.futures.FunctionMapper;
import com.logicaalternativa.futures.Monad;

public class AlterNone<T> implements AlterOption<T> {

	final String error;
	
	public AlterNone( String error ) {
		
		this.error = error;
		
	}
	
	public String getError() {
		return error;
	}

	@Override
	public <U> Monad<U> flatMap(FunctionMapper<T, Monad<U>> arg0) {
		
		return new AlterNone<U>( getError() );
	}

	@Override
	public <U> Monad<U> map(FunctionMapper<T, U> arg0) {
		
		return new AlterNone<U>( getError() );
	}

	@Override
	public <U> Monad<U> pure(U arg0) {
		
		return new AlterSome<U>(arg0);
	}

}
