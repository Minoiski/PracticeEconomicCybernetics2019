import java.io.Serializable;

//--- CMD_DISCONNECT
//Client send MessageDisconnect to Server:
//
public class MessageDisconnect extends Message implements Serializable {
		
	private static final long serialVersionUID = 1L;
	
	public MessageDisconnect() {
		super( Protocol.CMD_DISCONNECT );
	}
}

