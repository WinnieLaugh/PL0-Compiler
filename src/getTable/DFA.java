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
		//��ʼ�����
		Syntaxes.ini();
		
		for(int i=0; i<100; i++){
			closures[i] = new Closure();
		}
		
		//��ʼ�����ѵ�һ��closure�ĵ�һ����չ������
		ClosureEle firstEle = new ClosureEle();
		firstEle.order = 0;
		firstEle.pointer = 1;
		closures[0].closure.add(firstEle);
		
		//��ʼ������VNVT����
		for(int i=0; i<num_of_vt; i++){
			Vts[i] = 100 + i; 
		}
		for(int i=0; i<num_of_vn; i++){
			Vns[i] = 200 + i;
		}
	}
	
	
	public Closure getClosure(int k0){
		Closure from = closures[k0];
		
		//��ʼ����һ��Vn����Vt�����ԣ������ʼ����ʽ���ԣ�Ӧ���ǳ�ʼ�����ڵĺ����VN�Ŷ�
		//�����뷨�ܺã������Ͳ���ÿ��ȫԱ�ˣ��������ַ����ܶԣ��ܺ�
		int[] thisVnVt = new int [27];
		int thisindex = 0;
//		int syorder = from.closure.get(0).order;
//		thisVnVt[thisindex] = Syntaxes.syntaxes[syorder][0];
		
		boolean finished = false;
		while(!finished){
			finished = true;
			//ÿ�ζ�ѭ��һ�Σ���û�мӽ�����VN�ӽ�������û��������VN�˾�break
			int length = from.closure.size();

			//ÿ����һ�ξ������²���һ������һ�������ǲ������е�һ����
			for(int i=0; i<length; i++){
				int syNowOrder = from.closure.get(i).order;
				int syNowPointer = from.closure.get(i).pointer;
				
				//����ҵ���һ����VN��
				if(Syntaxes.syntaxes[syNowOrder][syNowPointer] >100){
					//�鿴�Ƿ��Ѿ������CLOSURE����
					boolean alreadyin = false;
					for(int testi=0; testi<27; testi++){
						if(thisVnVt[testi] == Syntaxes.syntaxes[syNowOrder][syNowPointer]){
							alreadyin = true;
							break;
						}
					}
					
					//�����û�мӽ������ͼӽ���,����finished��Ϊfalse�Ա����
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
		
		//�ȳ�ʼ����follow������
		Follows follow = new Follows();
		follow.ini();
		follow.work();
		
		//ÿ���õ��������㷨���������������Ȼ����Ҳ������ȫ˵������̷���
		boolean finished = false;
		int index = 0;//���index��¼��������Ҫ�����ڼ�����¼��ÿ�β���һ����¼֮ǰ��Ҫ++
		
		//�Ȳ�����һ����¼���ӵ�һ����¼��ɢ����
		closures[0] = getClosure(0);
		
//		int testLength0 = closures[index].closure.size();
//		for(int m=0; m<testLength0; m++){
//			System.out.println(closures[index].closure.get(m).order+ "\t"
//								+ closures[index].closure.get(m).pointer);
//		}
//		System.out.println();
		
		int i = 0;//���i��¼���������ߵ��ڼ�����¼�ˣ�ÿ��������һ�����i++
	
		while(!finished){
			
			if(closures[i].closure.size() == 0){
				finished = true;
				break;
			}				
			
			//���濪ʼ��һ���հ��Ĵ�����һ��Closure�Ĵ���
			int length = closures[i].closure.size();
			
			for(int j=0; j<length; j++){
				//���濪ʼ��ÿһ����Ŀ�Ĵ�������ÿһ��ClosureEle�Ĵ���
				ClosureEle ceNow = closures[i].closure.get(j);
				int syNowOrder = ceNow.order;
				int syNowPointer = ceNow.pointer;
				
				//�����һ��Ҫ�ƽ������ݣ�������ʲô
				int V = Syntaxes.syntaxes[syNowOrder][syNowPointer];
				if(V>=200){
					//�����һ��Ҫ�ƽ��������Ǹ�Vn����ô����ACTION������������
					//����index++��˵�������µ�״̬�ļ���
					index++;
					int VnNum = V - 200;//ACTION����λ��
					ACTION[i][VnNum] = index;
					
					//���漴Ϊ��������µıհ�
					//�Ȱѵ�һ����������ļ��룬����ָ��������һλ
					Closure cl = new Closure();
					ClosureEle ceToAdd = new ClosureEle();
					
					ceToAdd.order = syNowOrder;
					ceToAdd.pointer = syNowPointer+1;
					cl.closure.add(ceToAdd);
					
					//�ж�һ���Ƿ���������Ҫ�����תһ��״̬����Ŀ
					for(int k=j+1; k<length; k++){
						int syTempOrder = closures[i].closure.get(k).order;
						int syTempPointer = closures[i].closure.get(k).pointer;
						
						//��������һ���ƽ��ĺ����ڴ������һ��V���Ǿ����ˣ����������ͬ����״̬
						if(Syntaxes.syntaxes[syTempOrder][syTempPointer] == V){
							ceToAdd.order = syTempOrder;
							ceToAdd.pointer = syTempPointer+1;
							cl.closure.add(ceToAdd);
							j++;
						}
					}
					
					//���ϵĴ��ݸ��µ��Ժ�Ϳ������µ�������������һ��Closure��
					//�Ȱ������ʼֵ���ϣ��ٿ�ʼ���������Closure
					
					closures[index] = cl;
					closures[index] = getClosure(index);
					
					//����һ����������Ƿ��Ѿ���closure�����ˣ����ֳ������ֻ����һ��
					boolean alreadyin = true;
					//��ÿ��closure������
					for(int m=0; m<index; m++){
						alreadyin = true;
						int closurelength = closures[m].closure.size();
						int closureNowLength = closures[index].closure.size();
						//������closure���ĳ��ȺͲ��Ե�һ�����ٱȽ��Ƿ����������Ҳһģһ��
						if(closurelength == closureNowLength){
							for(int n=0; n<closurelength; n++){
								//��һ����һ���Ͳ�һ��
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
					//�����һ��Ҫ�ƽ��������Ǹ�Vt����ô����GOTO������������
					//����index++��˵�������µ�״̬�ļ���
					index++;
					int VtNum = V - 100;//GOTO����λ��
					GOTO[i][VtNum] = index;
					
					//���漴Ϊ��������µıհ�
					//�Ȱѵ�һ����������ļ��룬����ָ��������һλ
					Closure cl = new Closure();
					ClosureEle ceToAdd = new ClosureEle();
					
					ceToAdd.order = syNowOrder;
					ceToAdd.pointer = syNowPointer+1;
					cl.closure.add(ceToAdd);
					
					//�ж�һ���Ƿ���������Ҫ�����תһ��״̬����Ŀ
					if(j+1 < length){
//						System.out.println((j+1) + "\t" + length);
						for(int k=j+1; k<length; k++){
//							System.out.println(length + "\t" + closures[i].closure.size());
//							System.out.println(i + "  OKOK");
							int syTempOrder = closures[i].closure.get(k).order;
							int syTempPointer = closures[i].closure.get(k).pointer;
							
							//��������һ���ƽ��ĺ����ڴ������һ��V���Ǿ����ˣ����������ͬ����״̬
							if(Syntaxes.syntaxes[syTempOrder][syTempPointer] == V){
								ceToAdd.order = syTempOrder;
								ceToAdd.pointer = syTempPointer+1;
								cl.closure.add(ceToAdd);
								j++;
							}
						}
					}
					
					
					//���ϵĴ��ݸ��µ��Ժ�Ϳ������µ�������������һ��Closure��
					//�Ȱ������ʼֵ���ϣ��ٿ�ʼ���������Closure
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
					//��������Ըо����˺������ƽ���Լ��ͻ����Ϊ�ƽ�����ٿ��������µ���
					//�������һ����Լ����
					//��ô�Ͱ������VN��follow���е�Vt����GOTO���Ӧ����closure������Ϊ��Լ��
					int VnToSum = Syntaxes.syntaxes[syNowOrder][0];
					int Vntemp = VnToSum - 200;
					
					int lengthfo = follow.followers[Vntemp].first_followers.size();
					for(int k=1; k<lengthfo; k++){
						//ȡ�����VN������follow��Ȼ������Ƕ�Ӧ�Ĳ��ֵ������ݶ�����Ϊ��Լ
						//follow���п��ܴ�0Ϊ��ֹ�������ٿ���һ�£���
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
			//���������ȥ����������ˣ���������һ��closure�ˣ�����һ����¼��
			i++;
		}
	}
	
	
}
