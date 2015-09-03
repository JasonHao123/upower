package jason.app.weixin.common.service;

import jason.app.weixin.common.model.FileInfo;

public interface IAmazonS3Service {

	String saveFile(FileInfo media) throws Exception;

}
