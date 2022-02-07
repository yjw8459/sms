package peg.sms.vo;


import lombok.Data;

@Data
public class ResultVO {
	private String success;
	private DataVO data;
	
	public ResultVO() {
		this.success = "yes";
	}
}
