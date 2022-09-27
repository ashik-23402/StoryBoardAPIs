package com.ashik.storyboard.services.implservices;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ashik.storyboard.services.FileService;

@Service
public class FileServicesImpl implements FileService {

	@Override
	public String UploadImage(String path, MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		
		if(file == null) {
			return "null";
		}
		
		
		//filename
		
		String name = file.getOriginalFilename();
		//---aslike abs.png
		
		//random name generate
		
		String randomID= UUID.randomUUID().toString();
		String filename1 = randomID.concat(name.substring(name.lastIndexOf(".")));
		
		
		//fullpath
		
		String filepath = path + File.separator + filename1;
		
		
		//createfolder if not created
		
		File f = new File(path);
		
		if(!f.exists()) {
			f.mkdir();
		}
		
		
		Files.copy(file.getInputStream(), Paths.get(filepath));
		
		
		return filename1;
	}
	
	
	

	@Override
	public InputStream getResource(String path, String filename) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		String fullpath = path + File.separator + filename;
		InputStream is = new FileInputStream(fullpath);
		
		//db logic
		
		
		
		return is;
	}

}
