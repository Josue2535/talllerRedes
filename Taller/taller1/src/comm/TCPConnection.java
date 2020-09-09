package comm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;

public class TCPConnection extends Thread {

	private OnMessageListener listener;
	private ServerSocket server;
	private Socket socket;
	private int puerto;

	public void setListener(OnMessageListener listener) {
		this.listener = listener;
	}

	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}

	@Override
	public void run() {
		try {

			
			server = new ServerSocket(puerto);

			System.out.println("Esperando un cliente");
			socket = server.accept();
			System.out.println("Cliente conectado");

			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);

			OutputStream os = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);

			while (true) {
				
				String msg = br.readLine();
				System.out.println(msg);
				
				if (msg.equals("rtt") ) {
					String p1024 = br.readLine() + "\n";
					System.out.println(p1024);
					bw.write(p1024);
					bw.flush();

					listener.OnMessage("Palabra reenviada exitosamente <3" );

				} else if(msg.equals("speed")){
					String p1024 = br.readLine() + "\n";
					System.out.println(p1024);
					bw.write(p1024);
					bw.flush();

					listener.OnMessage("Palabra reenviada exitosamente :3");
					
					
				}if(msg.equalsIgnoreCase("remoteipconfig")){
					InetAddress myA = InetAddress.getLocalHost();
					
					bw.write(myA.getHostAddress() + "\n");
					bw.flush();

					listener.OnMessage(myA.getHostAddress());
				}else if(msg.equalsIgnoreCase("interface")) {
					InetAddress myA = InetAddress.getLocalHost();
					NetworkInterface net = NetworkInterface.getByInetAddress(myA);
					bw.write(net.getName()+ "\n");
					bw.flush();
					listener.OnMessage(net.getName());
					
				}else if(msg.equalsIgnoreCase("whattimeisit")) {
					Calendar calendario = Calendar.getInstance();
					bw.write(calendario.getTime().toString()+ "\n");
					bw.flush();
					listener.OnMessage(calendario.getTime().toString());
				}else {
					bw.write("comando equivocado :c" + "\n");
					bw.flush();
					listener.OnMessage("comando erroneo");
				}
				
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
