<template>
  <div class="block-column insurance-data-form form-can-payment">
    <div class="block-row header">
      <div class="block-row header-content">
        <div class="header-text header-key">
          WIN номер:
        </div>
        <div class="header-text header-value">
          {{ winNumber }}
        </div>
      </div>
      <div class="block-row header-content">
        <div class="header-text header-key">
          Регистрационный номер:
        </div>
        <div class="header-text header-value">
          {{ registrationNumber }}
        </div>
      </div>
    </div>
    <div class="block-column insurance-data-body-container">
      <div class="block-row block-row-items-margin">
        <div class="block-column insurance-data-body">
          <InsuranceDataKeyValue name="Фамилия собственника">{{ ownerSecondName }}</InsuranceDataKeyValue>
          <InsuranceDataKeyValue name="Имя собственника">{{ ownerFirstName }}</InsuranceDataKeyValue>
          <InsuranceDataKeyValue name="Отчество собственника">{{ ownerPatronymic }}</InsuranceDataKeyValue>
          <InsuranceDataKeyValue name="Статус страховки">{{ computedInsuranceStatus }}</InsuranceDataKeyValue>
        </div>
        <div class="block-column insurance-data-body">
          <InsuranceDataKeyValue name="Статус оплаты">{{ computedPaymentStatus }}</InsuranceDataKeyValue>
          <InsuranceDataKeyValue name="Внесено денег">{{ paid }} BYN</InsuranceDataKeyValue>
          <InsuranceDataKeyValue name="Крайний срок оплаты">{{ paymentDeadline }}</InsuranceDataKeyValue>
          <InsuranceDataKeyValue v-if="timer" name="Осталось времени">{{ leftTime }}</InsuranceDataKeyValue>
        </div>
      </div>
      <RowList :items="variantNames"/>
    </div>
    <div class="block-row header">
      <div class="block-row header-content">
        <div class="header-text header-key">
          Действует до:
        </div>
        <div class="header-text header-value">
          {{ endTime }}
        </div>
      </div>
      <div class="block-row header-content">
        <div class="header-text header-key">
          Стоимость:
        </div>
        <div class="header-text header-value">
          {{ price }} BYN
        </div>
      </div>
    </div>
    <div v-if="canPay && paid === 0 && paymentStatus === 'WAIT'" class="paymentBanner block-column center">
      <Button class="btn green-btn d-btn-max-size pay-btn" :click="pay">Оплатить 💸</Button>
    </div>
  </div>
</template>

<script>
import InsuranceDataKeyValue from "./InsuranceDataNameValue.vue";
import {div} from '../../../../api/Util.js'
import RowList from "../../../items/RowList.vue";
import Button from "../../../items/Button.vue";

export default {
  components: {Button, RowList, InsuranceDataKeyValue},
  props: ['insurance', 'canPay', 'updateInsuranceStatus'],
  data() {
    return {
      registrationNumber: this.insurance.registration_number,
      ownerFirstName: this.insurance.owner_first_name,
      ownerSecondName: this.insurance.owner_second_name,
      ownerPatronymic: this.insurance.owner_patronymic,
      winNumber: this.insurance.win_number,
      paymentDeadline: this.insurance.payment.deadline,
      price: this.insurance.payment.sum,
      paid: this.insurance.payment.paid_sum,
      endTime: this.insurance.end_time,
      paymentStatus: this.insurance.payment.status,
      insuranceStatus: this.insurance.status,
      timer: undefined,
      secondsLeft: undefined,
      leftTime: undefined,
      variantNames: []
    }
  },
  methods: {
    setTime() {
      let sl = this.secondsLeft
      const s = sl % 60
      sl = div(sl, 60)
      const m = sl % 60
      sl = div(sl, 60)
      this.leftTime = `${sl < 10 ? '0' + sl : sl}:${m < 10 ? '0' + m : m}:${s < 10 ? '0' + s : s}`
    },
    pay() {
      this.$banner.showPayment('Оплата страховки', this.insurance.payment.id, this.insurance.payment.sum, () => {
        this.paid = this.price
        this.paymentStatus = 'PAID'
        this.insuranceStatus = 'VALID'
        this.updateInsuranceStatus(this.insurance.id, this.insuranceStatus)
        this.timer = undefined
      })
    }
  },
  computed: {
    computedInsuranceStatus() {
      if (this.insuranceStatus === 'AWAITING_PAYMENT') {
        return 'Ожидает оплату'
      } else if (this.insuranceStatus === 'VALID') {
        return 'Действует'
      } else if (this.insuranceStatus === 'PAYMENT_OVERDUE') {
        this.endTime = '-'
        return 'Оплата просрочена'
      } else if (this.insuranceStatus === 'END') {
        return 'Вышел срок'
      } else {
        return 'Неопознанный статус'
      }
    },
    computedPaymentStatus() {
      if (this.paymentStatus === 'WAIT') {
        return 'Ожидает оплату'
      } else if (this.paymentStatus === 'PAID') {
        return 'Оплачено'
      } else if (this.paymentStatus === 'OVERDUE') {
        return 'Просрочена'
      } else {
        return 'Неопознанный статус'
      }
    }
  },
  beforeMount() {
    this.$api.getSelectionNamesByIds(this.insurance.variants, this.variantNames)
  },
  mounted() {
    this.secondsLeft = new Date(this.insurance.payment.deadline).getTime() - new Date().getTime()
    if (this.secondsLeft > 0 && this.insurance.payment.status === 'WAIT') {
      this.secondsLeft = Math.floor(this.secondsLeft / 1000)
      if (!this.timer) {
        this.timer = setInterval(() => {
          this.secondsLeft--
          if (this.secondsLeft === 0) {
            clearInterval(this.timer)
            this.insuranceStatus = 'PAYMENT_OVERDUE'
            this.paymentStatus = 'OVERDUE'
            this.timer = undefined
          } else
            this.setTime(this.secondsLeft)
        }, 1000)
      }
    }
  }
}
</script>

<style scoped>
.insurance-data-form {
  position: relative;
  border: 5px solid #ffffff;
  height: auto;
  background: linear-gradient(#462446, #723d66, #643564, #754269);
  font-size: 20px;
  font-weight: normal;
  border-radius: 5px;
  box-shadow: #888888 -1px 2px 5px;
}

.insurance-data-form:hover {
  transform: scale(1.02, 1.02);
}

.header {
  height: 40px;
  max-height: 40px;
}

.header-content {
  padding-left: 10px;
  padding-right: 10px;
  justify-content: left;
}

.insurance-data-body {
  justify-content: flex-start;
  background-color: transparent;
}

.insurance-data-body-container {
  background: linear-gradient(45grad, #ffdeff, #dac9ff);
  padding: 5px 15px;
}

.header-text {
  color: white;
}

.header-key {
  padding-left: 5px;
  padding-right: 5px;
  font-weight: bold;
}

.header-value {
  padding-left: 5px;
  padding-right: 5px;
}

.form-can-payment:hover > .paymentBanner {
  max-height: 500px;
}

.paymentBanner {
  transition-duration: 300ms;
  position: absolute;
  top: 0;
  left: 0;
  background-color: rgba(0, 0, 0, 0.5);
  width: 100%;
  height: 100%;
  max-height: 0;
  z-index: 3;
  overflow: hidden;
}

.pay-btn {
  width: 75%;
}
</style>