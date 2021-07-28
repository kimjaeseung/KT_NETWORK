import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        servername: '',
        privateport: '',
        publicport: '',
        protocol: '',
        ip: '',
        message: '',
    },
});