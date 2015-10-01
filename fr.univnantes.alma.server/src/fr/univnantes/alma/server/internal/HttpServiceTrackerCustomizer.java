package fr.univnantes.alma.server.internal;

import javax.servlet.ServletException;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class HttpServiceTrackerCustomizer implements ServiceTrackerCustomizer<HttpService, HttpService> {
	
	private BundleContext bundleContext;
	
	private HttpService httpService;

	public HttpServiceTrackerCustomizer(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	@Override
	public HttpService addingService(ServiceReference<HttpService> reference) {
		this.httpService = this.bundleContext.getService(reference);
		
		if (httpService != null) {
			try {
				System.out.println("Registering the servlet");
				httpService.registerServlet("/api", new UserServlet(), null, null);
			} catch (ServletException | NamespaceException e) {
				e.printStackTrace();
			}
		}
		
		return httpService;
	}

	@Override
	public void modifiedService(ServiceReference<HttpService> reference, HttpService service) {
		
	}

	@Override
	public void removedService(ServiceReference<HttpService> reference, HttpService service) {
		this.httpService = null;
	}
	
	public void unregister() {
		if (this.httpService != null) {
			System.out.println("Unregistering the servlet");
			this.httpService.unregister("/api");
		}
	}

}
