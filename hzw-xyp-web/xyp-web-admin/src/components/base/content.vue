<template>
    <div id="content">
      <el-tabs v-model="activity_tab" type="card" @tab-click="clickTab" @tab-remove="removeTab">
        <el-tab-pane label="首页"></el-tab-pane>
        <el-tab-pane
          v-for="(item, index) in tabs"
          :key="item.code"
          :label="item.title"
          :name="item.code"
          closable
        >
        </el-tab-pane>
      </el-tabs>
      <router-view name="adminHome"></router-view>
    </div>
</template>

<script>
    export default {
      data() {
        return {
          activity_tab: '1',
          tabs: [{
            title: 'Tab 1',
            code: '1',
            content: 'Tab 1 content'
          }, {
            title: 'Tab 2',
            code: '2',
            content: 'Tab 2 content'
          }]
        }
      },
      methods: {
        addTab(targetName) {
          let newTabName = ++this.tabIndex + '';
          this.editableTabs2.push({
            title: 'New Tab',
            name: newTabName,
            content: 'New Tab content'
          });
          this.editableTabsValue2 = newTabName;
        },
        removeTab(targetName) {
          let tabs = this.editableTabs2;
          let activeName = this.editableTabsValue2;
          if (activeName === targetName) {
            tabs.forEach((tab, index) => {
              if (tab.name === targetName) {
                let nextTab = tabs[index + 1] || tabs[index - 1];
                if (nextTab) {
                  activeName = nextTab.name;
                }
              }
            });
          }

          this.editableTabsValue2 = activeName;
          this.editableTabs2 = tabs.filter(tab => tab.name !== targetName);
        },
        clickTab(tab){ // 点击标签
          console.log("被选中的标签", tab);
          //this.$router.push("/admin/home");

        }
      }
    }
</script>

<style scoped>

</style>
