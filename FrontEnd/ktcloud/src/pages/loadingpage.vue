<template>
 <div class="row">
  <div class="col-md-12">
    <card>
        <h2>서버 생성, 포트포워딩 생성, 방화벽 정책 생성
        </h2>
        <h2>ping 체크까지 한번에 진행중입니다. 잠시만 기다려주세요.
        </h2>
        <p></p><vue-simple-spinner size="large" />
    </card>
  </div>
 </div>
</template>
<script>
import axios from "axios";
import VueSimpleSpinner from 'vue-simple-spinner'
import Main from "./Main"
import success from "./success"
export default {
  components: { 
      VueSimpleSpinner
      ,Main
   },
  data() {
    return {
      data: null,
      temp: null,
      status : '',
      name: '',
    };
  },
  methods: {
    fetchData() {
        const frm = new FormData() 
        frm.append('privateport', this.$store.state.privateport)
        frm.append('publicport', this.$store.state.publicport)
        frm.append('protocol', this.$store.state.protocol)
        frm.append('ip', this.$store.state.ip)
        axios.post('http://localhost:8080/ktd1createportforwarding', frm)
        .then(response => {
          console.log(response.data)
          this.$store.state.message = response.data
          console.log(this.$store.state.message)
          this.$router.push('success')
          })
        .catch((error) => {
        })
    },
  },
  created() {
    this.fetchData();
  },
  mounted(){
  },
  computed:{
    
  }
};
</script>
<style>
</style>
