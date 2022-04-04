<template>
  <div class="block-column insuranceDataForm" style="height: auto">
    <InfoFrame ref="infoFrame"/>
    <Input ref="ownerFirstName" inputClass="d-input-size" placeholder="Имя собственника"
           :value="this.createInsuranceData.ownerFirstName"/>
    <Input ref="ownerSecondName" inputClass="d-input-size" placeholder="Фамилия собственника"
           :value="this.createInsuranceData.ownerSecondName"/>
    <Input ref="ownerPatronymic" inputClass="d-input-size" placeholder="Отчество собственника"
           :value="this.createInsuranceData.ownerPatronymic"/>
    <Checkbox ref="creatorIsOwner" :select="ownerItsMe" :unselect="ownerIsNotMe"
              :defSelected="this.createInsuranceData.ownerItsMe">Собственником являюсь я
    </Checkbox>
    <Input ref="winNumber" inputClass="d-input-size" placeholder="Идентификационный номер ТС (WIN)"
           :value="this.createInsuranceData.winNumber"/>
    <Input ref="registrationNumber" inputClass="d-input-size" placeholder="Регистрационный знак"
           :value="this.createInsuranceData.registrationNumber"/>
    <Button ref="nextButton" :click="collectData" class="simple-btn btn green-btn">Продолжить</Button>
  </div>
</template>

<script>
import Input from "../../../../items/Input.vue";
import Checkbox from "../../../../items/Checkbox.vue";
import Button from "../../../../items/Button.vue";
import InfoFrame from "../../../../items/InfoFrame.vue";

export default {
  components: {InfoFrame, Button, Checkbox, Input},
  props: ['outUserdata', 'nextSlide', 'createInsuranceData'],
  data() {
    return {
      fields: undefined
    }
  },
  methods: {
    ownerItsMe() {
      if (this.$refs.ownerFirstName) {
        this.$refs.ownerFirstName.setTextAndReadonly(this.outUserdata.first_name)
        this.$refs.ownerSecondName.setTextAndReadonly(this.outUserdata.second_name)
        this.$refs.ownerPatronymic.setTextAndReadonly(this.outUserdata.patronymic)
      }
    },
    ownerIsNotMe() {
      if (this.$refs.ownerFirstName) {
        this.$refs.ownerFirstName.clearAndWritable()
        this.$refs.ownerSecondName.clearAndWritable()
        this.$refs.ownerPatronymic.clearAndWritable()
      }
    },
    collectData() {
      if (this.testFields()) {
        this.createInsuranceData.ownerItsMe = this.$refs.creatorIsOwner.selected
        this.createInsuranceData.ownerFirstName = this.$refs.ownerFirstName.getText()
        this.createInsuranceData.ownerSecondName = this.$refs.ownerSecondName.getText()
        this.createInsuranceData.ownerPatronymic = this.$refs.ownerPatronymic.getText()
        this.createInsuranceData.winNumber = this.$refs.winNumber.getText()
        this.createInsuranceData.registrationNumber = this.$refs.registrationNumber.getText()

        this.$refs.nextButton.block()
        this.$api.validInsuranceData(this.createInsuranceData).then(
            (ok) => {
              this.$refs.nextButton.unblock()
              this.nextSlide()
            },
            (err) => {
              console.log(err.response);
              console.log(this.createInsuranceData.getInsuranceDataForTransfer());
              this.$api.errorHandler(err,
                  () => this.$refs.infoFrame,
                  () => this.$refs)
              this.$refs.nextButton.unblock()
            }
        )
      }
    },
    testFields() {
      let ok = true
      this.fields.forEach(el => {
        if (!el.test()) ok = false
      })
      return ok;
    }
  },
  mounted() {
    this.fields = [
      this.$refs.ownerFirstName,
      this.$refs.ownerSecondName,
      this.$refs.ownerPatronymic,
      this.$refs.winNumber,
      this.$refs.registrationNumber
    ]
    if (this.createInsuranceData.ownerItsMe) {
      this.ownerItsMe()
    }
  }
}
</script>

<style scoped>
@import "../../../../../vue_css/base_form.css";
@import "../../../../../vue_css/default_size.css";

.insuranceDataForm {

}

.insuranceDataForm > * {
  margin-top: 20px;
}
</style>