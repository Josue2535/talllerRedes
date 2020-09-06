package app;

import comm.TCPConnection;

public class Application implements comm.OnMessageListener{
	
	private TCPConnection connection;
	
	public Application() {
			
		connection = new TCPConnection();
		connection.setPuerto(5000);
		connection.setListener(this);
		
	}
	
	public void init() {
		connection.start();
	}

	
	
	
	@Override
	public void OnMessage(String msg) {
		System.out.println("Respuesta [servidor]: "+msg);
	}

	
	

	
	

}