package com.logicaalternativa.forcomprehensions.poc.dummy;

import com.logicaalternativa.futures.Monad;

public interface LanguageIO {

	public abstract Monad<String> read();

	public abstract Monad<String> echo(String message);

}