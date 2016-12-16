package getTable;

public class Syntaxes {
	public static int[][] syntaxes = new int[100][20];
	
	//下面为特殊符号的定义	
//	static int NULL = -1;
	
    //下面为终结符的数字代表定义，所以终结符均为以1开头的三位数
	public static int Begin = 100;
	public static int Call = 101;
	public static int CONST = 102;
	public static int Do = 103;
	public static int Else = 104;
	public static int End = 105;
	public static int IF = 106;
	public static int Odd = 107;
	public static int Procedure = 108;
	public static int Read = 109;
	public static int Repeat = 110;
	public static int Then = 111;
	public static int Until = 112;
	public static int VAR = 113;
	public static int While = 114;	
	public static int Write = 115;
	public static int ID = 116;
	public static int Semicolon = 117;
	public static int Period = 118;
	public static int Becomes = 119;
	public static int NULL = 120;
	public static int Times = 121;
	public static int Slash = 122;
	public static int Leq = 123;
	public static int Lparen = 124;
	public static int Rparen = 125;
	public static int Neq = 126;
	public static int Plus = 127;
	public static int Comma = 128;	
	public static int INT = 129;
	public static int Geq = 130;
	public static int Equal = 131;	
	public static int Minus = 132;
	public static int Lss = 133;
	public static int Gtr = 134;
	
	//这两个也是终结符，在词法分析的时候就能判断出来
//	static int ID = 151;
//	static int INT = 152;

	//下面是非终结符
	public static int S = 200;
	public static int S_1 = 201;
	public static int CS = 202;
	public static int VS = 203;
	public static int PS = 204;
	public static int L = 205;
	public static int CD = 206;
	public static int PF = 207;
	public static int VL = 208;
	public static int IFL = 209;
	public static int WHILEL = 210;
	public static int PROCL = 211;
	public static int COMPL = 212;
	public static int ST = 213;
	public static int IFS = 214;
	public static int SYRE = 215;
	public static int M = 216;
	public static int PMS = 217;
	public static int N = 218;
	public static int MDS = 219;
	public static int A = 220;
	public static int B = 221;
	public static int C = 222;
	public static int D = 223;
	public static int F = 224;
	public static int G = 225;
	public static int H = 226;
	public static int I = 227;
		
	public static void ini(){
		//S->S' 程序->分程序
		syntaxes[0][0] = S;
		syntaxes[0][1] = S_1;
		
		//S' -> [CS][VS][PS][L]
		syntaxes[1][0] = S_1;
		syntaxes[1][1] = CS;
		syntaxes[1][2] = VS;
		syntaxes[1][3] = PS;
		syntaxes[1][4] = L;
		
		//CS -> CONST CD A ;
		syntaxes[2][0] = CS;
		syntaxes[2][1] = CONST;
		syntaxes[2][2] = CD;
		syntaxes[2][3] = A;
		syntaxes[2][4] = Semicolon;
		
		//CS -> NULL 	
		syntaxes[3][0] = CS;
		syntaxes[3][1] = NULL;
		
		//A -> , CD A
		syntaxes[4][0] = A;
		syntaxes[4][1] = Comma;
		syntaxes[4][2] = CD;
		syntaxes[4][3] = A;
		
		//A -> NULL
		syntaxes[5][0] = A;
		syntaxes[5][1] = NULL;
		
		//VS -> VAR ID B ;
		syntaxes[6][0] = VS;
		syntaxes[6][1] = VAR;
		syntaxes[6][2] = ID;
		syntaxes[6][3] = B;
		syntaxes[6][4] = Semicolon;
		
		//VS -> NULL
		syntaxes[7][0] = VS;
		syntaxes[7][1] = NULL;
		
		//B -> , ID B
		syntaxes[8][0] = B;
		syntaxes[8][1] = Comma;
		syntaxes[8][2] = ID;
		syntaxes[8][3] = B;
		
		//B -> NULL
		syntaxes[9][0] = B;
		syntaxes[9][1] = NULL;
		
		//PS -> PF S_1 ; PS
		syntaxes[10][0] = PS;
		syntaxes[10][1] = PF;
		syntaxes[10][2] = S_1;
		syntaxes[10][3] = Semicolon;
		syntaxes[10][4] = PS;
		
		//PS -> NULL
		syntaxes[11][0] = PS;
		syntaxes[11][1] = NULL;
		
		//PF -> procedure ID ;
		syntaxes[12][0] = PF;
		syntaxes[12][1] = Procedure;
		syntaxes[12][2] = ID;
		syntaxes[12][3] = Semicolon;
		
		//L -> VL
		syntaxes[13][0] = L;
		syntaxes[13][1] = VL;
		
		//L -> IFL
		syntaxes[14][0] = L;
		syntaxes[14][1] = IFL;
		
		//L -> WHILEL
		syntaxes[15][0] = L;
		syntaxes[15][1] = WHILEL;
		
		//L -> PROCL
		syntaxes[16][0] = L;
		syntaxes[16][1] = PROCL;
		
		
		//L -> COMPL
		syntaxes[17][0] = L;
		syntaxes[17][1] = COMPL;
		
		//L -> NULL
		syntaxes[18][0] = L;
		syntaxes[18][1] = NULL;
		
		//CD -> ID = INT
		syntaxes[19][0] = CD;
		syntaxes[19][1] = ID;
		syntaxes[19][2] = Becomes;
		syntaxes[19][3] = INT;
		
		//VL->ID := ST
		syntaxes[20][0] = VL;
		syntaxes[20][1] = ID;
		syntaxes[20][2] = Becomes;
		syntaxes[20][3] = ST;
		
		//COMPL -> begin L C end
		syntaxes[21][0] = COMPL;
		syntaxes[21][1] = Begin;
		syntaxes[21][2] = L;
		syntaxes[21][3] = C;
		syntaxes[21][4] = End;
		
		//C -> ; L 
		syntaxes[22][0] = C;
		syntaxes[22][1] = Semicolon;
		syntaxes[22][2] = L;
		
		//C -> NULL
		syntaxes[23][0] = C;
		syntaxes[23][1] = NULL;
		
		//IFS -> ST SYRE　ST
		syntaxes[24][0] = IFS;
		syntaxes[24][1] = ST;
		syntaxes[24][2] = SYRE;
		syntaxes[24][3] = ST;
		
		//IFS -> odd ST
		syntaxes[25][0] = IFS;
		syntaxes[25][1] = Odd;
		syntaxes[25][2] = ST;
		
		//ST -> + M D
		syntaxes[26][0] = ST;
		syntaxes[26][1] = Plus;
		syntaxes[26][2] = M;
		syntaxes[26][3] = D;
		
		//ST -> - M D
		syntaxes[27][0] = ST;
		syntaxes[27][1] = Minus;
		syntaxes[27][2] = M;
		syntaxes[27][3] = D;
		
		//D -> PMS M D
		syntaxes[28][0] = D;
		syntaxes[28][1] = PMS;
		syntaxes[28][2] = M;
		syntaxes[28][3] = D;
		
		//D -> NULL:
		syntaxes[29][0] = D;
		syntaxes[29][1] = NULL;
	
		//M -> N F
		syntaxes[30][0] = M;
		syntaxes[30][1] = N;
		syntaxes[30][2] = F;
		
		//F -> MDS N F
		syntaxes[31][0] = F;
		syntaxes[31][1] = MDS;
		syntaxes[31][2] = N;
		syntaxes[31][3] = F;
		
		//F -> NULL
		syntaxes[32][0] = F;
		syntaxes[32][1] = NULL;
		
		//N -> ID
		syntaxes[33][0] = N;
		syntaxes[33][1] = ID;
		
		//N -> INT
		syntaxes[34][0] = N;
		syntaxes[34][1] = INT;
		
		//N -> ST
		syntaxes[35][0] = N;
		syntaxes[35][1] = ST;
		
		//PMS -> +
		syntaxes[36][0] = PMS;
		syntaxes[36][1] = Plus;
		
		//PMS -> -
		syntaxes[37][0] = PMS;
		syntaxes[37][1] = Minus;
		
		//MDS -> *
		syntaxes[38][0] = MDS;
		syntaxes[38][1] = Times;
		
		//MDS -> /
		syntaxes[39][0] = MDS;
		syntaxes[39][1] = Slash;
		
		//SYRE -> =
		syntaxes[40][0] = SYRE;
		syntaxes[40][1] = Equal;
		
		//SYRE -> #
		syntaxes[41][0] = SYRE;
		syntaxes[41][1] = Neq;

		//SYRE -> <
		syntaxes[42][0] = SYRE;
		syntaxes[42][1] = Lss;
		
		//SYRE -> <=
		syntaxes[43][0] = SYRE;
		syntaxes[43][1] = Leq;
		
		//SYRE -> >
		syntaxes[44][0] = SYRE;
		syntaxes[44][1] = Gtr;
		
		//SYRE -> >=
		syntaxes[45][0] = SYRE;
		syntaxes[45][1] = Geq;
		
		//IFL ->if IFS then I L
		syntaxes[46][0] = IFL;
		syntaxes[46][1] = IF;
		syntaxes[46][2] = IFS;
		syntaxes[46][3] = Then;
		syntaxes[46][4] = I;
		syntaxes[46][5] = L;
		
		//I -> NULL
		syntaxes[47][0] = I;
		syntaxes[47][1] = NULL;
				
		//PROC -> call ID
		syntaxes[48][0] = PROCL;
		syntaxes[48][1] = Call;
		syntaxes[48][2] = ID;
		
		//WHILEL ->while G IFS do H L
		syntaxes[49][0] = WHILEL;
		syntaxes[49][1] = While;
		syntaxes[49][2] = G;
		syntaxes[49][3] = IFS;
		syntaxes[49][4] = Do;
		syntaxes[49][5] = H;
		syntaxes[49][6] = L;
		
		// G -> NULL
		syntaxes[50][0] = G;
		syntaxes[50][1] = NULL;
		
		// H -> NULL;
		syntaxes[51][0] = H;
		syntaxes[51][1] = NULL;
			
	}
	
}
