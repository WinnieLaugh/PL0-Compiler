package getTable;


public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		DFA dfaTable = new DFA();
		
		dfaTable.ini();
		dfaTable.GotoAction();
		
		//初始化第一行
		System.out.print("\t");
		for(int j=0; j<35; j++){
			System.out.print((100+j) + "\t");
		}
		System.out.print("\t\t");
		for(int j=0; j<28; j++){
			System.out.print((200+j) + "\t");
		}
		System.out.println();
		
		//写入其他的
		for(int i=0; i<100; i++){
			System.out.print(i + "\t");
			
			for(int j=0; j<35; j++){
				System.out.print(dfaTable.GOTO[i][j] + "\t");
			}
			System.out.print("\t\t");
			for(int j=0; j<28; j++){
				System.out.print(dfaTable.ACTION[i][j] + "\t");
			}
			System.out.println();
		}
	}

}

