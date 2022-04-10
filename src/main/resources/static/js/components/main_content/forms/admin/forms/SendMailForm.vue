<template>
  <div class="block-column">
    <TextArea ref="textArea" :title="'Письмо для ' + email" placeholder="Текст письма" style="margin-bottom: 15px">
      Письмо будет отправлено адресату от имени компании. Текст может содержать HTML
    </TextArea>
    <Button :click="send" class="btn green-btn d-btn-max-size">Отправить</Button>
  </div>
</template>

<script>
import TextArea from "../../items/TextArea.vue";
import Button from "../../items/Button.vue";

export default {
  components: {Button, TextArea},
  props: ['email'],
  methods: {
    send() {
      console.log(this.$refs.textArea.getText());
    }
  }
}
</script>

<style scoped>

</style>