package net.ucopy.drugcheck.communicate;

import java.util.ArrayList;
import java.util.Map.Entry;

import net.ucopy.drugcheck.entity.DrugInfo;
import net.ucopy.drugcheck.tools.KeyValueEntry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseHtml {

	public static DrugInfo parseHtml(String src) {
		Document doc = Jsoup.parse(src);
		Elements es = doc.getElementsByAttributeValue("class", "infobox");
		DrugInfo drugInfo = null;

		if (es.size() > 1) {
			// 处理basrinfo
			Element baseInfoElement = es.get(0);
			String imgUrl = null;// 图片地址
			ArrayList<Entry<String, String>> baseInfoList = new ArrayList<Entry<String, String>>();
			String status = null;// 物流状态

			try {
				imgUrl = baseInfoElement.select("img[src$=.jpg]").attr("src");
				Elements baseInfos = baseInfoElement.select("li");

				for (Element element : baseInfos) {
					String[] sub = element.childNode(0).toString().split("】");
					if (sub.length > 1) {
						// map.put(mapToken2Info(sub[0]), sub[1]);

						Entry<String, String> entry = new KeyValueEntry<String, String>(sub[0], sub[1]);
						baseInfoList.add(entry);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 处理物流信息
			Element statusElement = es.get(1);

			Elements statusElements = statusElement.select("p");
			if (statusElements.size() > 0) {
				status = statusElements.get(0).childNode(0).toString();
			}

			drugInfo = new DrugInfo(imgUrl, status, baseInfoList);

		}

		return drugInfo;
	}

	public static String mapToken2Info(String src) {

		if (src.contains("药品通用名")) {
			return "commName";
		} else if (src.contains("剂型")) {
			return "dosageForm";
		} else if (src.contains("制剂规格")) {
			return "dosageSpecif";
		} else if (src.contains("包装规格")) {
			return "packingSpecif";
		} else if (src.contains("生产企业")) {
			return "prodEnter";
		} else if (src.contains("生产日期")) {
			return "prodDate";
		} else if (src.contains("产品批号")) {
			return "batchNumber";
		} else if (src.contains("有效期至")) {
			return "validDate";
		} else if (src.contains("批准文号")) {
			return "approvalNumber";
		} else {
			return null;
		}

	}

}
