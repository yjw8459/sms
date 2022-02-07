package peg.sms.mapper;


import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Mapper("messageMapper")
public interface MessageMapper {

	public String selectTelNo(String cust_id);
	public String getToken();
	public String selectSmsNo();
	public Map<String, String> selectSendResult(Map<String, String> param);
	public int insertCustToken(Map<String, String> param);
	public int insertSmsMaster(Map<String, Object> param);
	public int insertSmsDetail(Map<String, Object> param);
	public int insertSendSms(Map<String, Object> param);
	public int insertSendMms(Map<String, Object> param);
	public void p2p_appr_no(Map<String, String> param);
	public void p2p_send_appr_no(Map<String, Object> param);
}
