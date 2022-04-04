<template>
  <div ref="container" class="container">
    <span class="title">{{ loginForm ? 'Вход' : 'Создание аккаунта' }}</span>
    <InfoFrame ref="infoForm"/>
    <Login v-if="loginForm" ref="loginForm" :block="blockButtons" :unblock="unblockButtons" :swap="swap"
           :infoForm="getInfoForm" :authorized="authorized"/>
    <CreateAccount v-else ref="createAccountForm" :swap="swap" :block="blockButtons" :unblock="unblockButtons"
                   :infoForm="getInfoForm"/>
    <Button v-if="loginForm" :ref="(btn) => buttons[0] = btn" :click="login" class="green-btn simple-btn">Войти
    </Button>
    <Button v-else :ref="(btn) => buttons[0] = btn" :click="createAccount" class="green-btn simple-btn">Создать
      аккаунт
    </Button>
    <Button v-if="loginForm" :ref="(btn) => buttons[1] = btn" :click="createAccount" class="blue-btn simple-btn">
      Создать аккаунт
    </Button>
    <Button v-else :ref="(btn) => buttons[1] = btn" :click="login" class="blue-btn simple-btn">Войти</Button>
  </div>
</template>

<script>
import Login from "./Login.vue";
import CreateAccount from "./CreateAccount.vue";
import Button from "../items/Button.vue";
import InfoFrame from '../items/InfoFrame.vue'

export default {
  props: ['authorized'],
  data() {
    return {
      loginForm: true,
      buttonsBlocked: true,
      buttons: [undefined, undefined]
    }
  },
  methods: {
    login() {
      this.getInfoForm().update()
      this.getCurrentForm().logIn()
    },
    createAccount() {
      this.getInfoForm().update()
      this.getCurrentForm().createAccount()
    },
    swap() {
      this.$refs.container.style.opacity = '0';
      setTimeout(() => {
        this.loginForm = !this.loginForm
        this.$refs.container.style.opacity = '1';
      }, 300);
    },
    getCurrentForm() {
      if (this.loginForm)
        return this.$refs.loginForm
      else
        return this.$refs.createAccountForm
    },
    blockButtons() {
      this.buttons.forEach(el => el.block())
    },
    unblockButtons() {
      this.buttons.forEach(el => el.unblock())
    },
    getInfoForm() {
      return this.$refs.infoForm
    },
    showMessageIfExists() {
      const params = window.location.search.substring(1).split('&')
      let message, type
      params.forEach(el => {
        const keyValue = el.split('=')
        if (keyValue[0] === 'message')
          message = keyValue[1]
        else if (keyValue[0] === 'type')
          type = keyValue[1]
      })
      if (message && type) {
        window.history.pushState('ala', 'www', window.location.search.substring(0, window.location.search.indexOf('?') - 1))
        this.getInfoForm().show(decodeURI(message).replace('+', ' '), type)
      }
    },
  },
  components: {Login, Button, CreateAccount, InfoFrame},
  mounted() {
    this.showMessageIfExists()
  }
}
</script>

<style>
.container {
  overflow: hidden;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  width: 500px;
  background-color: #FF9077FF;
  padding: 50px 50px 50px 50px;
  border-radius: 4px;
  transition-duration: 300ms;
}

.container > * {
  margin: 11px 0 11px 0;
}

.title {
  font-size: 24px;
  text-transform: uppercase;
  font-weight: bold;
  width: 100%;
}
</style>