package com.companydomain.clientdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.companydomain.clientdemo.reciever.Client;

@SpringBootApplication
@EnableScheduling
public class ClientApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(ClientApplication.class, args);
//		Reciever reciever = applicationContext.getBean(Reciever.class);
//		reciever.recieve();
		Client client = applicationContext.getBean(Client.class);
		client.recieve();
	}

}

