package outer;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AbstractGetOrdDate ordDate=new DB();
		ConvertFactory convertFactory=new ConvertFactory(ordDate.getOrdDate(),ordDate.getFieldMap());
		Calculation calculation=new Calculation(convertFactory);
//		for(int i=0;i<date.length;i++) {
//			for(int j=0;j<date[0].length;j++) {
//				System.out.print(date[i][j]+" ");
//			}
//			System.out.println();
//		}
	}
}
