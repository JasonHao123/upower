package jason.app.weixin.social.translator;

import jason.app.weixin.social.entity.AddFriendRequestImpl;
import jason.app.weixin.social.model.AddFriendRequest;
import jason.app.weixin.social.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class AddFriendRequestTranslator {

	public static List<AddFriendRequest> toDTO(
			Page<AddFriendRequestImpl> requests) {
		List<AddFriendRequest> result = new ArrayList<AddFriendRequest>();
		for(AddFriendRequestImpl impl:requests) {
			result.add(toDTO(impl));
		}
		return result;
	}

	public static AddFriendRequest toDTO(AddFriendRequestImpl impl) {
		AddFriendRequest user = new AddFriendRequest();
		user.setId(impl.getId());
		user.setFromUser(SocialUserTranslator.toDTO(impl.getFrom()));
		user.setMessage(impl.getMessage());
		user.setRating(impl.getRating());
		user.setFriendshipType(ArrayUtil.toLongArray(impl.getFriendType()));
		return user;
	}

}
