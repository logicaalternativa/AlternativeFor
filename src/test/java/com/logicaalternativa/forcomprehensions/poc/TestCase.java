package com.logicaalternativa.forcomprehensions.poc;

import static com.logicaalternativa.forcomprehensions.util.UtilFor.args;
import static com.logicaalternativa.forcomprehensions.util.UtilFor.function;
import static com.logicaalternativa.forcomprehensions.util.UtilFor.invoke;
import static com.logicaalternativa.forcomprehensions.util.UtilFor.mapper;
import static com.logicaalternativa.forcomprehensions.util.UtilFor.var;
import static com.logicaalternativa.forcomprehensions.util.UtilFor.fromMonad;
import static org.junit.Assert.assertEquals;

import java.util.concurrent.Executors;

import org.junit.Ignore;

import com.logicaalternativa.forcomprehensions.IFor;
import com.logicaalternativa.forcomprehensions.build.BuilderFor;
import com.logicaalternativa.forcomprehensions.build.BuilderFor.Type;
import com.logicaalternativa.forcomprehensions.option.AlterOption;
import com.logicaalternativa.forcomprehensions.poc.dummy.Dummy;
import com.logicaalternativa.forcomprehensions.poc.dummy.DummyImp;
import com.logicaalternativa.forcomprehensions.poc.dummy.LanguageIO;
import com.logicaalternativa.forcomprehensions.poc.dummy.LanguageIOImpure;
import com.logicaalternativa.futures.AlternativeFuture;
import com.logicaalternativa.futures.imp.AwaitAlternativeFuture;
import com.logicaalternativa.futures.util.activeobject.imp.BuilderActiveObject;

public class TestCase {

	@org.junit.Test
	public void test() throws Exception {
		
//		Dummy languaje = new DummyImp();
//		Object languaje = this;
		
		Dummy languaje = BuilderActiveObject
							.getInstance(Dummy.class)
							.withExecutor( Executors.newCachedThreadPool() )
							.withImplementation( new DummyImp() )
							.build();
								
				
		IFor interpreter = BuilderFor.getInstance(Type.PARALLEL);
		
		AlternativeFuture<Integer> integerFuture = (AlternativeFuture<Integer>) interpreter
		.line( 
			   var("cc"), 
			   function( String.class, invoke( languaje, "dummyConcat" ) ),
			   args( "cc", "cc" )
			 )
		.line( 
			   var("dd"), 
			   function( String.class, invoke( languaje, "dummyConcat" ) ),
			   args( "dd", "dd" )
			 )
		.line( 
			   var("ff"), 
			   function( String.class, invoke( languaje, "dummyConcat" ) ),
			   args( "ff", "ff" )
			 )
		.line( 
			   var("x"), 
			   function( String.class, invoke( languaje, "dummy" ) ),
			   args( "1" ) 
		 )
		.line( 
			   var("y"), 
			   function( String.class, invoke( languaje, "dummyConcat" ) ),
			   args( var("x"), "1" )
		)
		.line( 
			   var("aaaaaa"), 
			   function( String.class, invoke( languaje, "dummyConcat" ) ),
			   args( "aaaaa", "aaaaa" )
			 )
		.line( 
		   var("z"), 
		   function( String.class, invoke( languaje, "dummyConcat" ) ),
		   args( "oe", "oe" )
		 )
	    .yield( 
	    		mapper( Integer.class,  invoke( this, "dummyConvert") ),
	    		args(  var("y"), var("x"), var( "z" ) )
	    		
	     );
		
		
		Integer res = AwaitAlternativeFuture.result( integerFuture, 5000L );
		
		assertEquals(16, res.intValue() );
	}
	
	@org.junit.Test
	@Ignore
	public void testWithOption() throws Exception {
		
//		LanguageIO languaje = new LanguageIOPure();								
		LanguageIO languaje = new LanguageIOImpure();								
				
		IFor interpreter = BuilderFor.getInstace();
		
		AlterOption<String> stringOption = ( AlterOption<String> ) interpreter
				.line( 
						   var("x"), 
						   ( s ) -> languaje.read(),
						   null 
						 )
				.line( 
						   var("y"), 
						   ( s ) -> languaje.echo( s[0].toString() ),
						   args( var("x") ) 
						 )
			.yield( 
				( s ) -> s[0].toString(),
				args(  var("y") )
	    		
	     );
		
		
		String res = stringOption.get();
		
		assertEquals("message", res);
	}
	
	
	@org.junit.Test
	public void testJava8() throws Exception {
		
		Dummy languaje = new DummyImp();
		
		AlternativeFuture<String> resFuture = (AlternativeFuture<String>) BuilderFor
		.getInstace()
		.line( 
			   var("x"), 
			   s->  languaje.dummy( (String) s[0] ),
			   args( "1" ) 
			 )
	    .yield( 
	    		s  -> (( String )s[0]).equals("1") ? "OK" : "KO" ,
	    		args(  var("x") )
	    		
	     );
		
		
		Object res = AwaitAlternativeFuture.result( resFuture, 200L );
		
		assertEquals("OK", res.toString() );
	}
	
	@org.junit.Test
	public void testPararell() throws Exception {
		
		Dummy languaje = BuilderActiveObject
				.getInstance(Dummy.class)
				.withExecutor( Executors.newCachedThreadPool() )
				.withImplementation( new DummyImp() )
				.build();
		
		AlternativeFuture<String> future1 = languaje.dummy("1");
		AlternativeFuture<String> future2 = languaje.dummy("2");
		AlternativeFuture<String> future3 = languaje.dummy("oeoeoe");
		
		AlternativeFuture<Integer> integerFuture = (AlternativeFuture<Integer>) BuilderFor
		.getInstace()
		.line( 
			   var("x"), 
			   fromMonad( future1 ),
			   null
			 )
		.line( 
			   var("y"), 
			   fromMonad( future2 ),
			   null
		)
		.line( 
			   var("z"), 
			   fromMonad( future3 ),
			   null
			 )
	    .yield( 
	    		mapper(Integer.class,  invoke( this, "dummyConvert") ),
	    		args(  var("y"), var("x"), var( "z" ) )
	    		
	     );
		
		
		Integer res = AwaitAlternativeFuture.result(integerFuture, 5000L);
		
		assertEquals( 9, res.intValue() );
	}
	
	
	@org.junit.Test
	public void testError() throws Exception {
		
		Dummy languaje = BuilderActiveObject
				.getInstance(Dummy.class)
				.withExecutor( Executors.newCachedThreadPool() )
				.withImplementation( new DummyImp() )
				.build();
		
		final Exception exceptionExpected = new Exception( "Error" );

		AlternativeFuture<String> future2 = AlternativeFuture.failed( exceptionExpected );
		
		AlternativeFuture<Integer> integerFuture = (AlternativeFuture<Integer>) BuilderFor
		.getInstace()
		.line( 
			   var("x"), 
			   (s) -> languaje.dummy("1"),
			   null
			 )
		.line( 
			   var("y"), 
			   fromMonad( future2 ),
			   null
		)
		.line( 
			   var("z"), 
			   (s) -> languaje.dummy("oeoeoe"),
			   null
			 )
	    .yield( 
	    		mapper(Integer.class,  invoke( this, "dummyConvert") ),
	    		args(  var("y"), var("x"), var( "z" ) )
	    		
	     );
		
		
		try {
			
			AwaitAlternativeFuture.result(integerFuture, 5000L);
			
		} catch (Exception e) {
			
			assertEquals( exceptionExpected, e );
		}
		
	}
	
	
	private AlternativeFuture<String> dummy( String arg1 ){
		
		return AlternativeFuture.successful( arg1 ); 
		
		
	}	
	
	private AlternativeFuture<String> dummyConcat( String arg1, String arg2 ){
		
		return AlternativeFuture.successful( arg1.concat(arg2)); 
		
		
	}	

	
	private Integer dummyConvert( String arg1, String arg2, String arg3){
		
		return Integer.parseInt(arg1) + Integer.parseInt(arg2 ) + arg3.length(); 
		
		
	}

}
