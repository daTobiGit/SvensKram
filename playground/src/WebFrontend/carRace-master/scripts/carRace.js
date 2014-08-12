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

function updateCars(obj)
{
	if(obj.update === 0)
	{
		updateOwn(obj);
	}
	
	if(obj.update === 1)
	{
		updateEnemy(obj);
	}
}

ownData = {};
function updateOwn(obj){
	ownData = obj;
}

enemyData = {};
enemyCar = {};
function updateEnemy(enemyObject)
{
	var id = enemyObject.id;
	enemyData[id] = enemyObject;
	
	if(!enemyCar[id])
	{
		enemyCar[id] = new MapEntity( "images/top.png", ctx, true, canvas.width/2, canvas.height/2, true);
	}
}

function game( canvasID )
{
	var map;
	var playerCar; 
	
	var iH;
	
	var lastTime;
	var thisTime;
	var dt;
	
	var init = function()
	{
		canvas = $( canvasID )[0];
		ctx = canvas.getContext("2d");
		
		iH = new InputHandler();
		
		webSocketHandler.connect();
		
		map			= new MapEntity( "images/map.jpg", ctx, true, canvas.width/2, canvas.height/2, false );
		playerCar	= new MapEntity( "images/top.png", ctx, true, canvas.width/2, canvas.height/2, true );
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
	
//		iH.update( dt );
	
		ctx.clear();
//		map.draw( iH.posX, iH.posY, 0 );
		map.draw( ownData.X, ownData.Y, 0 );
//		playerCar.draw( 0, 0, iH.carHeading );
		playerCar.draw( 0, 0, ownData.Heading );
		
		$.each(enemyData, function(key, value)
				{
					enemyCar[key].draw(-(value.X) + ownData.X, -(value.Y) + ownData.Y, value.Heading);	
				}
		);		
		requestAnimationFrame( gameLoop );
	}

	init();
}



// SETUP HTML PAGE
$( function()
{
	myGame = new game( "#view" );
	myGame.start();
});