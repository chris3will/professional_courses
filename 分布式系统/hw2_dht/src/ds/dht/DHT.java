package ds.dht;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DHT {
    // 采用环的原子管理算法进行管理
    Map<Integer, SNode> activeNodes;
    List<Integer> nodeIds;
    int FTSize;
    int DHTSize;

    public DHT(int FTSize){
        // 完成必要的初始化，为装载数据提供空间
        this.FTSize = FTSize;
        this.DHTSize = (int) Math.pow(2, FTSize);
//        System.out.println("the size of this dht is:" + this.DHTSize);
        // 维护着实节点
        // 每个value的长度应当与FTSize保持一致
        this.activeNodes = new HashMap<>();
        // Ids列表很关键，维护着系统中的有效数据。
        this.nodeIds = new ArrayList<>();
    }

    // 默认从第一个插入的节点进行查找工作
    // 采用递归调用方式不断去查找
    // 关键函数
    // 让节点id跟踪succ(id)沿着环进行线性搜索
    public int lookUp(int k, int startId){
        // 我们默认从startId节点开始查找
        // 如果startId本身对应一个实际节点，且k == startId的话，则不需要继续寻找，已经终止了

        SNode ft = activeNodes.get(startId);
        int[] table = ft.table;
//        System.out.println("Here in startId node, show its ft table" + startId + "!!!");
//        for(int a : table){
//            System.out.println(a);
//        }

        if(nodeIds.contains(startId)){
            if(k==startId)return startId;
        }
        // 检测目的信息是否在第一张表中就已经查到
        // 如果已经查到，则不继续进行
        for(int i=0;i<table.length;i++){
            // 初始表中已经包含必要信息，不需要再去查找。
            if(k==table[i])return startId;
        }

        // 对于一个identifier为k的key（即哈希值为k），它由环上第一个identifier不小于k的node负责维护。该node叫做identifier k的successor node，记作successor(k)。可以说successor(k)是环上从k顺时针起（包括k本身）第一个遇到的node。
        // 我们在环上又是按大小排列的，如果table[0]正好比k大，说明我们直接找到了
        if(table[0] >= k){
            return table[0];
        }

        for(int i = 0; i < table.length - 1; i++){
//            System.out.println("show the table[i]: "+table[i] + " and the table[i+1] "+ table[i+1] );
            if (table[i] <= k && table[i+1] > k){
                System.out.print(table[i] + " ");
                return lookUp(k, table[i]);
            }else if(table[i] <= k && table[i+1] >= 0 && table[i+1] <= startId){
                System.out.println(table[i] + " ");
                return lookUp(k, table[i]);
            }
        }
        // ft之前的位置都没有匹配成功，直接跳最后一项
        System.out.print(table[FTSize - 1] + " ");
        return lookUp(k, table[FTSize - 1]);
    }

    // 更新列表
    public void update(List<Integer> nodeIds){
        for(Integer id : activeNodes.keySet()){
            // 通过哈希关系获取映射，为每个节点明确finger table中存储的内容
            SNode node = activeNodes.get(id);
            // 为每个映射中的对象构造FT,且dht中activeNodes每发生一次更新，各个节点的finger table都要来一次重新计算
            node.buildFT(nodeIds);
        }
    }

    // 创建一个新节点，插入哈希表中
    public void insert(int k){
        // 为每个实节点创建一个finger table
        SNode ft = new SNode(k,FTSize);
        // 将该表插入哈希对象中
        activeNodes.put(k, ft);
        // 将该表序号进行存储以便标记
        nodeIds.add(k);
        // 我们每个节点中的finger table中的表项也只需要记录实key，即identifier的序号
        // 更新当前dht环中，每个节点上的finger table信息，以确保当前是最新状态
        update(nodeIds);
    }

    public void delete(int k){
        activeNodes.remove(k);
        nodeIds.remove(k);
        update(nodeIds);
    }

    public static void test_case1(){
        // 定义一个表长度为5的finger table，以满足32个结点组成的命名空间。
        DHT dht = new DHT(5);
        dht.insert(1);
        dht.insert(4);
        dht.insert(9);
        dht.insert(11);
        dht.insert(14);
        dht.insert(18);
        dht.insert(20);
        dht.insert(21);
        dht.insert(28);

        System.out.println("Searching the k = 26");
        System.out.print("We start from node 1, the search path is : ");
        System.out.println(dht.lookUp(26, 1));
        System.out.println("finishing search");
    }

    public static void test_case2(){
        // 定义一个表长度为7的finger table，以满足32个结点组成的命名空间。
        DHT dht = new DHT(7);
        // 模拟初始化，执行插入操作
        // 注意，第一项插入的内容决定了startId
        dht.insert(1);
        dht.insert(9);
        dht.insert(22);
        dht.insert(30);
        dht.insert(45);
        dht.insert(64);
        dht.insert(65);
        dht.insert(77);
        dht.insert(83);
        dht.insert(95);

        // 模拟查找行为
        System.out.println("Searching the k = 78");
        System.out.print("We start from node 1, the search path is : ");
        System.out.println(dht.lookUp(78, 1));
    }

    public static void test_case3(){
        // 定义一个finger table大小为3的实例
        DHT dht = new DHT(3);
        dht.insert(0);
        dht.insert(1);
        dht.insert(3);

        // 模拟查找行为
        System.out.println("Searching the k = 3");
        System.out.print("We start from node 0, the search path is : ");
        System.out.println(dht.lookUp(3, 0));
    }

    public static void main(String[] args){
        // 每次打印的均是跳转有效节点的路径
        test_case1();
        test_case2();
//        test_case3();
    }
}
