<template>
  <div class="block-column admin-user-card">
    <div class="block-row admin-user-card-title" style="justify-content: left; position: relative">
      <div class="admin-user-card-title-id">
        ID: {{ userdata.id }}
      </div>
      <div class="admin-user-card-title-username">
        {{ userdata.second_name + ' ' + userdata.first_name + ' ' + userdata.patronymic }}
      </div>
    </div>
    <PageSlider ref="pageSlider" :hideTitle="true">
      <Slide ref="infoSlide"
             :pageSliderGetter="getPageSlider"
             :defView="true"
             :noPadding="true"
             :init="fillingFields">
        <div class="block-row admin-user-card-body">
          <div class="block-column admin-user-card-data">
            <Input ref="email"
                   type="email"
                   placeholder="Email"
                   labelColor="#444444"
                   :value="userdata.email"
                   :changeListener="true"/>
            <Input labelColor="#444444" :editable="false" placeholder="Дата регистрации"
                   :value="userdata.registration_date"/>
            <RadioButtonGroup ref="role"
                              :small="true" title="Роль" :items="userRoleItems"
                              :selectIndex="userdata.roles.indexOf('ADMIN') > -1 ? 0 : 1"
                              :changeListener="true"/>
            <Input ref="phone"
                   type="phone"
                   placeholder="Номер телефона"
                   labelColor="#444444"
                   :value="getFormatPhoneNumber(userdata.insurance.phone)"
                   :changeListener="true"
                   :editable="hasInsuranceData"
                   :keydown="checkPhoneInputUpdate"
                   maxlength="17"/>
            <Input ref="passportId"
                   placeholder="ID паспорта"
                   labelColor="#444444"
                   :value="userdata.insurance.passport_id"
                   :editable="hasInsuranceData"
                   maxlength="19" :changeListener="true"/>
            <InsuranceDataStatus ref="insuranceDataRbg" :hasInsuranceData="hasInsuranceData"
                                 :insuranceStatus="userdata.insurance.status"/>
          </div>
          <hr class="v-separator">
          <div class="block-column center admin-user-card-data">
            <Input ref="secondName"
                   placeholder="Фамилия"
                   labelColor="#444444"
                   :value="userdata.second_name"
                   :changeListener="true"
                   :updateStartValueListener="newVal => userdata.second_name = newVal"/>
            <Input ref="firstName"
                   placeholder="Имя"
                   labelColor="#444444"
                   :value="userdata.first_name"
                   :changeListener="true"
                   :updateStartValueListener="newVal => userdata.first_name = newVal"/>
            <Input ref="patronymic"
                   placeholder="Отчество"
                   labelColor="#444444"
                   :value="userdata.patronymic"
                   :changeListener="true"
                   :updateStartValueListener="newVal => userdata.patronymic = newVal"/>
            <div class="block-row block-row-items-margin" style="height: auto;">
              <Button :click="toSendMailSlide" class="btn green-btn d-btn-max-size" title="Отправить письмо">📧
              </Button>
              <Button
                  :click="showPhoto"
                  :startBlock="userdata.insurance.status === 'NONE'" class="btn green-btn d-btn-max-size"
                  title="Посмотреть фото паспорта">📷️
              </Button>
            </div>
            <Button :click="editUserData" class="btn green-btn d-btn-max-size">Применить изменения
            </Button>
          </div>
        </div>
      </Slide>
      <Slide ref="sendMailSlide"
             :pageSliderGetter="getPageSlider"
             :defView="false"
             :noPadding="true">
        <div class="admin-user-card-body">
          <SendMailForm :email="userdata.email" :userId="userdata.id"/>
        </div>
      </Slide>
    </PageSlider>
  </div>
</template>

<script>
import Input from "../../../../items/Input.vue";
import RadioButtonGroup from "../../../../items/RadioButtonGroup.vue";
import Checkbox from "../../../../items/Checkbox.vue";
import {checkPhoneInputUpdate, getFormatPhoneNumber} from "../../../../../api/Util";
import Button from "../../../../items/Button.vue";
import Slide from "../../../../items/slider/Slide.vue";
import PageSlider from "../../../../items/slider/PageSlider.vue";
import SendMailForm from "./SendMailForm.vue";
import InsuranceDataStatus from "./InsuranceDataStatus.vue";

export default {
  components: {SendMailForm, PageSlider, Slide, Button, Checkbox, Input, RadioButtonGroup, InsuranceDataStatus},
  props: ['userdata'],
  data() {
    return {
      fields: undefined,
      checkPhoneInputUpdate: checkPhoneInputUpdate,
      getFormatPhoneNumber: getFormatPhoneNumber,
      hasInsuranceData: this.userdata.insurance.status !== 'NONE',
      userRoleItems: [
        {
          name: 'ADMIN',
          isAdmin: true
        },
        {
          name: 'USER',
          isAdmin: false
        }
      ]
    }
  },
  methods: {
    showPhoto() {
      const photo = this.userdata.insurance.photo
      console.log(photo);
      this.$banner.showPhoto('sssss', photo.content, photo.type)
    },
    editUserData() {
      const data =
          this.$adminApi.editUserDataBuilder(this.userdata.id)
              .secondName(this.$refs.secondName.getNewValue())
              .firstName(this.$refs.firstName.getNewValue())
              .patronymic(this.$refs.patronymic.getNewValue())
              .email(this.$refs.email.getNewValue())
              .phone(this.$refs.phone.getNewValue())
              .passportId(this.$refs.passportId.getNewValue())
              .adminStatus(this.getNewRole())
              .insuranceDataStatus(this.getNewInsuranceStatus())
              .data

      this.$adminApi.editUserData(data).then(
          (ok) => {
            this.fields.forEach(el => el.updateStartValue())
          },
          (err) => {
            this.$api.errorHandler(err, undefined, () => this.$refs)
          }
      )
    },
    getNewRole() {
      const newValue = this.$refs.role.getNewValue();
      return newValue ? newValue.isAdmin : undefined
    },
    getNewInsuranceStatus() {
      const newValue = this.$refs.insuranceDataRbg.getNewValue();
      return newValue ? newValue.status : undefined
    },
    getPageSlider() {
      return this.$refs.pageSlider
    },
    toSendMailSlide() {
      this.getPageSlider().showSlide(1)
    },
    fillingFields() {
      this.fields = [
        this.$refs.secondName,
        this.$refs.firstName,
        this.$refs.patronymic,
        this.$refs.email,
        this.$refs.phone,
        this.$refs.passportId,
        this.$refs.role,
        this.$refs.insuranceDataRbg
      ]
    }
  },
  mounted() {
    this.$refs.sendMailSlide.simplePreview = true

    this.$refs.pageSlider.initSlides(
        [
          this.$refs.infoSlide,
          this.$refs.sendMailSlide
        ]
    )
  }
}
</script>

<style scoped>
.admin-user-card {
  background-color: #47D2E9;
  border-radius: 5px;
  height: auto;
  box-shadow: -1px 2px 5px #444444;
}

.admin-user-card-title {
  position: relative;
  padding: 10px 25px;
  background-color: #444444;
  border-radius: 5px 5px 0 0;
  height: auto;
  color: white;
}

.admin-user-card-title-id {
  position: absolute;
  top: 50%;
  left: 50px;
  transform: translate(-50%, -50%);
}

.admin-user-card-title-username {
  text-align: center;
  width: 100%;
}

.admin-user-card-body {
  overflow: hidden;
  background-color: #47D2E9;
  border-radius: 0 0 5px 5px;
  height: 340px;
  padding: 20px 25px;
  width: 100%;
}

.admin-user-card-body > *:not(:last-child) {
  margin-right: 15px;
}

.admin-user-card-data {
  width: 50%;
}

.admin-user-card-data > *:not(:last-child) {
  margin-bottom: 20px;
}

</style>