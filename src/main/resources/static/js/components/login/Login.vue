<template>
  <Input ref="username" type="email" placeholder="Email" input-class="simple-input"/>
  <Input ref="password" placeholder="Пароль" type="password" input-class="simple-input"/>
</template>

<script>
import Input from '../items/Input.vue'

export default {
  props: ['swap', 'block', 'unblock', 'infoForm', 'authorized'],
  data() {
    return {
      username: '',
      password: '',
      infoText: 'default info text'
    }
  },
  methods: {
    logIn() {
      if (this.testFields()) {
        this.block()
        this.username = this.$refs.username.getText()
        this.password = this.$refs.password.getText()
        this.$api.getSelfData(this.username, this.password)
            .then(
                (ok) => {
                  this.authorized()
                  this.unblock()
                  this.$api.initSelectionData()
                },
                (err) => {
                  this.unblock()
                  if (err.response.status === 401)
                    this.infoForm().showError('Неправильная почта или пароль')
                }
            )
      }
    },
    createAccount() {
      this.swap()
    },
    testFields() {
      const res = this.$refs.password.test() || this.$refs.username.test();
      if (!res)
        return false;
      if (!this.$refs.username.getText().match('.+@.+\\..+')) {
        this.$refs.username.showError('Неправильный формат почты')
        return false;
      }
      return true;
    }
  },
  components: {Input},
  mounted() {
    this.$refs.username.addUpdateBundle(this.infoForm())
    this.$refs.password.addUpdateBundle(this.infoForm())
  }
}
</script>