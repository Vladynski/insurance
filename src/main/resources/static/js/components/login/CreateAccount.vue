<template>
  <Input ref="firstName" placeholder="Имя" input-class="simple-input"/>
  <Input ref="secondName" placeholder="Фамилия" input-class="simple-input"/>
  <Input ref="patronymic" placeholder="Отчество" input-class="simple-input"/>
  <Input ref="email" type="email" placeholder="Email" input-class="simple-input"/>
  <Input ref="password" type="password" placeholder="Пароль" input-class="simple-input"/>
  <Input ref="password2" type="password" placeholder="Повторите пароль" input-class="simple-input"/>
</template>

<script>
import Input from '../items/Input.vue'

export default {
  props: ['swap', 'block', 'unblock', 'infoForm'],
  data() {
    return {
      fields: [],
      errorText: ''
    }
  },
  methods: {
    logIn() {
      this.swap()
    },
    createAccount() {
      if (this.testFields()) {
        this.block()
        this.$axios.post(
            '/registration',
            {
              email: this.$refs.email.getText(),
              first_name: this.$refs.firstName.getText(),
              second_name: this.$refs.secondName.getText(),
              patronymic: this.$refs.patronymic.getText(),
              password: this.$refs.password.getText()
            }
        ).then(
            (s) => {
              this.unblock()
              this.infoForm().showOk('Аккаунт создан')
              this.swap()
            },
            (e) => {
              this.unblock()
              if (Object.values(e.response.data).indexOf('message')) {
                this.infoForm().showError(e.response.data.message)
              }
            }
        )
      }
    },
    testFields() {
      let ok = true
      this.fields.forEach(el => {
        if (!el.test())
          ok = false
      })
      if (ok) {
        if (this.$refs.password2.getText() !== this.$refs.password.getText()) {
          this.$refs.password.showError('Пароли не совпадают')
          ok = false;
        } else {
          this.$refs.password.update()
        }
        if (!this.$refs.email.getText().match('.+@.+\\..+')) {
          this.$refs.email.showError('Неправильный формат почты')
          ok = false
        }
      }
      return ok
    }
  },
  mounted() {
    this.fields.push(
        this.$refs.firstName,
        this.$refs.secondName,
        this.$refs.patronymic,
        this.$refs.email,
        this.$refs.password,
        this.$refs.password2
    )
  },
  components: {Input}
}
</script>

<style src="./login.css"></style>