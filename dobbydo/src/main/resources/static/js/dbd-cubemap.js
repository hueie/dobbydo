function setStackId(stack_id){
	if(stack_id == -1){
		// 되돌리기
		stack_id = $("#stack_id").val();
	}else{
		$("#stack_id").val(stack_id);
	}
	
	$.ajax({
        type: "get",
        url: "/cubemap/Cubemap",
        data: {"stack_id" : stack_id},
        success: function(data){
            initpushdata(data);
			init();
			render();
        },
        error:function (xhr, ajaxOptions, thrownError){
            // alert(xhr.status);
        } 
    });
}

var object_id = 0; // In Graph
var static_linked_id = 0;

function savemap(){
    var str = "";
	var cube_list = [];
	// cube_axis : none:0 y:1 z:2 x:3
	var cube_idx, pos_x, pos_y, pos_z, object_id, cube_type, linked_id, cube_size, cube_axis;
    for (var idx in objects) {
		if(idx != 0){
			str += '\n'+idx +'th ';
			cube_idx = idx;
			for (var key in objects[idx]) {
		        if (objects[idx].hasOwnProperty(key)) {
		            if(key == "position"){
		            	str += ' x:' + objects[idx][key]['x'];
		            	str += ' y:' + objects[idx][key]['y'];
		            	str += ' z:' + objects[idx][key]['z'];
		            	
		            	pos_x = objects[idx][key]['x'];
		            	pos_y = objects[idx][key]['y'];
		            	pos_z = objects[idx][key]['z'];
		            }
		            if(key == "name"){
		            	// alert(objects[idx][key]);
		            	var jsonobj = JSON.parse(objects[idx][key]);
		            	str += ' cube_idx : '+jsonobj.cube_idx +' ';
	        			str += ' object_id : ' + jsonobj.object_id +' ';
		            	str += ' cube_type : '+jsonobj.cube_type +' ';
	        			str += ' linked_id : ' + jsonobj.linked_id +' ';
	        			str += ' cube_size : ' + jsonobj.cube_size +' ';
	        			str += ' cube_axis : ' + jsonobj.cube_axis +' ';
	        			object_id = jsonobj.object_id;
	        			cube_type = jsonobj.cube_type;
	        			linked_id = jsonobj.linked_id;
	        			cube_size = jsonobj.cube_size;
	        			cube_axis = jsonobj.cube_axis;
		            }
		        }
			}
			cube_list.push({cube_idx:cube_idx, pos_x:pos_x, pos_y:pos_y, pos_z:pos_z, object_id:object_id, cube_type:cube_type, linked_id:linked_id, cube_size:cube_size, cube_axis:cube_axis });
		}
    }
	// alert(str);
	
	var stack_id = $("#stack_id").val();
	var myJsonString = "{cube_list:"+JSON.stringify(cube_list)+"}";
    $.ajax({
        type: "post",
        url: "/cubemap/CubemapSavemap",
        data: {"cube_list" : myJsonString, "stack_id":stack_id},
        success: function(msg){
         // alert(msg);
        },
        error:function (xhr, ajaxOptions, thrownError){
            alert(xhr.status);
            alert(thrownError);
        } 
    });
	
}
function savestack(){
	var stackId = $("#stack_id").val();
	$.ajax({
        type: "post",
        url: "/cubemap/CubemapSavestack",
        data: {"stackId" : stackId},
        success: function(msg){
           // alert(msg);
        },
        error:function (xhr, ajaxOptions, thrownError){
            alert(xhr.status);
            alert(thrownError);
        } 
    });
}
function addStack(){
	var stackNm = $("#stack_nm").val();
	var stackRemk = $("#stack_remk").val();
	
	$.ajax({
        type: "post",
        url: "/cubemap/CubemapAddStack",
        data: {"stack_nm" : stackNm, "stack_remk" : stackRemk},
        success: function(msg){
            // alert(msg);
            getStackList();
        },
        error:function (xhr, ajaxOptions, thrownError){
            alert(xhr.status);
            alert(thrownError);
        } 
    });
}
function addBooksf(){
	var stack_id = $("#stack_id").val();
	var booksf_nm = $("#booksf_nm").val();
	var booksf_remk = $("#booksf_remk").val();
	var booksf_y = $("#booksf_y").val();
	
	$.ajax({
        type: "post",
        url: "/cubemap/CubemapAddBooksf",
        data: {"stack_id" : stack_id, "booksf_nm" : booksf_nm, "booksf_remk" : booksf_remk, "booksf_y":booksf_y},
        success: function(msg){
            // alert(msg);
            getBooksfList();
        },
        error:function (xhr, ajaxOptions, thrownError){
            alert(xhr.status);
            alert(thrownError);
        } 
    });
}

function addBox(){
	var box_nm = $("#box_nm").val();
	var box_remk = $("#box_remk").val();
	
	$.ajax({
        type: "post",
        url: "/cubemap/CubemapAddBox",
        data: {"box_nm" : box_nm, "box_remk" : box_remk},
        success: function(msg){
            // alert(msg);
            getBoxList();
        },
        error:function (xhr, ajaxOptions, thrownError){
            alert(xhr.status);
            alert(thrownError);
        } 
    });
}

function getStackList(){
	$("#stack_add_form").css("display","block");
	$("#booksf_add_form").css("display","none");
	$("#box_add_form").css("display","none");
	// 서고
    var stack_id = 0;
    
	$.ajax({
        type: "get",
        url: "/cubemap/CubemapStackList",
        data: { },
        // contentType: "application/json",
        success: function(data, textStatus, xhr){
        	// var objs = JSON.parse(data);
        	var obj = data;// objs.data;
        	var html = "";
        	for(var idx in obj){
        		// alert(idx);
        		html += "<span><button class=\"btn btn-xs btn-warning\" onclick=\"setStackId("+obj[idx].stack_id+")\">서고배치</button> "+ obj[idx].stack_id + ", " + obj[idx].stack_nm + "</span><br>"; 
        	}
        	document.getElementById("list").innerHTML = html;
        },
        error:function (xhr, ajaxOptions, thrownError){
            alert(xhr.status);
            alert(thrownError);
        } 
    });
}
function getBooksfList(){
	$("#stack_add_form").css("display","none");
	$("#booksf_add_form").css("display","block");
	$("#box_add_form").css("display","none");
	// 서가
    var stack_id = $("#stack_id").val();
	$.ajax({
        type: "get",
        url: "/cubemap/CubemapBooksfList",
        data: {"stack_id":stack_id },
        success: function(data, textStatus, xhr){
        	// alert(data);
        	var objs = data;// JSON.parse(msg);
        	var html = "";
        	for(var idx in objs){
        		// alert(idx);
        		html += "<span><button class=\"btn btn-xs btn-warning\" onclick=\"upNdown('static_booksf_y',"+objs[idx].booksf_height+");upNdown('linked_id',"+objs[idx].booksf_id+");setPen_type(7)\">서가배치</button> "+ objs[idx].stack_id + ", " + objs[idx].booksf_id + ", " + objs[idx].booksf_nm + ", " + objs[idx].booksf_remk  + "</span><br>"; 
        	}
        	document.getElementById("list").innerHTML = html;
        	
        },
        error:function (xhr, ajaxOptions, thrownError){
            alert(xhr.status);
            alert(thrownError);
        } 
    });
}
function getBoxList(){
	$("#stack_add_form").css("display","none");
	$("#booksf_add_form").css("display","none");
	$("#box_add_form").css("display","block");
	// 상자
    
	$.ajax({
        type: "post",
        url: "/cubemap/CubemapBoxList",
        data: { },
        success: function(data, textStatus, xhr){
        	var objs = data;// JSON.parse(msg);
        	var html = "";
        	for(var idx in objs){
        		html += "<span><button class=\"btn btn-xs btn-warning\" onclick=\"upNdown('linked_id',"+objs[idx].box_id+");setPen_type(1)\">흰상자배치</button><button class=\"btn btn-xs btn-warning\" onclick=\"upNdown('linked_id',"+objs[idx].box_id+");setPen_type(2)\">빨간상자배치</button> "+ objs[idx].box_id + ", " + objs[idx].box_nm + ", " + objs[idx].box_remk + "</span><br>"; 
        	}
        	document.getElementById("list").innerHTML = html;
        },
        error:function (xhr, ajaxOptions, thrownError){
            alert(xhr.status);
            alert(thrownError);
        } 
    });
}

function getAllCams(){
	$("#stack_add_form").css("display","none");
	$("#booksf_add_form").css("display","none");
	$("#box_add_form").css("display","none");
    
	$.ajax({
        type: "get",
        url: "/cammapping/getAllCams",
        data: { },
        success: function(data, textStatus, xhr){
        	var objs = data;// JSON.parse(msg);
        	var html = "";
        	for(var idx in objs){
        		html += "<span><button class=\"btn btn-xs btn-warning\" onclick=\"getLinesfsByCamId("+objs[idx].cam_id+");\">사진 선택</button>"+ objs[idx].cammapping_id + ", " + objs[idx].cam_id + ", " + objs[idx].line_id + "</span><br>"; 
        	}
        	document.getElementById("list").innerHTML = html;
        },
        error:function (xhr, ajaxOptions, thrownError){
            alert(xhr.status);
            alert(thrownError);
        } 
    });
}
function getLinesfsByCamId(cam_id){
	$("#stack_add_form").css("display","none");
	$("#booksf_add_form").css("display","none");
	$("#box_add_form").css("display","none");
    
	$.ajax({
        type: "get",
        url: "/cammapping/getLinesfsByCamId",
        data: {"cam_id":cam_id },
        success: function(data, textStatus, xhr){
        	var objs = data;// JSON.parse(msg);
        	var html = "";
        	for(var idx in objs){
        		html += "<span><button onclick=\"updateBooksfIdToCammapping("+objs[idx].cammapping_id+");\">연동하기</button>"+ objs[idx].cammapping_id + ", " + objs[idx].cam_id + ", " + objs[idx].line_id + ", " + objs[idx].booksf_id + "</span><br>"; 
        	}
        	document.getElementById("list").innerHTML = html;
        },
        error:function (xhr, ajaxOptions, thrownError){
            alert(xhr.status);
            alert(thrownError);
        } 
    });
}
function updateBooksfIdToCammapping(cammapping_id){
	var booksf_id = $("#booksf_id").val();
	if(booksf_id == ""){
		alert("연결할 서가를 선택해주세요.");
		return false;
	}
	
	$.ajax({
        type: "put",
        url: "/cammapping/updateBooksfIdToCammapping",
        data: {"booksf_id" : booksf_id, "cammapping_id" : cammapping_id},
        success: function(data){
        	alert("save it finish");
        },
        error:function (xhr, ajaxOptions, thrownError){
            alert(xhr.status);
            alert(thrownError);
        } 
    });
}


function getBooksfView(booksf_id){
	// 서가
	$.ajax({
        type: "post",
        url: "/cubemap/CubemapBooksfView",
        data: { "booksf_id" : booksf_id},
        success: function(data){
        	var obj = data;
        	document.getElementById("view").innerHTML = "<span>"+ obj.stack_id + ", " + obj.booksf_id + ", " + obj.booksf_nm + ", " + obj.booksf_remk + "<br>"; 
        	$("#booksf_id").val(booksf_id);
        },
        error:function (xhr, ajaxOptions, thrownError){
            alert(xhr.status);
            alert(thrownError);
        } 
    });
}
function getBoxView(box_id){
	// 상자
	$.ajax({
        type: "post",
        url: "/cubemap/CubemapBoxView",
        data: { "box_id" : box_id},
        success: function(data){
        	var obj = data;
        	document.getElementById("view").innerHTML = "<span>"+ obj.box_id + ", " + obj.box_nm + ", " + obj.box_remk + "</span><br>"; 
        },
        error:function (xhr, ajaxOptions, thrownError){
            alert(xhr.status);
            alert(thrownError);
        } 
    });
}
var booksf_y = 1; 
var booksf_z = 1; 
var booksf_x = 1; 
function upNdown(tag_id,i){
	var value =  $("#"+tag_id).val();
	if(tag_id == "linked_id"){
		value = i;
	} else if(tag_id == "static_booksf_y"){
		value = i;
	} else{
		value = parseInt(value) + parseInt(i);
	}
	
	if(value >= 0){
		if(tag_id == "static_booksf_y"){
			$("#booksf_y").val(value);
		} else {
			$("#"+tag_id).val(value);
		}
	}
	
	if(tag_id == "booksf_y"){
		booksf_y = value;
	} else if(tag_id == "booksf_z"){
		booksf_z = value;
	} else if(tag_id == "booksf_x"){
		booksf_x = value;
	} else if(tag_id == "linked_id"){
		static_linked_id = value;
	} else if(tag_id == "static_booksf_y"){
		booksf_y = value;
	}
}


var container;
var camera, scene, renderer;
var plane, cube, line;
var mouse, raycaster, isShiftDown = false, isCtrlDown = false, isAltDown = false;
var rollOverGeo, rollOverMesh, rollOverMaterial;


var rollOverBooksfXGeo, rollOverBooksfXMaterial, realBooksfXMaterial, rollOverBooksfXMesh = [];
var rollOverBooksfYGeo, rollOverBooksfYMaterial, realBooksfYMaterial, rollOverBooksfYMesh = [];
var rollOverBooksfZGeo, rollOverBooksfZMaterial, realBooksfZMaterial, rollOverBooksfZMesh = [];

var cubeGeo, cubeMaterial, cctvMaterial;

var objects = [];
var pen_type = 1; // 999; //0:eraser//1:white box//2:red box//3,4,5:y,z,x-axis
					// green pen//6:rack
var cubes = [];

function initpushdata(objs){
	objects = [];
	pen_type = 1;
	cubes = [];
	for(var idx in objs){
		cubes.push({cube_idx:objs[idx].cube_idx, pos_x:objs[idx].pos_x, pos_y:objs[idx].pos_y, pos_z:objs[idx].pos_z, object_id:objs[idx].object_id, cube_type:objs[idx].cube_type, linked_id:objs[idx].linked_id, cube_size:objs[idx].cube_size, cube_axis:objs[idx].cube_axis });
	}
}

function init() {
	// container = document.createElement( 'div' );
	// document.body.appendChild( container );
	container = document.getElementById( 'container' );
	container.innerHTML = '';
	
	/* Create Scene */
	scene = new THREE.Scene();
	/* Camera Setting */
	camera = new THREE.PerspectiveCamera( 45, window.innerWidth*6/10 / window.innerHeight, 1, 10000 );
	camera.position.set( 1000, 1000, 0 );
	camera.lookAt( new THREE.Vector3() );
	
	/* Blue Cube Setting (roll-over helpers) */
	rollOverGeo = new THREE.BoxGeometry( 50, 50, 50 );
	rollOverMaterial = new THREE.MeshBasicMaterial( { color: 0x0000ff, opacity: 0.5, transparent: true } );
	rollOverMesh = new THREE.Mesh( rollOverGeo, rollOverMaterial ); 
	scene.add( rollOverMesh );
	
	/* Start Rack */
	rollOverBooksfYGeo = new THREE.BoxGeometry(8,50*booksf_y,8);
	rollOverBooksfYMaterial = new THREE.MeshBasicMaterial( { color: 0x0000ff, opacity: 0.5, transparent: true } );
	realBooksfYMaterial = new THREE.MeshBasicMaterial( { color: 0x000000, opacity: 1, transparent: true } );
	
	rollOverBooksfYMesh[1] = new THREE.Mesh( rollOverBooksfYGeo, realBooksfYMaterial ); 
	rollOverBooksfYMesh[1].rotation.y = 0.5*Math.PI;
	// rollOverBooksfYMesh.position.copy( intersect.point ).add(
	// intersect.face.normal );
	rollOverBooksfYMesh[1].position.divideScalar( 50 ).round().multiplyScalar( 50 );// .addScalar(
																					// 25
																					// );
	rollOverBooksfYMesh[1].position.y += 25*booksf_y;
	scene.add( rollOverBooksfYMesh[1] );
	
	rollOverBooksfYMesh[2] = rollOverBooksfYMesh[1].clone();
	rollOverBooksfYMesh[2].position.x += 50*booksf_x;
	scene.add( rollOverBooksfYMesh[2] );

	rollOverBooksfYMesh[3] = rollOverBooksfYMesh[1].clone();
	rollOverBooksfYMesh[3].position.z += 50*booksf_z;
	scene.add( rollOverBooksfYMesh[3] );

	rollOverBooksfYMesh[4] = rollOverBooksfYMesh[1].clone();
	rollOverBooksfYMesh[4].position.x += 50*booksf_x;
	rollOverBooksfYMesh[4].position.z += 50*booksf_z;
	scene.add( rollOverBooksfYMesh[4] );
	
	
	rollOverBooksfZGeo = new THREE.BoxGeometry(8,8,50*booksf_z);
	rollOverBooksfZMaterial = new THREE.MeshBasicMaterial( { color: 0x0000ff, opacity: 0.5, transparent: true } );
	realBooksfZMaterial = new THREE.MeshBasicMaterial( { color: 0x000000, opacity: 1, transparent: true } );
	
	rollOverBooksfZMesh[1] = new THREE.Mesh( rollOverBooksfZGeo, realBooksfZMaterial ); 
	// rollOverBooksfZMesh.position.copy( intersect.point ).add(
	// intersect.face.normal );
	rollOverBooksfZMesh[1].position.divideScalar( 50 ).round().multiplyScalar( 50 );
	rollOverBooksfZMesh[1].position.z += 25*booksf_z;
	scene.add( rollOverBooksfZMesh[1] );
	
	rollOverBooksfZMesh[2] = rollOverBooksfZMesh[1].clone();
	rollOverBooksfZMesh[2].position.y += 50*booksf_y;
	scene.add( rollOverBooksfZMesh[2] );
	
	rollOverBooksfZMesh[3] = rollOverBooksfZMesh[1].clone();
	rollOverBooksfZMesh[3].position.x += 50*booksf_x;
	scene.add( rollOverBooksfZMesh[3] );
	
	rollOverBooksfZMesh[4] = rollOverBooksfZMesh[1].clone();
	rollOverBooksfZMesh[4].position.y += 50*booksf_y;
	rollOverBooksfZMesh[4].position.x += 50*booksf_x;
	scene.add( rollOverBooksfZMesh[4] );
	
	
	rollOverBooksfXGeo = new THREE.BoxGeometry(50*booksf_x,8,8);
	rollOverBooksfXMaterial = new THREE.MeshBasicMaterial( { color: 0x0000ff, opacity: 0.5, transparent: true } );
	realBooksfXMaterial = new THREE.MeshBasicMaterial( { color: 0x000000, opacity: 1, transparent: true } );
	
	rollOverBooksfXMesh[1] = new THREE.Mesh( rollOverBooksfXGeo, realBooksfXMaterial ); 
	rollOverBooksfXMesh[1].rotation.x = 0.5*Math.PI;
	// rollOverBooksfXMesh.position.copy( intersect.point ).add(
	// intersect.face.normal );
	rollOverBooksfXMesh[1].position.divideScalar( 50 ).round().multiplyScalar( 50 );
	rollOverBooksfXMesh[1].position.x += 25*booksf_x;
	scene.add( rollOverBooksfXMesh[1] );

	rollOverBooksfXMesh[2] = rollOverBooksfXMesh[1].clone();
	rollOverBooksfXMesh[2].position.y += 50*booksf_y;
	scene.add( rollOverBooksfXMesh[2] );
	
	rollOverBooksfXMesh[3] = rollOverBooksfXMesh[1].clone();
	rollOverBooksfXMesh[3].position.z += 50*booksf_z;
	scene.add( rollOverBooksfXMesh[3] );
	
	rollOverBooksfXMesh[4] = rollOverBooksfXMesh[1].clone();
	rollOverBooksfXMesh[4].position.y += 50*booksf_y;
	rollOverBooksfXMesh[4].position.z += 50*booksf_z;
	scene.add( rollOverBooksfXMesh[4] );
	/* End Rack */
	
	
	/* Textures !!! */
	/* White Cube Setting */
	cubeGeo = new THREE.BoxGeometry( 50, 50, 50 );
	// cubeMaterial = new THREE.MeshLambertMaterial( { color: 0xfeb74c, map: new
	// THREE.TextureLoader().load( "textures/square-outline-textured.png" ) } );
	cubeMaterial = new THREE.MeshLambertMaterial( { color: 0xffffff } ); // White
																			// Cube
	/* Red Cube Setting */
	cctvMaterial = new THREE.MeshLambertMaterial( { color: 0xff0000 } ); // Red
																			// Cube
	
	
	/* Grid Floor Setting */
	var size = 500, step = 50;
	var geometry = new THREE.Geometry();
	for ( var i = - size; i <= size; i += step ) {
		geometry.vertices.push( new THREE.Vector3( - size, 0, i ) );
		geometry.vertices.push( new THREE.Vector3(   size, 0, i ) );
		geometry.vertices.push( new THREE.Vector3( i, 0, - size ) );
		geometry.vertices.push( new THREE.Vector3( i, 0,   size ) );
	}
	var material = new THREE.LineBasicMaterial( { color: 0x000000, opacity: 0.2, transparent: true } );
	line = new THREE.LineSegments( geometry, material );
	scene.add( line );
	
	/* Raycaster Setting : Renders a 3D world based on a 2D map */
	raycaster = new THREE.Raycaster();
	mouse = new THREE.Vector2();
	var geometry = new THREE.PlaneBufferGeometry( 1000, 1000 );
	geometry.rotateX( -Math.PI/2 );
	plane = new THREE.Mesh( geometry, new THREE.MeshBasicMaterial( { visible: false } ) );
	scene.add( plane );
	objects.push( plane );
	
	/* Lights Setting */
	var ambientLight = new THREE.AmbientLight( 0x606060 );
	scene.add( ambientLight );
	var directionalLight = new THREE.DirectionalLight( 0xffffff );
	directionalLight.position.set( 1, 0.75, 0.5 ).normalize();
	scene.add( directionalLight );
	
	
	/* Build Boxes */
	for(var key in cubes){
		var voxel;
		if(cubes[key]['cube_type'] == 1){
			voxel = new THREE.Mesh( cubeGeo, cubeMaterial );
			voxel.position.set(cubes[key]['pos_x'], cubes[key]['pos_y'], cubes[key]['pos_z']);
		} else if(cubes[key]['cube_type'] == 2){
			voxel = new THREE.Mesh( cubeGeo, cctvMaterial );
			voxel.position.set(cubes[key]['pos_x'], cubes[key]['pos_y'], cubes[key]['pos_z']);
		} else if(cubes[key]['cube_type'] == 3){
			// green pen
			voxel = new THREE.Mesh( rollOverPenGeo, rollOverPenMaterial );
			voxel.position.set(cubes[key]['pos_x'], cubes[key]['pos_y'], cubes[key]['pos_z']);
		} else if(cubes[key]['cube_type'] == 4){
			// green pen
			voxel = new THREE.Mesh( rollOverPenGeo, rollOverPenMaterial );
			voxel.rotation.x = 0.5*Math.PI;
			voxel.rotation.z = 0;
			voxel.position.set(cubes[key]['pos_x'], cubes[key]['pos_y'], cubes[key]['pos_z']);
		} else if(cubes[key]['cube_type'] == 5){
			// green pen
			voxel = new THREE.Mesh( rollOverPenGeo, rollOverPenMaterial );
			voxel.rotation.x = 0.5*Math.PI;
			voxel.rotation.z = 0.5*Math.PI;
			voxel.position.set(cubes[key]['pos_x'], cubes[key]['pos_y'], cubes[key]['pos_z']);
		} else if(cubes[key]['cube_type'] == 7){
			// cube_axis : none:0 y:1 z:2 x:3
			if(cubes[key]['cube_axis'] == 1){
				// YMesh
				var rollOverBooksfYGeo = new THREE.BoxGeometry(8,50*cubes[key]['cube_size'],8);
				var rollOverBooksfYMaterial = new THREE.MeshBasicMaterial( { color: 0x000000, opacity: 1, transparent: true } );
				voxel = new THREE.Mesh( rollOverBooksfYGeo, rollOverBooksfYMaterial ); 
				voxel.rotation.y = 0.5*Math.PI;
				voxel.position.set(cubes[key]['pos_x'], cubes[key]['pos_y'], cubes[key]['pos_z']);
			} else if(cubes[key]['cube_axis'] == 2){
				// ZMesh
				var rollOverBooksfZGeo = new THREE.BoxGeometry(8,8,50*cubes[key]['cube_size']);
				var rollOverBooksfZMaterial = new THREE.MeshBasicMaterial( { color: 0x000000, opacity: 1, transparent: true } );
				voxel = new THREE.Mesh( rollOverBooksfZGeo, rollOverBooksfZMaterial ); 
				voxel.position.set(cubes[key]['pos_x'], cubes[key]['pos_y'], cubes[key]['pos_z']);
			} else if(cubes[key]['cube_axis'] == 3){
				// XMesh
				var rollOverBooksfXGeo = new THREE.BoxGeometry(50*cubes[key]['cube_size'],8,8);
				var rollOverBooksfXMaterial = new THREE.MeshBasicMaterial( { color: 0x000000, opacity: 1, transparent: true } );
				voxel = new THREE.Mesh( rollOverBooksfXGeo, rollOverBooksfXMaterial ); 
				voxel.rotation.x = 0.5*Math.PI;
				voxel.position.set(cubes[key]['pos_x'], cubes[key]['pos_y'], cubes[key]['pos_z']);
			}
		}
		voxel.name = "{ \"cube_type\":"+cubes[key]['cube_type']+", \"linked_id\":"+cubes[key]['linked_id']+", \"object_id\":"+cubes[key]['object_id']+", \"cube_size\":"+cubes[key]['cube_size']+", \"cube_axis\":"+cubes[key]['cube_axis']+" }";
		scene.add( voxel );
		objects.push( voxel );
	}
	
	/* Render */
	renderer = new THREE.WebGLRenderer( { antialias: true } );
	renderer.setClearColor( 0xf0f0f0 );
	renderer.setPixelRatio( window.devicePixelRatio );
	renderer.setSize( window.innerWidth*6/10, window.innerHeight );
	container.appendChild( renderer.domElement );
	
	/* Event */
	document.getElementById('container').addEventListener( 'mousemove', onDocumentMouseMove, false );
	document.getElementById('container').addEventListener( 'mousedown', onDocumentMouseDown, false );
	document.addEventListener( 'keydown', onDocumentKeyDown, false );
	document.addEventListener( 'keyup', onDocumentKeyUp, false );
	// When Window is Resized
	window.addEventListener( 'resize', onWindowResize, false );
}
function onWindowResize() {
	camera.aspect = (window.innerWidth*6/10) / window.innerHeight;
	camera.updateProjectionMatrix();
	renderer.setSize( (window.innerWidth*6/10), window.innerHeight );
}
function onDocumentMouseMove( event ) {
	event.preventDefault();
	var d = document.getElementById('cubemapview').getBoundingClientRect();
	var left_margin = parseInt(d.left);
	var top_margin = parseInt(d.top);
	
	// alert(top_margin + " "+left_margin);
	mouse.set( ( (event.clientX-left_margin) / (window.innerWidth*6/10) ) * 2 - 1, - ( (event.clientY-top_margin) / window.innerHeight ) * 2 + 1 );
	raycaster.setFromCamera( mouse, camera );
	var intersects = raycaster.intersectObjects( objects );
	if ( intersects.length > 0 ) {
		var intersect = intersects[ 0 ];
		
		// alert("Point : "+intersect.point.x+" face :
		// "+intersect.face.normal.x+"Point : "+intersect.point.y+" face :
		// "+intersect.face.normal.y);
		if(pen_type == 0 || pen_type == 999){
			
		} else if(pen_type == 1 || pen_type == 2){
			rollOverMesh.position.copy( intersect.point ).add( intersect.face.normal );
			rollOverMesh.position.divideScalar( 50 ).floor().multiplyScalar( 50 ).addScalar( 25 );
		} else if(pen_type == 7){
			rollOverBooksfYMesh[1].scale.y = booksf_y;// 50*booksf_y;
			rollOverBooksfYMesh[2].scale.y = booksf_y;
			rollOverBooksfYMesh[3].scale.y = booksf_y;// 50*booksf_y;
			rollOverBooksfYMesh[4].scale.y = booksf_y;
			
			rollOverBooksfYMesh[1].position.copy( intersect.point ).add( intersect.face.normal );
			rollOverBooksfYMesh[1].position.divideScalar( 50 ).round().multiplyScalar( 50 );// .addScalar(
																							// 25
																							// );
			rollOverBooksfYMesh[1].position.y += 25*booksf_y;
			
			rollOverBooksfYMesh[2].position.copy( intersect.point ).add( intersect.face.normal );
			rollOverBooksfYMesh[2].position.divideScalar( 50 ).round().multiplyScalar( 50 );// .addScalar(
																							// 25
																							// );
			rollOverBooksfYMesh[2].position.y += 25*booksf_y;
			rollOverBooksfYMesh[2].position.x += 50*booksf_x;
			
			rollOverBooksfYMesh[3].position.copy( intersect.point ).add( intersect.face.normal );
			rollOverBooksfYMesh[3].position.divideScalar( 50 ).round().multiplyScalar( 50 );// .addScalar(
																							// 25
																							// );
			rollOverBooksfYMesh[3].position.y += 25*booksf_y;
			rollOverBooksfYMesh[3].position.z += 50*booksf_z;
			
			rollOverBooksfYMesh[4].position.copy( intersect.point ).add( intersect.face.normal );
			rollOverBooksfYMesh[4].position.divideScalar( 50 ).round().multiplyScalar( 50 );// .addScalar(
																							// 25
																							// );
			rollOverBooksfYMesh[4].position.y += 25*booksf_y;
			rollOverBooksfYMesh[4].position.x += 50*booksf_x;
			rollOverBooksfYMesh[4].position.z += 50*booksf_z;
			
			
			rollOverBooksfZMesh[1].scale.z = booksf_z;
			rollOverBooksfZMesh[2].scale.z = booksf_z;
			rollOverBooksfZMesh[3].scale.z = booksf_z;
			rollOverBooksfZMesh[4].scale.z = booksf_z;
			
			rollOverBooksfZMesh[1].position.copy( intersect.point ).add( intersect.face.normal );
			rollOverBooksfZMesh[1].position.divideScalar( 50 ).round().multiplyScalar( 50 );
			rollOverBooksfZMesh[1].position.z += 25*booksf_z;
			
			rollOverBooksfZMesh[2].position.copy( intersect.point ).add( intersect.face.normal );
			rollOverBooksfZMesh[2].position.divideScalar( 50 ).round().multiplyScalar( 50 );
			rollOverBooksfZMesh[2].position.z += 25*booksf_z;
			rollOverBooksfZMesh[2].position.y += 50*booksf_y;
			
			rollOverBooksfZMesh[3].position.copy( intersect.point ).add( intersect.face.normal );
			rollOverBooksfZMesh[3].position.divideScalar( 50 ).round().multiplyScalar( 50 );
			rollOverBooksfZMesh[3].position.z += 25*booksf_z;
			rollOverBooksfZMesh[3].position.x += 50*booksf_x;
			
			rollOverBooksfZMesh[4].position.copy( intersect.point ).add( intersect.face.normal );
			rollOverBooksfZMesh[4].position.divideScalar( 50 ).round().multiplyScalar( 50 );
			rollOverBooksfZMesh[4].position.z += 25*booksf_z;
			rollOverBooksfZMesh[4].position.y += 50*booksf_y;
			rollOverBooksfZMesh[4].position.x += 50*booksf_x;
			

			rollOverBooksfXMesh[1].scale.x = booksf_x;
			rollOverBooksfXMesh[2].scale.x = booksf_x;
			rollOverBooksfXMesh[3].scale.x = booksf_x;
			rollOverBooksfXMesh[4].scale.x = booksf_x;
			
			rollOverBooksfXMesh[1].position.copy( intersect.point ).add( intersect.face.normal );
			rollOverBooksfXMesh[1].position.divideScalar( 50 ).round().multiplyScalar( 50 );
			rollOverBooksfXMesh[1].position.x += 25*booksf_x;
			
			rollOverBooksfXMesh[2].position.copy( intersect.point ).add( intersect.face.normal );
			rollOverBooksfXMesh[2].position.divideScalar( 50 ).round().multiplyScalar( 50 );
			rollOverBooksfXMesh[2].position.x += 25*booksf_x;
			rollOverBooksfXMesh[2].position.y += 50*booksf_y;
			
			rollOverBooksfXMesh[3].position.copy( intersect.point ).add( intersect.face.normal );
			rollOverBooksfXMesh[3].position.divideScalar( 50 ).round().multiplyScalar( 50 );
			rollOverBooksfXMesh[3].position.x += 25*booksf_x;
			rollOverBooksfXMesh[3].position.z += 50*booksf_z;
			
			rollOverBooksfXMesh[4].position.copy( intersect.point ).add( intersect.face.normal );
			rollOverBooksfXMesh[4].position.divideScalar( 50 ).round().multiplyScalar( 50 );
			rollOverBooksfXMesh[4].position.x += 25*booksf_x;
			rollOverBooksfXMesh[4].position.y += 50*booksf_y;
			rollOverBooksfXMesh[4].position.z += 50*booksf_z;
		}
			
	}
	render();
}
function onDocumentMouseDown( event ) {
	event.preventDefault();
	var d = document.getElementById('cubemapview').getBoundingClientRect();
	var left_margin = parseInt(d.left);
	var top_margin = parseInt(d.top);
	mouse.set( ( (event.clientX-left_margin) / (window.innerWidth*6/10) ) * 2 - 1, - ( (event.clientY-top_margin) / window.innerHeight ) * 2 + 1 );
	raycaster.setFromCamera( mouse, camera );
	var intersects = raycaster.intersectObjects( objects );
	
	if ( intersects.length > 0 ) {
		var intersect = intersects[0];
		var voxel;
		if ( pen_type == 0 ) {
			// eraser
			if ( intersect.object != plane ) {
				var jsonobj = JSON.parse(intersect.object["name"]);
				if(jsonobj.cube_type == "7"){
					// alert("obj:"+jsonobj.object_id);
					// Rack
					var erased_id = jsonobj.object_id;
					var objectsdel_flag = false, objectsdel_idx=0;
					for(var idx in objects){
						// alert(idx);
						if(objects[idx] != plane){
							var tmpjsonobj = JSON.parse(objects[idx]["name"]);
							if(tmpjsonobj.object_id == erased_id){
								scene.remove( objects[idx] );
								if(objectsdel_flag == false){
									objectsdel_idx = idx;
									objectsdel_flag = true;
								}
							}
						}
					}
					if(objectsdel_flag){
						// objects.splice( objects.indexOf(
						// objects[objectsdel_idx-12] ), 12 );
						objects.splice( objectsdel_idx, 12 );
					}
				} else{
					scene.remove( intersect.object );
					objects.splice( objects.indexOf( intersect.object ), 1 );
				}
			}
		} else if( pen_type == 1) {
			// white pen
			voxel = new THREE.Mesh( cubeGeo, cubeMaterial );
			voxel.position.copy( intersect.point ).add( intersect.face.normal );
			voxel.position.divideScalar( 50 ).floor().multiplyScalar( 50 ).addScalar( 25 );
			voxel.name = "{ \"cube_type\":1, \"linked_id\":"+static_linked_id+", \"object_id\":"+object_id+", \"cube_size\":1, \"cube_axis\":0 }";
			object_id++;
		} else if ( pen_type == 2 ) {
			// red box
			voxel = new THREE.Mesh( cubeGeo, cctvMaterial );
			voxel.position.copy( intersect.point ).add( intersect.face.normal );
			voxel.position.divideScalar( 50 ).floor().multiplyScalar( 50 ).addScalar( 25 );
			voxel.name = "{ \"cube_type\":2, \"linked_id\":"+static_linked_id+", \"object_id\":"+object_id+", \"cube_size\":1, \"cube_axis\":0 }";
			object_id++;
		} else if( pen_type == 7) {
			var voxel;
			for(var idx=0; idx<12; idx++){
				var remainder = idx%4;
				var quotient = parseInt(idx/4);
				if(quotient == 0){
					voxel = rollOverBooksfYMesh[remainder+1].clone();
					voxel.name = "{ \"cube_type\":7, \"linked_id\":"+static_linked_id+", \"object_id\":"+object_id+", \"cube_size\":"+booksf_y+", \"cube_axis\":1 }";
				} else if(quotient == 1){
					voxel = rollOverBooksfZMesh[remainder+1].clone();
					voxel.name = "{ \"cube_type\":7, \"linked_id\":"+static_linked_id+", \"object_id\":"+object_id+", \"cube_size\":"+booksf_z+", \"cube_axis\":2 }";
				} else if(quotient == 2){
					voxel = rollOverBooksfXMesh[remainder+1].clone();
					voxel.name = "{ \"cube_type\":7, \"linked_id\":"+static_linked_id+", \"object_id\":"+object_id+", \"cube_size\":"+booksf_x+", \"cube_axis\":3 }";
				}
				scene.add( voxel );
				objects.push( voxel );
			}
			object_id++;
		} else if ( pen_type == 999 ) {
			if ( intersect.object != plane ) {
				var jsonobj = JSON.parse(intersect.object["name"]);
				if(jsonobj.cube_type == "1" || jsonobj.cube_type == "2"){
					getBoxView(jsonobj.linked_id);
				} else if(jsonobj.cube_type == "7"){
					getBooksfView(jsonobj.linked_id);
				}
			}
		}
	
		
		if ( pen_type == 0 || pen_type == 7 || pen_type == 999 ) {
			
		} else {
			scene.add( voxel );
			objects.push( voxel );
		}
	
		render();
	}
}
function onDocumentKeyDown( event ) {
	switch( event.keyCode ) {
		// case 16: isShiftDown = true; break; //shift key
		// case 17: isCtrlDown = true; break; //ctrl key
		// case 18: isAltDown = true; break; //ctrl key
		case 37:  // left = 37
			if(camera.position.z != -2000){
				camera.position.z -= 100;
				camera.lookAt( new THREE.Vector3() );
				render();
			}
			break;
		case 38:  // up = 38
			if(camera.position.y != 0){
				camera.position.x -= 100;
				camera.position.y -= 100;
				camera.lookAt( new THREE.Vector3() );
				render();
			}
			break;
		case 39:  // right = 39
			if(camera.position.z != 2000){
				camera.position.z += 100;
				camera.lookAt( new THREE.Vector3() );
				render();
			}
			break;
		case 40:  // down = 40
			camera.position.x += 100;
			camera.position.y += 100;
			camera.lookAt( new THREE.Vector3() );
			render();
			break;
	}
}
function onDocumentKeyUp( event ) {
	switch ( event.keyCode ) {
		// case 16: isShiftDown = false; break;
		// case 17: isCtrlDown = false; break;
		// case 18: isAltDown = false; break;
	}
}
function render() {
	renderer.render( scene, camera );
}
function setPen_type(i){
	if(i==3){
		// y-axis green pen
		rollOverPenMesh.rotation.x = 0;
		rollOverPenMesh.rotation.z = 0;
	} else if(i==4){
		// z-axis green pen
		rollOverPenMesh.rotation.x = 0.5*Math.PI;
		rollOverPenMesh.rotation.z = 0;
	} else if(i==5){
		// x-axis green pen
		rollOverPenMesh.rotation.z = 0.5*Math.PI;
	} else if(i==7){
		
	}
	pen_type=i;
}
