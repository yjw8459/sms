package peg.sms.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import peg.sms.service.MessageService;
import peg.sms.vo.DataVO;
import peg.sms.vo.ResultVO;

@Controller
public class MessageController {
	
	@Resource(name="messageService") 
	MessageService messageService;
	
	
	@RequestMapping(value="/token", method={RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public ResultVO getToken(@RequestBody Map<String, String> param) {
		return messageService.token(param);
	}
	
	@RequestMapping(value="/send", method= RequestMethod.POST)
	@ResponseBody
	public ResultVO sendMessage(@RequestBody Map<String, Object> param) {
		return messageService.sendMessage(param);
	}
	
	@RequestMapping(value="/result", method=RequestMethod.POST)
	@ResponseBody
	public ResultVO sendResult(@RequestBody List<DataVO> param) {
		return messageService.sendResult(param);
	}
}
