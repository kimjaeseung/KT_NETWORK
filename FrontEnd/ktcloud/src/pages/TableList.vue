<template>
 <div class="row">
  <div class="col-md-12">
    <card>
      <template slot="header">
        <h4 class="card-title">kt cloud firewall data</h4>
      </template>
      <div class="content">
          <h1>test</h1>
          <div>
            
          </div>
          <h1>{{data}}</h1>
          
      </div>
    </card>
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
        .get("http://localhost:8080/ktip")
        .then(res => {
          console.log(res);
          this.data = res.data;
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  created() {
    this.fetchData();
  }
};
</script>
<style>
</style>
