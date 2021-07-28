
import Vue from "vue";
import App from "./App";
import router from "./router/index";

import PaperDashboard from "./plugins/paperDashboard";
import "vue-notifyjs/themes/default.css";
import axios from 'axios'
import store from './store/index';

import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
Vue.use(PaperDashboard);
Vue.prototype.$http = axios
Vue.use(BootstrapVue)
Vue.use(IconsPlugin)

Vue.config.productionTip = false;

// main.js
import BarChart from "@mitevpi/v3";
Vue.use(BarChart);

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
