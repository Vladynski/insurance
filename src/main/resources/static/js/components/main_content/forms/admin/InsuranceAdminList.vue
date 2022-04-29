<template>
  <div class="block-column" style="justify-content: flex-start; padding: 15px">
    <SlidingBlock ref="findInsuranceSB" title="Поиск страховых данных">
      <FindInsuranceForm :updateList="updateList" :setInfoText="setInfoText"/>
    </SlidingBlock>
    <div class="block-column def-block-vertical-scrolling block-items-margin user-data-list">
      <InsuranceCard v-for="item in insuranceList" :key="item.id" :insurance="item"/>
    </div>
  </div>
</template>

<script>
import SlidingBlock from "../../../items/SlidingBlock.vue";
import Input from "../../../items/Input.vue";
import AdminUserCard from "./forms/AdminUserCard.vue";
import FindInsuranceForm from "./forms/FindInsuranceForm.vue";
import InsuranceCard from "../cards/InsuranceCard.vue";

export default {
  components: {InsuranceCard, FindInsuranceForm, AdminUserCard, SlidingBlock, Input},
  data() {
    return {
      insuranceList: undefined
    }
  },
  methods: {
    setInfoText(text) {
      this.$refs.findInsuranceSB.setInfoText(text)
    },
    updateList(list) {
      if (this.insuranceList)
        this.insuranceList.length = 0

      this.insuranceList = list

      if (this.insuranceList.length > 0)
        this.$refs.findInsuranceSB.openBody = false
    }
  }
}
</script>

<style scoped>
.user-data-list {
  margin-top: 20px;
}
</style>