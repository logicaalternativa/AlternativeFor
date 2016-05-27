package com.logicaalternativa.forcomprehensions.build;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.logicaalternativa.forcomprehensions.IFor;
import com.logicaalternativa.forcomprehensions.IVar;
import com.logicaalternativa.futures.Monad;

public abstract class ForBase implements IFor {
	
	protected Map<String, Object> vars = new ConcurrentHashMap<>();
	
	protected static enum Empty {EMPTY, NULL};
	
	protected IVar lastIVar;
	
	protected Monad<?> myMonad = null;
	
	
	protected Object[] newParameters( final Object[] old ) {
		
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

}
