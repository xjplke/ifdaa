package cn.adfi.portal.utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import cn.adfi.portal.exceptions.UDPHandshakeFrameFailed;

public abstract class UDPHandshakeFrame {
	static int timeout = 3;
	static int repeat = 5;

	public static int getTimeout() {
		return timeout;
	}
	public static void setTimeout(int timeout) {
		UDPHandshakeFrame.timeout = timeout;
	}
	public static int getRepeat() {
		return repeat;
	}
	public static void setRepeat(int repeat) {
		UDPHandshakeFrame.repeat = repeat;
	}
	
	abstract DatagramPacket getSendPacket();
	abstract int procRecvPacket(DatagramPacket pkt);

	static void doFrameWork( DatagramSocket socket, UDPHandshakeFrame frame, int waitmore) {

		try {
			socket.setSoTimeout(timeout*1000);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new UDPHandshakeFrameFailed("socket error", e1);
		}

		DatagramPacket packet = frame.getSendPacket();
		
		byte[] rcv = new byte[4096];
		DatagramPacket pktRecv = new DatagramPacket(rcv,rcv.length);

		for(int count = repeat; count>0;count--){
			try {
				socket.send(packet);
				try {
					int ret;
					socket.receive(pktRecv);
					ret = frame.procRecvPacket(pktRecv);//对于重发的请求有可能收到认证请求中，实际上我们希望在第一个请求的响应后再响应http
					while (ret !=0){
						socket.receive(pktRecv);
						ret = frame.procRecvPacket(pktRecv);
					}
					return;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		throw new UDPHandshakeFrameFailed("udp require timeout!");
	}
}