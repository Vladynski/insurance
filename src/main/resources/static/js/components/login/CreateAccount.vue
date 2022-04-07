<template>
  <!--FIXME-->
  <Input value="123" ref="firstName" placeholder="Имя" input-class="simple-input"/>
  <Input value="123" ref="secondName" placeholder="Фамилия" input-class="simple-input"/>
  <Input value="123" ref="patronymic" placeholder="Отчество" input-class="simple-input"/>
  <Input value="123@mail.ru" ref="email" type="email" placeholder="Email" input-class="simple-input"/>
  <Input value="123" ref="password" type="password" placeholder="Пароль" input-class="simple-input"/>
  <Input value="123" ref="password2" type="password" placeholder="Повторите пароль" input-class="simple-input"/>
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
        this.$api.registration(
            this.$refs.email.getText(),
            this.$refs.firstName.getText(),
            this.$refs.secondName.getText(),
            this.$refs.patronymic.getText(),
            this.$refs.password.getText()
        ).then(
            (ok) => {
              this.unblock()
              this.infoForm().showOk('Аккаунт создан')
              this.swap()
            },
            (e) => {
              this.unblock()
              this.$api.errorHandler(e, this.infoForm, () => {
                return this.$refs
              })
            }
        )
      }
    },
    testFields() {
      return true //FIXME
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

<style src="../../vue_css/login.css"></style>