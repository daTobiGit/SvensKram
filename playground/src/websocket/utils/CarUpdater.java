package websocket.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.Session;

import org.json.JSONObject;

import websocket.WebsocketServer;
import websocket.objects.Car;

public class CarUpdater {

	public static List<Session> sessions = new ArrayList<>();
	public static Map<String, Car> carPosition = new HashMap<>();
	
	public static void updateAllCars(){
		double dt = WebsocketServer.getTime();
		Collection<Car> values = carPosition.values();
		for (Car car : values) {
			car.calcPosition(dt);
		}
		
		for (Car car : values) {
			for (Session session : sessions) {
				JSONObject jsonCar = car.getJsonString();
				jsonCar.put("own", car.getSessionId().equals(session.getId()));
			}
		}
	}
	
	public static class calcThread extends Thread {

		@Override
		public void run() {
			updateAllCars();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
