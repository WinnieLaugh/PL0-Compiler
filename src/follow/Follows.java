package follow;

import getTable.Syntaxes;

public class Follows {

	public First_Followers [] followers = new First_Followers[28];
	
	public void ini(){
		//�����ʼ�����ǽ�����firstfollwer������λ����Ϊ��Ӧ��VN,����Ĳ���������ʵ��Follower
		for(int i=0; i<28; i++){
			followers[i] = new First_Followers();
		}
		
		for(int i=0; i<28; i++){
			followers[i].first_followers.add(200 + i);
		}
		
		//��$���뿪ʼ���ŵ�follow��
		followers[0].first_followers.add(0);
		followers[1].first_followers.add(0);
	}
	
	public void work(){
		//NUll����Ȼ�����ﻹ���ã�
		NULLS thisnull = new NULLS();
		thisnull.ini();
		thisnull.work();
		
		//�Ȱ�first�����ݶ�����˳�ʼ���õ��Ŵ����follow��
		Firsts first = new Firsts();
		first.ini();
		first.work();
		
		boolean finished = false;
		while(!finished){
			finished = true;
			
			System.out.println();
			for(int i=0; i<52; i++){
				int j=1;
				boolean this_one_finished =( Syntaxes.syntaxes[i][j] == 0);
				while(!this_one_finished){
					if(Syntaxes.syntaxes[i][j] >= 200){
						int Vn = Syntaxes.syntaxes[i][j];
						int n = Vn - 200;
						
						//����Ǹ����ս�����鷳�ˣ���һ��һ������
						if(Syntaxes.syntaxes[i][j+1] >= 200){
							boolean thisVnfinished = false;
							int index = j+1;
							while(!thisVnfinished){
								thisVnfinished = true;
								int V = Syntaxes.syntaxes[i][index];
								
								if(V >= 200){
									int m = V - 200;
									//���������ʽ�������в�Ϊ�յ�ȫ������
									int lengthm = first.VN[m].first_followers.size();
									for(int k=1; k<lengthm; k++){
										int temp = first.VN[m].first_followers.get(k);
										int lengthn = followers[n].first_followers.size();
										boolean alreadyin = false;
										for(int l=1; l<lengthn; l++){
											if(temp == followers[n].first_followers.get(l)){
												alreadyin = true;
												break;
											}
										}
										
										if(!alreadyin && temp != Syntaxes.NULL){
											followers[n].first_followers.add(temp);
											finished = false;
										}
									}
									//�жϺ���������ս��Ϊ��Ϊ�գ����Ϊ�վ�û�������ü�����
									if(thisnull.VN[m][1] == 1){
										thisVnfinished = false;
										index++;
									}else{
										break;
									}
								}else if(V >= 100){
									int lengthn = followers[n].first_followers.size();
									boolean alreadyin = false;
									for(int k=1; k<lengthn; k++){
										if(followers[n].first_followers.get(k) == V){
											alreadyin = true;
										}
									}
									if(!alreadyin){
										followers[n].first_followers.add(V);
										finished = false;
									}
								}else if( V == 0){
									//��0�Ļ�Ҳ���£�ԭʼVn��follow���üӵ����һ����Ҳ�������Լ�����
									int Vn0 = Syntaxes.syntaxes[i][0];
									int m0 = Vn0 - 200;
									int lengthm0 = followers[m0].first_followers.size();
									for(int k=1; k<lengthm0; k++){
										int followVn0 = followers[m0].first_followers.get(k);
										int lengthn = followers[n].first_followers.size();
										boolean alreadyin = false;
										for(int l=1; l<lengthn; l++){
											if(followVn0 == followers[n].first_followers.get(l))
												alreadyin = true;
										}
										
										if(!alreadyin){
											followers[n].first_followers.add(followVn0);
											finished = false;
										}
									}
								}
							}
						}else if(Syntaxes.syntaxes[i][j+1] >= 100){
							//����Ǹ��ս������
							int V = Syntaxes.syntaxes[i][j+1];
							int lengthn = followers[n].first_followers.size();
							boolean alreadyin = false;
							for(int k=1; k<lengthn; k++){
								if(followers[n].first_followers.get(k) == V){
									alreadyin = true;
								}
							}
							if(!alreadyin){
								followers[n].first_followers.add(V);
								finished = false;
							}
						}else if(Syntaxes.syntaxes[i][j+1] == 0){
							//��0�Ļ�Ҳ���£�ԭʼVn��follow���üӵ����һ����Ҳ�������Լ�����
							int Vn0 = Syntaxes.syntaxes[i][0];
							int m0 = Vn0 - 200;
							int lengthm0 = followers[m0].first_followers.size();
							for(int k=1; k<lengthm0; k++){
								int followVn0 = followers[m0].first_followers.get(k);
								int lengthn = followers[n].first_followers.size();
								boolean alreadyin = false;
								for(int l=1; l<lengthn; l++){
									if(followVn0 == followers[n].first_followers.get(l))
										alreadyin = true;
								}
								
								if(!alreadyin){
									followers[n].first_followers.add(followVn0);
									finished = false;
								}
							}
						}
						
						//ȥ����������ʽ����һ��VN��ֱ��û��Ϊֹ
						j++;
						this_one_finished =( Syntaxes.syntaxes[i][j] == 0);
					}else if(Syntaxes.syntaxes[i][j] >= 100){
						j++;
						this_one_finished = (Syntaxes.syntaxes[i][j] == 0);
					}else{
						this_one_finished = (Syntaxes.syntaxes[i][j] == 0);
					}
				}
			}
		}
		
	}
}
