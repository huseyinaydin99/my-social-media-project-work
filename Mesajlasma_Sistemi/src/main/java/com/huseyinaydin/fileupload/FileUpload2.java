package com.huseyinaydin.fileupload;

import javax.faces.bean.ManagedBean;
import javax.servlet.http.Part;

@ManagedBean(name = "fileUpload2")
public class FileUpload2 {
	private Part uFile;

	public Part getuFile() {
		return uFile;
	}

	public void setuFile(Part uFile) {
		this.uFile = uFile;
	}

	public String getFileName(Part part) {
		String filename = "";
		try {
			for (String cd : part.getHeader("content-disposition").split(";"))
				if (cd.trim().startsWith("filename")) {
					filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
					return filename;
				}
			
		} catch (NullPointerException e) {

		}
		return "";
	}
}
