package outer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class Calculation {
	
	private ConvertFactory convertFactory;
	
	public Calculation(ConvertFactory convertFactory) {
		this.convertFactory=convertFactory;
		Main();
	}
	
	public void Main() {
		ArrayList<ArrayList<String>>ord_date=new ArrayList<ArrayList<String>>(convertFactory.getOrdDate());
		ArrayList<String>resultList=new ArrayList<String>(convertFactory.getResultsSet());
		HashMap<Integer, String>fielfMap=new HashMap<Integer, String>(convertFactory.getFieldMap());
		TreeNode root=new TreeNode(new HashMap<String, String>(), ord_date);      //新建根
		fielfMap.remove(fielfMap.size()-1);      //去掉结果字段
		Spilt_tree(root,resultList,fielfMap);
		System.out.println();
		show(root);
//		do {			
//			length=ord_date.get(0).size();
//		    height=ord_date.size();
//			averageSize=calculate_averageSize(length,height,resultList,ord_date);
//			elementsHashMap=calculate_elements(length,height,fielfMap,ord_date,resultList);
//			String max_quality=null;
//			double temp=Double.MIN_VALUE;
//			Iterator iterator=elementsHashMap.entrySet().iterator();
//			while (iterator.hasNext()) {
//				Map.Entry<String, Double>mapEntry=(Map.Entry<String, Double>)iterator.next();
////				System.out.printf("E(%s)=%.3f\n",mapEntry.getKey(),mapEntry.getValue());
//				System.out.printf("Gain(%s)=%.3f\n",mapEntry.getKey(),averageSize-mapEntry.getValue());
//				if((averageSize-mapEntry.getValue())>temp) {
//					temp=mapEntry.getValue();
//					max_quality=mapEntry.getKey();
//				}
//			}
//			System.out.printf("经比较，%s 属性信息增益最大\n",max_quality);
//		} while (flag);
	}
	
	public void show(TreeNode treeNode) {
		if(treeNode.nodes.size()==0) {
			System.out.println(treeNode.classifyMap);
			System.out.println(treeNode.elementsList);
			return;
		}
		for(TreeNode node:treeNode.nodes) {
			show(node);
		}
	}
	
	public void Spilt_tree(TreeNode node,ArrayList<String>resultList,HashMap<Integer, String>fielfMap) {
		Double averageSize;
		boolean flag=judge_moreSpilt(node);
		HashMap<String,Double>elementsHashMap=new HashMap<String, Double>();
		if(!flag) {
			return;
		}
	    averageSize=calculate_averageSize(resultList,node.elementsList);
	    HashMap<Integer, String>tempFielfMap=new HashMap<Integer, String>(fielfMap);
	    Iterator iterator=node.classifyMap.entrySet().iterator();
	    while (iterator.hasNext()) {                                                 //去掉已经使用的字段
			Map.Entry<String, String>entry=(Map.Entry<String, String>)iterator.next();
			String key=entry.getKey();
			int order=0;
			for(Map.Entry<Integer, String> mapEntry:fielfMap.entrySet()) {
				if(mapEntry.getValue().equals(key))
					order=mapEntry.getKey();
			}
			tempFielfMap.remove(order);
		}
	    elementsHashMap=calculate_elements(tempFielfMap,node.elementsList,resultList);
		String max_quality=null;
		double temp=Double.MIN_VALUE;
		iterator=elementsHashMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Double>mapEntry=(Map.Entry<String, Double>)iterator.next();
//			System.out.printf("E(%s)=%.3f\n",mapEntry.getKey(),mapEntry.getValue());
			System.out.printf("Gain(%s)=%.3f\n",mapEntry.getKey(),averageSize-mapEntry.getValue());
			if((averageSize-mapEntry.getValue())>temp) {
				temp=averageSize-mapEntry.getValue();
				max_quality=mapEntry.getKey();
			}
		}
		System.out.printf("经比较，%s 属性信息增益最大\n",max_quality);
		ArrayList<ArrayList<String>>tempList;   //新建节点的数据集
		HashMap<String, String> classifyMap;    //新建节点的分割集
		TreeNode tempNode;
		ArrayList<HashSet<String>>elementsSets=new ArrayList<HashSet<String>>(convertFactory.getElementsSets());
		HashSet<String>itemSet;
		iterator=fielfMap.entrySet().iterator();
		int order=0;
		while (iterator.hasNext()) {
			Map.Entry<Integer, String>entry=(Map.Entry<Integer,String>)iterator.next();
			if(entry.getValue().equals(max_quality))
				order=entry.getKey();           //获取最大增益属性所对应的次序
		}
		itemSet=elementsSets.get(order);           //elementsSets存储了各个属性的所有性质构成的集合表
		for(String string:itemSet) {
			tempList=new ArrayList<ArrayList<String>>();
			classifyMap=new HashMap<String, String>(node.classifyMap);  //把当前节点的分割集加入子节点
			for(ArrayList<String>arrayList:node.elementsList) {         
				if(arrayList.get(order).equals(string)) {
					tempList.add(arrayList);
				}
			}
			classifyMap.put(max_quality, string);
			tempNode=new TreeNode(classifyMap, tempList);
			System.out.println(classifyMap);
			System.out.println(tempList);
			node.nodes.add(tempNode);
		}
		for(TreeNode treeNode:node.nodes) {
			Spilt_tree(treeNode, resultList, fielfMap);
		}
		return;
	}
	
	public boolean judge_moreSpilt(TreeNode node) {
		boolean flag;
		if(node.elementsList.size()==0) {
			return false;
		}
		int temp=node.elementsList.get(0).size();
		HashSet<String>tempSet=new HashSet<String>();
		for(ArrayList<String>arrayList:node.elementsList) {
			tempSet.add(arrayList.get(temp-1));
		}
		if(node.classifyMap.size()>=(temp-1)||tempSet.size()==1) {
			flag=false;
		}
		else {
			flag=true;
		}
		return flag;
	}
	
	public HashMap<String, Double> calculate_elements(HashMap<Integer, String>tempFielfMap,ArrayList<ArrayList<String>>ord_date,ArrayList<String>resultList) {
		String key;
		double value;
		int order,length=ord_date.get(0).size(),height=ord_date.size();
		HashMap<String,Double>elementsHashMap=new HashMap<String, Double>();
		ArrayList<HashSet<String>>elementsSets=new ArrayList<HashSet<String>>(convertFactory.getElementsSets());
		HashSet<String>itemSet;
		Iterator iterator=tempFielfMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<Integer, String>entry=(Map.Entry<Integer,String>)iterator.next();
			key=entry.getValue();
			order=entry.getKey();
			itemSet=elementsSets.get(order);
			value=calculate_item(order,itemSet, resultList, ord_date);
			elementsHashMap.put(key, value);
		}
		return elementsHashMap;
	}
	
	public double calculate_item(int order,HashSet<String>itemSet,ArrayList<String>resultList,ArrayList<ArrayList<String>>ord_date) {
		double value=0.0;
		ArrayList<String>itemList=new ArrayList<String>(itemSet);    //序列化
		int length=itemSet.size(),height=resultList.size(),temp=ord_date.get(0).size()-1,cnt;;
		int[][] item_n=new int[height][length];
		String quality,result;
		for(int i=0;i<length;i++) {
			quality=itemList.get(i);             //获取当前性质
			for(int j=0;j<height;j++) {
				result=resultList.get(j);        //获取当前结果
				cnt=0;
				for(ArrayList<String>arrayList:ord_date) {
					if((quality.equals(arrayList.get(order)))&&(result.equals(arrayList.get(temp)))) {
						cnt++;
					}
				}
				item_n[j][i]=cnt;
			}
		}
//		for(int i=0;i<length;i++) {
//			for(int j=0;j<height;j++) {
//				System.out.print(item_n[j][i]+" ");
//			}
//			System.out.println();
//		}
		int sum_temp,sum=0;
		int []item_sum_n=new int[length];
		double[][]item_p=new double[height][length];
		for(int i=0;i<length;i++) {
			sum_temp=0;
			for(int j=0;j<height;j++) {        
				sum_temp=sum_temp+item_n[j][i];
			}
			item_sum_n[i]=sum_temp;
			sum=sum+sum_temp;
			for(int j=0;j<height;j++) {
				if(sum_temp==0) {
					item_p[j][i]=0;
					continue;
				}
				item_p[j][i]=(double)item_n[j][i]/sum_temp;
			}
		}
//		for(int i=0;i<length;i++) {
//			for(int j=0;j<height;j++) {
//				System.out.print(item_p[j][i]+" ");
//			}
//			System.out.println();
//		}
		double e=0.0;
		for(int i=0;i<length;i++) {
			value=0.0;
			for(int j=0;j<height;j++) {
				if(item_p[j][i]==0.0) {
					continue;
				}
				value=value+item_p[j][i]*Math.log(item_p[j][i])/Math.log(2);
			}
			value=-value;
			value=item_sum_n[i]*value;
			e=e+value;
		}
		e=e/sum;
		return e;
	}
	
	public Double calculate_averageSize(ArrayList<String>resultList,ArrayList<ArrayList<String>>ord_date) {
		int denominator,length=ord_date.get(0).size(),height=ord_date.size();
	    Double molecule,averageSize=0.0,temp;
	    HashMap<String, Double> average_size=new HashMap<String, Double>();
		for(String string:resultList) {               //计算平均信息量
			molecule=temp=0.0;
			denominator=height;
			for(ArrayList<String> arrayList:ord_date) {
				if(arrayList.get(length-1).equals(string)) {
					molecule++;
				}
			}
			temp=molecule/denominator;
			average_size.put(string, temp);      
		}
		for(Double value:average_size.values()) {
			averageSize=averageSize+(value)*(Math.log(value)/Math.log(2));
		}
		averageSize=-averageSize;
		System.out.printf("训练集平均信息量为:%.3f\n",averageSize);
		return averageSize;
	}
	
}
