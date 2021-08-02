<template>
  <div id="app">
    <h1>{{jsonadd()}}</h1>
    <h1>접속 설정과 방화벽 정보를 제공합니다.</h1>
    <!--
    <b-form-select v-model="selected" :options="options" :select-size="4"></b-form-select>
    <div class="mt-3">Selected: <strong>{{ selected }}</strong></div>
    <div v-if="selected == 'ZONE'">
      <org-chart :datasource="ds2" @node-click="selectNode"></org-chart>
    </div>
    <div v-else-if="selected == '포트포워딩'">
      <org-chart :datasource="ds3" @node-click="selectNode"></org-chart>
    </div>
    <div v-else-if="selected == 'c'">
      <h1>test</h1>
    </div>
    -->
    <org-chart :datasource="ds3" @node-click="selectNode"></org-chart>

  </div>
</template>

<script>
import axios from "axios";
import OrgChart from '../components/OrganizationChartContainer.vue'
export default {
  name: 'app',
  components: {
    OrgChart
  },
  data () {
    return {
      ds: { 
            'id': '1', 'name': '내컴퓨터', 'title': '내컴퓨터' , 
                'children': [
                    { 'id': '2', 'name': '포트포워딩', 'title': 'data.servers[0].name' },
                    { 'id': '2', 'name': '포트포워딩', 'title': '공인IP : 211.34.247.18 / 사설Port : 22 / 공인Port : 10999' },
                    { 'id': '2', 'name': '포트포워딩', 'title': '공인IP : 211.34.247.18 / 사설Port : 22 / 공인Port : 10999',
                      'children': [
                        { 'id': '3', 'name': '방화벽', 'title': 'DMZ_Sub',
                            'children': [
                                { 'id': '4', 'name': 'jaeseung', 'title': '172.25.0.85',
               }
             ]
               }
             ]
           },
        ]
      },
      ds2: null,
      ds3: null,
      data: null,
      data2: null,
      data3: null,
      serverlist:'',
      selected: 'ZONE',
        options: [
          { value: 'ZONE', text: 'ZONE' },
          { value: '포트포워딩', text: '포트포워딩' },
          { value: 'c', text: '어떤경우가있을까여' },
          { value: 'd', text: '로드밸런서(개발진행중)', disabled: true },
        ]
    }
  },
  methods: {
    selectNode (nodeData) {
      alert('node ' + nodeData.title + ' is selected')
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
    ktfirewall() {
       axios
        .get("http://localhost:8080/ktd1firewall")
        .then(res => {
          this.data3 = res.data;
        })
        .catch(err => {
          console.log(err);
        });
    },
    jsonadd(){
      var jobj3 = new Object();
      jobj3.id = "1";
      jobj3.name = "Network";
      jobj3.title = "external";

      var i2;
      var test2 = [];
      for (i2 = 0; i2 < this.data2.nc_listentpublicipsresponse.publicips.length; i2++) {  
        var jobj4 = new Object();
        jobj4.id = i2+1;
        jobj4.name = "공인IP";
        jobj4.title = this.data2.nc_listentpublicipsresponse.publicips[i2].ip;
        test2.push(jobj4);
        var i3;
        var test3 = [];
        for(i3 = 0; i3< this.data2.nc_listentpublicipsresponse.publicips[i2].virtualips.length; i3++){
          var jobj5 = new Object();
          jobj5.id = i2+1;
          jobj5.name = "공인포트";
          jobj5.title = this.data2.nc_listentpublicipsresponse.publicips[i2].virtualips[i3].publicendport;
          test3.push(jobj5);

          var test4 = [];
          var jobj6 = new Object();
          jobj6.id = i2+2;
          jobj6.name = "사설포트";
          jobj6.title = this.data2.nc_listentpublicipsresponse.publicips[i2].virtualips[i3].privateport;
          test4.push(jobj6);
          jobj5.children = test4

          var test5 = [];
          var jobj7 = new Object();
          jobj7.id = i2+3;
          jobj7.name = "사설IP";
          jobj7.title = this.data2.nc_listentpublicipsresponse.publicips[i2].virtualips[i3].vmguestip;
          test5.push(jobj7);
          jobj6.children = test5

          var j;
          var j2;
          for(j=0; j<this.data3.nc_listfirewallrulesresponse.firewallrules.length; j++){
            for(j2=0; j2<this.data3.nc_listfirewallrulesresponse.firewallrules[j].acls.length; j2++){
              if(this.data2.nc_listentpublicipsresponse.publicips[i2].ip == 
              this.data3.nc_listfirewallrulesresponse.firewallrules[j].acls[j2].dstaddrs[0].ip.substring(3,16)
              && this.data2.nc_listentpublicipsresponse.publicips[i2].virtualips[i3].publicendport ==
              this.data3.nc_listfirewallrulesresponse.firewallrules[j].acls[j2].dstaddrs[0].ip.substring(17,22)
              ){

                
                var test6 = [];
                var jobj8 = new Object();
                jobj8.id = i2+4;
                jobj8.name = "프로토콜";
                jobj8.title = this.data3.nc_listfirewallrulesresponse.firewallrules[j].acls[j2].services[0].protocol;
                test6.push(jobj8);
                jobj7.children = test6

                var test7 = [];
                var jobj9 = new Object();
                jobj9.id = i2+5;
                jobj9.name = "Tier";
                jobj9.title = this.data3.nc_listfirewallrulesresponse.firewallrules[j].acls[j2].dstintfs[0].networkname;
                test7.push(jobj9);
                jobj8.children = test7
              }
            }
          }
          
          

        }
        jobj4.children = test3
      }

      jobj3.children = test2
      this.ds3 = jobj3
      console.log(this.ds3)
    },

  },

  created() {
    this.ktip();
    this.ktfirewall();
  },
  mounted(){
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