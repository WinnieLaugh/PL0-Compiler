package Word;

public class ErrorKind {
	
	static String errors[]={" ","error 0001:  常数说明中“=”写成“：=”",
							"error 0002:  常数说明中的“=”后应为数字",
							"error 0003:  常数说明中的标识符后应是“=”",
							"error 0004:  const,var,procedure后应为标识符",
							"error 0005:  漏掉了‘,’或‘;’",
							"error 0006:  过程说明后的符号不正确（应是语句开始符或过程开始符）",
							"error 0007:  应是语句开始符",
							"error 0008:  过程体内语句部分的后跟符不正确",
							"error 0009:  程序皆为丢了句号‘.’",
							"error 0010:  语句之间漏了‘;’",
							"error 0011:  标识符没说明",
							"error 0012:  赋值语句中，赋值号左部标识符属性应是变量",
							"error 0013:  赋值语句左部标识符应是赋值号:=",
							"error 0014:  call后应为标识符",
							"error 0015:  call后标识符属性应为过程",
							"error 0016:  条件语句中丢了then",
							"error 0017:  丢了end或;",
							"error 0018:  while型循环语句中丢了do",
							"error 0019:  语句后的标识符不正确",
							"error 0020:  应为关系运算符",
							"error 0021:  表达式内标识符属性不能是过程",
							"error 0022:  表达式中漏掉了右括号‘)’",
							"error 0023:  因子后的非法符号",
							"error 0024:  表达式开始符不能是此符号",
							"error 0025:  文件在不该结束的地方结束了",
							"error 0026:  结束符出现在不该结束的地方",
							"error 0027:  ","error 0028:  ","error 0029:  ","error 0030:  ",
							"error 0031:  数越界",
							"error 0032:  read语句括号中标识符不是变量",
							"error 0033:  else附近错误" ,
							"error 0034:  repeat附近错误"};
	
	
	public static void errorShow(int kind){
		System.out.println(errors[kind]);
	}
}
