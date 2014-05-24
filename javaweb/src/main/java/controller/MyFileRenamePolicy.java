package controller;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class MyFileRenamePolicy implements FileRenamePolicy{


	@Override
	public File rename(File f) {
	        
	        //Get the parent directory path as in h:/home/user or /home/user
	        String parentDir = f.getParent( );
	      
	        //Get filename without its path location, such as 'index.txt'
	        String fname = f.getName( );
	      
	        //Get the extension if the file has one
	        String fileExt = "";
	        int i = -1;
	        
	        if(( i = fname.lastIndexOf(".")) != -1){
	      
	            fileExt = fname.substring(i);
	            fname = fname.substring(0,i);
	        }
	      
	        //add the timestamp
	        Calendar cal = Calendar.getInstance();
	        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss.SSS"); 
	        fname = date.format(cal.getTime());
	        //piece together the filename
	        fname = parentDir + System.getProperty("file.separator") + fname + fileExt;
	      
	        System.out.println("fname");
	        File temp = new File(fname);

	         return temp;
	    }

	}
