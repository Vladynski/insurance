<template>
  <div class="block-column insurance-data-form" style="height: 250px">
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
    <div class="block-row insurance-data-body-container">
      <div class="block-column insurance-data-body">
        <InsuranceDataKeyValue name="Фамилия собственника">{{ ownerSecondName }}</InsuranceDataKeyValue>
        <InsuranceDataKeyValue name="Имя собственника">{{ ownerFirstName }}</InsuranceDataKeyValue>
        <InsuranceDataKeyValue name="Отчество собственника">{{ ownerPatronymic }}</InsuranceDataKeyValue>
        <InsuranceDataKeyValue name="Статус страховки">{{ insuranceStatus }}</InsuranceDataKeyValue>
      </div>
      <div class="block-column insurance-data-body">
        <InsuranceDataKeyValue name="Статус оплаты">{{ paymentStatus }}</InsuranceDataKeyValue>
        <InsuranceDataKeyValue name="Внесено денег">{{ paid }} BYN</InsuranceDataKeyValue>
        <InsuranceDataKeyValue name="Крайний срок оплаты">{{ paymentDeadline }}</InsuranceDataKeyValue>
        <InsuranceDataKeyValue v-if="timer" name="Осталось времени">{{ leftTime }}</InsuranceDataKeyValue>
      </div>
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
  </div>
</template>

<script>
import InsuranceDataKeyValue from "./InsuranceDataNameValue.vue";
import {div} from '../../../../api/Util.js'

export default {
  components: {InsuranceDataKeyValue},
  props: ["insurance"],
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
      timer: undefined,
      secondsLeft: undefined,
      leftTime: undefined
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
    }
  },
  computed: {
    insuranceStatus() {
      if (this.insurance.status === 'AWAITING_PAYMENT') {
        return 'Ожидает оплату'
      } else if (this.insurance.status === 'VALID') {
        return 'Действует'
      } else if (this.insurance.status === 'PAYMENT_OVERDUE') {
        return 'Оплата просрочена'
      } else if (this.insurance.status === 'END') {
        return 'Вышел срок'
      } else {
        return 'Неопознанный статус'
      }
    },
    paymentStatus() {
      if (this.insurance.payment.status === 'WAIT') {
        return 'Ожидает оплату'
      } else if (this.insurance.payment.status === 'PAID') {
        return 'Оплачено'
      } else if (this.insurance.payment.status === 'OVERDUE') {
        return 'Просрочена'
      } else {
        return 'Неопознанный статус'
      }
    }
  },
  mounted() {
    this.secondsLeft = new Date(this.insurance.payment.deadline).getTime() - new Date().getTime()
    if (this.secondsLeft > 0) {
      this.secondsLeft = Math.floor(this.secondsLeft / 1000)
      if (!this.timer) {
        this.timer = setInterval(() => {
          this.secondsLeft--
          this.setTime(this.secondsLeft)
        }, 1000)
      }
    }
  }
}
</script>

<style scoped>
.insurance-data-form {
  border: 5px solid #ffffff;
  /*background: white;*/
  background: linear-gradient(#462446, #723d66, #643564, #754269);
  font-size: 20px;
  font-weight: normal;
  max-height: 250px;
  border-radius: 5px;
  box-shadow: #888888 -1px 2px 5px;
}

.insurance-data-form:hover {
  transform: scale(1.02, 1.02);
}

.header {
  max-height: 40px;
}

.header-content {
  padding-left: 10px;
  padding-right: 10px;
  justify-content: left;
}

.insurance-data-body {
  padding-left: 10px;
  padding-right: 10px;
  justify-content: flex-start;
  background-color: transparent;
}

.insurance-data-body-container {
  background: linear-gradient(45grad, #ffdeff, #dac9ff);
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
</style>