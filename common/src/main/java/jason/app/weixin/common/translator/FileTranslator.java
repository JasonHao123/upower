package jason.app.weixin.common.translator;

import jason.app.weixin.common.entity.FileImpl;
import jason.app.weixin.common.model.FileItem;

public class FileTranslator {

	public static FileImpl toEntity(FileItem file) {
		// TODO Auto-generated method stub
		FileImpl impl = new FileImpl();
		impl.setCreateDate(file.getCreateDate());
		impl.setThumbnail(file.getThumbnail());
		impl.setUrl(file.getUrl());
		impl.setUserId(file.getUserId());
		return impl;
	}

}
