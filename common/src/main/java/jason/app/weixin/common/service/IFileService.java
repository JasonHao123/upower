package jason.app.weixin.common.service;

import java.io.File;

import jason.app.weixin.common.model.FileInfo;
import jason.app.weixin.common.model.FileItem;

public interface IFileService {

	FileItem saveFile(FileItem file);

	FileInfo createThumbnail(FileInfo media) throws Exception;

}
