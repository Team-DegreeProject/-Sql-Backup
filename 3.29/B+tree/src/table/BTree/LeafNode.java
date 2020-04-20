package table.BTree;

class LeafNode <T, V extends Comparable<V>> extends Node<T, V> {

    protected Object values[];
    protected LeafNode left;
    protected LeafNode right;

    public LeafNode(){
        super();
        this.values = new Object[BPlusTree.getmaxKeyNumber()];
        this.left = null;
        this.right = null;
    }

    //二分查找
    @Override
    public T select(V key) {
        if(this.keyNumber <=0 || key.compareTo((V)this.keys[this.keyNumber-1])>0)
            return null;
        int low = 0;
        int up = this.keyNumber;
        int middle = (low + up) / 2;
        while(low < up){
            V middleKey = (V) this.keys[middle];
            if(key.compareTo(middleKey) == 0)
                return (T) this.values[middle];
            else if(key.compareTo(middleKey) < 0)
                up = middle;
            else
                low = middle;
            middle = (low + up) / 2;
        }
        return null;
    }

    @Override
    public Node selectRange(V key) {
        if(this.keyNumber <=0 || key.compareTo((V)this.keys[this.keyNumber-1])>0)
            return null;
        int low = 0;
        int up = this.keyNumber;
        int middle = (low + up) / 2;
        while(low < up){
            V middleKey = (V) this.keys[middle];
            if(key.compareTo(middleKey) == 0)
                return this;
            else if(key.compareTo(middleKey) < 0)
                up = middle;
            else
                low = middle;
            middle = (low + up) / 2;
        }
        return null;
    }


    @Override
    public Node<T, V> insert(T value, V key) {
        //找到插入数据位置

        int i = this.keyNumber-1;
        while(i >=0){
            if(key.compareTo((V) this.keys[i]) > 0){
                break;
            } else if(key.compareTo((V) this.keys[i]) == 0){
                return null;
            }
            i--;
        }
        //复制数组,完成添加
        Object tempKeys[] = new Object[BPlusTree.getmaxKeyNumber()];
        Object tempValues[] = new Object[BPlusTree.getmaxKeyNumber()];
        if(i==(-1)){
            System.arraycopy(this.keys, 0, tempKeys, 1, this.keyNumber);
            System.arraycopy(this.values, 0, tempValues, 1, this.keyNumber);
            tempKeys[0] = key;
            tempValues[0] = value;
        }else{
            System.arraycopy(this.keys, 0, tempKeys, 0, i+1);
            System.arraycopy(this.values, 0, tempValues, 0, i+1);
            System.arraycopy(this.keys, i+1, tempKeys, i + 2, this.keyNumber - i - 1);
            System.arraycopy(this.values, i+1, tempValues, i + 2, this.keyNumber - i - 1);
            tempKeys[i+1] = key;
            tempValues[i+1] = value;
        }
        this.keyNumber++;
//            System.out.println("插入完成,当前节点key为:");
//            for(int j = 0; j < this.keyNumber; j++)
//                System.out.print(tempKeys[j] + " ");
//            System.out.println();
        //判断插入值是否小于父节点
        if(i==(-1)){
            Node node = this;
//            System.out.println("999当前节点key为:");
//            for(int j = 0; j < this.keyNumber; j++)
//                System.out.print(node.keys[j] + " ");
//            System.out.println();
            V tempkey=(V)tempKeys[0];
            while (node.parent != null){
                if(tempkey.compareTo((V)node.parent.keys[0]) < 0){
                    node.parent.keys[0] = tempkey;
                    node = node.parent;
                }
                else {
                    break;
                }
            }
        }
        //保存该节点储存在父节点的key值
        V oldKey = null;
        if(this.keyNumber > 0)
            oldKey = (V) tempKeys[0];
        //判断是否需要拆分
        //如果不需要拆分完成复制后直接返回
        if(this.keyNumber <= BPlusTree.getOrder()){
            System.arraycopy(tempKeys, 0, this.keys, 0, this.keyNumber);
            System.arraycopy(tempValues, 0, this.values, 0, this.keyNumber);
//                System.out.println("叶子节点,插入key: " + key + ",不需要拆分");
            return null;
        }
        //如果需要拆分,则从中间把节点拆分差不多的两部分
        int middle = this.keyNumber / 2;
        //新建叶子节点,作为拆分的右半部分
        LeafNode<T, V> tempNode = new LeafNode<T, V>();
        tempNode.keyNumber = this.keyNumber - middle;
        //如果父节点为空，新建父节点
        if(this.parent == null) {
            NonLeafNode<T, V> tempNonLeafNode = new NonLeafNode<T, V>();
            tempNode.parent = tempNonLeafNode;
            this.parent = tempNonLeafNode;
            oldKey = null;
        }else{
            tempNode.parent = this.parent;
        }

        System.arraycopy(tempKeys, middle, tempNode.keys, 0, tempNode.keyNumber);
        System.arraycopy(tempValues, middle, tempNode.values, 0, tempNode.keyNumber);
        //让原有叶子节点作为拆分的左半部分
        this.keyNumber = middle;
        this.keys = new Object[BPlusTree.getmaxKeyNumber()];
        this.values = new Object[BPlusTree.getmaxKeyNumber()];
        System.arraycopy(tempKeys, 0, this.keys, 0, middle);
        System.arraycopy(tempValues, 0, this.values, 0, middle);
        //刷新左右叶节点左右指针
        tempNode.right=this.right;
        if(this.right!=null){
            this.right.left=tempNode;
        }
        this.right = tempNode;
        tempNode.left = this;


//        System.out.println("当前叶节点key为:");
//        for(int j = 0; j < this.keyNumber; j++)
//            System.out.print(this.keys[j] + " ");
//        System.out.println();
//        System.out.println("oldkey "+oldKey);
        //将新节点插入到父节点
        NonLeafNode<T, V> parentNode = (NonLeafNode<T, V>)this.parent;
        return parentNode.insertNode(this, tempNode, oldKey);
    }

    @Override
    public boolean delete(V key) {

//        System.out.println("删除： "+key+"   keynumber： "+this.keyNumber);
//        System.out.println("当前叶子节点key为:"+this);
//        for(int z = 0; z < this.keyNumber; z++)
//            System.out.print(this.keys[z] + " ");
//        System.out.println();
//        System.out.println("当前叶子节点父节点key为:");
//        for(int z = 0; z < this.parent.keyNumber; z++)
//            System.out.print(this.parent.keys[z] + " ");
//        System.out.println();
//        if(this.right!=null){
//            System.out.println("当前叶子节点右节点key为:");
//            for(int z = 0; z < this.right.keyNumber; z++)
//                System.out.print(this.right.keys[z] + " ");
//            System.out.println();
//        }
//        if(this.left!=null){
//            System.out.println("当前叶子节点左节点key为:");
//            for(int z = 0; z < this.left.keyNumber; z++)
//                System.out.print(this.left.keys[z] + " ");
//            System.out.println();
//            System.out.println("当前叶子节点左节点父节点key为:");
//            for(int z = 0; z < this.left.parent.keyNumber; z++)
//                System.out.print(this.left.parent.keys[z] + " ");
//            System.out.println();
//            System.out.println("当前叶子节点左节点右节点key为:");
//            for(int z = 0; z < this.left.right.keyNumber; z++)
//                System.out.print(this.left.right.keys[z] + " ");
//            System.out.println();
//        }

        //记录该节点最小key值
        V oldKey=(V)this.keys[0];
        //找到删除数据位置
        int i = this.keyNumber-1;
        while(i >=0){
            if(key.compareTo((V) this.keys[i]) == 0){
                break;
            }
            i--;
        }
        if(i==(-1)){
            System.out.println("not exist");
            return false;
        }
        //删除最后一个值
        if((this.keyNumber==1 && this.parent!=null && this.parent.parent==null) || this.keyNumber==1 && this.parent==null){
            return true;
        }
        //复制数组,完成删除
        Object tempKeys[] = new Object[BPlusTree.getmaxKeyNumber()];
        Object tempValues[] = new Object[BPlusTree.getmaxKeyNumber()];
        System.arraycopy(this.keys, 0, tempKeys, 0, i);
        System.arraycopy(this.values, 0, tempValues, 0, i);
        System.arraycopy(this.keys, i+1, tempKeys, i , this.keyNumber - i -1);
        System.arraycopy(this.values, i+1, tempValues, i , this.keyNumber - i -1);
        this.keyNumber--;
        int threshold=BPlusTree.getOrder()/2;
        if(this.keyNumber>=threshold || this.parent==null){//直接删除
            System.arraycopy(tempKeys, 0, this.keys, 0, this.keyNumber);
            System.arraycopy(tempValues, 0, this.values, 0, this.keyNumber);
            //判断父节点key值是否需要改变
            if(i==0){
                changeParentKey(this,oldKey);
//                System.out.println("true");
            }
//            System.out.println("case 0:  keynumber:"+this.keyNumber+"   oldkey: "+oldKey);
//            if(this.left!=null){
//                System.out.println("当前叶子节点左节点key为@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@:");
//                for(int z = 0; z < this.left.keyNumber; z++)
//                    System.out.print(this.left.keys[z] + " ");
//                System.out.println();
//            }
        }else if(this.keyNumber<threshold && this.right!=null && this.right.parent==this.parent && this.right.keyNumber>threshold){
            Object lendKey=this.right.keys[0];
            Object lendValue=this.right.values[0];
            for(int j=0;j<this.right.keyNumber-1;j++){
                this.right.keys[j]=this.right.keys[j+1];
                this.right.values[j]=this.right.values[j+1];
            }
            this.right.keyNumber--;
            //改变父节点的key值
            changeParentKey(this.right,(V)lendKey);
            System.arraycopy(tempKeys, 0, this.keys, 0, this.keyNumber);
            System.arraycopy(tempValues, 0, this.values, 0, this.keyNumber);
            this.keys[this.keyNumber]=lendKey;
            this.values[this.keyNumber]=lendValue;
            this.keyNumber++;
            //判断父节点key值是否需要改变
            if(i==0){
                changeParentKey(this,oldKey);
            }
//            System.out.println("case 1:  "+this.keyNumber);
        }else if(this.keyNumber<threshold && this.left!=null && this.left.parent==this.parent && this.left.keyNumber>threshold){
            Object lendKey=this.left.keys[this.left.keyNumber-1];
            Object lendValue=this.left.values[this.left.keyNumber-1];
            this.left.keys[this.left.keyNumber-1]=null;
            this.left.values[this.left.keyNumber-1]=null;
            this.left.keyNumber--;
            System.arraycopy(tempKeys, 0, this.keys, 1, this.keyNumber);
            System.arraycopy(tempValues, 0, this.values, 1, this.keyNumber);
            this.keys[0]=lendKey;
            this.values[0]=lendValue;
            this.keyNumber++;
            //改变父节点的key值
            changeParentKey(this,oldKey);
//            System.out.println("case 2:  "+this.keyNumber);
        }else if(this.keyNumber<threshold && this.left!=null && this.left.parent==this.parent && this.left.keyNumber<=threshold){//合并子节点
//            System.out.println("没合并前叶子节点key为:");
//            for(int z = 0; z < this.keyNumber; z++)
//                System.out.print(tempKeys[z] + " ");
//            System.out.println();
//            System.out.println("没合并前左叶子节点key为:");
//            for(int z = 0; z < this.left.keyNumber; z++)
//                System.out.print(this.left.keys[z] + " ");
//            System.out.println();
//            System.out.println("没合并前右叶子节点key为:");
//            for(int z = 0; z < this.right.keyNumber; z++)
//                System.out.print(this.right.keys[z] + " ");
//            System.out.println();
            System.arraycopy(tempKeys, 0, this.left.keys, this.left.keyNumber, this.keyNumber);
            System.arraycopy(tempValues, 0, this.left.values, this.left.keyNumber, this.keyNumber);
            this.left.keyNumber=this.left.keyNumber+this.keyNumber;
            if(this.parent!=null){
                //删除此节点对应的父节点的key和child
                int j = 0;
                while(j < this.parent.keyNumber){
                    if(oldKey.compareTo((V) this.parent.keys[j]) == 0){
                        break;
                    }
                    j++;
                }
                for(int x=j;x<this.parent.keyNumber-1;x++){
                    this.parent.keys[x]=this.parent.keys[x+1];
                    this.parent.childs[x]= this.parent.childs[x+1];
                }
                this.parent.keyNumber--;
                //判断父节点是否需要合并
//                System.out.println("当前叶子节点key为:");
//                for(int z = 0; z < this.left.keyNumber; z++)
//                    System.out.print(this.left.keys[z] + " ");
//                System.out.println();
//
//                System.out.println("叶子父节点key为:");
//                for(int z = 0; z < this.parent.keyNumber; z++)
//                    System.out.print(this.parent.keys[z] + " ");
//                System.out.println();
                NonLeafNode parentNode=(NonLeafNode) this.left.parent;
                parentNode.deleteNode();
            }
            this.left.right=this.right;
            if(this.right!=null){
                this.right.left=this.left;
            }
//            System.out.println("case 3:  "+this.keyNumber);
        }else if(this.keyNumber<threshold && this.right!=null && this.right.parent==this.parent && this.right.keyNumber<=threshold){
            System.arraycopy(this.right.keys, 0, tempKeys, this.keyNumber, this.right.keyNumber);
            System.arraycopy(this.right.values, 0, tempValues, this.keyNumber, this.right.keyNumber);
            System.arraycopy(tempKeys, 0, this.keys, 0, this.keyNumber+this.right.keyNumber);
            System.arraycopy(tempValues, 0, this.values, 0, this.keyNumber+this.right.keyNumber);
            //判断父节点key值是否需要改变
            this.keyNumber=this.right.keyNumber+this.keyNumber;
            if(i==0){
                changeParentKey(this,oldKey);
            }

            //删除右节点对应的父节点的key值和child
            if(this.parent!=null){
                int j = 0;
                while(j < this.parent.keyNumber){
                    if(((V)this.right.keys[0]).compareTo((V) this.parent.keys[j]) == 0){
                        break;
                    }
                    j++;
                }
                for(int x=j;x<this.parent.keyNumber-1;x++){
                    this.parent.keys[x]=this.parent.keys[x+1];
                    this.parent.childs[x]= this.parent.childs[x+1];
                }
                this.parent.keyNumber--;

//                System.out.println("在这之前父节点key为:");
//                for(int z = 0; z < this.parent.keyNumber; z++)
//                    System.out.print(this.parent.keys[z] + " ");
//                System.out.println();

                //判断父节点是否需要合并
                NonLeafNode parentNode=(NonLeafNode) this.parent;
                parentNode.deleteNode();
            }
            if(this.right.right!=null){
                this.right.right.left=this;
            }
            this.right=this.right.right;

//            System.out.println("case 4:  "+this.keyNumber);
//            if(this.left!=null){
//                System.out.println("当前叶子节点左节点key为@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@:");
//                for(int z = 0; z < this.left.keyNumber; z++)
//                    System.out.print(this.left.keys[z] + " ");
//                System.out.println();
//            }
        }
//        System.out.println("当前节点key为:");
//        for(int j = 0; j < this.keyNumber; j++)
//            System.out.print(this.keys[j] + " ");
//        System.out.println();
//
//        System.out.println("父节点key为:");
//        for(int j = 0; j < this.parent.keyNumber; j++)
//            System.out.print(this.parent.keys[j] + " ");
//        System.out.println();
        return false;
    }

    public void changeParentKey(Node node, V key){

        while (node.parent != null){
//            System.out.println("当前节点key为:");
//            for(int j = 0; j < node.keyNumber; j++)
//                System.out.print(node.keys[j] + " ");
//            System.out.println();
//
//            System.out.println("fu节点key为:");
//            for(int i = 0; i < node.parent.keyNumber; i++)
//                System.out.print(node.parent.keys[i] + " ");
//            System.out.println();
            int j = 0;
            while(j < node.parent.keyNumber){
                if(key.compareTo((V) node.parent.keys[j]) == 0){
                    break;
                }
                j++;
            }
            node.parent.keys[j]=node.keys[0];

//            System.out.println("改变后fu节点key为:");
//            for(int i = 0; i < node.parent.keyNumber; i++)
//                System.out.print(node.parent.keys[i] + " ");
//            System.out.println();

            if(j==0){
                node = node.parent;
            }
            else {
                break;
            }
        }
    }
    @Override
    LeafNode<T, V> refreshLeft() {
        if(this.keyNumber <= 0)
            return null;
        return this;
    }

}
