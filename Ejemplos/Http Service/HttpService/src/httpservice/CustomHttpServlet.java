package httpservice;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Deactivate;

@Component
public class CustomHttpServlet {

	private ServiceTracker httpTracker;

	// Acceder a la url http://localhost:8081/HttpServiceExample
	@Activate
	void activate(BundleContext bundleContext) {
		httpTracker = new ServiceTracker(bundleContext, HttpService.class.getName(), null) {
			public void removedService(ServiceReference reference, Object service) {
				try {
					((HttpService) service).unregister("/hello");
				} catch (IllegalArgumentException exception) {
				}
			}

			public Object addingService(ServiceReference reference) {
				HttpService httpService = (HttpService) this.context.getService(reference);
				try {
					httpService.registerServlet("/HttpServiceExample", new HttpServiceExample(), null, null);
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				return httpService;
			}
		};
		httpTracker.open();
	}

	@Deactivate
	void deactivate() {
		httpTracker.close();
	}

}
