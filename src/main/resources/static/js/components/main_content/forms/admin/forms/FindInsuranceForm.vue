<template>
  <div class="find-user-form block-column">
    <div class="block-row block-row-items-margin">
      <Input ref="ownerSecondName" inputClass="d-input-size" placeholder="По фамилии владельца"/>
      <Input ref="ownerFirstName" inputClass="d-input-size" placeholder="По имени владельца"/>
      <Input ref="ownerPatronymic" inputClass="d-input-size" placeholder="По отчеству владельца"/>
    </div>
    <div class="block-row block-row-items-margin">
      <Input ref="winNumber" inputClass="d-input-size" placeholder="По win номеру"/>
      <Input ref="registrationNumber" inputClass="d-input-size" placeholder="По регистрационному номеру"/>
    </div>
    <div class="block-row block-row-items-margin">
      <Input ref="insuranceId" inputClass="d-input-size" :keydown="numbersKeydown" style="max-width: 135px"
             maxlength="10" placeholder="По id"/>
      <Input ref="userId" inputClass="d-input-size" :keydown="numbersKeydown" placeholder="По id пользователя"/>
      <Input ref="insuranceDataId" inputClass="d-input-size" :keydown="numbersKeydown"
             placeholder="По id страховых данных"/>
      <Input ref="passportId" inputClass="d-input-size" :keydown="numbersKeydown" placeholder="По полному ID паспорта"/>
    </div>
    <div class="block-row center">
      <RadioButtonGroup ref="insuranceStatus"
                        :small="true" title="Статус страховки"
                        :items="insuranceStatusList"
                        :selectIndex="0"/>
    </div>
    <Input ref="phone" :keydown="phoneKeydown" :maxlength="17" inputClass="d-input-size"
           placeholder="По номеру телефона"/>
    <div class="block-row block-row-items-margin">
      <Button :click="find" class="btn green-btn d-btn-max-size">Найти</Button>
      <Button :click="clear" class="btn blue-btn d-btn-max-size">Очистить</Button>
    </div>
  </div>
</template>

<script>
import Input from "../../../../items/Input.vue";
import Checkbox from "../../../../items/Checkbox.vue";
import Button from "../../../../items/Button.vue";
import RadioButtonGroup from "../../../../items/RadioButtonGroup.vue";
import {checkNumbersInputUpdate, checkPhoneInputUpdate} from "../../../../../api/Util.js";

export default {
  components: {RadioButtonGroup, Button, Checkbox, Input},
  props: ['updateList', 'setInfoText'],
  data() {
    return {
      inputList: undefined,
      numbersKeydown: checkNumbersInputUpdate,
      phoneKeydown: checkPhoneInputUpdate,
      insuranceStatusList: [
        {
          name: '❌',
          status: 'NONE',
        },
        {
          name: 'Ожидает оплату',
          status: 'AWAITING_PAYMENT',
        },
        {
          name: 'Действует',
          status: 'VALID',
        },
        {
          name: 'Оплата просрочена',
          status: 'PAYMENT_OVERDUE',
        },
        {
          name: 'Вышел срок',
          status: 'END',
        }
      ]
    }
  },
  methods: {
    clear() {
      this.inputList.forEach(el => el.setText(''))
      this.$refs.isAdmin.selected = false
    },
    getSelectedInsuranceStatus() {
      const status = this.$refs.insuranceStatus.selected.status
      if (!status || status === 'NONE')
        return undefined
      return status
    },
    find() {
      if (this.test()) {
        this.$adminApi.insuranceFilter(
            this.$refs.insuranceId.getTextU(),
            this.$refs.insuranceDataId.getTextU(),
            this.$refs.userId.getTextU(),
            this.$refs.ownerFirstName.getTextU(),
            this.$refs.ownerSecondName.getTextU(),
            this.$refs.ownerPatronymic.getTextU(),
            this.$refs.winNumber.getTextU(),
            this.$refs.registrationNumber.getTextU(),
            this.$refs.passportId.getTextU(),
            this.$refs.phone.getTextU(),
            this.getSelectedInsuranceStatus()
        ).then(
            (ok) => {
              this.updateList(ok.data);
              this.setInfoText('Найдено элементов: ' + ok.data.length)
            },
            (err) => {
              console.log(err.response);
            }
        )
      }
    },
    test() {
      let result = false
      this.inputList.forEach(el => result = result || el.silentTest())
      result = result || this.getSelectedInsuranceStatus() !== undefined

      if (!result)
        this.setInfoText('Должно быть заполнено хотя бы одно поле')

      return result
    }
  },
  mounted() {
    this.inputList = [
      this.$refs.insuranceId,
      this.$refs.insuranceDataId,
      this.$refs.userId,
      this.$refs.ownerFirstName,
      this.$refs.ownerSecondName,
      this.$refs.ownerPatronymic,
      this.$refs.winNumber,
      this.$refs.registrationNumber,
      this.$refs.passportId,
      this.$refs.phone
    ]
  }
}
</script>

<style scoped>
.find-user-form > * {
  margin-bottom: 20px;
}
</style>