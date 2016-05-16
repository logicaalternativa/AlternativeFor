package com.logicaalternativa.forcomprehensions.option;

import com.logicaalternativa.futures.FunctionMapper;
import com.logicaalternativa.futures.Monad;

public final class AlterNone<T> implements AlterOption<T> {

	protected AlterNone() {
		
	}

	@Override
	public <U> Monad<U> flatMap(FunctionMapper<T, Monad<U>> arg0) {
		
		return new AlterNone<U>();
	}

	@Override
	public <U> Monad<U> map(FunctionMapper<T, U> arg0) {
		
		return new AlterNone<U>();
	}

	@Override
	public <U> Monad<U> pure(U arg0) {
		
		return new AlterSome<U>(arg0);
	}

	@Override
	public Boolean isEmpty() {
		
		return true;
	}

	@Override
	public T get() {
		
		return null;
		
	}

}
