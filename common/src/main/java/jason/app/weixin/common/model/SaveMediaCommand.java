package jason.app.weixin.common.model;

import jason.app.weixin.common.constant.MediaType;

import java.io.Serializable;

public class SaveMediaCommand implements Serializable{
	public SaveMediaCommand(String mediaId,String thumbnailId,String openid) {
		this.mediaId = mediaId;
		this.thumbnailId = thumbnailId;
		this.openid = openid;
	}
	private String mediaId;
	private String thumbnailId;
	private String openid;
	private MediaType mediaType;
	
	
	public MediaType getMediaType() {
		return mediaType;
	}
	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getThumbnailId() {
		return thumbnailId;
	}
	public void setThumbnailId(String thumbnailId) {
		this.thumbnailId = thumbnailId;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
}
