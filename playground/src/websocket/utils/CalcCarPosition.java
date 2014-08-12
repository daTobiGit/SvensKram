package websocket.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.websocket.Session;

import org.json.JSONObject;

import websocket.WebsocketServer;
import websocket.objects.Car;

public class CalcCarPosition {
	public static void updateAllCars(Map<String, Car> cars, String aktSessionId, List<Session> sessions){
		double dt = WebsocketServer.getTime();
		Collection<Car> values = cars.values();
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
}
