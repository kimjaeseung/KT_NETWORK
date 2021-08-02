<template>
    <div class="row">
      <div class="col-md-8">
        <card>
          <template slot="header">
            <h4 class="card-title">KT Cloud Server 생성</h4><p>{{data2.keypairs[0].keypair.name}}</p>
            </h4>
            <div class="col-md-4">
            </div>
          </template>
          <div class="container">
  <div class="row">
    <div class="col-2">
      위치
    </div>
    <div class="col-4">
      <b-form-select v-model="select">
              <option v-for="option in op" v-bind:select="option.value">
                 {{ option.text }}
              </option>
      </b-form-select>
    </div>
  </div><p></p>
  <div class="row">
    <div class="col-2">
      Tier
    </div>
    <div class="col-4">
      <b-form-select v-model="select2">
              <option v-for="option in op2" v-bind:value="option.value">
                 {{ option.text }}
              </option>
      </b-form-select>
    </div>
  </div><p></p>
  <div class="row">
    <div class="col-2">
      서버이름
    </div>
    <div class="col-4">
      <b-form-input
      id="input-live"
      v-model="name"
      :state="nameState"
      aria-describedby="input-live-help input-live-feedback"
      placeholder="Enter your name"
      trim
    ></b-form-input>
    <b-form-invalid-feedback id="input-live-feedback">
      영문으로 시작해야 합니다.
    </b-form-invalid-feedback>
    </div>
  </div>
  <p></p>
  <div class="row">
    <div class="col-2">
      Key Pair
    </div>
    <div class="col-4">
      <b-form-select v-model="keypair">
              <option v-for="option in keypair2" v-bind:value="option.value">
                 {{ option.text }}
              </option>
      </b-form-select>
    </div>
  </div>
  <p></p>
  <div class="row">
    <div class="col-3">
      옵션
      <b-form-group v-slot="{ ariaDescribedby }">
      <b-form-radio-group
        id="btn-radios-3"
        v-model="selected1"
        :options="options1"
        :aria-describedby="ariaDescribedby"
        name="radio-btn-stacked"
        buttons
        stacked
      ></b-form-radio-group>
    </b-form-group>
    </div>
    <div class="col-3">
      <p></p>
            <b-form-group v-slot="{ ariaDescribedby }">
      <b-form-radio-group
        id="btn-radios-3"
        v-model="selected2"
        :options="options2"
        :aria-describedby="ariaDescribedby"
        name="radio-btn-stacked"
        buttons
        stacked
      ></b-form-radio-group>
    </b-form-group>
    </div>
    <div class="col-3">
      <p></p>
      <b-form-group v-slot="{ ariaDescribedby }">
      <b-form-radio-group
        id="btn-radios-3"
        v-model="selected3"
        :options="options3"
        :aria-describedby="ariaDescribedby"
        name="radio-btn-stacked"
        buttons
        stacked
      ></b-form-radio-group>
    </b-form-group>
    </div>
    <div class="col-3">
      <p></p>
            <b-form-group v-slot="{ ariaDescribedby }">
      <b-form-radio-group
        id="btn-radios-3"
        v-model="flavorRef"
        :options="options4"
        :aria-describedby="ariaDescribedby"
        name="radio-btn-stacked"
        buttons
        stacked
      ></b-form-radio-group>
    </b-form-group>
    </div>
  </div>
</div>
          <template slot="header">
          <div class="content all-icons">   
            
          </div>
          </template>
          <P>
          </P>
          <b-container class="bv-example-row bv-example-row-flex-cols">
  <b-row>
    <b-col></b-col><b-col></b-col><b-col></b-col>
    <b-col><b-button v-on:click="go()">Create Server</b-button></b-col>
  </b-row>
          </b-container>
        </card>
      </div>
    <div class="col-md-4">
      <card>
        <template slot="header">
            <h4 class="card-title">KT Cloud Server 포트포워딩 설정</h4><p></p>
        </template>
        <div class="container">
          <div class="row">
            <div class="col-4">
              <p>공인IP</p>
            </div>
            <div class="col-8">
              <b-form-select v-model="ip2">
                <option v-for="item in ip.nc_listentpublicipsresponse.publicips" :value="item.ip">
                  {{item.ip}}
                </option>
              </b-form-select>
            </div>
          </div><p></p>
          <div class="row">
            <div class="col-4">
              <p>사설Port</p>
            </div>
            <div class="col-8">
              <b-form-input
      id="input-live"
      v-model="sport"
      :state="nameState3"
      aria-describedby="input-live-help input-live-feedback"
      placeholder="22(SSH)"
      trim
    ></b-form-input>
            </div>
          </div><p></p>
          <div class="row">
            <div class="col-4">
              <p>공인Port</p>
            </div>
            <div class="col-8">
              <b-form-input
      id="input-live"
      v-model="gport"
      :state="nameState4"
      aria-describedby="input-live-help input-live-feedback"
      placeholder="1~10999, 12501~65535"
      trim
    ></b-form-input>
            </div>
          </div><p></p>
          <div class="row">
            <div class="col-4">
              <p>프로토콜</p>
            </div>
            <div class="col-8">
              <b-form-select v-model="select3">
                <option v-for="item in tcp" :value="item.text">
                  {{item.text}}
                </option>
              </b-form-select>
            </div>
          </div>

        </div>
      </card>
    </div>
    </div>
</template>
<script>
import axios from "axios";


export default {
  components: {
    
  },
  computed: {
      nameState() {
        return this.name.length > 2 ? true : false
      },
      nameState2() {
        return this.keypair.length > 2 ? true : false
      },
      nameState3() {
        return this.sport.length > 0 ? true : false
      },
      nameState4() {
        return this.gport.length > 0   ? true : false
      },
      go(){
        console.log(this.name+" "+this.keypair)
        this.$store.state.ip = this.ip2;
        this.$store.state.servername = this.name;
        this.$store.state.privateport = this.sport;
        this.$store.state.publicport = this.gport;
        this.$store.state.protocol = this.select3;
        const frm = new FormData() 
        frm.append('name', this.name) 
        frm.append('key_name', this.keypair)
        frm.append('flavorRef', this.flavorRef)
        frm.append('nuuid', this.select2)
        frm.append('vuuid', this.selected3)
        axios.post('http://localhost:8080/ktd1createserver', frm)
        .then((response) => {
          this.$router.push('loadingpage')
          })
        .catch((error) => {
        })
      }
  },
  data() {
    return {
      data: null,
      data2: null,
      keypair2: null,
      ip: null,
      ip2: '',
      value: '',
      name:'',
      keypair:'',
      sport: '',
      gport: '',
      startport: "",
      ipaddress: "",
      protocol: "",
      cidrlist: "",
      ipaddressid: "",
      networkid: "",
      id: "",
      state: "",
      endport: "",
      select: 'DX-M1',
      select2: 'DMZ_Sub',
      select3: 'TCP',

    op: [
      { text: 'DX-M1', value: 'DX-M1' },
    ],
    op2: [
      { text: 'DMZ_Sub', value: 'd001fa9d-12f4-466d-b82b-893248c31c9c' },
      { text: 'Private_Sub', value: '4d066435-3345-41d3-b8c4-68bb390bc0621' },
    ],
    tcp: [
      { text: 'TCP', value: 'TCP' },
      { text: 'UDP', value: 'UDP' },
    ],
    selected1: 'radio1',
        options1: [
          { text: 'OS', value: 'OS' },
          { text: 'Application', value: 'Application' },
          { text: 'DB', value: 'DB'},
          { text: 'GPU Server', value: 'GPU Server' }
        ],
    selected2: 'radio2',
        options2: [
          { text: 'CentOS', value: 'd001fa9d-12f4-466d-b82b-893248c31c9c' },
          { text: 'Ubuntu', value: '4d066435-3345-41d3-b8c4-68bb390bc0621' },
          { text: 'Debian', value: '4d066435-3345-41d3-b8c4-68bb390bc0622'},
          { text: 'RedhatLinux', value: '4d066435-3345-41d3-b8c4-68bb390bc0623' },
          { text: 'Windows', value: '4d066435-3345-41d3-b8c4-68bb390bc0624' },
        ],
    selected3: 'radio3',
        options3: [
          { text: 'CentOS 7.0 64bit', value: 'radio1' },
          { text: 'CentOS 7.1 64bit', value: 'radio2' },
          { text: 'CentOS 7.6 64bit', value: 'fa8fdef3-fb2c-43fd-ad38-de858c39a53a'},
          { text: 'CentOS 7.8 64bit', value: '8e373c2e-77c4-4881-ba9c-7c704002608b' },
          { text: 'CentOS 7.9 64bit', value: 'radio5' },
          { text: 'CentOS 7.2 64bit', value: 'aa5ab18c-b818-4e45-b055-a0bc7a1fcc3a' },
        ],
    flavorRef: 'radio4',
        options4: [
          { text: '1vCore 1GB', value: 'f9764e6b-1b46-421d-8998-816c2d8d13ce' },
          { text: '1vCore 2GB', value: 'f9764e6b-1b46-421d-8998-816c2d8d13ce2' },
          { text: '2vCore 2GB', value: 'e31c8e80-3902-4194-8808-2e0784e5c6d0'},
          { text: '2vCore 4GB', value: 'e7ce0e88-7b5e-49c6-880a-68db7020b0ac' },
          { text: '2vCore 8GB', value: '39b0a097-993b-4067-8db4-7a1bee96ce57' },
          { text: '2vCore 16GB', value: '7de90e36-d65c-420f-8bf8-7530bdc4a161' },
          { text: '4vCore 4GB', value: '93e8987c-0e69-4e21-a189-84f40294b3f9' },
        ],
    };
  },
  methods: {
    fetchData() {
      axios
        .get("http://localhost:8080/ktfirewall")
        .then(res => {
          this.data = res.data;
        })
        .catch(err => {
          console.log(err);
        });
    },
    fetchData2() {
      axios
        .get("http://localhost:8080/ktip")
        .then(res => {
          this.ip = res.data;
        })
        .catch(err => {
          console.log(err);
        });
    },
    fetchData3() {
      axios
        .get("http://localhost:8080/ktkeypair")
        .then(res => {
          this.data2 = res.data;
          this.jsonadd();
        })
        .catch(err => {
          console.log(err);
        });
    },
    jsonadd(){
      var i;
      var test = [];
      for (i = 0; i < this.data2.keypairs.length; i++) {
        var jobj2 = new Object();
        jobj2.text = this.data2.keypairs[i].keypair.name
        jobj2.value = this.data2.keypairs[i].keypair.name
        test.push(jobj2);
      }
      this.keypair2 = test
    },
  },
  created() {
    this.fetchData();
  },
  mounted(){
    this.fetchData2();
    this.fetchData3();
    this.jsonadd();
  }
};
</script>
<style>
</style>
