package com.dobbydo;

import org.springframework.context.annotation.Configuration;

@Configuration
public class LibLoading {
	static {
		//System.setProperty("java.library.path", "C:\\Users\\Kait\\git\\dobbydo\\dobbydo\\libs");
		//System.loadLibrary( "opencv_java320.dll" );    
		//System.load( "C:\\Users\\Kait\\git\\dobbydo\\dobbydo\\libs\\opencv_java320.dll" );
	}
}
