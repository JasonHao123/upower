package jason.app.weixin.neo4j.job.quartz;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

public class SpringSchedulerFactoryBean extends SchedulerFactoryBean {
	@Autowired(required=false)
	private List<QuartzJobBean> jobs;

	@Autowired
	private AutowireCapableBeanFactory factory;

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		// collect trigger list
		List<Trigger> triggers = new ArrayList<Trigger>();
		List<JobDetail> jobDetails = new ArrayList<JobDetail>();
		if(jobs!=null) {
		for (QuartzJobBean job : jobs) {
			Class<?> objClz = job.getClass();
			if (org.springframework.aop.support.AopUtils.isAopProxy(job)) {

				objClz = org.springframework.aop.support.AopUtils
						.getTargetClass(job);
			}
			jason.app.weixin.neo4j.job.quartz.Trigger trigger = null;
			jason.app.weixin.neo4j.job.quartz.Job job2 = null;
			if (objClz
					.isAnnotationPresent(jason.app.weixin.neo4j.job.quartz.Job.class)) {
				job2 = objClz
						.getAnnotation(jason.app.weixin.neo4j.job.quartz.Job.class);
			} 
			if (objClz
					.isAnnotationPresent(jason.app.weixin.neo4j.job.quartz.Trigger.class)) {
				trigger = objClz
						.getAnnotation(jason.app.weixin.neo4j.job.quartz.Trigger.class);
			} 
			if(trigger!=null && job2!=null) {
				String name = job2.name();
				if(!org.springframework.util.StringUtils.hasText(name) && org.springframework.util.StringUtils.hasText(job2.value())) {
					name = job2.value();
				}
				JobDetail jobDetail = createJobDetail(name,job2.group(),job2.durability(),job2.requestsRecovery(),job2.description(), objClz);
				Trigger triggerBean = null;
				if(trigger.interval()>0) {
					triggerBean = createSimpleTrigger(trigger.name(),trigger.group(),trigger.delay(),trigger.interval(),trigger.repeatCount(),trigger.requestsRecovery(),jobDetail);
				}else if(org.springframework.util.StringUtils.hasText(trigger.cron())) {
					triggerBean = createCronTrigger(trigger.name(),trigger.group(),trigger.cron(),trigger.description(),jobDetail);
				}

				if(triggerBean!=null) {
					jobDetails.add(jobDetail);
					triggers.add(triggerBean);
				}
			}
		}
		}
		this.setJobDetails(jobDetails.toArray(new JobDetail[0]));
		this.setTriggers(triggers.toArray(new Trigger[0]));
		super.afterPropertiesSet();
	}
	

	private Trigger createCronTrigger(String name, String group,String cron,String description,
			JobDetail jobDetail) {
		Class<?> cronTriggerClass;
		Method jobKeyMethod;
		try {
			cronTriggerClass = ClassUtils.forName("org.quartz.impl.triggers.CronTriggerImpl", getClass().getClassLoader());
			jobKeyMethod = JobDetail.class.getMethod("getKey");
		}
		catch (ClassNotFoundException ex) {
			cronTriggerClass = CronTrigger.class;
			jobKeyMethod = null;
		}
		catch (NoSuchMethodException ex) {
			throw new IllegalStateException("Incompatible Quartz version");
		}
		BeanWrapper bw = new BeanWrapperImpl(cronTriggerClass);
		MutablePropertyValues pvs = new MutablePropertyValues();
		pvs.add("name", name);
		pvs.add("group", group);
		if (jobKeyMethod != null) {
			pvs.add("jobKey", ReflectionUtils.invokeMethod(jobKeyMethod, jobDetail));
		}
		else {
			pvs.add("jobName", "testJob");
			pvs.add("jobGroup", group);
		}

		pvs.add("startTime", new Date());
		pvs.add("cronExpression", cron);
		pvs.add("description", description);
		bw.setPropertyValues(pvs);
		return (CronTrigger) bw.getWrappedInstance();
	}


	private Trigger createSimpleTrigger(String name,String group,int delay, long interval, long count, boolean requestsRecovery, JobDetail jobDetail) {
		// TODO Auto-generated method stub
		Class<?> simpleTriggerClass;
		Method jobKeyMethod;
		try {
			simpleTriggerClass = ClassUtils.forName("org.quartz.impl.triggers.SimpleTriggerImpl", getClass().getClassLoader());
			jobKeyMethod = JobDetail.class.getMethod("getKey");
		}
		catch (ClassNotFoundException ex) {
			simpleTriggerClass = SimpleTrigger.class;
			jobKeyMethod = null;
		}
		catch (NoSuchMethodException ex) {
			throw new IllegalStateException("Incompatible Quartz version");
		}
		BeanWrapper bw = new BeanWrapperImpl(simpleTriggerClass);
		MutablePropertyValues pvs = new MutablePropertyValues();
		pvs.add("name", name);
		pvs.add("group", group);
		if (jobKeyMethod != null) {
			pvs.add("jobKey", ReflectionUtils.invokeMethod(jobKeyMethod, jobDetail));
		}
		else {
			pvs.add("jobName", "testJob");
			pvs.add("jobGroup", group);
		}

		pvs.add("repeatInterval", interval);
		pvs.add("repeatCount", count);
	//	pvs.add("requestsRecovery",requestsRecovery);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, delay);
		pvs.add("startTime", calendar.getTime());
		bw.setPropertyValues(pvs);
		return  (SimpleTrigger) bw.getWrappedInstance();
	}


	public JobDetail createJobDetail(String name,String group,boolean durability, boolean requestsRecovery,String desc, Class<?> jobClass) {

		Class<?> jobDetailClass;
		try {
			jobDetailClass = ClassUtils.forName("org.quartz.impl.JobDetailImpl", getClass().getClassLoader());
		}
		catch (ClassNotFoundException ex) {
			jobDetailClass = JobDetail.class;
		}
		BeanWrapper bw = new BeanWrapperImpl(jobDetailClass);
		MutablePropertyValues pvs = new MutablePropertyValues();
		pvs.add("name", name);
		pvs.add("group", group);
		pvs.add("jobClass", jobClass);
		pvs.add("jobDataMap", new JobDataMap());
		pvs.add("durability", durability);
		pvs.add("requestsRecovery", requestsRecovery);
		pvs.add("description", desc);
		bw.setPropertyValues(pvs);
		return  (JobDetail) bw.getWrappedInstance();
	}
}
