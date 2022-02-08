package peg.sms.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//단건 조회
@Getter
@Setter
@ToString
public class ResultVO extends ResponseVO{
	private DataVO data;
	public ResultVO() {
		super("yes");
	}
}
