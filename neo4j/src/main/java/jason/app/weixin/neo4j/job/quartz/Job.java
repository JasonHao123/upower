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
public @interface Job {
	String value() default "";
	String name() default "";
	String group() default Scheduler.DEFAULT_GROUP;
	boolean durability() default true; 
	boolean requestsRecovery() default false;
	String description() default "";
}
