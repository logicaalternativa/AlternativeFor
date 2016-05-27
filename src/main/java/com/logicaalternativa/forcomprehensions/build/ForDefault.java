package com.logicaalternativa.forcomprehensions.build;

import com.logicaalternativa.forcomprehensions.IFor;
import com.logicaalternativa.forcomprehensions.IFunction;
import com.logicaalternativa.forcomprehensions.IMapper;
import com.logicaalternativa.forcomprehensions.IVar;
import com.logicaalternativa.futures.Monad;

public class ForDefault extends ForBase {
	
	protected ForDefault(){
		
	}
	
	
	
				
	@Override
	public <T> IFor line( final IVar varReturn, final IFunction<T> function,
			final Object[] parameters) {
		
		vars.put(varReturn.getName(), Empty.EMPTY);				
		
		if ( myMonad == null ) {

			lastIVar = varReturn;
			
			 myMonad = function.exec( newParameters(parameters ) );					
			 
		} else {
			
			myMonad =  myMonad.flatMap(
					s -> { 
							vars.put(lastIVar.getName(), ( s != null ? s : Empty.NULL ) );
							lastIVar = varReturn;
							return function.exec( newParameters(parameters ) );
						});		
			
			
		}
		return this;
	}

	@Override
	public <T> Monad<T> yield(final IMapper<T> function,
			final Object[] parameters) {
		
		return myMonad.map(
				s -> { 
						vars.put(lastIVar.getName(), ( s != null ? s : Empty.NULL ) );
						
						return function.exec( newParameters(parameters ) );
						 
				}); 
		
	}
	
	


}
