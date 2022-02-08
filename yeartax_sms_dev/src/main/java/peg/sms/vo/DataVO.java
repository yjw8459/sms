package peg.sms.vo;


import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
	
	private String resDsc;
	
	private String result;	//전송 갯수
	
	public void setReturnSmsData(String smsno, String callback) {
		this.smsno = smsno;
		this.callback = callback;
	}
	
	
	public DataVO(String result, String res_dsc, String smsno ) {
		this.result = result;
		this.resDsc = res_dsc;
		this.smsno = smsno;
	}
}
