/*
 * @Author: Chris Wang
 * @Date: 2020-03-12 19:51:21
 * @LastEditors: your name
 * @LastEditTime: 2020-03-12 20:06:50
 * @Description: file content
 */
//5元组进行状态转移

#include<iostream>
#include<cstdio>
#include<map>

using namespace std;
const int MAX_ROW=5,MAX_COL=3;
enum state {s0=0,s1,s2,s3,ERR};

map<char,int> shift=
        {
                {'a',1},
                {'b',2}
        };

state changeFunction[MAX_ROW][MAX_COL]=
        {
                {ERR,s1,s0},
                {ERR,s1,s2},
                {ERR,s1,s3},
                {ERR,s1,s0}
        };

state F(state s,char c)
{
    int col = shift[c];
    state ret = changeFunction[s][col];
    return ret;
}

char getChar()
{
    char ret;
    cin>>ret;
    return ret;
}

void tabledrive()  //表驱动迭代函数
{
    state s=s0;
    char c = getChar();
    while(c!='$')
    {
        s = F(s,c);
        c = getChar();
    }
    if(s==s3)cout<<"yes"<<endl;
    else cout<<"no"<<endl;
}

int main()
{
    cout<<"enter your string with $ as the end to catch the substring of *abb"<<endl;
    while(1)
    {
        cout<<">";
        tabledrive();
    }
    return 0;
}