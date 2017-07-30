function signIn(){
	var email = $("#email").val();
	var password = $("#password").val();
    
	$.ajax({
        type: "post",
        url: "/user/Signin",
        data: {email:email, password:password },
        success: function(data, textStatus, xhr){
        	alert(data);
        	document.getElementById("list").innerHTML = "<span>"+data+"</span>";
        },
        error:function (xhr, ajaxOptions, thrownError){
            alert(xhr.status);
            alert(thrownError);
        } 
    });
}



function signUp(){
	var last_name = $("#last_name").val();
	var name = $("#name").val();
	var email = $("#email").val();
	var password = $("#password").val();
    
	$.ajax({
        type: "post",
        url: "/user/Signup",
        data: {last_name:last_name, name:name, email:email, password:password },
        success: function(data, textStatus, xhr){
        	document.getElementById("list").innerHTML = "<span>"+data+"</span>";
        },
        error:function (xhr, ajaxOptions, thrownError){
            alert(xhr.status);
            alert(thrownError);
        } 
    });
}


