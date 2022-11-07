package com.polysocial.entity;

import org.springframework.web.multipart.MultipartFile;

public class FileSave {
	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
}