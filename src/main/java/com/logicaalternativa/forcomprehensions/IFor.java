package com.logicaalternativa.forcomprehensions;

import java.util.function.Function;

import com.logicaalternativa.futures.Monad;

public interface IFor {
	
	<T> IFor line(final  IVar varReturn, final Function<Object[] , Monad<T>> function,
			final Object[] parameters);
	
	<T> Monad<T> yield( final Function<Object[] , T> function, Object[] parameters );

}
