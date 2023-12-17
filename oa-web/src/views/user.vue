<template>
  <div class="container">
    <el-button
      type="danger"
      icon="el-icon-delete"
      size="mini"
      @click="clearToken()"
      title="清除token"
      >清除token</el-button
    >
    <van-nav-bar title="基本信息" />
    <div class="detail-wrap">
      <div class="item">
        <h5>用户姓名：{{ user.name }}</h5>
      </div>
      <div class="item">
        <h5>手机号：{{ user.phone }}</h5>
      </div>
      <div class="item">
        <h5>所在部门：{{ user.deptName }}</h5>
      </div>
      <div class="item">
        <h5>岗位：{{ user.postName }}</h5>
      </div>
    </div>
    <el-button
      type="danger"
      icon="el-icon-delete"
      size="mini"
      @click="clearCookie()"
      title="清除cookie"
      >清除cookie</el-button
    >
  </div>
</template>

<script>
import api from "@/api/userInfo";
export default {
  name: "process",

  data() {
    return {
      user: {},
    };
  },

  created() {
    this.fetchData();
  },

  methods: {
    fetchData() {
      // debugger
      api.getCurrentUser().then((response) => {
        this.user = response.data;
      });
    },

    clearCookie() {
      window.localStorage.setItem("token", "");
      this.$message.success("cookie清除成功");
      location.reload();
    },

    clearToken(paramName) {},
  },
};
</script>

<style lang="scss" scoped>
.container {
  padding: 20px;

  .detail-wrap {
    .item {
      h5 {
        color: #838485;
        margin: 10px;
      }

      p {
        color: #1b1f22;
        margin: 0;
      }
    }
  }
}
</style>
