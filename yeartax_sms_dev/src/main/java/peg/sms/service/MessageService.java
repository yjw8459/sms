package peg.sms.service;

import java.util.Map;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import peg.sms.vo.ResultVO;

public interface MessageService {

	public ResultVO token(Map<String, String> param);
	public ResultVO sendMessage(Map<String, Object> param);
	public ResultVO sendResult(Map<String, String> param);
}
