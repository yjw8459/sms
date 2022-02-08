package peg.sms.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseVO {

	private String success;
	
	public ResponseVO(String message) {
		success = message;
	}
	
}
