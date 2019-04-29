// 表单验证 公共

const userName = (rule, value, callback) => { // 验证用户名
  if (/^[0-9a-zA-Z]{1,20}$/.test(value) == false) {
    callback(
      new Error("用户名可包含1-20位数字、字母（大小写）!")
    );
  } else {
    callback();
  }
};

// 验证密码
const pwd = (rule, value, callback) => {
  if (/^(?=.*[a-zA-Z])(?=.*\d)[0-9a-zA-Z]{6,20}$/.test(value) == false) {
    callback(new Error("请输入6-20位密码，必须包含字母和数字"));
  } else {
    callback();
  }
};

// 验证手机号
const mobile = (rule, value, callback) => {
  if (value && /^1[34578]{1}\d{9}$/.test(value) == false) {
    callback(new Error("请输入正确的手机号"));
  } else {
    callback();
  }
};

// 验证邮箱
const email = (rule, value, callback) => {
  if (value && /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/.test(value) == false) {
    callback(new Error("请输入正确的邮箱格式"));
  } else {
    callback();
  }
};

export default {
  userName,
  pwd,
  mobile,
  email
}

