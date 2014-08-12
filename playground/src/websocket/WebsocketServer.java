package websocket;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.glassfish.tyrus.server.Server;
import org.json.JSONObject;

import websocket.objects.Car;
import websocket.utils.CarUpdater;

@ServerEndpoint(value = "/game")
public class WebsocketServer {

	public static boolean closed = false;
	
	private static Date thisTime;
	private static Date lastTime = new Date();
	
	
	public WebsocketServer() {
		new CarUpdater.calcThread().start();
	}
	
	public static double getTime(){
		thisTime = new Date();
		double dt = ( thisTime.getTime() - lastTime.getTime() ) / 1000;
		lastTime = thisTime;
		return dt;
	}
	
	@OnOpen
	public void onOpen(Session session) {
		System.out.println("Connected ... " + session.getId());
		CarUpdater.sessions.add(session);
		CarUpdater.carPosition.put(session.getId(), new Car(session.getId()));
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		JSONObject jsonMessageObject = new JSONObject(message);
		String type = jsonMessageObject.getString("type");
		if("position".equals(type)){
			Car car = CarUpdater.carPosition.get(session.getId());
			car.parseJson(jsonMessageObject);
		}else{
			switch (message) {
				case "quit":
					try {
						session.close(new CloseReason(CloseCodes.NORMAL_CLOSURE,"Game ended"));
						closed = true;
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
					break;
			    default:
			    	System.out.println(message);
			}
		}
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		System.out.println(String.format("Session %s closed because of %s",
				session.getId(), closeReason));
	}
	
	public static void main(String[] args) throws InterruptedException {
		Map<String, Object> properties = new HashMap<>(); 
		Server server = new Server("localhost", 8025, "/websockets", properties ,WebsocketServer.class);
		
		try {
			server.start();
			while(!closed){
				Thread.sleep(500);
			}
			server.stop();
		} catch (DeploymentException e) {
			e.printStackTrace();
		}
		
	}
}
