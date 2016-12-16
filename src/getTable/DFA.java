package getTable;

import follow.Follows;

public class DFA {
	
	int num_of_V = 100;
	int num_of_vt = 35;
	int num_of_vn = 28;
	
	public int[][] GOTO = new int[100][num_of_vt];
	public int[][] ACTION = new int [100][num_of_vn];
	public Closure[] closures = new Closure[100];
	public int[] Vts = new int[num_of_vt];
	public int[] Vns = new int[num_of_vn];
	public int nowClosure = 0;
	
	public void ini(){
		//初始化语句
		Syntaxes.ini();
		
		for(int i=0; i<100; i++){
			closures[i] = new Closure();
		}
		
		//初始化，把第一个closure的第一个扩展语句加入
		ClosureEle firstEle = new ClosureEle();
		firstEle.order = 0;
		firstEle.pointer = 1;
		closures[0].closure.add(firstEle);
		
		//初始化，将VNVT加入
		for(int i=0; i<num_of_vt; i++){
			Vts[i] = 100 + i; 
		}
		for(int i=0; i<num_of_vn; i++){
			Vns[i] = 200 + i;
		}
	}
	
	
	public Closure getClosure(int k0){
		Closure from = closures[k0];
		
		//初始化第一个Vn或者Vt，不对，这个初始化方式不对，应该是初始化现在的后面的VN才对
		//但是想法很好，这样就不用每次全员了，不，这种方法很对，很好
		int[] thisVnVt = new int [27];
		int thisindex = 0;
//		int syorder = from.closure.get(0).order;
//		thisVnVt[thisindex] = Syntaxes.syntaxes[syorder][0];
		
		boolean finished = false;
		while(!finished){
			finished = true;
			//每次都循环一次，把没有加进来的VN加进来，若没有这样的VN了就break
			int length = from.closure.size();

			//每次走一次就是往下查找一个，走一整个就是查找现有的一整套
			for(int i=0; i<length; i++){
				int syNowOrder = from.closure.get(i).order;
				int syNowPointer = from.closure.get(i).pointer;
				
				//如果找到了一个是VN的
				if(Syntaxes.syntaxes[syNowOrder][syNowPointer] >100){
					//查看是否已经在这个CLOSURE中了
					boolean alreadyin = false;
					for(int testi=0; testi<27; testi++){
						if(thisVnVt[testi] == Syntaxes.syntaxes[syNowOrder][syNowPointer]){
							alreadyin = true;
							break;
						}
					}
					
					//如果还没有加进来，就加进来,并将finished改为false以便继续
					if(!alreadyin){
						finished = false;
						thisVnVt[thisindex] = Syntaxes.syntaxes[syNowOrder][syNowPointer];
						thisindex++;
						for(int j=0; j<52; j++){
							if(Syntaxes.syntaxes[j][0] == 
									Syntaxes.syntaxes[syNowOrder][syNowPointer]){
								ClosureEle addClosure = new ClosureEle();
								addClosure.order = j;
								addClosure.pointer = 1;
								from.closure.add(addClosure);
							}
						}
					}				
				}
			}
		}
		
		return from;
	}

	
	
	public void GotoAction(){
		
		//先初始化好follow集备用
		Follows follow = new Follows();
		follow.ini();
		follow.work();
		
		//每次用到不动点算法就是用这个啊！虽然这样也不能完全说不动点短发了
		boolean finished = false;
		int index = 0;//这个index记录的是现在要产生第几条记录，每次产生一条记录之前都要++
		
		//先产生第一条记录，从第一条记录发散开来
		closures[0] = getClosure(0);
		
//		int testLength0 = closures[index].closure.size();
//		for(int m=0; m<testLength0; m++){
//			System.out.println(closures[index].closure.get(m).order+ "\t"
//								+ closures[index].closure.get(m).pointer);
//		}
//		System.out.println();
		
		int i = 0;//这个i记录的是现在走到第几条记录了，每次往下走一次这个i++
	
		while(!finished){
			
			if(closures[i].closure.size() == 0){
				finished = true;
				break;
			}				
			
			//下面开始对一个闭包的处理，即一个Closure的处理
			int length = closures[i].closure.size();
			
			for(int j=0; j<length; j++){
				//下面开始对每一个项目的处理，即对每一个ClosureEle的处理
				ClosureEle ceNow = closures[i].closure.get(j);
				int syNowOrder = ceNow.order;
				int syNowPointer = ceNow.pointer;
				
				//求出下一个要移进的内容，看看是什么
				int V = Syntaxes.syntaxes[syNowOrder][syNowPointer];
				if(V>=200){
					//如果下一个要移进的内容是个Vn，那么就在ACTION表里面加入这个
					//并且index++，说明会有新的状态的加入
					index++;
					int VnNum = V - 200;//ACTION表中位置
					ACTION[i][VnNum] = index;
					
					//下面即为生成这个新的闭包
					//先把第一个产生这个的加入，并把指针往后移一位
					Closure cl = new Closure();
					ClosureEle ceToAdd = new ClosureEle();
					
					ceToAdd.order = syNowOrder;
					ceToAdd.pointer = syNowPointer+1;
					cl.closure.add(ceToAdd);
					
					//判断一下是否还有其他将要和这个转一个状态的项目
					for(int k=j+1; k<length; k++){
						int syTempOrder = closures[i].closure.get(k).order;
						int syTempPointer = closures[i].closure.get(k).pointer;
						
						//如果这个下一个移进的和现在处理的是一个V，那就是了，把这个加入同样的状态
						if(Syntaxes.syntaxes[syTempOrder][syTempPointer] == V){
							ceToAdd.order = syTempOrder;
							ceToAdd.pointer = syTempPointer+1;
							cl.closure.add(ceToAdd);
							j++;
						}
					}
					
					//把老的传递给新的以后就可以让新的自力更生生成一个Closure了
					//先把这个初始值附上，再开始生成整体的Closure
					
					closures[index] = cl;
					closures[index] = getClosure(index);
					
					//测试一下这个内容是否已经在closure集中了，即分程序最多只能有一层
					boolean alreadyin = true;
					//对每个closure集测试
					for(int m=0; m<index; m++){
						alreadyin = true;
						int closurelength = closures[m].closure.size();
						int closureNowLength = closures[index].closure.size();
						//如果这个closure集的长度和测试的一样，再比较是否里面的内容也一模一样
						if(closurelength == closureNowLength){
							for(int n=0; n<closurelength; n++){
								//有一个不一样就不一样
								boolean test = (closures[m].closure.get(n).order != 
														closures[index].closure.get(n).order);
//								System.out.println(closures[m].closure.get(n).order + " \t" 
//										+ closures[index].closure.get(n).order + "OKOK");
								test = test || 
										(closures[m].closure.get(n).pointer != 
												closures[index].closure.get(n).pointer);
//								System.out.println(closures[m].closure.get(n).pointer
//										+ " \t" + closures[index].closure.get(n).pointer + "OKOK");
								if(test){
									alreadyin = false;
									break;
								}
							}
							if(alreadyin){
								closures[index] = new Closure();
								index--;
								ACTION[i][VnNum] = m;
								
								break;
							}
						}else{
							alreadyin = false;
						}
					}
					
//					int testLength = cl.closure.size();
//					System.out.println("this V input: " + V);
//					for(int k=0; k<testLength; k++){
//						System.out.println(cl.closure.get(k).order+ "\t"
//											+ cl.closure.get(k).pointer);
//					}
//					System.out.println();
					
				}else if((V >= 100) && (V != Syntaxes.NULL)){
					//如果下一个要移进的内容是个Vt，那么就在GOTO表里面加入这个
					//并且index++，说明会有新的状态的加入
					index++;
					int VtNum = V - 100;//GOTO表中位置
					GOTO[i][VtNum] = index;
					
					//下面即为生成这个新的闭包
					//先把第一个产生这个的加入，并把指针往后移一位
					Closure cl = new Closure();
					ClosureEle ceToAdd = new ClosureEle();
					
					ceToAdd.order = syNowOrder;
					ceToAdd.pointer = syNowPointer+1;
					cl.closure.add(ceToAdd);
					
					//判断一下是否还有其他将要和这个转一个状态的项目
					if(j+1 < length){
//						System.out.println((j+1) + "\t" + length);
						for(int k=j+1; k<length; k++){
//							System.out.println(length + "\t" + closures[i].closure.size());
//							System.out.println(i + "  OKOK");
							int syTempOrder = closures[i].closure.get(k).order;
							int syTempPointer = closures[i].closure.get(k).pointer;
							
							//如果这个下一个移进的和现在处理的是一个V，那就是了，把这个加入同样的状态
							if(Syntaxes.syntaxes[syTempOrder][syTempPointer] == V){
								ceToAdd.order = syTempOrder;
								ceToAdd.pointer = syTempPointer+1;
								cl.closure.add(ceToAdd);
								j++;
							}
						}
					}
					
					
					//把老的传递给新的以后就可以让新的自力更生生成一个Closure了
					//先把这个初始值附上，再开始生成整体的Closure
					closures[index] = cl;
					closures[index] = getClosure(index);
					
					boolean alreadyin = true;
					for(int m=0; m<index; m++){
						alreadyin = true;
						int closurelength = closures[m].closure.size();
						int closureNowLength = closures[index].closure.size();

						if(closurelength == closureNowLength){
							for(int n=0; n<closurelength; n++){
								boolean test = (closures[m].closure.get(n).order != 
											closures[index].closure.get(n).order);
								test = test || 
										(closures[m].closure.get(n).pointer != 
													closures[index].closure.get(n).pointer);
								if(test){
									alreadyin = false;
									break;
								}
							}
							if(alreadyin){
								closures[index] = new Closure();
								index--;
								GOTO[i][VtNum] = m;
//								int testLength = closures[index].closure.size();
//								System.out.println("this V input: " + V);
//								for(int k=0; k<testLength; k++){
//									System.out.println(closures[index].closure.get(k).order+ "\t"
//														+ closures[index].closure.get(k).pointer);
//								}
//								System.out.println();
								break;
							}
						}else{
							alreadyin = false;
						}
					}
//					int testLength = cl.closure.size();
//					System.out.println("this V input: " + V);
//					for(int k=0; k<testLength; k++){
//						System.out.println(cl.closure.get(k).order+ "\t"
//											+ cl.closure.get(k).pointer);
//					}
//					System.out.println();
					
				}else if(V ==0 || V == Syntaxes.NULL){
					//这里就明显感觉到了忽略了移进规约冲突，因为移进项将不再考虑生成新的项
					//如果这是一个规约符号
					//那么就把在这个VN的follow集中的Vt项在GOTO表对应这条closure的项置为规约项
					int VnToSum = Syntaxes.syntaxes[syNowOrder][0];
					int Vntemp = VnToSum - 200;
					
					int lengthfo = follow.followers[Vntemp].first_followers.size();
					for(int k=1; k<lengthfo; k++){
						//取出这个VN的所有follow，然后把他们对应的部分的项内容都更新为规约
						//follow集有可能带0为终止符！！再考虑一下！！
						int foTemp = follow.followers[Vntemp].first_followers.get(k);
						if(foTemp == 0){
							foTemp = Syntaxes.NULL - 100;
						}else{
							foTemp = foTemp - 100;
						}
						GOTO[i][foTemp] = syNowOrder + 1000;
					}
					
				}
			}
			//上面加来加去的任务都完成了，可以走下一个closure了，即下一个记录了
			i++;
		}
	}
	
	
}
