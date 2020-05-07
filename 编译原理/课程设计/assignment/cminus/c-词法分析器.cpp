//这个文件旨在根据tiny的demo实现cminus的词法分析器

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

//定义cminus的缓冲区
#define BUFLEN 4096

//定义保留字数组的长度
#define MAXRESERVED 6
//定义行号
int lineno = 0;
FILE* source;  //源文件指针
FILE* listing;  //打印指针

//打印追踪标记
int EchoSource = FALSE;
int TraceScan = TRUE;

//定义各种枚举类型，避免用整数代替显得不易理解
typedef enum
    //文件结束符与检索错误标记
    {ENDFILE, ERROR,
     //保留字数组
     IF, ELSE, INT, RETURN, VOID, WHILE,
     //两类多字符标识符简称
     ID,NUM,
     //特别操作符
    PLUS, MINUS, TIMES, OVER, XYH, XYDY, DYH, DYDY, DE, UE, E, SEMI, DOU, DOT, LPAREN,
    RPAREN,LZKH,RZKH,LDKH,RDKH,
     } TokenType;  //学习样例，没有将空白符和注释加入特别枚举类型中，将在DFA中直接处理

//下面主要定义Scanner的内容
#define MAXTOKENLEN 256
char tokenString[MAXTOKENLEN + 1]; //还有一个休止符

//定义TOKEN变换的DFA,注意，把OVER的状态归结到了INCOMMENT中，这是由于CMINUS的词法规则导致的
typedef enum{
    START,INCOMMENT,INCOMMENT2,INCOMMENT3,INNUM,INID,INLE,INRE,INDE,INUE,DONE
}StateType;

//定义缓冲区
static char lineBuf[BUFLEN];
static int linepos = 0;  //行内的位置，方便打印，是用来读取和回退位于源文件内指针的标记
static int bufsize = 0;
static int EOF_flag = FALSE;  //文件末尾指针

//打印输出TOKEN结果函数
void printToken(TokenType token, const char* tokenString)
{
    switch (token)
    {
        //注释按照定义是不属于token的，将不被打印
        case IF:
        case ELSE:
        case INT:
        case RETURN:
        case VOID:
        case WHILE:
            fprintf(listing,"reserved word: %s\n",tokenString);
            break;
        case PLUS: fprintf(listing, "+\n");break;
        case MINUS: fprintf(listing, "-\n");break;
        case TIMES: fprintf(listing, "*\n");break;
        case OVER: fprintf(listing, "/\n");break;
        case XYH: fprintf(listing, "<\n");break;
        case XYDY: fprintf(listing, "<=\n");break;
        case DYH: fprintf(listing, ">\n");break;
        case DYDY: fprintf(listing, ">=\n");break;
        case DE: fprintf(listing, "==\n");break;
        case UE: fprintf(listing, "!=\n");break;
        case E:fprintf(listing, "=\n");break;
        case SEMI: fprintf(listing,";\n");break;
        case DOU: fprintf(listing, ",\n");break;
        case DOT: fprintf(listing,".\n");break;
        case LPAREN: fprintf(listing, "(\n");break;
        case RPAREN: fprintf(listing,")\n");break;
        case LZKH: fprintf(listing, "[\n");break;
        case RZKH: fprintf(listing,"]\n");break;
        case LDKH: fprintf(listing, "{\n");break;
        case RDKH: fprintf(listing, "}\n");break;
        case ENDFILE: fprintf(listing,"EOF\n");break;
        case NUM:
            fprintf(listing,
                    "NUM, name = %s\n",tokenString);
            break;
        case ID:
            fprintf(listing,
                    "ID, name = %s\n",tokenString);
            break;
        case ERROR:
            fprintf(listing,
                    "ERROR: %s\n",tokenString);
            break;
        default:
            fprintf(listing, "Unknown token: %d\n",token);
    }
}

//获取字符
static int getNextChar()
{
    //注意到无论是linepos还是lineno都是全局的
    //fgets只管读取，每次行内标号到哪都由linepos控制，只有正好读到了这行缓冲过的内容末尾，他就会归0，读下一行的内容
    if(!(linepos < bufsize))
    {
        //当这一行的位置没读完的时候就继续输出这一行
        //缓冲区不能超出
        lineno++; //行号，默认是从1开始读
        if(fgets(lineBuf, BUFLEN-1,source))
        {
            //从源文件指针读入一信息塞入lineBuf缓冲区
            //如果需要打印源文件的一行
            if(EchoSource) fprintf(listing,"%4d: %s",lineno,lineBuf);
            bufsize = strlen(lineBuf);  //得到这一行读入的有效字符长度
            linepos = 0;
            return lineBuf[linepos++];
        } else
        {
            EOF_flag = TRUE; //文件读取完毕，终止扫描
            return EOF;
        }
    } else
    {
        return lineBuf[linepos++];
    }
}

//回退函数
static void ungetNextChar()
{
    if(!EOF_flag) linepos --;
}

static struct
{
    char* str;
    TokenType tok;
}reservedWords[MAXRESERVED]=
        {
                {"if",IF},{"else",ELSE},{"int",INT},
                {"return",RETURN},{"void",VOID},{"while",WHILE}
        };

static TokenType reserverdLookup(char *s)
{
    for(auto & reservedWord : reservedWords)
    {
        if(!strcmp(s,reservedWord.str))
            return reservedWord.tok;
    }
    //他不是保留字，就是有字母构成的ID
    return ID;
}

//核心扫描函数
TokenType getToken()
{
    //tokenBuf的控制下标
    int tokenStringIndex = 0;  //缓冲区初始为空

    TokenType currentToken;  //存储需要返回的token

    //当前所处的DFA状态
    StateType state = START;

    //存储标记来判断是否将下一个字符读入Buf中
    int save;

    while(state!=DONE)
    {
        int c = getNextChar(); //从源文件读入字符
        save =TRUE;  //假设它是一般的需要存储的内容
        switch (state) {
            case START:
                if (isdigit(c))
                    state = INNUM;
                else if (isalpha(c))
                    state = INID;
                else if (c == '<')
                {
                    //save =FALSE;
                    state = INLE;
                }
                else if (c == '>')
                {
                    //save =FALSE;
                    state = INRE;
                }
                else if (c == '=')
                {
                    //save = FALSE;
                    state = INDE;
                }
                else if (c == '!')
                {
                    //save = FALSE;
                    state = INUE;
                }
                else if (c == '/')
                {
                    save = FALSE;  //我先不保存，因为现在不确定最终状态
                    state = INCOMMENT;
                }
                else if ((c == ' ') || (c == '\t') || (c == '\n'))
                    save = FALSE;
                else {
                    //可能的情况已经被遍历过
                    state = DONE;
                    switch (c)
                    {
                        case EOF:
                            save = FALSE;
                            currentToken = ENDFILE;  //文件到尾部了
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
                        case ';':
                            currentToken = SEMI;
                            break;
                        case ',':
                            currentToken = DOU;
                            break;
                        case '.':
                            currentToken = DOT;
                            break;
                        case '(':
                            currentToken = LPAREN;
                            break;
                        case ')':
                            currentToken = RPAREN;
                            break;
                        case '[':
                            currentToken = LZKH;
                            break;
                        case ']':
                            currentToken = RZKH;
                            break;
                        case '{':
                            currentToken = LDKH;
                            break;
                        case '}':
                            currentToken = RDKH;
                            break;
                    }
                }
                break;
            case INCOMMENT:
                //上一个字符是'/'我现在需要区分它是注释的第一部分还是单纯的over
                save = FALSE;
                if (c != '*')
                {
                    //这是一个单纯的over
                    ungetNextChar();
                    save = TRUE;
                    currentToken = OVER;  //修改状态
                }
                //这部分有动作，但是我又觉得这部分不能简单拆，还要把*/右半部分的这个单独拆出来才行
                else
                {
                    state = INCOMMENT2;
                    //仍然保持不保存状态
                }
                break;
            case INCOMMENT2:
                //我假设状态二也是可以收缩的
                save = FALSE;
                if(c=='*')
                {
                    state = INCOMMENT3;
                }
                else if(c==EOF)
                {
                    state = DONE;
                    currentToken = ENDFILE;
                }
                break;
            case INCOMMENT3:
                save = FALSE;
                if(c=='*')
                    state = INCOMMENT3;
                else if(c=='/')
                {
                    state = START;
                }
                else
                    state = INCOMMENT2;
                break;
            case INLE:
                state = DONE; //已经得到了2字符操作符的第二个字符，无论如何都可以结束了，要么成功要么报错
                if(c=='=')
                    currentToken = XYDY;
                else
                {
                    //是一个单纯的小于号
                    ungetNextChar();
                    currentToken = XYH;
                }
                break;
            case INRE:
                state = DONE;
                if(c=='=')
                    currentToken = DYDY;
                else
                {
                    //是一个单纯的大于号
                    ungetNextChar();
                    currentToken = DYH;
                }
                break;
            case INDE:
                state = DONE;
                if(c=='=')
                    currentToken = DE;
                else
                {
                    //这是一单纯的等号
                    ungetNextChar();
                    currentToken = E;
                }
                break;
            case INUE:
                state = DONE;
                if(c=='=')
                    currentToken = UE;
                else
                {
                    ungetNextChar();
                    save = FALSE;
                    currentToken = ERROR;
                }
                break;
            case INNUM:
                if(!isdigit(c))
                {
                    //得到的字符不是连续的数字
                    ungetNextChar();
                    save= FALSE;
                    state = DONE;
                    currentToken = NUM;
                }
                break;
            case INID:
                if(!isalpha(c))
                {
                    ungetNextChar();
                    save = FALSE;
                    state =DONE;
                    currentToken = ID;
                }
                break;
            case DONE:
            default:  //不应该发生的事情
                fprintf(listing, "Scanner Bug: state = %d",state);
                state = DONE;
                currentToken = ERROR;
                break;
        }
        if((save) && (tokenStringIndex <= MAXTOKENLEN))
        {
            tokenString[tokenStringIndex++] = (char)c;
        }
        if(state == DONE)
        {
            //真正的结束了，将字符串末尾添加休止符
            tokenString[tokenStringIndex] = '\0';
            if(currentToken == ID)
                currentToken = reserverdLookup(tokenString);
        }
    }
    if(TraceScan)
    {
        //这一个Token已经查询完毕
        fprintf(listing, "\t%d: ",lineno);
        printToken(currentToken, tokenString);
    }
    return currentToken;
}



int main(int argc, char* argv[]) {
    char pgm[120];

    if(argc < 2)
    {
        fprintf(stderr, "usage: %s <filename>\n",argv[0]);
        exit(1);
    }
    strcpy(pgm,argv[1]);
    if(NULL == strchr(pgm,'.'))
        strcat(pgm,".c-");
    source = fopen(pgm,"r");  //以制度方式打开文件
    if(source == NULL)
    {
        fprintf(stderr,"File %s not found.\n",pgm);
        exit(1);
    }

    listing = stdout;
    while(getToken()!=ENDFILE);

    fclose(source);
    //std::cout << "Hello, World!" << std::endl;
    return 0;
}
