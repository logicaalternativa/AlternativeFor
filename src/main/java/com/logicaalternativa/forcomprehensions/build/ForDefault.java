package com.logicaalternativa.forcomprehensions.build;

import java.util.function.Function;

import com.logicaalternativa.forcomprehensions.IFor;
import com.logicaalternativa.forcomprehensions.IVar;
import com.logicaalternativa.futures.Monad;

public class ForDefault extends ForBase {
	
	private Monad<?> myMonad = null;
	
	protected ForDefault(){
		
	}
	
	
	
				
	@Override
	public <T> IFor line(IVar varReturn, Function<Object[], Monad<T>> function,
			Object[] parameters) {
		
		vars.put(varReturn.getName(), Empty.EMPTY);				
		
		if ( myMonad == null ) {

			lastIVar = varReturn;
			
			 myMonad = function.apply( newParameters(parameters ) );					
			 
		} else {
			
			myMonad =  myMonad.flatMap(
					s -> { 
							vars.put(lastIVar.getName(), ( s != null ? s : Empty.NULL ) );
							lastIVar = varReturn;
							return function.apply( newParameters(parameters ) );
						});		
			
			
		}
		return this;
	}

	@Override
	public <T> Monad<T> yield(final Function<Object[], T> function,
			final Object[] parameters) {
		
		return myMonad.map(
				s -> { 
						vars.put(lastIVar.getName(), ( s != null ? s : Empty.NULL ) );
						
						return function.apply( newParameters(parameters ) );
						 
				}); 
		
	}




	
	
	


}
