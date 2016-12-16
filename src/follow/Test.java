package follow;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		Firsts first = new Firsts();
//		
//		first.ini();
//		first.work();
//		
//		for(int i=0; i<28; i++){
//			int length= first.VN[i].first_followers.size();
//			for(int j=0; j<length; j++){
//				System.out.print(first.VN[i].first_followers.get(j)+" \t");
//			}
//			System.out.println();
//		}
		
		Follows follow = new Follows();
		
		follow.ini();
		follow.work();
		
		for(int i=0; i<28; i++){
			int length= follow.followers[i].first_followers.size();
			for(int j=0; j<length; j++){
				System.out.print(follow.followers[i].first_followers.get(j)+" \t");
			}
			System.out.println();
		}
	}

}
