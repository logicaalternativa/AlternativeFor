package com.logicaalternativa.forcomprehensions.option;

import java.util.function.Function;

import com.logicaalternativa.futures.Monad;

public final class AlterNone<T> implements AlterOption<T> {

	protected AlterNone() {
		
	}

	@Override
	public <U> Monad<U> flatMap(Function<T, Monad<U>> arg0) {
		
		return new AlterNone<U>();
	}

	@Override
	public <U> Monad<U> map(Function<T, U> arg0) {
		
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
