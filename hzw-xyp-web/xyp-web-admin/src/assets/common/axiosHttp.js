// axios全局配置
import axios from "axios";
import qs from "qs";
// 导入全局常量
import global_ from "@/assets/common/constant.js";

axios.defaults.timeout = 6000;
axios.defaults.baseURL = global_.BASE_REQUEST_URL;
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
axios.defaults.withCredentials = true;
//;application/json; charset=UTF-8
//application/x-www-form-urlencoded

// request拦截器
axios.interceptors.request.use(
  config => {
    config.data = qs.stringify({data: JSON.stringify(config.data)});
    console.log("请求数据", config.data);
    return config;
  },
  err => {
    return Promise.reject(err);
  }
);

// response拦截器
axios.interceptors.response.use(
  config => {
    console.log("响应成功", config && config.data);
    return config && config.data;
  },
  error => {
    var errorData = {message : "未知错误！"};
    if(error && error.response){
      errorData = error.response.data || errorData;
    }
    console.log("响应失败", errorData);
    return Promise.reject(errorData);
  }
);

export default axios;
