package jason.app.weixin.neo4j.job.quartz;

import java.lang.reflect.Method;

import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ReflectionUtils;

public class SpringJobFactory implements JobFactory,ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler)
			throws SchedulerException {
		// TODO Auto-generated method stub
		try {
		Method getJobDetail = bundle.getClass().getMethod("getJobDetail");
		Object jobDetail = ReflectionUtils.invokeMethod(getJobDetail, bundle);
		Method getJobClass = jobDetail.getClass().getMethod("getJobClass");
		Class<?> jobClass = (Class<?>) ReflectionUtils.invokeMethod(getJobClass, jobDetail);
		return (Job) applicationContext.getBean(jobClass);
		}catch(Exception e) {
			
		}
		return null;
	}

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = context;
	}

}
