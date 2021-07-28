<template>
  <div id="app">
    <h1>{{jsonadd()}}</h1>
    <h1>VM정보를 나타냅니다</h1>

      <org-chart :datasource="ds2" @node-click="selectNode"></org-chart>
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
      serverlist:'',
    }
  },
  methods: {
    selectNode (nodeData) {
      alert('node ' + nodeData.title + ' is selected')
    },
    serverList() {
      axios
        .get("http://localhost:8080/ktd1serverlist")
        .then(res => {
          this.data = res.data;
          console.log(this.ds)
        })
        .catch(err => {
          console.log(err);
        });
    },
    jsonadd(){
      console.log("JSONADD()함수")
      var jobj = new Object();
      jobj.id = "1";
      jobj.name = "위치";
      jobj.title = "DX-M1";

      var i;
      var test = [];
      for (i = 0; i < this.data.servers.length; i++) {
      var jobj2 = new Object();
      jobj2.id = i+1;
      jobj2.name = "서버이름";
      jobj2.title = this.data.servers[i].name;
      test.push(jobj2);
      }
      jobj.children = test
      this.ds2 = jobj
    },

  },

  created() {
    this.serverList();
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