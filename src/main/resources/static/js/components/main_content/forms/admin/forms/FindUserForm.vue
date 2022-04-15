<template>
  <div class="find-user-form block-column">
    <div class="block-row block-row-items-margin">
      <Input ref="secondName" inputClass="d-input-size" placeholder="По фамилии"/>
      <Input ref="firstName" inputClass="d-input-size" placeholder="По имени"/>
      <Input ref="patronymic" inputClass="d-input-size" placeholder="По отчеству"/>
    </div>
    <div class="block-row block-row-items-margin">
      <Input ref="email" inputClass="d-input-size" placeholder="По Email'у"/>
      <Input ref="phone" inputClass="d-input-size" placeholder="По номеру телефона"/>
    </div>
    <div class="block-row block-row-items-margin">
      <Input ref="userId" inputClass="d-input-size" placeholder="По id"/>
      <Input ref="passportId" inputClass="d-input-size" placeholder="По ID паспорта (полному)"/>
      <Input ref="insuranceDataId" inputClass="d-input-size" placeholder="По id страховых данных"/>
    </div>
    <Checkbox ref="isAdmin">Только администраторы</Checkbox>
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

export default {
  components: {Button, Checkbox, Input},
  props: ['updateList', 'setInfoText'],
  data() {
    return {
      inputList: undefined
    }
  },
  methods: {
    clear() {
      this.inputList.forEach(el => el.setText(''))
      this.$refs.isAdmin.selected = false
    },
    find() {
      if (this.test()) {
        this.$adminApi.userFilter(
            this.$refs.userId.getTextU(),
            this.$refs.firstName.getTextU(),
            this.$refs.secondName.getTextU(),
            this.$refs.patronymic.getTextU(),
            this.$refs.email.getTextU(),
            this.$refs.passportId.getTextU(),
            this.$refs.insuranceDataId.getTextU(),
            this.$refs.isAdmin.selected
        ).then(
            (ok) => {
              this.updateList(ok.data);
              this.setInfoText('Найдено элементов: ' + ok.data.length)
            }
        )
      }
    },
    test() {
      let result = false
      this.inputList.forEach(el => result = result || el.silentTest())

      if (!result)
        this.setInfoText('Должно быть заполнено хотя бы одно поле')

      return result
    }
  },
  mounted() {
    this.inputList = [
      this.$refs.firstName,
      this.$refs.secondName,
      this.$refs.patronymic,
      this.$refs.email,
      this.$refs.passportId,
      this.$refs.userId,
      this.$refs.insuranceDataId
    ]
  }
}
</script>

<style scoped>
.find-user-form > * {
  margin-bottom: 20px;
}
</style>