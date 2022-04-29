<template>
  <div v-if="show" class="banner">
    <div class="page-container">
      <TextPageBanner v-if="text" :banner="this" :text="text"/>
      <PaymentBanner v-else-if="payment" :banner="this" :payment="payment"/>
      <PhotoBanner v-else-if="photo" :banner="this" :photo="photo"/>
    </div>
  </div>
</template>

<script>
import TextArea from "../TextArea.vue";
import Button from "../Button.vue";
import TextPageBanner from "./TextPageBanner.vue";
import PaymentBanner from "./PaymentBanner.vue";
import PhotoBanner from "./PhotoBanner.vue";

export default {
  components: {TextPageBanner, Button, TextArea, PaymentBanner, PhotoBanner},
  data() {
    return {
      show: false,
      text: undefined,
      payment: undefined,
      photo: undefined,
      hideAction: undefined,
      acceptAction: undefined
    }
  },
  methods: {
    showPhoto(title, content, type) {
      this.photo = {
        title: title,
        content: content,
        type: type
      }
      this.show = true
    },
    showHtmlText(title, text, hideAction, acceptAction) {
      this.text = {
        title: title,
        text: text
      }
      this.hideAction = hideAction
      this.acceptAction = acceptAction
      this.show = true
    },
    showPayment(title, paymentId, sum, acceptAction) {
      this.payment = {
        title: title,
        sum: sum,
        paymentId: paymentId
      }
      this.hideAction = undefined
      this.acceptAction = acceptAction
      this.show = true
    },
    hide() {
      if (this.hideAction)
        this.hideAction()
      this.hideForm()
    },
    accept() {
      if (this.acceptAction)
        this.acceptAction()
      this.hideForm()
    },
    hideForm() {
      this.show = false
      this.text = undefined
      this.payment = undefined
    }
  }
}
</script>

<style scoped>
.banner {
  position: absolute;
  height: 100%;
  width: 100%;
  top: 0;
  left: 0;
  background-color: rgba(0, 0, 0, 0.65);
  z-index: 100;
}

.page-container {
  position: relative;
  width: 100%;
  height: 100%;
}
</style>