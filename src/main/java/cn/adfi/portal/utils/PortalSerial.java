package cn.adfi.portal.utils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.locks.ReentrantLock;

import cn.adfi.portal.utils.MD5;

import cn.adfi.portal.exceptions.UDPHandshakeFrameFailed;


public class PortalSerial {
	private DatagramSocket socket;
	private SocketAddress bindaddr;
	int serialNo;
	
	final static int MAX_SERIAL_NUM = 65535;
	private static int serialRoll = 1;
	private static byte[] serialNoFlagArr = new byte[MAX_SERIAL_NUM+1];
	private static ReentrantLock serialNoLock = new ReentrantLock();
	
	private static int getUnusedSerialNo(){
		serialNoLock.lock();
		int prevSerialRoll = serialRoll;
		serialRoll++;
		while(true){
			if(serialRoll>MAX_SERIAL_NUM){
				serialRoll = 1;
			}
			if(serialRoll == prevSerialRoll){
				break;//not find unused serial no.
			}
			
			if(serialNoFlagArr[serialRoll]!=1){
				serialNoFlagArr[serialRoll]=1;
				break;
			}
			serialRoll++;
		}
		serialNoLock.unlock();
		return serialRoll&0xffff;
	}
	
	private static void clearSerialNoFlag( int serialNo ){
		if(serialNo>MAX_SERIAL_NUM){
			return;
		}
		serialNoLock.lock();
		serialNoFlagArr[serialNo] = 0;
		serialNoLock.unlock();
	}
	
	private void portalSerialInit(){
		try {
			socket = new DatagramSocket(bindaddr);
			socket.setSoTimeout(20000);//2 seconds;
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		serialNo = getUnusedSerialNo();
	}
	
	protected void finalize(){
		clearSerialNoFlag(serialNo);
		socket.close();
	}
	
	public PortalSerial(SocketAddress bindaddr){
		this.bindaddr = bindaddr;
		portalSerialInit();
	}
	
	public PortalSerial(int port, InetAddress laddr){
		bindaddr = new InetSocketAddress(laddr, port);
		portalSerialInit();
	}
	
	public PortalSerial(int port, String lhost){
		try {
			InetAddress iladdr = InetAddress.getByName(lhost);
			bindaddr = new InetSocketAddress(iladdr, port);
			portalSerialInit();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class ChallengeProc extends UDPHandshakeFrame {
		PortalUserLoginVo params;
		PortalPacket reqPkt;
		
		public ChallengeProc(PortalUserLoginVo params){
			this.params = params;
			reqPkt = new PortalPacket();			
			reqPkt.setVersion(1);
			reqPkt.setPkgType(PortalPacket.TYPE_REQ_CHALLENGE);
			reqPkt.setAuthType(PortalPacket.AUTH_CHAP);
			reqPkt.setSerialNo(serialNo);
			
			params.setReqID(0);
			reqPkt.setReqID(0);
			
			reqPkt.setUserIP(params.getUserInetAddr());
		}
		
		@Override
		DatagramPacket getSendPacket() {
			byte[] se = reqPkt.getSerialization();
			return new DatagramPacket(se,se.length, params.getNasInetAddr(),2000);
		}

		@Override
		int procRecvPacket(DatagramPacket pkt) {
			PortalPacket rcv;
			try {
				rcv = PortalPacket.getPortalPacketByBytes(pkt.getData());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
			
			if (PortalPacket.getTYPE_ACK_CHALLENGE() !=  rcv.getPkgType()) {
				return 0;
			}
			
			params.setErrCode(rcv.getErrCode());
			params.setReqID(rcv.getReqID());
			params.setChallenge(rcv.getAttr(PortalPacket.ATTR_CHALLENGE));
			
			byte[] errID = rcv.getAttr(PortalPacket.ATTR_ERRID);
			if (null != errID) {
				params.setErrID(new String(errID));
			} else {
				params.setErrID(new String(""));
			}
			byte[] auditIp = rcv.getAttr(PortalPacket.ATTR_AUDITIP);
			if (null != auditIp) {
				params.setAuditIp(new String(auditIp));
			} else {
				params.setAuditIp(new String(""));
			}
			return 0;
		}
		
		public void doProc(){
			try{
				UDPHandshakeFrame.doFrameWork(socket, this, 0);
			}catch (UDPHandshakeFrameFailed e){
				params.setErrCode(1);
				params.setMessage(e.getMessage());
				params.setErrID("PT802");
			}
		}
	}
	
	public void doChallenge( PortalUserLoginVo params ){
		ChallengeProc chalProc= new ChallengeProc(params);
		chalProc.doProc();
	}
	
	class ChapAuthProc extends UDPHandshakeFrame {
		PortalUserLoginVo params;
		PortalPacket reqPkt;
		
		public ChapAuthProc(PortalUserLoginVo params){
			this.params = params;
			reqPkt = new PortalPacket();			
			reqPkt.setVersion(1);
			reqPkt.setPkgType(PortalPacket.TYPE_REQ_AUTH);
			reqPkt.setAuthType(PortalPacket.AUTH_CHAP);
			reqPkt.setSerialNo(serialNo);
			
			reqPkt.setReqID(params.getReqID());
			reqPkt.setUserIP(params.getUserInetAddr());
			
			MD5 md5 = new MD5();
			byte[] chapid = new byte[1];
			chapid[0] = (byte) (reqPkt.getReqID()>>8&0xff);//because java is big-endian
			md5.md5Update(chapid, chapid.length);
			byte[] password = params.getPassword().getBytes();
			md5.md5Update(password, password.length);
			byte[] challenge = params.getChallenge();
			md5.md5Update(challenge, challenge.length);
			md5.md5Final();
			byte[] chappasswd = md5.md5GetResult();
			
			reqPkt.addAttr(PortalPacket.ATTR_USERNAME,params.getUsername().getBytes());
			reqPkt.addAttr(PortalPacket.ATTR_CHAPPASSWD, chappasswd);
			
		}

		@Override
		DatagramPacket getSendPacket() {
			byte[] se = reqPkt.getSerialization();
			return new DatagramPacket(se,se.length, params.getNasInetAddr(),2000);
		}

		@Override
		int procRecvPacket(DatagramPacket pkt) {
			PortalPacket rcv;
			try {
				rcv = PortalPacket.getPortalPacketByBytes(pkt.getData());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
			if (PortalPacket.getTYPE_ACK_AUTH() !=  rcv.getPkgType()) {
				return 0;
			}
			
			String errid = "PT999";
			
			if(rcv.getErrCode()==3){
				return 1; 
			}
			params.setErrCode(rcv.getErrCode());
			
			if(rcv.getErrCode()==2){
				errid = "PT108";
				params.setMessage("用户已登录");
			}else if(rcv.getErrCode()!=0){
				params.setMessage("账号或密码错误");
				//params.setMessage(rcv.get);//使用portal返回的错误信息，有可能来自于radius server!
			}
			
			byte[] errID = rcv.getAttr(PortalPacket.ATTR_ERRID);
			if (null != errID) {
				errid = new String(errID);
			}
			params.setErrID(errid);
			
			byte[] auditIp = rcv.getAttr(PortalPacket.ATTR_AUDITIP);
			if (null != auditIp) {
				params.setAuditIp(new String(auditIp));
			} else {
				params.setAuditIp(new String(""));
			}
			byte[] acctSessionId = rcv.getAttr(PortalPacket.ATTR_SESSIONID);
			if (null != acctSessionId) {
				params.setAcctSessionId(new String(acctSessionId));
			} else {
				params.setAcctSessionId(new String(""));
			}
			return 0;
			
		}
		
		public void doProc(){
			try{
				UDPHandshakeFrame.doFrameWork(socket, this, 0);
			}catch (UDPHandshakeFrameFailed e){
				params.setErrCode(1);
				params.setMessage(e.getMessage());
			}
		}		
	}
	
	public void doChapAuthProc( PortalUserLoginVo params ){
		ChapAuthProc chalAuthProc= new ChapAuthProc(params);
		chalAuthProc.doProc();
	}
	
	public void doChapAuth(PortalUserLoginVo params){
		doChallenge(params);
		if(params.getErrCode()==0){
			doChapAuthProc(params);
		}
	}
	
	public void doPapAuth( PortalUserLoginVo params ){
	
	}
	
	class LogoffProc extends UDPHandshakeFrame{
		PortalUserLoginVo params;
		PortalPacket reqPkt;
		public LogoffProc(PortalUserLoginVo params){
			this.params = params;
			
			reqPkt = new PortalPacket();			
			reqPkt.setVersion(1);
			reqPkt.setPkgType(PortalPacket.TYPE_REQ_LOGOUT);
			reqPkt.setSerialNo(serialNo);
			reqPkt.setUserIP(params.getUserInetAddr());
		}
		@Override
		DatagramPacket getSendPacket() {
			byte[] se = reqPkt.getSerialization();
			return new DatagramPacket(se,se.length, params.getNasInetAddr(),2000);
		}

		@Override
		int procRecvPacket(DatagramPacket pkt) {
			PortalPacket rcv;
			try {
				rcv = PortalPacket.getPortalPacketByBytes(pkt.getData());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
			if (PortalPacket.getTYPE_ACK_LOGOUT() !=  rcv.getPkgType()) {
				return 0;
			}
			
			params.setErrCode(rcv.getErrCode());
			
			byte[] auditIp = rcv.getAttr(PortalPacket.ATTR_AUDITIP);
			if (null != auditIp) {
				params.setAuditIp(new String(auditIp));
			} else {
				params.setAuditIp(new String(""));
			}
			byte[] acctSessionId = rcv.getAttr(PortalPacket.getATTR_SESSIONID());
			if (null != acctSessionId) {
				params.setAcctSessionId(new String(acctSessionId));
			} else {
				params.setAcctSessionId(new String(""));
			}
			
			
			return 0;
		}
		
		public void doProc(){
			try{
				UDPHandshakeFrame.doFrameWork(socket, this, 0);
			}catch (UDPHandshakeFrameFailed e){
				params.setErrCode(1);
				params.setMessage(e.getMessage());
			}
		}
	}
	
	public void doLogoff( PortalUserLoginVo params ){
		LogoffProc prc = new LogoffProc(params);
		prc.doProc();
	}
	
	public static void main(String[] args){
		PortalUserLoginVo params = new PortalUserLoginVo();
		try {
			params.setNasIP("192.168.78.111");
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			params.setUserIP("192.168.78.100");
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		params.setUsername("aaa");
		params.setPassword("aaa");
		
		PortalSerial se = new PortalSerial(0,"192.168.78.116");
		se.doChapAuth(params);
		
		if(params.getErrCode()!=0){
			System.out.println(params.getMessage());
			return;
		}
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PortalSerial se2 = new PortalSerial(0,"192.168.78.116");
		se2.doLogoff(params);
		
	}
}
