<template>

<div class="row">
  <div class="col-md-12">
    <div class="card text-white bg-info mb-3">
    <div class="card-header"><h1>KT Cloud VM 자동점검</h1></div>         
    <p></p>
    </div>
  </div>
  <div class="col-sm-12">
      <p></p>
    <b-form-radio-group
        id="btn-radios-2"
        v-model="selected"
        :options="data.servers"
        value-field="name"
        text-field="name"
        :aria-describedby="ariaDescribedby"
        button-variant="outline-primary"
        size="lg"
        name="radio-btn-outline"
        buttons
      ></b-form-radio-group>
    </b-form-group>

  </div>
    <div class="col-sm-12">
        <div class="card" style="text-align:center;">
            <div class="card-body">
                <h2 class="card-title" v-if="selected && message == null">VM의 연결을 체크합니다.</h2>
                <p class="card-text">
                    <h3 v-if="selected && message == null && temp == null">VM의 포트포워딩과 방화벽 정책을 검사합니다.</h3>
                    <h3 v-if="selected && message == null && temp == null">잠시만 기다려주세요. {{selectaler(selected)}} </h3>
                    <h3 v-if="selected && message == null && temp == 1">VM의 포트포워딩과 방화벽 정책을 자동으로 생성합니다.</h3>
                    <vue-simple-spinner v-if="selected && message == null && temp == null || temp == 1" size="large" />
                    <h3 v-if="message">
                        <h3>검사가 완료 되었습니다.</h3>
                        <div v-if="this.message.includes(this.una)">
                            <h3>포트포워딩 및 방화벽 정책에 문제가 발생하였습니다.</h3>
                            <b-button v-if="temp == null" v-on:click="portfire()">포트포워딩 및 방화벽 정책 자동생성하기</b-button>
                        </div>
                        <div v-else>
                            <h3>네트워크 연결을 확인했습니다.</h3>
                            <hr>
                            <h5>아이피: {{this.message.substring(8,22)}}</h5>
                            <h5>포트: {{this.message.substring(23,28)}}</h5>
                            <h5>프로토콜: {{this.message.substring(29,32)}}</h5>
                            <h5>{{this.message.substring(34,48)}} !! </h5>
                            <hr>
                            <p>{{this.message}}</p>
                        </div>
                    </h3>
                </p>
            </div>
        </div>
    </div>
  <!--
  <div class="col-sm-4" v-for="item in data.servers">
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
  -->
 </div>
</template>
<script>
import VueSimpleSpinner from 'vue-simple-spinner'

import axios from "axios";

export default {
  components: {
      VueSimpleSpinner,
  },
  data() {
    return {
        una: 'unable',
      data: null,
      data2: null,
      data3: null,
      startport: "",
      ipaddress: "",
      protocol: "",
      cidrlist: "",
      ipaddressid: "",
      networkid: "",
      id: "",
      state: "",
      endport: "",
      publicport: null,
      ip: null,
      selected: null,
      message:null,
      temp: null,
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
    pingck() {
        const frm = new FormData() 
        frm.append('publicport', this.publicport)
        frm.append('ip', this.ip)
        axios.post('http://localhost:8080/pingck', frm)
        .then(response => {
          console.log(response.data)
          this.message = response.data
          console.log(this.message)
          })
        .catch((error) => {
        })
    },

    selectaler(nodeData){
        console.log(nodeData);
        console.log("node데이터입니다 !");
      var tt = nodeData;
      var i;
      for(i=0;i<this.data3.length;i++){
        if(this.data3[i].id == tt){
          var ip = this.data3[i].ip;
          console.log(this.data3[i].ip);
          var i2;
          for(i2=0;i2<this.data2.nc_listentpublicipsresponse.publicips.length; i2++){
            var i3;
            for(i3=0;i3<this.data2.nc_listentpublicipsresponse.publicips[i2].virtualips.length;i3++){
              if(ip == this.data2.nc_listentpublicipsresponse.publicips[i2].virtualips[i3].vmguestip){
                console.log(this.data2.nc_listentpublicipsresponse.publicips[i2].virtualips[i3].ipaddress);
                console.log(this.data2.nc_listentpublicipsresponse.publicips[i2].virtualips[i3].publicendport);

                  this.ip = this.data2.nc_listentpublicipsresponse.publicips[i2].virtualips[i3].ipaddress;
                  this.publicport = this.data2.nc_listentpublicipsresponse.publicips[i2].virtualips[i3].publicendport;
              }
            }
          }
        }
      }
      return this.pingck();
    },

    ktip() {
       axios
        .get("http://localhost:8080/ktip")
        .then(res => {
          this.data2 = res.data;
        })
        .catch(err => {
          console.log(err);
        });
    },
    tier() {
      axios
        .get("http://localhost:8080/ktd1tier")
        .then(res => {
          this.data3 = res.data;
        })
        .catch(err => {
          console.log(err);
        });
    },
    portfire() {
        this.message = null;
        this.temp = 1
        const frm = new FormData() 
        frm.append('sname', this.selected)
        axios.post('http://localhost:8080/portfire', frm)
        .then(response => {
          console.log(response.data)
          this.temp = 2
          this.message = response.data
          console.log(this.message)
          })
        .catch((error) => {
        })
    },
  },
  created() {
    this.fetchData();
  },
  mounted() {
    this.tier();
    this.ktip();
  },
};
</script>
<style>
</style>
