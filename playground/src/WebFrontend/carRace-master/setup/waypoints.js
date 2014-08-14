
function Waypoint( startX, startY, endX, endY, carHeading, initialActive )
{
	this.sX = startX;
	this.sY = startY;
	this.eX = endX;
	this.eY = endY;
	this.active = initialActive ? true : false;
	
	this.spawnX = ( startX + endX ) / 2 ;
	this.spawnY = ( startY + endY ) / 2;
	this.heading = carHeading;
}

var start = new Waypoint( 1, 1, 50, 50, 0.87, true );
var a = new Waypoint( 1060, 335, 1171, 433, -0.52 );
var b = new Waypoint( 3087, 137, 3181, 266, 0.25 );
var c = new Waypoint( 2577, 1880, 2673, 2058, -2.58 );
var d = new Waypoint( 422, 1658, 528, 1807, -2.8 );
var e = new Waypoint( 2367, 2680, 2467, 2802, 0 );
var f = new Waypoint( 979, 2917, 1102, 3068, 3.1 );

waypointList = [ start, a, b, c, d, e, f ];