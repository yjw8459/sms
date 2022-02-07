package peg;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import pegsystem.util.XmlUtil;

public class XmlTest {

	
	public static void main(String[] args) {
		
		List<EgovMap> dataList = new ArrayList<EgovMap>();
		String rootElementName = "root";
		String topElementName = "top";
		String characterSet = "UTF-8";
		String downloadPath = "e://a.xml";
		
		EgovMap temp = new EgovMap();
		temp.put("AAAA", "가가가가");
		temp.put("BBBB", "나나나나");
		temp.put("CCCC", "다다다다");
		temp.put("DDDD", 1);
		dataList.add(temp);
		
		temp = new EgovMap();
		temp.put("AAAA", "라라라라");
		temp.put("BBBB", "");
		temp.put("CCCC", "마마마마");
		temp.put("DDDD", 2);
		dataList.add(temp);
		
		temp = new EgovMap();
		temp.put("AAAA", "바바바바");
		temp.put("BBBB", "사사사");
		temp.put("CCCC", "아앙아아아아아");
		temp.put("DDDD", 3);
		dataList.add(temp);
		
		temp = new EgovMap();
		temp.put("AAAA", "자자자자");
		temp.put("BBBB", "차차차");
		temp.put("CCCC", "카카카카카카카카");
		temp.put("DDDD", 4);
		dataList.add(temp);
		
		
		
		XmlUtil xmlMaker = new XmlUtil() {
			
			@Override
			public Document setXmlData(List<EgovMap> dataList) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		
		int result = xmlMaker.makeXml(dataList, characterSet, downloadPath);
		System.out.println(result);
	}
}
