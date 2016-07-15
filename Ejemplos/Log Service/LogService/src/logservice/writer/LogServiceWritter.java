package logservice.writer;

import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Deactivate;

@Component
public class LogServiceWritter {

	@Activate
	void activate(BundleContext bundleContext) {
		ServiceTracker logServiceTracker = new ServiceTracker(bundleContext, LogService.class.getName(), null);
		logServiceTracker.open();
		
		LogService logService = (LogService) logServiceTracker.getService();
		
		if(logService != null)
	        logService.log(LogService.LOG_INFO, "[" + this.getClass().getName() + "] Testing the logServiceWritter!");
	
	}

	@Deactivate
	void deactivate() {
	}

}
