import java.io.Serializable;

//--- CMD_LETTER
//Client send MessageLetter to Server:
//
public class MessageLetter extends Message implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public String usrFullName;
	public int i;
	public int j;
	
	public MessageLetter( String usrFullName, int i,int j ) {
		
		super( Protocol.CMD_LETTER );
		this.usrFullName = usrFullName;
		this.i = i;
		this.j = j;
	}
	
}