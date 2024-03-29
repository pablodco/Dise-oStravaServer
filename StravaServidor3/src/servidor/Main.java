package servidor;

import java.rmi.Naming;
import java.util.Calendar;
import java.util.Date;

import servidor.dominio.Actividad;
import servidor.dominio.Entrenamiento;
import servidor.dominio.MetodoLogin;
import servidor.remote.*;

public class Main {
	@SuppressWarnings({ "removal", "deprecation" })
	public static void main(String[] args) {	
		//Activate Security Manager. It is needed for RMI.
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		//args[0] = RMIRegistry IP
		//args[1] = RMIRegistry Port
		//args[2] = Service Name
		String name = "//" + args[0] + ":" + args[1] + "/" + args[2];		
		
		//Bind remote facade instance to a sirvice name using RMIRegistry
		try {
			IRemoteFacade remoteFacade = new RemoteFacade();			
			Naming.rebind(name, remoteFacade);
			System.out.println(" * strava Server v1 '" + name + "' started!!");
			remoteFacade.registro("pablodel@gmail.com","1","Pablo","2003/01/29",0, 0, 0, 0,"META");
			long token=remoteFacade.login("pablodel@gmail.com","1", "META");
			remoteFacade.crearEntrenamiento(token, 30,"Correr al aire libre","running",13,Calendar.getInstance().getTime());
			System.out.println(remoteFacade.aceptarReto(token, "Reto1"));
			System.out.println(remoteFacade.obtenerRetosActivos(token));
			System.out.println(remoteFacade.obtenerRetosDisponibles(token));
		} catch (Exception ex) {
			System.err.println(" # strava Server Exception: " + ex.getMessage());
			ex.printStackTrace();
		}
		
	}
}
