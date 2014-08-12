 

function PlayerCar( pctx, canvasWidth, canvasHeight )
{
	var ctx;
	var img;
	
	var init = function()
	{
		ctx = pctx;
		img = new Image();
		img.src = ;
	}


	this.draw = function()
	{
		ctx.save();
		ctx.translate( 400, 400 );
		//ctx.rotate( carHeading );
		ctx.drawImage( img, -img.width/2, -img.height/2 );
		ctx.restore();
	}
	
	this.update = function( dt )
	{
	
	}
	
	init();
}