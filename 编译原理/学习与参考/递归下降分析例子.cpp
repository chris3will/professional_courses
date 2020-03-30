/*
 * @Author: Chris Wang
 * @Date: 2020-03-30 20:21:25
 * @LastEditors: your name
 * @LastEditTime: 2020-03-30 20:35:02
 * @Description: file content
 */
#include<iostream>
#include<cstdio>
#include<cstring>
/*
 * Grammar: E --> i E'
 *  E' --> + i E' | e
 *  程序运行之后直接输入一串字符串，如果不满足上述的语法定义，则会输出error
 */

using namespace std;

char l;
void match(char t)
{
    if(l==t)
    {
        l = getchar();
    }
    else
    {
        printf("Error");
    }

}
void E_()
{
    if(l=='+')
    {
        match('+');
        match('i');
        E_();
    }
    else
        return;
}
void E()
{
    if(l=='i')
    {
        match('i');
        E_();
    }
}
int main()
{
    l = getchar();
    E();

    if (l == '$')
        printf("Parsing Successful");
}