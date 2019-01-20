package com.companydomain.clientdemo.reciever;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Reciever {
	
	// Reading the file location from application.properties
	@Value("${fileLocation}")
	private String fileLocation;

	// Reading host name from application.properties
	@Value("${host}")
	private String host;
	
	// Reading port number from application.properties
	@Value("${port}")
	private int port;
	
//	@Scheduled(cron = "${cronExpressionForEvery10mins}")
	public void recieve() {
		LocalTime startTime = LocalTime.now(); 
		System.out.println(startTime.getMinute()+":"+startTime.getSecond());
		System.out.println("Inside recieve");
		byte buffer[] = new byte[10240]; //10 KB
		try(Socket socket = new Socket(host, port);
			InputStream inputStream = socket.getInputStream();
			// Appending current date to the file name so that files will not be overridden
			FileOutputStream outputStream = new FileOutputStream(fileLocation+"-"+LocalDate.now()+".txt");) {
			int count;
			while ((count = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, count);
			}
			System.out.println("Send: After Write");
			LocalTime endTime = LocalTime.now(); 
			System.out.println(endTime.getMinute()+":"+endTime.getSecond());
		} catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
	}
}
