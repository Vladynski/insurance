<template>
  <div class="base-form">
    <div class="base-form-title def-block title-background-container admin-panel-title-background">
      Панель администрирования
    </div>
    <RadioButtonGroup :small="true" title="Раздел" :items="items" :selectIndex="0" style="margin-bottom: 5px"/>
    <div v-show="adminUsers" class="base-form-data def-block">
      <AdminUsers/>
    </div>
    <div v-show="insuranceList" class="base-form-data def-block">
      <InsuranceAdminList/>
    </div>
    <div v-show="faqEditor" class="base-form-data def-block">
      <FaqEditor/>
    </div>
    <div v-show="userQuestions" class="base-form-data def-block">
      <UserQuestions/>
    </div>
    <div v-show="selections" class="base-form-data def-block">
      <SelectionVariants/>
    </div>
  </div>
</template>

<script>
import RadioButtonGroup from "../../items/RadioButtonGroup.vue";
import AdminUsers from "./admin/AdminUsers.vue";
import InsuranceAdminList from "./admin/InsuranceAdminList.vue";
import FaqEditor from "./admin/FaqEditor.vue";
import UserQuestions from "./admin/UserQuestions.vue";
import SelectionVariants from "./admin/SelectionVariants.vue";

export default {
  components: {SelectionVariants, UserQuestions, FaqEditor: FaqEditor, RadioButtonGroup, AdminUsers, InsuranceAdminList},
  data() {
    return {
      adminUsers: false ,
      insuranceList: false,
      faqEditor: false,
      userQuestions: false,
      selections: true,
      settings: false,
      items: [
        {
          name: 'Пользователи',
          select: () => {
            this.unselectAll()
            this.adminUsers = true
          }
        },
        {
          name: 'Страховки',
          select: () => {
            this.unselectAll()
            this.insuranceList = true
          }
        },
        {
          name: 'FAQ',
          select: () => {
            this.unselectAll()
            this.faqEditor = true
          }
        },
        {
          name: 'Вопросы пользователей',
          select: () => {
            this.unselectAll()
            this.userQuestions = true
          }
        },
        {
          name: 'Подбор',
          select: () => {
            this.unselectAll()
            this.selections = true
          }
        },
        {
          name: 'Настройки',
          select: () => {
            this.unselectAll()
            this.settings = true
          }
        },
      ]
    }
  },
  methods: {
    unselectAll() {
      this.adminUsers = false
      this.insuranceList = false
      this.faqEditor = false
      this.userQuestions = false
      this.selections = false
      this.settings = false
    }
  }
}
</script>

<style scoped>
@import "../../../vue_css/base_form.css";
@import "../../../vue_css/default_size.css";

.admin-panel-title-background:after {
  background-image: url("../../pic/admin.png");
  background-color: #f15959;
}
</style>