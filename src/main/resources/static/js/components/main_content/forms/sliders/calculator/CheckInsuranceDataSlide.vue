<template>
  <div class="base-form-data ">
    <div class="block-column">
      <div class="def-block-vertical-scrolling" style="width: 100%; max-height: 500px">
        <div class="block-column elements-margin" style="min-height: 500px">
          <div style="width: 100%">
            <Table :title="title" :data="getData()" :small="true"/>
          </div>
          <Input ref="firstName" lightDisable="true" inputClass="d-input-size" placeholder="Имя собственника" :editable="false"/>
          <Input ref="secondName" lightDisable="true" inputClass="d-input-size" placeholder="Фамилия собственника" :editable="false"/>
          <Input ref="patronymic" lightDisable="true" inputClass="d-input-size" placeholder="Отчество собственника" :editable="false"/>
          <Checkbox :editable="false" :defSelected="this.createInsuranceData.ownerItsMe">Собственником являюсь я
          </Checkbox>
          <Input ref="win" lightDisable="true" inputClass="d-input-size" placeholder="Идентификационный номер ТС (WIN)" :editable="false"/>
          <Input ref="registrationNumber" lightDisable="true" inputClass="d-input-size" placeholder="Регистрационный знак"
                 :editable="false"/>
        </div>
      </div>
      <div class="block-row block-row-right" style="height: auto; position:relative;">
        <Checkbox ref="checkDataBox">Данные верны</Checkbox>
      </div>
      <Button ref="nextButton" :startBlock="true" :click="nextSlide" class="simple-btn btn green-btn">Продолжить
      </Button>
    </div>
  </div>
</template>

<script>
import Checkbox from "../../../../items/Checkbox.vue";
import Input from "../../../../items/Input.vue";
import Button from "../../../../items/Button.vue";
import Table from "../../../../items/Table.vue";

export default {
  components: {Checkbox, Button, Input, Table},
  props: ['createInsuranceData', 'nextSlide'],
  data() {
    return {
      title: ['Название варианта', 'Влияние на стоимость'],
    }
  },
  methods: {
    getData() {
      const data = []
      this.createInsuranceData.metadata.selectedVariants.forEach(
          el => {
            data.push([el.name, el.coefficient])
          }
      )
      data.push(['Срок', 'Полгода'])
      if (this.createInsuranceData.metadata.resultSum)
        data.push(['Итоговая сумма', this.createInsuranceData.metadata.resultSum + ' BYN'])
      return data
    }
  },
  mounted() {
    this.$refs.firstName.setTextAndReadonly(this.createInsuranceData.ownerFirstName)
    this.$refs.secondName.setTextAndReadonly(this.createInsuranceData.ownerSecondName)
    this.$refs.patronymic.setTextAndReadonly(this.createInsuranceData.ownerPatronymic)
    this.$refs.win.setTextAndReadonly(this.createInsuranceData.winNumber)
    this.$refs.registrationNumber.setTextAndReadonly(this.createInsuranceData.registrationNumber)
    this.$refs.checkDataBox.setButton(this.$refs.nextButton)
  }
}
</script>

<style scoped>
@import "../../../../../vue_css/base_form.css";
@import "../../../../../vue_css/default_size.css";

.elements-margin > * {
  margin-bottom: 20px;
}
</style>