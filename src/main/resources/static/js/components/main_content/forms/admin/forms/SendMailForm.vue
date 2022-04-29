<template>
  <div class="block-column">
    <TextArea ref="textArea" :title="'Письмо для ' + email" placeholder="Текст письма" style="margin-bottom: 15px">
      Письмо будет отправлено адресату от имени компании. Текст может содержать HTML
    </TextArea>
    <Button :click="send" class="btn green-btn d-btn-max-size">Отправить</Button>
  </div>
</template>

<script>
import TextArea from "../../../../items/TextArea.vue";
import Button from "../../../../items/Button.vue";

export default {
  components: {Button, TextArea},
  props: ['email', 'userId'],
  methods: {
    send() {
      if (this.$refs.textArea.test()) {
        this.$adminApi.sendMail(this.userId, this.$refs.textArea.getText()).then(
            (ok) => {
              this.$refs.textArea.showError('Письмо отправлено')
              this.$refs.textArea.clear()
              setTimeout(() => {
                this.$refs.textArea.update()
              }, 5000)
            },
            (err) => {
              console.log(err.response);
              this.$refs.textArea.showError(err.response.data.message)
            }
        )
      }
    }
  }
}
</script>

<style scoped>

</style>