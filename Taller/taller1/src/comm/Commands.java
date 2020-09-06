package comm;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Calendar;

public class Commands{

	public Commands() {
		
	}
	
	public String Comandos(String comando) {
		String respuesta = "";
		try {
			if(comando.equalsIgnoreCase("remoteipconfig")){
				InetAddress myA = InetAddress.getLocalHost();
				respuesta = myA.getHostAddress();
			}else if(comando.equalsIgnoreCase("interface")) {
				InetAddress myA = InetAddress.getLocalHost();
				NetworkInterface net = NetworkInterface.getByInetAddress(myA);
				respuesta= net.getName();
				
			}else if(comando.equalsIgnoreCase("rtt")) {
				respuesta = "mensaje de 1024 bytes ha sido enviado al cliente";
			}else if(comando.equalsIgnoreCase("whattimeisit")) {
				Calendar calendario = Calendar.getInstance();
				respuesta = calendario.getTime().toString();
			}else {
				respuesta = "comando erroneo";
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return respuesta;
		
	}

}
