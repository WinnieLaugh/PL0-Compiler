package follow;

import getTable.Syntaxes;

public class NULLS {
	
	public int [][] VN = new int[28][2];
	
	public void ini(){
		int i;
		for(i=0; i<28; i++){
			VN[i][0] = 200 + i;
			VN[i][1] = 2;
		}
	}
	
	public void work(){
		Syntaxes.ini();
		
		boolean finished = false;
		while(!finished){
			finished = true;
			for(int i=0; i<=51; i++){	
				//如果这个推导式还存在
				if(Syntaxes.syntaxes[i][0] != 0){
					//如果这个推导式推出空，将其VN设为可推出空的，1，并将其所有推导式删除
					if(Syntaxes.syntaxes[i][1] == Syntaxes.NULL || Syntaxes.syntaxes[i][1] == 0){
						int Vn = Syntaxes.syntaxes[i][0];
						int j = Vn - 200;
						VN[j][1] = 1;
//						System.out.println(Vn);
						for(int k=0; k<=51; k++){
							if(Syntaxes.syntaxes[k][0] == Vn){
									Syntaxes.syntaxes[k][0] = 0;
								}
							}
						}else{
						//如果这个不是第一个就推出空的，判断一下它是该推不出空还是该删去一些为空的符号
							int j = 1;
							if(Syntaxes.syntaxes[i][j] < 200 ){
								int Vn = Syntaxes.syntaxes[i][0];
//								System.out.println(Vn);
								Syntaxes.syntaxes[i][0] = 0;
								//让它跳出去，这个语句无用了，不用往后看了
								Syntaxes.syntaxes[i][j+1] = 0;
								
								boolean testAll = true;
								for(int k=0; k<=51; k++){
									if(Syntaxes.syntaxes[k][0] == Vn){
										testAll = false;
										break;
									}
								}
								
								if(testAll){
									int n = Vn - 200;
									VN[n][1] = 0;									
								}
							}else{//如果这里面有之前已经判定好的VN，利用之
								int V = Syntaxes.syntaxes[i][j];
								int Vn = Syntaxes.syntaxes[i][0];
								
								int n = Vn -200;
								int m = V - 200;
								if(VN[m][1] == 0){
									VN[n][1] = 0;
									for(int k=0; k<=51; k++)
										if(Syntaxes.syntaxes[k][0] == Vn)
												Syntaxes.syntaxes[k][0] = 0;
									break;
								}else if(VN[m][1] == 1){
										for(int k =j; k <19; k++)
											Syntaxes.syntaxes[i][k] = Syntaxes.syntaxes[i][k+1];
										break;
										}
								}
							}
						}
					}
		
			for(int i=0; i<28; i++){
				if(VN[i][1] == 2){
					finished = false;
					break;
				}					
			}
		}
		
//		for(int i=0; i<28; i++){
//			if(VN[i][1] == 1)
//				System.out.print(VN[i][0] + "\t");
//		}
//		System.out.println();
		//用完以后再把随便改的别人的东西修回来
		Syntaxes.ini();
 }
}
