package fr.univnantes.alma.server.internal;

import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.equinox.http.jetty.JettyConfigurator;
import org.eclipse.equinox.http.jetty.JettyConstants;

public class Application implements IApplication {
	
	private IApplicationContext context;

	@Override
	public Object start(IApplicationContext context) throws Exception {
		System.out.println("Starting the application");
		
		this.context = context;
		
		Dictionary<String, Object> properties = new Hashtable<String, Object>();
		properties.put(JettyConstants.HTTP_ENABLED, Boolean.TRUE);
		properties.put(JettyConstants.HTTP_HOST, "localhost");
		properties.put(JettyConstants.HTTP_PORT, "8080");
		
		try {
			JettyConfigurator.startServer("ALMAServer", properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return IApplicationContext.EXIT_ASYNC_RESULT;
	}

	@Override
	public void stop() {
		System.out.println("Stopping the application");
		try {
			JettyConfigurator.stopServer("ALMAServer");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.context.setResult(EXIT_OK, this);
	}

}
