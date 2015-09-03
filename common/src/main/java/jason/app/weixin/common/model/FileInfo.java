package jason.app.weixin.common.model;

import jason.app.weixin.common.constant.MediaType;

import java.io.File;

public class FileInfo {
	private File file;
	private String contentType;
	private String fileName;
	private MediaType mediaType;
	public MediaType getMediaType() {
		return mediaType;
	}
	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
