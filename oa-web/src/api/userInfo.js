import request from "@/utils/request";

export default {
  bindPhone(bindPhoneVO) {
    return request({
      url: `/admin/wechat/bindPhone`,
      method: "post",
      data: bindPhoneVO,
    });
  },

  getCurrentUser() {
    return request({
      url: `/admin/system/sysUser/getCurrentUser`,
      method: "get",
    });
  },
};
