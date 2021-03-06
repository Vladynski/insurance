<template>
  <div class="profile-container">
    <div class="base-form-title def-block title-background-container profile-title-background">
      {{ second_name + ' ' + first_name + ' ' + patronymic }}
    </div>
    <div class="block-column def-block " style="margin-bottom: 25px; overflow: hidden">
      <span class="base-title">Информация о пользователе</span>
      <div class="block-column" style="position:relative;">
        <div class="block-row">
          <div class="card-container">
            <Card title="Email"
                  :body="email"
                  :editable="true"
                  :editClick="editEmail"/>
            <Card title="Дата регистрации"
                  :body="registration_date"/>
            <Card ref="insuranceCount"
                  title="Оформлено страховок"
                  :body="insurance_count"/>
          </div>
        </div>
        <ChangePassword/>
      </div>
    </div>
    <div class="block-column def-block">
      <span class="base-title">Страховая информация</span>
      <div v-if="insuranceNotExists" class="insurance-user-data" style="width: 55%">
        <InfoFrame ref="infoFrame" :defText="warningMessage"></InfoFrame>
        <Input ref="passportId" placeholder="Идентификационный номер паспорта" input-class="d-input-size"
               maxlength="19"/>
        <Input ref="phone" placeholder="Мобильный номер телефона" input-class="d-input-size" type="tel"
               :keydown="checkPhone" value="+375 " maxlength="17"/>
        <FileInput :choose="processFile" accept=".png, .jpg, .jpeg" id="passportPhoto"></FileInput>
        <div class="row-right">
          <Button :click="createInsuranceData" class="green-btn d-btn-max-size">Отправить</Button>
        </div>
      </div>
      <div v-else style="width: 100%; height: 100%" class="insurance-user-data">
        <div class="card-container">
          <Card title="Номер паспорта"
                :body="userdata.insurance.passport_id">
          </Card>
          <Card title="Номер телефона"
                :body="formatPhone(userdata.insurance.phone)"
                :keydown="checkPhone"
                :editable="true"
                :editClick="editPhone"
                :maxlength="17"/>
          <Card title="Статус"
                :body="userdata.insurance.status === 'WAIT_CONFIRMATION' ? 'Ожидает подтверждение (информация с решением придёт на эл. почту)' : 'Подтверждён'"/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import InfoFrame from '../../items/InfoFrame.vue'
import Card from '../../items/Card.vue'
import Input from "../../items/Input.vue";
import Button from "../../items/Button.vue";
import FileInput from "../../items/FileInput.vue";
import {checkPhoneInputUpdate, clearPhoneNumber, getFormatPhoneNumber} from "../../../api/Util.js";
import ChangePassword from "../../items/ChangePassword.vue";

export default {
  components: {ChangePassword, Button, Input, Card, InfoFrame, FileInput},
  props: ['outUserdata'],
  data() {
    return {
      second_name: this.outUserdata.second_name,
      first_name: this.outUserdata.first_name,
      patronymic: this.outUserdata.patronymic,
      email: this.outUserdata.email,
      registration_date: this.outUserdata.registration_date,
      insurance_count: this.outUserdata.insurance_count,

      imgBase64: undefined,
      imgContentFormat: undefined,
      insuranceNotExists: false,
      userdata: this.outUserdata,
      warningMessage: 'У вас нет возможности оформить страховку, т.к. вы не передали необходимые для этого данные: идентификационный номер паспорта, номер телефона, фото паспорта'
    }
  },
  methods: {
    checkPhone(event) {
      checkPhoneInputUpdate(event)
    },
    formatPhone(phoneNumber) {
      return getFormatPhoneNumber(phoneNumber)
    },
    processFile(file) {
      this.setContentType(file)

      const reader = new FileReader()
      const setter = (value) => this.imgBase64 = value
      reader.onloadend = () => {
        setter(reader.result.substring(reader.result.indexOf('64,') + 3))
      }
      reader.readAsDataURL(file);
    },
    setContentType(file) {
      const filename = file.name
      const extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase()
      this.imgContentFormat = 'image/' + extension
    },
    editPhone(newVal) {
      const password = prompt('Для выполнения действия необходимо ввести пароль')
      if (password !== null)
        return this.$api.editUserDataDto(password, undefined, clearPhoneNumber(newVal))
    },
    editEmail(newVal) {
      const password = prompt('Для выполнения действия необходимо ввести пароль')
      if (password !== null)
        return this.$api.editUserDataDto(password, newVal)
    },
    createInsuranceData() {
      this.$api.updateInsuranceData(
          clearPhoneNumber(this.$refs.phone.getText()),
          this.$refs.passportId.getText().replaceAll(/\\s/g, ''),
          this.imgBase64,
          this.imgContentFormat).then(
          (ok) => this.updateUserdata(),
          (err) => {
            this.$api.errorHandler(err, () => {
              return this.$refs.infoFrame
            }, () => {
              return this.$refs
            })
          }
      )
    },
    updateUserdata() {
      this.$api.getSelfData().then(
          (ok) => {
            this.userdata = ok.data
            this.$refs.insuranceCount.setContentBody(this.userdata.insurance_count)
            if (this.userdata.insurance.status !== 'NONE')
              this.insuranceNotExists = false
          }
      )
    }
  },
  beforeMount() {
    if (this.userdata.insurance.status === 'NONE')
      this.insuranceNotExists = true
  },
  mounted() {
    if (this.userdata.insurance.status === 'NONE') {
      this.$refs.infoFrame.showWarning(this.warningMessage)
      this.$refs.infoFrame.setConstant(true)
    }
    this.updateUserdata()
  },
}
</script>

<style scoped>
@import "../../../vue_css/base_form.css";
@import "../../../vue_css/default_size.css";

.profile-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
  width: 100%;
  height: 100%;
  padding: 10px;
}

.row-right {
  display: flex;
  justify-content: flex-end;
}

.card-container {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  width: 100%;
  height: 70%;
  padding: 30px;
}

.title-row > div {
  width: auto;
  text-align: center;
}

.insurance-user-data {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.insurance-user-data > div {
  margin-bottom: 20px;
}

.profile-title-background:after {
  background-image: url("../../pic/profile.png");
  background-color: rgba(255, 227, 156, 0.61);
}
</style>