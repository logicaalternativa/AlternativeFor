package com.logicaalternativa.forcomprehensions;

import com.logicaalternativa.futures.AlternativeFuture;
import com.logicaalternativa.futures.Monad;

public interface IFunction<T> {

	Monad<T> exec( Object...params );
	
}
