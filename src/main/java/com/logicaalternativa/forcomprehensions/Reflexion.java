package com.logicaalternativa.forcomprehensions;

import java.lang.reflect.InvocationTargetException;

public interface Reflexion {
	
	Object invoke( Object[] arguments ) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;

}
