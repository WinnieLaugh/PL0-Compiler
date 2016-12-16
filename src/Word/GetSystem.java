package Word;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;	

public class GetSystem {
	
	public int SYM = -1;
	public String ID;
	public int NUM = 0;
	public String comp;
	public char[] code = new char[500];
	int length;
	public final String word[] = {"begin","call","const","do","else","end","if","odd",
							"procedure","read","repeat","then","until","var","while","write"};
	
	public int BeginSym=0, CallSym=1, ConstSym=2, DoSym=3, ElseSym=4, EndSym=5, IfSym=6, OddSym=7, ProcSym=8;
	public int ReadSym=9, RepeatSym=10, ThenSym=11, UntilSym=12, VarSym=13, WhileSym=14, WriteSym=15;
	public int Ident=16, Semicolon=17, Period=18, Becomes=19, NUL=20, Times=21, Slash=22, Leq=23, Lparen=24;
	public int Rparen=25, Neq=26, Plus=27, Comma=28, Number=29, Geq=30, Eql=31;
	public int Minus=32, Lss=33, Gtr=34;
	
	public int symbolSym[] = new int[30];
	public boolean finished = false;
	
	//index_i是记录现在取到哪里了，就相当于C中的getch里面的指针
	int index_i = 0;
	
	public void initialize(){
	
		symbolSym['+' - 33] = Plus;
		symbolSym['-' - 33] = Minus;
		symbolSym['*' - 33] = Times;
		symbolSym['/' - 33] = Slash;
		symbolSym['(' - 33] = Lparen;
		symbolSym[')' - 33] = Rparen;
		symbolSym['=' - 33] = Eql;
		symbolSym[',' - 33] = Comma;
		symbolSym['.' - 33] = 43;
		symbolSym['#' - 33] = Neq;
		symbolSym['<' - 33] = Lss;
		symbolSym['>' - 33] = Gtr;
		symbolSym[';' - 33] = Semicolon;
		
   }
	
	char word_now[] = new char[10];
	
	
	public void getCode(){
		File file = new File("D:\\workspace\\java\\JavaTest\\Compiler2.0", "test.txt");
		
		try {
			// 读取文件内容 (输入流)
			FileInputStream out = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(out);
			int ch;
			
			int i=0;
			while ((ch = isr.read()) != -1) {
				code[i] = (char)ch;
				i++;
			}
			
			length = i;
			isr.close();
			
		} catch (Exception e) {
		}
	}
	
	public boolean isDigit(char ch){
		boolean is = false;
		int test = ch - '0';
		if(test >=0 && test <=9){
			is = true;
		}
		return is;
	}
	

	public char GetCh(int index){	
		char ch = code[index];
		return ch;
	}
	
	
	public boolean isAlpha(char ch){
		boolean is = true;
		int test = ch - 'a';
		if(test<0 || test>25){
			test = ch - 'A';
			if(test<0 || test>25){
				is = false;
			}
		}
		return is;		
	}
	
	public void work(){		
		
		//初始化，清空一下
		for(int j=0; j<10; j++){
			word_now[j] = 0;
		}
		SYM = -1;
		ID = new String();
		NUM = 0;
		comp = new String();
		
		if(index_i == length)
		{
			finished = true;
			return;
		}
		
		int index_temp = index_i;
		//除去空格以后取出第一个非空格字符
		while(true){
			if(GetCh(index_temp)==' ' || GetCh(index_temp) == 9 ||
					GetCh(index_temp) == 10 || GetCh(index_temp) == '\r' ){
				index_temp++;
				continue;
			}else{
				word_now[0] = GetCh(index_temp);
				index_i = index_temp;
				break; 
			}
		}
		
		if(isAlpha(word_now[0])){
			//这里应该要考虑到有可能不是字母或者数字，而是符号
			    int word_i=0;
				boolean con = true;

				//取出这个单词
				while(con){
					word_now[word_i++]=GetCh(index_i++);
					con=isAlpha(GetCh(index_i)) || isDigit(GetCh(index_i));
				}
				comp = String.valueOf(word_now);
				comp = comp.trim();
				
				//比较，看这个单词是不是关键词
				boolean isSYM = false;
				for(int index_key=0; index_key<16; index_key++){
					if(comp.equals(word[index_key])){
						SYM = index_key;
						isSYM = true;
						break;
					}
				}
				
				//如果不是关键词，那就把它设为id
				if(!isSYM){
					SYM = Ident;
					ID = comp;
				}
			}else if(isDigit(word_now[0])){
				SYM = Number;
				int num_now = word_now[0] - '0';
				for(int i=0; i<10; i++){
					if(isDigit(GetCh(index_i))){
						num_now = GetCh(index_i) - '0';
						NUM = NUM*10 + num_now;
						index_i++;
					}else
						break;
				}
			}else if(word_now[0] == ':'){
				index_i++;
				word_now[1] = GetCh(index_i);
				if(word_now[1] == '='){
					index_i++;
					SYM = Becomes;
				}else{
					SYM = NUL;
				}
			}else if(word_now[0] == '<'){
				index_i++;
				word_now[1] = GetCh(index_i);
				if(word_now[1] == '='){
					index_i++;
					SYM = Leq;
				}else{
					SYM = Lss;
				}
			}else if(word_now[0] == '>'){
				index_i++;
				word_now[1] = GetCh(index_i);
				if(word_now[1] == '='){
					index_i++;
					SYM = Geq;
				}else{
					SYM = Gtr;
				}
			}else{
				SYM = symbolSym[word_now[0] - 33]; 
				index_i++;
			}
	}
	
	public int getSym(){
		return SYM;
	}
	
	public String getId(){
		return ID;
	}
	
	public int getNum(){
		return NUM;
	}
	
	public boolean getState(){
		return finished;
	}
}
