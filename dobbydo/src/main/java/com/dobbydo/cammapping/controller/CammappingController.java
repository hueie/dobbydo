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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dobbydo.cammapping.entity.Cammapping;
import com.dobbydo.cammapping.service.CammappingService;
import com.dobbydo.cubemap.entity.Cubemap;
import com.dobbydo.cubemap.service.CubemapService;

@Controller
@RequestMapping("cubemap")
public class CammappingController {
	@Autowired
	private CammappingService cammappingService;
	

	@PostMapping("Cammapping")
	public ResponseEntity<Void> createMapping(@RequestParam(value="line_list", required = false)String line_list) throws JSONException {
		JSONObject obj = new JSONObject(line_list);
		JSONArray items = obj.getJSONArray("line_list");

		for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            int cammapping_id = item.getInt("cammapping_id");
            int line_id = item.getInt("line_id");
            int start_x = item.getInt("start_x");
            int end_x = item.getInt("end_x");
            int start_y = item.getInt("start_y");
            int end_y = item.getInt("end_y");
		    
            Cammapping cammapping = new Cammapping();
            
            
            boolean flag = cammappingService.createCammapping(cammapping);
            if (flag == false) {
            	return new ResponseEntity<Void>(HttpStatus.CONFLICT);
            }
		    String sql = "insert into cammapping(cammapping_id, line_idx, start_x, start_y, end_x, end_y) values (?, ?, ?, ?, ?, ?)";
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
