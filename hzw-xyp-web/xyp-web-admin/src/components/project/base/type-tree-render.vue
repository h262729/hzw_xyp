<template>
  <span class="custom-tree-node">
    <span><i class="el-icon-loading" v-show="DATA.selected">222</i>{{ DATA.name }}</span>
    <span>
      <el-button type="text" size="mini" @click.stop="selectedNode(STORE,DATA,NODE)">
        选中测试
      </el-button>
      <!--<el-button type="text" size="mini" v-if="node.level < 3" @click.stop="createNextNode(STORE,DATA,NODE)">
        添加下级
      </el-button>
      <el-button type="text" size="mini" @click.stop="createSomeNode(STORE,DATA,NODE)">
        添加同级
      </el-button>
      <el-button type="text" size="mini" @click.stop="removeNode(STORE,DATA,NODE)">
        删除
      </el-button>-->
    </span>
  </span>
</template>

<script>
  export default {
    name: "type-tree-render",

    props: ['NODE', 'DATA', 'STORE'],
    methods: {
      selectedNode(s,d,n){//新增
        this.$emit('selectedNode',s,d,n)
      },
      /*nodeEdit(s,d,n){//编辑
        this.$emit('nodeEdit',s,d,n)
      },
      nodeDel(s,d,n){//删除
        this.$emit('nodeDel',s,d,n)
      }*/
    }
  }
</script>

<style scoped>

</style>
