// 这里存放公共的函数
import ElementUI from 'element-ui';

/**
 * 消息提醒 -- 对ElementUI的消息提醒组件的二次封装
 * @param msg 提醒内容
 * @param type  消息类型 success/warning/info/error
 */
const message = (msg, type) => {
  ElementUI.Message({
    showClose: true,  // 是否显示关闭按钮
    message: msg || "消息提醒", // 内容
    type: type || 'info', // 提醒类型
    duration: 2000 // 停留时间
  });
}
const success = (msg) => {
  message(msg, 'success');
}
const error = (msg) => {
  console.log("错误提醒！" ,msg);
  message(msg, 'error');
}
const info = (msg) => {
  message(msg, 'info');
}
const warning = (msg) => {
  message(msg, 'warning');
}

/**
 * 缓存路由页面表单数据
 * cacheRouteData 缓存
 * getRouteData 获取
 */
const cacheRouteData = (_route, beanData) => { // 缓存当前表单数据
  let sessionName = _route.meta.code;
  let key = sessionName;
  if(_route.query.id){
    key = sessionName + ":" + (_route.query.id || -1);
  }
  // 要缓存的数据, 不为空对象的时候
  // console.log("缓存>" + key, JSON.stringify(beanData));
  window.sessionStorage.setItem(key, JSON.stringify(beanData));
}

const getRouteData = (_route) => { // 获取缓存的表单数据
  let sessionName = _route.meta.code;
  let key = sessionName;
  if(_route.query.id){
    key = sessionName + ":" + (_route.query.id || -1);
  }
  let beanData = window.sessionStorage.getItem(key);
  beanData = JSON.parse( beanData);
  // console.log("获取>" + key, JSON.stringify(beanData));
  return beanData;
}

const clearRouteData = (tab) => { // 清除表单缓存
  window.sessionStorage.removeItem(tab.code);
}


export default {
  success,
  error,
  info,
  warning,

  cacheRouteData,
  getRouteData,
  clearRouteData
}
