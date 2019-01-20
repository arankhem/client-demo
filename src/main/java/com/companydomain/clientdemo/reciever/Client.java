package com.companydomain.clientdemo.reciever;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Client {
	
	// Reading the file location from application.properties
	@Value("${fileLocation}")
	private String fileLocation;

	// Reading host name from application.properties
	@Value("${host}")
	private String host;
		
	// Reading port number from application.properties
	@Value("${port}")
	private int port;
	
	@Scheduled(cron = "${cronExpressionForEvery10mins}")
	public void recieve() {
		SocketChannel socketChannel = null;
		RandomAccessFile file = null;
		try {
			socketChannel = SocketChannel.open();
			SocketAddress socketAddress = new InetSocketAddress(host, port);
			socketChannel.connect(socketAddress);
			System.out.println("Connected, sending the file");
			file = new RandomAccessFile(fileLocation, "rw");
			FileChannel inChannel = file.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(10240); // allocating buffer size as 10KB
			while (socketChannel.read(buffer) > 0) {
				buffer.flip();
				inChannel.write(buffer);
				buffer.clear();
			}
			System.out.println("File Recieved on "+LocalDateTime.now());
			socketChannel.close();
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

}
