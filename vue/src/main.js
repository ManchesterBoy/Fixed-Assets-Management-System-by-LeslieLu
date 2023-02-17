import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/globe.css'
import request from "@/utils/request";


Vue.config.productionTip = false


Vue.use(ElementUI,{size:"small"});

//更改浏览器标题
Vue.directive('title',{
  inserted: function (el,binding){
    document.title = el.dataset.title
  }
})

Vue.prototype.request=request

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
