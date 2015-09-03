package jason.app.weixin.common.translator;

import jason.app.weixin.common.entity.FileImpl;
import jason.app.weixin.common.model.FileItem;

import java.util.ArrayList;
import java.util.List;

public class FileTranslator {

	public static FileImpl toEntity(FileItem file) {
		// TODO Auto-generated method stub
		FileImpl impl = new FileImpl();
		impl.setCreateDate(file.getCreateDate());
		impl.setThumbnail(file.getThumbnail());
		impl.setUrl(file.getUrl());
		impl.setUserId(file.getUserId());
		impl.setContentType(file.getContentType());
		return impl;
	}

	public static List<FileItem> toDTO(
			List<FileImpl> items) {
		// TODO Auto-generated method stub
		List<FileItem> result = new ArrayList<FileItem>();
		for(FileImpl impl:items) {
			result.add(toDTO(impl));
		}
		return result;
	}

	private static FileItem toDTO(FileImpl file) {
		// TODO Auto-generated method stub
		FileItem impl = new FileItem();
		impl.setCreateDate(file.getCreateDate());
		impl.setThumbnail(file.getThumbnail());
		impl.setUrl(file.getUrl());
		impl.setUserId(file.getUserId());
		impl.setContentType(file.getContentType());
		return impl;
	}

}
