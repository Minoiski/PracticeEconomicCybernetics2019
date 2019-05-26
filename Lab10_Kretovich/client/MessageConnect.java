import java.io.Serializable;

//--- CMD_CONNECT
//Client send MessageConnect to Server:
//
public class MessageConnect extends Message implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public String userFullName;
	public String telephone;
	public String complaint;
	
	public MessageConnect(String userFullName,String telephone,String complaint ) {
		super( Protocol.CMD_CONNECT );
		this.userFullName = userFullName;
		this.telephone = telephone;
		this.complaint=complaint;
	}
}