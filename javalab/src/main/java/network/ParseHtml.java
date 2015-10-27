package network;


import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseHtml {

	public static JSONObject parseHtml(String src) {
		Document doc = Jsoup.parse(src);
		Elements es = doc.getElementsByAttributeValue("class", "infobox");

        JSONObject json = new JSONObject();

        if (es.size() > 1) {
			Element baseInfoElement = es.get(0);

			try {
				String imgUrl = baseInfoElement.select("img[src$=.jpg]").attr("src");
                json.put("picUrl",imgUrl);

				Elements baseInfos = baseInfoElement.select("li");

				for (Element element : baseInfos) {
					String[] sub = element.childNode(0).toString().split("】");
					if (sub.length > 1) {
                        json.put(mapToken2Info(sub[0]), sub[1]);

					}
				}

                // 处理物流信息
                Element statusElement = es.get(1);

                Elements statusElements = statusElement.select("p");
                if (statusElements.size() > 0) {
                    String status = statusElements.get(0).childNode(0).toString();
                    json.put("status",status);
                }

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        return json;
	}

    /**
     * 网站信息是汉字，根据汉字获取字段名称。
     * @param src
     * @return
     */
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
