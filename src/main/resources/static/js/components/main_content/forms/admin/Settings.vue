<template>
  <div class="block-column" style="justify-content: flex-start; padding: 15px">
    <InfoFrame ref="infoFrame"/>
    <div class="block-column def-block-vertical-scrolling block-items-margin user-data-list">
      <SettingCard v-for="setting in settingsList" :setting="setting"/>
    </div>
  </div>
</template>

<script>
import SettingCard from "./forms/SettingCard.vue";
import InfoFrame from "../../../items/InfoFrame.vue";

export default {
  components: {InfoFrame, SettingCard},
  data() {
    return {
      settingsList: undefined
    }
  },
  methods: {},
  beforeMount() {
    this.$adminApi.getSettings().then(
        (ok) => {
          this.settingsList = ok.data
        }, (err) => {
          this.$api.errorHandler(err, () => this.$refs.infoFrame)
        }
    )
  }
}
</script>

<style scoped>
.user-data-list {
  margin-top: 20px;
}
</style>