package Word;

public class test {

	public static void main(String[] args) throws Exception {
		
//		public int kind;
//		public int value;
//		public int level;
//		public int add;
//		public String name;
		
		
			GEN g = new GEN();
			TableElement[] table = g.getTable();
			
			for(int i=0; i<10; i++){
				System.out.println(table[i].kind + "\t"
					             + table[i].value + "\t"
					             + table[i].level + "\t"
					             + table[i].add + "\t"
					             + table[i].name);
			}
	}

}
