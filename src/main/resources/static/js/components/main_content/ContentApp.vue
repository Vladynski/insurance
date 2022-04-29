<template>
  <div class="main-container">
    <div class="sidenav">
      <Menu ref="menu" :username="username"/>
    </div>
    <div class="content">
      <Profile v-if="showProfile" :outUserdata="userdata"/>
      <Calculator v-else-if="showCalculator" :outUserdata="userdata"/>
      <InsuranceList v-else-if="showInsuranceList"/>
      <AskQuestion v-else-if="askQuestion"/>
      <FaqList v-else-if="faq"/>
      <AdminPanel v-else-if="adminPanel"/>
    </div>
  </div>
</template>

<script>
import Menu from './menu/Menu.vue'
import Profile from "./forms/Profile.vue";
import Calculator from "./forms/Calculator.vue";
import InsuranceList from "./forms/InsuranceList.vue";
import AskQuestion from "./forms/AskQuestion.vue";
import FaqList from "./forms/FaqList.vue";
import AdminPanel from "./forms/AdminPanel.vue";

export default {
  components: {AdminPanel, FaqList, AskQuestion, InsuranceList, Calculator, Profile, Menu},
  data() {
    return {
      username: '',
      userdata: undefined,
      showProfile: false,
      showCalculator: false,
      showInsuranceList: false,
      askQuestion: false,
      faq: false,
      adminPanel: false
    }
  },
  mounted() {
    this.$refs.menu.addItem('Профиль', "profile.png", () => this.showProfile = true, () => this.showProfile = false)
    this.$refs.menu.addItem('Страховки', "list.png", () => this.showInsuranceList = true, () => this.showInsuranceList = false)
    this.$refs.menu.addItem('Оформление', "secure-insurance.png", () => this.showCalculator = true, () => this.showCalculator = false)
    this.$refs.menu.addItem('FAQ', "faq.png", () => this.faq = true, () => this.faq = false)
    this.$refs.menu.addItem('Задать вопрос', "question.png", () => this.askQuestion = true, () => this.askQuestion = false)
    this.$refs.menu.addItem('Выйти', "logout.png", () => {
      this.$api.logout().then((ok) => {
        document.location.reload()
      })
    }, () => this.askQuestion = false)

    this.$api.getSelfData().then(
        (ok) => {
          this.userdata = ok.data
          this.username = this.userdata.second_name + ' ' + this.userdata.first_name + ' ' + this.userdata.patronymic
          if (this.userdata.roles.indexOf('ADMIN') >= 0) {
            this.$refs.menu.addItemByIndex(-1, 'Админ.', "admin.png", () => this.adminPanel = true, () => this.adminPanel = false)
          }
        }
    )
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