import Vue from 'vue'
import Vuex from 'vuex'
import router, {resetRouter} from "@/router";

Vue.use(Vuex)

const store = new Vuex.Store({
    state: {
        currentPathName: ''
    },
    mutations:{
        setPath(state){
            state.currentPathName = localStorage.getItem("currentPathName")
        },
        logout(state){
            //清空缓存
            localStorage.removeItem("user")
            localStorage.removeItem("menus")
            localStorage.removeItem("assets")
            localStorage.removeItem("users")
            router.push("/login")

            //重置路由
            resetRouter()
        }
    }
})

export default store