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
				//�������Ƶ�ʽ������
				if(Syntaxes.syntaxes[i][0] != 0){
					//�������Ƶ�ʽ�Ƴ��գ�����VN��Ϊ���Ƴ��յģ�1�������������Ƶ�ʽɾ��
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
						//���������ǵ�һ�����Ƴ��յģ��ж�һ�����Ǹ��Ʋ����ջ��Ǹ�ɾȥһЩΪ�յķ���
							int j = 1;
							if(Syntaxes.syntaxes[i][j] < 200 ){
								int Vn = Syntaxes.syntaxes[i][0];
//								System.out.println(Vn);
								Syntaxes.syntaxes[i][0] = 0;
								//��������ȥ�������������ˣ�����������
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
							}else{//�����������֮ǰ�Ѿ��ж��õ�VN������֮
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
		//�����Ժ��ٰ����ĵı��˵Ķ����޻���
		Syntaxes.ini();
 }
}
