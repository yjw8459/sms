package peg.sms.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import peg.sms.mapper.MessageMapper;
import peg.sms.service.MessageService;
import peg.sms.vo.DataVO;
import peg.sms.vo.ResponseVO;
import peg.sms.vo.ResultVO;
import peg.sms.vo.ResultsVO;
import peg.sms.vo.SendResultVO;

@Slf4j
@Service("messageService")
public class MessageServiceImpl implements MessageService{

	@Resource(name="messageMapper")
	MessageMapper messageMapper;
	
	
	//Token 발급
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

	//Mail Send
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
			param.put("smsno", smsno);
			param.put("now_date", dtm);

			//Schedule_type = 0 : 예약 아닐 경우
			String scheduleYn = (String)param.get("schedule_type");
			
			if ( "0".equals(scheduleYn) ) 	
				param.put("send_date", dtm);
			if ( param.get("dest_type") == null ) 	
				param.put("dest_type", "0");
			
			log.info(param.toString());
			
			//SMS or MMS Insert
			param.put("dest_info", insertSmsData(param));
			//"N" = SMS, else MMS
			if ( "N".equals(param.get("mms_yn")) ) 	messageMapper.insertSendSms(param);
			else 									messageMapper.insertSendMms(param);
			
		} catch (IllegalStateException e) {
			exceptionHandler(e, "-1", (String)param.get("as_dberrtext"), result, data, param);
		} catch (Exception e) {
			exceptionHandler(e, "-999", e.getMessage(), result, data, param);
		}
		
		if ( "yes".equals(result.getSuccess()) ) 
			data.setReturnSmsData((String)param.get("smsno"), (String)param.get("as_dberrtext"));
		
		result.setData(data);
		return result;
	}
	
	//SMS 관리 테이블 Insert Method
	private String insertSmsData(Map<String, Object> param) throws Exception{
		
		//Master Insert
		messageMapper.insertSmsMaster(param);
		
		//Detail Insert
		String dest_info = "";
		List<Map<String, String>> destList = (ArrayList<Map<String,String>>)param.get("dest_info");
		for (int i = 0; i < destList.size(); i++) {
			Map<String, String> map = destList.get(i);
			
			dest_info += map.get("dest_name") + "^" + map.get("tel_no");
			if ( destList.size()-1 != i ) dest_info += "|";
			
			param.put("sms_seq", String.valueOf(i+1));
			messageMapper.insertSmsDetail(param);
		}
		
		return dest_info;
		
	}
	
	/**
	 * 전송 결과 조회
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ResultsVO sendResult(Map<String, Object> params) {
		ResultsVO results = new ResultsVO();
		List<DataVO> sendResult = null;
		try {
			sendResult = messageMapper.selectSendResults(params);
			
			if ( sendResult == null ) 
				throw new IllegalStateException("조회 결과를 찾을 수 없습니다.");
			else {
				
				List<LinkedHashMap<String, String>> dataList = (List<LinkedHashMap<String, String>>)params.get("data");
				int paramCount = dataList.size();
				int resultCount = sendResult.size();
				
				if ( paramCount != resultCount ) {
					for (int i = 0; i < paramCount; i++) {
						boolean check = false;
						String smsno = dataList.get(i).get("smsno"); //파라미터 smsno
						for (int j = 0; j < sendResult.size(); j++) {
							DataVO item = sendResult.get(j);
							if ( smsno.equals(item.getSmsno()) ) {
								check = true;
								break;
							}
						}
						if ( ! check ) {
							resultCount++;
							sendResult.add(new DataVO("-1", "결과 조회 실패", smsno));
						}
						if ( resultCount == paramCount ) break;	//결과 조회 실패 건을 다 찾을 시 break;
					}
				}
				
			}
		} catch (Exception e) {
			exceptionHandler(e, "-1", e.getMessage(), results, new DataVO(), new HashMap<String, String>());
		}
		results.setReturnData(sendResult, sendResult.size());
		return results;
	}
	
	
	public <T> void exceptionHandler(Exception e, String errCode, String errMsg, ResponseVO resultVO, DataVO dataVO, Map<String, T> params) {
		System.out.println(params.toString());
		log.info(params.toString());
		e.printStackTrace();
		resultVO.setSuccess("no");
		dataVO.setErrno(errCode);
		dataVO.setErrtext(errMsg);
	}
	
}
