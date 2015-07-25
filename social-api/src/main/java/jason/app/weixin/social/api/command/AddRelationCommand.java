package jason.app.weixin.social.api.command;

import jason.app.weixin.social.api.constant.CommandType;

public class AddRelationCommand implements ICommand{
	private ICommandReceiver receiver;
	private Long fromUser;
	private Long toUser;
	private Long[] types;
	private Float rating;
	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	@Override
	public CommandType getType() {
		// TODO Auto-generated method stub
		return CommandType.ADD_RELATION;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		receiver.createRelation(fromUser,toUser,types);
	}

	@Override
	public void setReceiver(ICommandReceiver receiver) {
		// TODO Auto-generated method stub
		this.receiver = receiver;
	}

	public Long getFromUser() {
		return fromUser;
	}

	public void setFromUser(Long fromUser) {
		this.fromUser = fromUser;
	}

	public Long getToUser() {
		return toUser;
	}

	public void setToUser(Long toUser) {
		this.toUser = toUser;
	}

	public Long[] getTypes() {
		return types;
	}

	public void setTypes(Long[] types) {
		this.types = types;
	}

	public ICommandReceiver getReceiver() {
		return receiver;
	}

}
