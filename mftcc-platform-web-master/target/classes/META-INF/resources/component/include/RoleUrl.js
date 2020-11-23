             var myDBol = false;
			 function getRoleUrl(btnNo){
			 var myD = document.getElementById("role-ul-content");
			 var myUl = document.getElementById("url-ul");
			 myDBol = !myDBol;
			    if(myDBol){
			     $.ajax({
			       type:"GET",
			       url:webPath+"DemoActionAjax_requestAjax.action",
			       data:{
			       		query : btnNo
			       },
			       dataType:"json",
			       success:function(result){
			           myUl.innerHTML = "";
			           if(result.formData.length == 1){
			              window.location.href = result.formData[0].urlValue;
			           }else if(result.formData.length != 0){
			             var index = 0;
			             for(var link in result.formData){
			              index++;
			              var links = document.createElement("A");
			              var myLi = document.createElement("LI");
			              links.innerHTML = result.formData[link].urlName;
			              links.href = result.formData[link].urlValue;
			              if(index % 2 == 0){
			                 myLi.style.background = "#f9f9f9";
			              }else{
			                 myLi.style.background = "#ffffff";
			              }
			              myLi.appendChild(links);
			              console.log(myLi);
			              myUl.appendChild(myLi); 
			             }
			           }  
			       }
			    });
			      myD.style.opacity = 1;
			      myD.style.height = "auto";
			    }else{
			      myD.style.opacity = 0;
			      myD.style.height = 0;
			    } 
			    
			   
			 }