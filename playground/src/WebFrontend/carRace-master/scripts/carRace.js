/* IMPORTANT  IMPORTANT  IMPORTANT  IMPORTANT  IMPORTANT  IMPORTANT  IMPORTANT  IMPORTANT */
/* http://stackoverflow.com/questions/2142535/how-to-clear-the-canvas-for-redrawing       */
/* Add clear method with saving transformation state                                      */
CanvasRenderingContext2D.prototype.clear =
  CanvasRenderingContext2D.prototype.clear || function (preserveTransform) {
    if (preserveTransform) {
      this.save();
      this.setTransform(1, 0, 0, 1, 0, 0);
    }

    this.clearRect(0, 0, this.canvas.width, this.canvas.height);

    if (preserveTransform) {
      this.restore();
    }           
};

// Array Remove - By John Resig (MIT Licensed)
Array.prototype.remove = function(from, to) {
	var rest = this.slice((to || from) + 1 || this.length);
	this.length = from < 0 ? this.length + from : from;
	return this.push.apply(this, rest);
};

window.requestAnimationFrame = window.requestAnimationFrame || window.mozRequestAnimationFrame || window.webkitRequestAnimationFrame || window.msRequestAnimationFrame;
/* IMPORTANT  IMPORTANT  IMPORTANT  IMPORTANT  IMPORTANT  IMPORTANT  IMPORTANT  IMPORTANT */

enemyData = [];

function updateEnemy(enemyObject)
{
	enemyData[0] = enemyObject;	
}

function game( canvasID )
{
	var _this = this;

	var map;
	var playerCar;
	var enemyCar;
	var img;
	var offscreenCanvas;
	var offscreenCtx;
	
	var iH;
	
	var lastTime;
	var thisTime;
	var dt;
	
	
	
	var init = function()
	{
		canvas = $( canvasID )[0];
		ctx = canvas.getContext("2d");
		
		iH = new InputHandler();
		
		map			= new MapEntity( "images/map.jpg", ctx, true, canvas.width/2, canvas.height/2, false );
		img = new Image();
		img.onload = setOffscreenCanvas;
		img.src = "images/map.jpg";
	
		playerCar	= new MapEntity( "images/top.png", ctx, true, canvas.width/2, canvas.height/2, true );
		enemyCar	= new MapEntity( "images/top.png", ctx, true, canvas.width/2, canvas.height/2, true);
	}
	
	var setOffscreenCanvas = function()
	{
		offscreenCanvas = $('<canvas/>')[0];
		offscreenCanvas.width = img.width;
		offscreenCanvas.height = img.height;
		offscreenCtx = offscreenCanvas.getContext('2d');
		offscreenCtx.drawImage(img, 0, 0, img.width, img.height);
		_this.start();
	}
	
	this.start = function()
	{
		lastTime = Date.now();
		requestAnimationFrame( gameLoop );
		
		//while( !map.loaded || !playerCar.loaded ){}; // wait till all is loaded
	}
	
	
	var gameLoop = function()
	{
		thisTime = Date.now();
		dt = ( thisTime - lastTime ) / 1000;
		lastTime = thisTime;
	
		/* update positions */
		iH.update( dt );
		if( offroad( iH.posX, iH.posY ) )
		{
			iH.posX = 1;
			iH.posY = 1;
			iH.setSpeed( 0 );
			iH.setHeading( 0.87 );
		}
		
		/* draw objects */
		ctx.clear();
		map.draw( iH.posX, iH.posY, 0 );
		playerCar.draw( 0, 0, iH.carHeading );
		
		if(enemyData[0]){
			enemyCar.draw(-(enemyData[0].X) + iH.posX, -(enemyData[0].Y) + iH.posY, enemyData[0].Heading);
		}
		
		requestAnimationFrame( gameLoop );
	}

	var offroad = function( x, y )
	{
		if( x < 0 || y < 0 || x > 4000 || y > 4000 ){ return true; }
		var data = offscreenCtx.getImageData( x, y, 1, 1 ).data;
		console.log( "0:" + data[0] + " 1:" + data[1] + " 2:" + data[2] );
		if( data[0] == 0 && data[1] == 0 && data[2] == 0 ){ return false; } // black is street
		return true;
	}
	
	init();
}



// SETUP HTML PAGE
$( function()
{
	myGame = new game( "#view" );
});