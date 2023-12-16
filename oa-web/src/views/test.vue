<template>
  <div>
    <div>账号切换</div>
    <button @click="wjl()" type="default" size="mini">admin</button>
    <button @click="lrsjl()" type="default" size="mini">zhangsan</button>
    <button @click="zzjl()" type="default" size="mini">lisi</button>
    <button @click="lisi()" type="default" size="mini">test3</button>
    <button @click="test4()" type="default" size="mini">test4</button>
    <button @click="test5()" type="default" size="mini">zhangsan01</button>
    <div>当前token：{{ token }}</div>
  </div>
</template>

<script>
export default {
  name: "Test",

  data() {
    return {
      token: "",
    };
  },

  created() {
    this.token = window.localStorage.getItem("token");
  },

  methods: {
    wjl() {
      window.localStorage.setItem("token", "");
      let token =
        "eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWKi5NUrJScgwN8dANDXYNUtJRSq0oULIyNDc2MTI3NLQw0lEqLU4t8kwBihlD2HmJualAPYkpuZl5SrUA4Z3SC0MAAAA.1g3_K46lHQR5MPoE4nMTzk1Pb2phOk5-Gihg2JdpqFgmpEgCZa9aTHU5UvgWO09H-yxjfP_kgs4yYb17m4qX6Q";
      window.localStorage.setItem("token", token);
      this.token = window.localStorage.getItem("token");
    },

    lrsjl() {
      window.localStorage.setItem("token", "");
      let token =
        "eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWKi5NUrJScgwN8dANDXYNUtJRSq0oULIyNDc2MTI3srAw01EqLU4t8kwBiplA2HmJualAPVUZiXnpxYl5SrUAmwDnQ0YAAAA.zo39oluWS7MMAL3pJO9rLhQ6yjN8WV-M5NOckBFemxTedmVDTTIJjvWanA4YTukixOayKY1nILYB1kO_d-ZuMA";
      window.localStorage.setItem("token", token);
      this.token = window.localStorage.getItem("token");
    },

    zzjl() {
      window.localStorage.setItem("token", "");
      let token =
        "eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWKi5NUrJScgwN8dANDXYNUtJRSq0oULIyNDc2MTI3srAw01EqLU4t8kwBiplC2HmJualAPTmZxZlKtQAabGPwQgAAAA.LKzXcgaguwgqDjfq8w65HZzodw_Y3HkbQMznc-kre8JE81vpFCtIN5_BEyryO_AG1Y2cMpnJbxKtHHyUCxSY0A";
      window.localStorage.setItem("token", token);
      this.token = window.localStorage.getItem("token");
    },

    lisi() {
      window.localStorage.setItem("token", "");
      let token =
        "eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWKi5NUrJScgwN8dANDXYNUtJRSq0oULIyNDc2MTI3NLQw1lEqLU4t8kwBiplB2HmJualAPSWpxSXGSrUAufF-V0MAAAA.oojoy2ul9gCeuwRLxSTAQDN6mQwA4OBH3eDBbkEsvDabxoN2opUGJgt0uMM1ixLQQjlXb6mZmwgZB9sISlKoUQ";
      window.localStorage.setItem("token", token);
      this.token = window.localStorage.getItem("token");
    },

    test4() {
      window.localStorage.setItem("token", "");
      let token =
        "eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWKi5NUrJScgwN8dANDXYNUtJRSq0oULIyNDc2MTI3NLQw1lEqLU4t8kwBiUHYeYm5qUA9JanFJSZKtQCiZOvNQwAAAA.8TVInMcR_zyrStE0HfOcS1ItcwDHj_DzcONt0huT89Ewai4_BUlZ7KKESbJcIIlnsKQkcn9qLdc-e7vnvSsxLg";
      window.localStorage.setItem("token", token);
      this.token = window.localStorage.getItem("token");
    },

    test5() {
      window.localStorage.setItem("token", "");
      let token =
        "eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWKi5NUrJScgwN8dANDXYNUtJRSq0oULIyNDc2MTI3NLQw1lEqLU4t8kwBillA2HmJualAPVUZiXnpxYl5BoZKtQAJvQFGSAAAAA.Hx2DbJ2wW-WZ7el3QB5UNfsr73t4IlpK9mi8DGFIUk0nCqBCDdfdomch74vpl_DDBdyCq16O-gQRfRd1Sumsgg";
      window.localStorage.setItem("token", token);
      this.token = window.localStorage.getItem("token");
    },
  },
};
</script>
