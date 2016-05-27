package com.logicaalternativa.forcomprehensions.build;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import com.logicaalternativa.forcomprehensions.IFor;
import com.logicaalternativa.forcomprehensions.IFunction;
import com.logicaalternativa.forcomprehensions.IMapper;
import com.logicaalternativa.forcomprehensions.IVar;
import com.logicaalternativa.futures.Monad;

public class ForParallel extends ForBase {
	
	final BlockingQueue<RegLine<?>> queue;	
	
	protected ForParallel() {
		
		queue = new LinkedBlockingQueue<>();
		
	}

	@Override
	public <T> IFor line(final  IVar varReturn, final IFunction<T> function,
			final Object[] parameters) {
		
		final String name = varReturn.getName();
		
		if ( vars.containsKey( name ) ) {
			
			throw new IllegalArgumentException( String.format("Var '$s' is already defined", name) );
		}
		
		vars.put( name, Empty.EMPTY );
		
		final RegLine<T> regLine = new RegLine<>(varReturn, function, parameters);
		
		queue.add( regLine );	
		
		lastIVar = varReturn;
		
		return this;
	}

	@Override
	public <T> Monad<T> yield(IMapper<T> function, Object[] parameters) {
		
		final Monad<?> monad = recursiveConcat( queue );
		
		 return monad.map( s -> {
			 
			 updateVars( lastIVar, s);	
			 return function.exec( newParameters( parameters ) );
			
		 } );
		
	}
	
	private Monad<?> recursiveConcat( final BlockingQueue<RegLine<?>> queue  ) {
		
		final RegLine<?> regLine = takeOfQueue( queue );
		
		final Object[] odlParameters = regLine.getParameters();
		
		Monad<?> monad = regLine.getFunction().exec(  newParameters( odlParameters )  );
		
		return recursiveFlatMap( monad, regLine.getVarReturn(), queue );
		
	}
	
	private Monad<?> recursiveFlatMap( Monad<?> fromMonad, IVar iVarToUpdate, BlockingQueue<RegLine<?>> queue ) {
		
		
		if ( queue.isEmpty() ) {
			
			return fromMonad;		
			
		}
		
		final RegLine<?> regLine = takeOfQueue( queue );
		
		final Map<String, Object> copyVars = new ConcurrentHashMap<>( vars );
		
		final Object[] args = regLine.getParameters();
		
		int contParamsEmpty = countParametersEmpty( args, copyVars);
		
		if ( contParamsEmpty == 0 ) {
			
			return flatMapWhenParamsIsEmpty(fromMonad, iVarToUpdate, queue, regLine, args);
			
		} else if ( contParamsEmpty == 1 ) {
			
			return flatMapWhenParamsNotEmpty(fromMonad, iVarToUpdate, queue, regLine, args);
			
		} else {
			
			throw new RuntimeException( "More than one argument not yet assigned" );
			
		}
		
	}

	private Monad<?> flatMapWhenParamsIsEmpty(Monad<?> fromMonad, IVar iVarToUpdate,
			BlockingQueue<RegLine<?>> queue, final RegLine<?> regLine,
			final Object[] args) {
		final Monad<?> execution = regLine.getFunction().exec( newParameters( args ) );
		
		final Monad<?> newMonad = fromMonad.flatMap(
					s -> {
						updateVars( iVarToUpdate, s);
						return execution;
					}
				);
		
		return recursiveFlatMap( newMonad, regLine.varReturn, queue );
	}

	private Monad<?> flatMapWhenParamsNotEmpty(Monad<?> fromMonad, IVar iVarToUpdate,
			BlockingQueue<RegLine<?>> queue, final RegLine<?> regLine,
			final Object[] args) {
		
		Monad<?> newMonad = fromMonad.flatMap(
				s -> {
					updateVars( iVarToUpdate, s);
					return regLine.getFunction().exec( newParameters( args ) );
			}
		);
		
		if ( ! queue.isEmpty() ){
			
			newMonad = newMonad.flatMap(
					s -> {
						updateVars( regLine.getVarReturn(), s);
						return recursiveConcat( queue );
				}
			);
			
		}
		
		return newMonad;
	}

	private void updateVars(final IVar updateVar, Object s) {
		
		vars.put( updateVar.getName(), ( s != null ? s : Empty.NULL ) );
	}
	
	private int countParametersEmpty( final Object[] args, final Map<String, Object> copyVars ) {
		
		int cont = 0;
		
		if ( args == null ) {
			
			return cont;
		}
		
		for ( Object argument : args ) {
			
			boolean findEmpty = argument != null
								&& argument instanceof IVar;
			
			if ( ! findEmpty  ) {
				
				continue;
			}
			
			final String name = ( ( IVar ) argument ).getName();
			
			final Object value = copyVars.get( name );
			
			findEmpty = value != null
						&& value instanceof Empty;
			
			if ( ! findEmpty  ) {
				
				continue;
				
			}
			
			final Empty empty = ( Empty ) value;
								
			if ( Empty.EMPTY.equals( empty ) ) {
				
				cont++;
				
			}		
			
		}
		
		return cont;	
		
	}

	private RegLine<?> takeOfQueue( BlockingQueue<RegLine<?>> queue ) {
		
		try {
			
			 return queue.take();
			 
		} catch (InterruptedException e) {
			
			throw new RuntimeException( e );
		}
	}
	
	private class RegLine <T> {
		
		final IVar varReturn; 
		final IFunction<T> function;
		final Object[] parameters;
		public RegLine(
				IVar varReturn,
				IFunction<T> function,
				Object[] parameters) {
			super();
			this.varReturn = varReturn;
			this.function = function;
			this.parameters = parameters;
		}
		
		public IVar getVarReturn() {
			return varReturn;
		}
		
		public IFunction<T> getFunction() {
			return function;
		}
		
		public Object[] getParameters() {
			return parameters;
		}
		
	}
	

}
