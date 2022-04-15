<template>
  <div class="block-column block-column-items-margin" style="justify-content: flex-start; padding: 10px">
    <InfoFrame v-if="questions.length === 0" defText="Нет неотвеченных вопросов" outsideType="info" :constant="true"/>
    <div v-else class="block-column block-column-items-margin def-block-vertical-scrolling scrolling-margin"
         style="justify-content: flex-start">
      <UserQuestionForm v-for="question in questions" :question="question"/>
    </div>
  </div>
</template>

<script>

import UserQuestionForm from "./forms/UserQuestionForm.vue";
import RadioButtonGroup from "../../../items/RadioButtonGroup.vue";
import InfoFrame from "../../../items/InfoFrame.vue";

export default {
  components: {InfoFrame, RadioButtonGroup, UserQuestionForm},
  data() {
    return {
      questions: []
    }
  },
  beforeMount() {
    this.$adminApi.getQuestions().then((ok) => {
      this.questions = ok.data
      console.log(ok);
    })
  }
}
</script>

<style scoped>

</style>