package follow;

import getTable.Syntaxes;

public class Firsts {

	public First_Followers [] VN = new First_Followers[28];
	
	
	public void ini(){
		//�����ʼ�����ǽ�����firstfollwer������λ����Ϊ��Ӧ��VN,����Ĳ���������ʵ��First
		
		for(int i=0; i<28; i++){
			VN[i] = new First_Followers();
		}
		
		for(int i=0; i<28; i++){
			VN[i].first_followers.add(200 + i);
		}

	}
	
	public void work(){
		//�����NULL����
		NULLS test = new NULLS();
		test.ini();
		test.work();
		
		//�Ȱ�NULL���������NULLԪ�ؼ��룬����������Ͳ���������NULL��
		for(int i=0; i<28; i++){
			if(test.VN[i][1] == 1)
				VN[i].first_followers.add(Syntaxes.NULL);
		}

		//�Ȱ����в���ʽ�ĵ�һλ�����ս������ã��������Ͳ��þ�����,ɾ��֮
		for(int i=0; i<52; i++){
			if(Syntaxes.syntaxes[i][1]<200 && Syntaxes.syntaxes[i][1] != Syntaxes.NULL){
				int Vn = Syntaxes.syntaxes[i][0];
				int j = Vn - 200;
				VN[j].first_followers.add(Syntaxes.syntaxes[i][1]);
				Syntaxes.syntaxes[i][1] = 0;
			}
		}
		

		
		//���������ǰ�������ʽ���ˣ������ƺ�������λΪ�ս���Ĳ���ʽȥ����,�����Ϊ����һλ���Ҳ��
		for(int i=0; i<52; i++)
			if(Syntaxes.syntaxes[i][1] < 200)
				Syntaxes.syntaxes[i][1] = 0;

		
		//�����������������Ĵ�Ϸ�ˣ����ս��֮��Ķ���
		boolean finished = false;
		while(!finished){
//			System.out.println("OK Here");
			finished = true;
			for(int i=0; i<52; i++){			 
				int j = 1;
				
				boolean this_one_finished = Syntaxes.syntaxes[i][j] == 0;
				while(!this_one_finished){
					this_one_finished = true;
					int V = Syntaxes.syntaxes[i][j];
					int Vn = Syntaxes.syntaxes[i][0];
					
					if(V >= 200){
//						finished = false;
						int k = V -200;//��ǰҪ��������Vn��Vn
						int m = Vn - 200;//��ǰҪ����������Vn��Vn
						int lengthk = VN[k].first_followers.size();
						int lengthm = VN[m].first_followers.size();
							
						//��������ԭ��û�е�first�����ݣ�ʣ�µ�Ϊ�ղ�Ϊ�յ���˵
						for(int n=1; n<lengthk; n++){
							int temp = VN[k].first_followers.get(n);
							boolean alreadyin = false;
							for(int index=0; index<lengthm; index++)
								if(VN[m].first_followers.get(index) == temp){
									alreadyin = true;
									break;
								}
											
							if(!alreadyin){
								VN[m].first_followers.add(temp);
								finished = false;
							}
						}
						
						//�����������ж�Ϊ�ղ�Ϊ�յ�ʱ���ˣ���Ϊ�ռ�̫���ˣ�Ϊ����˵
						if(test.VN[k][1] == 0){
							this_one_finished = true;
//							Syntaxes.syntaxes[i][1] = 0;
						}else{
							//Ϊ�յĻ����Ǿ͵ü�������һ����
							j++;
							this_one_finished = false;
						}
					}else if (V >= 100){
						Syntaxes.syntaxes[i][1] = 0;//����������䣬��������Ѿ��ߵ���ͷ��
						int m = Vn - 200;//��ǰҪ����������Vn��Vn
						int lengthm = VN[m].first_followers.size();
						boolean alreadyin = false;
						for(int index=1; index<lengthm; index++){
							if(VN[m].first_followers.get(index) == V){
								alreadyin = true;
								break;
							}
										
							if(!alreadyin){
								VN[m].first_followers.add(V);
								finished = false;
							}
						}
					}
				}
			}
		}
		
		//�����Ժ��ٰ����ĵı��˵Ķ����޻���
		Syntaxes.ini();
	}
	
}
