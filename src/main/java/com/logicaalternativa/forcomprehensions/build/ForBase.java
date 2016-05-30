package com.logicaalternativa.forcomprehensions.build;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import com.logicaalternativa.forcomprehensions.IFor;
import com.logicaalternativa.forcomprehensions.IVar;
import com.logicaalternativa.futures.Monad;

public abstract class ForBase implements IFor {
	
	protected Map<String, Object> vars = new ConcurrentHashMap<>();
	
	protected static enum Empty {EMPTY, NULL};
	
	protected IVar lastIVar;	
	
	protected Object[] newParameters( final Object[] old ) {
		
		if ( old == null ) {
			
			return null;
		}
		
		
		return Stream.of( old )
			.map (s -> {
						if ( s instanceof IVar) {
							
							final IVar var =  (IVar) s;
							
							final Object object = vars.get( var.getName() );
							
							return object instanceof Empty ? null : object;
							
						} else {
							
							return s;
						}
						
				} )
			 .toArray();
				
		
	}

}
