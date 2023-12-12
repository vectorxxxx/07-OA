<template>
  <div class="app-container">
    <div style="padding: 20px 20px 0 20px">
      授权角色：{{ $route.query.roleName }}
    </div>
    <el-tree
      style="margin: 20px 0"
      ref="tree"
      :data="sysMenuList"
      node-key="id"
      show-checkbox
      default-expand-all
      :props="defaultProps"
    />
    <div style="padding: 20px 20px">
      <el-button
        :loading="loading"
        type="primary"
        icon="el-icon-check"
        size="mini"
        @click="save"
        >保存</el-button
      >
      <el-button
        @click="$router.push('/system/sysRole')"
        size="mini"
        icon="el-icon-refresh-right"
        >返回</el-button
      >
    </div>
  </div>
</template>
<script>
import api from "@/api/system/sysMenu";

export default {
  name: "roleAuth",

  data() {
    return {
      loading: false,
      sysMenuList: [],
      defaultProps: {
        children: "children",
        label: "name",
      },
    };
  },

  created() {
    this.fetchData()
  },

  methods: {
    fetchData() {
        const roleId = this.$route.query.id;
        api.toAssign(roleId).then(result => {
            const sysMenuList = result.data
            this.sysMenuList = sysMenuList
            const checkedIds = this.getCheckedIds(sysMenuList)
            this.$refs.tree.setCheckedKeys(checkedIds)
        })
    },
    getCheckedIds(auths, initArr = []){
        return auths.reduce((pre, item) => {
          if(item.select && item.children.length === 0) {
            pre.push(item.id)
          } else if(item.children){
            this.getCheckedIds(item.children, initArr)
          }
          return pre
        }, initArr)
    },
    save() {
      const allCheckedNodes = this.$refs.tree.getCheckedNodes(false, true)
      let idList = allCheckedNodes.map(item => item.id)
      let assignMenuVO = {
        roleId: this.$route.query.id,
        menuIdList: idList
      }
      api.doAssign(assignMenuVO).then(response => {
        this.loading = false
        this.$message.success(response.$message || '分配成功')
        this.$router.push('/system/sysRole')
      })
    }
  }
};
</script>
