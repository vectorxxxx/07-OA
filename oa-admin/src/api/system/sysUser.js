/*
用户管理相关的API请求函数
*/
import request from "@/utils/request";

const api_name = "/admin/system/sysUser";

export default {
  // 获取用户列表
  getPageList(page, limit, searchObj) {
    return request({
      url: `${api_name}/${page}/${limit}`,
      method: "get",
      params: searchObj,
    });
  },
  // 获取用户
  getById(id) {
    return request({
      url: `${api_name}/get/${id}`,
      method: "get",
    });
  },
  // 添加用户
  save(user) {
    return request({
      url: `${api_name}/save`,
      method: "post",
      data: user,
    });
  },
  // 修改用户
  updateById(user) {
    return request({
      url: `${api_name}/update`,
      method: "put",
      data: user,
    });
  },
  // 删除用户
  removeById(id) {
    return request({
      url: `${api_name}/remove/${id}`,
      method: "delete",
    });
  },
};
