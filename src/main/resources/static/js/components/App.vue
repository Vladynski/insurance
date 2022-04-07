<template>
  <div style="transition-duration: 500ms; display: flex; align-items: center; justify-content: center; height: 100%" :style="show ? 'opacity: 1' : 'opacity: 0'">
    <login-component v-if="!authorized" :authorized="()=>this.authorized=true"></login-component>
    <main-app v-else></main-app>
  </div>
</template>

<script>
import LoginComponent from './login/LoginPage.vue'
import MainApp from './main_content/ContentApp.vue'

export default {
  mounted() {
    this.$api.getSelfData().then(
        (ok) => {
          this.authorized = true
          this.show = true
        },
        (err) => this.show = true
    )
  },
  data() {
    return {
      show: false,
      authorized: false
    }
  },
  components: {
    LoginComponent, MainApp
  }
}
</script>

<style scoped></style>