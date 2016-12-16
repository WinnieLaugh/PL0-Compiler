package follow;

import getTable.Syntaxes;

public class Firsts {

	public First_Followers [] VN = new First_Followers[28];
	
	
	public void ini(){
		//这里初始化，是将所有firstfollwer集中首位都置为相应的VN,后面的才是他们真实的First
		
		for(int i=0; i<28; i++){
			VN[i] = new First_Followers();
		}
		
		for(int i=0; i<28; i++){
			VN[i].first_followers.add(200 + i);
		}

	}
	
	public void work(){
		//先求出NULL集合
		NULLS test = new NULLS();
		test.ini();
		test.work();
		
		//先把NULL集合里面的NULL元素加入，这样待会儿就不用再折腾NULL了
		for(int i=0; i<28; i++){
			if(test.VN[i][1] == 1)
				VN[i].first_followers.add(Syntaxes.NULL);
		}

		//先把所有产生式的第一位就是终结符的算好，接下来就不用纠结了,删除之
		for(int i=0; i<52; i++){
			if(Syntaxes.syntaxes[i][1]<200 && Syntaxes.syntaxes[i][1] != Syntaxes.NULL){
				int Vn = Syntaxes.syntaxes[i][0];
				int j = Vn - 200;
				VN[j].first_followers.add(Syntaxes.syntaxes[i][1]);
				Syntaxes.syntaxes[i][1] = 0;
			}
		}
		

		
		//接下来就是挨个产生式找了，但是似乎可以首位为终结符的产生式去除了,这里改为将第一位标记也好
		for(int i=0; i<52; i++)
			if(Syntaxes.syntaxes[i][1] < 200)
				Syntaxes.syntaxes[i][1] = 0;

		
		//接下来，就是真正的大戏了，非终结符之间的斗争
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
						int k = V -200;//当前要加入其他Vn的Vn
						int m = Vn - 200;//当前要被加入其他Vn的Vn
						int lengthk = VN[k].first_followers.size();
						int lengthm = VN[m].first_followers.size();
							
						//加入所有原先没有的first集内容，剩下的为空不为空的再说
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
						
						//接下来就是判断为空不为空的时候了，不为空即太棒了，为空再说
						if(test.VN[k][1] == 0){
							this_one_finished = true;
//							Syntaxes.syntaxes[i][1] = 0;
						}else{
							//为空的话，那就得继续看下一个了
							j++;
							this_one_finished = false;
						}
					}else if (V >= 100){
						Syntaxes.syntaxes[i][1] = 0;//断了这条语句，这条语句已经走到尽头了
						int m = Vn - 200;//当前要被加入其他Vn的Vn
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
		
		//用完以后再把随便改的别人的东西修回来
		Syntaxes.ini();
	}
	
}
