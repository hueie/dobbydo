package com.dobbydo.wsfileserver.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.servlet.ServletContext;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyHandler extends AbstractWebSocketHandler {

	@Autowired
	ServletContext context;
	
	//application.properties
	@Value("${dobbydo.file.upload.dir}")
	private String path;
	
	BufferedOutputStream bos;
    
	@Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
		System.out.println("Upload File Path : "+path);
		
		//Get Message Content
		String msg = message.getPayload();
		System.out.println("Text Message : "+msg);
        if(!msg.equals("end")){ //File Upload Start
            // msg shape is "filename:aaa.jpg"
            String fileName = msg.substring(msg.indexOf(":")+1);
            File file = new File(path + fileName);
            try {
                bos = new BufferedOutputStream(new FileOutputStream(file));
            } catch (FileNotFoundException e) {
            	System.out.println(e.toString());
            	e.printStackTrace();
            }
        }else if(msg.equals("end")){ //File Upload End
        	System.out.println("End!!!");
            // If msg is "end", Close OutputStream
            try {
                bos.flush();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
        	System.out.println("Else!!!");
        }
    }

	@Override
	public void handleBinaryMessage(WebSocketSession session, BinaryMessage message)  {
		//System.out.println("Start Binary Upload");
        while(message.getPayload().hasRemaining()){
            try {
                bos.write(message.getPayload().get());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("WebSocket File Server Open......");
	}
	
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		System.out.println("error.......");
		exception.printStackTrace();
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("close.......");
	}
}
