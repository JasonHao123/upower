package jason.app.weixin.neo4j.job.quartz;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.quartz.Scheduler;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Trigger {
	String value() default "";
	String name() default "";
	int delay() default 5;
	String group() default Scheduler.DEFAULT_GROUP;
	long interval() default -1; 
	long repeatCount() default -1;
	String cron() default "";
	boolean requestsRecovery() default false;
	String description() default "";
}
