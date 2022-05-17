package ds.dht;

import java.util.List;

public class SNode {
    int id;
    int pre;
    int next;
    int[] table;
    int FTLength;

    // 构造函数
    public SNode(int id,int FTLength){
        this.id = id;
        this.FTLength = FTLength;
        // 指状表
        this.table = new int[FTLength];
        // 默认前驱与后继都指向自己
        this.pre = id;
        this.next = id;
    }

    // 定位
    private void locateIndex(List<Integer> nodeIds){
        // 需要更新当前节点的前驱和后继节点
        // 首先知道表中的有效节点都有谁
        for(int i = 0; i < nodeIds.size() - 1; i++){
            if(nodeIds.get(i) <= id && nodeIds.get(i+1) > id){
                pre = nodeIds.get(i);
                next = nodeIds.get(i+1);
                return;
            }

        }
        // 我们需要另外更新最后一个节点的情况
        if(nodeIds.size() > 1 && id == nodeIds.get(nodeIds.size() - 1)){
            pre = nodeIds.get(nodeIds.size() - 2);
            next = nodeIds.get(0);
            return;
        }

        // 只有一个节点的情况
        if(nodeIds.size() == 1){
            if(nodeIds.get(0) < id){
                pre = nodeIds.get(0);
            }else if(nodeIds.get(0) > id){
                next = nodeIds.get(0);
            }
        }
    }

    // 为每个节点构造指状表
    public void buildFT(List<Integer> nodeIds) {
        int dhtSize = (int) Math.pow(2, FTLength);
        for(int i=0; i<FTLength; i++){
            // 计算偏移量，是进行映射查找的关键环节。
            // 始终牢记一个可用节点的概念，存入表中的起始位一定要大于等于可用节点的标号。
            // 且这样调用函数，已经考虑完毕环形结构序号轮回的情况。
            int FTVal = Math.floorMod(id + (int) Math.pow(2, i), dhtSize);
//            System.out.println("this is id: "+ this.id +" ,show the FTVal" + FTVal);
            // 布尔判断标记，记录该位是否已经计算出合适的哈希值
            boolean flag = false;
            for(int val : nodeIds){
                // 如果有效节点值刚好有不比本位制小的点，则用其填充
                if(val >= FTVal){
                    // 正好是计算值的下一个位置
                    table[i] = val;
                    flag = true;
                    break;
                }
            }
            if(!flag){
                // 说明计算完成FTVal之后，环路已经从原点0重新开始计算
                table[i] = nodeIds.get(0);
            }
        }
        // 留下序号处理接口，为后期的dht功能性扩展提供支撑
        locateIndex(nodeIds);
    }

    public void update(int oldVal, int target){
        for(int i = FTLength - 1; i >= 0; i--){
            if(table[i] == oldVal){
                table[i] = target;
            }
        }
    }
}
