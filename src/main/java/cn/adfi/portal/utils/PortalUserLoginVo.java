package cn.adfi.portal.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class PortalUserLoginVo {
	private String username;
	private String password;
	private String userIP;
	private InetAddress userInetAddr;
	private String acName;
	private String nasIP;
	private InetAddress nasInetAddr;
	
	
	private byte[] challenge;
	private byte[] chappassword;
	private int serialNo;
	private int reqID;
	private int errCode;
	private String errID; 
	private String message;	
	private String auditIp;
	private String acctSessionId;
	
	public byte[] getChappassword() {
		return chappassword;
	}

	public void setChappassword(byte[] chappassword) {
		this.chappassword = chappassword;
	}

	public byte[] getChallenge() {
		return challenge;
	}

	public void setChallenge(byte[] challenge) {
		this.challenge = challenge;
	}

	public int getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	public int getReqID() {
		return reqID;
	}

	public void setReqID(int reqID) {
		this.reqID = reqID;
	}

	public String getUserIP() {
		return userIP;
	}

	public void setUserIP(String userIP) throws UnknownHostException {
		this.userInetAddr = InetAddress.getByName(userIP);
		this.userIP = userIP;
	}
	
	public InetAddress getUserInetAddr(){
		return userInetAddr;
	}

	public String getAcName() {
		return acName;
	}

	public void setAcName(String acName) {
		this.acName = acName;
	}

	public String getNasIP() {
		return nasIP;
	}

	public void setNasIP(String nasIP) throws UnknownHostException {
		this.nasInetAddr = InetAddress.getByName(nasIP);
		this.nasIP = nasIP;
	}

	public InetAddress getNasInetAddr(){
		return nasInetAddr;
	}	
	
	public String getUsername(){
		return username;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getErrID() {
		return errID;
	}

	public void setErrID(String errID) {
		this.errID = errID;
	}

	public String getAuditIp() {
		return auditIp;
	}

	public void setAuditIp(String auditIp) {
		this.auditIp = auditIp;
	}

	public String getAcctSessionId() {
		return acctSessionId;
	}

	public void setAcctSessionId(String acctSessionId) {
		this.acctSessionId = acctSessionId;
	}
	
	public void setPassword(String password){
		this.password = password;
	}

	public String getMessage(){
		return message;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
	
    public static String byteHEX(byte ib) {
        char[] Digit = { '0','1','2','3','4','5','6','7','8','9',
        'A','B','C','D','E','F' };
        char [] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0F];
        ob[1] = Digit[ib & 0X0F];
        String s = new String(ob);
        return s;
	}
    
    public static String byte2hex(byte[] b){
		StringBuffer ret=new StringBuffer("");
		if( b != null ){
			for(int i=0;i<b.length;i++){
				ret.append(byteHEX(b[i]));
			}
		}
		return ret.toString();
	}
    
	private static char toHexLow(byte b)
	{
		if(b > 16 || b < 0 )
		{
			throw new IllegalArgumentException("inpt parameter should less than 16 and greater than 0");
		}
		if(b < 10){
			return (char)('0' + (char)b);
		}else{
			return (char)('A' + (b-10));
		}
    }
    
    public static StringBuffer toHex(int val)
    {
     StringBuffer buf = toHex((byte)(val >>24 & 0xff)).append(toHex((byte)(val>>16&0xff)));
     return buf.append(toHex((byte)(val>>8&0xff))).append(toHex((byte)(val & 0xff)));
    }
      
    public static byte[] hex2byte( String hexs ){
    	int bytelen = hexs.length()/2;
    	byte[] ret = new byte[bytelen];
    	
    	
    	return ret;
    }
	public void showParams(){
		System.out.println("\ncountent:"+
				"\nusername      :" + username +
				"\npassword	     :" + password +
				"\nuserIP        :" + userIP +
				"\nacName        :" + acName +
				"\nnasIP         :" + nasIP +
				"\nchallenge     :" + byte2hex(challenge) +
				"\nserialNo      :" + serialNo +
				"\nreqID         :" + reqID +
				"\nerrcode       :" + errCode +
				"\nerrID         :" + errID +
				"\nacctSessionId         :" + acctSessionId +
				"\nmessage       :" + message );
	}
}