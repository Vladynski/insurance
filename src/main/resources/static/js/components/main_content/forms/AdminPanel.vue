<template>
  <div class="base-form">
    <div class="base-form-title def-block title-background-container admin-panel-title-background">
      Панель администрирования
    </div>
    <RadioButtonGroup title="Раздел" :items="items" :selectIndex="0" style="margin-bottom: 5px"/>
    <div class="base-form-data def-block">
      <AdminUsers v-if="adminUsers"/>
    </div>
  </div>
</template>

<script>
import RadioButtonGroup from "../../items/RadioButtonGroup.vue";
import AdminUsers from "./admin/AdminUsers.vue";

export default {
  components: {RadioButtonGroup, AdminUsers},
  data() {
    return {
      adminUsers: true,
      items: [
        {
          name: 'Пользователи',
          select: () => {
          }
        },
        {
          name: '54321',
          select: () => {
          }
        },
        {
          name: '67890',
          select: () => {
          }
        },
      ]
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