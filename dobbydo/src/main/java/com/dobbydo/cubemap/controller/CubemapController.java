package com.dobbydo.cubemap.controller;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import com.dobbydo.atestpage.entity.Article;
import com.dobbydo.atestpage.service.IArticleService;
import com.dobbydo.cubemap.entity.Booksf;
import com.dobbydo.cubemap.entity.Box;
import com.dobbydo.cubemap.entity.Cubemap;
import com.dobbydo.cubemap.entity.Stack;
import com.dobbydo.cubemap.service.CubemapService;

@Controller
@RequestMapping("cubemap")
public class CubemapController {
	@Autowired
	private CubemapService cubemapService;
	
	@GetMapping("Cubemap")
	public ResponseEntity<List<Cubemap>> Cubemap (@RequestParam(value="stack_id", required = false)int stack_id) {
		System.out.println("stack_id : " + stack_id);
		List<Cubemap> list = null;
		if (stack_id != 0 ) {
			list = cubemapService.getCubemapsByStackId(stack_id);
			return new ResponseEntity<List<Cubemap>>(list, HttpStatus.OK);
		}else {
			return new ResponseEntity<List<Cubemap>>(list, HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("CubemapStackList")
	public ResponseEntity<List<Stack>> getAllStacks() {
		List<Stack> list = cubemapService.getAllStacks();
		return new ResponseEntity<List<Stack>>(list, HttpStatus.OK);
	}
	
	@GetMapping("CubemapBooksfList")
	public ResponseEntity<List<Booksf>> CubemapBooksfList(@RequestParam(value="stack_id", required = false)int stack_id) {
		List<Booksf> list = cubemapService.getBooksfsByStackId(stack_id);
		return new ResponseEntity<List<Booksf>>(list, HttpStatus.OK);
	}
	
	@PostMapping("CubemapAddStack")
	public ResponseEntity<Void> CubemapAddStack(@RequestParam(value="stack_nm", required = false)String stack_nm, 
			@RequestParam(value="stack_remk", required = false)String stack_remk, UriComponentsBuilder builder
			) throws Exception {
		System.out.println("CubemapAddStack");
		int stackId = 0;
		
		Stack stack = new Stack();
		stack.setStack_id(stackId);
		stack.setStack_nm(stack_nm);
		stack.setStack_remk(stack_remk);
		
		boolean flag = cubemapService.createStack(stack);
        if (flag == false) {
        	return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/cubemap/Cubemap?stack_id={stack_id}").buildAndExpand(stack.getStack_id()).toUri());
		
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	
	@PostMapping("CubemapAddBooksf")
	public ResponseEntity<Void> CubemapAddBooksf(@RequestParam(value="stack_id", required = false)int stack_id, 
			@RequestParam(value="booksf_nm", required = false)String booksf_nm, 
			@RequestParam(value="booksf_remk", required = false)String booksf_remk, 
			@RequestParam(value="booksf_y", required = false)int booksf_height) {
		
		Booksf booksf = new Booksf();
		booksf.setStack_id(stack_id);
		booksf.setBooksf_nm(booksf_nm);
		booksf.setBooksf_remk(booksf_remk);
		booksf.setBooksf_height(booksf_height);
		
		boolean flag = cubemapService.createBooksf(booksf);
        if (flag == false) {
        	return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	
	@PostMapping("CubemapAddBox")
	public ResponseEntity<Void> CubemapAddBox( 
			@RequestParam(value="box_nm", required = false)String box_nm, 
			@RequestParam(value="box_remk", required = false)String box_remk
			) {
		
		Box box = new Box();
		box.setBox_nm(box_nm);
		box.setBox_remk(box_remk);
		
		boolean flag = cubemapService.createBox(box);
        if (flag == false) {
        	return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	
	@PostMapping("CubemapBoxList")
	public ResponseEntity<List<Box>> getAllBoxes() {
		List<Box> list = cubemapService.getAllBoxes();
		return new ResponseEntity<List<Box>>(list, HttpStatus.OK);
	}
	
	
	@PostMapping("CubemapBoxView")
	public void CubemapBoxView(@RequestParam(value="box_id", required = false)String boxId, 
			HttpServletResponse response) throws Exception {
		
		Connection connect = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "";

		String boxNo = "";

		try{
		            // DB Open: mysql Server
		            // JDBC Driver Loading
		            String url = "jdbc:oracle:thin:@123.212.43.252:1521:ARCHIVE1";
		            String uid = "CBCK";
		            String pw = "CBCK";    
		                                               
		            Class.forName("oracle.jdbc.driver.OracleDriver");
		           
		            // Mysql DB Connection!!
		            connect = DriverManager.getConnection(url,uid,pw);
		            statement = connect.createStatement();
		            
		/*
		String cube_list = request.getParameter("cube_list");
		JSONObject obj = new JSONObject(cube_list);
		JSONArray items = obj.getJSONArray("cube_list");
		*/

		sql = "SELECT * FROM TB_PVBOX WHERE 1=1 ";
		if(!boxId.equals("") ){
			sql += "AND BOX_ID = " + boxId;
		}

		resultSet = statement.executeQuery(sql);    

		while (resultSet.next()) {
		    boxId = resultSet.getString("BOX_ID");
		    boxNo = resultSet.getString("BOX_NO");
		    
		}
		resultSet.close();
		statement.close();
		connect.close();
		}catch(Exception ex){
		}finally{
			
		}

		JSONObject obj = new JSONObject();
		obj.put("boxId", boxId);
		obj.put("boxNo", boxNo);
		
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		out.write(obj.toString());
		out.flush();
	}
	
	
	
	@PostMapping("CubemapBooksfView")
	public void CubemapBooksfView(@RequestParam(value="booksf_id", required = false)String booksfId, 
			HttpServletResponse response) throws Exception {
	
		Connection connect = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "";

		String stackId = "";
		String booksfNm = "";
			
		try{
		            // DB Open: mysql Server
		            // JDBC Driver Loading
		            String url = "jdbc:oracle:thin:@123.212.43.252:1521:ARCHIVE1";
		            String uid = "CBCK";
		            String pw = "CBCK";    
		                                               
		            Class.forName("oracle.jdbc.driver.OracleDriver");
		           
		            // Mysql DB Connection!!
		            connect = DriverManager.getConnection(url,uid,pw);
		            statement = connect.createStatement();
		            
		/*
		String cube_list = request.getParameter("cube_list");
		JSONObject obj = new JSONObject(cube_list);
		JSONArray items = obj.getJSONArray("cube_list");
		*/

		sql = "SELECT * FROM TB_PVBOOKSF WHERE 1=1 ";
		if(!booksfId.equals("") ){
			sql += "AND BOOKSF_ID = " + booksfId;
		}
		System.out.println(sql);
		resultSet = statement.executeQuery(sql);    
		while (resultSet.next()) {
		    stackId = resultSet.getString("STACK_ID");
		    booksfId = resultSet.getString("BOOKSF_ID");
		    booksfNm = resultSet.getString("BOOKSF_NM");
		}
		resultSet.close();
		statement.close();
		connect.close();
		}catch(Exception ex){
		}finally{
			
		}

		JSONObject obj = new JSONObject();
		obj.put("stackId", stackId);
		obj.put("booksfId", booksfId);
		obj.put("booksfNm", booksfNm);


		
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		out.write(obj.toString());
		out.flush();
	}	
	
	@PostMapping("CubemapSavemap")
	public ResponseEntity<Void> CubemapSavemap(@RequestParam(value="cube_list", required = false)String cube_list, 
			@RequestParam(value="stack_id", required = false)int stack_id) throws JSONException {
		
		//Clear Stack
		cubemapService.deleteCubemap(stack_id);
        
		//Create Monuments
		if(cube_list != null && !cube_list.equals("")){
			JSONObject obj = new JSONObject(cube_list);
			JSONArray items = obj.getJSONArray("cube_list");

			for (int i = 0; i < items.length(); i++) {
	            JSONObject item = items.getJSONObject(i);
	            int cube_idx = item.getInt("cube_idx");
	            int pos_x = item.getInt("pos_x");
	            int pos_y = item.getInt("pos_y");
	            int pos_z = item.getInt("pos_z");
	            int object_id = item.getInt("object_id");
	            int cube_type = item.getInt("cube_type");
	            int linked_id = item.getInt("linked_id");
	            int cube_size = item.getInt("cube_size");
	            int cube_axis = item.getInt("cube_axis");
			
	            Cubemap cubemap = new Cubemap();
	            cubemap.setCube_idx(cube_idx);
	            cubemap.setStack_id(stack_id);

	            cubemap.setPos_x(pos_x);
	            cubemap.setPos_y(pos_y);
	            cubemap.setPos_z(pos_z);
	            cubemap.setObject_id(object_id);
	            cubemap.setCube_type(cube_type);
	            cubemap.setLinked_id(linked_id);
	            cubemap.setCube_size(cube_size);
	            cubemap.setCube_axis(cube_axis);
			            
	            boolean flag = cubemapService.createCubemap(cubemap);
	            if (flag == false) {
	            	return new ResponseEntity<Void>(HttpStatus.CONFLICT);
	            }
			}
		}
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}	
	
	@PostMapping("CubemapSavestack")
	public void CubemapSavestack(@RequestParam(value="stackId", required = false)String stackId, 
			HttpServletResponse response) throws Exception {
		
		Connection connect = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet, resultSet2 = null;
		String sql = "";

		try{
		            // DB Open: mysql Server
		            // JDBC Driver Loading
		            /*
		            String url = "jdbc:mysql://localhost:3306/mmstestdb";
		            String uid = "root";
		            String pw = "1234";    
		                                               
		            Class.forName("com.mysql.jdbc.Driver");
		            */
		            // Mysql DB Connection!!
		            
		            // DB Open: oracle Server
				    // JDBC Driver Loading
				    String url = "jdbc:oracle:thin:@123.212.43.252:1521:ARCHIVE1";
				    String uid = "CBCK";
				    String pw = "CBCK";    
				                                       
				    Class.forName("oracle.jdbc.driver.OracleDriver");
		            
		            connect = DriverManager.getConnection(url,uid,pw);
		            statement = connect.createStatement();
		            

		sql = "SELECT object_id, linked_id, pos_y, pos_x, pos_z FROM YS_CUBE_MAP where stack_id = "+stackId+" and cube_type = 7 order by object_id, pos_y";

		resultSet = statement.executeQuery(sql);    
		System.out.println(sql);

		String obejctId="", booksfId = "";
		int posY, posX, posZ;

		int idx = 12;
		int minPosY=9999, maxPosY=-9999, minPosX=9999, maxPosX=-9999, minPosZ=9999, maxPosZ=-9999; 
		while (resultSet.next()) {
			if(idx == 12){
				idx = 0;
				minPosY=9999; maxPosY=-9999; minPosX=9999; maxPosX=-9999; minPosZ=9999; maxPosZ=-9999; 
				obejctId = resultSet.getString("object_id");
				booksfId = resultSet.getString("linked_id");
			}
			if( (0 <= idx && idx <= 3) || (8 <= idx && idx <= 11)){
				posY = resultSet.getInt("pos_y");
				posX = resultSet.getInt("pos_x");
				posZ = resultSet.getInt("pos_z");
				
				if(posX > maxPosX){
					maxPosX = posX;
				}
				if(posX < minPosX){
					minPosX = posX;
				}
				if(posZ > maxPosZ){
					maxPosZ = posZ;
				}
				if(posZ < minPosZ){
					minPosZ = posZ;
				}
				
				if(idx == 3){
					minPosY = posY;
				} else if(idx == 11){
					maxPosY = posY;
				}
			}
			if(idx == 11){
				sql = "SELECT linked_id, pos_y FROM YS_CUBE_MAP where stack_id = "+stackId+" and cube_type = 1"+ 
						" AND pos_y between "+minPosY+" and "+maxPosY+
						" AND pos_x between "+minPosX+" and "+maxPosX+
						" AND pos_z between "+minPosZ+" and "+maxPosZ;
				System.out.println(sql);

				resultSet2 = statement.executeQuery(sql);  
				int boxId = 0, arngId = 0, box_pos_y = 0;
				while (resultSet2.next()) {
					boxId = resultSet2.getInt("linked_id");
					box_pos_y = resultSet2.getInt("pos_y");
					
					sql = "SELECT MAX(arng_id) FROM TB_PVBOOKARNG";
					resultSet = statement.executeQuery(sql);
					resultSet.next();
					arngId = resultSet.getInt(1);
					System.out.println("arrgId : " + arngId);
					resultSet.close();
					
					sql = "insert into TB_PVBOOKARNG(ARNG_ID, BOX_ID, STACK_ID, BOOKSF_ID, BOOKSF_F_NO, BOOKSF_R_NO, BOOKSF_R_SNO, ARNG_CD, BOX_ARNG_DT) values (?, ?, LPAD(?, 3, '0'), LPAD(?, 3, '0'), ?, ?, ?, ?, TO_CHAR(SYSDATE, 'YYYYMMDDhh24miss'))";
					System.out.println(sql);
					preparedStatement = connect.prepareStatement(sql);
					preparedStatement.setInt(1, arngId+1);
					preparedStatement.setInt(2, boxId);
					preparedStatement.setString(3, stackId);
					preparedStatement.setString(4, booksfId);
					preparedStatement.setInt(5, (box_pos_y-25)/50 + 1);
					System.out.println((box_pos_y-25)/50 + 1);
					
					preparedStatement.setInt(6, 0);
					preparedStatement.setInt(7, arngId+1);
					preparedStatement.setString(8, "01");
					preparedStatement.executeUpdate();
				}
				resultSet2.close();
			}
			idx++;
		}

		//resultSet.close();
		preparedStatement.close();
		statement.close();
		connect.close();

		preparedStatement.close();
		statement.close();
		connect.close();
		}catch(Exception ex){
		}finally{
		}
		
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		out.write("Completely Insert Data Into DB.");
		out.flush();
	}	
		
}
