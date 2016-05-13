package com.logicaalternativa.forcomprehensions.build;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.logicaalternativa.forcomprehensions.IFor;
import com.logicaalternativa.forcomprehensions.IFunction;
import com.logicaalternativa.forcomprehensions.IMapper;
import com.logicaalternativa.forcomprehensions.IVar;
import com.logicaalternativa.futures.AlternativeFuture;
import com.logicaalternativa.futures.Monad;

public class BuilderFor {
	
	private static enum Empty {EMPTY};
	
	public static IFor getInstace( ) {		
		
		return new IFor() {
			
			private Map<String, Object> vars = new ConcurrentHashMap<>();
			
			private Monad<?> myMonad = null;
			
			private IVar lastIVar;
			
			private Object[] newParameters( final Object[] old ) {
				
				if ( old == null ) {
					
					return null;
				}
				
				final Object[] newparam = new Object[ old.length ];
				
				int i = 0;
				
				for (Object param : old) {
					
					if ( param instanceof IVar) {
						
						IVar var =  (IVar) param;
						
						Object object = vars.get( var.getName()  );
						
						newparam[i] = object instanceof Empty ? null : object;
						
					} else {
						
						newparam[i] = param;
					}
					
					i++;
					
				}
				
				return newparam;			
				
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
									vars.put(lastIVar.getName(), s );
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
								vars.put(lastIVar.getName(), s );
								
								return function.exec( newParameters(parameters ) );
								 
						}); 
				
			}
			
			
			
		};
		
	}

}
