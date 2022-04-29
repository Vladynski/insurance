<template>
  <div style="transition-duration: 500ms; display: flex; align-items: center; justify-content: center; height: 100%"
       :style="show ? 'opacity: 1' : 'opacity: 0'">
    <login-component v-if="!authorized" :authorized="()=>this.authorized=true"></login-component>
    <main-app v-else></main-app>
  </div>
  <Banner :ref="el => setBanner(el)"/>
</template>

<script>
import LoginComponent from './login/LoginPage.vue'
import MainApp from './main_content/ContentApp.vue'
import Banner from './items/banner/Banner.vue'

export default {
  components: {LoginComponent, MainApp, Banner},
  methods: {
    setBanner(el) {
      this.$app.config.globalProperties.$banner = el
    }
  },
  data() {
    return {
      show: false,
      authorized: false
    }
  },
  mounted() {
    this.$api.getSelfData().then(
        (ok) => {
          this.authorized = true
          this.show = true
        },
        (err) => this.show = true
    )
  }
}
</script>

<style scoped></style>