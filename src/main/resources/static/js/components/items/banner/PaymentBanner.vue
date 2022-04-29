<template>
  <div :class="['payment block-column', hideMainContent ? 'hide-main-content' : '']">
    <div v-show="!mainContent" class="block-column center">
      <InfoFrame ref="infoFrame"/>
    </div>
    <template v-if="mainContent">
      <div class="payment-title">{{ payment.title }}</div>
      <div class="block-row">
      <span>
        К оплате: {{ payment.sum }} BYN
      </span>
      </div>
      <div class="block-row block-row-items-margin card-number-inputs">
        <span>Номер карты: </span>
        <Input ref="first" :keydown="onlyNumbers" maxlength="4" inputClass="d-input-size"/>
        <Input ref="second" :keydown="onlyNumbers" maxlength="4" inputClass="d-input-size"/>
        <Input ref="third" :keydown="onlyNumbers" maxlength="4" inputClass="d-input-size"/>
        <Input ref="fourth" :keydown="onlyNumbers" maxlength="4" inputClass="d-input-size"/>
      </div>
      <div class="block-row block-row-items-margin card-number-inputs block-row-right">
        <span>CVV: </span>
        <Input ref="cvv" :keydown="onlyNumbers" maxlength="3" inputClass="d-input-size"/>
      </div>
      <div class="block-row block-row-items-margin">
        <Button :click="hide" class="btn blue-btn d-btn-max-size">Отмена</Button>
        <Button :click="pay" class="btn green-btn d-btn-max-size">Оплатить</Button>
      </div>
    </template>
    <template v-else>
      <Button :click="resultAction" class="btn green-btn d-btn-max-size">Закрыть</Button>
    </template>
    <div class="model-label">
      макет
    </div>
  </div>
</template>

<script>
import Input from "../Input.vue";
import {checkNumbersInputUpdate} from "../../../api/Util.js";
import Button from "../Button.vue";
import InfoFrame from "../InfoFrame.vue";

export default {
  components: {InfoFrame, Button, Input},
  props: ['banner', 'payment'],
  data() {
    return {
      onlyNumbers: checkNumbersInputUpdate,
      inputList: [],
      mainContent: true,
      hideMainContent: false,
      resultAction: () => {
      }
    }
  },
  methods: {
    hide() {
      this.banner.hide()
    },
    pay() {
      if (this.test() &&
          this.hasNNumbers(4, this.$refs.first) &&
          this.hasNNumbers(4, this.$refs.second) &&
          this.hasNNumbers(4, this.$refs.third) &&
          this.hasNNumbers(4, this.$refs.fourth) &&
          this.hasNNumbers(3, this.$refs.cvv)) {
        this.$api.pay(this.payment.paymentId).then((ok) => {
          this.hideMainContent = true
          setTimeout(() => {
            this.mainContent = false
            this.$refs.infoFrame.setDefaultText('Оплата прошла успешно')
            this.$refs.infoFrame.setConstant(true, 'OK')
            this.hideMainContent = false
            this.resultAction = () => this.banner.accept()
          }, 500);
        }, (err) => {
          this.hideMainContent = true
          setTimeout(() => {
            this.mainContent = false
            this.$refs.infoFrame.setDefaultText(err.response.data.message)
            this.$refs.infoFrame.setConstant(true, 'error')
            this.hideMainContent = false
            this.resultAction = () => this.banner.hide()
          }, 500);
        })
      }
    },
    test() {
      let result = true
      for (let el of this.inputList)
        result = result && el.test()
      return result;
    },
    hasNNumbers(n, field) {
      if (field.getText().length !== n) {
        field.showError('Ц'.repeat(n))
        return false
      }
      return true
    }
  },
  mounted() {
    this.inputList = [
      this.$refs.first,
      this.$refs.second,
      this.$refs.third,
      this.$refs.fourth,
      this.$refs.cvv
    ]
  }
}
</script>

<style scoped>
.payment {
  position: absolute;
  top: 50%;
  left: 50%;
  border-radius: 5px;
  transform: translate(-50%, -50%);
  max-width: 1000px;
  width: 800px;
  height: 400px;
  background-color: white;
  padding: 2%;
  overflow: hidden;
  transition-duration: 300ms;
}

.card-number-inputs *:not(:first-child) {
  width: 100px;
}

.payment span {
  font-size: 26px;
}

.payment-title {
  width: 100%;
  text-align: center;
  font-size: 30px;
  font-weight: bold;
}

.hide-main-content * {
  opacity: 0;
}

.model-label {
  position: absolute;
  left: 0;
  bottom: 0;
  padding: 3px;
  background-color: rgba(0, 0, 0, 0.3);
  font-size: 12px;
  color: black;
}
</style>