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
import java.util.Scanner;

public class TCPConnection extends Thread {

	private OnMessageListener listener;
	
	
	private Socket socket;

	public void setListener(OnMessageListener listener) {
		this.listener = listener;
	}

	

	@Override
	public void run() {
		try {

			
			socket = new Socket("127.0.0.1",5000);

			
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			Scanner sc = new Scanner(System.in);

			OutputStream os = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);

			while (true) {
				System.out.println("Escribe el comando :3");
				String msg = sc.nextLine() + "\n";
				bw.write(msg);
				bw.flush();
				if(msg.equals("rtt")) {
					long tiempo1 = System.nanoTime();
					byte[] p = new byte[1024];
					String p1024 = new String(p);
					bw.write(p1024+"\n");
					bw.flush();
					
					String res = br.readLine();
					long tiempo2 = System.nanoTime();
					if(res.length()==1024) {
						long trtt = tiempo2-tiempo1;
						System.out.println("servidor responde :3");
						listener.OnMessage("tiempo RTT : " + trtt+ " ns ");
					}
				}else if(msg.equalsIgnoreCase("speed")) {
					long t1 = System.currentTimeMillis();
					byte[] p = new byte[8192];
					String p8192 = new String(p);
					bw.write(p8192+"\n");
					bw.flush();
					String res = br.readLine();
					if(res.length()==8192) {
						long t2 = System.currentTimeMillis();
						long rtt = t2-t1;
						System.out.println("servidor responde");
						double aux = ((8192*2)/rtt)/1000.0;
						listener.OnMessage("velocidad de transmision = " + aux + " KB/s");
					}
				}else {
					
					System.out.println("servidor responde");
					String res = br.readLine();
					listener.OnMessage(res);
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
