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
        		html += "<span><button class=\"btn btn-xs btn-warning\" onclick=\"getLinesfsByCamId("+objs[idx].fileupload_reg_id+");\">사진 선택</button>"+ objs[idx].fileupload_id + ", " + objs[idx].file_nm + ", " + objs[idx].fileupload_reg_id + "</span><br>"; 
        	}
        	document.getElementById("list").innerHTML = html;
        },
        error:function (xhr, ajaxOptions, thrownError){
            alert(xhr.status);
            alert(thrownError);
        } 
    });
}



var canvas, ctx, img;
var prev_pointbool = false;
var start_x, start_y;
var end_x, end_y;
var line_list = [];
var line_id = 0;

function filltext(canvas, x, y, text) {
	var context = canvas.getContext('2d');
	context.font = 'italic 40pt Calibri';
	context.fillStyle = "#000000";
	context.fillText(text, x, y);
}

function fillpoint(canvas, x, y) {
	var context = canvas.getContext('2d');
	context.fillStyle = "#000000";
	context.fillRect(x - 5, y - 5, 10, 10);
}

function getMousePos(canvas, evt) {
	var rect = canvas.getBoundingClientRect();
	return {
		x : evt.clientX - rect.left,
		y : evt.clientY - rect.top
	};
}

function fillline(canvas, start_x, start_y, end_x, end_y) {
	var context = canvas.getContext('2d');
	context.beginPath();
	context.moveTo(start_x, start_y);
	context.lineTo(end_x, end_y);
	context.stroke();
}

function getImg() {
	img = document.getElementById("sampleimg");

	canvas = document.getElementById("myCanvas");
	ctx = canvas.getContext("2d");
	ctx.drawImage(img, 0, 0);

	/*
	 * canvas.addEventListener('mousemove', function(evt) { var mousePos =
	 * getMousePos(canvas, evt); var message = 'Mouse position: ' + mousePos.x +
	 * ',' + mousePos.y + ''; //writediv.append(message); fillpoint(canvas,
	 * mousePos.x, mousePos.y); }, false);
	 */
	canvas.addEventListener('click', function(evt) {
		var mousePos = getMousePos(canvas, evt);
		var message = 'Mouse position: ' + mousePos.x + ',' + mousePos.y + '';

		fillpoint(canvas, mousePos.x, mousePos.y);
		if (prev_pointbool) {
			prev_pointbool = false;
			end_x = mousePos.x;
			end_y = mousePos.y;
			fillline(canvas, start_x, start_y, end_x, end_y);
			filltext(canvas, start_x, start_y, line_id);
			line_list.push({
				line_id : line_id,
				start_x : start_x,
				start_y : start_y,
				end_x : end_x,
				end_y : end_y
			});
			line_id++;
		} else {
			prev_pointbool = true;
			start_x = mousePos.x;
			start_y = mousePos.y;
		}
	}, false);
};

function clearcanvas() {
    var writediv = document.getElementById("writediv");
	writediv.innerHTML = "";
	ctx.clearRect(0, 0, canvas.width, canvas.height);
	ctx.drawImage(img, 0, 0);
}

function sendtodb() {
    var writediv = document.getElementById("writediv");
	var msg = "";
	// alert(line_list.length);
	for (var i = 0; i < line_list.length; i++) {
		msg += line_list[i].line_id + ' START : x' + line_list[i].start_x
				+ ' y' + line_list[i].start_y + '<br>';
		msg += line_list[i].line_id + ' END   : x' + line_list[i].end_x + ' y'
				+ line_list[i].end_y + '<br><br>';
	}
	// alert(msg);
	writediv.innerHTML = msg;
	var myJsonString = "{line_list:" + JSON.stringify(line_list) + "}";
	$.ajax({
		type : "post",
		url : "/cammapping/Cammapping",
		data : {
			"line_list" : myJsonString
		},
		success : function(msg) {
			alert(msg);
		},
		error : function(xhr, ajaxOptions, thrownError) {
			alert(xhr.status);
			alert(thrownError);
		}
	});
}