<template>
  <div id="app">
    <router-view />

    <el-dialog title="绑定手机" :visible.sync="dialogVisible" width="80%">
      <el-form ref="dataForm" :model="bindPhoneVO" size="small">
        <h4>绑定你的手机号，建立云尚办公系统关联关系</h4>
        <el-form-item label="手机号码">
          <el-input v-model="bindPhoneVO.phone" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button
          type="primary"
          icon="el-icon-check"
          @click="saveBind()"
          size="small"
          >确 定</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>
<script>
import userInfoApi from "@/api/userInfo";
export default {
  data() {
    return {
      show: true,
      dialogVisible: false,
      bindPhoneVO: {
        openId: "",
        phone: "",
      },
    };
  },

  created() {
    // TODO 处理微信授权登录
    this.wechatLogin();
  },

  methods: {
    wechatLogin() {
      // 处理微信授权登录
      let token = this.getQueryString("token") || "";
      let openId = this.getQueryString("openId") || "";
      // token === '' && openId != '' 只要这种情况，未绑定账号
      if (!token && !!openId) {
        // 绑定账号
        this.bindPhoneVO.openId = openId;
        this.dialogVisible = true;
      } else {
        // 如果绑定了，授权登录直接返回token
        token = window.localStorage.getItem("token") || "";
        if (!!token) {
          window.localStorage.removeItem("token");
          window.localStorage.setItem("token", token);
        } else {
          let url = window.location.href.replace("#", "vectorx");
          window.location =
            "http://funnymudpee.natapp1.cc/admin/wechat/authorize?returnUrl=" +
            url;
        }
      }
    },

    saveBind() {
      if (this.bindPhoneVO.phone.length != 11) {
        alert("手机号码格式不正确");
        return;
      }

      userInfoApi.bindPhone(this.bindPhoneVO).then((response) => {
        window.localStorage.setItem("token", response.data);
        this.dialogVisible = false;
        window.location = "http://funnyboy.nat300.top";
      });
    },

    getQueryString(paramName) {
      if (window.location.href.indexOf("?") == -1) return "";

      let searchString = window.location.href.split("?")[1];
      let i,
        val,
        params = searchString.split("&");

      for (i = 0; i < params.length; i++) {
        val = params[i].split("=");
        if (val[0] == paramName) {
          return val[1];
        }
      }
      return "";
    },
  },
};
</script>
<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
</style>
