package cn.adfi.portal.utils;

import java.util.*;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class PortalPacket {
	final static int HEADER_SIZE = 16;
	final static int MAX_ATTR_LENGTH = 253;
	/*pkg type*/
	final static int TYPE_REQ_CHALLENGE = 0x01;
	final static int TYPE_ACK_CHALLENGE = 0x02;
	final static int TYPE_REQ_AUTH = 0x03;
	final static int TYPE_ACK_AUTH = 0x04;
	final static int TYPE_REQ_LOGOUT = 0x05;
	final static int TYPE_ACK_LOGOUT = 0x06;
	final static int TYPE_AFF_ACK_AUTH = 0x07;
	final static int TYPE_NTF_LOGOUT = 0x08;
	final static int TYPE_REQ_INFO = 0x09;
	final static int TYPE_ACK_INFO = 0x0a;
	final static int TYPE_REQ_MACBIND = 0x30;
	final static int TYPE_ACK_MACBIND = 0x31;
	
	/*attr type*/
	final static int ATTR_USERNAME = 0x01;
	final static int ATTR_PASSWORD = 0x02;
	final static int ATTR_CHALLENGE = 0x03;
	final static int ATTR_CHAPPASSWD = 0x04;
	final static int ATTR_ERRID = 0x05;
	final static int ATTR_USERMAC = 0x0b;
	final static int ATTR_SESSIONID = 0x41;
	final static int ATTR_AUDITIP = 0x42;
	/*auth type*/
	final static int AUTH_PAP=0x01;
	final static int AUTH_CHAP=0x00;
	
	
	private int version=1;
	private int pkgType;
	private int authType;
	private int resv;
	private int serialNo;
	private int reqID;
	private InetAddress userIP;
	private int userPort;
	private int errCode;
	private int attrNum;
	
	private int allAttrLength = 0;
	
	class PortalPacketAttr {
		public int type;
		public int length;
		public byte[] value;
	}
	
	private List<PortalPacketAttr> attrList = new ArrayList<PortalPacketAttr>();
	
	
	public void addAttr(PortalPacketAttr attr){
		attrList.add(attr);
		attrNum++;
		allAttrLength += attr.length;
	}
	
	public void addAttr(int type, byte[] value){
		
		if ( value.length > MAX_ATTR_LENGTH){
			return;
		}
		
		PortalPacketAttr attr = new PortalPacketAttr();
		attr.type = type;

		attr.length = value.length+2;
		attr.value = value;

		addAttr(attr);
	}
	

	
	public byte[] getAttr(int type){
		Iterator<PortalPacketAttr> itr = attrList.iterator();
		while(itr.hasNext()){
			PortalPacketAttr attr = itr.next();
			if(attr.type == type) {
				return attr.value;
			}
		}		
		return null;
	}
	
	public void delAttr(byte type){
		Iterator<PortalPacketAttr> itr = attrList.iterator();
		while(itr.hasNext()){
			PortalPacketAttr attr = itr.next();
			if(attr.type == type) {
				attrNum--;
				allAttrLength -= attr.length;
				attrList.remove(attr);
				break;
			}
		}
	}
	
	public byte[] getSerialization(){
		byte[] serialization = new byte[HEADER_SIZE + allAttrLength];
		
		serialization[0] = (byte)(version&0xff);
		serialization[1] = (byte)(pkgType&0xff);
		serialization[2] = (byte)(authType&0xff);
		serialization[3] = (byte)(resv&0xff);
		serialization[4] = (byte)(serialNo&0xff);
		serialization[5] = (byte)((serialNo>>8)&0xff);
		serialization[6] = (byte)(reqID&0xff);
		serialization[7] = (byte)((reqID>>8)&0xff);
		
		byte[] addr = userIP.getAddress();

		serialization[8] = addr[0];
		serialization[9] = addr[1];
		serialization[10] = addr[2];
		serialization[11] = addr[3];
		
		serialization[12] = (byte)(userPort&0xff);
		serialization[13] = (byte)((userPort>>8)&0xff);
		serialization[14] = (byte)(errCode&0xff);
		serialization[15] = (byte)(attrNum&0xff);
		
		int i=16;
		Iterator<PortalPacketAttr> itr = attrList.iterator();
		while(itr.hasNext()){
			PortalPacketAttr attr = itr.next();
			serialization[i] = (byte)(attr.type&0xff);
			i++;
			serialization[i] = (byte)(attr.length&0xff);
			i++;
			for(int j=0;j<attr.length-2;j++){
				serialization[i] = attr.value[j];
				i++;
			}
		}		
		return serialization;
	}

	public int getAttrNum() {
		return attrNum;
	}

	public void setAttrNum(int attrNum) {
		this.attrNum = attrNum;
	}

	public int getAllAttrLength() {
		return allAttrLength;
	}

	public void setAllAttrLength(int allAttrLength) {
		this.allAttrLength = allAttrLength;
	}

	public List<PortalPacketAttr> getAttrList() {
		return attrList;
	}

	public void setAttrList(List<PortalPacketAttr> attrList) {
		this.attrList = attrList;
	}

	public static int getHEADER_SIZE() {
		return HEADER_SIZE;
	}

	public static int getMAX_ATTR_LENGTH() {
		return MAX_ATTR_LENGTH;
	}

	public static int getTYPE_REQ_CHALLENGE() {
		return TYPE_REQ_CHALLENGE;
	}

	public static int getTYPE_ACK_CHALLENGE() {
		return TYPE_ACK_CHALLENGE;
	}

	public static int getTYPE_REQ_AUTH() {
		return TYPE_REQ_AUTH;
	}

	public static int getTYPE_ACK_AUTH() {
		return TYPE_ACK_AUTH;
	}

	public static int getTYPE_REQ_LOGOUT() {
		return TYPE_REQ_LOGOUT;
	}

	public static int getTYPE_ACK_LOGOUT() {
		return TYPE_ACK_LOGOUT;
	}

	public static int getTYPE_AFF_ACK_AUTH() {
		return TYPE_AFF_ACK_AUTH;
	}

	public static int getTYPE_NTF_LOGOUT() {
		return TYPE_NTF_LOGOUT;
	}

	public static int getTYPE_REQ_INFO() {
		return TYPE_REQ_INFO;
	}

	public static int getTYPE_ACK_INFO() {
		return TYPE_ACK_INFO;
	}

	public static int getTYPE_REQ_MACBIND() {
		return TYPE_REQ_MACBIND;
	}
	
	public static int getTYPE_ACK_MACBIND() {
		return TYPE_ACK_MACBIND;
	}
	
	public static int getATTR_USERNAME() {
		return ATTR_USERNAME;
	}

	public static int getATTR_PASSWORD() {
		return ATTR_PASSWORD;
	}

	public static int getATTR_CHALLENGE() {
		return ATTR_CHALLENGE;
	}

	public static int getATTR_CHAPPASSWD() {
		return ATTR_CHAPPASSWD;
	}
	
	public static int getATTR_USERMAC() {
		return ATTR_USERMAC;
	}
	
	public static int getAUTH_PAP() {
		return AUTH_PAP;
	}

	public static int getAUTH_CHAP() {
		return AUTH_CHAP;
	}

	public void setUserIP(String userIP) {
		try {
			this.userIP = InetAddress.getByName(userIP);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	public void setUserIP(byte[] userIP){
		try {
			this.userIP = InetAddress.getByAddress(userIP);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void setVersion(byte version) {
		this.version = version;
	}
	
	public void setVersion(int version) {
		setVersion((byte)version);
	}


	public void setPkgType(byte pkgType) {
		this.pkgType = pkgType;
	}
	
	public void setPkgType(int pkgType) {
		setPkgType((byte)pkgType);
	}


	public void setAuthType(byte authType) {
		this.authType = authType;
	}
	
	public void setAuthType(int authType) {
		setAuthType((byte)authType);
	}


	public void setResv(byte resv) {
		this.resv = resv;
	}
	
	public void setResv(int resv) {
		setResv((byte)resv);
	}
	
	public void setUserPort(int userPort){
		if (userPort > 65535) {
			return;
		} 
		this.userPort = userPort;
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

	public InetAddress getUserIP() {
		return userIP;
	}

	public void setUserIP(InetAddress userIP) {
		this.userIP = userIP;
	}

	public int getVersion() {
		return version;
	}

	public int getPkgType() {
		return pkgType;
	}

	public int getAuthType() {
		return authType;
	}

	public int getResv() {
		return resv;
	}

	public int getUserPort() {
		return userPort;
	}

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(byte errCode) {
		this.errCode = errCode;
	}
	
	public void setErrCode(int errcode) {
		setErrCode((byte)(errcode&0xff));
	}
	
	public static String bytes2HexString(byte []b){
		String ret = "packeg countent:";
		for( int i=0; i<b.length; i++){
			String hex = Integer.toHexString(b[i]&0xff);
			
			if(i%16 ==0){
				ret += ("\n	"+Integer.toHexString(i)+":");
				
			}
			if(hex.length()==1){
				hex = '0' + hex;
			}
			
			ret += hex.toUpperCase();
			
		}
		
		return ret;
	}
	
	public static int unsignedByte( byte b ){
		int ret = 0;
		ret = b>=0?b:256+b;
		return ret;
	}
	public static PortalPacket getPortalPacketByBytes(byte []buff) throws Exception{
		PortalPacket portalPacket = new PortalPacket();
		portalPacket.setVersion(buff[0]);
		portalPacket.setPkgType(buff[1]);
		portalPacket.setAuthType(buff[2]);
		portalPacket.setResv(buff[3]);
		
		int[] byte2 = new int[2];
		byte2[0] = unsignedByte(buff[4]);
		byte2[1] = unsignedByte(buff[5]);
		
		int temp = byte2[1]*256+byte2[0];//Integer.parseInt(byte2.toString());
		portalPacket.setSerialNo(temp);
		
		byte2[0] = unsignedByte(buff[6]);
		byte2[1] = unsignedByte(buff[7]);
		temp = byte2[1]*256+byte2[0];//Integer.parseInt(byte2.toString());
		portalPacket.setReqID(temp);
		
		byte[] byte4 = new byte[4];
		byte4[0] = buff[8];
		byte4[1] = buff[9];
		byte4[2] = buff[10];
		byte4[3] = buff[11];
		portalPacket.setUserIP(byte4);
		
		byte2[0] = unsignedByte(buff[12]);
		byte2[1] = unsignedByte(buff[13]);
		temp = byte2[1]*256+byte2[0];//Integer.parseInt(byte2.toString());
		portalPacket.setUserPort(temp);
		
		portalPacket.setErrCode(buff[14]);
		
		int attrNum = (int)buff[15];
		
		
		for(int i=0;i<attrNum;i++){
			
			byte type = buff[portalPacket.allAttrLength + HEADER_SIZE];
			int length = buff[portalPacket.allAttrLength + HEADER_SIZE + 1];
			if(length<=2){
				throw new Exception("portal package error! attrNum = "+attrNum+" i="+i+" length="+length
						+"pkgType="+portalPacket.getPkgType());
			}
			byte[] value = new byte[length-2];
			System.arraycopy(buff, portalPacket.allAttrLength + HEADER_SIZE+2, 
						value, 0, length-2);
			
			portalPacket.addAttr(type,value);
		}
		
		
		return portalPacket;
	}
	

	public static void main(String[] args){
		PortalPacket portalPkt = new PortalPacket();
		
		portalPkt.setAuthType(PortalPacket.AUTH_CHAP);
		portalPkt.setErrCode(0);
		portalPkt.setPkgType(PortalPacket.TYPE_ACK_CHALLENGE);
		portalPkt.setReqID(0xaa22);
		portalPkt.setResv(0);
		portalPkt.setSerialNo(0x22aa);
		portalPkt.setUserIP("192.168.1.3");
		portalPkt.setUserPort(123);
		portalPkt.setVersion(1);
		
		portalPkt.addAttr(PortalPacket.ATTR_USERNAME, ("abcdefge").getBytes());
		portalPkt.addAttr(PortalPacket.ATTR_PASSWORD, ("hijklmn").getBytes());
		
		
		String ser = PortalPacket.bytes2HexString(portalPkt.getSerialization());
		System.out.println(ser);
		
	}

	public static int getATTR_SESSIONID() {
		return ATTR_SESSIONID;
	}
}
