package peg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.mysql.fabric.xmlrpc.base.Array;


public class Test {

	public static void main(String[] args) {
		sendResultTest();	
	}
	
	
	public static void sendResultTest() {
		Map<String, String> param1 = new HashMap<>();
		param1.put("cust_cd", "CAPS");
		param1.put("smsno", "202202071150450650");
		Map<String, String> param2 = new HashMap<>();
		param2.put("cust_cd", "CAPS");
		param1.put("smsno", "202202071148251940");
		Map<String, String> param3 = new HashMap<>();
		param3.put("cust_cd", "CAPS");
		param1.put("smsno", "202202071149298340");
		Map<String, String> param4 = new HashMap<>();
		param4.put("cust_cd", "CAPS");
		param1.put("smsno", "202202041029447360");
		
		List<Map<String, String>> param = new ArrayList<>();
		param.add(param1);
		param.add(param2);
		param.add(param3);
		param.add(param4);
		
		Map<String, List<String>> sendParam = new HashMap<>();
		
		List<String> test = param.stream().map(map -> {
			return map.get("smsno");
		}).collect(Collectors.toList());
		
		System.out.println(test.toString());;
	}
	
	public static void joiningDestInfo() {
		Map<String, String> param1 = new HashMap<String, String>();
		param1.put("dest_name", "유종원");
		param1.put("tel_no", "01041028459");
		Map<String, String> param2 = new HashMap<String, String>();
		param2.put("dest_name", "피이지");
		param2.put("tel_no", "0267366057");
		Map<String, String> param3 = new HashMap<String, String>();
		param3.put("dest_name", "시스템");
		param3.put("tel_no", "01067366057");
		
		List<Map<String, String>> destList =  new ArrayList<>();
		destList.add(param1);
		destList.add(param2);
		destList.add(param3);
		String test = destList.stream().map( map -> {
			map.put("dest_info", map.get("dest_name") + "^" + map.get("tel_no"));
			return map;
		}).collect(Collectors.toList()).stream().map(map -> map.get("dest_info"))
		.collect(Collectors.joining("|"));
		System.out.println(destList.toString());
		System.out.println(test);
	}
}
