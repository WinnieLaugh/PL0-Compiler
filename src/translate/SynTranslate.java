package translate;

import getTable.DFA;
import getTable.Syntaxes;

import java.util.Stack;

import Word.GetSystem;

//����һ���﷨������ȷ����ȷ��Ȼ���ٿ���ô���﷨�Ƶ�����
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
				//�����һ���ƽ���䣬���ƽ��������Vt��used��Ϊused����һ�����ƽ�
				used = true;
				if(next_step == 0){
					System.out.println("This is a wrong program! The " + (100+v_now) + " Shouldn't be here!");
					return;
				}else{
					states.push(next_step);
					//�����Vt�Ž�ȥ�����������д�����֣������IDд��ID
					V_to_in v_to_in = new V_to_in();
					v_to_in.V = v_now + 100;
					if(v_to_in.V == Syntaxes.INT)
						v_to_in.value = gs.NUM;
					else if(v_to_in.V == Syntaxes.ID)
						v_to_in.id = gs.ID;
					Vs.push(v_to_in);
				}
			}else if(next_step > 1000){
				//�����һ����Լ��䣬���ƽ��������ø�used����Ϊû���õ�
				int syn = dfaTable.GOTO[state_now][v_now] - 1000;
				
				//����Ҫ��Լ�ĳ���
				int length=0;
				while(Syntaxes.syntaxes[syn][length] != 0){
					length ++;
				}
				length--;
				if(Syntaxes.syntaxes[syn][1] == Syntaxes.NULL)
					length = 0;
				
				//���й�Լ������ÿ���ö�����new�ĺð��������Ͳ��õ�����ǰ�ù���������
				//�����ƺ����ǵý���һ��new��������Ҳ���ã�ÿ�θ�ֵ�ֲ�����
				
				V_to_in[] vss = new V_to_in[8];
				for(int i=0; i<length; i++){
					states.pop();
					vss[i] = Vs.pop();
				}
				
				//��Լ���������ɴ���ʲô�ľͽ���������������
				int V_to = Syntaxes.syntaxes[syn][0];
				V_to_in v_to_in = new V_to_in();
				v_to_in.V = V_to;
				
				Translater.translate(syn, vss,v_to_in);
				
				//������,���ǰ����v_to_in�ٷ����ջ����
				
				state_now = states.peek();
				next_step = dfaTable.ACTION[state_now][V_to - 200];
				if(next_step == 0){
					System.out.println("Wrong Grammar!" + V_to  + " Should't be here!");
					return;
				}else{
					states.push(next_step);
					//�����VT�Ž�ȥ�����VT֮ǰ�ڹ�Լ��ʱ��Ӧ������
					Vs.push(v_to_in);
				}	
				
			}
		}

	}
	
}
