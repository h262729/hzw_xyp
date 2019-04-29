import Vue from 'vue';
import Vuex from 'vuex';
Vue.use(Vuex);

const state = { // 标签页面
  tabs : [],  // 标签组
  cachePages : [], // 缓存页面
};

const getters = {
  getTabs(state){
    return state.tabs;
  },
  getCachePages(state){
    return state.cachePages;
  }
}

const mutations = {
  pushTabs(state, item){  // 添加标签页
    state.tabs.push(item);
  },
  replaceTabs(state, items){  // 替换标签页
    state.tabs = items;
  },
  pushCachePages(state, item){  // 添加缓存页面
    state.cachePages.push(item);
  },
  replaceCachePages(state, items){  // 替换缓存页面
    state.cachePages = items;
  }
}

const actions = {
  pushTabs(context, item){
    context.commit('pushTabs',item);
  },
  replaceTabs(context, item){
    context.commit('replaceTabs',item);
  },
  pushCachePages(context, item){
    context.commit('pushCachePages',item);
  },
  replaceCachePages(context, item){
    context.commit('replaceCachePages',item);
  }
}

const store = new Vuex.Store({
  state,
  getters,
  mutations,
  actions
});

export default store;
