

function MapEntity( imagePath, pctx, initialTranslation, initX, initY, considerImageSize )
{
	var _this = this;
	
	var ctx = pctx;
	var img = new Image();
	var width = 0;
	var height = 0;
	
	this.loaded = false;
	
	var imgX = 0;
	var imgY = 0;
	
	var init = function()
	{
		img.onload = calculateDrawPosition;
		img.src = imagePath;
	}
	
	var calculateDrawPosition = function()
	{
		if( initialTranslation )
		{
			width	= initX;
			height	= initY;
		}
		
		if( considerImageSize )
		{
			imgX = img.width / 2;
			imgY = img.height/ 2;
		}
		
		this.loaded = true;
	}

	this.draw = function( posX, posY, rotation )
	{
		ctx.save();
		ctx.translate( -posX+width, -posY+height );
		ctx.rotate( rotation );
		ctx.drawImage( img, -imgX, -imgY );
		ctx.restore();
	}
	
	this.update = function( dt )
	{
		
		
	}
	
	init();
}
