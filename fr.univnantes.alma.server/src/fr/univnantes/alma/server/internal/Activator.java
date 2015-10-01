package fr.univnantes.alma.server.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator {
	
	private ServiceTracker<HttpService, HttpService> serviceTracker;
	
	private HttpServiceTrackerCustomizer httpServiceTrackerCustomizer;

	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("Starting the bundle");
		
		this.httpServiceTrackerCustomizer = new HttpServiceTrackerCustomizer(context);
		this.serviceTracker = new ServiceTracker<>(context, HttpService.class, httpServiceTrackerCustomizer);
		this.serviceTracker.open();
	}
	
	@Override
	public void stop(BundleContext context) throws Exception {
		System.out.println("Stopping the bundle");
		
		this.httpServiceTrackerCustomizer.unregister();
		this.serviceTracker.close();
	}
}
