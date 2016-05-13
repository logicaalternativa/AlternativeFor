package com.logicaalternativa.forcomprehensions;


public interface IMapper<T> {

	T exec( Object...params );
	
}
