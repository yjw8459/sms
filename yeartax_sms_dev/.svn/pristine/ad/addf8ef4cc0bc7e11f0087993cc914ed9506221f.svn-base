package peg.sms.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peg.sms.mapper.MessageMapper;
import peg.sms.service.MessageService;
import peg.sms.vo.DataVO;
import peg.sms.vo.ResultVO;

@Service("messageService")
public class MessageServiceImpl implements MessageService{

	@Resource(name="messageMapper")
	MessageMapper messageMapper;
	
	private final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class.getName());
	
	@Override
	@Transactional
	public ResultVO token(Map<String, String> param) {
		ResultVO result = new ResultVO();
		DataVO data = new DataVO();
		try {
			messageMapper.p2p_appr_no(param);
			if ( ((String)param.get("an_ret")).length() < 20 ) {
				throw new IllegalStateException("유효값 검증 실패");
			}
			else	data.setToken((String)param.get("an_ret"));
		} catch (IllegalStateException e) {
			exceptionHandler(e, String.valueOf(param.get("an_dbcode")), param.get("as_dberrtext"), result, data, param);
		} catch (Exception e) {
			exceptionHandler(e, "-999", e.getMessage(), result, data, param);
		}
		result.setData(data);
		return result;
	}

	@Override
	@Transactional
	public ResultVO sendMessage(Map<String, Object> param) {
		ResultVO result = new ResultVO();
		DataVO data = new DataVO();
		try {
			//프로시저 유효성 검사 
			messageMapper.p2p_send_appr_no(param);
			if ( Integer.valueOf((String)param.get("an_ret")) < 0 ) {
				throw new IllegalStateException("유효값 검증 실패");
			}
			
			//master Insert
			String smsno = messageMapper.selectSmsNo();
			String dtm = smsno.substring(0, 14);
			String scheduleYn = (String)param.get("schedule_type");
			
			//Schedule_type = 0 : 예약 아닐 경우 
			if ( "0".equals(scheduleYn) ) 	param.put("send_date", dtm);
			if ( param.get("dest_type") == null ) 	param.put("dest_type", "0");
			param.put("now_date", dtm);
			param.put("smsno", smsno);
			System.out.println(param.get("dest_info").toString());
			List<Map<String, String>> destList = (ArrayList<Map<String,String>>)param.get("dest_info");
			String dest_info = "";
			messageMapper.insertSmsMaster(param);
			
			//Detail Insert
			for (int i = 0; i < destList.size(); i++) {
				Map<String, String> map = destList.get(i);
				String destName = map.get("dest_name");
				String telNo = map.get("tel_no");
				dest_info += destName + "^" + telNo;
				
				if ( destList.size()-1 != i ) dest_info += "|";
				
				param.put("sms_seq", String.valueOf(i+1));
				param.put("recv_telno", telNo);
				param.put("recv_nm", destName);
				messageMapper.insertSmsDetail(param);
			}
			
			//SMS or MMS Insert
			param.put("dest_info", dest_info);
			LOGGER.info(param.toString());
			//"N" = SMS, else MMS0
			if ( "N".equals(param.get("mms_yn")) ) 	messageMapper.insertSendSms(param);
			else 									messageMapper.insertSendMms(param);
			
		} catch (IllegalStateException e) {
			exceptionHandler(e, "-1", (String)param.get("as_dberrtext"), result, data, param);
		} catch (Exception e) {
			exceptionHandler(e, "-999", e.getMessage(), result, data, param);
		}
		
		
		if ( "yes".equals(result.getSuccess()) ) {
			data.setSmsno((String)param.get("smsno"));
			data.setCallback((String)param.get("as_dberrtext"));
		}
		result.setData(data);
		return result;
	}
	
	@Override
	public ResultVO sendResult(Map<String, String> param) {
		ResultVO result = new ResultVO();
		DataVO data = new DataVO();
		try {
			Map<String, String> sendResult = messageMapper.selectSendResult(param);
			
			if ( sendResult == null ) 
				throw new IllegalStateException("조회 결과를 찾을 수 없습니다.");
			
			data.setRes_dsc(sendResult.get("RES_DSC"));
			data.setResult(String.valueOf(sendResult.get("RESULT")));
		} catch (Exception e) {
			exceptionHandler(e, "-1", e.getMessage(), result, data, param);
		}
		result.setData(data);
		return result;
	}
	
	public <T> void exceptionHandler(Exception e, String errCode, String errMsg, ResultVO resultVO, DataVO dataVO, Map<String, T> params) {
		System.out.println(params.toString());
		LOGGER.info(params.toString());
		e.printStackTrace();
		resultVO.setSuccess("no");
		dataVO.setErrno(errCode);
		dataVO.setErrtext(errMsg);
	}
	
}
