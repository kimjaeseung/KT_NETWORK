<template>
 <div class="row">
  <div class="col-md-12">
    <div class="card text-white bg-info mb-3">
    <div class="card-header"><h1>KT Cloud 네트워크 자동점검 및 솔루션</h1></div>         
    <p></p>
    </div>
  </div>
  <div class="col-sm-4">
    <div class="card" style="text-align:center;">
      <div class="card-body">
        <h2 class="card-title">자동 환경 구성</h2>
        <p>
        <p class="card-text">서버 VM, 포트포워딩을 생성하면 정책, 라우팅을 자동으로 구성해줍니다. 더불어 ping
            테스트 + 포트스캔으로 네트워크 상태를 점검합니다.
        </p>
        <a href="/createserver" class="btn btn-primary">서버 생성하러 가기</a>
      </div>
    </div>
  </div>
  <div class="col-sm-4">
    <div class="card" style="text-align:center;">
      <div class="card-body">
        <h2 class="card-title">자동 환경 검사</h2>
        <p>
        <p class="card-text">정책, 라우팅 요청에 기반하여 자동으로 점검하고 생성합니다. 나머지 케이스는 추후에 확장할 예정입니다. 감사합니다.</p>
        <a href="#" class="btn btn-primary">네트워크 검사하러 가기</a>
      </div>
    </div>
  </div>
  <div class="col-sm-4">
    <div class="card" style="text-align:center;">
      <div class="card-body">
        <h2 class="card-title">네트워크 토폴로지</h2>
        <p>
        <p class="card-text">Cloud Server의 네트워크가 어떻게 연결되어 있는지, 상태는 어떤지 한눈에 확인할 수 있게 시각화를 제공합니다. 감사합니다.</p>
        <a href="/loadingpage" class="btn btn-primary" >네트워크 그래프 보러가기</a>
      </div>
    </div>
  </div>
 </div>
 
</template>
<script>

export default {

  data() {
    
  },
  methods: {
    
  },
  created() {
  },
  computed:{
  }
 

};
</script>
<style> 
</style>
