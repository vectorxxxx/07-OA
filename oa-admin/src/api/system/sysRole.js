/*
角色管理相关的API请求函数
*/
import request from "@/utils/request";

const api_name = "/admin/system/sysRole";

export default {
  /*
  获取角色分页列表(带搜索)
  */
  getPageList(page, limit, searchObj) {
    return request({
      url: `${api_name}/${page}/${limit}`,
      method: "get",
      params: searchObj,
    });
  },
  /* 
  删除角色
   */
  removeById(id) {
    return request({
      url: `${api_name}/remove/${id}`,
      method: "delete",
    });
  },
  /* 
  添加角色 
  */
  save(role) {
    return request({
      url: `${api_name}/save`,
      method: "post",
      data: role,
    });
  },
  /* 
  修改角色 
  */
  getById(id) {
    return request({
      url: `${api_name}/get/${id}`,
      method: "get",
    });
  },
  updateById(role) {
    return request({
      url: `${api_name}/update`,
      method: "put",
      data: role,
    });
  },
  /* 
  批量删除
  */
  batchRemove(ids) {
    return request({
      url: `${api_name}/batchRemove`,
      method: "delete",
      data: ids,
    });
  },
};
