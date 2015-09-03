package jason.app.weixin.common.service.impl;

import jason.app.weixin.common.constant.MediaType;
import jason.app.weixin.common.model.FileInfo;
import jason.app.weixin.common.service.IAmazonS3Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

@Service
public class AmazonS3Service implements IAmazonS3Service,InitializingBean {
	@Value("#{ systemProperties['bucket'] }")
    private String bucket;
	
    private TransferManager tm;
    
//    private Calendar calendar = Calendar.getInstance();
    private DateFormat format = new SimpleDateFormat("yyyyMMdd");
    
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		 tm = new TransferManager(new EnvironmentVariableCredentialsProvider());        
		 tm.getAmazonS3Client().setEndpoint("s3.cn-north-1.amazonaws.com.cn");

	}

/**
    public boolean saveFile(Long id, String picUrl, String mimeType) {
		// TODO Auto-generated method stub
    	boolean isImage = false;
    	if(mimeType!=null && mimeType.startsWith("image/")) {
    		isImage = true;
    	}
    	String path = "/"+format.format(new Date())+"/";
    	if(isImage) {
    		 try {        	
    	        	BufferedImage image = ImageIO.read(new URL(picUrl));

    	        	PipedOutputStream output = new PipedOutputStream();
    	        	PipedInputStream input = new PipedInputStream(output);
    	        	try {
	    	        	Image target = null;
	    	        	if(image.getWidth()>600) {
	    	        		int scaledHeight =image.getHeight()*600/image.getWidth();
	    	        		target = image.getScaledInstance(600, scaledHeight, java.awt.Image.SCALE_SMOOTH);
	    	        	}else {
	    	        		target = image;
	    	        	}
	    	        	ImageIO.write(image, "jpg", output);
	    	        	path = path+"images/"+DigestUtils.md5Hex(picUrl)+".jpg";
    	        	}finally {
    	        		output.close();
    	        	}
    	        	
    	        	ObjectMetadata meta = new ObjectMetadata();
    	        	meta.setContentType("image/jpeg");
    	        	PutObjectRequest req = new PutObjectRequest(bucket, path, input,meta).withCannedAcl(CannedAccessControlList.PublicRead);
    	        	 Upload upload = tm.upload(req);
    	             System.out.println("Hello2");

    	             try {
    	             	// Or you can block and wait for the upload to finish
    	             	upload.waitForCompletion();
    	             	System.out.println("Upload complete.");
    	             	return true;
    	             } catch (AmazonClientException amazonClientException) {
    	             	System.out.println("Unable to upload file, upload was aborted.");
    	             	amazonClientException.printStackTrace();
    	             }
    	        	
    		 }catch(Exception e) {
    		 }
    		 
    	}
    	return false;
	}
*/

	@Override
	public String saveFile(FileInfo media) throws Exception{
		String path = getRootContextByMediaType(media.getMediaType())+format.format(new Date())+"/"+media.getFileName();		
		ObjectMetadata meta = new ObjectMetadata();
    	meta.setContentType(media.getContentType());
    	PutObjectRequest req = new PutObjectRequest(bucket, path, media.getFile()).withMetadata(meta).withCannedAcl(CannedAccessControlList.PublicRead);
    	 Upload upload = tm.upload(req);
         System.out.println("Hello2");

         try {
         	// Or you can block and wait for the upload to finish
         	upload.waitForCompletion();
         	System.out.println("Upload complete.");
         	return "http://s3.cn-north-1.amazonaws.com.cn/"+bucket+path;
         } catch (AmazonClientException amazonClientException) {
         	System.out.println("Unable to upload file, upload was aborted.");
         	amazonClientException.printStackTrace();
         }
         return null;
	}

private String getRootContextByMediaType(MediaType mediaType) {
	// TODO Auto-generated method stub
	switch(mediaType) {
	case FILE:
		return "/files/";
	case IMAGE:
		return "/images/";
	case VIDEO:
		return "/videos/";
	}
	return null;
}

	
}
