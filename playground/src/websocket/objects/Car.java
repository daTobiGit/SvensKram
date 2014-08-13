package websocket.objects;

import org.json.JSONObject;

public class Car {
	double x;
	double y;
	double angle;
	double heading;

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getAngle() {
		return angle;
	}

	public double getHeading() {
		return heading;
	}
	
	public void parseJson(JSONObject jsonObject){
		this.x = jsonObject.getDouble("X");
		this.y = jsonObject.getDouble("Y");
		this.angle = jsonObject.getDouble("Angle");
		this.heading = jsonObject.getDouble("Heading");
	}
	
	public String getJsonString(){
		return new JSONObject(this).toString();
	}

	@Override
	public String toString() {
		return "Car [x=" + x + ", y=" + y + ", angle=" + angle + ", heading="
				+ heading + "]";
	}
}
