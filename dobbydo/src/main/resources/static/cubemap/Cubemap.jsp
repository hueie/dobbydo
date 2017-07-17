
<%@ page contentType="text/html; charset=utf-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="html" uri="/WEB-INF/tlds/html.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	 --%>	
<script type="text/javascript">
var object_id = 0; //In Graph
var static_linked_id = 0;

function view(){
	var stack_id = $("#stack_id").val();
	window.location = 'mms_view.jsp?stack_id='+stack_id;
}

function savemap(){
    var str = "";
	var cube_list = [];
	//axis : none:0 y:1 z:2 x:3
	var cube_idx, pos_x, pos_y, pos_z, object_id, cube_type, linked_id, size, axis;
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
		            	//alert(objects[idx][key]);
		            	var jsonobj = JSON.parse(objects[idx][key]);
		            	str += ' obj_id : '+jsonobj.object_id +' ';
	        			str += ' object_id : ' + jsonobj.object_id +' ';
		            	str += ' cube_type : '+jsonobj.cube_type +' ';
	        			str += ' linked_id : ' + jsonobj.linked_id +' ';
	        			str += ' size : ' + jsonobj.size +' ';
	        			str += ' axis : ' + jsonobj.axis +' ';

	        			object_id = jsonobj.object_id;
	        			cube_type = jsonobj.cube_type;
	        			linked_id = jsonobj.linked_id;
	        			size = jsonobj.size;
	        			axis = jsonobj.axis;
		            }
		        }
			}
			cube_list.push({cube_idx:cube_idx, pos_x:pos_x, pos_y:pos_y, pos_z:pos_z, object_id:object_id, cube_type:cube_type, linked_id:linked_id, size:size, axis:axis });
		}
    }
	alert(str);
	
	var stack_id = $("#stack_id").val();
	var myJsonString = "{cube_list:"+JSON.stringify(cube_list)+"}";
    $.ajax({
        type: "post",
        url: "/ys/cubemap/CubemapSavemap.do",
        data: {"cube_list" : myJsonString, "stack_id":stack_id},
        success: function(msg){
            alert(msg);
        },
        error:function (xhr, ajaxOptions, thrownError){
            alert(xhr.status);
            alert(thrownError);
        } 
    });
	
}
function savestack(stackId){
	$.ajax({
        type: "post",
        url: "/ys/cubemap/CubemapSavestack.do",
        data: {"stackId" : stackId},
        success: function(msg){
            alert(msg);
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
        url: "/ys/cubemap/CubemapAddStack.do",
        data: {"stackNm" : stackNm, "stackRemk" : stackRemk},
        success: function(msg){
            alert(msg);
            getStackList();
        },
        error:function (xhr, ajaxOptions, thrownError){
            alert(xhr.status);
            alert(thrownError);
        } 
    });
}

function addBooksf(){
	var stackId = $("#stack_id").val();
	var booksfNm = $("#booksf_nm").val();
	var booksfRemk = $("#booksf_remk").val();
	var booksfFCnt = $("#booksf_y").val();
	
	$.ajax({
        type: "post",
        url: "/ys/cubemap/CubemapAddBooksf.do",
        data: {"stackId" : stackId, "booksfNm" : booksfNm, "booksfRemk" : booksfRemk, "booksfFCnt":booksfFCnt},
        success: function(msg){
            alert(msg);
            getBooksfList();
        },
        error:function (xhr, ajaxOptions, thrownError){
            alert(xhr.status);
            alert(thrownError);
        } 
    });
}

function setStackId(stackId){
	window.location = '/ys/cubemap/Cubemap.do?stack_id='+stackId;
}

function getStackList(){
	$("#stack_add_form").css("display","block");
	$("#booksf_add_form").css("display","none");
	$("#box_add_form").css("display","none");
	//서고
    var stack_id = 0;
    
	$.ajax({
        type: "post",
        url: "/ys/cubemap/CubemapStackList.do",
        data: { },
        contentType: "application/json",
        success: function(msg){
        	var objs = msg;//JSON.parse(msg);
        	var obj = objs.data;
        	var html = "";
        	for(var idx in obj){
        		html += "<span><button class=\"btn btn-xs btn-warning\" onclick=\"setStackId("+obj[idx].stackId+")\">서고배치</button> "+ obj[idx].stackId + ", " + obj[idx].stackNm + "</span><br>"; 
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
	//서가
    var stackId = $("#stack_id").val();
	var param = {"stackId":stackId };
	$.ajax({
        type: "post",
        url: "/ys/cubemap/CubemapBooksfList.do",
        data: param,
        success: function(msg){
        	var objs = msg;//JSON.parse(msg);
        	var obj = objs.data;
        	var html = "";
        	for(var idx in obj){
        		
        		html += "<span><button class=\"btn btn-xs btn-warning\" onclick=\"upNdown('static_booksf_y',"+obj[idx].booksfFCnt+");upNdown('linked_id',"+obj[idx].booksfId+");setPen_type(7)\">서가배치</button> "+ obj[idx].stackId + ", " + obj[idx].booksfId + ", " + obj[idx].booksfNm + "</span><br>"; 
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
	//상자
    var box_id = 0;
    
	$.ajax({
        type: "post",
        url: "/ys/cubemap/CubemapBoxList.do",
        data: { },
        success: function(msg){
        	var objs = JSON.parse(msg);
        	var obj = objs.data;
        	var html = "";
        	for(var idx in obj){
        		html += "<span><button class=\"btn btn-xs btn-warning\" onclick=\"upNdown('linked_id',"+obj[idx].boxId+");setPen_type(1)\">흰상자배치</button><button class=\"btn btn-xs btn-warning\" onclick=\"upNdown('linked_id',"+obj[idx].boxId+");setPen_type(2)\">빨간상자배치</button> "+ obj[idx].boxId + ", " + obj[idx].boxNo + "</span><br>"; 
        	}
        	document.getElementById("list").innerHTML = html;
        },
        error:function (xhr, ajaxOptions, thrownError){
            alert(xhr.status);
            alert(thrownError);
        } 
    });
}

function getBooksfView(booksf_id){
	//서가
	$.ajax({
        type: "post",
        url: "/ys/cubemap/CubemapBooksfView.do",
        data: { "booksf_id" : booksf_id},
        success: function(msg){
        	var obj = JSON.parse(msg);
        	document.getElementById("view").innerHTML = "<span>"+ obj.stackId + ", " + obj.booksfId + ", " + obj.booksfNm + "</span><br>"; 
        },
        error:function (xhr, ajaxOptions, thrownError){
            alert(xhr.status);
            alert(thrownError);
        } 
    });
}

function getBoxView(box_id){
	//상자
	$.ajax({
        type: "post",
        url: "/ys/cubemap/CubemapBoxView.do",
        data: { "box_id" : box_id},
        success: function(msg){
        	var obj = JSON.parse(msg);
        	document.getElementById("view").innerHTML = "<span>"+ obj.boxId + ", " + obj.boxNo + "</span><br>"; 
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
</script>



<!--Body content-->
<div id="content" class="clearfix">
	<div class="contentwrapper">
		<div class="row">
			<!-- subL Start -->
			<div id="contsLeft" class="col-lg-12">
				<div id="contsLeftTop">
					<!-- 네비게이션 시작 -->
					<div class="panel pannel-default">
						<div class="page-header">
							<h4>
								<span aria-hidden="true" class="icomoon-icon-quill orangedark"></span>
								<dfn>
									<span class="orange">
                   						특수
				                    	<span class="divider">
				                      		<span class="icon16 icomoon-icon-arrow-right-3 orangedark"></span>
				                    	</span>
				                    	3D 서가배치
				                    	<span class="divider">
				                      		<span class="icon16 icomoon-icon-arrow-right-3 orangedark"></span>
				                    	</span>
									</span>
								</dfn>
								<span class="orangedark">3D 서가배치</span> 
							</h4>
						</div>
					</div>
					<!-- 네비게이션 끝 -->
					<!-- 처리버튼 영역 시작 -->
						
					<div class="btn-header">
						<div class="row">
							<div class="col-lg-12">
							  <span class="right">
					                 <button class="btn btn-xs btn-warning"  onClick="javascript:fn_AddForm()" >
					                   <span class="icon12 icomoon-icon-file-plus white"></span> 등록
					                 </button>
					                 <button class="btn btn-xs btn-warning" onClick="javascript:fn_delete()">
					                   <span class="icomoon-icon-remove-3 white"></span> 삭제
					                 </button>
					             </span>	
							</div>
						</div>
					</div>
					
			           <!-- 처리버튼 영역 끝 -->
					<!-- Total Count & 목록개수 조절 시작 -->
					<form name="sendForm" method="post">
				           <input type="hidden" name="pageIndex" value="1">
				           <input type="hidden" name="orderColumn" value="">
				           <input type="hidden" name="orderType" value="">
				           
					<div class="search-header">
						<div class="row">
							<div class="col-lg-10">
								<span aria-hidden="true" class=" icomoon-icon-spinner-2"></span>
								전체건 : <span id="TrandelyListTotCnt"></span>
								<!-- 검색창 어코디언 실행 버튼 시작 -->
								<a class="btn btn-xs btn-default success accordion-toggle"	data-toggle="collapse" data-parent="#accordion"	href="#collapseSearch">
									 <span class="icomoon-icon-search-3"></span>
								</a>
								<!-- 검색창 어코디언 실행 버튼 끝 -->
							</div>
							<div class="col-lg-2">
								<select name="pageUnit" class="form-control input-sm" onchange="jsSearch();">
									<option>20</option>
									<option>30</option>
									<option>50</option>
									<option>100</option>
								</select>
							</div>
						</div>
					</div>
					<!-- Total Count & 목록개수 조절 끝 -->
					<!-- 검색창 어코디언 처리 영역 시작-->
					<div id="collapseSearch"
						class="gradient panel-collapse collapse">
						<!-- 검색창 시작 -->
						<div class="row">
							<div class="col-lg-12">
								<!-- 검색판넬 시작 -->
								<div class="panel panel-srch">
									<!-- 검색판넬 헤더 시작 -->
									<div class="panel-heading">
										<h4>
											<span><span class=" icomoon-icon-grid-2"></span>이관연기 신청서 검색</span>
											<span class="panel-heading-button right">
												<div class="btn-group">
													<a class="btn btn-default btn-sm" href="javascript:jsSrchReset()">
						                              <span	class="icomoon-icon-reply"></span>검색초기화
						                            </a>
						                            <a class="btn btn-default btn-sm"	href="javascript:jsSearch()">
						                              <span	class="icomoon-icon-search-3"></span>검색
						                            </a>
												</div>
											</span>
										</h4>
									</div>
									<!-- 검색판넬 헤더 끝 -->
									<!-- 검색판넬 바디 시작 -->
									<div class="panel-body">
											<div class="row">
												<div class="form-group">
													<label class="col-lg-2 control-label" for="normalInput">신청서 번호</label>
													<div class="col-lg-4">
														<input type="text" name="appro_num" class="form-control input-sm">
													</div>
													<label class="col-lg-2 control-label" for="normalInput">작성자</label>
													<div class="col-lg-4">
														<input type="text" name="tr_delay_name" class="form-control input-sm">
													</div>
												</div>
												<!-- End .form-group  -->
											</div>
											<div class="row">
												<div class="form-group">
													<label class="col-lg-2 control-label" for="picker">신청일자</label>
													<div class="col-lg-2">
														<span class="floatR padingT5">~</span> <input type="text"
															 name="regstart" id="regstart"
															class="form-control input-sm calendardatepicker input-date"  />
													</div>
													<div class="col-lg-2">
														<input type="text" name="regend" id="regend"
															class="form-control input-sm calendardatepicker input-date" />
													</div>														
													<label class="col-lg-2 control-label" for="normalInput">상태</label>
													<div class="col-lg-4">
														<html:cdselect name="tr_delay_status" css="form-control input-sm" codeType="TRAN03" defaultCode="" use="true" optionHead="- 전체 -" />
													</div>
												</div>
												<!-- End .form-group  -->
											</div>
									</div>
								</div>
								<!-- 검색판넬 바디 끝 -->
								<!-- 검색판넬 끝 -->
							</div>
						</div>
					<!-- 검색창 끝 -->
					</div>
					</form>
				</div>
				<!-- End .contsLeft -->
				<!-- 검색창 어코디언 처리 영역 끝-->
			
				<div id="cubemapview">
					<div style="width:60%;position:absolute">
						<div id="info">
							<!-- <strong>Small Shop Interior YS</strong><br> -->
							<strong>Left Key</strong>: Rotate Left, <strong>Right Key</strong>: Rotate Right<br><hr><br>
						</div>
					</div>
					<div>
						<div style="width:60%;display:inline;" id="container" ></div>
						<div style="width:30%;display:inline;float:right;" >
							<label class="col-lg-2 control-label" for="normalInput">서고 ID : </label>
							<div class="col-lg-4">
								<input class="form-control input-sm" type="text" id="stack_id" value="${CubemapVO.stack_id}" >
							</div>
							
							<label class="col-lg-2 control-label" for="normalInput">연결 ID : </label>
							<div class="col-lg-4">
								<input class="form-control input-sm" type="text" id="linked_id" value="0" >
							</div>
						
							<%--
							<button onclick="setPen_type(1)">white box</button><button onclick="setPen_type(2)">red box</button>
							<button onclick="setPen_type(7)">green rack</button><button onclick="setPen_type(0)">eraser</button><br><hr><br>
							 --%>
							<button class="btn btn-xs btn-warning" onclick="getStackList()">서고 목록 가져오기</button>
							<button class="btn btn-xs btn-warning" onclick="getBooksfList()">서가 목록 가져오기</button>
							<button class="btn btn-xs btn-warning" onclick="getBoxList()">상자 목록 가져오기</button>
							<button class="btn btn-xs btn-warning" onclick="setPen_type(0)">지우개</button>
							<button class="btn btn-xs btn-warning" onclick="setPen_type(999)">상세정보</button>
							<button class="btn btn-xs btn-warning" onclick="savemap()">배치도 저장</button> 
							<button class="btn btn-xs btn-warning" onclick="savestack(${CubemapVO.stack_id})">서고,사가,상자 배치 저장</button> 
							<button class="btn btn-xs btn-warning" onclick="location.reload()">되돌리기</button> 
							<%--
							<button onclick="view()">확인</button> 
							 --%>
							<br><hr><br>
							<div id="view"></div>
							<br><hr><br>
							<div id="stack_add_form" style="display:none">
								<span>
								<button class="btn btn-xs btn-warning" onclick="addStack()">새 서고등록</button> 
								서고명 : <input type="text" id="stack_nm">
								비고 : <input type="text" id="stack_remk" >
								</span>
								<br>
							</div>
							<div id="booksf_add_form" style="display:none">
								<span>
								<button class="btn btn-xs btn-warning" onclick="addBooksf()">새 서가등록</button> 
								서가명 : <input type="text" id="booksf_nm">
								<!-- 단계수 : <input type="text" id="booksf_f_cnt"><br> -->
								비고 : <input type="text" id="booksf_remk" >
								</span>
								<br><br>
								<div style="display:inline">x : <input type="text" id="booksf_x" style="width:20px" value="1"></input><button onclick="upNdown('booksf_x','1')">+</button><button onclick="upNdown('booksf_x','-1')">-</button></div>
								<div style="display:inline">z : <input type="text" id="booksf_z" style="width:20px" value="1"></input><button onclick="upNdown('booksf_z','1')">+</button><button onclick="upNdown('booksf_z','-1')">-</button></div>
								<div style="display:inline">단계수 : <input type="text" id="booksf_y" style="width:20px" value="1"></input><button onclick="upNdown('booksf_y','1')">+</button><button onclick="upNdown('booksf_y','-1')">-</button></div>
							</div>
							
							<div id="box_add_form" style="display:none">
								<%--
								<span>
								<button onclick="">새 상자등록</button> 
								부서명 : <input type="text" id="org_nm"><input type="hidden" name="org_cd" id="org_cd">
								상자번호 : <input type="text" id="box_no" >
								</span>
								<br>
								 --%>
							</div>
							<br><hr><br>
							<div id="list"></div>
						</div>
					</div>
					
					<script src="/js/threejs/three.js"></script>
					<!-- https://github.com/mrdoob/three.js/blob/master/build/three.js -->
					<script src="/js/threejs/Detector.js"></script>
					<!-- https://github.com/mrdoob/three.js/blob/master/examples/js/Detector.js -->
			
					<script>
						if ( ! Detector.webgl ) Detector.addGetWebGLMessage();
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
						var pen_type = 1; //999; //0:eraser//1:white box//2:red box//3,4,5:y,z,x-axis green pen//6:rack
						var cubes = [];
						
						initpushdata();
						init();
						render();
						
						function initpushdata(){
							var obj = ${CubemapVO.cubes};//JSON.parse(${CubemapVO.cubes});
							for(var idx in obj.data){
								cubes.push(obj.data[idx]);
							}
						}
						
						function init() {
							//container = document.createElement( 'div' );
							//document.body.appendChild( container );
							container = document.getElementById( 'container' );
							
							/*Create Scene*/
							scene = new THREE.Scene();
							/*Camera Setting*/
							camera = new THREE.PerspectiveCamera( 45, window.innerWidth*6/10 / window.innerHeight, 1, 10000 );
							camera.position.set( 1000, 1000, 0 );
							camera.lookAt( new THREE.Vector3() );
							
							/*Blue Cube Setting (roll-over helpers)*/
							rollOverGeo = new THREE.BoxGeometry( 50, 50, 50 );
							rollOverMaterial = new THREE.MeshBasicMaterial( { color: 0x0000ff, opacity: 0.5, transparent: true } );
							rollOverMesh = new THREE.Mesh( rollOverGeo, rollOverMaterial ); 
							scene.add( rollOverMesh );
							
							/*Start Rack*/
							rollOverBooksfYGeo = new THREE.BoxGeometry(8,50*booksf_y,8);
							rollOverBooksfYMaterial = new THREE.MeshBasicMaterial( { color: 0x0000ff, opacity: 0.5, transparent: true } );
							realBooksfYMaterial = new THREE.MeshBasicMaterial( { color: 0x000000, opacity: 1, transparent: true } );
							
							rollOverBooksfYMesh[1] = new THREE.Mesh( rollOverBooksfYGeo, realBooksfYMaterial ); 
							rollOverBooksfYMesh[1].rotation.y = 0.5*Math.PI;
							//rollOverBooksfYMesh.position.copy( intersect.point ).add( intersect.face.normal );
							rollOverBooksfYMesh[1].position.divideScalar( 50 ).round().multiplyScalar( 50 );//.addScalar( 25 );
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
							//rollOverBooksfZMesh.position.copy( intersect.point ).add( intersect.face.normal );
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
							//rollOverBooksfXMesh.position.copy( intersect.point ).add( intersect.face.normal );
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
							/*End Rack*/
							
							
							/*Textures !!! */
							/*White Cube Setting*/
							cubeGeo = new THREE.BoxGeometry( 50, 50, 50 );
							//cubeMaterial = new THREE.MeshLambertMaterial( { color: 0xfeb74c, map: new THREE.TextureLoader().load( "textures/square-outline-textured.png" ) } );
							cubeMaterial = new THREE.MeshLambertMaterial( { color: 0xffffff } ); //White Cube
							/*Red Cube Setting*/
							cctvMaterial = new THREE.MeshLambertMaterial( { color: 0xff0000 } ); //Red Cube
							
							
							/*Grid Floor Setting*/
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
							
							/*Raycaster Setting : Renders a 3D world based on a 2D map*/
							raycaster = new THREE.Raycaster();
							mouse = new THREE.Vector2();
							var geometry = new THREE.PlaneBufferGeometry( 1000, 1000 );
							geometry.rotateX( -Math.PI/2 );
							plane = new THREE.Mesh( geometry, new THREE.MeshBasicMaterial( { visible: false } ) );
							scene.add( plane );
							objects.push( plane );
							
							/*Lights Setting*/
							var ambientLight = new THREE.AmbientLight( 0x606060 );
							scene.add( ambientLight );
							var directionalLight = new THREE.DirectionalLight( 0xffffff );
							directionalLight.position.set( 1, 0.75, 0.5 ).normalize();
							scene.add( directionalLight );
							
							
							/* Build Boxes*/
							for(var key in cubes){
								var voxel;
								if(cubes[key]['cube_type'] == 1){
									voxel = new THREE.Mesh( cubeGeo, cubeMaterial );
									voxel.position.set(cubes[key]['x'], cubes[key]['y'], cubes[key]['z']);
								} else if(cubes[key]['cube_type'] == 2){
									voxel = new THREE.Mesh( cubeGeo, cctvMaterial );
									voxel.position.set(cubes[key]['x'], cubes[key]['y'], cubes[key]['z']);
								} else if(cubes[key]['cube_type'] == 3){
									// green pen
									voxel = new THREE.Mesh( rollOverPenGeo, rollOverPenMaterial );
									voxel.position.set(cubes[key]['x'], cubes[key]['y'], cubes[key]['z']);
								} else if(cubes[key]['cube_type'] == 4){
									//green pen
									voxel = new THREE.Mesh( rollOverPenGeo, rollOverPenMaterial );
									voxel.rotation.x = 0.5*Math.PI;
									voxel.rotation.z = 0;
									voxel.position.set(cubes[key]['x'], cubes[key]['y'], cubes[key]['z']);
								} else if(cubes[key]['cube_type'] == 5){
									//green pen
									voxel = new THREE.Mesh( rollOverPenGeo, rollOverPenMaterial );
									voxel.rotation.x = 0.5*Math.PI;
									voxel.rotation.z = 0.5*Math.PI;
									voxel.position.set(cubes[key]['x'], cubes[key]['y'], cubes[key]['z']);
								} else if(cubes[key]['cube_type'] == 7){
									//axis : none:0 y:1 z:2 x:3
									if(cubes[key]['axis'] == 1){
										//YMesh
										var rollOverBooksfYGeo = new THREE.BoxGeometry(8,50*cubes[key]['size'],8);
										var rollOverBooksfYMaterial = new THREE.MeshBasicMaterial( { color: 0x000000, opacity: 1, transparent: true } );
										voxel = new THREE.Mesh( rollOverBooksfYGeo, rollOverBooksfYMaterial ); 
										voxel.rotation.y = 0.5*Math.PI;
										voxel.position.set(cubes[key]['x'], cubes[key]['y'], cubes[key]['z']);
									} else if(cubes[key]['axis'] == 2){
										//ZMesh
										var rollOverBooksfZGeo = new THREE.BoxGeometry(8,8,50*cubes[key]['size']);
										var rollOverBooksfZMaterial = new THREE.MeshBasicMaterial( { color: 0x000000, opacity: 1, transparent: true } );
										voxel = new THREE.Mesh( rollOverBooksfZGeo, rollOverBooksfZMaterial ); 
										voxel.position.set(cubes[key]['x'], cubes[key]['y'], cubes[key]['z']);
									} else if(cubes[key]['axis'] == 3){
										//XMesh
										var rollOverBooksfXGeo = new THREE.BoxGeometry(50*cubes[key]['size'],8,8);
										var rollOverBooksfXMaterial = new THREE.MeshBasicMaterial( { color: 0x000000, opacity: 1, transparent: true } );
										voxel = new THREE.Mesh( rollOverBooksfXGeo, rollOverBooksfXMaterial ); 
										voxel.rotation.x = 0.5*Math.PI;
										voxel.position.set(cubes[key]['x'], cubes[key]['y'], cubes[key]['z']);
									}
								}
								voxel.name = "{ \"cube_type\":"+cubes[key]['cube_type']+", \"linked_id\":"+cubes[key]['linked_id']+", \"object_id\":"+cubes[key]['object_id']+", \"size\":"+cubes[key]['size']+", \"axis\":"+cubes[key]['axis']+" }";
								scene.add( voxel );
								objects.push( voxel );
							}
							
							/*Render*/
							renderer = new THREE.WebGLRenderer( { antialias: true } );
							renderer.setClearColor( 0xf0f0f0 );
							renderer.setPixelRatio( window.devicePixelRatio );
							renderer.setSize( window.innerWidth*6/10, window.innerHeight );
							container.appendChild( renderer.domElement );
							/*Event*/
							document.getElementById('container').addEventListener( 'mousemove', onDocumentMouseMove, false );
							document.getElementById('container').addEventListener( 'mousedown', onDocumentMouseDown, false );
							document.addEventListener( 'keydown', onDocumentKeyDown, false );
							document.addEventListener( 'keyup', onDocumentKeyUp, false );
							//When Window is Resized
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
							
							//alert(top_margin + " "+left_margin);
							mouse.set( ( (event.clientX-left_margin) / (window.innerWidth*6/10) ) * 2 - 1, - ( (event.clientY-top_margin) / window.innerHeight ) * 2 + 1 );
							raycaster.setFromCamera( mouse, camera );
							var intersects = raycaster.intersectObjects( objects );
							if ( intersects.length > 0 ) {
								var intersect = intersects[ 0 ];
								
								//alert("Point : "+intersect.point.x+" face : "+intersect.face.normal.x+"Point : "+intersect.point.y+" face : "+intersect.face.normal.y); 
								if(pen_type == 0 || pen_type == 999){
									
								} else if(pen_type == 1 || pen_type == 2){
									rollOverMesh.position.copy( intersect.point ).add( intersect.face.normal );
									rollOverMesh.position.divideScalar( 50 ).floor().multiplyScalar( 50 ).addScalar( 25 );
								} else if(pen_type == 7){
									rollOverBooksfYMesh[1].scale.y = booksf_y;//50*booksf_y;
									rollOverBooksfYMesh[2].scale.y = booksf_y;
									rollOverBooksfYMesh[3].scale.y = booksf_y;//50*booksf_y;
									rollOverBooksfYMesh[4].scale.y = booksf_y;
									
									rollOverBooksfYMesh[1].position.copy( intersect.point ).add( intersect.face.normal );
									rollOverBooksfYMesh[1].position.divideScalar( 50 ).round().multiplyScalar( 50 );//.addScalar( 25 );
									rollOverBooksfYMesh[1].position.y += 25*booksf_y;
									
									rollOverBooksfYMesh[2].position.copy( intersect.point ).add( intersect.face.normal );
									rollOverBooksfYMesh[2].position.divideScalar( 50 ).round().multiplyScalar( 50 );//.addScalar( 25 );
									rollOverBooksfYMesh[2].position.y += 25*booksf_y;
									rollOverBooksfYMesh[2].position.x += 50*booksf_x;
									
									rollOverBooksfYMesh[3].position.copy( intersect.point ).add( intersect.face.normal );
									rollOverBooksfYMesh[3].position.divideScalar( 50 ).round().multiplyScalar( 50 );//.addScalar( 25 );
									rollOverBooksfYMesh[3].position.y += 25*booksf_y;
									rollOverBooksfYMesh[3].position.z += 50*booksf_z;
									
									rollOverBooksfYMesh[4].position.copy( intersect.point ).add( intersect.face.normal );
									rollOverBooksfYMesh[4].position.divideScalar( 50 ).round().multiplyScalar( 50 );//.addScalar( 25 );
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
											//alert("obj:"+jsonobj.object_id);
											//Rack
											var erased_id = jsonobj.object_id;
											var objectsdel_flag = false, objectsdel_idx=0;
											for(var idx in objects){
												//alert(idx);
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
												//objects.splice( objects.indexOf( objects[objectsdel_idx-12] ), 12 );
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
									voxel.name = "{ \"cube_type\":1, \"linked_id\":"+static_linked_id+", \"object_id\":"+object_id+", \"size\":1, \"axis\":0 }";
									object_id++;
								} else if ( pen_type == 2 ) {
									// red box
									voxel = new THREE.Mesh( cubeGeo, cctvMaterial );
									voxel.position.copy( intersect.point ).add( intersect.face.normal );
									voxel.position.divideScalar( 50 ).floor().multiplyScalar( 50 ).addScalar( 25 );
									voxel.name = "{ \"cube_type\":2, \"linked_id\":"+static_linked_id+", \"object_id\":"+object_id+", \"size\":1, \"axis\":0 }";
									object_id++;
								} else if( pen_type == 7) {
									var voxel;
									for(var idx=0; idx<12; idx++){
										var remainder = idx%4;
										var quotient = parseInt(idx/4);
										if(quotient == 0){
											voxel = rollOverBooksfYMesh[remainder+1].clone();
											voxel.name = "{ \"cube_type\":7, \"linked_id\":"+static_linked_id+", \"object_id\":"+object_id+", \"size\":"+booksf_y+", \"axis\":1 }";
										} else if(quotient == 1){
											voxel = rollOverBooksfZMesh[remainder+1].clone();
											voxel.name = "{ \"cube_type\":7, \"linked_id\":"+static_linked_id+", \"object_id\":"+object_id+", \"size\":"+booksf_z+", \"axis\":2 }";
										} else if(quotient == 2){
											voxel = rollOverBooksfXMesh[remainder+1].clone();
											voxel.name = "{ \"cube_type\":7, \"linked_id\":"+static_linked_id+", \"object_id\":"+object_id+", \"size\":"+booksf_x+", \"axis\":3 }";
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
								//case 16: isShiftDown = true; break; //shift key
								//case 17: isCtrlDown = true; break; //ctrl key
								//case 18: isAltDown = true; break; //ctrl key
								case 37:  //left = 37
									if(camera.position.z != -2000){
										camera.position.z -= 100;
										camera.lookAt( new THREE.Vector3() );
										render();
									}
									break;
								case 38:  //up = 38
									if(camera.position.y != 0){
										camera.position.x -= 100;
										camera.position.y -= 100;
										camera.lookAt( new THREE.Vector3() );
										render();
									}
									break;
								case 39:  //right = 39
									if(camera.position.z != 2000){
										camera.position.z += 100;
										camera.lookAt( new THREE.Vector3() );
										render();
									}
									break;
								case 40:  //down = 40
									camera.position.x += 100;
									camera.position.y += 100;
									camera.lookAt( new THREE.Vector3() );
									render();
									break;
							}
						}
						function onDocumentKeyUp( event ) {
							switch ( event.keyCode ) {
								//case 16: isShiftDown = false; break;
								//case 17: isCtrlDown = false; break;
								//case 18: isAltDown = false; break;
							}
						}
						function render() {
							renderer.render( scene, camera );
						}
						function setPen_type(i){
							if(i==3){
								//y-axis green pen
								rollOverPenMesh.rotation.x = 0;
								rollOverPenMesh.rotation.z = 0;
							} else if(i==4){
								//z-axis green pen
								rollOverPenMesh.rotation.x = 0.5*Math.PI;
								rollOverPenMesh.rotation.z = 0;
							} else if(i==5){
								//x-axis green pen
								rollOverPenMesh.rotation.z = 0.5*Math.PI;
							} else if(i==7){
								
							}
							pen_type=i;
						}
					</script>
				</div>
			</div>
		</div>
		<!-- End .row -->
	</div>
	<!-- End contentwrapper -->
</div>
<!-- End #content -->
