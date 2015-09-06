package jason.app.weixin.common.service;

import jason.app.weixin.common.model.FileInfo;
import jason.app.weixin.common.model.FileItem;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface IFileService {

	FileItem saveFile(FileItem file);

	FileInfo createThumbnail(FileInfo media) throws Exception;

	FileInfo resizePhoto(FileInfo fileInfo) throws IOException;

	List<FileItem> findImagesByUser(Long id, Date start,
			Date end);

	List<FileItem> findFilesByUser(Long id, Date lastDay, Date date);

	List<FileItem> findVideosByUser(Long id, Date lastDay, Date date);

}
