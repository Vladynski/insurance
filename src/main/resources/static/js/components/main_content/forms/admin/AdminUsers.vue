<template>
  <div class="block-column" style="justify-content: flex-start; padding: 15px">
    <SlidingBlock ref="findUsersSB" title="Поиск пользователей">
      <FindUserForm :updateList="updateList" :setInfoText="setInfoText"/>
    </SlidingBlock>
    <div class="block-column def-block-vertical-scrolling block-items-margin user-data-list">
      <AdminUserCard v-for="user in userList" :key="user.id" :userdata="user"/>
    </div>
  </div>
</template>

<script>
import SlidingBlock from "../../../items/SlidingBlock.vue";
import Input from "../../../items/Input.vue";
import FindUserForm from "./forms/FindUserForm.vue";
import AdminUserCard from "./forms/AdminUserCard.vue";

export default {
  components: {AdminUserCard, SlidingBlock, Input, FindUserForm},
  data() {
    return {
      userList: undefined
    }
  },
  methods: {
    setInfoText(text) {
      this.$refs.findUsersSB.setInfoText(text)
    },
    updateList(list) {
      this.userList = list
      if (this.userList.length > 0)
        this.$refs.findUsersSB.openBody = false
    }
  }
}
</script>

<style scoped>
.user-data-list {
  margin-top: 20px;
}
</style>