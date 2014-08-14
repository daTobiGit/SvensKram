

function InputHandler()
{
	var direction = 0;
	var steerAngle = 0;
	
	var acceleration = 0;
	var carSpeed = 0;
	
	var wheelBase = 60;
	var oneDegree = Math.PI / 180;
	
	this.posX = 1;
	this.posY = 1;
	this.carHeading = 0.87;
	
	var init = function()
	{
		$(document).keydown( function( event )
		{
			handleKeyPress( event.which );
		} );
		
		$(document).keyup( function( event )
		{
			handleKeyRelease( event.which );
		} );
	}
	
	this.setSpeed = function( s )
	{
		carSpeed = s;
	}
	
	var handleKeyPress = function( key )
	{
		switch( key )
		{
			case 37:
				direction = -1; // left
				break;
			case 39:
				direction = 1; // right
				break;
			case 40:
				acceleration = -1; // down
				break;
			case 38:
				acceleration = 1; // up
				break;
			default:
				break;
		}
	}
	
	var handleKeyRelease = function( key )
	{
		switch( key )
		{
			case 37:
			case 39:
				direction = 0;
				break;
			case 40:
			case 38:
				acceleration = 0;
				break;
			default:
				break;
		}
	}
	
	this.update = function( dt )
	{
		if( acceleration )
		{
			carSpeed += 5 * acceleration;
			if( carSpeed > 300 ){ carSpeed = 300; }
			if( carSpeed < -50 ){ carSpeed = -50; }			
		}
		
		if( direction )
		{
			steerAngle = 45 * direction * oneDegree;
		}
		else
		{
			steerAngle = 0;
		}
	
		var frontWheelX = this.posX + wheelBase/2 * Math.cos(this.carHeading);
		var frontWheelY = this.posY + wheelBase/2 * Math.sin(this.carHeading);
		
		var backWheelX = this.posX - wheelBase/2 * Math.cos(this.carHeading);
		var backWheelY = this.posY - wheelBase/2 * Math.sin(this.carHeading);
		
		backWheelX += carSpeed * dt * Math.cos(this.carHeading);
		backWheelY += carSpeed * dt * Math.sin(this.carHeading);
		
		frontWheelX += carSpeed * dt * Math.cos(this.carHeading+steerAngle);
		frontWheelY += carSpeed * dt * Math.sin(this.carHeading+steerAngle);
		
		this.posX = (frontWheelX + backWheelX) / 2;
		this.posY = (frontWheelY + backWheelY) / 2;
		
		this.carHeading = Math.atan2( frontWheelY - backWheelY , frontWheelX - backWheelX );
		
		var msg = "posX: " + this.posX + "<br>";
		msg += "posY: " + this.posY + "<br>";
		msg += "angl: " + steerAngle + "<br>";
		msg += "heading:" + this.carHeading + "<br>";
		
		$( "#log" ).html( msg );
		 
		var websocketMsg = {"type":"position", "X":this.posX, "Y":this.posY, "Angle":steerAngle, "Heading":this.carHeading};
		var msg = JSON.stringify(websocketMsg);
		webSocketHandler.sendMessage(msg);
	}
	
	init();
}