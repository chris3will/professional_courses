//直接在这一个文件里写词法分析器
//2020/4/2 chris wang
#include <iostream>
#include <cstdio>
#include <cstdlib>
#include <cctype>
#include <cstring>

#ifndef FALSE
#define FALSE 0
#endif

#ifndef TRUE
#define TRUE 1
#endif

/*Buflen = length of the input buff for source code lines*/
#define BUFLEN 256  //缓冲区长度的声明

/* MAXRESERVED = the number of reserved words */
#define MAXRESERVED 8
int lineno = 0;
FILE* source;  //文件指针
FILE* code;
FILE* listing;

/* allocate and set tracing flags */
int EchoSource = TRUE;
int TraceScan = TRUE;
int TraceParse = FALSE;
int TraceAnalyze = FALSE;
int TraceCode = FALSE;

int Error = FALSE;


typedef enum
    /* book-keeping tokens */
    {ENDFILE,ERROR,
    /* reserved words */
    IF,THEN,ELSE,END,REPEAT,UNTIL,READ,WRITE,
    /* multicharacter tokens */
    ID,NUM,
    /* special symbols */
    ASSIGN,EQ,LT,PLUS,MINUS,TIMES,OVER,LPAREN,RPAREN,SEMI
    } TokenType;

//下面定义扫描程序的内容
#define MAXTOKENLEN 40
char tokenString[MAXTOKENLEN+1];
TokenType getToken();  //预先声明一下



//定义scan.c的内容
//DFA中的状态
typedef enum{
    START, INASSIGN, INCOMMIT, INNUM, INID, DONE
}StateType;


static char lineBuf[BUFLEN];
static int linepos = 0; //行中的位置
static int bufsize = 0;
static int EOF_flag = FALSE;

/*
 * 打印函数，把提取出的TOKEN根据标识符的情况选择打印
 * Procedure printToken prints a token
 * and its lexeme to the listing file
 */
void printToken( TokenType token, const char* tokenString )
{ switch (token)
    { case IF:
        case THEN:
        case ELSE:
        case END:
        case REPEAT:
        case UNTIL:
        case READ:
        case WRITE:
            fprintf(listing,
                    "reserved word: %s\n",tokenString);
            break;
        case ASSIGN: fprintf(listing,":=\n"); break;
        case LT: fprintf(listing,"<\n"); break;
        case EQ: fprintf(listing,"=\n"); break;
        case LPAREN: fprintf(listing,"(\n"); break;
        case RPAREN: fprintf(listing,")\n"); break;
        case SEMI: fprintf(listing,";\n"); break;
        case PLUS: fprintf(listing,"+\n"); break;
        case MINUS: fprintf(listing,"-\n"); break;
        case TIMES: fprintf(listing,"*\n"); break;
        case OVER: fprintf(listing,"/\n"); break;
        case ENDFILE: fprintf(listing,"EOF\n"); break;
        case NUM:
            fprintf(listing,
                    "NUM, val= %s\n",tokenString);
            break;
        case ID:
            fprintf(listing,
                    "ID, name= %s\n",tokenString);
            break;
        case ERROR:
            fprintf(listing,
                    "ERROR: %s\n",tokenString);
            break;
        default: /* should never happen */
            fprintf(listing,"Unknown token: %d\n",token);
    }
}

static int getNextChar()
{
    if(!(linepos < bufsize))
    {
        lineno++;
        if(fgets(lineBuf,BUFLEN-1,source))
        {
            if(EchoSource) fprintf(listing, "%4d: %s",lineno,lineBuf);
            bufsize = strlen(lineBuf);
            linepos = 0;
            return lineBuf[linepos++];
        } else{
            EOF_flag = TRUE;  //文件末尾标识符，此时进行必要的终止扫描
            return EOF;
        }
    }else{
        return lineBuf[linepos++];
    }
}

// 回退函数
static void ungetNextChar()
{
    if(!EOF_flag) linepos --;
}

//保留字查询表，即将线程的函数对已知的保留字进行遍历以确定身份
static struct
    {
        char* str;
        TokenType tok;
    }reservedWords[MAXRESERVED]
    ={{"if",IF},{"then",THEN},{"else",ELSE},{"end",END},
      {"repeat",REPEAT},{"until",UNTIL},{"read",READ},
      {"write",WRITE}};

//上述是将静态的保留字对应字典定义完毕，下面将定义函数来查询具体的string以获得对应的类型
static TokenType reservedLookup(char* s)
{
    int i;
    for(i=0;i<MAXRESERVED;i++)
    {
        if(!strcmp(s,reservedWords[i].str))
            return reservedWords[i].tok;
    }
    //如果这个string不与任何保留字对应，则其应被视为一个变量标识符
    return ID;
}

//核心扫描函数，getToken返回源文件中的下一个token
TokenType getToken()
{
    //根据DFA得到的TOKEN选择
    int tokenStringIndex=0;

    //这里面存储了需要返回的token变量
    TokenType currentToken;

    //当前DFA中的状态信息，需要从start开始
    StateType state = START;

    //标记变量save来判断此时读入的字符是否会被存入tokenbuf中
    int save;
    while(state!=DONE)
    {
        int c = getNextChar();  //读入下一个字符
        save = TRUE;  //先假设是正确的
        switch (state)
        {//先看看当前的DFA状态走到哪了，以便决定接下来可以接受的字符与相应的转移状态
            case START:
                if (isdigit(c))
                    state=INNUM;
                else if (isalpha(c))
                    state=INID;
                else if (c==':')
                    state=INASSIGN;
                else if ((c==' ')|| (c=='\t') || (c=='\n'))
                    save = FALSE;  //这些都是打字的信息，不属于任何TOKEN
                else if( c=='{')
                {
                    save = FALSE;
                    state = INCOMMIT;  // 现在是评论环节，不需要保存
                } else{
                    //可能的情况已经遍历完毕，然后对本次的TOKEN进行处理
                    state = DONE;
                    switch(c)
                    {
                        case EOF:
                            save = FALSE;  //文件结束符，不用保存
                            currentToken = ENDFILE;
                            break;
                        case '=':
                            currentToken=EQ;//这是一个等于符号
                            break;
                        case '<':
                            currentToken = LT;
                            break;
                        case '+':
                            currentToken = PLUS;
                            break;
                        case '-':
                            currentToken = MINUS;
                            break;
                        case '*':
                            currentToken = TIMES;
                            break;
                        case '/':
                            currentToken = OVER;
                            break;
                        case '(':
                            currentToken = LPAREN;
                            break;
                        case ')':
                            currentToken = RPAREN;
                            break;
                        case ';':
                            currentToken = SEMI;
                            break;
                        default:
                            currentToken = ERROR;
                            break;
                    }
                }
                break;
            case INCOMMIT:
                save = FALSE;
                if (c==EOF)
                {
                    state = DONE;  //评论部分结束了
                    currentToken = ENDFILE;
                }
                else if(c=='}')state = START;
                break;
            case INASSIGN:
                state = DONE;
                if(c=='=')
                    currentToken = ASSIGN;  //连接上了赋值符的第二个符号
                else
                {
                    //输入区回退，且报错;除了=都不是理想的输入内容
                    ungetNextChar();
                    save = FALSE;  //不保存
                    currentToken = ERROR;
                }
                break;
            case INNUM:
                if(!isdigit(c))
                {
                    //得到的字符不是数字
                    ungetNextChar();
                    save = FALSE;
                    state = DONE;
                    currentToken = NUM;
                }
                break;
            case INID:
                if(!isalpha(c))
                {
                    //根据上面一步，现在得到的判断是既不是数字现在也不是字母
                    ungetNextChar();//回退当前步骤得到的字符，指针前移
                    save = FALSE;
                    state = DONE;
                    currentToken = ID;
                }
                break;
            case DONE:
            default: //should never happen
                fprintf(listing,"Scanner Bug: state= %d\n",state);
                state = DONE;
                currentToken = ERROR;
                break;
        }
        if((save) && (tokenStringIndex <= MAXTOKENLEN))
            tokenString[tokenStringIndex++] = (char)c;
        if(state == DONE)
        {
            //只有真正到了完结的标志位
            //需要将其末尾添加标识符
            tokenString[tokenStringIndex] = '\0';
            if(currentToken == ID)
                currentToken = reservedLookup(tokenString);  //即观察它是不是保留字，否则还返回ID
        }
    }
    if(TraceScan)
    {
        //if i want to show the scan line number
        fprintf(listing, "\t%d: ",lineno);
        printToken(currentToken,tokenString);
    }
    return currentToken;
}//结束函数



int main(int argc, char*argv[]) {

    char pgm[120];  //源文件文件名
    if(argc < 2)
    {   //确保参数输入，长度为1是只有main.cpp本身，长度为2说明后跟参数
        fprintf(stderr,"usage: %s <filename>\n", argv[0]);
        exit(1);
    }
    strcpy(pgm,argv[1]);
    if(NULL == strchr(pgm, '.'))
        strcat(pgm,".tny");  //帮忙加入后缀
    source = fopen(pgm,"r");  //调用文件打开
    if(source == NULL)
    {
        //找不到源文件
        fprintf(stderr, "File %s not found\n",pgm);
        exit(1);
    }
    //把listing输入到标准输出上
    listing = stdout; // send listing to screen

    //相当于一个绑定
    fprintf(listing,"\nTINY COMPLIATION: %s\n",pgm);
    while(getToken()!=ENDFILE);

    fclose(source);
    return 0;
}
