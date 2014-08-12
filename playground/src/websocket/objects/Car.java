package websocket.objects;

import org.json.JSONObject;

public class Car {
	private static final double ONE_DEGREE = Math.PI / 180;
	private static final double WHEEL_BASE = 60;
	
	private double x;
	private double y;
	private double angle;
	private double heading;
	private String sessionId;

	// calc values
	private double acceleration;
	private double carSpeed;
	private double direction;
	
	public Car(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public String getSessionId() {
		return sessionId;
	}
	
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
		this.acceleration = jsonObject.getDouble("acc");
		this.direction = jsonObject.getDouble("dir");
	}
	
	public JSONObject getJsonString(){
		return new JSONObject(this);
	}

	@Override
	public String toString() {
		return "Car [x=" + x + ", y=" + y + ", angle=" + angle + ", heading=" + heading + "]";
	}
	
	public void calcPosition(double dt){
		if( acceleration != 0 )
		{
			carSpeed += 5 * acceleration;
			if( carSpeed > 300 ){ carSpeed = 300; }
			if( carSpeed < -50 ){ carSpeed = -50; }			
		}
		
		double steerAngle;
		if( direction != 0 )
		{
			steerAngle = 45 * direction * ONE_DEGREE;
		}
		else
		{
			steerAngle = 0;
		}
	
		double frontWheelX = this.x + WHEEL_BASE/2 * Math.cos(this.heading);
		double frontWheelY = this.y + WHEEL_BASE/2 * Math.sin(this.heading);
		
		double backWheelX = this.x - WHEEL_BASE/2 * Math.cos(this.heading);
		double backWheelY = this.y - WHEEL_BASE/2 * Math.sin(this.heading);
		
		backWheelX += carSpeed * dt * Math.cos(this.heading);
		backWheelY += carSpeed * dt * Math.sin(this.heading);
		
		frontWheelX += carSpeed * dt * Math.cos(this.heading + steerAngle);
		frontWheelY += carSpeed * dt * Math.sin(this.heading + steerAngle);
		
		this.x = (frontWheelX + backWheelX) / 2;
		this.y = (frontWheelY + backWheelY) / 2;
		
		this.heading = Math.atan2( frontWheelY - backWheelY , frontWheelX - backWheelX );
	}
}
