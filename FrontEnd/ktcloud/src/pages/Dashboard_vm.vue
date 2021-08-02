<template>

<div class="row">
  <div class="col-md-12">
    <div class="card text-white bg-info mb-3">
    <div class="card-header"><h1>KT Cloud VM 서버 모니터링을 제공합니다</h1></div>         
    <p></p>
    </div>
  </div>
  <div class="col-sm-3" v-for="item in data.servers">
    <div class="card" style="text-align:center;">
      <div class="card-body">
        <h2 class="card-title">{{item.name}}</h2>  
        <p>
        <p class="card-text">
        </p>
        <b-button pill variant="info" @click="onClickRedirect()">대시보드로 이동</b-button>
      </div>
    </div>
  </div>
 </div>
</template>
<script>
import { PaperTable } from "@/components";
import axios from "axios";

export default {
  components: {
  },
  data() {
    return {
      data: null,
      startport: "",
      ipaddress: "",
      protocol: "",
      cidrlist: "",
      ipaddressid: "",
      networkid: "",
      id: "",
      state: "",
      endport: "",
    };
  },
  methods: {
    fetchData() {
      axios
        .get("http://localhost:8080/ktd1serverlist")
        .then(res => {
          console.log(res);
          this.data = res.data;
        })
        .catch(err => {
          console.log(err);
        });
    },
    onClickRedirect(){
      window.open("https://jsman9777.grafana.net/d/a3k8UWG7k/node-exporter-full?orgId=1&refresh=1m", "_blank");
    },
  },
  created() {
    this.fetchData();
  }
};
</script>
<style>
</style>
