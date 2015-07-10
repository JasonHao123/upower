package jason.app.weixin.web.service;

public interface IRule extends Comparable<IRule>{
	public abstract String check();
	public abstract Integer getOrder();
}
