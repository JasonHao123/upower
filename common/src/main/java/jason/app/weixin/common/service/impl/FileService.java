package jason.app.weixin.common.service.impl;

import jason.app.weixin.common.constant.MediaType;
import jason.app.weixin.common.entity.FileImpl;
import jason.app.weixin.common.model.FileInfo;
import jason.app.weixin.common.model.FileItem;
import jason.app.weixin.common.repository.FileRepository;
import jason.app.weixin.common.service.IFileService;
import jason.app.weixin.common.translator.FileTranslator;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FileService implements IFileService {
	
	@Autowired
	private FileRepository fileRepo;

	@Override
	@Transactional
	public FileItem saveFile(FileItem file) {
		// TODO Auto-generated method stub
		FileImpl impl = FileTranslator.toEntity(file);
		impl = fileRepo.save(impl);
		file.setId(impl.getId());
		return file;
	}

	@Override
	public FileInfo createThumbnail(FileInfo media) throws Exception{
		// TODO Auto-generated method stub
		File output = File.createTempFile("weaktie_", ".tmp");
		if(MediaType.IMAGE==media.getMediaType()) {
			BufferedImage image = ImageIO.read(media.getFile());
			BufferedImage resizedImage = new BufferedImage(50, 50, BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D g = resizedImage.createGraphics();
			g.drawImage(image, 0, 0, 50, 50, null);
			g.dispose();
			g.setComposite(AlphaComposite.Src);
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			ImageIO.write(resizedImage, "jpg", output);
			FileInfo info = new FileInfo();
			info.setContentType("image/jpeg");
			info.setFile(output);
			if(media.getFileName()!=null) {
				info.setFileName(media.getFileName().replace(".", "_")+".jpg");
			}else {
				info.setFileName(DigestUtils.md5Hex(output.getPath())+".jpg");
			}
			info.setMediaType(media.getMediaType());
			return info;
		}
		return null;
	}

	@Override
	public List<FileItem> findImagesByUser(Long id, Date start, Date end) {
		// TODO Auto-generated method stub
		return FileTranslator.toDTO(fileRepo.findByUserIdAndCreateDateGreaterThanAndCreateDateLessThan(id,start,end));
		
	}

	@Override
	public FileInfo resizePhoto(FileInfo media) throws IOException {
		// TODO Auto-generated method stub
		BufferedImage image = ImageIO.read(media.getFile());
		if(image.getWidth()>600) {
			int scaledHeight = image.getHeight()*600/image.getHeight();
			BufferedImage resizedImage = new BufferedImage(600, scaledHeight, BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D g = resizedImage.createGraphics();
			g.drawImage(image, 0, 0, 600, scaledHeight, null);
			g.dispose();
			g.setComposite(AlphaComposite.Src);
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			ImageIO.write(resizedImage, "jpg", media.getFile());
		}
		return media;
	}

}
