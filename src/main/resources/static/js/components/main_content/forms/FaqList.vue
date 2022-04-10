<template>
  <div class="base-form">
    <div class="base-form-title def-block title-background-container faq-title-background">
      Часто задаваемые вопросы
    </div>
    <div class="base-form-data def-block def-block-vertical-scrolling" style="padding: 5%">
      <div class="block-column faq-list" style=" justify-content: flex-start; padding-bottom: 115px">
        <template v-for="(item, index) in faqList">
          <FaqCard :question="item.question" :answer="item.answer"/>
          <hr v-if="index !== faqList.length - 1" class="separator">
        </template>
      </div>
    </div>
  </div>
</template>

<script>
import FaqCard from "./cards/FaqCard.vue";

export default {
  components: {FaqCard},

  data() {
    return {
      faqList: undefined
    }
  },
  mounted() {
    this.$api.getFaqList().then(
        (ok) => {
          this.faqList = ok.data
        }
    )
  }
}
</script>

<style scoped>
.faq-title-background:after {
  background-image: url("../../pic/faq.png");
    background-color: #4fd27c;
}

.faq-list > * {
  margin-bottom: 25px;
}
</style>