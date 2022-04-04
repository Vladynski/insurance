<template>
  <div class="main-container">
    <div class="sidenav">
      <Menu ref="menu" :username="username"/>
    </div>
    <div class="content">
      <Profile v-if="showProfile" :outUserdata="userdata"/>
      <Calculator v-else-if="showCalculator" :outUserdata="userdata"/>
    </div>
  </div>
</template>

<script>
import Menu from './menu/Menu.vue'
import Profile from "./forms/Profile.vue";
import Calculator from "./forms/Calculator.vue";

export default {
  components: {Calculator, Profile, Menu},
  data() {
    return {
      username: '',
      userdata: undefined,
      showProfile: false,
      showCalculator: false
    }
  },
  mounted() {
    this.$api.getSelfData().then(
        (ok) => {
          this.userdata = ok.data
          // this.showCalculator = true
          this.username = this.userdata.second_name + ' ' + this.userdata.first_name + ' ' + this.userdata.patronymic
        }
    )
    this.$refs.menu.addItem('👤 Профиль', () => this.showProfile = true, () => this.showProfile = false)
    this.$refs.menu.addItem('💼 Страховки', () => this.showProfile = true, () => this.showProfile = false)
    this.$refs.menu.addItem('➗ Оформление', () => this.showCalculator = true, () => this.showCalculator = false)
    this.$refs.menu.addItem('❔ FAQ', () => this.showCalculator = true, () => this.showCalculator = false)
    this.$refs.menu.addItem('✉ Задать вопрос', () => this.showCalculator = true, () => this.showCalculator = false)
    this.$refs.menu.addItem('⚙ Настройки', () => this.showCalculator = true, () => this.showCalculator = false)
  }
}
</script>

<style scoped>
.main-container {
  display: flex;
  flex-direction: row;
  width: 75%;
  height: 95%;
  border-radius: 5px;
}

.sidenav {
  height: 100%;
  width: 300px;
  background-color: #3F3F3F;
}

.content {
  padding: 0.5%;
  width: inherit;
  height: 100%;
  background: repeating-linear-gradient(
      45deg,
      #d7d7d7,
      #d7d7d7 10px,
      #e1e1e1 10px,
      #e1e1e1 20px
  );
}

</style>