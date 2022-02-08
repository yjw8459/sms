package peg.sms.vo;


import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//다건 조회 
@Getter
@Setter
@ToString
public class ResultsVO extends ResponseVO{
	private int count;
	private List<DataVO> data;
	public ResultsVO(){
		super("yes");
	}
	
	public void setReturnData(List<DataVO> data, int count) {
		this.data = data;
		this.count = count;
	}
}
