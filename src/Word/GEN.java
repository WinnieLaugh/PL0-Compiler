package Word;

public class GEN {

	TableElement[] table = new TableElement[100];
	int i=0;
	int level = 0;
	
	int SYM;
	String ID;
	int Num;
	int add = 3;
	
	boolean isState; 
	
	public GEN(){
		GetSystem get = new GetSystem();
		get.initialize();

		for(int j=0; j<100; j++){
			table[j] = new TableElement();
		}
		
		try {
			get.getCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		boolean finished = get.getState();
		while(!finished){
			get.work();
			SYM = get.getSym();

			System.out.println(get.comp + "\t" + get.SYM + " OKOK!");
			if(SYM == 2){
				get.work();
				ID = get.getId();
				table[i].setKind(SYM);
				table[i].setName(ID);
				get.work();
				SYM = get.getSym();
				if(SYM == 19){
					get.work();
					Num = get.getNum();
					table[i].setValue(Num);
				}
				i++;
				get.work();
				System.out.println(get.comp + "\t" + get.SYM);
			}else if(SYM == 13){
				get.work();
				ID = get.getId();
				table[i].setKind(SYM);
				table[i].setName(ID);
				table[i].setAdd(add);
				add++;
				get.work();
				i++;
				if(SYM == 41){
					get.work();
					Num = get.getNum();
					table[i].setValue(Num);
					table[i].setLevel(level);
					table[i].setAdd(add);
					i++;
					add++;
				}else{
					while(get.SYM == 42){
						get.work();
						ID = get.getId();
						table[i].setKind(13);
						table[i].setName(ID);
						table[i].setAdd(add);
						i++;
						add++;
						get.work();
					}
				}
				System.out.println(get.comp + "\t" + get.SYM);
			}else if(SYM == 8){
				get.work();
				ID = get.getId();
				table[i].setLevel(level);
				table[i].setKind(SYM);
				table[i].setName(ID);
				i++;
				add = 3;
				level ++;
				if(level >= 4){
//					回头再定义这个错误
//					ErrorKind.errorShow(0);
				}
				get.work();
				System.out.println(get.comp + "\t" + get.SYM);
			}else {
				isState = false;
				break;
			}
			
			finished = get.getState();
		}
		
		
	}
	
	public TableElement[] getTable(){
		return table;
	}
}
