package websockts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import websockts.pojo.JsonResult;
import websockts.pojo.Message;
import websockts.service.NotificationService;

@Controller
@RequestMapping("msg")
public class ChatController {
	
	@Autowired
	@Qualifier("notificationService")
	private NotificationService notificationService;
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public JsonResult notification(@RequestBody Message message){
		return new JsonResult().setResult(notificationService.notificationAllUsers(message));
	}
	
	@RequestMapping(value="{id}/send",method=RequestMethod.PUT)
	@ResponseBody
	public JsonResult notificationSingle(@RequestBody Message message,@PathVariable("id") String id){
		return new JsonResult().setResult(notificationService.notificationSingle(id, message));
	}
}
