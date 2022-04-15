import * as Vue from 'vue'
import App from 'components/App.vue'
import axios from "axios";
import {createApi} from "./api/ApiWorker";
import {createAdminApi} from "./api/AdminApiWorker";

axios.defaults.headers.common['Accept'] = 'application/json'


let app = Vue.createApp(App);
app.config.globalProperties.$axios = axios;
// app.config.globalProperties.$app = app;
app.config.globalProperties.$api = createApi(axios).initSelectionData();
app.config.globalProperties.$adminApi = createAdminApi(axios);
app.mount('#app')
