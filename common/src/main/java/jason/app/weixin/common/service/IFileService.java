package jason.app.weixin.common.service;

import java.io.File;
import java.util.Date;
import java.util.List;

import jason.app.weixin.common.model.FileInfo;
import jason.app.weixin.common.model.FileItem;

public interface IFileService {

	FileItem saveFile(FileItem file);

	FileInfo createThumbnail(FileInfo media) throws Exception;

	List<FileItem> findImagesByUser(Long id, Date time, Date date);

}
