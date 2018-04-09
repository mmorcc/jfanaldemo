package com.demo;

import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileController extends Controller {

	public void index() {
		render("index.html");
	}

	public void downfile() {

		File file = new File("D:\\file\\test.jpg");

		if (file.exists()) {
			renderFile(file);
		} else {
			renderJson();
		}
	}

	public void uploadFile() {

		UploadFile uploadFile = this.getFile();

		String fileName = uploadFile.getOriginalFileName();

		File file = uploadFile.getFile();
		FileService fs = new FileService();
		File t = new File("D:\\file2\\" + UUID.randomUUID().toString()
				+ file.getName().substring(file.getName().lastIndexOf(".")));
		try {
			t.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		fs.fileChannelCopy(file, t);
		file.delete();
		this.renderHtml("success,<a href=\"./\">back</a>");
	}

}
