$(document).ready(function() {
	
	$.getScript("js/bootstrap/bootstrap.min.js", function(){
	
	   console.log("bootstrap loaded");
	   $.getScript("js/hogan/hogan-3.0.2.min.js", function(){
			
		   console.log("hogan loaded");
		   $.getScript("js/jquery/jquery-hashchange.js", function(){
				
			   console.log("jquery-hashchange loaded");
			   $.getScript("js/app.js", function(){
					
				   console.log("app loaded");
				   $.getScript("js/custom.js", function(){
						
					   console.log("custom loaded");
					
					});
				});
			});
		});
	});
});