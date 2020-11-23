package app.component.doc.entity;

import java.io.File;

public class Upload {
	private String uploadFileName;
	private String fileType;
	private String filePath;
	private File upload;
	private double uploadSize;
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadSize(double uploadSize) {
		this.uploadSize = uploadSize;
	}
	public double getUploadSize() {
		return uploadSize;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	
}