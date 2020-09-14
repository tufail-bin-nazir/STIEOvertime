function gotoPage(page){
	toggleBlockUI('block');
	var form = document.getElementById("form");

	if(page == 'registeration')
	{
		
		form.btnaction.value = "continue";
		form.formaction.value = "registeration";
		
	}else if(page == 'login'){
		
		form.btnaction.value = "continue";
		form.formaction.value = "login";
		
	}else if(page == 'dashboard'){
			
		form.btnaction.value = "continue";
		form.formaction.value = "dashboard";
			
	}else if(page== 'create'){
		form.btnaction.value = "continue";
		form.formaction.value = "create";
	}else if(page =='history'){
		form.btnaction.value = "continue";
		form.formaction.value = "history";
	}else if(page =='home'){

		form.btnaction.value = "continue";
		form.formaction.value = "home";
	}else if(page =='update'){
		
		form.btnaction.value = "continue";
		form.formaction.value = "update";
	}else if(page =='search'){
		
		form.btnaction.value = "continue";
		form.formaction.value = "search";
	}
	form.submit();

	
}

function toggleBlockUI(option){
	
	if(option == 'block'){
		   
			document.getElementById("blockUI").style.display = "block";	  
	}else{
		    document.getElementById("blockUI").style.display = "none";	   
	    
	}

}


function toggleSite(){
	document.getElementById('siteDiv').style.display = "block";
	document.getElementById('siteDiv').classList.remove("hide");
	document.getElementById('siteDiv').classList.remove("show");
	document.getElementById('woDiv').classList.remove("show");

	document.getElementById('woDiv').style.display = "none";

}

function toggleWorkorder(){
	
	
	document.getElementById('breakdowntype').style.display = "block";

	/*document.getElementById('siteDiv').style.display = "none";
	document.getElementById('woDiv').classList.remove("hide");
	document.getElementById('woDiv').classList.remove("show");
	document.getElementById('siteDiv').classList.remove("show");

	document.getElementById('woDiv').style.display = "block";*/
}


function toggleBreakDown(val){
	if(val == 'Yes'){
		document.getElementById('siteDiv').style.display = "block";
		document.getElementById('siteDiv').classList.remove("hide");
		document.getElementById('woDiv').style.display = "none";
		document.getElementById('woDiv').classList.remove("show");
        document.getElementById('breakdowntype').style.display = "none";
    	document.getElementById('breakdowntype').classList.remove("show");


	}else{
		
		document.getElementById('siteDiv').style.display = "none";
		document.getElementById('siteDiv').classList.remove("show");
        document.getElementById('breakdowntype').style.display = "block";
		document.getElementById('breakdowntype').classList.remove("hide");

	}
}


function toggleBreakDownType(val){
	
	if(val=='WorksOrder'){
		document.getElementById('woDiv').style.display = "block";
		document.getElementById('woDiv').classList.remove("hide");

		
	}else if(val=='PreventiveMaintenance'){
		document.getElementById('woDiv').style.display = "none";
		
	}else if(val=='Others'){
		document.getElementById('woDiv').style.display = "none";

	}
}


function viewOvertime(id){
	toggleBlockUI('block');
	var form = document.getElementById("form");
	form.btnaction.value = "view";
	form.formaction.value = "dashboard";
	form.id.value= id;
	form.submit();
}

function accept(id){
	toggleBlockUI('block');
	var form = document.getElementById("form");
	form.btnaction.value = "accept";
	form.formaction.value = "dashboard";
	form.id.value= id;
	form.submit();
}


function reject(id){
	toggleBlockUI('block');
	var form = document.getElementById("form");
	form.btnaction.value = "reject";
	form.formaction.value = "dashboard";
	form.id.value= id;
	form.submit();
}


function addremark(id){
	toggleBlockUI('block');
	var form = document.getElementById("form");
	form.btnaction.value = "remarkByEngineer";
	form.formaction.value = "dashboard";
	form.id.value= id;
	form.submit();
	
}

function cncl(id){
	
	toggleBlockUI('block');
	var form = document.getElementById("form");
	form.btnaction.value = "cancel";
	form.formaction.value = "dashboard";
	form.id.value= id;
	form.submit();
	
}


function pendingcancel(id){
	
	toggleBlockUI('block');
	var form = document.getElementById("form");
	form.btnaction.value = "pendingcancel";
	form.formaction.value = "dashboard";
	form.id.value= id;
	form.submit();
	
}

function search(){
	toggleBlockUI('block');
	var form = document.getElementById("form");
	form.btnaction.value = "search";
	form.formaction.value = "dashboard";
	
	form.submit();
}

function exportToExcel(){
	toggleBlockUI('block');
	var form = document.getElementById("form");
	form.btnaction.value = "export";
	form.formaction.value = "dashboard";
	
	form.submit();
	
	toggleBlockUI('none');

}

function approve(id){
	toggleBlockUI('block');
	var form = document.getElementById("form");
	form.btnaction.value = "approve";
	form.formaction.value = "dashboard";
	form.id.value= id;
	form.submit();
}

function approvecancel(id){
	toggleBlockUI('block');
	var form = document.getElementById("form");
	form.btnaction.value = "approvecancel";
	form.formaction.value = "dashboard";
	form.id.value= id;
	form.submit();
}



function validateForm(){

   
	
    var isForvalid = true;
    var name = $('#name').val();
    var department = $('#department').val();
    var startdate = $('#startdate').val();
    var starttime = $('#starttime').val();
    
    var enddate = $('#startdate').val();
    var endtime = $('#starttime').val();
    var purpose = $('#purpose').val();
    var breakdown = document.getElementsByName("breakdown");
    
    var breakdowntype =document.getElementsByName("breakdowntype");
    var site = $('#site').val();
    
    var worder = $('#worder').val();
    var manager = $('#manager').val();
    
    console.log("name" + name + "department" + department + "startdate" + startdate + "starttime" + starttime + "enddate" + enddate
    		 + "endtime" + endtime + "purpose" + purpose + "site" + site + "worder" + worder+ "manager" +manager);
   

     $('.error').hide();

        if(name == ""){
        	isForvalid = false;
            $('#nameLabel').after('&nbsp&nbsp&nbsp<span class="error"> Please enter technician name </span>');
        } 
        
        if(department == ""){
        	isForvalid = false;
            $('#departmentLabel').after('&nbsp&nbsp&nbsp<span class="error"> Please enter Department </span>');
        }
        
        if(startdate == "" || starttime == ""){
        	isForvalid = false;
            $('#startLabel').after('&nbsp&nbsp&nbsp<span class="error"> Please enter Start Date or Start Time </span>');
        }
        
        if(enddate == "" || endtime == ""){
        	isForvalid = false;
            $('#endLabel').after('&nbsp&nbsp&nbsp<span class="error"> Please enter End Date or End Time </span>');
        }
        
        if(purpose == ""){
        	isForvalid = false;
            $('#purposeLabel').after('&nbsp&nbsp&nbsp<span class="error"> Please enter Purpose </span>');
        }
        
        var isBreakDown = false;
        var count =0;
        for (i = 0; i < breakdown.length; i++) {
        	if(breakdown[i].checked==true){
        		++count;
        		isBreakDown = true;
        		break
        	}else{
        		--count;
        	}
        }
        if(!isBreakDown){
        	isForvalid = false;
        	 $('#breakdownLabel').after('&nbsp&nbsp&nbsp<span class="error"> Please select Breakdown </span>');
        }
       
        
        if(isBreakDown){
          if(count == 1){
        	  if(site == ""){
        		  isForvalid = false;
        	     $('#siteLabel').after('&nbsp&nbsp&nbsp<span class="error"> Please enter Site </span>');
        	  }
          }else if(count==0){
        	  var isBreakDownType= false;
        	  var bcounter = 0;
        	  for (i = 0; i < breakdowntype.length; i++) {
        		  if(breakdowntype[i].checked==true){
              		++bcounter;
              		isBreakDownType = true;
              		break
              	}else{
              		--bcounter;
              	}
        	  }
        	  if(!isBreakDownType){
        		  isForvalid = false;
        		  $('#breakdowntypeLabel').after('&nbsp&nbsp&nbsp<span class="error"> Please select any option Above </span>');
        	  }
        	  else {
        		  if(bcounter == 1){
        			  if(worder == ""){
        				    isForvalid = false;
        		            $('#worderLabel').after('&nbsp&nbsp&nbsp<span class="error"> Please enter Works Order Number </span>');
        		        } 
        		  }
        	  }
        	  
          }
        }
        
        if(manager == ""){
        	isForvalid = false;
            $('#managerLabel').after('&nbsp&nbsp&nbsp<span class="error"> Please select Manager To Route </span>');
        }

     
        console.log(isForvalid);
        return isForvalid
}   

function validateSearchForm(){
	
        var isFormvalid = true;
        
        $('.error').hide();

	    var startdate = $('#startdate').val();
	    var enddate = $('#enddate').val();
	    
	    if(startdate == ""){
        	isFormvalid = false;
            $('#startDateLabel').after('&nbsp&nbsp&nbsp<span class="error"> Please enter Start Date</span>');
        }
	    
	    if(enddate == ""){
	    	
	    	isFormvalid = false;
            $('#endDateLabel').after('&nbsp&nbsp&nbsp<span class="error"> Please enter End Date</span>');
	    	
	    }
	    
	    return isFormvalid;

}

function validateRejectModal(){
	
	  var isFormvalid = true;
	  
	  $('.error').hide();

	  var remarkbyman = $.trim($("#remarkbyman").val());
	  if(remarkbyman == ""){
	      isFormvalid = false;
          $('#remarkbyman').after('&nbsp&nbsp&nbsp<span class="error"> Please enter Remarks</span>');
	    	
	    }
	  return isFormvalid;
}

function validateRemarkModal(){

	  var isFormvalid = true;
	  
	  $('.error').hide();
	
	  var remarkbyeng = $.trim($("#remarkbyeng").val());

	  
	  if(remarkbyeng == ""){
	      isFormvalid = false;
        $('#remarkbyeng').after('&nbsp&nbsp&nbsp<span class="error"> Please enter Remarks</span>');
	    	
	    }
	  return isFormvalid;
}

function reasonforcancelModal(){
	
	 var isFormvalid = true;
	 
	 $('.error').hide();
		
	  var cancel = $.trim($("#cancel").val());

	  
	  if(cancel == ""){
	      isFormvalid = false;
       $('#cancel').after('&nbsp&nbsp&nbsp<span class="error"> Please enter Remarks</span>');
	    	
	    }
	  return isFormvalid;
}


function generate(){
	toggleBlockUI('block');
	var form = document.getElementById("form");
	form.btnaction.value = "generate";
	form.formaction.value = "dashboard";
	
	form.submit();
}

function register(){
	toggleBlockUI('block');
	var form = document.getElementById("form");
	form.btnaction.value = "register";
	form.formaction.value = "dashboard";
	
	form.submit();
}

function validateLogin(){
	 var isFormvalid = true;
	 
	 $('.error').hide();
		
	  var nric = $.trim($("#nric").val());
	  
	  var password = $.trim($("#password").val());

	  
	  if(nric == ""){
	      isFormvalid = false;
           $('#nricLabel').after('&nbsp&nbsp&nbsp<span class="error"> Please enter NRIC</span>');
	    	
	    }
	  
	  if(password ==""){
		  isFormvalid = false;
          $('#passwordLabel').after('&nbsp&nbsp&nbsp<span class="error"> Please enter Password</span>');

	  }
	  return isFormvalid;
}

function validateOTP(){
	  var isFormvalid = true;
	  var nric = $.trim($("#nric").val());
	  
	  $('.error').hide();
	  
	  if(nric == ""){
	      isFormvalid = false;
           $('#nricLabel').after('&nbsp&nbsp&nbsp<span class="error"> Please enter NRIC</span>');
	    	
	    }
	  
	  return isFormvalid;
}

function validateRegister(){
	
	var isFormvalid = true;
	$('.error').hide();
	
	
	
	
	
	var tovalidate = "";
		
	
	var nodes = document.querySelectorAll("input[type=text]");
		for (var i=0; i<nodes.length; i++){
			if (nodes[i].id=='otp'){
				tovalidate = "otp"
					break;
			}
		}
		   
    var pnodes = document.querySelectorAll("input[type=password]");
		if(pnodes.length==1){
			tovalidate = "password";
		}			   
	
	 console.log(tovalidate);
	 
	 if(tovalidate == 'otp'){
		 
		 var otp = $.trim($("#otp").val());
		 if(otp == ""){
			  isFormvalid = false;
			  $('#otpLabel').after('&nbsp&nbsp&nbsp<span class="error"> Please enter OTP</span>');
		 }
		 
	 }else if(tovalidate == 'password'){
		 
		 var password = $.trim($("#password").val());
		 if(password == ""){
			  isFormvalid = false;
			  $('#passwordLabel').after('&nbsp&nbsp&nbsp<span class="error"> Please enter Password</span>');
		 }
	 }
	
		 
	
	return isFormvalid;
	
	
}





