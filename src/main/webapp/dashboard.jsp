<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
	
    
    

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Untitled Document</title>
<link rel="stylesheet" href="style.css" />

<link rel="stylesheet" href="style480.css"  media="screen and (max-device-width: 480px)"  />
<link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>

</head>

<body>


<div id="container">
	<div id="header">
    	
    		<div id="h-left">
        	</div>
        
        	<div id="h-right">
            <ul id="h-right-menu">
            	Hello Athul,
                <li><a href="#">Logout</a></li>
            </ul>
        	</div>
       

    
  </div>
    
    
<div class="clear"></div>  
 <!--
 <div id="main-menu">
<ul id="main-menu-ul">
	
	<li><a href="#">Home</a></li>
    <li><a href="#">Simulator</a></li>
    
</ul><!-- End Mainmenu

</div>

-->
<div id="main-container-center">
<div id="main-container">

	<div id="main-prediction-header">
    	<!--<div id="right">Today's Expectation : <span id="tmrw-number">302</span></div>-->
		<div id="left">Expectation : <span id="tmrw-number">${TommorrowExpectation}</span></div>
    </div>
    


   <div class="clear"></div>
    
<div id="prediction-sort">
    	<h1>Whatever</h1>
    <div class="clear"></div>
    	<div id="prediction-selection">
        
   	  <form id="sorting-sel">
            	<label>Region</label>
            	<select id="location">
                	<option>Bangalore</option>
                </select>
        </form>
          <form id="sorting-sel">          
   
                 <label>Kitchen</label>
            	<select id="kitchen">
                	<option>Old Madras Road</option>
                    <option>RMZ Infinity</option>
                    <option>National Institue of Tech</option>

                 <option>M.G. Road</option>
                </select>
                
                  </form>
                
       <form id="sorting-sel">
       
                <label>School</label>
            	<select id="school">
                	<option>CUSAT</option>
                    <option>MIT</option>
                    <option>Indian Institue of Tech</option>
                    <option>National Institue of Tech</option>
                </select>
                        
 </form>
                
          
            
    </div>
	
</div>
</div>
</div>
<!--End Container-->


</body>
</html>
