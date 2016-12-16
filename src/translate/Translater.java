package translate;

import getTable.Syntaxes;
import Word.TableElement;

public class Translater {

	static CodeEle[] codes = new CodeEle[100];
	static TableElement[] table = new TableElement[20];
	static int table_i =0;
	static int cx =0;
	static int lev = 0;
	int SYM;
	String ID;
	int Num;
	static int add = 3;
	
	public static void ini(){
		CodeEle ce = new CodeEle();
		ce.f = "jmp";
		ce.l = 0;
		ce.a = 0;
		gen(ce);
		CodeEle ce2 = new CodeEle();
		ce2.f = "jmp";
		ce2.l = 0;
		ce2.a = 0;
		gen(ce2);
	}
	
	public static void translate(int syn, V_to_in[] v, V_to_in vn_to_in){
		CodeEle ce = new CodeEle();
		switch(syn){
		case 2:
			makeTable(v[2].id, Syntaxes.CONST, v[2].value, 0, 0, 0);
			break;
		case 4:
			makeTable(v[1].id, Syntaxes.CONST, v[1].value, 0, 0, 0);
			break;	
		case 6:
			vn_to_in.id = v[2].id;
			makeTable(v[2].id, Syntaxes.VAR, 0, lev, add++, 0);
			break;
		case 8:
			vn_to_in.id = v[2].id;
			makeTable(v[1].id, Syntaxes.VAR, 0, lev, add++, 0);
			break;
		case 12:
			makeTable(v[1].id, Syntaxes.Procedure, 0, lev++, cx, (add-3+2));
			codes[1].a = cx;
			add = 3;
			ce.f = "INT";
			ce.l = 0;
			ce.a = lookup(v[1].id).size;
			gen(ce);
			break;
		case 14:
			ce.f = "JMP";
			ce.l = 0;
			ce.a = v[0].place;
			gen(ce);
			break;
		case 15:
			ce.f = "JMP";
			ce.l = 0;
			ce.a = v[0].place;
			gen(ce);
			break;
		case 19:
			vn_to_in.id = v[2].id;
			vn_to_in.value = v[0].value;
			break;
		case 20:
			ce.f = "STO";
			ce.l = lev - lookup(v[2].id).level;
			ce.a = lookup(v[2].id).add;
			gen(ce);
			break;
		case 21:
			ce.f = "OPR";
			ce.l = 0;
			ce.a = 0;
			gen(ce);
			break;
		case 24:
			ce.f = "OPR";
			ce.l = 0;
			switch (v[1].V) {
			case 131://Equal
				ce.a = 8;
				break;
			case 126://Not Equal
				ce.a = 9;
				break;
			case 133://Less than
				ce.a = 10;
				break;
			case 123://Less or Equal
				ce.a = 13;
				break;
			case 134://Greater Than
				ce.a = 12;
				break;
			case 130://Greater than or Equal
				ce.a = 11;
				break;
			default:
				break;
			}
			gen(ce);
			break;
		case 25:
			ce.f = "OPR";
			ce.l = 0;
			ce.a = 6;
			gen(ce);
			break;
		case 26:
			ce.f = "OPR";
			ce.l = 0;
			ce.a = 2;
			gen(ce);
			break;
		case 27:
			ce.f = "OPR";
			ce.l = 0;
			ce.a = 3;
			gen(ce);
			break;
		case 28:
			ce.f = "OPR";
			ce.l = 0;
			if(v[2].V == Syntaxes.Plus)
				ce.a = 2;
			else
				ce.a = 3;
			gen(ce);
			break;
		case 31:
			ce.f = "OPR";
			ce.l = 0;
			if(v[2].V == Syntaxes.Times)
				ce.a = 4;
			else
				ce.a = 5;
			gen(ce);
			break;
		case 33:
			ce.f = "LOD";
			ce.l = lev - lookup(v[0].id).level;
			ce.a = lookup(v[0].id).add;
			gen(ce);
			break;
		case 34:
			ce.f = "LIT";
			ce.l = 0;
			ce.a = v[0].value;
			gen(ce);
			break;
		case 36:
			vn_to_in.V = v[0].V;
			break;
		case 37:
			vn_to_in.V = v[0].V;
			break;
		case 38:
			vn_to_in.V = v[0].V;
			break;
		case 39:
			vn_to_in.V = v[0].V;
			break;
		case 40:
			vn_to_in.V = v[0].V;
			break;
		case 41:
			vn_to_in.V = v[0].V;
			break;
		case 42:
			vn_to_in.V = v[0].V;
			break;
		case 43:
			vn_to_in.V = v[0].V;
			break;
		case 44:
			vn_to_in.V = v[0].V;
			break;
		case 45:
			vn_to_in.V = v[0].V;
			break;
		case 46:
			ce.f = "JPC";
			ce.l = 0;
			ce.a = v[1].place;
			gen(ce);
			break;
		case 47:
			vn_to_in.place = cx;
			break;
		case 48:
			ce.f = "CAL";
			ce.l = lev - lookup(v[0].id).level;
			ce.a = lookup(v[0].id).add;
			gen(ce);
			break;
		case 49:
			ce.f = "JPC";
			ce.l = 0;
			ce.a = v[1].place;
			gen(ce);
			vn_to_in.place = v[4].place;
			break;
		case 50:
			vn_to_in.place = cx;
			break;
		case 51:
			vn_to_in.place = cx;
			break;
		default:
			break;
		}
	}
	
	
	public static void makeTable(String name, int sym, int value, int level, int add, int size){
		TableElement te = new TableElement();
		te.add = add;
		te.level = level;
		te.value = value;
		te.kind = sym;
		te.name = name;
		te.size = size;
		table[table_i] = te;
		table_i++;
	}
	
	public static void gen(CodeEle ce){
		codes[cx] = ce;
		cx++;
	}
	
	public static TableElement lookup(String id){
		
		for(int i=0; i<20; i++){
			if(table[i] != null){
				if(table[i].name.equals(id))
					return table[i];
			}
		}
		return null;
	}

	public static void ShowCodes(){
		for(int i=0; i<100; i++){
			if(codes[i] == null)
				break;
			else{
				System.out.println(i + "\t" + codes[i].f + "\t"
									+ codes[i].l + "\t" + codes[i].a);
			}
		}
	}
	
	public static void showTable(){
		for(int i=0; i<20; i++){
			if(table[i] == null)
				break;
			else{
				if(table[i].kind == Syntaxes.CONST)
					System.out.println("NAME: "+ table[i].name + "\t" + "KIND: CONST" +
										"\t" + "VALUE: " +table[i].value);
				else if(table[i].kind == Syntaxes.VAR)
					System.out.println("NAME: " + table[i].name + "\t" + "KIND: VARIABLE" + "\t" 
										+ "LEVEL: "+ table[i].level + "\t" + "ADDR: " + table[i].add);
				else
					System.out.println("NAME: "+ table[i].name + "\t" + "KIND: PROCEDURE" + "\t" +
										"\t" + "LEVEL: "+ table[i].level + "\t"+ "ADD: "+
										table[i].add + "\t" + "SIZE: "+ table[i].size);
			}
		}
	}
}
