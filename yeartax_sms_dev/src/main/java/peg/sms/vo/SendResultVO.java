package peg.sms.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SendResultVO {

	private String smsno;
	
	private String res_dsc;
	
	private String result;	//전송 갯수
	
}
