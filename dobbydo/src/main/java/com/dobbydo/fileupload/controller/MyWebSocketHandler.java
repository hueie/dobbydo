package com.dobbydo.fileupload.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
            
            

        	//Get Video First Image
            String libName = "";
            if (SystemUtils.IS_OS_WINDOWS) {
                libName = "opencv_java320.dll";
            } else if (SystemUtils.IS_OS_LINUX) {
                libName = "libopencv_java320.so";
            }
            
            System.out.println( DOBBYDO_LIBS_DIR + libName );
            System.load( "C:\\Program Files\\Java\\jdk1.8.0_131\\jnilib\\opencv_java320.dll" );
    		
            //System.load( DOBBYDO_LIBS_DIR + libName );
            //System.loadLibrary( libName );            
            
            //Logic
            //VideoCapture vc = new VideoCapture(DOBBYDO_FILE_UPLOAD_DIR + fileName); 
            VideoCapture vc = new VideoCapture("C:\\upload\\sample.mp4"); 
            Mat frame = new Mat();
            vc.read(frame); //Get First Image
            Imgcodecs.imwrite("firstimage.jpg", frame);
            
            
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
        session.sendMessage(new TextMessage(currentChunkLength.toString()));
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
