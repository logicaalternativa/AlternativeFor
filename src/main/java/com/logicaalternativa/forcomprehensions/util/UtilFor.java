package com.logicaalternativa.forcomprehensions.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.logicaalternativa.forcomprehensions.IFunction;
import com.logicaalternativa.forcomprehensions.IMapper;
import com.logicaalternativa.forcomprehensions.IVar;
import com.logicaalternativa.forcomprehensions.Reflexion;
import com.logicaalternativa.futures.Monad;

public final class UtilFor {
	
	
	private UtilFor() {
		
	}
	
	public static Object[] args( Object...arguments ) {
		
		return arguments;
		
	}
	
	public static Reflexion invoke( Object object, final String methodName ) {
		
		return new Reflexion() {
			
			private Method getMethod() {
				
				Method[] declaredMethods = object.getClass().getDeclaredMethods();
				
				for (Method method : declaredMethods) {
					
					if ( method.getName().equals( methodName ) ){
						
						return method;
						
					}
				}
				
				return null;
				
			}
			
			
			@Override
			public Object invoke(Object[] arguments) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
				
				final Method method = getMethod();
						
				method.setAccessible(true);
				
				return method.invoke(object, arguments);
			}
		};
		
		
		
		
	}
	
	public static <T, S extends Monad<T>> IFunction<T> fromMonad (final S monad ) {
		
		return new IFunction<T>() {

			@Override
			public S exec(Object... params) {
				return monad;
			}
			
			
		};
		
		
	}
	
	
	
	public static <T> IFunction<T> function( final Class<T> typeReturn, final Reflexion reflexion ) {
		
		return new IFunction<T>() {	
			

			@SuppressWarnings("unchecked")
			@Override
			public Monad<T> exec(Object... params) {
				
				try {
				
					return (Monad<T>) reflexion.invoke(params);
					
				} catch (Exception e) {
					
					throw new RuntimeException( e );
				}			
			}
			
			
		};
		
		
	}
	
	public static <T> IMapper<T> mapper( final Class<T> typeReturn, final Reflexion reflexion ){
		
		return new IMapper<T>() {

			@SuppressWarnings("unchecked")
			@Override
			public T exec(Object... params) {
				
				try {
					return (T) reflexion.invoke(params);
					
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace();
					
					throw new RuntimeException(e);
				}
				
			}
		};
		
		
	} 
	
	public static IVar var( final String name ) {
		
		return new IVar() {
			
			public String getName() {
			
				return name;
			}
		};
		
		
	}
	
	public static Boolean gBol(Object object) {
		
		return toCast( object, Boolean.class );
	} 
	
	public static String gStr(Object object) {
		
		return toCast( object, String.class );
	} 
	
	@SuppressWarnings("unchecked")
	public  static <T> T toCast( Object object, Class<T> type ) {
		
		return object != null ? ( T ) object : null;
		
	}

}
