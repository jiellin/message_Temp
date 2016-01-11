

public class Vote {
	private int[] type;
	private int id;
	private int temp;

	public Vote(int[] type, int id, int temp) {
		this.type = type;
	}

	public int finalType() {

		int sum = 0;
		for (int i = 0; i < type.length; i++) {
			sum += type[i];
		}
		
		sum += temp;
//		if (sum % 2 == 0){
//			if (sum == (type.length / 2)) {
//				System.out.println(id + " is uncertain.");
//			}
//		}
		
		if (sum > ((type.length+1) / 2))
			return 1;
		else
			return 0;
	}
}
