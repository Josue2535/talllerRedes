package comm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPConnection extends Thread {

	private OnMessageListener listener;
	private Commands comandos;
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

			comandos = new Commands();
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
				System.out.println("Escribe el comando :3");
				String msg = br.readLine() + "\n";
				bw.write(msg);
				bw.flush();
				if (msg.equals("rtt") || msg.equals("speed")) {
					String p1024 = br.readLine() + "\n";
					System.out.println(p1024);
					bw.write(p1024);
					bw.flush();

					listener.OnMessage("Palabra reenviada exitosamente");

				} else {

					bw.write(comandos.Comandos(msg)+"\n");
					listener.OnMessage(comandos.Comandos(msg));
					bw.flush();
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
