package com.logicaalternativa.forcomprehensions;

import com.logicaalternativa.futures.Monad;

public interface IFor {
	
	<T> IFor line( final IVar varReturn, final IFunction<T> function, Object[] parameters );
	
	<T> Monad<T> yield( final IMapper<T> function, Object[] parameters );

}
