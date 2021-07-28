<template>
 <div class="row">
  <div class="col-md-12">
    <card>
        <h1> 서버, 포트포워딩, 방화벽 정책 생성이 완료 되었습니다.</h1>
        <h1> Ping 체크 성공 ! </h1>
        <p></p>
        <p style="white-space: pre-line;">{{ this.$store.state.message }}</p>
        <p></p>
    </card>
  </div>
 </div>
</template>
<script>
export default {
  components: { 
      
   },
  data() {
    return {
    };
  },
  methods: {
    
  },
  created() {
  },
  mounted(){
  },
  computed:{
    
  }
};
</script>
<style>
</style>
