<template>
    <div class="tree">
      <h-tree-node :nodes="nodes" :level="level || 0" :open="current_Open"></h-tree-node>
      <!--<div class="tree-node" >
        <template v-for="(node, index) in nodes">
          <div class="tree-node-content" :style="{paddingLeft : (15 * level) + 'px'}" @click="openNode(node, index)">
            <span>{{node.name}}</span>
          </div>
          &lt;!&ndash; node.children && node.children.length > 0 && &ndash;&gt;
          <div class="tree-child-node" v-if="node.children && node.children.length > 0 && current_Open">
            <h-tree :nodes="node.children" :level="(level || 0) + 1" :open="current_Open"></h-tree>
          </div>
        </template>
      </div>-->
    </div>
</template>

<script>
  import HTreeNode from '@/components/commons/h-tree-node';
  import HTree from '@/components/commons/h-tree';

  export default {
    name: "h-tree",
    data(){
      return {
        current_Open : true,
        current_nodes : this.nodes,
      }
    },
    created(){
      this.current_nodes = this.nodes || [];
      console.log("节点", this.current_nodes);
    },
    methods:{
      openNode(node, index){
        console.log("节点展开", node.open);
        this.current_Open = !this.current_Open;
        this.current_nodes[index].open = !this.current_nodes[index].open;
      }
    },
    components:{HTreeNode, HTree},
    props: {
      nodes : Array,
      level : Number,
      open : Boolean,
    }
  }
</script>
<style scoped>
  .tree{
    margin: 10px auto;
    border-top: 1px solid #c0c0c0;
  }
</style>
