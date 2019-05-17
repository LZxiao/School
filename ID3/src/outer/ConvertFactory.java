package outer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ConvertFactory {
	
	private int height,length;
	private String [][]date;
	private HashMap<Integer,String>fieldMap=new HashMap<Integer, String>();
	private ArrayList<ArrayList<String>>ordDate=new ArrayList<ArrayList<String>>();
	private HashSet<String>resultsSet=new HashSet<String>();
	private ArrayList<HashSet<String>>elementsSets=new ArrayList<HashSet<String>>();//Ë³ÐòºÍfieldMap¡¢ordDateÒ»ÖÂ
	
	public ConvertFactory(ArrayList<ArrayList<String>> ordDate,HashMap<Integer,String>fieldMap) {
		this.fieldMap=fieldMap;
		this.ordDate=ordDate;
		this.length=ordDate.get(0).size();
	    this.height=ordDate.size();
		convert();
		rusultSet();
		elementsSet();
	}
	
	public void convert() {
//		System.out.println(length+"  "+height);
		date=new String[height][length];
		for(int i=0;i<height;i++) {
			for(int j=0;j<length;j++) {
				date[i][j]=ordDate.get(i).get(j);
			}
		}
	}
	
	void rusultSet(){
		for(int i=0;i<height;i++) {
			resultsSet.add(date[i][length-1]);
		}
//		for(String string:resultsSet) {
//			System.out.println(string);
//		}
	}
	
	void elementsSet() {
		HashSet<String>temp;
		for(int i=0;i<length-1;i++) {
			temp=new HashSet<String>();
			for(int j=0;j<height;j++) {
				temp.add(date[j][i]);
			}
			elementsSets.add(temp);
//			for(String string:temp) {
//				System.out.print(string+" ");
//			}
//			System.out.println();
		}
	}

	public int getHeight() {
		return height;
	}

	public int getLength() {
		return length;
	}

	public HashMap<Integer, String> getFieldMap() {
		return fieldMap;
	}

	public String[][] getDate() {
		return date;
	}

	public ArrayList<ArrayList<String>> getOrdDate() {
		return ordDate;
	}

	public HashSet<String> getResultsSet() {
		return resultsSet;
	}

	public ArrayList<HashSet<String>> getElementsSets() {
		return elementsSets;
	}
	
}
