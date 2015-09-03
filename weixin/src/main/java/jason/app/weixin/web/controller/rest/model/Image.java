package jason.app.weixin.web.controller.rest.model;

public class Image {
    private String filelink="https://s3.cn-north-1.amazonaws.com.cn/weaktie/weak-ties.jpg";
    private String thumb="https://s3.cn-north-1.amazonaws.com.cn/weaktie/weak-ties.jpg";
    private String image="https://s3.cn-north-1.amazonaws.com.cn/weaktie/weak-ties.jpg";
    private String folder;
	public String getFilelink() {
		return filelink;
	}
	public void setFilelink(String filelink) {
		this.filelink = filelink;
	}
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}

}
