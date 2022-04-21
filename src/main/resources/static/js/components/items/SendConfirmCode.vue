<template>
  <div v-show="show" class="block-row block-row-right" style="font-size: 16px">
      <span v-if="sendTimeout > 0">
        Повторно запросить код можно будет через {{ sendTimeout }} сек
      </span>
    <span v-else-if="errorMessage">
      {{ errorMessage }}
    </span>
    <Button v-else class="btn blue-btn" :click="sendLetterWithCode">
      Письмо с кодом не пришло, отправить письмо ещё раз
    </Button>
  </div>
</template>

<script>
import Button from "./Button.vue";

let globalSendTimeout = -1
const globalSendTimeoutIfResponseError = 60 * 5 //5 minutes

export default {
  components: {Button},
  data() {
    return {
      sendTimeout: -1,
      show: false,
      errorMessage: undefined,
      timer: undefined
    }
  },
  methods: {
    sendLetterWithCode() {
      this.$api.requestConfirmationCode()
      this.sendTimeout = globalSendTimeout
      this.timer = undefined
      this.startTimer()
    },
    startTimer() {
      if (!this.timer) {
        this.sendTimeout = globalSendTimeout
        this.timer = setInterval(() => {
          this.sendTimeout--
          if (this.sendTimeout === 0)
            clearInterval(this.timer)
        }, 1000)
      }
    },
    sendCode() {
      this.$api.requestConfirmationCode().then(
          (ok) => {
            this.show = true
            this.$api.getMailSendTimeoutSeconds().then(
                (ok) => {
                  globalSendTimeout = ok.data
                  this.startTimer()
                },
                (err) => {
                  globalSendTimeout = globalSendTimeoutIfResponseError
                  this.startTimer()
                }
            )
          },
          (err) => {
            this.errorMessage = 'Не удалось запросить код для подтверждения личности'
          }
      )
    }
  }
}
</script>

<style scoped>
@import "../../vue_css/base_form.css";
@import "../../vue_css/default_size.css";
</style>