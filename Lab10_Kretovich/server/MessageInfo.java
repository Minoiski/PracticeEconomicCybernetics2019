import java.io.Serializable;


public class MessageInfo extends MessageResult implements Serializable {

	private static final long serialVersionUID = 1L;

	boolean[][] freeTime;
	public String apologies=null;
	
	public MessageInfo(String errorMessage ) { //Error
		super( Protocol.CMD_INFO, errorMessage );
	}
	public MessageInfo( boolean[][] freeTime ,boolean a) { // No errors
		super( Protocol.CMD_INFO );
		this.freeTime = freeTime;
		if(a)
			apologies="Your complaint was accepted";
	}
}
