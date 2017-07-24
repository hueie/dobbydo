package com.dobbydo.fileupload.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import javax.servlet.ServletContext;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.websocket.Session;

import org.apache.commons.lang3.SystemUtils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyWebSocketHandler extends AbstractWebSocketHandler {

	@Autowired
	ServletContext context;
	
	//application.properties
	@Value("${dobbydo.file.upload.dir}")
	private String DOBBYDO_FILE_UPLOAD_DIR;
	@Value("${dobbydo.libs.dir}")
	private String DOBBYDO_LIBS_DIR;
	@Value("${dobbydo.video.server.ip}")
	private String DOBBYDO_VIDEO_SERVER_IP;
	@Value("${dobbydo.video.server.port}")
	private String DOBBYDO_VIDEO_SERVER_PORT;
	
	Integer progressPercent, currentChunkLength, totalChunkLength;
	String fileName;
	BufferedOutputStream bos;
    
	@Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
		System.out.println("Upload File Path : "+DOBBYDO_FILE_UPLOAD_DIR);
		
		//Get Message Content
		String msg = message.getPayload();
		System.out.println("Text Message : "+msg);
        if(!msg.equals("end")){ //File Upload Start
            // msg shape is "filename:aaa.jpg"
        	String msginfo = msg.substring(0, msg.indexOf(":"));
        	System.out.println(msginfo);
        	if(msginfo.equals("filename")) {
        		fileName = msg.substring(msg.indexOf(":")+1);
                File file = new File(DOBBYDO_FILE_UPLOAD_DIR + fileName);
                try {
                    bos = new BufferedOutputStream(new FileOutputStream(file));
                } catch (FileNotFoundException e) {
                	System.out.println(e.toString());
                	e.printStackTrace();
                }
        	} else if(msginfo.equals("totalChunkLength")) {
        		totalChunkLength = new Integer(msg.substring(msg.indexOf(":")+1));
        		progressPercent = 0;
        		currentChunkLength = 0;
        		System.out.println(totalChunkLength);
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
            
            
            
            //TCP Message To Video Process Server
            String sentence;
            String modifiedSentence;
            DataOutputStream outToServer;
            Socket clientSocket;
			try {
				clientSocket = new Socket(DOBBYDO_VIDEO_SERVER_IP, new Integer(DOBBYDO_VIDEO_SERVER_PORT));
				outToServer = new DataOutputStream(clientSocket.getOutputStream());
	            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	            sentence = DOBBYDO_FILE_UPLOAD_DIR + fileName;//"user_id:"+"YsJoung"+","+"video_id:"+"v1"+","+"filepath:"+DOBBYDO_FILE_UPLOAD_DIR + fileName;
	            outToServer.writeBytes(sentence + '\n');
	            //modifiedSentence = inFromServer.readLine();
	            //System.out.println("FROM SERVER: " + modifiedSentence);
	            clientSocket.close();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
                        
        }else {
        	System.out.println("Else!!!");
        }
    }
	
	@Override
	public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws IOException  {
		//System.out.println("Start Binary Upload");
        while(message.getPayload().hasRemaining()){
            bos.write(message.getPayload().get());
        }
        currentChunkLength++;
        //reply
        //progressPercent = (currentChunkLength/totalChunkLength);
        
        //Open 443 Port For Transfer data to Browser
        
        //session.sendMessage(new TextMessage(currentChunkLength.toString()));
        //System.out.println(progressPercent);
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
