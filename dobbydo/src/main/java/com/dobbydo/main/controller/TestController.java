package com.dobbydo.main.controller;

import java.util.List;

import org.apache.commons.lang3.SystemUtils;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dobbydo.cammapping.entity.Cammapping;

@Controller
@RequestMapping("cammapping")
public class TestController {
	@GetMapping("testOpencv")
	public ResponseEntity<List<Void>> getAllCams() {
    	//Get Video First Image
        String libName = "";
        if (SystemUtils.IS_OS_WINDOWS) {
            libName = "opencv_java320.dll";
        } else if (SystemUtils.IS_OS_LINUX) {
            libName = "libopencv_java320.so";
        }
        
        //System.setProperty("java.library.path", "C:\\Users\\Kait\\git\\dobbydo\\dobbydo\\libs");
        System.out.println(System.getProperty("java.library.path"));
        
        //System.loadLibrary( libName );            
        //System.load( "C:\\Users\\Kait\\git\\dobbydo\\dobbydo\\libs\\opencv_java320.dll" );
        //Logic
        //VideoCapture vc = new VideoCapture(DOBBYDO_FILE_UPLOAD_DIR + fileName); 
        VideoCapture vc = new VideoCapture("C:\\upload\\sample.mp4"); 
        Mat frame = new Mat();
        vc.read(frame); //Get First Image
        Imgcodecs.imwrite("firstimage.jpg", frame);
        
		return new ResponseEntity<List<Void>>(HttpStatus.OK);
	}
	/*
	@GetMapping("testOpencv")
	public ResponseEntity<List<Void>> getJstest() {
	
		return new ResponseEntity<List<Void>>(HttpStatus.OK);
	}
	*/
	
}
