package net.ucopy.drugcheck;

import java.util.ArrayList;

import net.ucopy.drugcheck.tools.KeyValueEntry;

public class TestData {

	public ArrayList<KeyValueEntry<String, String>> tipsList = new ArrayList<KeyValueEntry<String, String>>();

	public ArrayList<KeyValueEntry<String, String>> infoList = new ArrayList<KeyValueEntry<String, String>>();

	public TestData() {
		tipsList.add(new KeyValueEntry<String, String>("如何辨别假药", "我们经常会遇到假药，看小编教你火眼金睛..."));
		tipsList.add(new KeyValueEntry<String, String>("什么时候吃药做好", "究竟什么时候吃药最合适呢！..."));
		
		
		infoList.add(new KeyValueEntry<String, String>("阿里医药再发力", "马云一直说买买买，他自己也一直在买买买..."));
		infoList.add(new KeyValueEntry<String, String>("药监局发布新一批假药名单", "究药监局发布新一批假药名单..."));
	}

}
