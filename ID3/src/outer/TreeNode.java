package outer;

import java.util.ArrayList;
import java.util.HashMap;

public class TreeNode {
	
	public HashMap<String, String> classifyMap=new HashMap<String, String>();
	public ArrayList<ArrayList<String>>elementsList=new ArrayList<ArrayList<String>>();
	public ArrayList<TreeNode>nodes=new ArrayList<TreeNode>();
	
	public TreeNode(HashMap<String, String> classifyMap,ArrayList<ArrayList<String>>elementsList) {
		this.classifyMap=classifyMap;
		this.elementsList=elementsList;
	}
	
}
