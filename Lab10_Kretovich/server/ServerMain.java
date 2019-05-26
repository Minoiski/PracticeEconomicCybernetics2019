import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Vector;

public class ServerMain {

	public static void main(String[] args) throws FileNotFoundException {
		try
		{
		Scanner in=new Scanner(new File("date.txt"));
			for(int g=0;g<7;g++)
				for(int h=0;h<5;h++)
					if(in.hasNextInt())
					{
						if (in.nextInt()==1)
							freeTime[g][h]=true;
						else
							freeTime[g][h]=false;
					}
		in.close();
		}
		catch(FileNotFoundException e){}
		try ( ServerSocket serv = new ServerSocket( Protocol.PORT  )) {
			System.err.println("initialized");
			while (true) {
				Socket sock = serv.accept();
				System.err.println(  sock.getInetAddress().getHostName() + " connected" );
				ServerThread server = new ServerThread(sock);
				server.start();
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
	
	static Object syncMap = 
			new Object();
	static TreeMap<String, ServerThread> users = 
			new TreeMap<String, ServerThread> ();
	static boolean[][] freeTime=new boolean[7][5];
}

class ServerThread extends Thread {
	
	private ObjectOutputStream 	os;
	private ObjectInputStream 	is;
	private InetAddress 		addr;
	
	private String userFullName;
	private String telephone;
	private Vector<String> complaints;
	
	private boolean[][] freeTime = new boolean[7][5];
	
	public ServerThread(Socket s) throws IOException {
		os = new ObjectOutputStream( s.getOutputStream() );
		is = new ObjectInputStream( s.getInputStream());
		addr = s.getInetAddress();
	}
	
	public void run() {
		
		try {
			while ( true ) {
				Message msg = ( Message ) is.readObject();
				switch ( msg.getID() ) {
			
					case Protocol.CMD_CONNECT:
						if ( !connect( (MessageConnect) msg )) 
							return;
						break;
						
					case Protocol.CMD_DISCONNECT:
						synchronized (ServerMain.syncMap) {
							ServerMain.users.put( userFullName, null );
						}
						return;
						
					case Protocol.CMD_LETTER:
						leter(( MessageLetter ) msg );
						break;
					
				}
			}	
		} catch (IOException e) {
			System.err.print("Disconnect...");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	boolean connect( MessageConnect msg ) throws IOException {
		userFullName = msg.userFullName;
		telephone=msg.telephone;
		
		ServerThread old = null;
		synchronized (ServerMain.syncMap) {
			old = ServerMain.users.get( userFullName );
		}
		if ( old == null )
		{
			synchronized (ServerMain.syncMap) {
				ServerMain.users.put( userFullName, this );
			}
			if(msg.complaint.equals("-"))
				os.writeObject( new MessageInfo(ServerMain.freeTime,false));
			else
				os.writeObject( new MessageInfo(ServerMain.freeTime,true));
			return true;
		} else {
			os.writeObject( new MessageInfo( 
				"User " + old.userFullName + " already connected"));
			return false;
		}
	}
	
	void leter( MessageLetter msg ) throws IOException {
		ServerThread th = null;
		synchronized (ServerMain.syncMap) {
			th = ServerMain.users.get( msg.usrFullName );
		}
		if ( th == null )
		{
			os.writeObject( new MessageLetterResult( 
					"User " + userFullName + " is not found" ));
		} else {
			synchronized ( th ) {
				if ( ServerMain.freeTime[msg.i][msg.j] == false ) {
					th.freeTime[msg.i][msg.j] = true;
					ServerMain.freeTime[msg.i][msg.j] = true;
				}
				else{
					os.writeObject( new MessageLetterResult( 
							"This time is alredy busy" ));
					return;
				}
			}
			PrintWriter out= new PrintWriter(new FileOutputStream("date.txt"));
			for(int g=0;g<7;g++)
				for(int h=0;h<5;h++)
					if(ServerMain.freeTime[g][h])
					{
						out.print(1);
						out.print(" ");
					}
					else
					{
						out.print(0);
						out.print(" ");
					}
			 out.flush();
			 out.close();
				os.writeObject( new MessageLetterResult());
		}
	}
		
	public void disconnect() {
		try {
			System.err.println( addr.getHostName() + " disconnected" );
			os.flush();
			os.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			this.interrupt();
		}
	}			
}

