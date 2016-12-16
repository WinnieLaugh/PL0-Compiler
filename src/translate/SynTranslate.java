package translate;

import getTable.DFA;
import getTable.Syntaxes;

import java.util.Stack;

import Word.GetSystem;

//先试一下语法分析正确不正确，然后再看怎么做语法制导翻译
public class SynTranslate {

	Stack<Integer> states = new Stack<Integer>();
	Stack<V_to_in> Vs = new Stack<V_to_in>();
	DFA dfaTable = new DFA();
	
	GetSystem gs = new GetSystem();
	
	public SynTranslate() {
		gs.initialize();
		gs.getCode();
		dfaTable.ini();
		dfaTable.GotoAction();
	}
	
	public void work(){
		Translater.ini();
		states.push(0);
		
		boolean used = true;
		while(true){
			if(used){
				gs.work();
				used = false;
			}
				
			int state_now = states.peek();
			int v_now = gs.getSym();
			
			if(gs.getSym() == -1){
				v_now = Syntaxes.NULL -100;
			}
				
			int next_step = dfaTable.GOTO[state_now][v_now];
			if(next_step == 1000){
				System.out.println("This is an accept condition! Congratulations!");
				return;
			}else if(next_step <1000){
				//如果是一个移进语句，就移进，则将这个Vt的used置为used，下一次再移进
				used = true;
				if(next_step == 0){
					System.out.println("This is a wrong program! The " + (100+v_now) + " Shouldn't be here!");
					return;
				}else{
					states.push(next_step);
					//把这个Vt放进去，如果是数字写入数字，如果是ID写入ID
					V_to_in v_to_in = new V_to_in();
					v_to_in.V = v_now + 100;
					if(v_to_in.V == Syntaxes.INT)
						v_to_in.value = gs.NUM;
					else if(v_to_in.V == Syntaxes.ID)
						v_to_in.id = gs.ID;
					Vs.push(v_to_in);
				}
			}else if(next_step > 1000){
				//如果是一个规约语句，则不移进，即不用改used，因为没有用到
				int syn = dfaTable.GOTO[state_now][v_now] - 1000;
				
				//处理将要规约的长度
				int length=0;
				while(Syntaxes.syntaxes[syn][length] != 0){
					length ++;
				}
				length--;
				if(Syntaxes.syntaxes[syn][1] == Syntaxes.NULL)
					length = 0;
				
				//进行规约操作，每次用都是新new的好啊，这样就不用担心以前用过的问题了
				//不过似乎还是得进行一下new操作，唔，也许不用，每次赋值又不调用
				
				V_to_in[] vss = new V_to_in[8];
				for(int i=0; i<length; i++){
					states.pop();
					vss[i] = Vs.pop();
				}
				
				//规约操作，生成代码什么的就交给其他类来做吧
				int V_to = Syntaxes.syntaxes[syn][0];
				V_to_in v_to_in = new V_to_in();
				v_to_in.V = V_to;
				
				Translater.translate(syn, vss,v_to_in);
				
				//接下来,就是把这个v_to_in再放入堆栈中了
				
				state_now = states.peek();
				next_step = dfaTable.ACTION[state_now][V_to - 200];
				if(next_step == 0){
					System.out.println("Wrong Grammar!" + V_to  + " Should't be here!");
					return;
				}else{
					states.push(next_step);
					//把这个VT放进去，这个VT之前在规约的时候应该有用
					Vs.push(v_to_in);
				}	
				
			}
		}

	}
	
}
