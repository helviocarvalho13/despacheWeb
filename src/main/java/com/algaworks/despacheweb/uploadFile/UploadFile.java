package com.algaworks.despacheweb.uploadFile;

import java.io.InputStream;

public interface UploadFile {

	String getContentType();
	
	InputStream getFile();
	
	String getFileName();
	
	long getSize();
}
