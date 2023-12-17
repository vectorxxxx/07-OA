import axios from "axios";

// 创建axios实例
const service = axios.create({
  baseURL: "http://funnymudpee.natapp1.cc", // api 的 base_url
  timeout: 30000, // 请求超时时间
});

// http request 拦截器
service.interceptors.request.use(
  (config) => {
    let token = window.localStorage.getItem("token") || "";
    if (token != "") {
      config.headers["token"] = token;
    }
    return config;
  },
  (err) => {
    return Promise.reject(err);
  }
);
// http response 拦截器
service.interceptors.response.use(
  (response) => {
    // 403, "没有权限"：需要先认证
    if (response.data.code == 403) {
      // debugger;
      // 替换# 后台获取不到#后面的参数
      let url = window.location.href.replace("#", "vectorx");
      window.location =
        "http://funnymudpee.natapp1.cc/admin/wechat/authorize?returnUrl=" + url;
    } else {
      if (response.data.code == 200) {
        return response.data;
      } else {
        if (response.data.code != 401) {
          alert(response.data.message || "error");
        }
        // 401, "认证失败"，拒绝访问
        return Promise.reject(response);
      }
    }
  },
  (error) => {
    return Promise.reject(error.response); // 返回接口返回的错误信息
  }
);

export default service;
