package com.logicaalternativa.forcomprehensions.build;

import com.logicaalternativa.forcomprehensions.IFor;

public class BuilderFor {
	
	public enum Type { DEFAULT, PARALLEL };
	
	public static IFor getInstance( Type type ) {
		
		if ( Type.PARALLEL.equals( type ) ) {
			
			return new ForParallel();			
			
		}
		
		return new ForDefault();
		
	}
	
	public static IFor getInstace( ) {		
		
		 return new ForDefault();
//		return new ForParallel();
		
	}

}
