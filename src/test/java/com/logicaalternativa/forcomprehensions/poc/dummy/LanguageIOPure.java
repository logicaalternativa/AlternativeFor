package com.logicaalternativa.forcomprehensions.poc.dummy;

import com.logicaalternativa.forcomprehensions.option.AlterOption;

public class LanguageIOPure implements LanguageIO {
	
	public LanguageIOPure() {		
		
	}
	
	/* (non-Javadoc)
	 * @see com.logicaalternativa.forcomprehensions.poc.dummy.LanguageIO#read()
	 */
	@Override
	public  AlterOption<String> read(){
		
		return AlterOption.some( "message" ); 
		
		
	}	
	
	/* (non-Javadoc)
	 * @see com.logicaalternativa.forcomprehensions.poc.dummy.LanguageIO#echo(java.lang.String)
	 */
	@Override
	public AlterOption<String> echo( String message ){
		
		return AlterOption.some( message); 
		
		
	}
}
