package com.logicaalternativa.forcomprehensions.poc.dummy;

import java.util.Scanner;

import com.logicaalternativa.forcomprehensions.option.AlterOption;

public class LanguageIOImpure implements LanguageIO {
	
	public LanguageIOImpure() {		
		
	}
	
	/* (non-Javadoc)
	 * @see com.logicaalternativa.forcomprehensions.poc.dummy.LanguageIO#read()
	 */
	@Override
	public  AlterOption<String> read(){

		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Escribe: ");
		
		String next = scanner.nextLine();
		
		return AlterOption.some( next ); 
		
		
	}	
	
	/* (non-Javadoc)
	 * @see com.logicaalternativa.forcomprehensions.poc.dummy.LanguageIO#echo(java.lang.String)
	 */
	@Override
	public AlterOption<String> echo( String message ){
		
		System.out.println( message );
		
		return AlterOption.some( message ); 
		
		
	}
}
