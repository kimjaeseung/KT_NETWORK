<template>
  <div id="app">
    <h1>{{jsonadd()}}</h1>
    <h1>VM정보를 나타냅니다</h1>
      <org-chart :datasource="ds2" @node-click="selectNo"></org-chart>
  </div>
</template>

<script>
import axios from "axios";
import OrgChart from '../components/OrganizationChartContainer.vue'

export default {
  name: 'app',
  components: {
    OrgChart,
  },
  data () {
    return {
      ds2: null,
      ds3: null,
      data: null,
      data2: null,
      data3: null,
      data5: null,
      data6: null,
      serverlist:'',
      text: '',
    }
  },
  methods: {
    selectNode (nodeData) {
      alert('node ' + nodeData.title + ' is selected')
    },
    selectNo (nodeData) {
        this.$alert(
      "정상 작동중",
      this.selectaler(nodeData.title),
      //selectaler(nodeData),
      "success"
    ).then(() => console.log("Closed"));
    },
    selectaler(nodeData){
      var tt = nodeData;
      var result;
      var i;
      for(i=0;i<this.data3.length;i++){
        if(this.data3[i].id == tt){
          var ip = this.data3[i].ip;
          var i2;
          for(i2=0;i2<this.data5.nc_listentpublicipsresponse.publicips.length; i2++){
            var i3;
            for(i3=0;i3<this.data5.nc_listentpublicipsresponse.publicips[i2].virtualips.length;i3++){
              if(ip == this.data5.nc_listentpublicipsresponse.publicips[i2].virtualips[i3].vmguestip){
                result = "서버: "+tt+" 포트포워딩\n" + 
                "\nipaddress: " + this.data5.nc_listentpublicipsresponse.publicips[i2].virtualips[i3].ipaddress
                + "\n사설포트: "+this.data5.nc_listentpublicipsresponse.publicips[i2].virtualips[i3].privateport + 
                "\nprotocol: "+this.data5.nc_listentpublicipsresponse.publicips[i2].virtualips[i3].protocol +
                "\npublicendport: "
                + this.data5.nc_listentpublicipsresponse.publicips[i2].virtualips[i3].publicendport;
              }
            }
          }
          
        }
      }
      return result;
    },
    serverList() {
      axios
        .get("http://localhost:8080/ktd1serverlist")
        .then(res => {
          this.data = res.data;
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
    jsonadd(){
      var jobj = new Object();
      jobj.id = "1";
      jobj.name = "위치";
      jobj.title = "DX-M1";
      
      var test2 = [];
      var jobj3 = new Object();
      jobj3.id = "2";
      jobj3.name = "Tier";
      jobj3.title = "DMZ";
  
      var jobj4 = new Object();
      jobj4.id = "3";
      jobj4.name = "Tier";
      jobj4.title = "Private";

      test2.push(jobj3);
      test2.push(jobj4);
      var i;
      var test = [];
      var test3 = [];
      
      for (i = 0; i < this.data.servers.length; i++) {
        if(this.data3[i].tier =="DMZ"){
          var jobj2 = new Object();
          jobj2.id = i+1;
          jobj2.name = "서버이름";
          jobj2.title = this.data3[i].id;
          test.push(jobj2);
        }
        else{
          var jobj2 = new Object();
          jobj2.id = i+1;   
          jobj2.name = "서버이름";
          jobj2.title = this.data3[i].id;
          test3.push(jobj2);
        }
      }
      jobj.children = test2
      jobj4.children = test3
      jobj3.children = test
      

      this.ds2 = jobj
    },

    ktip() {
       axios
        .get("http://localhost:8080/ktip")
        .then(res => {
          this.data5 = res.data;
        })
        .catch(err => {
          console.log(err);
        });
    },
    ktfirewall() {
       axios
        .get("http://localhost:8080/ktd1firewall")
        .then(res => {
          this.data6 = res.data;
        })
        .catch(err => {
          console.log(err);
        });
    },


  },

  created() {
    this.serverList();
  },
  mounted(){
    this.tier();
    this.ktip();
    this.ktfirewall();
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>