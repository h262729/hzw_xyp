<template>
  <div class="tree-node">
    <!-- v-if="level == 0 || open" -->
    <div >
      <template v-for="(node, index) in current_nodes" >
        <!-- @click="openNode(node, index)" -->
        <div class="tree-node-content" :style="{paddingLeft : (15 * level) + 'px'}" @click="node.open = !open">
          <span>{{node.name}}</span>
        </div>
        <div class="tree-child-node" v-if="node.children && node.children.length > 0 && node.open" >
          <h-tree-node :nodes="node.children" :level="level + 1" :open="node.open"></h-tree-node>
        </div>
      </template>
    </div>
  </div>
</template>

<script>
  import HTreeNode from '@/components/commons/h-tree-node';

  export default {
    name: "h-tree-node",
    data(){
      return {
        current_Open : this.open,
        current_nodes : this.nodes,
      }
    },
    created(){
      this.current_nodes = this.nodes || [];
      console.log("节点", this.current_nodes);
    },
    methods:{
      openNode(node, index){
        console.log("节点展开", this.current_Open);
        this.current_Open = !this.current_Open;
        this.current_nodes[index].open = !this.current_nodes[index].open;
      },

    },
    computed : {
      getOpen : function(index){
        console.log("获取节点展开", index);
        if(this.current_nodes[index].open === null){
          this.current_nodes[index].open = true;
        }
        return this.current_nodes[index].open;
      }
    },
    components:{HTreeNode},
    props: {
      nodes : Array,
      level : {type: Number, default : 0},
      open : {type: Boolean, default : true},
    }
  }
</script>

<style scoped>
  .tree-node-content {
    border-bottom: 1px solid #c0c0c0;
    cursor: pointer;
    line-height: 30px;
    font-size: 16px;
  }
  .tree-node-content:hover{
    background: aliceblue;
  }
</style>
