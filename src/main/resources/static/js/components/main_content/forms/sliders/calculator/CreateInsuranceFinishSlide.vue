<template>
  <div class="block-column d-bottom-margin" style="height: auto">
    <InfoFrame ref="infoFrame"/>
    <div class="d-text-size">
      <p class="paragraph">На Вашу почту было отправлено письмо с кодом, для подтверждения личности введите полученный
        код в поле ниже.</p>
      <p class="paragraph">После оформления заявки вам будет отправлено письмо на электронную почту. Вам необходимо
        внести первый платёж в срок, указанный в договоре, отправленном на почту. <b>В случае просрочки первого платежа
          сделка становится <u>неактивной</u>.</b></p>
    </div>
    <Input ref="verifyIdentity" inputClass="d-input-size" placeholder="Код отправленный на почту"
           style="margin-bottom: 2px"/>
    <SendConfirmCode ref="sendCodeForm"/>
    <div class="block-row block-row-right" style="height: auto; position:relative;">
      <span class="d-text-size">Итоговая стоимость <b>полугодовой</b> страховки: {{ yearSum }} BYN</span>
    </div>
    <Button ref="drawUpButton" class="btn green-btn d-btn-max-size" :click="createInsuranceAction">Оформить</Button>
  </div>
</template>

<script>
import Checkbox from "../../../../items/Checkbox.vue";
import Button from "../../../../items/Button.vue";
import Select from "../../../../items/Select.vue";
import Input from "../../../../items/Input.vue";
import InfoFrame from "../../../../items/InfoFrame.vue";
import SendConfirmCode from "../../../../items/SendConfirmCode.vue";
import {removeSpaces} from "../../../../../api/Util";

export default {
  props: ['nextSlide', 'createInsuranceData'],
  components: {SendConfirmCode, Checkbox, Button, Select, Input, InfoFrame},
  data() {
    return {
      resultSum: undefined,
      yearSum: 0,
    }
  },
  methods: {
    createInsuranceAction() {
      if (this.$refs.verifyIdentity.test()) {
        const code = removeSpaces(this.$refs.verifyIdentity.getText())
        if (code.length === 32) {
          this.$api.createInsurance(this.createInsuranceData, code).then(
              (ok) => {
                this.createInsuranceData.clear()
                this.$refs.infoFrame.showOk('Страховка оформлена. Перейдите в раздел Страховки чтобы посмотреть подробности. Так же вам на почту было выслано письмо с условиями сделки')
              }, (err) => {
                this.$api.errorHandler(err, () => this.$refs.infoFrame)
              }
          )
        } else {
          this.$refs.verifyIdentity.showError('Неверный формат кода')
        }
      }
    }
  },
  mounted() {
    this.yearSum = this.createInsuranceData.metadata.resultSum
    this.$refs.sendCodeForm.sendCode()
  }
}
</script>

<style scoped>
@import "../../../../../vue_css/base_form.css";
@import "../../../../../vue_css/default_size.css";
</style>