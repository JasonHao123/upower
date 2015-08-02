package jason.app.weixin.social.translator;

import java.util.Arrays;

import jason.app.weixin.social.entity.SettingsImpl;
import jason.app.weixin.social.model.Settings;
import jason.app.weixin.social.util.ArrayUtil;

public class SettingsTransaltor {

	public static Settings toDTO(SettingsImpl impl) {
		// TODO Auto-generated method stub
		
		Settings settings = new Settings();
		settings.setId(impl.getId());
		settings.setProfileVisibility(impl.getProfileVisibility());
		settings.setPersonalCircal(impl.getPersonalCircal());
		settings.setGroupCircle(impl.getGroupCircle());
		settings.setAcceptMessageTypes(ArrayUtil.toLongArray(impl.getAcceptMessageTypes()));
		return settings;
	}

	public static SettingsImpl toEntity(Settings impl) {
		SettingsImpl settings = new SettingsImpl();
		settings.setId(impl.getId());
		settings.setProfileVisibility(impl.getProfileVisibility());
		settings.setPersonalCircal(impl.getPersonalCircal());
		settings.setGroupCircle(impl.getGroupCircle());
		settings.setAcceptMessageTypes(Arrays.toString(impl.getAcceptMessageTypes()));
		return settings;
	}

}
