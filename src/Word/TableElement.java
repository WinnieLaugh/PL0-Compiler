package Word;

public class TableElement {

	public int kind;
	public int value;
	public int level;
	public int add;
	public String name;
	public int size;
	
	public void setKind(int kind){
		this.kind = kind;
	}
	
	public void setValue(int value){
		this.value = value;
	}
	
	public void setLevel(int level){
		this.level = level;
	}
	
	public void setAdd(int add){
		this.add = add;
	}
		
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public int getKind(){
		return kind;
	}
	
	public int getValue(){
		return value;
	}
	
	public int getLevel(){
		return level;
	}
	
	public int getAdd(){
		return add;
	}
}
