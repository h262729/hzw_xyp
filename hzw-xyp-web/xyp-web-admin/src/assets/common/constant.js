// 这里存放着全局常量
// 注意：为了区分于局部变量，这里的常量命名采用 H_ 开头，后面全部大写

// const BASE_URL = "http://127.0.0.1:8300/";
const BASE_URL = "http://120.78.184.188:8300/";
const BASE_REQUEST_URL = BASE_URL + "api/xyp-admin/";

const BASE_IMAGE_UPLOAD_URL = BASE_URL + "api/base/image/upload"; // 图片上传前缀
const BASE_IMAGE_GET_URL = BASE_URL + "images/"; // 图片获取前缀
export default {
  BASE_URL,
  BASE_REQUEST_URL,
  BASE_IMAGE_UPLOAD_URL,
  BASE_IMAGE_GET_URL
}
