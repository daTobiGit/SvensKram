var webSocketHandler = 
{
   ws: null,
   connected: false,
   
   connect: function()
   {
	   this.ws = new WebSocket("ws://localhost:8025/websockets/game")
	   this.ws.onopen = function()
	   {
		   console.log("WebSocket connected");
		   webSocketHandler.connected = true;
	   };
	   this.ws.onclose = function()
	   {
		   console.log("WebSocket disconnected");
		   webSocketHandler.connected = false;
	   };
	   this.ws.onmessage = function(msg)
	   {
		   console.log(msg.data);
		   var obj = eval("(" + msg.data + ")");
		   updateEnemy(obj);
	   };
	   this.ws.onerror = function(error)
	   {
		   console.log(error);
	   }
   },
		
   closeWebsocket: function()
   {
	   ws.close();
   },
		
   sendMessage: function(msg)
   {		
	   if(this.ws === null)
	   {
		   this.connect();
	   }
	   if(this.connected === true){
		   this.ws.send(msg);
	   }
   }
}