package jason.app.weixin.common.service;

import jason.app.weixin.common.model.FileInfo;
import jason.app.weixin.common.model.FileItem;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface IFileService {

	FileItem saveFile(FileItem file);

	FileInfo createThumbnail(FileInfo media) throws Exception;

	List<FileItem> findImagesByUser(Long id, Date time, Date date);

	FileInfo resizePhoto(FileInfo fileInfo) throws IOException;

}
