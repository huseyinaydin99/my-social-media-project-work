package com.huseyinaydin.fileupload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

@ManagedBean(name = "fileUpload3")
public class FileUpload3 {
	private Part uFile;

	public Part getuFile() {
		return uFile;
	}

	public void setuFile(Part uFile) {
		this.uFile = uFile;
	}

	public String getFileName(Part part) throws ServletException, IOException {
		String filename = "";
		try {
			Collection<Part> parts = this.getAllParts(part);
			List<Part> parts2 = new ArrayList<>();
			parts2.addAll(parts);
			for(int i = 0; i < parts2.size(); i++){
				Part partx = parts2.get(i);
				for (String cd : partx.getHeader("content-disposition").split(";")){
					if (cd.trim().startsWith("filename")) {
						filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
						return filename;
					}
				}
			}
			
		} catch (NullPointerException e) {

		}
		return "";
	}
	
	public static Collection<Part> getAllParts(Part part) throws ServletException, IOException {
	    HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    return request.getParts().stream().filter(p -> part.getName().equals(p.getName())).collect(Collectors.toList());
	}

}
