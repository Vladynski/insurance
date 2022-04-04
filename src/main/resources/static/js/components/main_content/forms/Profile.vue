<template>
  <div class="profile-container">
    <div class="base-form-title def-block" style="height: auto">
      <div>Иван Иваныч Пупкин</div>
    </div>
    <div class="block-column def-block">
      <span class="base-title">Информация о пользователе</span>
      <div class="block-row">
        <div class="card-container">
          <Card>
            <template #title>
              Email
            </template>
            <template #body>
              {{ userdata.email }}
            </template>
          </Card>
          <Card>
            <template #title>
              Дата регистрации
            </template>
            <template #body>
              {{ userdata.registration_date }}
            </template>
          </Card>
          <Card>
            <template #title>
              Оформлено страховок
            </template>
            <template #body>
              0
            </template>
          </Card>
        </div>
      </div>
    </div>
    <div class="block-column def-block">
      <span class="base-title">Страховая информация</span>
      <div v-if="insuranceNotExists" class="insurance-data" style="width: 55%">
        <InfoFrame ref="infoFrame" :defText="warningMessage"></InfoFrame>
        <Input ref="passportId" placeholder="Идентификационный номер паспорта" input-class="d-input-size"
               maxlength="19"/>
        <Input ref="phone" placeholder="Мобильный номер телефона" input-class="d-input-size" type="tel"
               :keydown="checkPhone" value="+375 " maxlength="17"/>
        <FileInput :choose="imgToBase64" id="passportPhoto"></FileInput>
        <div class="row-right">
          <Button :click="createInsuranceData" class="green-btn d-btn-max-size">Отправить</Button>
        </div>
      </div>
      <div v-else style="width: 100%; height: 100%" class="insurance-data">
        <div class="card-container">
          <Card>
            <template #title>
              Номер паспорта
            </template>
            <template #body>
              {{ userdata.insurance.passport_id }}
            </template>
          </Card>
          <Card>
            <template #title>
              Номер Телефона
            </template>
            <template #body>
              {{ userdata.insurance.phone }}
            </template>
          </Card>
          <Card>
            <template #title>
              Статус
            </template>
            <template #body>
              {{ userdata.insurance.status === 'WAIT_CONFIRMATION' ? 'Ожидает подтверждение (информация с решением придёт на эл. почту)' : 'Подтверждён' }}
            </template>
          </Card>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import InfoFrame from '../../items/InfoFrame.vue'
import Card from '../templates/Card.vue'
import Input from "../../items/Input.vue";
import Button from "../../items/Button.vue";
import FileInput from "../../items/FileInput.vue";
import {checkPhoneInputUpdate} from "../../../api/Util";

export default {
  //{{ userdata.second_name + ' ' + userdata.first_name + ' ' + userdata.patronymic }}
  props: ['outUserdata'],
  data() {
    return {
      imgBase64: undefined,
      insuranceNotExists: false,
      userdata: undefined,
      warningMessage: 'У вас нет возможности оформить страховку, т.к. вы не передали необходимые для этого данные: идентификационный номер паспорта, номер телефона, фото паспорта'
    }
  },
  methods: {
    checkPhone(event) {
      checkPhoneInputUpdate(event)
    },
    imgToBase64(file) {
      const reader = new FileReader()

      const setter = (value) => this.imgBase64 = value

      reader.onloadend = () => {
        setter(reader.result.substring(reader.result.indexOf('64,') + 3))
      }

      reader.readAsDataURL(file);
    },
    createInsuranceData() {
      this.$api.updateInsuranceData(
          this.$refs.phone.getText().replaceAll(/\D/g, ''),
          this.$refs.passportId.getText().replaceAll(/\\s/g, ''),
          this.imgBase64,
          'image/png').then(
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
            if (this.userdata.insurance.status !== 'NONE')
              this.insuranceNotExists = false
          }
      )
    }
  },
  components: {Button, Input, Card, InfoFrame, FileInput},
  beforeMount() {
    this.userdata = this.outUserdata
    if (this.userdata.insurance.status === 'NONE')
      this.insuranceNotExists = true
  },
  mounted() {
    if (this.userdata.insurance.status === 'NONE') {
      this.$refs.infoFrame.showWarning(this.warningMessage)
      this.$refs.infoFrame.setConstant(true)
    }
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
  /*background-color: red;*/
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
  height: 100%;
  padding: 30px;
}

.title-row > div {
  width: auto;
  text-align: center;
}

.insurance-data {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.insurance-data > div {
  margin-bottom: 20px;
}
</style>