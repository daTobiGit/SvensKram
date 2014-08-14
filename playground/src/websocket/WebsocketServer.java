package websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

@ServerEndpoint(value = "/game")
public class WebsocketServer {

	public static boolean closed = false;
	
	public static List<Session> sessions = new ArrayList<>();
	public static Map<String, Car> carPosition = new HashMap<>();
	
	@OnOpen
	public void onOpen(Session session) {
		System.out.println("Connected ... " + session.getId());
		sessions.add(session);
		carPosition.put(session.getId(), new Car(session.getId()));
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		JSONObject jsonMessageObject = new JSONObject(message);
		String type = jsonMessageObject.getString("type");
		if("position".equals(type)){
			Car car = carPosition.get(session.getId());
			car.parseJson(jsonMessageObject);
//			System.out.println(car);
			this.sendToAllOtherSession(session.getId(), jsonMessageObject);
		}else if("start".equals(type)){
			Car car = carPosition.get(session.getId());
			car.parseStartJson(jsonMessageObject);
			System.out.println("Start: " + car);
			this.sendToAllOtherSession(session.getId(), jsonMessageObject);
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

	private void sendToAllOtherSession(String id, JSONObject jsonObject) {
		try {
			for (Session session : sessions) {
				if(!session.getId().equals(id) && session.isOpen()){
					session.getBasicRemote().sendText(jsonObject.toString());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
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
