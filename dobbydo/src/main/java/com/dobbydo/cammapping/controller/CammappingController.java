package com.dobbydo.cammapping.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dobbydo.cammapping.entity.Cammapping;
import com.dobbydo.cammapping.service.CammappingService;
import com.dobbydo.cubemap.entity.Booksf;
import com.dobbydo.cubemap.entity.Cubemap;
import com.dobbydo.cubemap.entity.Stack;
import com.dobbydo.cubemap.service.CubemapService;

@Controller
@RequestMapping("cammapping")
public class CammappingController {
	@Autowired
	private CammappingService cammappingService;
	

	@PostMapping("Cammapping")
	public ResponseEntity<Void> createMapping(@RequestParam(value="line_list", required = false)String line_list) throws JSONException {
		JSONObject obj = new JSONObject(line_list);
		JSONArray items = obj.getJSONArray("line_list");

		for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            int cam_id = 1; //item.getInt("cammapping_id");
            int line_id = item.getInt("line_id");
            int start_x = item.getInt("start_x");
            int end_x = item.getInt("end_x");
            int start_y = item.getInt("start_y");
            int end_y = item.getInt("end_y");
		    
            Cammapping cammapping = new Cammapping();
            cammapping.setCam_id(cam_id);
            cammapping.setLine_id(line_id);
            cammapping.setStart_x(start_x);
            cammapping.setEnd_x(end_x);
            cammapping.setStart_y(start_y);
            cammapping.setEnd_y(end_y);
            
            boolean flag = cammappingService.createCammapping(cammapping);
            if (flag == false) {
            	return new ResponseEntity<Void>(HttpStatus.CONFLICT);
            }
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping("getAllCams")
	public ResponseEntity<List<Cammapping>> getAllCams() {
		List<Cammapping> list = cammappingService.getAllCams();
		return new ResponseEntity<List<Cammapping>>(list, HttpStatus.OK);
	}
	
	@GetMapping("getLinesfsByCamId")
	public ResponseEntity<List<Cammapping>> getLinesfsByCamId(@RequestParam(value="cam_id", required = false)int cam_id) {
		List<Cammapping> list = cammappingService.getLinesfsByCamId(cam_id);
		return new ResponseEntity<List<Cammapping>>(list, HttpStatus.OK);
	}
	
	@PutMapping("updateBooksfIdToCammapping")
	public ResponseEntity<Void> updateBooksfIdToCammapping(@RequestParam(value="booksf_id", required = false)int booksf_id,
			@RequestParam(value="cammapping_id", required = false)int cammapping_id) {
		Cammapping cammapping = new Cammapping();
        cammapping.setCammapping_id(cammapping_id);
        cammapping.setBooksf_id(booksf_id);
		cammappingService.updateBooksfIdToCammapping(cammapping);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
}
