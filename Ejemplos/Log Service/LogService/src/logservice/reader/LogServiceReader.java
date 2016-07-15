package logservice.reader;


import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Deactivate;

@Component
public class LogServiceReader {

	@Activate
	void activate(BundleContext bundleContext) {
		ServiceTracker logServiceTracker = new ServiceTracker(bundleContext, LogService.class.getName(), null);
		logServiceTracker.open();
		
		LogService logService = (LogService) logServiceTracker.getService();
		
		ServiceReference ref = bundleContext.getServiceReference(org.osgi.service.log.LogReaderService.class.getName());
	    if (ref != null)
	    {
	      org.osgi.service.log.LogReaderService reader = (org.osgi.service.log.LogReaderService)bundleContext.getService(ref);
	      reader.addLogListener(new LogListenerImpl(logService));
	    }
	}

	@Deactivate
	void deactivate() {
		// TODO Auto-generated method stub
	}

}
