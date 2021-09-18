package com.liang.netty.chat;

import com.liang.netty.chat.netty.NettyWebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyChatApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(NettyChatApplication.class, args);
    }


    @Autowired
    private NettyWebSocketServer nettyWebSocketServer;

    @Override
    public void run(String... args) throws Exception {

        new Thread(nettyWebSocketServer).start();
    }
}
