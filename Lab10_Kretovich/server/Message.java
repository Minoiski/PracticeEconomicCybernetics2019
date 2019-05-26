import java.io.Serializable;

//Message base class:

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private byte id;
	public byte getID() {
		return id;
	}
	
	protected Message() {
		assert( false );
	}
	
	protected Message( byte id ) {
		
		assert( Protocol.validID( id )== true );
		
		this.id = id;
	}
}

//MessageResult base class:
//
class MessageResult extends Message implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int errorCode;
	public int getErrorCode() {
		return errorCode;
	}
	public boolean Error() {
		return errorCode != Protocol.RESULT_CODE_OK;
	}
	
	private String errorMessage;
	public String getErrorMessage() {
		return errorMessage;
	}
	
	protected MessageResult() {
		super();
	}
	
	protected MessageResult( byte id, String errorMessage ) {
		
		super( id );
		this.errorCode = Protocol.RESULT_CODE_ERROR;
		this.errorMessage = errorMessage;
	}

	protected MessageResult( byte id ) {
		
		super( id );
		this.errorCode = Protocol.RESULT_CODE_OK;
		this.errorMessage = "";
	}
}
