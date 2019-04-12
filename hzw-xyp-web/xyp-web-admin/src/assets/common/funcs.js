// 这里存放公共的函数
import ElementUI from 'element-ui';

/**
 * 消息提醒 -- 对ElementUI的消息提醒组件的二次封装
 * @param msg 提醒内容
 * @param type  消息类型 success/warning/info/error
 */
function message(msg, type) {
  ElementUI.Message({
    showClose: true,  // 是否显示关闭按钮
    message: msg || "消息提醒", // 内容
    type: type || 'info', // 提醒类型
    duration: 2000 // 停留时间
  });
}
function success(msg) {
  message(msg, 'success');
}
function error(msg) {
  console.log("错误提醒！" ,msg);
  message(msg, 'error');
}
function info(msg) {
  message(msg, 'info');
}
function warning(msg) {
  message(msg, 'warning');
}

export default {
  success,
  error,
  info,
  warning
}
