package follow;

import getTable.Syntaxes;

public class Follows {

	public First_Followers [] followers = new First_Followers[28];
	
	public void ini(){
		//这里初始化，是将所有firstfollwer集中首位都置为相应的VN,后面的才是他们真实的Follower
		for(int i=0; i<28; i++){
			followers[i] = new First_Followers();
		}
		
		for(int i=0; i<28; i++){
			followers[i].first_followers.add(200 + i);
		}
		
		//把$加入开始符号的follow集
		followers[0].first_followers.add(0);
		followers[1].first_followers.add(0);
	}
	
	public void work(){
		//NUll集居然在这里还有用！
		NULLS thisnull = new NULLS();
		thisnull.ini();
		thisnull.work();
		
		//先把first的内容都求好了初始化好等着待会儿follow用
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
						
						//如果是个非终结符就麻烦了，得一点一点来看
						if(Syntaxes.syntaxes[i][j+1] >= 200){
							boolean thisVnfinished = false;
							int index = j+1;
							while(!thisVnfinished){
								thisVnfinished = true;
								int V = Syntaxes.syntaxes[i][index];
								
								if(V >= 200){
									int m = V - 200;
									//把这个产生式里面所有不为空的全部加入
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
									//判断后面这个非终结符为不为空，如果为空就没结束还得继续来
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
									//是0的话也有事，原始Vn的follow集得加到最后一个，也就是你自己身上
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
							//如果是个终结符还好
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
							//是0的话也有事，原始Vn的follow集得加到最后一个，也就是你自己身上
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
						
						//去整这条产生式的下一个VN，直到没有为止
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
