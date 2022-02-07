package peg.sms.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataVO {
	
	private String cust_cd;
	
	private String token;
	
	private String smsno;
	
	private String dbcode;
	
	private String dberrtext;
	
	private String callback;
	
	private String errno;
	
	private String errtext;
	
	private List<SendResultVO> results;
	
	public void setReturnSmsData(String smsno, String callback) {
		this.smsno = smsno;
		this.callback = callback;
	}
}
