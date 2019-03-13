package com.baoviet.agency.schedule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class FileService {

	
	@Value("${spring.upload.folder-upload}")
	private String folderUpload;
    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     * </p>
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeFile() {
    	File folder = new File(folderUpload);
    	File[] listOfFiles = folder.listFiles();
    	Date dateNow = new Date();
    	for (int i = 0; i < listOfFiles.length; i++) {
    	  if (listOfFiles[i].isFile()) {
    	    System.out.println("File " + listOfFiles[i].getName());
    	    String strDate = listOfFiles[i].getName().substring(0, 13);
    	    Date dateStr = new Date(Long.parseLong(strDate));
    	    if (dateNow.compareTo(dateStr) > 0) {
    	    	listOfFiles[i].delete();	
    	    }
    	  } else if (listOfFiles[i].isDirectory()) {
    	    System.out.println("Directory " + listOfFiles[i].getName());
    	  }
    	}
    }
    
//    public static void main(String[] args)
//    {	
//	File folder = new File("D:\\programme\\upload_temp");
//	File[] listOfFiles = folder.listFiles();
//	for (File file : listOfFiles) {
//		System.out.println("Before Format : " + file.lastModified());
//    	
//		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
//			
//		System.out.println("After Format : " + sdf.format(file.lastModified()));
//	}
//    }
    
//    public static void main(String[] args) throws IOException {
//
////        File file = new File("D:\\programme\\upload_temp");
//        
//
//        File folder = new File("D:\\programme\\upload_temp");
//    	File[] listOfFiles = folder.listFiles();
//    	
//    	for (File file : listOfFiles) {
//    		Path filePath = file.toPath();
//    		BasicFileAttributes attributes = null;
//    		attributes = Files.readAttributes(filePath, BasicFileAttributes.class);
//    		
//    		long milliseconds = attributes.creationTime().to(TimeUnit.MILLISECONDS);
//            if((milliseconds > Long.MIN_VALUE) && (milliseconds < Long.MAX_VALUE))
//            {
//                Date creationDate =
//                        new Date(attributes.creationTime().to(TimeUnit.MILLISECONDS));
//
//                System.out.println("File " + filePath.toString() + " created " +
//                        creationDate.getDate() + "/" +
//                        (creationDate.getMonth() + 1) + "/" +
//                        (creationDate.getYear() + 1900));
//            }
//		}
//        
//    }

}
