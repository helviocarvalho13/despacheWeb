package com.algaworks.despacheweb.uploadFile;

import java.io.InputStream;
import java.util.regex.Pattern;

public class DefaultUploadedFile implements UploadFile{
	
	  static final Pattern REGEX_REMOVE_SLASHES = Pattern.compile(".*(?:\\\\|\\/)(.+)$");

	    private final String contentType;

	    private final String fileName;

	    private final String completeFileName;

	    private final InputStream content;

	    private final long size;
	    
	    public DefaultUploadedFile(InputStream content, String completeFileName,
	        String contentType, long size) {
	        this.content = content;
	        this.fileName = REGEX_REMOVE_SLASHES.matcher(completeFileName).replaceAll("$1");
	        this.completeFileName = completeFileName;
	        this.contentType = contentType;
	        this.size = size;
	    }

	    @Override
	    public String toString() {
	        return "[uploadedFile uploadedCompleteName="
	            + this.completeFileName + " uploadedName=" + this.fileName
	            + " contentType=" + this.contentType + "]";
	    }

	    public String getContentType() {
	        return this.contentType;
	    }

	    public InputStream getFile() {
	        return content;
	    }

	    public String getFileName() {
	        return this.fileName;
	    }

	    public String getCompleteFileName() {
	        return this.completeFileName;
	    }

	    public long getSize() {
	        return size;
	    }

}
