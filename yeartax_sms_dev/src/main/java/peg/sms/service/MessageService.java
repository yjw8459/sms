package peg.sms.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import peg.sms.vo.DataVO;
import peg.sms.vo.ResultVO;
import peg.sms.vo.ResultsVO;

public interface MessageService {

	public ResultVO token(Map<String, String> param);
	public ResultVO sendMessage(Map<String, Object> param);
	public ResultsVO sendResult(Map<String, Object> params);
}
